package SoftEng751.SoftEng751.io;

import SoftEng751.SoftEng751.testMethods.DependencyVector;
import SoftEng751.SoftEng751.testMethods.LoopVar;
import spoon.Launcher;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.*;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.compiler.SnippetCompilationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpoonLoopParser implements LoopParser {


    private List<CtFor> loops;
    private List<LoopVar> loopVars;

    public SpoonLoopParser(String file) throws Exception {
    	
    	Launcher spoon = new Launcher();
  
    	
    	CtStatement parsedCode = SnippetCompilationHelper.compileStatement(spoon.getFactory().Code().createCodeSnippetStatement(file));

    	
    	this.loops = parsedCode.getElements(new TypeFilter<CtFor>(CtFor.class));
        if (this.loops.size() > 2) {
            throw new Exception("Error: Can only parse loops with max 2 nested levels");
        }

        if (this.loops.size() < 1) {
            throw new Exception("Error: No loops found in file");
        }
    }
    
    public CtFor getOutermostLoop() {
    	return this.loops.get(0);
    }


    public List<LoopVar> getLoopVars(){
        if (this.loopVars != null) {
            return loopVars;
        }
        List<LoopVar> loopVariables = new ArrayList<LoopVar>();

        LoopVar i = this.getLoopVarFromLoop(this.loops.get(0));
        loopVariables.add(i);
        if (loops.size() > 1) {
            LoopVar j = this.getLoopVarFromLoop(this.loops.get(1));
            loopVariables.add(j);
        }

        this.loopVars = loopVariables;
    	return this.loopVars;
    }

    public List<DependencyVector> getDependencyVectors() {
        List<DependencyVector> dependencyVectors = new ArrayList<DependencyVector>();
        List<String> loopVarNames = this.getLoopVarNames();
        List<CtArrayRead> arrayReads = this.loops.get(0).getElements(new TypeFilter<CtArrayRead>(CtArrayRead.class));

        for (int i = 1; i < arrayReads.size(); i += 3) {
            List<CtBinaryOperator> readExpressions = arrayReads.get(i).getElements(new TypeFilter<CtBinaryOperator>(CtBinaryOperator.class));
            DependencyVector dependencyVector = new DependencyVector(loopVarNames);

            for (CtBinaryOperator expression : readExpressions) {
                try {
                    String leftOperand = expression.getLeftHandOperand().toString();
                    String rightOperand = expression.getRightHandOperand().toString();
                    BinaryOperatorKind operandKind = expression.getKind();

                    String variable;
                    int distance;
                    try {
                        distance = Integer.parseInt(rightOperand);
                        variable = leftOperand;
                    } catch (Exception e) {
                        distance = Integer.parseInt(leftOperand);
                        variable = rightOperand;
                    }

                    if (operandKind == BinaryOperatorKind.PLUS) {
                        distance = distance * -1;
                    }

                    if (dependencyVector.getDependencyDistance(variable) > 0) {
                        // This code will execute if we have an access like A[j+1][j-1]
                        // this is becuz these should be two different vectors
                        DependencyVector secondaryDp = new DependencyVector(loopVarNames);
                        secondaryDp.setDistance(variable, distance);
                        dependencyVectors.add(secondaryDp);
                    } else {
                        dependencyVector.setDistance(variable, distance);
                    }
                } catch (Exception e) {
                    System.out.println("Error: Failed to create dependency vector: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            dependencyVectors.add(dependencyVector);
        }

        return dependencyVectors;
    }


    private List<String> getLoopVarNames() {
        return this.getLoopVars()
                .stream()
                .map(loopVariable -> (loopVariable.getName()))
                .collect(Collectors.toList());

    }

    private LoopVar getLoopVarFromLoop(CtFor loop) {
        List<CtExpression> expressions = loop.getElements(new TypeFilter<CtExpression>(CtExpression.class));
        String name = expressions.get(2).toString();
        int lowerbound = Integer.parseInt(expressions.get(0).toString());
        int upperbound = Integer.parseInt(expressions.get(3).toString());

        return new LoopVar(name, lowerbound, upperbound);
    }
}

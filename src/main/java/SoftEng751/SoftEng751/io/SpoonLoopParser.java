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

/**
 * A concrete implementation of the LoopParser interface.
 * This implementation uses spoon to parse the loops.
 */
public class SpoonLoopParser implements LoopParser {
    private List<CtFor> loops;
    private List<LoopVar> loopVars;

    /**
     * Constructor.
     *
     * @param loop A string representation of the loop to parse.
     * @throws Exception If loop exceeds 2 levels of nesting or if no loops were found.
     */
    public SpoonLoopParser(String loop) throws Exception {
    	Launcher spoon = new Launcher();
    	CtStatement parsedCode = SnippetCompilationHelper.compileStatement(spoon.getFactory().Code().createCodeSnippetStatement(loop));
    	
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
            return this.loopVars;
        }
        List<LoopVar> loopVariables = new ArrayList<LoopVar>();

        LoopVar i = this.getLoopVarFromLoop(this.loops.get(0), 1);
        loopVariables.add(i);
        if (this.loops.size() > 1) {
            LoopVar j = this.getLoopVarFromLoop(this.loops.get(1), 2);
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

    /**
     * Helper function for retrieving the loop var names from the list of loop vars.
     *
     * @return A list of strings representing the names of the loop vars.
     */
    private List<String> getLoopVarNames() {
        return this.getLoopVars()
                .stream()
                .map(loopVariable -> (loopVariable.getName()))
                .collect(Collectors.toList());

    }

    /**
     * A helper function which extracts the loop vars from a spoon for loop.
     *
     * @param loop The for loop to get extract the loop vars from
     * @param dimension The dimension of the loop.
     *
     * @return The extracted loop var.
     */
    private LoopVar getLoopVarFromLoop(CtFor loop, int dimension) {
        List<CtExpression> expressions = loop.getElements(new TypeFilter<CtExpression>(CtExpression.class));
        String name = expressions.get(2).toString();
        int lowerbound = Integer.parseInt(expressions.get(0).toString());
        int upperbound = Integer.parseInt(expressions.get(3).toString());

        return new LoopVar(name, lowerbound, upperbound, dimension);
    }
}

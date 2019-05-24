package SoftEng751.SoftEng751.io;

import SoftEng751.SoftEng751.testMethods.LoopVar;
import spoon.Launcher;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.ArrayList;
import java.util.List;

public class SpoonLoopParser implements LoopParser {

    private List<CtFor> loops;

    public SpoonLoopParser(String file, String methodName) throws Exception {
        CtClass parsedClass = Launcher.parseClass(file);
        this.loops = parsedClass.getMethod(methodName).getElements(new TypeFilter<CtFor>(CtFor.class));

        if (this.loops.size() > 2) {
            throw new Exception("Error: Can only parse loops with max 2 nested levels");
        }

        if (this.loops.size() < 1) {
            throw new Exception("Error: No loops found in file");
        }
    }

    public List<LoopVar> getLoopVars(){
        List<LoopVar> loopVariables = new ArrayList<LoopVar>();

        LoopVar i = this.getLoopVarFromLoop(this.loops.get(0));
        loopVariables.add(i);
        if (loops.size() > 1) {
            LoopVar j = this.getLoopVarFromLoop(this.loops.get(1));
            loopVariables.add(j);
        }

    	return loopVariables;
    }

    public List<int[]> getDependencyVectors() {
        this.printLoops();
        return null;
    }

    private LoopVar getLoopVarFromLoop(CtFor loop) {
        List<CtExpression> expressions = loop.getElements(new TypeFilter<CtExpression>(CtExpression.class));
        String name = expressions.get(2).toString();
        int lowerbound = Integer.parseInt(expressions.get(0).toString());
        int upperbound = Integer.parseInt(expressions.get(3).toString());

        return new LoopVar(name, lowerbound, upperbound);
    }

    // For testing
    private void printLoops() {
        for (CtFor loop : this.loops) {
            System.out.println(loop);
        }
    }
}

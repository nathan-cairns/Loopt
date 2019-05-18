package SoftEng751.SoftEng751.io;

import SoftEng751.SoftEng751.testMethods.LoopVar;
import spoon.Launcher;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.ArrayList;
import java.util.List;

public class SpoonLoopParser implements LoopParser {

    @Override
    public List<LoopVar> getLoopVars(String file, String methodName) throws Exception {
        List<LoopVar> loopVariables = new ArrayList<>();

        CtClass parsedClass = Launcher.parseClass(file);
    	List<CtFor> loops = parsedClass.getMethod(methodName).getElements(new TypeFilter<CtFor>(CtFor.class));

    	if (loops.size() > 2) {
            throw new Exception("Error: Can only parse loops with max 2 nested levels");
        }

    	if (loops.size() < 1) {
    	    throw new Exception("Error: No loops found in file");
        }

    	LoopVar i = this.getLoopVarFromLoop(loops.get(0));
        loopVariables.add(i);
        if (loops.size() > 1) {
            LoopVar j = this.getLoopVarFromLoop(loops.get(1));
            loopVariables.add(j);
        }

    	return loopVariables;
    }

    private LoopVar getLoopVarFromLoop(CtFor loop) {
        List<CtExpression> expressions = loop.getElements(new TypeFilter<CtExpression>(CtExpression.class));

        String name = expressions.get(2).toString();
        int lowerbound = Integer.parseInt(expressions.get(0).toString());
        int upperbound = Integer.parseInt(expressions.get(3).toString());

        return new LoopVar(name, lowerbound, upperbound);
    }
}

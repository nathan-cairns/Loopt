package SoftEng751.SoftEng751.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import SoftEng751.SoftEng751.testMethods.LoopVar;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.Launcher;

public class DefaultOutParser implements OutputParser {

	public String output(List<LoopVar> transformedLoopVars, CtFor originalLoop) {

		Launcher spoon = new Launcher();
		CtFor newFor = spoon.getFactory().Core().createFor();
		//newFor.addForInit();
		
		CtFor loop = originalLoop;
		
		for (LoopVar loopVar : transformedLoopVars) {
			
			String lowerBound = loopVar.boundsName.replaceAll(loopVar.name + "1", Integer.toString(loopVar.lowerbound));
			String upperBound = loopVar.boundsName.replaceAll(loopVar.name + "1", Integer.toString(loopVar.upperbound));
			
			CtStatement forInit = spoon.getFactory().Code().createCodeSnippetStatement("int " + loopVar.name + "1 = " + lowerBound);
			List<CtStatement> forInitList = new ArrayList<CtStatement>();
			forInitList.add(forInit);
			
			CtExpression<Boolean> forExp = spoon.getFactory().createCodeSnippetExpression(loopVar.name + "1 < " + upperBound);
			
			CtStatement forUpdate = spoon.getFactory().Code().createCodeSnippetStatement(loopVar.name + "1++");
			List<CtStatement> forUpdateList = new ArrayList<CtStatement>();
			forUpdateList.add(forUpdate);
			
			loop.setExpression(forExp);
			loop.setForInit(forInitList);
			loop.setForUpdate(forUpdateList);
			
			List<CtFor> loopElements = loop.getBody().getElements(new TypeFilter<CtFor>(CtFor.class));
			if (!loopElements.isEmpty()) {
				loop = loopElements.get(0);
			}
		}
		
		Collections.reverse(transformedLoopVars);
		
		for (LoopVar loopVar : transformedLoopVars) {
			CtStatement varDefine = spoon.getFactory().Code().createCodeSnippetStatement("int " + loopVar.name + " = " + loopVar.transformedname);
			loop.getBody().insertBefore(varDefine);
		}
		
		return originalLoop.toString();
		
	}
	
}

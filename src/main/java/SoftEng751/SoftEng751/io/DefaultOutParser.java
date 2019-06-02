package SoftEng751.SoftEng751.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import SoftEng751.SoftEng751.polyhedral.LoopVar;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.Launcher;

public class DefaultOutParser implements OutputParser {

	public String output(List<LoopVar> transformedLoopVars, CtFor originalLoop) {

		Launcher spoon = new Launcher();
		
		CtFor loop = originalLoop;
		
		// For each loop in the code
		for (LoopVar loopVar : transformedLoopVars) {
			
			// Create the lower and upper bounds of the loop conditions
			String lowerBound = loopVar.boundsName.replaceAll(loopVar.name + "1", Integer.toString(loopVar.lowerbound));
			String upperBound = loopVar.boundsName.replaceAll(loopVar.name + "1", Integer.toString(loopVar.upperbound));
			
			// Create the initializion for the loop conditions
			CtStatement forInit = spoon.getFactory().Code().createCodeSnippetStatement("int " + loopVar.name + "1 = " + lowerBound);
			List<CtStatement> forInitList = new ArrayList<CtStatement>();
			forInitList.add(forInit);
			
			// Create the loop expression
			CtExpression<Boolean> forExp = spoon.getFactory().createCodeSnippetExpression(loopVar.name + "1 < " + upperBound);
			
			// Create the loop update
			CtStatement forUpdate = spoon.getFactory().Code().createCodeSnippetStatement(loopVar.name + "1++");
			List<CtStatement> forUpdateList = new ArrayList<CtStatement>();
			forUpdateList.add(forUpdate);
			
			// Assemble the loop statement
			loop.setExpression(forExp);
			loop.setForInit(forInitList);
			loop.setForUpdate(forUpdateList);
			
			// set the next loop to rewrite as the next nested loop (if it exists)
			List<CtFor> loopElements = loop.getBody().getElements(new TypeFilter<CtFor>(CtFor.class));
			if (!loopElements.isEmpty()) {
				loop = loopElements.get(0);
			}
		}
		
		// Need to write the "i" and "j" (or equivalent variables) in reverse order to how they're stored
		Collections.reverse(transformedLoopVars);
		
		// Define "i" and "j" (or equivalent vars) as functions of the transformed variables
		for (LoopVar loopVar : transformedLoopVars) {
			CtStatement varDefine = spoon.getFactory().Code().createCodeSnippetStatement("int " + loopVar.name + " = " + loopVar.transformedname);
			loop.getBody().insertBefore(varDefine);
		}
		
		return originalLoop.toString();
		
	}
	
}

package SoftEng751.SoftEng751.testMethods;

import java.util.HashMap;

import spoon.reflect.code.CtFor;

public interface DependenceTestsInterface {

	public HashMap<DependenceNode,DependenceNode> outputDependencies(CtFor pretransformedLoop);
	
}

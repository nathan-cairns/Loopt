package SoftEng751.SoftEng751.transformations;

import java.util.ArrayList;
import java.util.HashMap;

import SoftEng751.SoftEng751.testMethods.DependenceNode;

public interface Transformations {

	public String transform(HashMap<DependenceNode,DependenceNode> Dependencies);
	
}

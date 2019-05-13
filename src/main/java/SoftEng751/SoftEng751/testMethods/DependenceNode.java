package SoftEng751.SoftEng751.testMethods;

import java.util.ArrayList;

public abstract class DependenceNode {

	private int _lineNumber;
	private ArrayList<DependenceNode> _dependsOn; // list of nodes that this one depends on (these must come first)
	private ArrayList<DependenceNode> _dependOnThis; // list of nodes that depend on this one
	
	public int getLineNum() {
		return _lineNumber;
	}
	
	public ArrayList<DependenceNode> getDependsOn() {
		return _dependsOn;
	}
	
	public ArrayList<DependenceNode> getDependOnThis() {
		return _dependOnThis;
	}
}

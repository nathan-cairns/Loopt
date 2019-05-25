package SoftEng751.SoftEng751.testMethods;

public class LoopVar {

	public String name = "";
	public String transformedname = "";
	public int lowerbound;
	public int upperbound;
	public String boundsName = "";
	public int dimension;

	public LoopVar(String _name, int _lowerbound, int _upperbound, int _dimension){
		name = _name;
		lowerbound = _lowerbound;
		upperbound = _upperbound;
		dimension = _dimension;
	}

	public String getName() {
		return this.name;
	}
}

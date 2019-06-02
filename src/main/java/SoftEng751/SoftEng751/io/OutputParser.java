package SoftEng751.SoftEng751.io;

import spoon.reflect.code.CtFor;

import java.util.List;

import SoftEng751.SoftEng751.polyhedral.LoopVar;

public interface OutputParser {
	
	/**
	 * Get the new transformed java code in string form
	 * @param transformedLoopVars The loop vars with set transformed name
	 * @param originalLoop The original loop code that was input into the system
	 * @return The transformed code (in string form)
	 */
    String output(List<LoopVar> transformedLoopVars, CtFor originalLoop);
}

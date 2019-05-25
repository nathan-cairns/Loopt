package SoftEng751.SoftEng751;

import java.io.File;
import java.util.List;

import SoftEng751.SoftEng751.io.DefaultOutParser;
import SoftEng751.SoftEng751.io.LoopParser;
import SoftEng751.SoftEng751.io.OutputParser;
import SoftEng751.SoftEng751.io.SpoonLoopParser;
import SoftEng751.SoftEng751.testMethods.DependencyVector;
import org.apache.commons.io.FileUtils;

import SoftEng751.SoftEng751.testMethods.AffineTransformation;
import SoftEng751.SoftEng751.testMethods.LoopVar;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	// Input parsing
    	String str = FileUtils.readFileToString(new File("./testFor.txt"));
		LoopParser loopParser = new SpoonLoopParser(str);
		List<LoopVar> loopvariables = loopParser.getLoopVars();
		List<DependencyVector> dependencyVectors = loopParser.getDependencyVectors();

    	AffineTransformation m = new AffineTransformation();
		List<LoopVar> transformedVariables = m.method(loopvariables);

		// Output generation
    	OutputParser out = new DefaultOutParser();
    	String outString = out.output(transformedVariables, loopParser.getOutermostLoop());
    	System.out.print(outString);
    }
    

}

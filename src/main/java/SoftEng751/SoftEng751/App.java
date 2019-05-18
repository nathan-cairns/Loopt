package SoftEng751.SoftEng751;

import java.io.File;
import java.util.List;

import SoftEng751.SoftEng751.io.LoopParser;
import SoftEng751.SoftEng751.io.SpoonLoopParser;
import org.apache.commons.io.FileUtils;

import SoftEng751.SoftEng751.testMethods.AffineTransformation;
import SoftEng751.SoftEng751.testMethods.LoopVar;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	String str = FileUtils.readFileToString(new File("./src/main/java/SoftEng751/SoftEng751/testClass.java"));
    	str = str.replaceAll("package SoftEng751.SoftEng751;", "");

		LoopParser loopParser = new SpoonLoopParser();
		List<LoopVar> loopvariables = loopParser.getLoopVars(str, "transformLoop");
    	AffineTransformation m = new AffineTransformation();
		List<LoopVar> transformedVariables = m.method(loopvariables);
    }
    

}

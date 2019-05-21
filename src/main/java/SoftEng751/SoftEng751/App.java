package SoftEng751.SoftEng751;

import java.io.File;
import java.util.List;

import SoftEng751.SoftEng751.io.DefaultOutParser;
import SoftEng751.SoftEng751.io.LoopParser;
import SoftEng751.SoftEng751.io.OutputParser;
import SoftEng751.SoftEng751.io.SpoonLoopParser;
import org.apache.commons.io.FileUtils;

import SoftEng751.SoftEng751.testMethods.AffineTransformation;
import SoftEng751.SoftEng751.testMethods.LoopVar;
import spoon.Launcher;
import spoon.reflect.code.CtFor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

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
		
		
		CtClass parsedClass = Launcher.parseClass(str);
    	List<CtFor> loops = parsedClass.getMethod("transformLoop").getElements(new TypeFilter<CtFor>(CtFor.class));
    	
    	OutputParser out = new DefaultOutParser();
    	out.output(transformedVariables, loops.get(0));
    	
    	
		
    }
    

}

package SoftEng751.SoftEng751;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtArrayRead;
import spoon.reflect.code.CtArrayWrite;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.Factory;
import spoon.reflect.visitor.filter.TypeFilter;
import SoftEng751.SoftEng751.testClass;
import SoftEng751.SoftEng751.testMethods.AffineTransformation;
import SoftEng751.SoftEng751.testMethods.LoopVar;
import SoftEng751.SoftEng751.io.DefaultOutParser;
import SoftEng751.SoftEng751.io.OutputParser;

import static spoon.testing.utils.ModelUtils.build;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	String str = FileUtils.readFileToString(new File("./src/main/java/SoftEng751/SoftEng751/testClass.Java"));
    	str = str.replaceAll("package SoftEng751.SoftEng751;", "");
    	CtClass l = Launcher.parseClass(str);
    	CtFor loop = l.getMethod("transformLoop").getElements(new TypeFilter<CtFor>(CtFor.class)).get(0);
    	List<CtExpression> ex = loop.getElements(new TypeFilter<CtExpression>(CtExpression.class));
    	
    	
    	AffineTransformation m = new AffineTransformation();
    	
		LoopVar i = new LoopVar("i",1,5,0);
		LoopVar j = new LoopVar("j",1,6,1);
		ArrayList<LoopVar> variables = new ArrayList<LoopVar>();
		variables.add(i);
		variables.add(j);
		
		ArrayList<LoopVar> transformedVariables = m.method(variables);
		
    	
    }
    

}

package SoftEng751.SoftEng751;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtArrayRead;
import spoon.reflect.code.CtArrayWrite;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.visitor.filter.TypeFilter;
import SoftEng751.SoftEng751.testClass;
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
    	
    	System.out.println( loop.getElements(new TypeFilter<CtStatement>(CtStatement.class)).get(3) );
    	

        
        
    }
    

}

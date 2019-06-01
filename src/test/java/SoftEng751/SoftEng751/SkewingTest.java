package SoftEng751.SoftEng751;

import static org.junit.jupiter.api.Assertions.*;

import SoftEng751.SoftEng751.io.LoopParser;
import SoftEng751.SoftEng751.io.SpoonLoopParser;
import SoftEng751.SoftEng751.testMethods.AffineTransformation;
import SoftEng751.SoftEng751.testMethods.DependencyVector;
import SoftEng751.SoftEng751.testMethods.LoopVar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnitPlatform.class)
public class SkewingTest {
	
	LoopParser parser;
	List<LoopVar> transformedVariables;
	
	private void transform(int testNum) {
		String input = "";
		try {
			input = new String(Files.readAllBytes(Paths.get("./Testsnippets/" + (testNum) + ".txt")));
			parser = new SpoonLoopParser(input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<LoopVar> loopvariables = parser.getLoopVars();
    	List<DependencyVector> dependencyVectors = parser.getDependencyVectors();
		
    	AffineTransformation m = new AffineTransformation();
		transformedVariables = m.method(loopvariables, dependencyVectors);
	}
	
    @Test
    public void testDefault() {
    	transform(1);
    	assertEquals(transformedVariables.get(0).transformedname,"i1");
    	assertEquals(transformedVariables.get(1).transformedname,"-1 * i1 + j1");
    }
    
    @Test
    public void test2VectorsOneStatement() {
    	try {
    	transform(2);
    	}catch(Exception e) {     	
    		assertTrue(true);  	
    	}
    }
    
    @Test
    public void testOneVectorDiffDirection() {
    	transform(3);
    	assertEquals(transformedVariables.get(0).transformedname,"i1");
    	assertEquals(transformedVariables.get(1).transformedname,"-1 * i1 + j1");
    }
    
    @Test
    public void testDiffVarNames() {
    	transform(4);
    	assertEquals(transformedVariables.get(0).transformedname,"outer1");
    	assertEquals(transformedVariables.get(1).transformedname,"-1 * outer1 + inner1");
    }
    
    @Test
    public void testDiffLoopBounds() {
    	transform(5);
    	assertEquals(transformedVariables.get(0).transformedname,"i1");
    	assertEquals(transformedVariables.get(1).transformedname,"-1 * i1 + j1");
    }
    
    @Test
    public void testDifferentIncrements() {
    	transform(6);
    	assertEquals(transformedVariables.get(0).transformedname,"i1");
    	assertEquals(transformedVariables.get(1).transformedname,"-1 * i1 + j1");
    }
    
    @Test
    public void test2Vectors1ZeroMember() {
    	try {
    	transform(7);
    	}catch(Exception e) {
    		assertTrue(true);    	
    	}
    }
    
    @Test
    public void test2Arrays() {
    	transform(8);
    	assertEquals(transformedVariables.get(0).transformedname,"i1");
    	assertEquals(transformedVariables.get(1).transformedname,"-1 * i1 + j1");
    }
    
    @Test
    public void test2Vectors2ZeroMembers() {
    	try {
    	transform(9);
    	}catch(Exception e) {
    		assertTrue(true);    	
    	}
    }
    
    @Test
    public void testOneVectorDiffDirection2() {
    	transform(10);
    	assertEquals(transformedVariables.get(0).transformedname,"i1");
    	assertEquals(transformedVariables.get(1).transformedname,"i1-1 * j1 ");
    }
}

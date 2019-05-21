package SoftEng751.SoftEng751.testMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.ejml.data.DMatrixRMaj;
import org.ejml.equation.Equation;

public class AffineTransformation {

	
	public List<LoopVar> method(List<LoopVar> variables){
		
		
		DMatrixRMaj x = new DMatrixRMaj(4, 2); //fixed for 2vars
		DMatrixRMaj t = new DMatrixRMaj(2, 2); //fixed
		DMatrixRMaj tdash = new DMatrixRMaj(2, 2); //fixed
		DMatrixRMaj dv = new DMatrixRMaj(2, 1);
		DMatrixRMaj b = new DMatrixRMaj(4, 1);

		Equation eq = new Equation();
		eq.alias(x, "x", t, "t", tdash, "tdash", dv, "dv", b, "b");

		eq.process("x = [-1,0;1,0;0,-1;0,1]");
		eq.process("t = [1,0;1,1]");
		eq.process("tdash = [1,0;-1,1]");
		eq.process("dv = [1;-1]");
		eq.process("b = [-1;6;-1;5]");
		
		for(int u = 0; u < variables.size(); u++){
			
			LoopVar current = variables.get(u);
			for(int v = 0; v < variables.size(); v++){
				
				int coefTDash = (int) tdash.get(u, v);
				int coefT = (int) t.get(u, v);
				

				if(coefTDash > 0 && v >= 1){
					
					current.transformedname += "+ ";
				}
				
				if(coefTDash == 0){
					
				}else if(coefTDash == 1){
					
					current.transformedname += variables.get(v).name + "1";
					
				}else{
					
					current.transformedname += String.format("%d * %s1 ", coefTDash, variables.get(v).name);
					
				}
				
				if(coefT > 0 && v >= 1){
					
					current.boundsName += "+ ";
				}
				
				if(coefT == 0){
					
				}else if(coefT == 1){
					
					current.boundsName += variables.get(v).name + "1";
					
				}else{
					
					current.boundsName += String.format("%d * %s1 ", coefT, variables.get(v).name);
					
				}
				
					
			}
		}
		
		
		return variables;
	} 
	
}

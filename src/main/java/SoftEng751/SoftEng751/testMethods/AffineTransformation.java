package SoftEng751.SoftEng751.testMethods;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.equation.Equation;
import org.ejml.simple.SimpleMatrix;

public class AffineTransformation {

	public List<LoopVar> method(List<LoopVar> variables, List<DependencyVector> dependencyVectors) {
		
		//retrieve the a list of dependency vector matrix
		ArrayList<DMatrixRMaj> dv = generateDV(dependencyVectors, variables);
		
		//generate the transformation matrix
		DMatrixRMaj t = generateT(dv); 
		
		//generate the inverse of the transformation matrix
		DMatrixRMaj tdash = new DMatrixRMaj(2, 2);
		CommonOps_DDRM.invert(t, tdash); // fixed
		System.out.println(t.toString());
		System.out.println(tdash.toString());

		//for each variable within the loop
		for (int u = 0; u < variables.size(); u++) {

			LoopVar current = variables.get(u);
			
			//loop through all the other variables
			for (int v = 0; v < variables.size(); v++) {

				//define the variable in terms of other variables post transformation
				//based on the inverse of t
				int coefTDash = (int) tdash.get(u, v);
				int coefT = (int) t.get(u, v);
				
				//use values in inverse t to variable transformation mapping
				if (coefTDash > 0 && v >= 1) {

					current.transformedname += "+ ";
				}

				if (coefTDash == 0) {

				} else if (coefTDash == 1) {

					current.transformedname += variables.get(v).name + "1";

				} else {

					current.transformedname += String.format("%d * %s1 ", coefTDash, variables.get(v).name);

				}
				
				//use values in transformation matrix to find bounds of loops
				if (coefT > 0 && v >= 1) {

					current.boundsName += "+ ";
				}

				if (coefT == 0) {

				} else if (coefT == 1) {

					current.boundsName += variables.get(v).name + "1";

				} else {

					current.boundsName += String.format("%d * %s1 ", coefT, variables.get(v).name);

				}

			}
		}

		//return variables with new 'transformed name', field declared
		return variables;
	}

	//help method to generate a list of dv matricies using a list of DependencyVector objects and loopVars
	private ArrayList<DMatrixRMaj> generateDV(List<DependencyVector> dependencyVectors, List<LoopVar> variables) {
		
		//delare an empty matrix list
		ArrayList<DMatrixRMaj> list = new ArrayList<DMatrixRMaj>();
		
		//for each dependency in dependencyVectors
		for(DependencyVector dvBox : dependencyVectors){
			
			DMatrixRMaj dv = new DMatrixRMaj(2, 1);
			
			//unpack the object
			for(LoopVar var: variables)
			{
				
				//try to put values in matrix
				try {
					dv.set(var.dimension - 1, 0, dvBox.getDependencyDistance(var.getName()));
				} catch (Exception e) {
					dv.set(var.dimension - 1, 0, 0);
				}
				
			}
			
			//add dv matrix to list
			list.add(dv);
		}
		
		return list;
	}

	//generates the transformation matrix using bruteforce tests
	private DMatrixRMaj generateT(ArrayList<DMatrixRMaj> dvList){
		
		DMatrixRMaj dv = dvList.get(0);
		System.out.println(dv);
		dvList.remove(0);
		
		int x = (int) dv.getData()[0];
		int y = (int) dv.getData()[1];
		
		//if dependency is 0 for an array access, loop can already be parrallelised
		//stop transformation
		if(x == 0 || y == 0){
			
			System.out.println("No skewing transformation found for this case");
			return null;
			
			
		}
		
		ArrayList<Integer> commonMultiples = new ArrayList<Integer>();
		
		//attempt to generate a list of multiples 
		for(int i = 0; i < 1000; i++){
		
			int temp = x * y;
			
			//check for common multiple between 2 values of dv
			if(x*i % Math.abs(y) == 0)
			{
				commonMultiples.add(x*i);
			}
			
			//obtain a list of 100
			if(commonMultiples.size() >= 100){
				
				break;
				
			}
			
		}
		
		//run check for each common multiple in list
		for(int testMultiple : commonMultiples){
			int c = testMultiple / x;
			int d = testMultiple / -y;
			
			//try find a value that satisfies constraints
			// b must be 0
			// Determinant must be 1
			
			for(int a = 1; a < 100; a++){
				
				//in conditions met, return candidate transformation matrix
				if(Math.abs(a*d) == 1){
					
					int b = 0;
					DMatrixRMaj t = new DMatrixRMaj(2, 2);
					t.set(0, 0, a);
					t.set(0, 1, b);
					t.set(1, 0, c);
					t.set(1, 1, d);
					
					//if more than one dependency vector exists, check 
					//satisfaction for all
					if(dvList.size() > 0){
						if(bruteTest(t, dvList)){
							
							return t;
							
						}
						
					}else {
						
						return t;

						
					}
					
				}
				
				
			}
		}
		//if no transformation can be found return null
		System.out.println("no transformations could be found");
		return null;
		
		
	}
	
	//helper method to see constraints for t * dv applies
	private boolean bruteTest(DMatrixRMaj t, ArrayList<DMatrixRMaj> dvList) {

	
		for(DMatrixRMaj dvMatrix : dvList){
			DMatrixRMaj c = new DMatrixRMaj(2,1);
			CommonOps_DDRM.mult(t,dvMatrix, c);
			
			if(c.get(1,0) == 0){
				
			return true;	
				
			}

		}
		
		return false;
	}
}

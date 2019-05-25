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
		Equation eq = new Equation();
		DMatrixRMaj x = new DMatrixRMaj(4, 2); // fixed for 2vars
		
		ArrayList<DMatrixRMaj> dv = generateDV(dependencyVectors, variables);
		
		DMatrixRMaj b = new DMatrixRMaj(4, 1);

		eq.alias(x, "x", b, "b");
		eq.process("x = [-1,0;1,0;0,-1;0,1]");
		eq.process("b = [-1;6;-1;5]");
		
		DMatrixRMaj t = generateT(dv,eq); 
		DMatrixRMaj tdash = new DMatrixRMaj(2, 2);
		CommonOps_DDRM.invert(t, tdash); // fixed
		System.out.println(t.toString());
		System.out.println(tdash.toString());

		for (int u = 0; u < variables.size(); u++) {

			LoopVar current = variables.get(u);
			for (int v = 0; v < variables.size(); v++) {

				int coefTDash = (int) tdash.get(u, v);
				int coefT = (int) t.get(u, v);

				if (coefTDash > 0 && v >= 1) {

					current.transformedname += "+ ";
				}

				if (coefTDash == 0) {

				} else if (coefTDash == 1) {

					current.transformedname += variables.get(v).name + "1";

				} else {

					current.transformedname += String.format("%d * %s1 ", coefTDash, variables.get(v).name);

				}

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

		return variables;
	}

	private ArrayList<DMatrixRMaj> generateDV(List<DependencyVector> dependencyVectors, List<LoopVar> variables) {
		
		ArrayList<DMatrixRMaj> list = new ArrayList<DMatrixRMaj>();
		
		for(DependencyVector dvBox : dependencyVectors){
			
			DMatrixRMaj dv = new DMatrixRMaj(1, 2);
			for(LoopVar var: variables)
			{
				
				try {
					dv.set(0, var.dimension - 1, dvBox.getDependencyDistance(var.getName()));
				} catch (Exception e) {
					dv.set(0, var.dimension - 1, 0);
				}
				
			}
			list.add(dv);
		}
		
		return list;
	}

	private DMatrixRMaj generateT(ArrayList<DMatrixRMaj> dvList, Equation eq){
		
		DMatrixRMaj dv = dvList.get(0);
		dvList.remove(0);
		
		int x = (int) dv.getData()[0];
		int y = (int) dv.getData()[1];
		
		ArrayList<Integer> commonMultiples = new ArrayList<Integer>();
		
		for(int i = 1; i < 100; i++){
		
			int temp = x * y;
			if(x*i % Math.abs(y) == 0)
			{
				commonMultiples.add(x*i);
			}
			if(commonMultiples.size() >= 100){
				
				break;
				
			}
			
		}
		
		for(int testMultiple : commonMultiples){
			int c = testMultiple / x;
			int d = testMultiple / -y;
			for(int a = 1; a < 100; a++){
				

				if((a*d - 1)%c == 0){
					
					int b = (a*d - 1)/c;
					DMatrixRMaj t = new DMatrixRMaj(2, 2);
					t.set(0, 0, a);
					t.set(0, 1, b);
					t.set(1, 0, c);
					t.set(1, 1, d);
					
					if(dvList.size() > 0){
						if(bruteTest(t, dvList)){
							
							return t;
							
						}
					}
					
				}
				
				
			}
		}
		System.out.println("no transformations could be found");
		return null;
		
		
	}

	private boolean bruteTest(DMatrixRMaj t, ArrayList<DMatrixRMaj> dvList) {

	
		for(DMatrixRMaj dvMatrix : dvList){
			DMatrixRMaj c = new DMatrixRMaj(1,2);
			CommonOps_DDRM.mult(dvMatrix,t, c);
			if(c.get(0,1) == 0){
				
			return true;	
				
			}
		}
	
		return false;
	}
}

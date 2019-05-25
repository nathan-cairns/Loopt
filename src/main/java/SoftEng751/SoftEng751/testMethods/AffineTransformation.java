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

	public List<LoopVar> method(List<LoopVar> variables) {
		
		Equation eq = new Equation();
		DMatrixRMaj x = new DMatrixRMaj(4, 2); // fixed for 2vars
		DMatrixRMaj dv = new DMatrixRMaj(2, 1);
		
		DMatrixRMaj b = new DMatrixRMaj(4, 1);

		eq.alias(x, "x", dv, "dv", b, "b");
		eq.process("x = [-1,0;1,0;0,-1;0,1]");
		eq.process("dv = [1;-1]");
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

	private DMatrixRMaj generateT(DMatrixRMaj dv, Equation eq){
		
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
		System.out.println(commonMultiples.size());
		
		for(int testMultiple : commonMultiples){
			int c = testMultiple / x;
			int d = testMultiple / -y;
			for(int a = 1; a < 100; a++){
				

				if((a*d - 1)%c == 0){
					
					int b = (a*d - 1)/c;
					System.out.println(a + " " + b + " " + c + " " + d);
					DMatrixRMaj t = new DMatrixRMaj(2, 2);
					t.set(0, 0, a);
					t.set(0, 1, b);
					t.set(1, 0, c);
					t.set(1, 1, d);
					return t;
					
				}
				
				
			}
		}
		
		return null;
		
		
	}

	private void bruteTest() {

	}
}

package SoftEng751.SoftEng751;

public class testClass {

	int[][] A;
	
	public void transformLoop(){

	    	for(int i = 0; i < 6; i++){
	    		
	    		for(int j = 0; j < 5; j++)
	    		{

	    			A[i][j] = A[i-1][j+1] + 1;

	    		}
	    		
	    	}
		
	} 
	
}

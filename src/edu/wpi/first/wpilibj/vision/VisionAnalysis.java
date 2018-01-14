package edu.wpi.first.wpilibj.vision;

public class VisionAnalysis {

	public static double distance(double p){
		  double[][] table = new double[7][2];
		  table[0][0]=190;
		  table[0][1]=19;
		     
		  table[1][0]=152;
		  table[1][1]=24;
		     
		  table[2][0]=123;
		  table[2][1]=30;
		    
		  table[3][0]=95;
		  table[3][1]=39;
		     
		  table[4][0]=78;
		  table[4][1]=47;
		    
		  table[5][0]=62;
		  table[5][1]=59;
		     
		  table[6][0]=52;
		  table[6][1]=72;
		     
		  table[6][0]=47;
		  table[6][1]=80;
		     
		  Boolean toggle = true;
		  int i =0;
		  while (toggle){
			  if (table[i][0]>=p & table[i+1][0]<=p){
				  toggle = false;
			  }
			  else{
				  i++;
			  }
		  }   
		     
		  return table[i][1]+(p-table[i][0])*(table[i+1][1]-table[i][1])/(table[i+1][0]-table[i][0]);
	}
		  public static void main(String[] args) {
		    System.out.println(distance(135.5));
		  }
	
}

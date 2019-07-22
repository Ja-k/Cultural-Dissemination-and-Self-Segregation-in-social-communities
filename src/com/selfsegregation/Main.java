package com.selfsegregation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
	
	//=============================================================
	public static double randomGenerator(int min,int interval){
		double random = (int)((Math.random() *   (interval - min) )+ min) ; 
		return random;
	}
	public static double doubleRandomGenerator(int min,int interval){
		double random = ((Math.random() *   (interval - min) )+ min) ; 
		return random;
	}
	/*
	public static double kronockerDelta(Node n1, Node n2){
		double sum = 0;
		for(int i = 0 ; i < n1.getF().size() ; i++){
			if( n1.getF().get(i) == n2.getF().get(i) ){
				sum++;
			}else continue;
		}
		return sum;
	}
	*/
	//=============================================================
	public static List<Node> generateAgents( int N ,int q ){
		List<Node> agents = new ArrayList<>();
		for(int n = 0 ; n < N ; n++){
			agents.add( new Node(n , q ) );
		}
		System.out.println("Agents Generated.");
		return agents;
	}
	//===============================================================
	
	public static Node[][] populateLattice(List<Node> agents , int L){
		System.out.println("Constructing Lattice ...");
		Node[][] square_lattice = new Node[L][L];
		System.out.println("Populating Square_Lattice by Agents ... ");
		System.out.println();
		int row;
		int column;
		while(!(agents.isEmpty())){
			row = (int)randomGenerator(0,L);
			column = (int)randomGenerator(0,L);
			if(square_lattice[row][column] == null){
				square_lattice[row][column] = agents.get(0);
				agents.remove(0);
				}else continue;
		}
		System.out.println("The Lattice Has Been Populated by Agents.");
		System.out.println();
		return square_lattice;
	}
	//======================================================================
	
	public static void printLattic(Node[][] square_lattice , int L){
		System.out.println("After Populating the Lattice , It's As Follow : ");
		System.out.println();
		for(int s = 0 ; s < square_lattice.length ; s++){
			System.out.print("[ ");
			for(int c = 0 ; c < square_lattice.length ; c++){
				if(square_lattice[s][c] != null){
				System.out.print(" "+square_lattice[s][c].getId()+" ");
				}else{
					System.out.print(" E ");
				}
			}
			System.out.print(" ]");
			System.out.println();
		}
	}
	public static double cultural_overlap(Node n1 , Node n2){
		int sum = 0;
		for(int i = 0 ; i < n1.getF().size();i++){
			if(n1.getF().get(i) == n2.getF().get(i)){
				sum++;
			}else continue;
		}
		
		return sum / n1.getF().size();
	}
	
	//======================= Main Method ========================
	public static void main(String[] args) {
		
		//============= Parameter Initialization ==================
		int L = 10; 								// Lattice size
		int q = 5 ;									// no. of cultures traits
		double T; 									// Threshold
		double h = 0.05; 							// Empty Sites Density in our Lattice
		int N = (int)(( 1 - h ) * ( Math.pow(L,2)));// no. of agents
		int M ;										// No. of Iterations
 		//=========================================================
				
		List<Node> agents = generateAgents(N , q );
		
		//Prints Cultural Traits of Each Node
		/*
		for(int n = 0; n < agents.size(); n++){
			System.out.print("Cultural Traits of Agent ( " + n + " ) are : "+" [ ");
			for(int i = 0 ; i < agents.get(n).getF().size();i++){
				System.out.print(" " + agents.get(n).getF().get(i)+ " ");
			}
			System.out.println(" ] ");
			System.out.println();
		}
		*/
		
		Node[][] square_lattice = populateLattice( agents , L);
		printLattic(square_lattice , L );
		
		int counter = 0;
		boolean first = true;
		List<List<Node>> NeighborHood = new ArrayList<>();
		System.out.println("Neighbor Identification in Process ... ");
		System.out.println();
		for(int i = 0 ; i < square_lattice.length; i++){
			for(int j = 0 ; j < square_lattice.length ; j++ ){
				if(first){}
				else{counter++;}
				if(square_lattice[i][j] != null){
					if(j == 0 && i == 0){
						List<Node> neighbors = new ArrayList<>();
						neighbors.add(square_lattice[i][j]);
						if(square_lattice[i+1][j] != null){
							neighbors.add(square_lattice[i+1][j]);
						}
						if(square_lattice[i][j+1] != null){
							neighbors.add(square_lattice[i][j+1]);
						}
						NeighborHood.add(neighbors);
												
					}else if( i == 0 && ((j+1) <= square_lattice.length - 1) ){
						List<Node> neighbors = new ArrayList<>();
						neighbors.add(square_lattice[i][j]);
						if(square_lattice[i][j+1] != null){
							neighbors.add(square_lattice[i][j+1]);
						}
						if(square_lattice[i][j-1] != null){
							neighbors.add(square_lattice[i][j-1]);
						}
						if(square_lattice[i+1][j] != null){
							neighbors.add(square_lattice[i+1][j]);
						}
						NeighborHood.add(neighbors);
						
						
					}else if( i == 0 && j == (square_lattice.length-1)){
						List<Node> neighbors = new ArrayList<>();
						neighbors.add(square_lattice[i][j]);
						if(square_lattice[i][j-1] != null){
							neighbors.add(square_lattice[i][j-1]);
						}
						if(square_lattice[i+1][j] != null){
							neighbors.add(square_lattice[i+1][j]);
						}
						NeighborHood.add(neighbors);
						
					}else if( j == 0 && ((i+1) <= square_lattice.length-1) ){
						List<Node> neighbors = new ArrayList<>();
						neighbors.add(square_lattice[i][j]);
						if(square_lattice[i-1][j] != null){
							neighbors.add(square_lattice[i-1][j]);
						}
						if(square_lattice[i][j+1] != null){
							neighbors.add(square_lattice[i][j+1]);
						}
						if(square_lattice[i+1][j] != null){
							neighbors.add(square_lattice[i+1][j]);
						}
						NeighborHood.add(neighbors);
						
					}else if( j == (square_lattice.length - 1)&& ((i+1) <= square_lattice.length-1) ){
						List<Node> neighbors = new ArrayList<>();
						neighbors.add(square_lattice[i][j]);
						if(square_lattice[i-1][j] != null){
							neighbors.add(square_lattice[i-1][j]);
						}
						if(square_lattice[i][j-1] != null){
							neighbors.add(square_lattice[i][j-1]);
						}
						if(square_lattice[i+1][j] != null){
							neighbors.add(square_lattice[i+1][j]);
						}
						NeighborHood.add(neighbors);
						
					}else if( (i == (square_lattice.length - 1)) && (j == 0)){
						List<Node> neighbors = new ArrayList<>();
						neighbors.add(square_lattice[i][j]);
						if(square_lattice[i-1][j] != null){
							neighbors.add(square_lattice[i-1][j]);
						}
						if(square_lattice[i][j+1] != null){
							neighbors.add(square_lattice[i][j+1]);
						}
						NeighborHood.add(neighbors);
						
					}else if( (i == square_lattice.length - 1)&& ((j+1) <= square_lattice.length - 1) ){
						List<Node> neighbors = new ArrayList<>();
						neighbors.add(square_lattice[i][j]);
						if(square_lattice[i][j-1] != null){
							neighbors.add(square_lattice[i][j-1]);
						}
						if(square_lattice[i][j+1] != null){
							neighbors.add(square_lattice[i][j+1]);
						}
						if(square_lattice[i-1][j] != null){
							neighbors.add(square_lattice[i-1][j]);
						}
						NeighborHood.add(neighbors);
						
					}else if((i == (square_lattice.length - 1 ))&&(j == (square_lattice.length - 1))){
						List<Node> neighbors = new ArrayList<>();
						neighbors.add(square_lattice[i][j]);
						if(square_lattice[i][j-1] != null){
							neighbors.add(square_lattice[i][j-1]);
						}
						if(square_lattice[i-1][j] != null){
							neighbors.add(square_lattice[i-1][j]);
						}
						
						NeighborHood.add(neighbors);
					}else{
						List<Node> neighbors = new ArrayList<>();
						neighbors.add(square_lattice[i][j]);
						if(square_lattice[i][j+1] != null){
							neighbors.add(square_lattice[i][j+1]);
						}
						if(square_lattice[i][j-1] != null){
							neighbors.add(square_lattice[i][j-1]);
						}
						if(square_lattice[i+1][j] != null){
							neighbors.add(square_lattice[i+1][j]);
						}
						if(square_lattice[i-1][j] != null){
							neighbors.add(square_lattice[i-1][j]);
						}
						NeighborHood.add(neighbors);
						
					}
					
				}else continue;
				
			}
		}
 		
		
		
		
		
		
		
		/*
		List<Node> segregated = new ArrayList<>();
		HashMap< Node , List<Node> > graph = new HashMap<>();
		try{
		for(int i = 0 ; i < square_lattice.length; i++){
			for(int j = 0 ; j < square_lattice.length; j++){
				if(square_lattice[i][j] != null){
					if(j == 0 && i == 0){
						List<Node> neighbors = new ArrayList<>();
						if(square_lattice[i+1][j] != null){
							neighbors.add(square_lattice[i+1][j]);
						}
						if(square_lattice[i][j+1] != null){
							neighbors.add(square_lattice[i][j+1]);
						}
						if(neighbors.size() == 0){
							segregated.add(square_lattice[i][j]);
						}else{
							graph.put(square_lattice[i][j], neighbors);
						}
												
					}else if( i == 0 && ((j+1) <= square_lattice.length - 1) ){
						List<Node> neighbors = new ArrayList<>();
						if(square_lattice[i][j+1] != null){
							neighbors.add(square_lattice[i][j+1]);
						}
						if(square_lattice[i][j-1] != null){
							neighbors.add(square_lattice[i][j-1]);
						}
						if(square_lattice[i+1][j] != null){
							neighbors.add(square_lattice[i+1][j]);
						}
						if(neighbors.size() == 0){
							segregated.add(square_lattice[i][j]);
						}else{
							graph.put(square_lattice[i][j], neighbors);
						}
						//graph.put(square_lattice[i][j], neighbors);
						
					}else if( i == 0 && j == (square_lattice.length-1)){
						List<Node> neighbors = new ArrayList<>();
						if(square_lattice[i][j-1] != null){
							neighbors.add(square_lattice[i][j-1]);
						}
						if(square_lattice[i+1][j] != null){
							neighbors.add(square_lattice[i+1][j]);
						}
						if(neighbors.size() == 0){
							segregated.add(square_lattice[i][j]);
						}else{
							graph.put(square_lattice[i][j], neighbors);
						}
						graph.put(square_lattice[i][j], neighbors);
						
					}else if( j == 0 && ((i+1) <= square_lattice.length-1) ){
						List<Node> neighbors = new ArrayList<>();
						if(square_lattice[i-1][j] != null){
							neighbors.add(square_lattice[i-1][j]);
						}
						if(square_lattice[i][j+1] != null){
							neighbors.add(square_lattice[i][j+1]);
						}
						if(square_lattice[i+1][j] != null){
							neighbors.add(square_lattice[i+1][j]);
						}
						if(neighbors.size() == 0){
							segregated.add(square_lattice[i][j]);
						}else{
							graph.put(square_lattice[i][j], neighbors);
						}
						//graph.put(square_lattice[i][j], neighbors);
						
					}else if( j == (square_lattice.length - 1)&& ((i+1) <= square_lattice.length-1) ){
						List<Node> neighbors = new ArrayList<>();
						if(square_lattice[i-1][j] != null){
							neighbors.add(square_lattice[i-1][j]);
						}
						if(square_lattice[i][j-1] != null){
							neighbors.add(square_lattice[i][j-1]);
						}
						if(square_lattice[i+1][j] != null){
							neighbors.add(square_lattice[i+1][j]);
						}
						if(neighbors.size() == 0){
							segregated.add(square_lattice[i][j]);
						}else{
							graph.put(square_lattice[i][j], neighbors);
						}
						//graph.put(square_lattice[i][j], neighbors);
						
					}else if( (i == (square_lattice.length - 1)) && (j == 0)){
						List<Node> neighbors = new ArrayList<>();
						if(square_lattice[i-1][j] != null){
							neighbors.add(square_lattice[i-1][j]);
						}
						if(square_lattice[i][j+1] != null){
							neighbors.add(square_lattice[i][j+1]);
						}
						if(neighbors.size() == 0){
							segregated.add(square_lattice[i][j]);
						}else{
							graph.put(square_lattice[i][j], neighbors);
						}
						//graph.put(square_lattice[i][j], neighbors);
						
					}else if( (i == square_lattice.length - 1)&& ((j+1) <= square_lattice.length - 1) ){
						List<Node> neighbors = new ArrayList<>();
						if(square_lattice[i][j-1] != null){
							neighbors.add(square_lattice[i][j-1]);
						}
						if(square_lattice[i][j+1] != null){
							neighbors.add(square_lattice[i][j+1]);
						}
						if(square_lattice[i-1][j] != null){
							neighbors.add(square_lattice[i-1][j]);
						}
						if(neighbors.size() == 0){
							segregated.add(square_lattice[i][j]);
						}else{
							graph.put(square_lattice[i][j], neighbors);
						}
						//graph.put(square_lattice[i][j], neighbors);
						
					}else if((i == (square_lattice.length - 1 ))&&(j == (square_lattice.length - 1))){
						List<Node> neighbors = new ArrayList<>();
						if(square_lattice[i][j-1] != null){
							neighbors.add(square_lattice[i][j-1]);
						}
						if(square_lattice[i-1][j] != null){
							neighbors.add(square_lattice[i-1][j]);
						}
						if(neighbors.size() == 0){
							segregated.add(square_lattice[i][j]);
						}else{
							graph.put(square_lattice[i][j], neighbors);
						}
						//graph.put(square_lattice[i][j], neighbors);
						
					}else{
						List<Node> neighbors = new ArrayList<>();
						if(square_lattice[i][j+1] != null){
							neighbors.add(square_lattice[i][j+1]);
						}
						if(square_lattice[i][j-1] != null){
							neighbors.add(square_lattice[i][j-1]);
						}
						if(square_lattice[i+1][j] != null){
							neighbors.add(square_lattice[i+1][j]);
						}
						if(square_lattice[i-1][j] != null){
							neighbors.add(square_lattice[i-1][j]);
						}
						if(neighbors.size() == 0){
							segregated.add(square_lattice[i][j]);
						}else{
							graph.put(square_lattice[i][j], neighbors);
						}
						//graph.put(square_lattice[i][j], neighbors);
					}
					
				}else continue;
			}
		}//End of for loop
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		
		//System.out.println("The graph size is = " + graph.size());
		//System.out.println("The No. of segregatd nodes  is = " + segregated.size());
		/*
		//For Printing the Graph to see each node's neighbors You can unComment the following code snippet
		Set set = graph.entrySet();
	      Iterator iterator = set.iterator();
	      while(iterator.hasNext()) {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         System.out.print(mentry.getKey() + " -----> ");
	         System.out.println(mentry.getValue());
	      }
	      */
	     /*
	      Set set = hmap.entrySet();
	      Iterator iterator = set.iterator();
	      while(iterator.hasNext()) {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
	         System.out.println(mentry.getValue());
	      } 
		*/
		System.out.println("Neighbor Identification is Done. It's as Follow : ");
	      for(int n = 0 ; n < NeighborHood.size();n++){
	    	  System.out.print(" "+NeighborHood.get(n).get(0).getId()+" -- > " );
	    	  for(int m = 1; m < NeighborHood.get(n).size();m++){
	    		  System.out.print(" "+NeighborHood.get(n).get(m).getId()+" , ");
	    	  }
	    	  System.out.println();
	      }
	      
	}//End of main method
}// End of the class

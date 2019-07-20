package com.selfsegregation;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	//=============================================================
	public static double randomGenerator(int interval){
		double random = (int)((Math.random() *   interval )) ; 
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
			row = (int)randomGenerator(L);
			column = (int)randomGenerator(L);
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
	
	//======================= Main Method ========================
	public static void main(String[] args) {
		
		//============= Parameter Initialization ==================
		int L = 5; 									// Lattice size
		int q = 5 ;									// no. of cultures
		double T; 									// Threshold
		double h = 0.05; 							// Empty Sites Density in our Lattice
		int N = (int)(( 1 - h ) * ( Math.pow(L,2)));// no. of agents
		int F = 10;									// Cultural Feature of each Agent
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
		
		}

}

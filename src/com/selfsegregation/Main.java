package com.selfsegregation;

import java.util.ArrayList;
import java.util.List;


public class Main {

	// =============================================================
	public static int randomGenerator(int min, int interval) {
		int random = (int) ((Math.random() * (interval - min)) + min);
		return random;
	}

	public static double doubleRandomGenerator(int min, int interval) {
		double random = ((Math.random() * (interval - min)) + min);
		return random;
	}

	/*
	 * public static double kronockerDelta(Node n1, Node n2){ double sum = 0;
	 * for(int i = 0 ; i < n1.getF().size() ; i++){ if( n1.getF().get(i) ==
	 * n2.getF().get(i) ){ sum++; }else continue; } return sum; }
	 */
	// ==============================================================

	public static List<Node> generateAgents(int N, int q) {
		List<Node> agents = new ArrayList<>();
		for (int n = 0; n < N; n++) {
			agents.add(new Node(n, q));
		}
		System.out.println("Agents Generated.");
		return agents;
	}

	// ===============================================================

	public static Node[][] populateLattice(List<Node> agents, int L) {
		System.out.println("Constructing Lattice ...");
		Node[][] square_lattice = new Node[L][L];
		System.out.println("Populating Square_Lattice by Agents ... ");
		System.out.println();
		int row;
		int column;
		while (!(agents.isEmpty())) {
			row = (int) randomGenerator(0, L);
			column = (int) randomGenerator(0, L);
			if (square_lattice[row][column] == null) {
				square_lattice[row][column] = agents.get(0);
				agents.remove(0);
			} else
				continue;
		}
		System.out.println("The Lattice Has Been Populated by Agents.");
		System.out.println();
		return square_lattice;
	}

	// ======================================================================

	public static void printLattic(Node[][] square_lattice) {
		System.out.println("After Populating the Lattice , It's As Follow : ");
		System.out.println();
		for (int s = 0; s < square_lattice.length; s++) {
			System.out.print("[ ");
			for (int c = 0; c < square_lattice.length; c++) {
				if (square_lattice[s][c] != null) {
					System.out.print(String.format("%03d",
							square_lattice[s][c].getId())
							+ " ");
				} else {
					System.out.print("EEE ");
				}
			}
			System.out.print(" ]");
			System.out.println();
		}
	}

	public static double cultural_overlap(Node n1, Node n2) {
		int sum = 0;
		for (int i = 0; i < n1.getF().size(); i++) {
			if ((n1.getF().get(i)) == (n2.getF().get(i))) {
				sum++;
			} 
		}
		System.out.println((double)(sum / (n1.getF().size()))+"");
		return ((double)(sum / n1.getF().size()));
	}

	public static void swap(Node n1, Node[][] square_lattice)
			throws NullPointerException { // second Arg. should be null to swap
											// a null with a node.
		boolean nulled = false;
		try {
			while (!nulled) {
				for (int i = 0; i < square_lattice.length; i++) {
					for (int j = 0; j < square_lattice.length; j++) {
						if (square_lattice[i][j] != null) {
							if (square_lattice[i][j].getId() == n1.getId()) {
								square_lattice[i][j] = null;
								nulled = true;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void replaceNode(Node n, Node[][] square_lattice, int q) {
		boolean replaced = false;
		int row;
		int column;
		while (!replaced) {
			row = (int) randomGenerator(0, square_lattice.length);
			column = (int) randomGenerator(0, square_lattice.length);
			if (square_lattice[row][column] == null) {
				swap(n, square_lattice);
				square_lattice[row][column] = new Node(n.getId(), q);
				square_lattice[row][column].setF(n.getF());
				replaced = true;
			} else
				continue;
		}
	}

	public static double average_overlap(List<Node> list) {
		double sum = 0;
		for (int s = 1; s < list.size(); s++) {
			sum += cultural_overlap(list.get(0), list.get(s));
		}
		return (sum / list.size());
	}

	public static void copy(Node node, Node randomNode) {
		boolean copied = false;
		while (!copied) {
			int random = (int) randomGenerator(0, node.getF().size());
			if (node.getF().get(random) != randomNode.getF().get(random)) {
				node.getF().set(random, randomNode.getF().get(random));
				copied = true;
			}
		}
	}
	public static double mobility_prob( double avg_overlap, double threshold ){
		int x;
		if((threshold - avg_overlap) > 0 ){
			x = 1;
		}else x = 0;
		return ((1 - avg_overlap) * x) ;
	}
	
	public static double mobility(List<Double> m){
		double sum = 0;
		for(int i = 0 ; i < m.size(); i++){
			sum += m.get(i);
		}
		return (sum / m.size()) ;
	}

	// ======================= Main Method ========================
	public static void main(String[] args) {

		// ============= Parameter Initialization ==================
		int L = 30; // Lattice size
		int q = 5; // no. of cultures traits
		double T = 0.7; // Threshold
		double h = 0.5; // Empty Sites Density in our Lattice
		int N = (int) ((1 - h) * (Math.pow(L, 2))); // no. of agents
		int M = 10; // No. of Iterations
		// =========================================================

		List<Node> agents = generateAgents(N, q);

		// Prints Cultural Traits of Each Node
		
		  for(int n = 0; n < agents.size(); n++){
		  System.out.print("Cultural Traits of Agent ( " + n +
		  " ) are : "+" [ "); for(int i = 0 ; i <
		  agents.get(n).getF().size();i++){ System.out.print(" " +
		  agents.get(n).getF().get(i)+ " "); } System.out.println(" ] ");
		  System.out.println(); }
		 

		Node[][] square_lattice = populateLattice(agents, L);
		printLattic(square_lattice);
		int counter;
		boolean first;
		List<List<Node>> NeighborHood = new ArrayList<>();
		List<Double> mobility_rate = new ArrayList<>();
		double mob =0  ;
		boolean first2 = true;
		List<Node> segregated = new ArrayList<>();
		//============================= MAIN ITERATION ==================================
		while(first2 || mob >= 0.3){
			first2 = false;
		//for (int iteration = 0; iteration < M; iteration++) {
			//printLattic(square_lattice);
			counter = 0;
			first = true;
			// List<List<Node>> NeighborHood = new ArrayList<>();
			System.out.println("Neighbor Identification in Process ... ");
			System.out.println();
			for (int i = 0; i < square_lattice.length; i++) {
				for (int j = 0; j < square_lattice.length; j++) {
					if (first) {
					} else {
						counter++;
					}
					if (square_lattice[i][j] != null) {
						if (j == 0 && i == 0) {
							List<Node> neighbors = new ArrayList<>();
							neighbors.add(square_lattice[i][j]);
							if (square_lattice[i + 1][j] != null) {
								neighbors.add(square_lattice[i + 1][j]);
							}
							if (square_lattice[i][j + 1] != null) {
								neighbors.add(square_lattice[i][j + 1]);
							}
							NeighborHood.add(neighbors);

						} else if (i == 0
								&& ((j + 1) <= square_lattice.length - 1)) {
							List<Node> neighbors = new ArrayList<>();
							neighbors.add(square_lattice[i][j]);
							if (square_lattice[i][j + 1] != null) {
								neighbors.add(square_lattice[i][j + 1]);
							}
							if (square_lattice[i][j - 1] != null) {
								neighbors.add(square_lattice[i][j - 1]);
							}
							if (square_lattice[i + 1][j] != null) {
								neighbors.add(square_lattice[i + 1][j]);
							}
							NeighborHood.add(neighbors);

						} else if (i == 0 && j == (square_lattice.length - 1)) {
							List<Node> neighbors = new ArrayList<>();
							neighbors.add(square_lattice[i][j]);
							if (square_lattice[i][j - 1] != null) {
								neighbors.add(square_lattice[i][j - 1]);
							}
							if (square_lattice[i + 1][j] != null) {
								neighbors.add(square_lattice[i + 1][j]);
							}
							NeighborHood.add(neighbors);

						} else if (j == 0
								&& ((i + 1) <= square_lattice.length - 1)) {
							List<Node> neighbors = new ArrayList<>();
							neighbors.add(square_lattice[i][j]);
							if (square_lattice[i - 1][j] != null) {
								neighbors.add(square_lattice[i - 1][j]);
							}
							if (square_lattice[i][j + 1] != null) {
								neighbors.add(square_lattice[i][j + 1]);
							}
							if (square_lattice[i + 1][j] != null) {
								neighbors.add(square_lattice[i + 1][j]);
							}
							NeighborHood.add(neighbors);

						} else if (j == (square_lattice.length - 1)
								&& ((i + 1) <= square_lattice.length - 1)) {
							List<Node> neighbors = new ArrayList<>();
							neighbors.add(square_lattice[i][j]);
							if (square_lattice[i - 1][j] != null) {
								neighbors.add(square_lattice[i - 1][j]);
							}
							if (square_lattice[i][j - 1] != null) {
								neighbors.add(square_lattice[i][j - 1]);
							}
							if (square_lattice[i + 1][j] != null) {
								neighbors.add(square_lattice[i + 1][j]);
							}
							NeighborHood.add(neighbors);

						} else if ((i == (square_lattice.length - 1))
								&& (j == 0)) {
							List<Node> neighbors = new ArrayList<>();
							neighbors.add(square_lattice[i][j]);
							if (square_lattice[i - 1][j] != null) {
								neighbors.add(square_lattice[i - 1][j]);
							}
							if (square_lattice[i][j + 1] != null) {
								neighbors.add(square_lattice[i][j + 1]);
							}
							NeighborHood.add(neighbors);

						} else if ((i == square_lattice.length - 1)
								&& ((j + 1) <= square_lattice.length - 1)) {
							List<Node> neighbors = new ArrayList<>();
							neighbors.add(square_lattice[i][j]);
							if (square_lattice[i][j - 1] != null) {
								neighbors.add(square_lattice[i][j - 1]);
							}
							if (square_lattice[i][j + 1] != null) {
								neighbors.add(square_lattice[i][j + 1]);
							}
							if (square_lattice[i - 1][j] != null) {
								neighbors.add(square_lattice[i - 1][j]);
							}
							NeighborHood.add(neighbors);

						} else if ((i == (square_lattice.length - 1))
								&& (j == (square_lattice.length - 1))) {
							List<Node> neighbors = new ArrayList<>();
							neighbors.add(square_lattice[i][j]);
							if (square_lattice[i][j - 1] != null) {
								neighbors.add(square_lattice[i][j - 1]);
							}
							if (square_lattice[i - 1][j] != null) {
								neighbors.add(square_lattice[i - 1][j]);
							}

							NeighborHood.add(neighbors);
						} else {
							List<Node> neighbors = new ArrayList<>();
							neighbors.add(square_lattice[i][j]);
							if (square_lattice[i][j + 1] != null) {
								neighbors.add(square_lattice[i][j + 1]);
							}
							if (square_lattice[i][j - 1] != null) {
								neighbors.add(square_lattice[i][j - 1]);
							}
							if (square_lattice[i + 1][j] != null) {
								neighbors.add(square_lattice[i + 1][j]);
							}
							if (square_lattice[i - 1][j] != null) {
								neighbors.add(square_lattice[i - 1][j]);
							}
							NeighborHood.add(neighbors);

						}

					} else
						continue;

				}
			}

						
			/*
			 * //For Printing the Graph to see each node's neighbors You can
			 * unComment the following code snippet Set set = graph.entrySet();
			 * Iterator iterator = set.iterator(); while(iterator.hasNext()) {
			 * Map.Entry mentry = (Map.Entry)iterator.next();
			 * System.out.print(mentry.getKey() + " -----> ");
			 * System.out.println(mentry.getValue()); }
			 */
			/*
			 * Set set = hmap.entrySet(); Iterator iterator = set.iterator();
			 * while(iterator.hasNext()) { Map.Entry mentry =
			 * (Map.Entry)iterator.next(); System.out.print("key is: "+
			 * mentry.getKey() + " & Value is: ");
			 * System.out.println(mentry.getValue()); }
			 */

			System.out
					.println("Neighbor Identification is Done. It's as Follow : "
							+ NeighborHood.size());
			/*for (int n = 0; n < NeighborHood.size(); n++) {
				System.out.print(" " + NeighborHood.get(n).get(0).getId()
						+ " -- > ");
				for (int m = 1; m < NeighborHood.get(n).size(); m++) {
					System.out.print(" " + NeighborHood.get(n).get(m).getId()
							+ " , ");
				}
				System.out.println();
			}*/
			// Calculation of cultural overlap and mobility probability
			int random;
			for (int i = 0; i < NeighborHood.size(); i++) {
				if (NeighborHood.get(i).size() > 1) {
					random = randomGenerator(1, NeighborHood.get(i)
							.size());
					Node randomNode = NeighborHood.get(i).get(random);
					double overlap = cultural_overlap(NeighborHood.get(i)
							.get(0), randomNode);
					//double r = doubleRandomGenerator(0, 1);
					if (overlap < 0.5) {
						copy(NeighborHood.get(i).get(0), randomNode);
					}
					double averageOverlap = average_overlap(NeighborHood.get(i));
					if (averageOverlap < T) {
						replaceNode(NeighborHood.get(i).get(0), square_lattice,
								q);
					}
					double mobility = mobility_prob(averageOverlap, T );
					mobility_rate.add(mobility);
					
				}else if(NeighborHood.get(i).size() == 1){
					segregated.add(NeighborHood.get(i).get(0));
				}
			}
			mob = mobility(mobility_rate);
			System.out.println("The mobility is : " + mob);
			NeighborHood.clear();
			mobility_rate.clear();
			for(int s = 0 ; s < segregated.size(); s++){
				replaceNode(segregated.get(s),square_lattice , q);
			}
			segregated.clear();

		}// End of Iteration
	}// End of main method

}// End of the class

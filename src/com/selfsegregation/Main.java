package com.selfsegregation;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static double doubleRandomGenerator(int min, int interval) {
		double random = (double) ((Math.random() * (interval - min)) + min);
		return (double) random;
	}

	// ==============================================================

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

	// ======================= Main Method ========================
	public static void main(String[] args) {

		// ============= Parameter Initialization ==================
		int L = 20; // Lattice size
		int q = 100; // no. of cultures traits
		double T = 0.3; // Threshold
		double h = 0.5; // Empty Sites Density in our Lattice
		int N = (int) ((1 - h) * (Math.pow(L, 2))); // no. of agents

		// =========================================================
		Model model = new ModelImpl(L, T, h, q);// Initiating the Model
		List<Node> agents = model.generateAgents(N, q);// Generating the Agents

		// ========================== Prints the Cultural traits of Each
		// Agent===============
		/*
		 * for (int n = 0; n < agents.size(); n++) {
		 * System.out.print("Cultural Traits of Agent ( " + n + " ) are : " +
		 * " [ "); for (int i = 0; i < agents.get(n).getF().size(); i++) {
		 * System.out.print(" " + agents.get(n).getF().get(i) + " "); }
		 * System.out.println(" ] "); System.out.println(); }
		 */
		// ======================================================================================

		Node[][] square_lattice = model.populateLattice(agents, L);
		printLattic(square_lattice);
		List<Double> mobility_rate = new ArrayList<>();
		double mob = 1;

		List<Node> segregated = new ArrayList<>();
		int Iteration = 0;
		// ============================= MAIN ITERATION
		// ==================================
		do {
			Iteration++;
			System.out.println("Iteration : " + Iteration);

			// System.out
			// .println("Neighbor Identification is Done. It's as Follow : "
			// + NeighborHood.size());

			// If you want to see neighbors of each node , you can un-comment
			// the following iteration
			/*
			 * for (int n = 0; n < NeighborHood.size(); n++) {
			 * System.out.print(" " + NeighborHood.get(n).get(0).getId() +
			 * " -- > "); for (int m = 1; m < NeighborHood.get(n).size(); m++) {
			 * System.out.print(" " + NeighborHood.get(n).get(m).getId() +
			 * " , "); } System.out.println(); }
			 */
			List<List<Node>> NeighborHood = model.findNeighbors(square_lattice);
			int random = 0;
			for (int i = 0; i < NeighborHood.size(); i++) {

				if (NeighborHood.get(i).size() > 1) {

					random = (int) model.randomGenerator(1, NeighborHood.get(i)
							.size());
					Node randomNeighbor = (Node) NeighborHood.get(i)
							.get(random);
					double overlap = (double) model.cultural_overlap(
							NeighborHood.get(i).get(0), randomNeighbor);

					if (overlap < 0.3) {
						model.copy(NeighborHood.get(i).get(0), randomNeighbor);

					} else {
						double r = (double) doubleRandomGenerator(0, 1);

						if (r > overlap) {

							model.copy(NeighborHood.get(i).get(0),
									randomNeighbor);
						}
					}
					double averageOverlap = (double) model
							.average_overlap(NeighborHood.get(i));

					if (averageOverlap < T) {
						model.replaceNode(NeighborHood.get(i).get(0),
								square_lattice, q);
					}
					double mobility = (double) model.mobility_prob(
							averageOverlap, T);
					mobility_rate.add(mobility);

				} else {
					segregated.add(NeighborHood.get(i).get(0));
				}
			}

			mob = model.mobility(mobility_rate);
			System.out.println("The mobility is : " + mob);
			NeighborHood.clear();
			mobility_rate.clear();

			System.out.println("the size of segregated node is : "
					+ segregated.size());
			segregated.clear();
			// printLattic(square_lattice);

		} while (mob >= 0.2);// End of Iteration

	}// End of main method

}// End of the Main class

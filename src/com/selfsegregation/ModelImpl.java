package com.selfsegregation;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
	private int L;
	private double T;
	private double h;
	private double q;

	public ModelImpl(int l, double t, double h, double q) {
		L = l;
		T = t;
		this.h = h;
		this.q = q;
	}

	public int getL() {
		return L;
	}

	public void setL(int l) {
		L = l;
	}

	public double getT() {
		return T;
	}

	public void setT(double t) {
		T = t;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getQ() {
		return q;
	}

	public void setQ(double q) {
		this.q = q;
	}
	/*
	 * (non-Javadoc)
	 * @see com.selfsegregation.Model#generateAgents(int, int)
	 * This method generates N agents present in the square lattice space
	 * the number of agents would be bases on N formula
	 */
	@Override
	public List<Node> generateAgents(int N, int q) {
		List<Node> agents = new ArrayList<>();
		for (int n = 0; n < N; n++) {
			agents.add(new Node(n, q));
		}
		System.out.println("Agents Generated.");
		return agents;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.selfsegregation.Model#populateLattice(java.util.List, int)
	 * This method place the agents through out the square lattice space according to 
	 * defined conditions (Randomization in selecting the site).
	 */

	@Override
	public Node[][] populateLattice(List<Node> agents, int L) {
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

	/*
	 * (non-Javadoc)
	 * @see com.selfsegregation.Model#cultural_overlap(com.selfsegregation.Node, com.selfsegregation.Node)
	 * This method measures the cultural similarity between two agents n1 and n2 by using Kronecker's
	 * delta function.
	 */
	@Override
	public double cultural_overlap(Node n1, Node n2) {
		double sum = 0;
		double size = n1.getF().size();
		for (int i = 0; i < size; i++) {
			if ((n1.getF().get(i)) == (n2.getF().get(i))) {
				sum++;
			}
		}

		double overlap = (double) (sum / size);
		// System.out.println("The Overlap value is : "+overlap);
		return overlap;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.selfsegregation.Model#swap(com.selfsegregation.Node, com.selfsegregation.Node[][])
	 * This method is defined to null the current position of the node which we want to replace it in lattice.
	 */
	@Override
	public void swap(Node n1, Node[][] square_lattice)
			throws NullPointerException {
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
	
	/*
	 * (non-Javadoc)
	 * @see com.selfsegregation.Model#replaceNode(com.selfsegregation.Node, com.selfsegregation.Node[][], int)
	 * This method find the a random new empty site to replace with the a node that has to be replaced.
	 */
	@Override
	public void replaceNode(Node n, Node[][] square_lattice, int q) {
		boolean replaced = false;
		int row;
		int column;
		while (!replaced) {
			row = (int) (randomGenerator(0, square_lattice.length));
			column = (int) (randomGenerator(0, square_lattice.length));
			if (square_lattice[row][column] == null) {
				swap(n, square_lattice);
				square_lattice[row][column] = new Node(n.getId(), q);
				square_lattice[row][column].setF(n.getF());
				replaced = true;
			} else
				continue;
		}

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.selfsegregation.Model#average_overlap(java.util.List)
	 * This method measure the average cultural similarity between a node and it's neighbors
	 */
	@Override
	public double average_overlap(List<Node> list) {
		double sum = 0;
		for (int s = 1; s < list.size(); s++) {
			sum += cultural_overlap(list.get(0), list.get(s));
		}
		return (double) (sum / list.size());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.selfsegregation.Model#copy(com.selfsegregation.Node, com.selfsegregation.Node)
	 * This method is defined to copy one one of the cultural features of a random neighbor if 
	 * the condition is met.
	 */
	@Override
	public void copy(Node node, Node randomNode) {
		boolean copied = false;
		while (!copied) {
			int random = (int) randomGenerator(0, node.getF().size());
			if (node.getF().get(random) != randomNode.getF().get(random)) {
				node.getF().set(random, randomNode.getF().get(random));
				copied = true;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.selfsegregation.Model#mobility_prob(double, double)
	 * This method calculate the mobility probability of the node based on average cultural overlap and 
	 * using Heaviside step function.
	 */
	@Override
	public double mobility_prob(double avg_overlap, double threshold) {
		double x = 0;
		if ((threshold - avg_overlap) > 0) {
			x = 1;
		} else
			x = 0;
		return (double) ((1 - avg_overlap) * x);
	}

	/*
	 * (non-Javadoc)
	 * @see com.selfsegregation.Model#mobility(java.util.List)
	 * This method calculate the mobility probability of whole system using the mobility 
	 * of each node calculated by another method (i.e. moblity_prob) using Heaviside step function
	 */
	@Override
	public double mobility(List<Double> m) {
		double sum = 0;
		for (int i = 0; i < m.size(); i++) {
			sum += m.get(i);
		}
		return (double) (sum / m.size());
	}

	/*
	 * (non-Javadoc)
	 * @see com.selfsegregation.Model#randomGenerator(int, int)
	 * This method is to generate a random integer value between an
	 *  interval(e.g. selecting random neighbor)
	 */
	@Override
	public int randomGenerator(int min, int interval) {
		int random = (int) ((Math.random() * (interval - min)) + min);
		return random;
	}

	/*
	 * (non-Javadoc)
	 * @see com.selfsegregation.Model#findNeighbors(com.selfsegregation.Node[][])
	 * This method is to find the neighbors of each agent in Von Neumann neighborhood
	 * by looping over each site in square lattice by considering the place of the agent
	 * in the lattice space by providing it's position.
	 * It returns a list of list of nodes such that the first element of each list, the 
	 * agent itself is present and neighbors in next indexes of the list.
	 */
	@Override
	public List<List<Node>> findNeighbors(Node[][] square_lattice) {
		System.out.println("Neighbor Identification in Process ... ");
		System.out.println();
		int counter = 0;
		List<List<Node>> NeighborHood = new ArrayList<>();
		for (int i = 0; i < square_lattice.length; i++) {

			for (int j = 0; j < square_lattice.length; j++) {

				counter++;
				if (square_lattice[i][j] != null) {
					if (i == 0 && j == 0) {
						List<Node> neighbors = new ArrayList<>();
						neighbors.add(square_lattice[i][j]);
						if (square_lattice[i + 1][j] != null) {
							neighbors.add(square_lattice[i + 1][j]);
						}
						if (square_lattice[i][j + 1] != null) {
							neighbors.add(square_lattice[i][j + 1]);
						}
						NeighborHood.add(neighbors);

					} else if (i == 0 && (j < square_lattice.length - 1)) {// changed
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

					} else if (j == 0 && (i < square_lattice.length - 1)) {
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
							&& (i < square_lattice.length - 1)) {
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

					} else if ((i == (square_lattice.length - 1)) && (j == 0)) {
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
							&& (j < square_lattice.length - 1)) {
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
		return NeighborHood;
	}

}

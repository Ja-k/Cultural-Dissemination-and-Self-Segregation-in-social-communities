package com.selfsegregation;

import java.util.List;

public interface Model {
	public int randomGenerator(int min, int interval);
	public List<Node> generateAgents(int N, int q);
	public Node[][] populateLattice(List<Node> agents, int L);
	public double cultural_overlap(Node n1, Node n2);
	public void swap(Node n1, Node[][] square_lattice);
	public void replaceNode(Node n, Node[][] square_lattice, int q);
	public double average_overlap(List<Node> list);
	public void copy(Node node, Node randomNode);
	public double mobility_prob(double avg_overlap, double threshold);
	public double mobility(List<Double> m);
	public List<List<Node>> findNeighbors(Node[][] square_lattice);
	
}

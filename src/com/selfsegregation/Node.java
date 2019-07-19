package com.selfsegregation;

import java.util.List;


public class Node implements Comparable<Node> {
	private int id;
	

	private List<Integer> F;
	
	// no Constructor yet
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<Integer> getF() {
		return F;
	}

	public void setF(List<Integer> cultural_trait) {
		this.F = cultural_trait;
	}

	@Override
	public int compareTo(Node node) {
		// TODO Auto-generated method stub
		return 0;
	}
}

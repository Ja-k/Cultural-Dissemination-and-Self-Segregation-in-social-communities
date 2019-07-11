package com.selfsegregation;

import java.util.List;


public class Node {
	
	private List<Integer> cultural_trait;
	
	Node(List<Integer> cultural_trait){
		this.cultural_trait = cultural_trait;
	}
	
	public List<Integer> getCultural_trait() {
		return cultural_trait;
	}

	public void setCultural_trait(List<Integer> cultural_trait) {
		this.cultural_trait = cultural_trait;
	}
}

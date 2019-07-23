package com.selfsegregation;

import java.util.ArrayList;
import java.util.List;


public class Node{
	private int id;
	

	private List<Integer> F;
	
	public Node(int id , int q){
		this.id = id;
		this.F = new ArrayList<Integer>();
		for(int i = 0 ; i < 10 ; i++){
			F.add((int)(Math.random() * q) );
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setF(List<Integer> f) {
		F = f;
	}

	public List<Integer> getF() {
		return F;
	}
/*
	public void setF(List<Integer> cultural_trait) {
		this.F = cultural_trait;
	}
*/	

	

	@Override
	public String toString() {
		return "Node [id=" + id + "]";
	}

}

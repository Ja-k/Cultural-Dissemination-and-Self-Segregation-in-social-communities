package com.selfsegregation;

public class Main {
	
	public static double kronockerDelta(Node n1, Node n2){
		double sum = 0;
		for(int i = 0 ; i < n1.getCultural_trait().size() ; i++){
			if( n1.getCultural_trait().get(i) == n2.getCultural_trait().get(i) ){
				sum++;
			}else continue;
		}
		return sum;
	}
	// at the end it should be divided by length of cultural traits
	public static void main(String[] args) {
		//int random = (int )(Math.random() * 50);
		//System.out.println(random);
	}

}

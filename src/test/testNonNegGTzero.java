package test;

import Trivial.qual.*;

public class testNonNegGTzero {

	public static void main(String[] args) {
		test(2);
	}
	
	public static void test(@Unknown int x){
		int[] a = new int[6];
		if(x >= 0){
			int c = a[x];
		} else {
			int r = a[x]; // error
		}
		if(x > -1){
			int c = a[x];
		} else {
			int r = a[x]; // error
		}
		
	}

}

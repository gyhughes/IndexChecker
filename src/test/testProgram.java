package test;

import annotations.IndexFor;
import annotations.IndexHigh;
import annotations.IndexTop;

public class testProgram {
	public static void main(String[] args){
		int[] a = new int[47];
		@IndexTop int sum = 0;
		@IndexHigh("a") int l = a.length;
		for(@IndexFor("a") int i = 0; i < l; i++){
			sum += a[i];
		}
		System.out.println(sum);
	}
}

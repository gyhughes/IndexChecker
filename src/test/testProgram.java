package test;

import index.qual.*;

public class testProgram {
	public static void main(String[] args) {
		int[] a = new int[47];
		@IndexTop int sum = 0;
		@IndexOrHigh("a") int l = a.length;
		for (@IndexFor("a") int i = 0; i < l; i++) {
			sum += a[i];
		}
		System.out.println(sum);
	}
}

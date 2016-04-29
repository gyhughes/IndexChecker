package test;

import index.qual.IndexFor;
import index.qual.Unknown;

public class testIndex {

	public testIndex() {
		foo(-50, 10, 0);
	}

	void foo(@Unknown int a, @IndexFor("arr") int b, @IndexFor("a") int f) {
		int[] arr = new int[20];
		int c = arr[a];  //  this line should warn for Unknown access
		c = arr[b];
		c = arr[f]; // should warn for bad name
	}

}

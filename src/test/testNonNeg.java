package test;
import Trivial.qual.NonNegative;
import Trivial.qual.Unknown;
public class testNonNeg {

	public testNonNeg() {
		foo(-50, 10);
	}

	void foo(@Unknown int a, @NonNegative int b){
		@NonNegative int[] arr = new int[20];
		int c = arr[a];  //  this line should warn for Unknown access
		b = arr[b];
	}

}

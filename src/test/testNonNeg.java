package test;
import Trivial.qual.NonNegative;
import Trivial.qual.Unknown;
public class testNonNeg {

	public testNonNeg() {
		foo(-50, 10);
	}

	void foo(@Unknown int a, @NonNegative int b){
		@NonNegative int[] arr = new int[20];
		b = arr[a];
	}

}

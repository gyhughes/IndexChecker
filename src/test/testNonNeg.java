package test;
import Trivial.qual.NonNegative;
import Trivial.qual.Unknown;
public class testNonNeg {

	public testNonNeg() {
		foo(4,10);
	}
	@SuppressWarnings("unused")
	void foo(@Unknown int a, @NonNegative int b){
		@NonNegative int d = a;
	}

}

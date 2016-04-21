package test;

import Trivial.*;
import Trivial.quals.*;

@SuppressWarnings("unused")
public class testTrivial {
	public static void main(String[] args){
		int b = 0;
		@Unknown int k = -10;
		@NonNegative int m = k;

		
		NN2NN(b);
		NN2U(b);
		U2U(b);
		U2NN(b); // ERROR
		
		System.out.println("testNonNegative: Success");
	}
	
	public static void U2NN(@Unknown int x) {
		@NonNegative int y = x;   // ERROR!
	}
	
	public static void NN2U(@NonNegative int x) {
		@Unknown int y = x;   // OK
	}
	public static void U2U(@Unknown int x) {
		@Unknown int y = x;   // OK
	}
	
	public static void NN2NN(@NonNegative int x) {
		@NonNegative int y = x;   // OK
	}
}
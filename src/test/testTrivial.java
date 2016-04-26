package test;

import Trivial.qual.NonNegative;
import Trivial.qual.Unknown;

public class testTrivial {
	public static void main(String[] args) {
		
		@NonNegative int a = 1; 
		@Unknown int b = -50;
		
		// Tests for Qualifier Hierarchy.
		NN2NN(a);
		NN2U(a);
		U2U(a);
		U2NN(b);
		
		// Tests for Nullness checker, make sure checker framework plugin works(
		nulla(null);
		
		System.out.println("testNonNegative: Success");
	}

	// @NonNegative variable assigned a @NonNegative value. Legal.
	public static void NN2NN(@NonNegative int x) {
		@SuppressWarnings("unused")
		@NonNegative int y = x;
	}
	
	// @Unknown variable assigned a @NonNegative value. Legal.
	public static void NN2U(@NonNegative int x) {
		@SuppressWarnings("unused")
		@Unknown int y = x;
	}
	
	// @Unknown variable assigned an @Unknown value. Legal.
	public static void U2U(@Unknown int x) {
		@SuppressWarnings("unused")
		@Unknown int y = x;
	}
	
	// @NonNegative variable assigned an @Unknown value. Illegal.
	public static void U2NN(@Unknown int x) {
		@SuppressWarnings("unused")
		@NonNegative int y = x; // warning here for improper assignment
	}
	
	public static void nulla(Object m) { 
		m.getClass(); 
	}
}

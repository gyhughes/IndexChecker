package test;
import Trivial.qual.*;
public class testNonNegTransfer {

	public static void main(String[] args) {
		int nn = 5;
		int uk = -10;
		int x = nn + uk; // should be unknown
		int y = nn + nn; // should be nn
		int z = uk + uk; // should be unknown
		transfer(x,y,z, 16);
	}
	
	public static void transfer( @Unknown int x, @NonNegative int y, @Unknown int z, @NonNegative int minus){
		int next = y - minus;
		int nn = y + minus;
		int[] a = new int[24];
		int c = a[x]; // error
		c = a[y]; // fine
		c = a[z]; // error
		c = a[next]; // error
		c = a[nn]; // fine
	}

}

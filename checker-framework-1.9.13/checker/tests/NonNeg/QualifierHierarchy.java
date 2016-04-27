import Trivial.qual.*;
@Trivial.qual.DefaultQualifier(Unknown.class)
public class ArrayArgs {

    public void test(@NonNegative int i) {
    }

    public void test() {
        test(NonNull.class);

        @NonNegative int n1 = 1;
        test(n1);
        @Unknown int n2 = -1;
        //:: warning: (argument.type.incompatible)
        test(n2);
        
        @Unknown int n3 = 2;
        //:: warning: (assignment.type.incompatible)
        @NonNegative int n4 = n3;

    }
}
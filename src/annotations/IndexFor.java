package annotations;

import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf({NonNegative.class, IndexHigh.class, IndexLow.class, LTLength.class})
public @interface IndexFor {

	String value();

}

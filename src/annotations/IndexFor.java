package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf({NonNegative.class, IndexHigh.class, IndexLow.class, LTLength.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})

public @interface IndexFor {

	String value();

}

package index.qual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf({NonNegative.class, IndexOrHigh.class, IndexOrLow.class, LTLength.class})
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
public @interface IndexFor {

	String value();

}

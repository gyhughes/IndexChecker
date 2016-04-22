package Trivial.qual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf({Unknown.class})
@Target({ElementType.TYPE_USE, ElementType.PARAMETER})
public @interface NonNegative {

}

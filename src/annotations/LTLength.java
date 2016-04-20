package annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf(IndexTop.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})

public @interface LTLength {

}

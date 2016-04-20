package annotations;

import java.lang.annotation.*;

import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf({})
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface IndexTop {

}

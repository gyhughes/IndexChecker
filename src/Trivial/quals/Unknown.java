package Trivial.quals;

import java.lang.annotation.*;

import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf( { } )
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface Unknown {

}

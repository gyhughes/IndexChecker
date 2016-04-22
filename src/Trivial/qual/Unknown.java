package Trivial.qual;

import java.lang.annotation.*;

import org.checkerframework.framework.qual.SubtypeOf;
import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;

@SubtypeOf( { } )
@Target({ElementType.TYPE_USE, ElementType.PARAMETER})
@DefaultQualifierInHierarchy
public @interface Unknown {

}

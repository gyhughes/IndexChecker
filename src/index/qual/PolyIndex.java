package index.qual;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import org.checkerframework.framework.qual.PolymorphicQualifier;

@PolymorphicQualifier
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
public @interface PolyIndex { }
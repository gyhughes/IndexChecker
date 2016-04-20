package index.qual;

import java.lang.annotation.*;

import javax.lang.model.type.TypeKind;

import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.ImplicitFor;
import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf({})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@DefaultQualifierInHierarchy
@ImplicitFor(types = TypeKind.INT)
public @interface IndexTop {

}

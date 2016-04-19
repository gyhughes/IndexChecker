
import java.lang.annotation.*;

import org.checkerframework.framework.*;

@SubtypeOf(Unqualified.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface IndexTop {

}

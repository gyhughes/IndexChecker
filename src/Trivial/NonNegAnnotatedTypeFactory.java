package Trivial;
import javax.lang.model.element.AnnotationMirror;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.type.AnnotatedTypeFactory;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.util.AnnotationBuilder;
import com.sun.source.tree.Tree;

import index.qual.NonNegative;

public class NonNegAnnotatedTypeFactory extends AnnotatedTypeFactory{

	public NonNegAnnotatedTypeFactory(BaseTypeChecker checker, boolean useFlow) {
		super(checker);
	}

	//returns a new @NonNegative annotation
	AnnotationMirror createNonNegAnnotation() {
		AnnotationBuilder builder =
				new AnnotationBuilder(processingEnv, NonNegative.class);
		return builder.build();
	}
	
	@Override
	public void annotateImplicit(Tree tree, AnnotatedTypeMirror type) {
		
	}
		
}


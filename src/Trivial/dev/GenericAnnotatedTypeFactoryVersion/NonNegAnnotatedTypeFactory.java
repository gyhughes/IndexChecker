package Trivial.dev.GenericAnnotatedTypeFactoryVersion;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.flow.CFAnalysis;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFTransfer;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.framework.type.GenericAnnotatedTypeFactory;
import org.checkerframework.framework.util.AnnotationBuilder;

import Trivial.qual.NonNegative;
import Trivial.qual.Unknown;

public class NonNegAnnotatedTypeFactory extends GenericAnnotatedTypeFactory{


	public NonNegAnnotatedTypeFactory(BaseTypeChecker checker) {
		super(checker);
	}

	//returns a new @NonNegative annotation
	AnnotationMirror createNonNegAnnotation() {
		AnnotationBuilder builder =
				new AnnotationBuilder(processingEnv, NonNegative.class);
		return builder.build();
	}
//    @Override
//    protected Set<Class<? extends Annotation>> createSupportedTypeQualifiers() {
//        return getBundledTypeQualifiersWithPolyAll();
//    }
	
//	@Override 
//	protected Set<Class<? extends Annotation>> createSupportedTypeQualifiers() {
//	      return Collections.unmodifiableSet(new HashSet<Class<? extends Annotation>>(Arrays.asList(Unknown.class,NonNegative.class)));
//	  }
	
//	@Override
//	public void annotateImplicit(Tree tree, AnnotatedTypeMirror type) {
//		if(!type.isAnnotatedInHierarchy(AnnotationUtils.fromClass(elements,NonNegative.class))) {
//			if(tree.getKind().equals(Tree.Kind.INT_LITERAL)) {
//				if(((int)((LiteralTree) tree).getValue()) > -1) {
//					type.addAnnotation(createNonNegAnnotation());
//				}
//			}
//		}
//	}
		
}


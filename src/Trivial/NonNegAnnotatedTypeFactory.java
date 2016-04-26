package Trivial;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;

import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.type.AnnotatedTypeFactory;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.type.treeannotator.ImplicitsTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.ListTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.PropagationTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.TreeAnnotator;
import org.checkerframework.framework.util.AnnotationBuilder;
import org.checkerframework.javacutil.AnnotationUtils;

import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;

import Trivial.qual.NonNegative;
import Trivial.qual.Unknown;
import index.qual.IndexFor;

// The current functionality of this class is to create @NonNegative instances if the annotation is manually used by the programmer, and to assign @NonNegative to strictly positive Literals (i.e. constants in expressions)

public class NonNegAnnotatedTypeFactory extends BaseAnnotatedTypeFactory{
	
	//TODO: Comment on why/for what purpose postInit() is necessary
	public NonNegAnnotatedTypeFactory(BaseTypeChecker checker) {
		super(checker);
		this.postInit();
	}
	
	//returns a new @NonNegative annotation
	AnnotationMirror createNonNegAnnotation() {
		AnnotationBuilder builder = new AnnotationBuilder(processingEnv, NonNegative.class);
		return builder.build();
	}
	
    @Override
    public TreeAnnotator createTreeAnnotator() {
        return new ListTreeAnnotator(
                new ImplicitsTreeAnnotator(this),
                new NonNegTreeAnnotator(this),
                new PropagationTreeAnnotator(this)
        );
    }
	
	private class NonNegTreeAnnotator extends TreeAnnotator {

		public NonNegTreeAnnotator(AnnotatedTypeFactory atypeFactory) {
			super(atypeFactory);
		}

		// if literal is at least zero assign it @NonNegative
		// TODO: Is this using IndexFor.class correctly? Or should it be NonNegative.class? 
		@Override
		public Void visitLiteral(LiteralTree tree, AnnotatedTypeMirror type) {
			if (!type.isAnnotatedInHierarchy(AnnotationUtils.fromClass(elements, IndexFor.class))) {
				if (tree.getKind() == Tree.Kind.INT_LITERAL) {
					if ((int)tree.getValue() > -1) {
						type.addAnnotation(createNonNegAnnotation());
					}
				}
			}
			return super.visitLiteral(tree, type);
		}

	}
}


package Trivial;

import org.checkerframework.framework.type.treeannotator.ListTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.PropagationTreeAnnotator;

import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.type.AnnotatedTypeFactory;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.type.treeannotator.TreeAnnotator;
import org.checkerframework.framework.util.AnnotationBuilder;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.TreeUtils;

import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

import index.qual.IndexFor;
import index.qual.NonNegative;
import org.checkerframework.framework.type.treeannotator.ImplicitsTreeAnnotator;

public class NonNegAnnotatedTypeFactory extends BaseAnnotatedTypeFactory{

	public NonNegAnnotatedTypeFactory(BaseTypeChecker checker, boolean useFlow) {
		super(checker, useFlow);
	}

	@Override
	public TreeAnnotator createTreeAnnotator() {
		return new ListTreeAnnotator(
				new ImplicitsTreeAnnotator(this),
				new NonNegTreeAnnotator(this),
				new PropagationTreeAnnotator(this)
				);
	}

	//returns a new @NonNegative annotation
	AnnotationMirror createNonNegAnnotation() {
		AnnotationBuilder builder =
				new AnnotationBuilder(processingEnv, NonNegative.class);
		return builder.build();
	}

	@Override
	public void annotateImplicit(Element element, AnnotatedTypeMirror type) {

	}
	private class NonNegTreeAnnotator extends TreeAnnotator {

		public NonNegTreeAnnotator(AnnotatedTypeFactory atypeFactory) {
			super(atypeFactory);
		}
		
		// if literal is at least zero assign it @NonNegative
		@Override
		public Void visitLiteral(LiteralTree tree, AnnotatedTypeMirror type){
			if(!type.isAnnotatedInHierarchy(AnnotationUtils.fromClass(elements, IndexFor.class))){
				if(tree.getKind() == Tree.Kind.INT_LITERAL) {
					if((int)tree.getValue() > -1){
						type.addAnnotation(createNonNegAnnotation());
					}
				}
			}
			return super.visitLiteral(tree, type);
		}

		
	}
}

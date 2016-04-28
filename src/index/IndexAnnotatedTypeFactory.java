package index;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

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

import index.qual.*;

public class IndexAnnotatedTypeFactory extends BaseAnnotatedTypeFactory{

	public IndexAnnotatedTypeFactory(BaseTypeChecker checker, boolean useFlow) {
		super(checker, useFlow);
	}
	
	@Override
	public TreeAnnotator createTreeAnnotator() {
		return new ListTreeAnnotator(
				new ImplicitsTreeAnnotator(this),
				new IndexTreeAnnotator(this),
				new PropagationTreeAnnotator(this)
				);
	}
	
	//returns a new @NonNegative annotation
	AnnotationMirror createNonNegAnnotation() {
		AnnotationBuilder builder = new AnnotationBuilder(processingEnv, NonNegative.class);
		return builder.build();
	}
	// returns new @IndexLow
	AnnotationMirror createIndexLowAnnotation() {
		AnnotationBuilder builder = new AnnotationBuilder(processingEnv, IndexOrLow.class);
		return builder.build();
	}

	@Override
	public void annotateImplicit(Element element, AnnotatedTypeMirror type) {

	}
	
	private class IndexTreeAnnotator extends TreeAnnotator {

		public IndexTreeAnnotator(AnnotatedTypeFactory atypeFactory) {
			super(atypeFactory);
		}
		
		// if literal is at least zero assign it @NonNegative
		@Override
		public Void visitLiteral(LiteralTree tree, AnnotatedTypeMirror type){
			if (!type.isAnnotatedInHierarchy(AnnotationUtils.fromClass(elements, IndexFor.class))) {
				if (tree.getKind() == Tree.Kind.INT_LITERAL) {
					if ((int)tree.getValue() > -1) {
						type.addAnnotation(createNonNegAnnotation());
					}
					if ((int)tree.getValue() == -1) {
						type.addAnnotation(createIndexLowAnnotation());
					}
				}
			}
			return super.visitLiteral(tree, type);
		}
		// adding top to @NonNegative make it top
		@Override
		public Void visitBinary(BinaryTree tree, AnnotatedTypeMirror type) {
			return super.visitBinary(tree, type);
		}
	}
}

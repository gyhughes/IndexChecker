package Trivial;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.framework.type.AnnotatedTypeFactory;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.type.GenericAnnotatedTypeFactory;
import org.checkerframework.framework.type.treeannotator.ImplicitsTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.ListTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.PropagationTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.TreeAnnotator;
import org.checkerframework.framework.util.AnnotationBuilder;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.Pair;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;

import Trivial.qual.NonNegative;
import Trivial.qual.Unknown;

// The current functionality of this class is to create @NonNegative instances if the annotation is manually used by the programmer, and to assign @NonNegative to strictly positive Literals (i.e. constants in expressions)

public class NonNegAnnotatedTypeFactory extends GenericAnnotatedTypeFactory<CFValue, CFStore, NonNegTransfer, NonNegAnalysis>{
	
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
	//returns a new @Unknown annotation
	AnnotationMirror createUnknownAnnotation(){
		AnnotationBuilder builder = new AnnotationBuilder(processingEnv, Unknown.class);
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
	
    @Override
    protected NonNegAnalysis createFlowAnalysis(List<Pair<VariableElement, CFValue>> fieldvalues){
    	return new NonNegAnalysis(checker, this, fieldvalues);
    }
    
	private class NonNegTreeAnnotator extends TreeAnnotator {

		public NonNegTreeAnnotator(AnnotatedTypeFactory atypeFactory) {
			super(atypeFactory);
		}

		// if literal is at least zero assign it @NonNegative
		@Override
		public Void visitLiteral(LiteralTree tree, AnnotatedTypeMirror type) {
			if (!type.isAnnotatedInHierarchy(AnnotationUtils.fromClass(elements, NonNegative.class))) {
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


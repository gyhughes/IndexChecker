package index;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.framework.type.AnnotatedTypeFactory;
import org.checkerframework.framework.type.GenericAnnotatedTypeFactory;
import org.checkerframework.framework.type.QualifierHierarchy;
import org.checkerframework.framework.type.treeannotator.ImplicitsTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.ListTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.PropagationTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.TreeAnnotator;
import org.checkerframework.framework.util.GraphQualifierHierarchy;
import org.checkerframework.framework.util.MultiGraphQualifierHierarchy.MultiGraphFactory;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.Pair;
import index.qual.*;

public class IndexAnnotatedTypeFactory
extends GenericAnnotatedTypeFactory<CFValue, CFStore, IndexTransfer, IndexAnalysis> {

	protected final AnnotationMirror IndexFor;
	protected final AnnotationMirror IndexBottom;


	public IndexAnnotatedTypeFactory(BaseTypeChecker checker) {
		super(checker);
		IndexFor = AnnotationUtils.fromClass(elements, IndexFor.class);
		IndexBottom = AnnotationUtils.fromClass(elements, IndexBottom.class);
		this.postInit();
	}

	@Override
	public TreeAnnotator createTreeAnnotator() {
		return new ListTreeAnnotator(
				new ImplicitsTreeAnnotator(this),
				new IndexTreeAnnotator(this),
				new PropagationTreeAnnotator(this)
				);
	}

	@Override
	protected IndexAnalysis createFlowAnalysis(List<Pair<VariableElement, CFValue>> fieldvalues){
		return new IndexAnalysis(checker, this, fieldvalues);
	}

	private class IndexTreeAnnotator extends TreeAnnotator {
		public IndexTreeAnnotator(AnnotatedTypeFactory atypeFactory) {
			super(atypeFactory);
		}
	}
    @Override
    protected Set<Class<? extends Annotation>> createSupportedTypeQualifiers() {
        return getBundledTypeQualifiersWithPolyAll(IndexFor.class);
    }

	@Override
	public QualifierHierarchy createQualifierHierarchy(MultiGraphFactory factory) {
		return new IndexQualifierHierarchy(factory, IndexBottom);
	}
	private final class IndexQualifierHierarchy extends GraphQualifierHierarchy {

		public IndexQualifierHierarchy(MultiGraphFactory f,
				AnnotationMirror bottom) {
			super(f, bottom);
		}

		@Override
		public boolean isSubtype(AnnotationMirror rhs, AnnotationMirror lhs) {
			// Ignore annotation values to ensure that annotation is in supertype map.
			if (AnnotationUtils.areSameIgnoringValues(lhs, IndexFor)) {
				lhs = IndexFor;
			}
			if (AnnotationUtils.areSameIgnoringValues(rhs, IndexFor)) {
				rhs = IndexFor;
			}
			return super.isSubtype(rhs, lhs);
		}
	}

}

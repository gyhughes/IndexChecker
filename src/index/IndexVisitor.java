package index;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.framework.source.Result;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.TreeUtils;

import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.ExpressionTree;

import index.qual.IndexFor;




public class IndexVisitor extends BaseTypeVisitor<IndexAnnotatedTypeFactory> {
	
	protected final ExecutableElement IndexValueElement;

	public IndexVisitor(BaseTypeChecker checker) {
		super(checker);
		ProcessingEnvironment env = checker.getProcessingEnvironment();
		IndexValueElement = TreeUtils.getMethod("index.qual.IndexFor", "value", 0, env);
	}
	
	@Override
	public Void visitArrayAccess(ArrayAccessTree tree, Void type) {
		ExpressionTree index = tree.getIndex();
		String name = tree.getExpression().toString();
		AnnotatedTypeMirror indexType = atypeFactory.getAnnotatedType(index);
		// warn if not Index for
		if (!indexType.hasAnnotation(IndexFor.class)) {
			checker.report(Result.warning("Potentially unsafe array access: only use @IndexFor as index"), index);
		}
		// warn if it is IndexFor nut not the right array
		else if(!(getIndexValue(indexType.getAnnotation(IndexFor.class), IndexValueElement).equals(name))){
			checker.report(Result.warning("Potentially unsafe array access: only use IndexFor("+ name +") index"), index);
		}
		return super.visitArrayAccess(tree, type);
	}
	
	// returns the value of an IndexFor annotation, given the annotation and Value method
	protected static String getIndexValue(AnnotationMirror indexType, ExecutableElement IndexValueElement) {
		return (String) AnnotationUtils.getElementValuesWithDefaults(indexType).get(IndexValueElement).getValue();
	}

}

package Trivial;

import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.framework.source.Result;
import org.checkerframework.framework.type.AnnotatedTypeMirror;

import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.ExpressionTree;



public class NonNegVisitor extends BaseTypeVisitor<BaseAnnotatedTypeFactory> {
	public NonNegVisitor(BaseTypeChecker checker) {
		super(checker);
	}

	@Override
	public Void visitArrayAccess(ArrayAccessTree tree, Void type) {
		ExpressionTree index = tree.getIndex();
		AnnotatedTypeMirror indexType = atypeFactory.getAnnotatedType(index);
		if (!indexType.hasAnnotation(Trivial.qual.NonNegative.class)) {
			checker.report(Result.warning("Potentially unsafe array access: only use NonNegative index"), index);
		}
		return super.visitArrayAccess(tree, type);
	}

}

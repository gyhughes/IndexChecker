package Trivial.dev;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;



public class NonNegVisitor extends BaseTypeVisitor<NonNegAnnotatedTypeFactory>{

	public NonNegVisitor(BaseTypeChecker checker) {
		super(checker);
	}

//	@Override
//	public Void visitArrayAccess(ArrayAccessTree tree, Void type){
//		AnnotatedTypeMirror indexType = atypeFactory.getAnnotatedType(tree.getIndex());
//		if (!indexType.hasAnnotation(Trivial.qual.NonNegative.class)) {
//			checker.report(Result.failure("array.access.unsafe", indexType), indexType);
//		}
//		return super.visitArrayAccess(tree, type);
//	}

}

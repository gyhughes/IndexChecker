package Trivial;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.framework.source.Result;
import org.checkerframework.framework.type.AnnotatedTypeFactory;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.type.GenericAnnotatedTypeFactory;

import com.sun.source.tree.ArrayAccessTree;



public class NonNegVisitor extends BaseTypeVisitor{

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

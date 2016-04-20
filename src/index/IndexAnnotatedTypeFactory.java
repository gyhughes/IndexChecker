package index;

import javax.lang.model.element.Element;

import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.type.AnnotatedTypeMirror;

public class IndexAnnotatedTypeFactory extends BaseAnnotatedTypeFactory{

	public IndexAnnotatedTypeFactory(BaseTypeChecker checker, boolean useFlow) {
		super(checker, useFlow);
	}
	
	@Override
	public void annotateImplicit(Element element, AnnotatedTypeMirror type) {
	
	}

}

package Trivial;

import javax.lang.model.element.AnnotationMirror;

import org.checkerframework.dataflow.analysis.RegularTransferResult;
import org.checkerframework.dataflow.analysis.TransferInput;
import org.checkerframework.dataflow.analysis.TransferResult;
import org.checkerframework.dataflow.cfg.node.NumericalSubtractionNode;
import org.checkerframework.framework.flow.CFAbstractTransfer;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;

public class NonNegTransfer extends CFAbstractTransfer<CFValue, CFStore, NonNegTransfer> {

	/** like super.analysis, but more specific */
	protected NonNegAnalysis analysis;

	public NonNegTransfer(NonNegAnalysis analysis) {
		super(analysis);
		this.analysis = analysis;
	}
	
	// make any subtraction result in an @unknown value.
	@Override
	public TransferResult<CFValue, CFStore> visitNumericalSubtraction(NumericalSubtractionNode n, TransferInput<CFValue, CFStore> in){
		NonNegAnnotatedTypeFactory atypeFactory = (NonNegAnnotatedTypeFactory) analysis.getTypeFactory();
		TransferResult<CFValue, CFStore> result = super.visitNumericalSubtraction(n, in);
		AnnotationMirror anno = atypeFactory.createUnknownAnnotation();
		CFValue newResultValue = analysis.createSingleAnnotationValue(anno, result.getResultValue().getType().getUnderlyingType());
		return new RegularTransferResult<>(newResultValue, result.getRegularStore());
	}
}

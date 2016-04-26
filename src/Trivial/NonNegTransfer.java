package Trivial;

import javax.lang.model.element.AnnotationMirror;

import org.checkerframework.dataflow.analysis.ConditionalTransferResult;
import org.checkerframework.dataflow.analysis.FlowExpressions.LocalVariable;
import org.checkerframework.dataflow.analysis.FlowExpressions.Receiver;
import org.checkerframework.dataflow.analysis.RegularTransferResult;
import org.checkerframework.dataflow.analysis.TransferInput;
import org.checkerframework.dataflow.analysis.TransferResult;
import org.checkerframework.dataflow.cfg.node.GreaterThanOrEqualNode;
import org.checkerframework.dataflow.cfg.node.LocalVariableNode;
import org.checkerframework.dataflow.cfg.node.Node;
import org.checkerframework.dataflow.cfg.node.NumericalSubtractionNode;
import org.checkerframework.framework.flow.CFAbstractTransfer;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.framework.type.AnnotatedTypeMirror;

import Trivial.qual.NonNegative;

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


	//checking >= @NonNegative transforms to @NonNegative
	@Override
	public TransferResult<CFValue, CFStore> visitGreaterThanOrEqual(GreaterThanOrEqualNode node, TransferInput<CFValue, CFStore> in){
		Node right = node.getRightOperand();
		NonNegAnnotatedTypeFactory atypeFactory = (NonNegAnnotatedTypeFactory) analysis.getTypeFactory();
		TransferResult<CFValue, CFStore> result = super.visitGreaterThanOrEqual(node, in);
		AnnotatedTypeMirror operand = atypeFactory.getAnnotatedType(right.getTree());	

		CFStore thenStore = result.getRegularStore();
		CFStore elseStore = thenStore.copy();

		ConditionalTransferResult<CFValue, CFStore> newResult = new ConditionalTransferResult<>(result.getResultValue(), thenStore, elseStore);

		try{
			LocalVariableNode n =((LocalVariableNode) node.getLeftOperand());
			Receiver r = new LocalVariable(n);
			elseStore.insertValue(r , atypeFactory.createUnknownAnnotation());
			if(operand.hasAnnotation(NonNegative.class)){
				AnnotationMirror anno = atypeFactory.createNonNegAnnotation();
				thenStore.insertValue(r, anno);
				return newResult;
			}
		}
		catch(ClassCastException e){
		}
		return result;

	}
}

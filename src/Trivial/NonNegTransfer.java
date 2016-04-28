package Trivial;

import javax.lang.model.element.AnnotationMirror;

import org.checkerframework.dataflow.analysis.ConditionalTransferResult;
import org.checkerframework.dataflow.analysis.FlowExpressions.LocalVariable;
import org.checkerframework.dataflow.analysis.FlowExpressions.Receiver;
import org.checkerframework.dataflow.analysis.RegularTransferResult;
import org.checkerframework.dataflow.analysis.TransferInput;
import org.checkerframework.dataflow.analysis.TransferResult;
import org.checkerframework.dataflow.cfg.node.GreaterThanNode;
import org.checkerframework.dataflow.cfg.node.GreaterThanOrEqualNode;
import org.checkerframework.dataflow.cfg.node.LocalVariableNode;
import org.checkerframework.dataflow.cfg.node.Node;
import org.checkerframework.dataflow.cfg.node.NumericalSubtractionNode;
import org.checkerframework.dataflow.cfg.node.ValueLiteralNode;
import org.checkerframework.framework.flow.CFAbstractTransfer;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.framework.type.AnnotatedTypeMirror;

import com.sun.source.tree.Tree;

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
	public TransferResult<CFValue, CFStore> visitNumericalSubtraction(NumericalSubtractionNode n, TransferInput<CFValue, CFStore> in) {
		NonNegAnnotatedTypeFactory atypeFactory = (NonNegAnnotatedTypeFactory) analysis.getTypeFactory();
		TransferResult<CFValue, CFStore> result = super.visitNumericalSubtraction(n, in);
		AnnotationMirror anno = atypeFactory.createUnknownAnnotation();
		CFValue newResultValue = analysis.createSingleAnnotationValue(anno, result.getResultValue().getType().getUnderlyingType());
		return new RegularTransferResult<>(newResultValue, result.getRegularStore());
	}


	//checking >= @NonNegative transforms to @NonNegative
	@Override
	public TransferResult<CFValue, CFStore> visitGreaterThanOrEqual(GreaterThanOrEqualNode node, TransferInput<CFValue, CFStore> in) {
		NonNegAnnotatedTypeFactory atypeFactory = (NonNegAnnotatedTypeFactory) analysis.getTypeFactory();
		TransferResult<CFValue, CFStore> result = super.visitGreaterThanOrEqual(node, in);
		// get the annotation from the right operand		
		// we only refine if the left is a variable so see if it is by try/catch on the cast
		try {
			LocalVariableNode n =((LocalVariableNode) node.getLeftOperand());
			
			//initialize the then and else store for this node, then applies to true branch, else to false
			CFStore thenStore = result.getRegularStore();
			CFStore elseStore = thenStore.copy();
			// initialize a transfer result for this node, using the super's results
			ConditionalTransferResult<CFValue, CFStore> newResult = new ConditionalTransferResult<>(result.getResultValue(), thenStore, elseStore);
			
			// set the node that receives the new annotation
			Receiver leftHand = new LocalVariable(n);
			// set the else branch type to be @unknown (its not greater so don't refine)
			elseStore.insertValue(leftHand , atypeFactory.createUnknownAnnotation());
			// we only refine if the right side is @NonNegative so check that
			Node right = node.getRightOperand();
			AnnotatedTypeMirror operand = atypeFactory.getAnnotatedType(right.getTree());
			if(operand.hasAnnotation(NonNegative.class)) {
				// create the @NonNegative annotation and put it in the then branch
				AnnotationMirror anno = atypeFactory.createNonNegAnnotation();
				thenStore.insertValue(leftHand, anno);
				return newResult;
			}
		}
		catch(ClassCastException e) {
		}
		// if we don't refine on the conditional just return what the super gave us
		return result;
	}
	
	// for checking > @NonNegative || > 1 transforms to @NonNegative
	// same as >= but check for -1
	@Override 
	public TransferResult<CFValue, CFStore> visitGreaterThan(GreaterThanNode node, TransferInput<CFValue, CFStore> in) {
		NonNegAnnotatedTypeFactory atypeFactory = (NonNegAnnotatedTypeFactory) analysis.getTypeFactory();
		TransferResult<CFValue, CFStore> result = super.visitGreaterThan(node, in);
		
		try {
			LocalVariableNode n =((LocalVariableNode) node.getLeftOperand());
			CFStore thenStore = result.getRegularStore();
			CFStore elseStore = thenStore.copy();
			ConditionalTransferResult<CFValue, CFStore> newResult = new ConditionalTransferResult<>(result.getResultValue(), thenStore, elseStore);
			Receiver r = new LocalVariable(n);
			elseStore.insertValue(r , atypeFactory.createUnknownAnnotation());
			boolean rightIsNeg1 = false;
			Node right = node.getRightOperand();
			AnnotatedTypeMirror operand = atypeFactory.getAnnotatedType(right.getTree());
			// here is the check for -1, try cast to int and check
			try {
				ValueLiteralNode v = (ValueLiteralNode) right;
				rightIsNeg1 = ((int)v.getValue() == -1);
			}
			catch(ClassCastException e) {
				
			}
			if (operand.hasAnnotation(NonNegative.class) || rightIsNeg1) {				
				AnnotationMirror anno = atypeFactory.createNonNegAnnotation();
				thenStore.insertValue(r, anno);
				return newResult;
			}
		}
		catch(ClassCastException e) {
			
		}
		return result;
	}
}

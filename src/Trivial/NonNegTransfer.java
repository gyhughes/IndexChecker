package Trivial;

import org.checkerframework.framework.flow.CFAbstractAnalysis;
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

}

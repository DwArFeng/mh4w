package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.jier.mh4w.core.model.struct.Flow;

/**
 * 过程模型观察器适配器。
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public abstract class FlowAdapter implements FlowObverser{

	@Override
	public void fireProgressChanged(Flow flow, int oldValue, int newValue) {}
	@Override
	public void fireTotleProgressChanged(Flow flow, int oldValue, int newValue) {}
	@Override
	public void fireDeterminateChanged(Flow flow, boolean oldValue, boolean newValue) {}
	@Override
	public void fireMessageChanged(Flow flow, String oldValue, String newValue) {}
	@Override
	public void fireThrowableChanged(Flow flow, Throwable oldValue, Throwable newValue) {}
	@Override
	public void fireCancelableChanged(Flow flow, boolean oldValue, boolean newValue) {}
	@Override
	public void fireCanceled(Flow flow) {}
	@Override
	public void fireDone(Flow flow) {}
	
}

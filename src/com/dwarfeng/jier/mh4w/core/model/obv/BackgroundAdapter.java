package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.jier.mh4w.core.model.struct.Flow;

/**
 * 后台模型观察器适配器。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public abstract class BackgroundAdapter implements BackgroundObverser {

	@Override
	public void fireFlowProgressChanged(Flow flow, int oldValue, int newValue) {}
	@Override
	public void fireFlowTotleProgressChanged(Flow flow, int oldValue, int newValue) {}
	@Override
	public void fireFlowDeterminateChanged(Flow flow, boolean oldValue, boolean newValue) {}
	@Override
	public void fireFlowMessageChanged(Flow flow, String oldValue, String newValue) {}
	@Override
	public void fireFlowThrowableChanged(Flow flow, Throwable oldValue, Throwable newValue) {}
	@Override
	public void fireFlowCancelableChanged(Flow flow, boolean oldValue, boolean newValue) {}
	@Override
	public void fireFlowCanceled(Flow flow) {}
	@Override
	public void fireFlowDone(Flow flow) {}
	@Override
	public void fireFlowAdded(Flow flow) {}
	@Override
	public void fireFlowRemoved(Flow flow) {}
	
}

package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.jier.mh4w.core.model.eum.CountState;

/**
 * ×´Ì¬¹Û²ìÆ÷ÊÊÅäÆ÷¡£
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class StateAdapter implements StateObverser {

	@Override
	public void fireReadyForCountChanged(boolean newValue) {}
	@Override
	public void fireCountStateChanged(CountState oldValue, CountState newValue) {}
	@Override
	public void fireCountResultOutdatedChanged(boolean newValue) {}

}

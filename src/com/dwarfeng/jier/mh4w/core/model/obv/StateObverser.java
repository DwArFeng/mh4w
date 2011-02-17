package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.eum.CountState;

/**
 * 状态观察器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface StateObverser extends Obverser{
	
	/**
	 * 通知模型准备好统计状态改变。
	 * @param newValue 新的状态。
	 */
	public void fireReadyForCountChanged(boolean newValue);
	
	/**
	 * 通知模型的统计状态发生改变。
	 * @param oldValue 旧的统计状态。
	 * @param newValue 新的统计状态。
	 */
	public void fireCountStateChanged(CountState oldValue, CountState newValue);
	
	/**
	 * 通知模型的过时状态发生改变。
	 * @param newValue 新的过时状态。
	 */
	public void fireCountResultOutdatedChanged(boolean newValue);
	
}

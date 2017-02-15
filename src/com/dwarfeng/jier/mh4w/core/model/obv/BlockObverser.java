package com.dwarfeng.jier.mh4w.core.model.obv;

import java.util.Set;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 阻挡观察器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface BlockObverser extends Obverser {

	/**
	 * 通知入口被添加。
	 * @param key 指定的键。
	 * @param value 指定的值。
	 */
	public void fireEntryAdded(String key, Set<String> value);
	
	/**
	 * 通知入口被移除。
	 * @param key 指定的键。
	 */
	public void fireEntryRemoved(String key);
	
	/**
	 * 通知入口被更改。
	 * @param key 指定的键。
	 * @param oldValue 旧的值。
	 * @param newValue 新的值。
	 */
	public void fireEntryChanged(String key, Set<String> oldValue, Set<String> newValue);
	
	/**
	 * 通知模型被清除。
	 */
	public void fireCleared();
	
	/**
	 * 通知模型被更新。
	 */
	public void fireUpdated();
	
}

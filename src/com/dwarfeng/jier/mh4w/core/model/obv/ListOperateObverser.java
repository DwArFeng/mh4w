package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 列表操作观察器。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface ListOperateObverser<T> extends Obverser {

	/**
	 * 通知数据被添加。
	 * @param index 序号。
	 * @param value 数据。
	 */
	public void fireAdded(int index, T value);
	
	/**
	 * 通知数据被更改。
	 * @param index 序号。
	 * @param oldValue 旧数据。
	 * @param newValue 新数据。
	 */
	public void fireChanged(int index, T oldValue, T newValue);
	
	/**
	 * 通知指定序号的数据被移除。
	 * @param index 序号。
	 */
	public void fireRemoved(int index);
	
	/**
	 * 通知数据被清除。
	 */
	public void fireCleared();
	
}

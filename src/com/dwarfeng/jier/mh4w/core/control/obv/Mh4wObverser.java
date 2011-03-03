package com.dwarfeng.jier.mh4w.core.control.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.dutil.basic.prog.RuntimeState;

/**
 * 工时统计实例观察器。
 * @author DwArFeng
 * @since 1.1.0
 */
public interface Mh4wObverser extends Obverser{

	/**
	 * 通知实例的状态发生改变。
	 * @param oldValue 旧的状态。
	 * @param newValue 新的状态。
	 */
	public void fireStateChanged(RuntimeState oldValue, RuntimeState newValue);
	
}

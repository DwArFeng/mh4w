package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;

/**
 * 工作观察器。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface JobObverser extends Obverser {
	
	/**
	 * 通知指定的工作被添加。
	 * @param job 指定的工作。
	 */
	public void fireJobAdded(Job job);
	
	/**
	 * 通知指定的工作被移除。
	 * @param job 指定的工作。
	 */
	public void fireJobRemoved(Job job);
	
	/**
	 * 通知该工作模型清空。
	 */
	public void fireJobCleared();
	
}

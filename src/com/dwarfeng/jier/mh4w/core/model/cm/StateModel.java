package com.dwarfeng.jier.mh4w.core.model.cm;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.eum.CountState;
import com.dwarfeng.jier.mh4w.core.model.obv.StateObverser;

/**
 * 统计状态模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface StateModel extends ExternalReadWriteThreadSafe, ObverserSet<StateObverser>{

	/**
	 * 返回是否准备好进行统计。
	 * @return 是否准备好进行统计
	 */
	public boolean isReadyForCount();
	
	/**
	 * 是否准备好进行统计。
	 * @param aFlag 是否准备好。
	 */
	public void setReadyForCount(boolean aFlag);
	
	/**
	 * 获取统计状态。
	 * @return 统计状态。
	 */
	public CountState getCountState();
	
	/**
	 * 设置统计状态。
	 * @param countState 指定的统计状态。
	 * @return 该操作是否改变了模型本身。
	 */
	public boolean setCountState(CountState countState);
	
	/**
	 * 统计结果是否过期。
	 * @return 统计结果是否过期。
	 */
	public boolean isCountResultOutdated();
	
	/**
	 * 设置统计结果是否过期。
	 * @param aFlag 统计结果是否过期。
	 */
	public void setCountResultOutdated(boolean aFlag);
	
}

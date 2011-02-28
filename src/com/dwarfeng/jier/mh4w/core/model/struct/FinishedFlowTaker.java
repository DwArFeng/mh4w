package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel;

/**
 * 完成过程取出器。
 * <p> 用于在后台模型中取出完成的过程，并且记录在指定的 Logger 之中。
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface FinishedFlowTaker extends ExternalReadWriteThreadSafe, MutilangSupported{

	/**
	 * 获取该完成过程取出器持有的记录器。
	 * @return 完成过程取出器持有的记录器。
	 */
	public Logger getLogger();
	
	/**
	 * 更新记录器。
	 * <p> 该方法一般用于在记录器模型更新后，完成过程取出器进行相关的更新。
	 */
	public void updateLogger();
	
	/**
	 * 获取该过程取出器中的后台模型。
	 * @return 该过程取出器中的后台模型。
	 */
	public BackgroundModel getBackgroundModel();
	
	/**
	 * 关闭该完成过程取出器。
	 * <p> 调用此方法后，取出器将停止从指定的后台模型中取出完成的过程。
	 */
	public void shutdown();
	
}

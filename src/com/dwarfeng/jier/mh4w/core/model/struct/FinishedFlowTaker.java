package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel;

/**
 * 完成过程取出器。
 * <p> 用于在后台模型中取出完成的过程，并且记录在指定的 Logger 之中。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface FinishedFlowTaker extends ExternalReadWriteThreadSafe, MutilangSupported{

	/**
	 * 获取该完成过程取出器持有的记录器。
	 * @return 完成过程取出器持有的记录器。
	 */
	public Logger getLogger();
	
	/**
	 * 设置该完成过程取出器中的记录器。
	 * @param logger 指定的记录器。
	 * @return 该操作是否对该记录器造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean setLogger(Logger logger);
	
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

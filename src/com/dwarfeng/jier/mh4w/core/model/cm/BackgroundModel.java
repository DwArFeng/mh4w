package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.BackgroundObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Flow;

/**
 * 后台模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface BackgroundModel extends ObverserSet<BackgroundObverser>, ExternalReadWriteThreadSafe, Iterable<Flow>{
	
	/**
	 * 返回该后台模型中用于处理过程的执行器服务。
	 * <p> 注意：返回的执行器服务仅应该用于查询状态，调用其其它方法会抛出 {@link UnsupportedOperationException}。
	 * @return 后台模型中的执行器服务。
	 */
	public ExecutorService getExecutorService();
	
	/**
	 * 向后台模型中提交一个过程。
	 * <p> 当指定的进程为 <code>null</code>，或者模型中已经包含了指定的进程时，不进行任何操作。
	 * @param flow 指定的进程。
	 * @return 该操作是否对模型造成了改变。
	 */
	public boolean submit(Flow flow);
	
	/**
	 * 向后台模型中提交指定集合中的所有过程。
	 * <p> 当指定的进程为 <code>null</code>，或者模型中已经包含了指定的进程时，不进行任何操作。
	 * @param c 所有过程组成的集合。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 * @return 该操作过程是否对模型造成了改变。
	 */
	public boolean submitAll(Collection<? extends Flow> c);
	
	/**
	 * 返回该后台进程是否包含指定的过程。
	 * @param flow 指定的对象。
	 * @return 该后台模型中是否包含指定的对象。
	 */
	public boolean contains(Flow flow);
	
	/**
	 * 返回后台进程中是否包含全部的指定对象。
	 * @param c 所有指定对象组成的集合。
	 * @return 后台模型中是否包含所有的指定对象。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean containsAll(Collection<Flow> c);
	
	/**
	 * 返回该模型是否为空。
	 * @return 该模型是否为空。
	 */
	public boolean isEmpty();
	
	/**
	 * 返回该模型中是否包含已经完成的过程。
	 * @return 是否包含已经完成的过程。
	 */
	public boolean hasFinished();
	
	/**
	 * 返回最早的已经完成的过程对象，如果没有，则等待。
	 * @return 最早的已经完成的过程对象。
	 * @throws InterruptedException 等待过程中线程被中断。
	 */
	public Flow takeFinished() throws InterruptedException;
	
	/**
	 * 清除模型中所有的已经完成的过程。
	 * @return 该方法是否改变了模型本身。
	 */
	public boolean clearFinished();
	
	/**
	 * 关闭该后台模型。
	 * <p> 后台模型被关闭后，会拒绝所有过程的提交；对于已经提交的过程则无影响。
	 */
	public void shutdown();
	
}

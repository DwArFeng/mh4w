package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.concurrent.Callable;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.FlowObverser;

public interface Flow extends Callable<Object>, ObverserSet<FlowObverser>, ExternalReadWriteThreadSafe {

	/**
	 * 返回过程的进度。
	 * @return 过程的进度。
	 */
	public int getProgress();

	/**
	 * 返回过程的总进度。
	 * @return 过程的总进度。
	 */
	public int getTotleProgress();

	/**
	 * 返回该过程是确定过程还是不确定过程。
	 * @return 该过程是否为确定过程。
	 */
	public boolean isDeterminate();

	/**
	 * 返回该过程是否被取消。
	 * @return 该过程是否被取消。
	 */
	public boolean isCancel();
	
	/**
	 * 指示该过程是否能被取消。
	 * @return 该过程能否被取消。
	 */
	public boolean isCancelable();

	/**
	 * 返回该过程是否完成。
	 * @return 该过程是否完成。
	 */
	public boolean isDone();

	/**
	 * 返回该过程的消息。
	 * @return 该过程的消息。
	 */
	public String getMessage();
	
	/**
	 * 返回过程中的可抛出对象。
	 * <p> 返回值为 <code>null</code>通常代表过程被成功的执行完毕；
	 * 不为 <code>null</code> 则通常意味着过程在什么地方出现了问题，
	 * 而返回的可抛出对象就是问题的根源。
	 * @return 返回过程中的可抛出对象。
	 */
	public Throwable getThrowable();
	
	/**
	 * 等待该过程执行完毕。
	 * <p> 调用该方法的线程会在过程执行完毕之前一直阻塞。
	 * @throws InterruptedException 线程在等待的时候被中断。
	 */
	public void waitFinished() throws InterruptedException;

}
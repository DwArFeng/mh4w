package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 只读执行器服务器。
 * <p> 该类代理一个执行器服务器，用于获取执行器服务器的状态。
 * <p> 该执行器服务器仅可用于查询状态，调用其它的任何方法都将抛出异常。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class ReadOnlyExecutorService implements ExecutorService{
	
	private final ExecutorService es;
	
	/**
	 * 新实例。
	 * @param es 指定的代理执行器服务器。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public ReadOnlyExecutorService(ExecutorService es) {
		Objects.requireNonNull(es, "入口参数 es 不能为 null。");
		this.es = es;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.Executor#execute(java.lang.Runnable)
	 */
	@Override
	public void execute(Runnable command) {
		throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#shutdown()
	 */
	@Override
	public void shutdown() {
		throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#shutdownNow()
	 */
	@Override
	public List<Runnable> shutdownNow() {
		throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#isShutdown()
	 */
	@Override
	public boolean isShutdown() {
		return es.isShutdown();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#isTerminated()
	 */
	@Override
	public boolean isTerminated() {
		return es.isTerminated();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#awaitTermination(long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#submit(java.util.concurrent.Callable)
	 */
	@Override
	public <T> Future<T> submit(Callable<T> task) {
		throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#submit(java.lang.Runnable, java.lang.Object)
	 */
	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#submit(java.lang.Runnable)
	 */
	@Override
	public Future<?> submit(Runnable task) {
		throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#invokeAll(java.util.Collection)
	 */
	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#invokeAll(java.util.Collection, long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException {
		throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#invokeAny(java.util.Collection)
	 */
	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
			throws InterruptedException, ExecutionException {
		throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#invokeAny(java.util.Collection, long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		throw new UnsupportedOperationException("该 ExecutorService 仅可用于查询状态。");
	}
	
}
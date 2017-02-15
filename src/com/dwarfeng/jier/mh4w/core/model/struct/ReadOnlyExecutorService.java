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
 * ֻ��ִ������������
 * <p> �������һ��ִ���������������ڻ�ȡִ������������״̬��
 * <p> ��ִ�����������������ڲ�ѯ״̬�������������κη��������׳��쳣��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class ReadOnlyExecutorService implements ExecutorService{
	
	private final ExecutorService es;
	
	/**
	 * ��ʵ����
	 * @param es ָ���Ĵ���ִ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public ReadOnlyExecutorService(ExecutorService es) {
		Objects.requireNonNull(es, "��ڲ��� es ����Ϊ null��");
		this.es = es;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.Executor#execute(java.lang.Runnable)
	 */
	@Override
	public void execute(Runnable command) {
		throw new UnsupportedOperationException("�� ExecutorService �������ڲ�ѯ״̬��");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#shutdown()
	 */
	@Override
	public void shutdown() {
		throw new UnsupportedOperationException("�� ExecutorService �������ڲ�ѯ״̬��");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#shutdownNow()
	 */
	@Override
	public List<Runnable> shutdownNow() {
		throw new UnsupportedOperationException("�� ExecutorService �������ڲ�ѯ״̬��");
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
		throw new UnsupportedOperationException("�� ExecutorService �������ڲ�ѯ״̬��");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#submit(java.util.concurrent.Callable)
	 */
	@Override
	public <T> Future<T> submit(Callable<T> task) {
		throw new UnsupportedOperationException("�� ExecutorService �������ڲ�ѯ״̬��");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#submit(java.lang.Runnable, java.lang.Object)
	 */
	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		throw new UnsupportedOperationException("�� ExecutorService �������ڲ�ѯ״̬��");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#submit(java.lang.Runnable)
	 */
	@Override
	public Future<?> submit(Runnable task) {
		throw new UnsupportedOperationException("�� ExecutorService �������ڲ�ѯ״̬��");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#invokeAll(java.util.Collection)
	 */
	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		throw new UnsupportedOperationException("�� ExecutorService �������ڲ�ѯ״̬��");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#invokeAll(java.util.Collection, long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException {
		throw new UnsupportedOperationException("�� ExecutorService �������ڲ�ѯ״̬��");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#invokeAny(java.util.Collection)
	 */
	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
			throws InterruptedException, ExecutionException {
		throw new UnsupportedOperationException("�� ExecutorService �������ڲ�ѯ״̬��");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.ExecutorService#invokeAny(java.util.Collection, long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		throw new UnsupportedOperationException("�� ExecutorService �������ڲ�ѯ״̬��");
	}
	
}
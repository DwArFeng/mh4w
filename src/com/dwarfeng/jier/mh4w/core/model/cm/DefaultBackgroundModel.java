package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Condition;

import com.dwarfeng.dutil.basic.threads.NumberedThreadFactory;
import com.dwarfeng.jier.mh4w.core.model.obv.BackgroundObverser;
import com.dwarfeng.jier.mh4w.core.model.obv.FlowObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Flow;

/**
 * 默认后台模型。
 * <p> 后台模型接口的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultBackgroundModel extends AbstractBackgroundModel {
	
	private static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("background");
	
	private final Condition condition = lock.writeLock().newCondition();
	private final Set<Flow> flows = new HashSet<>();
	private final Queue<Flow> finishedFlows = new ArrayDeque<>();
	private final FlowObverser flowObverser = new FlowObverser() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FlowObverser#fireTotleProgressChanged(com.dwarfeng.jier.mh4w.core.model.struct.Flow, int, int)
		 */
		@Override
		public void fireTotleProgressChanged(Flow flow, int oldValue, int newValue) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireFlowTotleProgressChanged(flow, oldValue, newValue);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FlowObverser#fireThrowableChanged(com.dwarfeng.jier.mh4w.core.model.struct.Flow, java.lang.Throwable, java.lang.Throwable)
		 */
		@Override
		public void fireThrowableChanged(Flow flow, Throwable oldValue, Throwable newValue) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireFlowThrowableChanged(flow, oldValue, newValue);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FlowObverser#fireCancelableChanged(com.dwarfeng.jier.mh4w.core.model.struct.Flow, boolean, boolean)
		 */
		@Override
		public void fireCancelableChanged(Flow flow, boolean oldValue, boolean newValue) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireFlowCancelableChanged(flow, oldValue, newValue);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FlowObverser#fireProgressChanged(com.dwarfeng.jier.mh4w.core.model.struct.Flow, int, int)
		 */
		@Override
		public void fireProgressChanged(Flow flow, int oldValue, int newValue) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireFlowProgressChanged(flow, oldValue, newValue);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FlowObverser#fireMessageChanged(com.dwarfeng.jier.mh4w.core.model.struct.Flow, java.lang.String, java.lang.String)
		 */
		@Override
		public void fireMessageChanged(Flow flow, String oldValue, String newValue) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireFlowMessageChanged(flow, oldValue, newValue);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FlowObverser#fireDone(com.dwarfeng.jier.mh4w.core.model.struct.Flow)
		 */
		@Override
		public void fireDone(Flow flow) {
			lock.writeLock().lock();
			try{
				finishedFlows.offer(flow);
				condition.signalAll();
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireFlowDone(flow);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FlowObverser#fireDeterminateChanged(com.dwarfeng.jier.mh4w.core.model.struct.Flow, boolean, boolean)
		 */
		@Override
		public void fireDeterminateChanged(Flow flow, boolean oldValue, boolean newValue) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireFlowDeterminateChanged(flow, oldValue, newValue);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FlowObverser#fireCanceled(com.dwarfeng.jier.mh4w.core.model.struct.Flow)
		 */
		@Override
		public void fireCanceled(Flow flow) {
			lock.writeLock().lock();
			try{
				for(BackgroundObverser obverser : obversers){
					if(Objects.nonNull(obverser)) obverser.fireFlowCanceled(flow);
				}
			}finally {
				lock.writeLock().unlock();
			}
		}
	};
	
	/**
	 * 新实例。
	 */
	public DefaultBackgroundModel() {
		super(Executors.newCachedThreadPool(THREAD_FACTORY));
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel#submit(com.dwarfeng.jier.mh4w.core.model.struct.Flow)
	 */
	@Override
	public boolean submit(Flow flow) {
		lock.writeLock().lock();
		try{
			if(es.isShutdown()) return false;
			if(Objects.isNull(flow)) return false;
			if(flows.contains(flow)) return false;
			
			flow.addObverser(flowObverser);
			flows.add(flow);
			es.submit(flow);
			fireFlowAdded(flow);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireFlowAdded(Flow flow) {
		for(BackgroundObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireFlowAdded(flow);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel#submitAll(java.util.Collection)
	 */
	@Override
	public boolean submitAll(Collection<? extends Flow> c) {
		Objects.requireNonNull(c, "入口参数 c 不能为 nul。");
		
		lock.writeLock().lock();
		try{
			boolean aFlag = false;
			for(Flow flow : c){
				if(submit(flow)) aFlag = true;
			}
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel#contains(com.dwarfeng.jier.mh4w.core.model.struct.Flow)
	 */
	@Override
	public boolean contains(Flow flow) {
		lock.readLock().lock();
		try{
			return flows.contains(flow);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<Flow> c) {
		Objects.requireNonNull(c, "入口参数 c 不能为 null。");
		
		lock.readLock().lock();
		try{
			return flows.containsAll(c);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		lock.readLock().lock();
		try{
			return flows.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * 返回该后台模型的过程迭代器。
	 * <p> 注意，迭代器合不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 该后台模型的过程迭代器。
	 */
	@Override
	public Iterator<Flow> iterator() {
		lock.readLock().lock();
		try{
			return flows.iterator();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel#hasFinished()
	 */
	@Override
	public boolean hasFinished() {
		lock.readLock().lock();
		try{
			return !finishedFlows.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel#takeFinished()
	 */
	@Override
	public Flow takeFinished() throws InterruptedException {
		lock.writeLock().lock();
		try{
			while(finishedFlows.isEmpty()){
				condition.await();
			}
			Flow flow = finishedFlows.peek();
			remove(flow);
			return flow;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void remove(Flow flow) {
		finishedFlows.remove(flow);
		flows.remove(flow);
		fireFlowRemoved(flow);
	}

	private void fireFlowRemoved(Flow flow) {
		for(BackgroundObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireFlowRemoved(flow);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel#clearFinished()
	 */
	@Override
	public boolean clearFinished() {
		lock.writeLock().lock();
		try{
			if(finishedFlows.isEmpty()) return false;
			
			for(Flow flow : finishedFlows){
				remove(flow);
			}
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel#shutdown()
	 */
	@Override
	public void shutdown() {
		lock.writeLock().lock();
		try{
			es.shutdown();
		}finally {
			lock.writeLock().unlock();
		}
	}
	
}

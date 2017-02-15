package com.dwarfeng.jier.mh4w.core.view.struct;

import java.awt.Component;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractGuiController<T extends Component> implements GuiController<T>{

	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	protected T component = null;

	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe#getLock()
	 */
	@Override
	public ReadWriteLock getLock() {
		return this.lock;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.struct.GuiController#hasInstance()
	 */
	@Override
	public boolean hasInstance() {
		lock.readLock().lock();
		try{
			return Objects.nonNull(null);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.struct.GuiController#getInstance()
	 */
	@Override
	public T getInstance() {
		lock.readLock().lock();
		try{
			return component;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.struct.GuiController#isVisible()
	 */
	@Override
	public boolean isVisible() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(component)) return false;
			return component.isVisible();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.struct.GuiController#setVisible(boolean)
	 */
	@Override
	public boolean setVisible(boolean aFlag) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(component)) return false;
			component.setVisible(aFlag);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.struct.GuiController#show()
	 */
	@Override
	public void show() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(component)){
				component = subNewInstance();
				if(component == null) return;
				component.setVisible(true);
			}else{
				component.setVisible(true);
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.struct.GuiController#newInstance()
	 */
	@Override
	public boolean newInstance() {
		lock.writeLock().lock();
		try{
			if(Objects.nonNull(component)) return false;
			component = subNewInstance();
			return component != null;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 *生成新实例。
	 *<p> 只有控制器中没有实例且调用了 {@link #newInstance()} 方法时，才会调用此方法。
	 * @return 实例有没有被生成。
	 */
	protected abstract T subNewInstance();

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.struct.GuiController#dispose()
	 */
	@Override
	public boolean dispose() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(component)) return false;
			subDispose(component);
			this.component = null;
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 释放实例。
	 * <p> 只有控制器中有实例且调用了 {@link #dispose()} 方法时，才会调用此方法。
	 * <p> 该方法中传入的入口参数保证不为 <code>null</code>。
	 */
	protected abstract void subDispose(T component);
	
}

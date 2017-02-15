package com.dwarfeng.jier.mh4w.core.view.ctrl;

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
				component = newInstanceImpl();
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
			component = newInstanceImpl();
			return component != null;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 *������ʵ��ʵ�ַ�����
	 *<p> ֻ�п�������û��ʵ���ҵ����� {@link #newInstance()} ����ʱ���Ż���ô˷�����
	 * @return ʵ����û�б����ɡ�
	 */
	protected abstract T newInstanceImpl();

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.struct.GuiController#dispose()
	 */
	@Override
	public boolean dispose() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(component)) return false;
			disposeImpl(component);
			this.component = null;
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * �ͷ�ʵ��ʵ�ַ�����
	 * <p> ֻ�п���������ʵ���ҵ����� {@link #dispose()} ����ʱ���Ż���ô˷�����
	 * <p> �÷����д������ڲ�����֤��Ϊ <code>null</code>��
	 */
	protected abstract void disposeImpl(T component);
	
}

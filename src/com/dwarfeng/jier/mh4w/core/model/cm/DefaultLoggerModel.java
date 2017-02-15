package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.jier.mh4w.core.model.obv.LoggerObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Logger;
import com.dwarfeng.jier.mh4w.core.model.struct.LoggerInfo;
import com.dwarfeng.jier.mh4w.core.model.struct.ProcessException;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;

/**
 * Ĭ�ϼ�¼��ģ�͡�
 * <p> ��¼��ģ�ͽӿڵ�Ĭ��ʵ�֡�
 * <p> ��ģ���е����ݵĶ�д�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultLoggerModel extends AbstractLoggerModel {
	
	private Set<LoggerInfo> delegate = new HashSet<>();
	private LoggerContext loggerContext;
	
	private final InnerLogger logger = new InnerLogger();
	
	/**
	 * ��ʵ����
	 */
	public DefaultLoggerModel() {
		this(Mh4wUtil.newDefaultLoggerContext(), Constants.getDefaultLoggerInfos());
	}
	
	/**
	 * ��ʵ����
	 * @param loggerContext ָ���ļ�¼�������ġ�
	 * @param loggerInfos ��ʼ�ļ�¼����Ϣ���ϡ�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultLoggerModel(LoggerContext loggerContext, Set<LoggerInfo> loggerInfos) {
		Objects.requireNonNull(loggerContext, "��ڲ��� loggerContext ����Ϊ null��");
		Objects.requireNonNull(loggerInfos, "��ڲ��� loggerInfos ����Ϊ null��");
		
		this.loggerContext = loggerContext;
		this.delegate.addAll(loggerInfos);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.LoggerModel#getLoggerContext()
	 */
	@Override
	public LoggerContext getLoggerContext() {
		lock.readLock().lock();
		try{
			return this.loggerContext;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.LoggerModel#setLoggerContext(org.apache.logging.log4j.core.LoggerContext)
	 */
	@Override
	public boolean setLoggerContext(LoggerContext loggerContext) {
		Objects.requireNonNull(loggerContext, "��ڲ��� loggerContext ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			if(Objects.equals(loggerContext, this.loggerContext)) return false;
			LoggerContext oldOne = this.loggerContext;
			this.loggerContext = loggerContext;
			fireLoggerContextChanged(oldOne, loggerContext);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireLoggerContextChanged(LoggerContext oldOne, LoggerContext newOne) {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoggerContextChanged(oldOne, newOne);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#size()
	 */
	@Override
	public int size() {
		lock.readLock().lock();
		try{
			return delegate.size();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		lock.readLock().lock();
		try{
			return delegate.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		lock.readLock().lock();
		try{
			return delegate.contains(o);
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ظ�ģ�͵ĵ�������
	 * <p> �õ����������̰߳�ȫ�ģ�
	 * ����ʱ��ʹ�� {@link #getLock()} ͬ���������ⲿͬ����
	 * @return ��ģ�͵ĵ�������
	 */
	@Override
	public Iterator<LoggerInfo> iterator() {
		lock.readLock().lock();
		try{
			return delegate.iterator();
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ظ�ģ�͵����顣
	 * <p> �����鲻���̰߳�ȫ�ģ�
	 * ����ʱ��ʹ�� {@link #getLock()} ͬ���������ⲿͬ����
	 * @return ��ģ�͵����顣
	 */
	@Override
	public Object[] toArray() {
		lock.readLock().lock();
		try{
			return delegate.toArray();
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ظ�ģ�͵����飬�����������ָ����������ͬ��
	 * <p> �����鲻���̰߳�ȫ�ģ�
	 * ����ʱ��ʹ�� {@link #getLock()} ͬ���������ⲿͬ����
	 * @param a ָ�������顣
	 * @return ��ģ�͵����顣
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		lock.readLock().lock();
		try{
			return delegate.toArray(a);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#add(java.lang.Object)
	 */
	@Override
	public boolean add(LoggerInfo e) {
		Objects.requireNonNull(e, "��ڲ��� e ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			boolean aFlag = delegate.add(e);
			if(aFlag){
				fireLoggerInfoAdded(e);
			}
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireLoggerInfoAdded(LoggerInfo loggerInfo) {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoggerInfoAdded(loggerInfo);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		lock.writeLock().lock();
		try{
			boolean aFlag = delegate.remove(o);
			if(aFlag){
				fireLoggerRemoved((LoggerInfo) o);
			}
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireLoggerRemoved(LoggerInfo loggerInfo) {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoggerInfoRemoved(loggerInfo);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		Objects.requireNonNull(c, "��ڲ��� c ����Ϊ null��");
		
		lock.readLock().lock();
		try{
			return delegate.containsAll(c);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends LoggerInfo> c) {
		Objects.requireNonNull(c, "��ڲ��� c ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			boolean aFlag = false;
			for(LoggerInfo loggerInfo : c){
				if(delegate.add(loggerInfo)) aFlag = true;
			}
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("��ģ�Ͳ�֧�ִ˷���");
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		Objects.requireNonNull(c, "��ڲ��� c ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			boolean aFlag = false;
			for(Object obj : c){
				if(delegate.remove(obj)) aFlag = true;
			}
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#clear()
	 */
	@Override
	public void clear() {
		lock.writeLock().lock();
		try{
			delegate.clear();
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Updateable#update()
	 */
	@Override
	public void update() throws ProcessException {
		lock.writeLock().lock();
		logger.loggerLock.lock();
		try{
			try{
				Set<org.apache.logging.log4j.core.Logger> loggers = null;
				try{
					loggers = new HashSet<>();
					for(LoggerInfo loggerInfo : delegate){
						loggers.add(loggerContext.getLogger(loggerInfo.getName()));
					}
				}catch (Exception e) {
					throw new ProcessException("��¼��ģ�͸���ʱ�����쳣��ʹ���ϴε�����", e);
				}
				logger.loggers = loggers;
			}finally {
				fireUpdated();
			}
			
		}finally {
			lock.writeLock().unlock();
			logger.loggerLock.unlock();
		}
		
	}

	private void fireUpdated() {
		for(LoggerObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireUpdated();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.LoggerModel#getLogger()
	 */
	@Override
	public Logger getLogger() {
		return logger;
	}
	
	
	private class InnerLogger implements Logger {
		
		private final Lock loggerLock = new ReentrantLock();
		
		private Set<org.apache.logging.log4j.core.Logger> loggers;
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.struct.Logger#debug(java.lang.String)
		 */
		@Override
		public void debug(String message) {
			loggerLock.lock();
			try{
				for(org.apache.logging.log4j.core.Logger logger : loggers){
					logger.debug(message);
				}
			}finally {
				logger.loggerLock.unlock();
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.struct.Logger#trace(java.lang.String)
		 */
		@Override
		public void trace(String message) {
			loggerLock.lock();
			try{
				for(org.apache.logging.log4j.core.Logger logger : loggers){
					logger.trace(message);
				}
			}finally {
				logger.loggerLock.unlock();
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.struct.Logger#info(java.lang.String)
		 */
		@Override
		public void info(String message) {
			loggerLock.lock();
			try{
				for(org.apache.logging.log4j.core.Logger logger : loggers){
					logger.info(message);
				}
			}finally {
				logger.loggerLock.unlock();
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.struct.Logger#warn(java.lang.String)
		 */
		@Override
		public void warn(String message) {
			loggerLock.lock();
			try{
				for(org.apache.logging.log4j.core.Logger logger : loggers){
					logger.warn(message);
				}
			}finally {
				logger.loggerLock.unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.struct.Logger#warn(java.lang.String, java.lang.Throwable)
		 */
		@Override
		public void warn(String message, Throwable t) {
			loggerLock.lock();
			try{
				for(org.apache.logging.log4j.core.Logger logger : loggers){
					logger.warn(message, t);
				}
			}finally {
				logger.loggerLock.unlock();
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.struct.Logger#error(java.lang.String, java.lang.Throwable)
		 */
		@Override
		public void error(String message, Throwable t) {
			loggerLock.lock();
			try{
				for(org.apache.logging.log4j.core.Logger logger : loggers){
					logger.error(message, t);
				}
			}finally {
				logger.loggerLock.unlock();
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.struct.Logger#fatal(java.lang.String, java.lang.Throwable)
		 */
		@Override
		public void fatal(String message, Throwable t) {
			loggerLock.lock();
			try{
				for(org.apache.logging.log4j.core.Logger logger : loggers){
					logger.fatal(message, t);
				}
			}finally {
				logger.loggerLock.unlock();
			}
		}
	}
	
}

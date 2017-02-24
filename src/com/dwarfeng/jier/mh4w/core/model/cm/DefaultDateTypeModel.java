package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.obv.DateTypeObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;

/**
 * Ĭ����������ģ�͡�
 * <p> ��������ģ�͵�Ĭ��ʵ�֡�
 * <p> ��ģ���е����ݵĶ�д�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultDateTypeModel extends AbstractDateTypeModel {

	private final Map<CountDate, DateType> delegate = new HashMap<>();
	
	/**
	 * ��ʵ����
	 */
	public DefaultDateTypeModel() {
		this(new HashMap<>());
	}
	
	/**
	 * ��ʵ����
	 * @param m ָ���ĳ�ʼ�����ݡ�
	 * @throws NullPointerException  ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultDateTypeModel(Map<CountDate, DateType> m) {
		Objects.requireNonNull(m, "��ڲ��� m ����Ϊ null��");
		delegate.putAll(m);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Map#size()
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
	 * @see java.util.Map#isEmpty()
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
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		lock.readLock().lock();
		try{
			return delegate.containsKey(key);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		lock.readLock().lock();
		try{
			return delegate.containsValue(value);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public DateType get(Object key) {
		lock.readLock().lock();
		try{
			return delegate.get(key);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public DateType put(CountDate key, DateType value) {
		lock.writeLock().lock();
		try{
			boolean aFlag = delegate.containsKey(key);
			if(aFlag){
				DateType oldValue = delegate.put(key, value);
				fireDateChanged(key, oldValue, value);
				return oldValue;
			}else{
				DateType dateType = delegate.put(key, value);
				fireDatePut(key, value);
				return dateType;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireDatePut(CountDate key, DateType value) {
		for(DateTypeObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDatePut(key, value);
		}
	}

	private void fireDateChanged(CountDate key, DateType oldValue, DateType newValue) {
		for(DateTypeObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDateChanged(key, oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public DateType remove(Object key) {
		lock.writeLock().lock();
		try{
			boolean aFlag = delegate.containsKey(key);
			DateType dateType = delegate.remove(key);
			if(aFlag){
				//ֻ�� key ���� CountDate ���󣬲��п��ܱ��Ƴ�����˸�����ת��ʱ��ȫ�ġ�
				fireDateRemoved((CountDate) key);
			}
			return dateType;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireDateRemoved(CountDate key) {
		for(DateTypeObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDateRemoved(key);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		lock.writeLock().lock();
		try{
			delegate.clear();
			fireDateCleared();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireDateCleared() {
		for(DateTypeObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDateCleared();
		}
	}

	/**
	 * ���ظ�ԭʼ����ģ�͵ļ����ϡ�
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ��ԭʼ����ģ�͵ļ����ϡ�
	 */
	@Override
	public Set<CountDate> keySet() {
		lock.readLock().lock();
		try{
			return delegate.keySet();
		}finally {
			lock.readLock().lock();
		}
	}

	/**
	 * ���ظ�ԭʼ����ģ�͵�ֵ���ϡ�
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ��ԭʼ����ģ�͵�ֵ���ϡ�
	 */
	@Override
	public Collection<DateType> values() {
		lock.readLock().lock();
		try{
			return delegate.values();
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ظ�ԭʼ����ģ�͵���ڼ��ϡ�
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ��ԭʼ����ģ�͵���ڼ��ϡ�
	 */
	@Override
	public Set<java.util.Map.Entry<CountDate, DateType>> entrySet() {
		lock.readLock().lock();
		try{
			return delegate.entrySet();
		}finally {
			lock.readLock().unlock();
		}
	}
	
	@Override
	public void putAll(Map<? extends CountDate, ? extends DateType> m) {
		throw new UnsupportedOperationException("��ģ�Ͳ�֧�ִ˷���");
	}

}

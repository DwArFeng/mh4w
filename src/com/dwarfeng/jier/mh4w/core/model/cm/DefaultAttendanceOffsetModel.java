package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.jier.mh4w.core.model.obv.AttendanceOffsetObverser;

/**
 * Ĭ�Ͽ��ڲ���ģ�͡�
 * <p> ���ڲ���ģ�͵�Ĭ��ʵ�֡�
 * <p> ��ģ���е����ݵĶ�д�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class DefaultAttendanceOffsetModel extends AbstractAttendanceOffesetModel {

	private final Map<String, Double> delegate = new HashMap<>();
	
	/**
	 * ��ʵ����
	 */
	public DefaultAttendanceOffsetModel() {
		this(new HashMap<>());
	}
	
	/**
	 * ��ʵ����
	 * @param m ָ���ĳ�ʼ�����ݡ�
	 * @throws NullPointerException  ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultAttendanceOffsetModel(Map<String, Double> m) {
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
	public Double get(Object key) {
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
	public Double put(String key, Double value) {
		lock.writeLock().lock();
		try{
			boolean aFlag = delegate.containsKey(key);
			if(aFlag){
				Double oldValue = delegate.put(key, value);
				fireWorkNumberChanged(key, oldValue, value);
				return oldValue;
			}else{
				Double d = delegate.put(key, value);
				fireWorkNumberPut(key, value);
				return d;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireWorkNumberPut(String key, Double value) {
		for(AttendanceOffsetObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireWorkNumberPut(key, value);
		}
	}

	private void fireWorkNumberChanged(String key, Double oldValue, Double newValue) {
		for(AttendanceOffsetObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireWorkNumberChanged(key, oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public Double remove(Object key) {
		lock.writeLock().lock();
		try{
			boolean aFlag = delegate.containsKey(key);
			Double d = delegate.remove(key);
			if(aFlag){
				//ֻ�� key ���� CountDate ���󣬲��п��ܱ��Ƴ�����˸�����ת���ǰ�ȫ�ġ�
				fireWorkNumberRemoved((String) key);
			}
			return d;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireWorkNumberRemoved(String key) {
		for(AttendanceOffsetObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireWorkNumberRemoved(key);
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
			fireWorkNumberCleared();
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireWorkNumberCleared() {
		for(AttendanceOffsetObverser obverser : obversers){
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
	public Set<String> keySet() {
		lock.readLock().lock();
		try{
			return delegate.keySet();
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ظ�ԭʼ����ģ�͵�ֵ���ϡ�
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ��ԭʼ����ģ�͵�ֵ���ϡ�
	 */
	@Override
	public Collection<Double> values() {
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
	public Set<java.util.Map.Entry<String, Double>> entrySet() {
		lock.readLock().lock();
		try{
			return delegate.entrySet();
		}finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public void putAll(Map<? extends String, ? extends Double> m) {
		throw new UnsupportedOperationException("��ģ�Ͳ�֧�ִ˷���");
	}

}

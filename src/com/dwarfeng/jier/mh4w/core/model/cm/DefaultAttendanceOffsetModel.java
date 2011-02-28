package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.jier.mh4w.core.model.obv.AttendanceOffsetObverser;

/**
 * 默认考勤补偿模型。
 * <p> 考勤补偿模型的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class DefaultAttendanceOffsetModel extends AbstractAttendanceOffesetModel {

	private final Map<String, Double> delegate = new HashMap<>();
	
	/**
	 * 新实例。
	 */
	public DefaultAttendanceOffsetModel() {
		this(new HashMap<>());
	}
	
	/**
	 * 新实例。
	 * @param m 指定的初始化数据。
	 * @throws NullPointerException  入口参数为 <code>null</code>。
	 */
	public DefaultAttendanceOffsetModel(Map<String, Double> m) {
		Objects.requireNonNull(m, "入口参数 m 不能为 null。");
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
				//只有 key 属于 CountDate 对象，才有可能被移除，因此该类型转换是安全的。
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
	 * 返回该原始考勤模型的键集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 该原始考勤模型的键集合。
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
	 * 返回该原始考勤模型的值集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 该原始考勤模型的值集合。
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
	 * 返回该原始考勤模型的入口集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 该原始考勤模型的入口集合。
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
		throw new UnsupportedOperationException("该模型不支持此方法");
	}

}

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
 * 默认日期类型模型。
 * <p> 日期类型模型的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultDateTypeModel extends AbstractDateTypeModel {

	private final Map<CountDate, DateType> delegate = new HashMap<>();
	
	/**
	 * 新实例。
	 */
	public DefaultDateTypeModel() {
		this(new HashMap<>());
	}
	
	/**
	 * 新实例。
	 * @param m 指定的初始化数据。
	 * @throws NullPointerException  入口参数为 <code>null</code>。
	 */
	public DefaultDateTypeModel(Map<CountDate, DateType> m) {
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
				//只有 key 属于 CountDate 对象，才有可能被移除，因此该类型转换时安全的。
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
	 * 返回该原始考勤模型的键集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 该原始考勤模型的键集合。
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
	 * 返回该原始考勤模型的值集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 该原始考勤模型的值集合。
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
	 * 返回该原始考勤模型的入口集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 该原始考勤模型的入口集合。
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
		throw new UnsupportedOperationException("该模型不支持此方法");
	}

}

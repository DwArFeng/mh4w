package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.jier.mh4w.core.model.struct.Resource;

/**
 * 默认资源模型。
 * <p> 资源模型接口的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultResourceModel extends AbstractResourceModel {
	
	private final Map<String, Resource> delegate = new HashMap<>();
	
	/**
	 * 新实例。
	 */
	public DefaultResourceModel() {
		this(new HashMap<>());
	}
	
	/**
	 * 新实例。
	 * @param map 初始值。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultResourceModel(Map<String, Resource> map) {
		Objects.requireNonNull(map, "入口参数 map 不能为 null。");
		delegate.putAll(map);
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
			return delegate.containsKey(key	);
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
	public Resource get(Object key) {
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
	public Resource put(String key, Resource value) {
		Objects.requireNonNull(key, "入口参数 key 不能为 null。");
		Objects.requireNonNull(value, "入口参数 value 不能为 null。");

		lock.writeLock().lock();
		try{
			return delegate.put(key, value);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public Resource remove(Object key) {
		lock.writeLock().lock();
		try{
			return delegate.remove(key);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends String, ? extends Resource> m) {
		Objects.requireNonNull(m, "入口参数 m 不能为 null。");
		
		lock.writeLock().lock();
		try{
			delegate.putAll(m);
		}finally {
			lock.writeLock().unlock();
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
		}finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 获取模型的键集合。
	 * <p> 注意，该键集合不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的键集合。
	 */
	@Override
	public Set<String> keySet() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(delegate.keySet());
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * 获取模型的值集合。
	 * <p> 注意，该值集合不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的值集合。
	 */
	@Override
	public Collection<Resource> values() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableCollection(delegate.values());
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * 获取模型的入口集合。
	 * <p> 注意，该入口集合不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的入口集合。
	 */
	@Override
	public Set<java.util.Map.Entry<String, Resource>> entrySet() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(delegate.entrySet());
		}finally {
			lock.readLock().unlock();
		}
	}

}

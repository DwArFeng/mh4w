package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.dutil.develop.cfg.ConfigEntry;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.dutil.develop.cfg.ConfigObverser;
import com.dwarfeng.dutil.develop.cfg.DefaultConfigModel;

/**
 * 默认同步配置模型。
 * <p> 配置模型的线程安全的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultSyncConfigModel extends AbstractSyncConfigModel {

	private final ConfigModel delegate = new DefaultConfigModel();
	
	/**
	 * 新实例。
	 */
	public DefaultSyncConfigModel() {
		this(new ConfigEntry[0]);
	}
	
	/**
	 * 新实例。
	 * @param configEntries 指定的配置入口。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 * @throws IllegalArgumentException 配置入口集合中至少一个入口无效。
	 */
	public DefaultSyncConfigModel(ConfigEntry[] configEntries) {
		Objects.requireNonNull(configEntries, "入口参数 configEntries 不能为 null。");
		delegate.addAll(Arrays.asList(configEntries));
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#clear()
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
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#containsKey(com.dwarfeng.dutil.develop.cfg.ConfigKey)
	 */
	@Override
	public boolean containsKey(ConfigKey configKey) {
		lock.readLock().lock();
		try{
			return delegate.containsKey(configKey);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#getCurrentValue(com.dwarfeng.dutil.develop.cfg.ConfigKey)
	 */
	@Override
	public String getCurrentValue(ConfigKey configKey) {
		lock.readLock().lock();
		try{
			return delegate.getCurrentValue(configKey);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#isEmpty()
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

	/**
	 * 返回该模型的键集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的键集合。
	 */
	@Override
	public Set<ConfigKey> keySet() {
		lock.readLock().lock();
		try{
			return delegate.keySet();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#add(com.dwarfeng.dutil.develop.cfg.ConfigEntry)
	 */
	@Override
	public boolean add(ConfigEntry configEntry) {
		lock.writeLock().lock();
		try{
			return delegate.add(configEntry);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<ConfigEntry> configEntries) {
		lock.writeLock().lock();
		try{
			return delegate.addAll(configEntries);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#remove(com.dwarfeng.dutil.develop.cfg.ConfigKey)
	 */
	@Override
	public boolean remove(ConfigKey configKey) {
		lock.writeLock().lock();
		try{
			return delegate.remove(configKey);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<ConfigKey> configKeys) {
		lock.writeLock().lock();
		try{
			return delegate.removeAll(configKeys);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<ConfigKey> configKeys) {
		lock.writeLock().lock();
		try{
			return delegate.retainAll(configKeys);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#size()
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
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#isValueValid(com.dwarfeng.dutil.develop.cfg.ConfigKey, java.lang.String)
	 */
	@Override
	public boolean isValueValid(ConfigKey configKey, String value) {
		lock.readLock().lock();
		try{
			return delegate.isValueValid(configKey, value);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#getValidValue(com.dwarfeng.dutil.develop.cfg.ConfigKey)
	 */
	@Override
	public String getValidValue(ConfigKey configKey) {
		lock.readLock().lock();
		try{
			return delegate.getValidValue(configKey);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#getConfigFirmProps(com.dwarfeng.dutil.develop.cfg.ConfigKey)
	 */
	@Override
	public ConfigFirmProps getConfigFirmProps(ConfigKey configKey) {
		lock.readLock().lock();
		try{
			return delegate.getConfigFirmProps(configKey);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#setConfigFirmProps(com.dwarfeng.dutil.develop.cfg.ConfigKey, com.dwarfeng.dutil.develop.cfg.ConfigFirmProps)
	 */
	@Override
	public boolean setConfigFirmProps(ConfigKey configKey, ConfigFirmProps configFirmProps) {
		lock.writeLock().lock();
		try{
			return delegate.setConfigFirmProps(configKey, configFirmProps);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#setCurrentValue(com.dwarfeng.dutil.develop.cfg.ConfigKey, java.lang.String)
	 */
	@Override
	public boolean setCurrentValue(ConfigKey configKey, String currentValue) {
		lock.writeLock().lock();
		try{
			return delegate.setCurrentValue(configKey, currentValue);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#setAllCurrentValue(java.util.Map)
	 */
	@Override
	public boolean setAllCurrentValue(Map<ConfigKey, String> map) {
		lock.writeLock().lock();
		try{
			return delegate.setAllCurrentValue(map);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#resetCurrentValue(com.dwarfeng.dutil.develop.cfg.ConfigKey)
	 */
	@Override
	public boolean resetCurrentValue(ConfigKey configKey) {
		lock.writeLock().lock();
		try{
			return delegate.resetCurrentValue(configKey);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigModel#resetAllCurrentValue()
	 */
	@Override
	public boolean resetAllCurrentValue() {
		lock.writeLock().lock();
		try{
			return delegate.resetAllCurrentValue();
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<ConfigObverser> getObversers() {
		lock.readLock().lock();
		try{
			return delegate.getObversers();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(ConfigObverser obverser) {
		lock.writeLock().lock();
		try{
			return delegate.addObverser(obverser);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(ConfigObverser obverser) {
		lock.writeLock().lock();
		try{
			return delegate.removeObverser(obverser);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#clearObverser()
	 */
	@Override
	public void clearObverser() {
		lock.writeLock().lock();
		try{
			delegate.clearObverser();
		}finally {
			lock.writeLock().unlock();
		}
	}

}

package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.dwarfeng.jier.mh4w.core.model.obv.MutilangObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangInfo;
import com.dwarfeng.jier.mh4w.core.model.struct.ProcessException;
import com.dwarfeng.jier.mh4w.core.util.Constants;

/**
 * 默认多语言模型。
 * <p> 多语言模型接口的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public final class DefaultMutilangModel extends AbstractMutilangModel {
	
	private final Map<Locale, MutilangInfo> delegate = new HashMap<>();
	
	private Set<String> supportedKeys = new HashSet<>();
	private Locale currentLocale;
	private MutilangInfo defaultMutilangInfo;
	private String defaultValue;

	private final InnerMutilang mutilang = new InnerMutilang();
	
	/**
	 * 新实例。
	 */
	public DefaultMutilangModel(){
		this(null, Constants.getDefaultMutilangInfo(), Constants.getDefaultMissingString());
	}
	
	/**
	 * 新实例。
	 * @param currentLocale 指定的当前语言，可以为 <code>null</code>。
	 * @param defaultMutilangInfo 指定的默认多语言信息。
	 * @param defaultValue 指定的默认文本。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultMutilangModel(Locale currentLocale, MutilangInfo defaultMutilangInfo, String defaultValue){
		Objects.requireNonNull(defaultMutilangInfo, "入口参数 defaultMutilangInfo 不能为 null。");
		Objects.requireNonNull(defaultValue, "入口参数 defaultValue 不能为 null。");
		
		this.currentLocale = currentLocale;
		this.defaultMutilangInfo = defaultMutilangInfo;
		this.defaultValue = defaultValue;
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
	public MutilangInfo get(Object key) {
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
	public MutilangInfo put(Locale key, MutilangInfo value) {
		Objects.requireNonNull(key, "入口参数 key 不能为 null。");
		Objects.requireNonNull(value, "入口参数 value 不能为 null。");
		
		lock.writeLock().lock();
		try{
			boolean changeFlag = containsKey(key);
			MutilangInfo oldValue = get(key);	//Maybe null
			MutilangInfo dejavu = delegate.put(key, value);
			
			if(changeFlag){
				fireEntryChanged(key, oldValue, value);
			}else{
				fireEntryAdded(key, value);
			}
			
			return dejavu;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireEntryAdded(Locale locale, MutilangInfo info) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryAdded(locale, info);
		}
	}

	private void fireEntryChanged(Locale locale, MutilangInfo oldValue, MutilangInfo newValue) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryChanged(locale, oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public MutilangInfo remove(Object key) {
		lock.writeLock().lock();
		try{
			boolean removeFlag = containsKey(key);
			MutilangInfo dejavu = delegate.remove(key);
			if(removeFlag){
				fireEntryRemoved((Locale) key);
			}
			return dejavu;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireEntryRemoved(Locale locale) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireEntryRemoved(locale);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends Locale, ? extends MutilangInfo> m) {
		Objects.requireNonNull(m, "入口参数 m 不能为 null。");
		
		lock.writeLock().lock();
		try{
			for(Map.Entry<? extends Locale, ? extends MutilangInfo> entry : m.entrySet()){
				put(entry.getKey(), entry.getValue());
			}
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
			fireCleared();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCleared() {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCleared();
		}
	}

	/**
	 * 返回该模型的键集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的键集合。
	 */
	@Override
	public Set<Locale> keySet() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(delegate.keySet());
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * 返回该模型的值集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的值集合。
	 */
	@Override
	public Collection<MutilangInfo> values() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableCollection(delegate.values());
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * 返回该模型的入口集合。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 模型的入口集合。
	 */
	@Override
	public Set<java.util.Map.Entry<Locale, MutilangInfo>> entrySet() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(delegate.entrySet());
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.MutilangModel#getSupportedKeys()
	 */
	@Override
	public Set<String> getSupportedKeys() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(supportedKeys)){
				return Collections.unmodifiableSet(new HashSet<>());
			}
			return Collections.unmodifiableSet(supportedKeys);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.MutilangModel#setSupportedKeys(java.util.Set)
	 */
	@Override
	public boolean setSupportedKeys(Set<String> names) {
		Objects.requireNonNull(names, "入口参数 names 不能为 null。");
		
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.supportedKeys, names)) return false;
			Set<String> oldValue = this.supportedKeys;
			this.supportedKeys = names;
			fireSupportedKeysChanged(Collections.unmodifiableSet(oldValue), Collections.unmodifiableSet(names));
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireSupportedKeysChanged(Set<String> oldValue, Set<String> newValue) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireSupportedKeysChanged(oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.MutilangModel#getCurrentLocale()
	 */
	@Override
	public Locale getCurrentLocale() {
		lock.readLock().lock();
		try{
			return this.currentLocale;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.MutilangModel#setCurrentLocale(java.util.Locale)
	 */
	@Override
	public boolean setCurrentLocale(Locale locale) {
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.currentLocale, locale)) return false;
			Locale oldValue = this.currentLocale;
			this.currentLocale = locale;
			fireCurrentLocaleChanged(oldValue, locale);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCurrentLocaleChanged(Locale oldValue, Locale newValue) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCurrentLocaleChanged(oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.MutilangModel#getDefaultMutilangInfo()
	 */
	@Override
	public MutilangInfo getDefaultMutilangInfo() {
		lock.readLock().lock();
		try{
			return this.defaultMutilangInfo;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.MutilangModel#setDefaultMutilangInfo(com.dwarfeng.jier.mh4w.core.model.struct.MutilangInfo)
	 */
	@Override
	public boolean setDefaultMutilangInfo(MutilangInfo mutilangInfo) {
		Objects.requireNonNull(mutilangInfo, "入口参数 mutilangInfo 不能为 null。");
		
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.defaultMutilangInfo, mutilangInfo)) return false;
			MutilangInfo oldValue = this.defaultMutilangInfo;
			this.defaultMutilangInfo = mutilangInfo;
			fireDefaultMutilangMapChanged(oldValue, mutilangInfo);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireDefaultMutilangMapChanged(MutilangInfo oldValue, MutilangInfo newValue) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDefaultMutilangMapChanged(oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.MutilangModel#getDefaultValue()
	 */
	@Override
	public String getDefaultValue() {
		lock.readLock().lock();
		try{
			return this.defaultValue;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.MutilangModel#setDefaultValue(java.lang.String)
	 */
	@Override
	public boolean setDefaultValue(String value) {
		Objects.requireNonNull(value, "入口参数 value 不能为 null。");
		
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.defaultValue, value)) return false;
			String oldValue = this.defaultValue;
			this.defaultValue = value;
			fireDefaultValueChanged(oldValue, value);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireDefaultValueChanged(String oldValue, String newValue) {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDefaultVauleChanged(oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Updateable#update()
	 */
	@Override
	public void update() throws ProcessException {
		lock.writeLock().lock();
		mutilang.mutilangLock.lock();
		try{
			try{
				Set<String> tSupportedKeys = null;
				String tDefaultValue = null;
				Map<String, String> tMutilangMap = null;
				try{
					tSupportedKeys = supportedKeys;
					tDefaultValue = defaultValue;
					tMutilangMap = delegate.getOrDefault(currentLocale, defaultMutilangInfo).getMutilangMap();
				}catch (Exception e) {
					throw new ProcessException("多语言模型更新过程遇到异常，使用上次的设置", e);
				}
				mutilang.supportedKeys = tSupportedKeys;
				mutilang.defaultValue = tDefaultValue;
				mutilang.mutilangMap = tMutilangMap;
			}finally {
				fireUpdated();
			}

		}finally {
			mutilang.mutilangLock.unlock();
			lock.writeLock().unlock();
		}
	}

	private void fireUpdated() {
		for(MutilangObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireUpdated();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.MutilangModel#getMutilang()
	 */
	@Override
	public Mutilang getMutilang() {
		return mutilang;
	}
	
	
	private class InnerMutilang implements Mutilang {
		
		private final Lock mutilangLock = new ReentrantLock();
		
		private Set<String> supportedKeys = new HashSet<>();
		private Map<String, String> mutilangMap = new HashMap<>(); 
		private String defaultValue = "";
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.struct.Mutilang#getString(java.lang.String)
		 */
		@Override
		public String getString(String key) {
			Objects.requireNonNull(key, "入口参数 key 不能为 null。");
			
			mutilangLock.lock();
			try{
				if(! supportedKeys.contains(key)){
					throw new IllegalArgumentException("多语言接口-不支持此键");
				}
				return mutilangMap.getOrDefault(key, defaultValue);
			}finally {
				mutilangLock.unlock();
			}
			
		}

	}
	
}

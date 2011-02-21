package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.jier.mh4w.core.model.obv.JobObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;

public final class DefaultJobModel extends AbstractJobModel {
	
	private final Map<String, Job> jobMap = new HashMap<>();
	
	/**
	 * 新实例。
	 */
	public DefaultJobModel() {
		this(new HashSet<>());
	}
	
	/**
	 * 新实例。
	 * @param c 指定的初始值。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultJobModel(Set<Job> c){
		Objects.requireNonNull(c, "入口参数 c 不能为 null。");
		for(Job job : c){
			notFireAdd(job);
		}
	}
	
	/**
	 * 添加指定的工作，但不通知，也不线程安全。
	 * @param job 指定的工作。
	 */
	private void notFireAdd(Job job){
		if(Objects.isNull(job)) return;
		if(jobMap.containsKey(job.getName())) return;
		
		jobMap.put(job.getName(), job);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.JobModel#addJob(com.dwarfeng.jier.mh4w.core.model.struct.Job)
	 */
	@Override
	public boolean add(Job job) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(job)) return false;
			if(jobMap.containsKey(job.getName())) return false;
			
			jobMap.put(job.getName(), job);
			fireJobAdded(job);
			
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireJobAdded(Job job){
		for(JobObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireJobAdded(job);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.JobModel#remove(java.lang.String)
	 */
	@Override
	public boolean remove(String name) {
		lock.writeLock().lock();
		try{
			if(! jobMap.containsKey(name)) return false;
			Job job = jobMap.remove(name);
			fireJobRemoved(job);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireJobRemoved(Job job) {
		for(JobObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireJobRemoved(job);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.JobModel#size()
	 */
	@Override
	public int size() {
		lock.readLock().lock();
		try{
			return jobMap.size();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.JobModel#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String name) {
		lock.readLock().lock();
		try{
			return jobMap.containsKey(name);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.JobModel#get(java.lang.String)
	 */
	@Override
	public Job get(String name) {
		lock.readLock().lock();
		try{
			return jobMap.get(name);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.JobModel#clear()
	 */
	@Override
	public void clear() {
		lock.writeLock().lock();
		try{
			jobMap.clear();
			fireCleared();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCleared() {
		for(JobObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireJobCleared();
		}
	}

	/**
	 * 返回该工作模型的过程迭代器。
	 * <p> 注意，该迭代器不是线程安全的，如果要实现线程安全，请使模型中提供的读写锁
	 * {@link #getLock()}进行外部同步。
	 * @return 该工作模型的过程迭代器。
	 */
	@Override
	public Iterator<Job> iterator() {
		lock.readLock().lock();
		try{
			return jobMap.values().iterator();
		}finally {
			lock.readLock().unlock();
		}
	}

}

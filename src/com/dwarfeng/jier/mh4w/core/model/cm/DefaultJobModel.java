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
	 * ��ʵ����
	 */
	public DefaultJobModel() {
		this(new HashSet<>());
	}
	
	/**
	 * ��ʵ����
	 * @param c ָ���ĳ�ʼֵ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultJobModel(Set<Job> c){
		Objects.requireNonNull(c, "��ڲ��� c ����Ϊ null��");
		for(Job job : c){
			notFireAdd(job);
		}
	}
	
	/**
	 * ���ָ���Ĺ���������֪ͨ��Ҳ���̰߳�ȫ��
	 * @param job ָ���Ĺ�����
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
	 * ���ظù���ģ�͵Ĺ��̵�������
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return �ù���ģ�͵Ĺ��̵�������
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

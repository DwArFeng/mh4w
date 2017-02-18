package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalWorkticketData;

/**
 * Ĭ��ԭʼ��Ʊģ�͡�
 * <p> ԭʼ��Ʊģ�͵�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultOriginalWorkticketDataModel extends AbstractOriginalWorkticketModel {
	
	private final List<OriginalWorkticketData> delegate = new ArrayList<>();

	/**
	 * ��ʵ����
	 */
	public DefaultOriginalWorkticketDataModel() {
		this(new ArrayList<>());
	}
	
	/**
	 * ��ʵ����
	 * @param c ָ���ĳ�ʼ�����ݡ�
	 * @throws NullPointerException ��ڲ���Ϊ null��
	 */
	public DefaultOriginalWorkticketDataModel(Collection<OriginalWorkticketData> c) {
		Objects.requireNonNull(c, "��ڲ��� c ����Ϊ null��");
		delegate.addAll(c);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.List#size()
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
	 * @see java.util.List#isEmpty()
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
	 * @see java.util.List#contains(java.lang.Object)
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
	 * ���ظ�ԭʼ��Ʊģ�͵Ĺ��̵�������
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ��ԭʼ��Ʊģ�͵Ĺ��̵�������
	 */
	@Override
	public Iterator<OriginalWorkticketData> iterator() {
		lock.readLock().lock();
		try{
			return delegate.iterator();
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#toArray()
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

	/*
	 * (non-Javadoc)
	 * @see java.util.List#toArray(java.lang.Object[])
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
	 * @see java.util.List#add(java.lang.Object)
	 */
	@Override
	public boolean add(OriginalWorkticketData e) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(e)) return false;
			
			return notLockAdd(e);
		}finally {
			lock.writeLock().unlock();
		}
	}

	private boolean notLockAdd(OriginalWorkticketData e) {
		int index = delegate.size();
		boolean aFlag = delegate.add(e);
		if(aFlag) fireAdded(index, e);
		return aFlag;
	}
	
	private void fireAdded(int index, OriginalWorkticketData e) {
		for(ListOperateObverser<OriginalWorkticketData> obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireAdded(index, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		lock.writeLock().lock();
		try{
			return notLockRemove(o);
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private boolean notLockRemove(Object o) {
		int index = delegate.indexOf(o);
		boolean aFlag = delegate.remove(o);
		if(aFlag) fireRemoved(index);
		return aFlag;
	}

	private void fireRemoved(int index) {
		for(ListOperateObverser<OriginalWorkticketData> obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireRemoved(index);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		lock.readLock().lock();
		try{
			return delegate.containsAll(c);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#clear()
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
		for(ListOperateObverser<OriginalWorkticketData> obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCleared();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#get(int)
	 */
	@Override
	public OriginalWorkticketData get(int index) {
		lock.readLock().lock();
		try{
			return delegate.get(index);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	@Override
	public OriginalWorkticketData set(int index, OriginalWorkticketData element) {
		Objects.requireNonNull(element, "��ڲ��� element ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			OriginalWorkticketData oldValue = delegate.set(index, element);
			fireChanged(index, oldValue, element);
			return oldValue;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireChanged(int index, OriginalWorkticketData oldValue, OriginalWorkticketData newValue) {
		for(ListOperateObverser<OriginalWorkticketData> obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireChanged(index, oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, OriginalWorkticketData element) {
		Objects.requireNonNull(element, "��ڲ��� element ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			notLockAdd(index, element);
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void notLockAdd(int index, OriginalWorkticketData element) {
		delegate.add(index, element);
		fireAdded(index, element);
	}


	/*
	 * (non-Javadoc)
	 * @see java.util.List#remove(int)
	 */
	@Override
	public OriginalWorkticketData remove(int index) {
		lock.writeLock().lock();
		try{
			return notLockRemove(index);
		}finally {
			lock.writeLock().unlock();
		}
	}

	private OriginalWorkticketData notLockRemove(int index){
		OriginalWorkticketData originalAttendanceData = delegate.remove(index);
		fireRemoved(index);
		return originalAttendanceData;
	}

	
	/*
	 * (non-Javadoc)
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	@Override
	public int indexOf(Object o) {
		lock.readLock().lock();
		try{
			return delegate.indexOf(o);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	@Override
	public int lastIndexOf(Object o) {
		lock.readLock().lock();
		try{
			return delegate.lastIndexOf(o);
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ظ�ԭʼ��Ʊģ�͵Ĺ��̵�������
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ��ԭʼ��Ʊģ�͵Ĺ��̵�������
	 */
	@Override
	public ListIterator<OriginalWorkticketData> listIterator() {
		lock.readLock().lock();
		try{
			return delegate.listIterator();
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ظ�ԭʼ��Ʊģ�͵Ĺ��̵�������
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @param index ��ʼ��λ�á�
	 * @return ��ԭʼ��Ʊģ�͵Ĺ��̵�������
	 */
	@Override
	public ListIterator<OriginalWorkticketData> listIterator(int index) {
		lock.readLock().lock();
		try{
			return delegate.listIterator(index);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#subList(int, int)
	 */
	@Override
	public List<OriginalWorkticketData> subList(int fromIndex, int toIndex) {
		lock.readLock().lock();
		try{
			return delegate.subList(fromIndex, toIndex);
		}finally {
			lock.readLock().unlock();
		}
	}
	
	@Override
	public boolean addAll(Collection<? extends OriginalWorkticketData> c) {
		throw new UnsupportedOperationException("��֧�ִ˷���");
	}
	@Override
	public boolean addAll(int index, Collection<? extends OriginalWorkticketData> c) {
		throw new UnsupportedOperationException("��֧�ִ˷���");
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("��֧�ִ˷���");
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("��֧�ִ˷���");
	}

}

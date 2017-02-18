package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateObverser;

/**
 * Ĭ���б�ģ�͡�
 * <p> �б�ģ�͵�Ĭ��ʵ�֡�
 * <p> ��ģ���е����ݵĶ�д�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultDataListModel<E> extends AbstarctDataListModel<E> {
	
	private final List<E> delegate = new ArrayList<>();

	/**
	 * ��ʵ����
	 */
	public DefaultDataListModel() {
		this(new ArrayList<>());
	}
	
	/**
	 * ��ʵ����
	 * @param c ָ���ĳ�ʼ�����ݡ�
	 * @throws NullPointerException ��ڲ���Ϊ null��
	 */
	public DefaultDataListModel(Collection<E> c) {
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
	 * ���ظ�ԭʼ����ģ�͵Ĺ��̵�������
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ��ԭʼ����ģ�͵Ĺ��̵�������
	 */
	@Override
	public Iterator<E> iterator() {
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
	public boolean add(E e) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(e)) return false;
			
			return notLockAdd(e);
		}finally {
			lock.writeLock().unlock();
		}
	}

	private boolean notLockAdd(E e) {
		int index = delegate.size();
		boolean aFlag = delegate.add(e);
		if(aFlag) fireAdded(index, e);
		return aFlag;
	}

	private void fireAdded(int index, E e) {
		for(ListOperateObverser<E> obverser : obversers){
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
		for(ListOperateObverser<E> obverser : obversers){
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
		for(ListOperateObverser<E> obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCleared();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#get(int)
	 */
	@Override
	public E get(int index) {
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
	public E set(int index, E element) {
		Objects.requireNonNull(element, "��ڲ��� element ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			E oldValue = delegate.set(index, element);
			fireChanged(index, oldValue, element);
			return oldValue;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireChanged(int index, E oldValue, E newValue) {
		for(ListOperateObverser<E> obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireChanged(index, oldValue, newValue);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, E element) {
		Objects.requireNonNull(element, "��ڲ��� element ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			notLockAdd(index, element);
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void notLockAdd(int index, E element) {
		delegate.add(index, element);
		fireAdded(index, element);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#remove(int)
	 */
	@Override
	public E remove(int index) {
		lock.writeLock().lock();
		try{
			return notLockRemove(index);
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private E notLockRemove(int index){
		E originalAttendanceData = delegate.remove(index);
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
	 * ���ظ�ԭʼ����ģ�͵Ĺ��̵�������
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return ��ԭʼ����ģ�͵Ĺ��̵�������
	 */
	@Override
	public ListIterator<E> listIterator() {
		lock.readLock().lock();
		try{
			return delegate.listIterator();
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ظ�ԭʼ����ģ�͵Ĺ��̵�������
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @param index ��ʼ��λ�á�
	 * @return ��ԭʼ����ģ�͵Ĺ��̵�������
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
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
	public List<E> subList(int fromIndex, int toIndex) {
		lock.readLock().lock();
		try{
			return delegate.subList(fromIndex, toIndex);
		}finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException("��֧�ִ˷���");
	}
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
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

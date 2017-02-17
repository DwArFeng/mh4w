package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalAttendanceData;

/**
 * Ĭ��ԭʼ����ģ�͡�
 * <p> ԭʼ����ģ�͵�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultOriginalAttendanceModel extends AbstractOriginalAttendanceModel {
	
	private final List<OriginalAttendanceData> delegate = new ArrayList<>();

	/**
	 * ��ʵ����
	 */
	public DefaultOriginalAttendanceModel() {
		this(new ArrayList<>());
	}
	
	/**
	 * ��ʵ����
	 * @param c ָ���ĳ�ʼ�����ݡ�
	 * @throws NullPointerException ��ڲ���Ϊ null��
	 */
	public DefaultOriginalAttendanceModel(Collection<OriginalAttendanceData> c) {
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
	public Iterator<OriginalAttendanceData> iterator() {
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
	public boolean add(OriginalAttendanceData e) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(e)) return false;
			
			return notLockAdd(e);
		}finally {
			lock.writeLock().unlock();
		}
	}

	private boolean notLockAdd(OriginalAttendanceData e) {
		int index = delegate.size();
		boolean aFlag = delegate.add(e);
		if(aFlag) fireAdded(index, e);
		return aFlag;
	}

	private void fireAdded(int index, OriginalAttendanceData e) {
		for(ListOperateObverser<OriginalAttendanceData> obverser : obversers){
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
		for(ListOperateObverser<OriginalAttendanceData> obverser : obversers){
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
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends OriginalAttendanceData> c) {
		lock.writeLock().lock();
		try{
			boolean aFlag = false;
			for(OriginalAttendanceData originalAttendanceData : c){
				if(notLockAdd(originalAttendanceData)) aFlag = true;
			}
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
	}

	
	@Override
	public boolean addAll(int index, Collection<? extends OriginalAttendanceData> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
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
		for(ListOperateObverser<OriginalAttendanceData> obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCleared();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#get(int)
	 */
	@Override
	public OriginalAttendanceData get(int index) {
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
	public OriginalAttendanceData set(int index, OriginalAttendanceData element) {
		Objects.requireNonNull(element, "��ڲ��� element ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			OriginalAttendanceData oldValue = delegate.set(index, element);
			fireChanged(index, oldValue, element);
			return oldValue;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireChanged(int index, OriginalAttendanceData oldValue, OriginalAttendanceData newValue) {
		for(ListOperateObverser<OriginalAttendanceData> obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireChanged(index, oldValue, newValue);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, OriginalAttendanceData element) {
		Objects.requireNonNull(element, "��ڲ��� element ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			notLockAdd(index, element);
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void notLockAdd(int index, OriginalAttendanceData element) {
		delegate.add(index, element);
		fireAdded(index, element);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#remove(int)
	 */
	@Override
	public OriginalAttendanceData remove(int index) {
		lock.writeLock().lock();
		try{
			return notLockRemove(index);
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private OriginalAttendanceData notLockRemove(int index){
		OriginalAttendanceData originalAttendanceData = delegate.remove(index);
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
	public ListIterator<OriginalAttendanceData> listIterator() {
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
	public ListIterator<OriginalAttendanceData> listIterator(int index) {
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
	public List<OriginalAttendanceData> subList(int fromIndex, int toIndex) {
		lock.readLock().lock();
		try{
			return delegate.subList(fromIndex, toIndex);
		}finally {
			lock.readLock().unlock();
		}
	}

}

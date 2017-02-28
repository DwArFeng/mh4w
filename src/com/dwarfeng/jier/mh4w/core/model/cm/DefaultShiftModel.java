package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.jier.mh4w.core.model.obv.ShiftObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Shift;

/**
 * Ĭ�ϰ��ģ�͡�
 * <p> ���ģ�͵�Ĭ��ʵ�֡�
 * <p> ��ģ���е����ݵĶ�д�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class DefaultShiftModel extends AbstractShiftModel {
	
	private final Map<String, Shift> shiftMap = new LinkedHashMap<>();
	
	/**
	 * ��ʵ����
	 */
	public DefaultShiftModel() {
		this(new HashSet<>());
	}
	
	/**
	 * ��ʵ����
	 * @param c ָ���ĳ�ʼֵ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultShiftModel(Set<Shift> c){
		Objects.requireNonNull(c, "��ڲ��� c ����Ϊ null��");
		for(Shift shift : c){
			notFireAdd(shift);
		}
	}
	
	/**
	 * ���ָ���İ�Σ�����֪ͨ��Ҳ���̰߳�ȫ��
	 * @param shift ָ���İ�Ρ�
	 */
	private void notFireAdd(Shift shift){
		if(Objects.isNull(shift)) return;
		if(shiftMap.containsKey(shift.getName())) return;
		
		shiftMap.put(shift.getName(), shift);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel#addShift(com.dwarfeng.jier.mh4w.core.model.struct.Shift)
	 */
	@Override
	public boolean add(Shift shift) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(shift)) return false;
			if(shiftMap.containsKey(shift.getName())) return false;
			
			shiftMap.put(shift.getName(), shift);
			fireShiftAdded(shift);
			
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireShiftAdded(Shift shift){
		for(ShiftObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireShiftAdded(shift);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel#remove(java.lang.String)
	 */
	@Override
	public boolean remove(String name) {
		lock.writeLock().lock();
		try{
			if(! shiftMap.containsKey(name)) return false;
			Shift shift = shiftMap.remove(name);
			fireShiftRemoved(shift);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireShiftRemoved(Shift shift) {
		for(ShiftObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireShiftRemoved(shift);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel#size()
	 */
	@Override
	public int size() {
		lock.readLock().lock();
		try{
			return shiftMap.size();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String name) {
		lock.readLock().lock();
		try{
			return shiftMap.containsKey(name);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel#get(java.lang.String)
	 */
	@Override
	public Shift get(String name) {
		lock.readLock().lock();
		try{
			return shiftMap.get(name);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel#clear()
	 */
	@Override
	public void clear() {
		lock.writeLock().lock();
		try{
			shiftMap.clear();
			fireCleared();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCleared() {
		for(ShiftObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireShiftCleared();
		}
	}

	/**
	 * ���ظð��ģ�͵Ĺ��̵�������
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return �ð��ģ�͵Ĺ��̵�������
	 */
	@Override
	public Iterator<Shift> iterator() {
		lock.readLock().lock();
		try{
			return shiftMap.values().iterator();
		}finally {
			lock.readLock().unlock();
		}
	}

}

package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.eum.CountState;
import com.dwarfeng.jier.mh4w.core.model.obv.StateObverser;

/**
 * Ĭ��״̬ģ�͡�
 * <p> ״̬ģ�͵�Ĭ��ʵ�֡�
 * <p> ��ģ���е����ݵĶ�д�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public final class DefaultStateModel extends AbstractStateModel {
	
	private boolean readyForCount;
	private CountState countState;
	private boolean countResultOutdated;

	public DefaultStateModel() {
		this(false, CountState.NOT_START, false);
	}
	
	/**
	 * ��ʵ����
	 * @param readyForCount �Ƿ�׼����ͳ���ˡ�
	 * @param countState ͳ�Ƶ�״̬��
	 * @param countResultOutdated ͳ�ƵĽ���Ƿ��ʱ��
	 */
	public DefaultStateModel(boolean readyForCount, CountState countState, boolean countResultOutdated) {
		Objects.requireNonNull(countState, "��ڲ��� countState ����Ϊ null��");
		
		this.readyForCount = readyForCount;
		this.countState = countState;
		this.countResultOutdated = countResultOutdated;
	}



	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.StateModel#isReadyForCount()
	 */
	@Override
	public boolean isReadyForCount() {
		lock.readLock().lock();
		try{
			return readyForCount;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.StateModel#setReadyForCount(boolean)
	 */
	@Override
	public void setReadyForCount(boolean aFlag) {
		lock.writeLock().lock();
		try{
			if(readyForCount != aFlag){
				readyForCount = aFlag;
				fireReadyForCountChanged(aFlag);
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireReadyForCountChanged(boolean newValue) {
		for(StateObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireReadyForCountChanged(newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.StateModel#getCountState()
	 */
	@Override
	public CountState getCountState() {
		lock.readLock().lock();
		try{
			return countState;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.StateModel#setCountState(com.dwarfeng.jier.mh4w.core.model.eum.CountState)
	 */
	@Override
	public boolean setCountState(CountState countState) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(countState)) return false;
			if(countState.equals(this.countState)) return false;
			CountState oldValue = this.countState;
			this.countState = countState;
			fireCountStateChanged(oldValue, countState);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCountStateChanged(CountState oldValue, CountState newValue) {
		for(StateObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCountStateChanged(oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.StateModel#isCountResultOutdated()
	 */
	@Override
	public boolean isCountResultOutdated() {
		lock.readLock().lock();
		try{
			return countResultOutdated;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.StateModel#setCountResultOutdated(boolean)
	 */
	@Override
	public void setCountResultOutdated(boolean aFlag) {
		lock.writeLock().lock();
		try{
			if(countResultOutdated != aFlag){
				countResultOutdated = aFlag;
				fireCountResultOutdated(aFlag);
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCountResultOutdated(boolean newValue) {
		for(StateObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCountResultOutdatedChanged(newValue);
		}
	}

}

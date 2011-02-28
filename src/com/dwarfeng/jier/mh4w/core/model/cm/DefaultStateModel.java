package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.eum.CountState;
import com.dwarfeng.jier.mh4w.core.model.obv.StateObverser;

/**
 * 默认状态模型。
 * <p> 状态模型的默认实现。
 * <p> 该模型中的数据的读写均是线程安全的。
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
	 * 新实例。
	 * @param readyForCount 是否准备好统计了。
	 * @param countState 统计的状态。
	 * @param countResultOutdated 统计的结果是否过时。
	 */
	public DefaultStateModel(boolean readyForCount, CountState countState, boolean countResultOutdated) {
		Objects.requireNonNull(countState, "入口参数 countState 不能为 null。");
		
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

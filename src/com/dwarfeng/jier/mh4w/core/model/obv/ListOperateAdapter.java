package com.dwarfeng.jier.mh4w.core.model.obv;

/**
 * �б�����۲�����������
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public abstract class ListOperateAdapter<T> implements ListOperateObverser<T>{

	@Override
	public void fireAdded(int index, T value) {}
	@Override
	public void fireChanged(int index, T oldValue, T newValue) {}
	@Override
	public void fireRemoved(int index) {}
	@Override
	public void fireCleared() {}
	
}

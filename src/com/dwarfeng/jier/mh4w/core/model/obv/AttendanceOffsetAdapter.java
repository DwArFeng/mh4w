package com.dwarfeng.jier.mh4w.core.model.obv;

/**
 * ���ڲ���ģ�͹۲�����������
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public abstract class AttendanceOffsetAdapter implements AttendanceOffsetObverser {

	@Override
	public void fireWorkNumberPut(String key, Double value) {}
	@Override
	public void fireWorkNumberRemoved(String key) {}
	@Override
	public void fireWorkNumberChanged(String key, Double oldValue, Double newValue) {}
	@Override
	public void fireDateCleared() {}
	
}

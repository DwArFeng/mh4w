package com.dwarfeng.jier.mh4w.core.model.obv;

import java.io.File;

/**
 * �ļ�ѡ��ģ�͹۲�����������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class FileSelectAdapter implements FileSelectObverser{

	@Override
	public void fireAttendanceFileChanged(File oldValue, File newValue) {}
	@Override
	public void fireWorkticketFileChanged(File oldValue, File newValue) {}
	
}
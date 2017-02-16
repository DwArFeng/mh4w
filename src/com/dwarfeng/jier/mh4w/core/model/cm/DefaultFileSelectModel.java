package com.dwarfeng.jier.mh4w.core.model.cm;

import java.io.File;
import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.obv.FileSelectObverser;

/**
 * Ĭ���ļ�ѡ��ģ�͡�
 * <p> �ļ�ѡ��ģ�͵�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultFileSelectModel extends AbstractFileSelectModel{
	
	private File attendanceFile;
	private File workticketFile;
	
	/**
	 * ��ʵ����
	 */
	public DefaultFileSelectModel() {
		this(null, null);
	}
	
	/**
	 * ��ʵ����
	 * @param attendanceFile ָ���ĳ����ļ���
	 * @param workticketFile ָ���Ĺ�Ʊ�ļ���
	 */
	public DefaultFileSelectModel(File attendanceFile, File workticketFile) {
		this.attendanceFile = attendanceFile;
		this.workticketFile = workticketFile;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.FileSelectModel#getAttendanceFile()
	 */
	@Override
	public File getAttendanceFile() {
		lock.readLock().lock();
		try{
			return attendanceFile;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.FileSelectModel#setAttendanceFile(java.io.File)
	 */
	@Override
	public boolean setAttendanceFile(File file) {
		lock.writeLock().lock();
		try{
			if(Objects.equals(attendanceFile, file)) return false;
			File oldValue = attendanceFile;
			attendanceFile = file;
			fireAttendanceFileChanged(oldValue, file);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireAttendanceFileChanged(File oldValue, File newValue) {
		for(FileSelectObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireAttendanceFileChanged(oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.FileSelectModel#getWorkticketFile()
	 */
	@Override
	public File getWorkticketFile() {
		lock.readLock().lock();
		try{
			return workticketFile;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.FileSelectModel#setWorkticketFile(java.io.File)
	 */
	@Override
	public boolean setWorkticketFile(File file) {
		lock.writeLock().lock();
		try{
			if(Objects.equals(workticketFile, file)) return false;
			File oldValue = workticketFile;
			workticketFile = file;
			fireWorkticketFileChanged(oldValue, file);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireWorkticketFileChanged(File oldValue, File newValue) {
		for(FileSelectObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireWorkticketFileChanged(oldValue, newValue);
		}
	}

}

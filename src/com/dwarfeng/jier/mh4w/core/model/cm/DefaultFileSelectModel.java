package com.dwarfeng.jier.mh4w.core.model.cm;

import java.io.File;
import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.obv.FileSelectObverser;

/**
 * 默认文件选择模型。
 * <p> 文件选择模型的默认实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultFileSelectModel extends AbstractFileSelectModel{
	
	private File attendanceFile;
	private File workticketFile;
	
	/**
	 * 新实例。
	 */
	public DefaultFileSelectModel() {
		this(null, null);
	}
	
	/**
	 * 新实例。
	 * @param attendanceFile 指定的出勤文件。
	 * @param workticketFile 指定的工票文件。
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

package com.dwarfeng.jier.mh4w.core.model.cm;

import java.io.File;
import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.obv.FileSelectorObverser;

/**
 * 默认文件选择器模型。
 * <p> 文件选择器模型的默认实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultFileSelectorModel extends AbstractFileSelectorModel{
	
	private File attendanceFile;
	private File workticketFile;
	private boolean readyFlag;
	
	/**
	 * 新实例。
	 */
	public DefaultFileSelectorModel() {
		this(null, null);
	}
	
	/**
	 * 新实例。
	 * @param attendanceFile 指定的出勤文件。
	 * @param workticketFile 指定的工票文件。
	 */
	public DefaultFileSelectorModel(File attendanceFile, File workticketFile) {
		this.attendanceFile = attendanceFile;
		this.workticketFile = workticketFile;
		if(Objects.nonNull(attendanceFile) && Objects.nonNull(workticketFile)){
			readyFlag = true;
		}else{
			readyFlag = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.FileSelectorModel#getAttendanceFile()
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
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.FileSelectorModel#setAttendanceFile(java.io.File)
	 */
	@Override
	public boolean setAttendanceFile(File file) {
		lock.writeLock().lock();
		try{
			if(Objects.equals(attendanceFile, file)) return false;
			File oldValue = attendanceFile;
			attendanceFile = file;
			fireAttendanceFileChanged(oldValue, file);
			checkReady();
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireAttendanceFileChanged(File oldValue, File newValue) {
		for(FileSelectorObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireAttendanceFileChanged(oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.FileSelectorModel#getWorkticketFile()
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
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.FileSelectorModel#setWorkticketFile(java.io.File)
	 */
	@Override
	public boolean setWorkticketFile(File file) {
		lock.writeLock().lock();
		try{
			if(Objects.equals(workticketFile, file)) return false;
			File oldValue = workticketFile;
			workticketFile = file;
			fireWorkticketFileChanged(oldValue, file);
			checkReady();
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireWorkticketFileChanged(File oldValue, File newValue) {
		for(FileSelectorObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireWorkticketFileChanged(oldValue, newValue);
		}
	}
	
	private void checkReady(){
		boolean readyFlag = Objects.nonNull(attendanceFile) && Objects.nonNull(workticketFile);
		if(this.readyFlag != readyFlag){
			this.readyFlag = readyFlag;
			fireReadyChanged(readyFlag);
		}
	}

	private void fireReadyChanged(boolean readyFlag) {
		for(FileSelectorObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireReadyChanged(readyFlag);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.FileSelectorModel#isReady()
	 */
	@Override
	public boolean isReady() {
		lock.readLock().lock();
		try{
			return readyFlag;
		}finally {
			lock.readLock().unlock();
		}
	}

}

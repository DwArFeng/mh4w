package com.dwarfeng.jier.mh4w.core.view.ctrl;

import java.awt.Point;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.view.gui.AttrFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.DetailFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.MainFrame;

/**
 * 抽象主界面控制器。
 * <p> 主界面控制器的抽象实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class AbstractGuiController implements GuiController {
	
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	protected MainFrame mainFrame = null;
	protected DetailFrame detailFrame = null;
	protected AttrFrame attrFrame = null;
	
	/* 
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe#getLock()
	 */
	@Override
	public ReadWriteLock getLock() {
		return lock;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#newMainFrame()
	 */
	@Override
	public boolean newMainFrame() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(mainFrame)){
				mainFrame = newMainFrameImpl();
				return Objects.nonNull(mainFrame);
			}else{
				return false;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * 新建主面板的实现方法。
	 * <p> 该方法已经在调用方法处上锁，不用单独上锁。
	 * @return 新的主界面。
	 */
	 protected abstract MainFrame newMainFrameImpl();

	/* 
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#disposeMainFrame()
	 */
	@Override
	public boolean disposeMainFrame() {
		lock.writeLock().lock();
		try{
			if(Objects.nonNull(mainFrame)){
				if(disposeMainFrameImpl()){
					mainFrame = null;
					return true;
				}else{
					return false;
				}
			}else {
				return false;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 释放主面板的实现方法。
	 * <p> 该方法已在调用方法处上锁，不用单独上锁。
	 * @return 是否释放成功。
	 */
	protected abstract boolean disposeMainFrameImpl();

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#hasMainFrame()
	 */
	@Override
	public boolean hasMainFrame() {
		lock.readLock().lock();
		try{
			return Objects.nonNull(mainFrame);
		}finally {
			lock.readLock().unlock();
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#getMainFrameVisible()
	 */
	@Override
	public boolean getMainFrameVisible() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(mainFrame)) return false;
			return mainFrame.isVisible();
		}finally {
			lock.readLock().unlock();
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#setMainFrameVisible(boolean)
	 */
	@Override
	public boolean setMainFrameVisible(boolean aFlag) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(mainFrame)) return false;
			mainFrame.setVisible(aFlag);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#getMainFrameMutilang()
	 */
	@Override
	public Mutilang getMainFrameMutilang() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(mainFrame)) return null;
			return mainFrame.getMutilang();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#setMainFrameMutilang(com.dwarfeng.jier.mh4w.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setMainFrameMutilang(Mutilang mutilang) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(mainFrame)) return false;
			return mainFrame.setMutilang(mutilang);
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#askFile()
	 */
	@Override
	public File[] askFile(File directory, FileFilter[] fileFilters, boolean acceptAllFileFilter, boolean mutiSelectionEnabled,
			int fileSelectionMode) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(mainFrame)) return null;
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(directory);
			chooser.setAcceptAllFileFilterUsed(acceptAllFileFilter);
			chooser.setMultiSelectionEnabled(mutiSelectionEnabled);
			chooser.setFileSelectionMode(fileSelectionMode);
			if(Objects.nonNull(fileFilters)){
				for(FileFilter fileFilter : fileFilters){
					chooser.setFileFilter(fileFilter);
				}
			}

			int status = chooser.showOpenDialog(mainFrame);
			if(status == JFileChooser.APPROVE_OPTION){
				if(mutiSelectionEnabled){
					return chooser.getSelectedFiles();
				}else{
					return new File[]{chooser.getSelectedFile()};
				}
			}else{
				return new File[0];
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#attendanceClickUnlock()
	 */
	@Override
	public boolean attendanceClickUnlock() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(mainFrame)) return false;
			mainFrame.attendanceClickUnlock();
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#workticketClickUnlock()
	 */
	@Override
	public boolean workticketClickUnlock() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(mainFrame)) return false;
			mainFrame.workticketClickUnlock();
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#setDetailButtonSelect(boolean, boolean)
	 */
	@Override
	public boolean setDetailButtonSelect(boolean value, boolean isAdjusting) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(mainFrame)) return false;
			mainFrame.setDetailButtonSelect(value, isAdjusting);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 *  (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#newDetailFrame()
	 */
	@Override
	public boolean newDetailFrame() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(detailFrame)){
				detailFrame = newDetailFrameImpl();
				return Objects.nonNull(detailFrame);
			}else{
				return false;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 新建详细面板的实现方法。
	 *  <p> 该方法已经在调用方法处上锁，不用单独上锁。
	 * @return 新的详细界面。
	 */
	protected abstract DetailFrame newDetailFrameImpl();

	/* 
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#disposeDetialFrame()
	 */
	@Override
	public boolean disposeDetialFrame() {
		lock.writeLock().lock();
		try{
			if(Objects.nonNull(detailFrame)){
				if(disposeDetailFrameImpl()){
					detailFrame = null;
					return true;
				}else{
					return false;
				}
			}else {
				return false;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 释放详细面板的实现方法。
	 * <p> 该方法已在调用方法处上锁，不用单独上锁。
	 * @return 是否释放成功。
	 */
	protected abstract boolean disposeDetailFrameImpl();

	/* 
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#hasDetailFrame()
	 */
	@Override
	public boolean hasDetailFrame() {
		lock.readLock().lock();
		try{
			return Objects.nonNull(detailFrame);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 *  (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#getDetailFrameVisible()
	 */
	@Override
	public boolean getDetailFrameVisible() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(detailFrame)) return false;
			return detailFrame.isVisible();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 *  (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#setDetailFrameVisible(boolean)
	 */
	@Override
	public boolean setDetailFrameVisible(boolean aFlag) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(detailFrame)) return false;
			if(aFlag){
				Point dest = new Point(0,0);
				SwingUtilities.convertPointToScreen(dest, mainFrame);
				detailFrame.setBounds(dest.x + mainFrame.getWidth(), dest.y, 700, 800);
			}
			detailFrame.setVisible(aFlag);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#newAttrFrame()
	 */
	@Override
	public boolean newAttrFrame() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(attrFrame)){
				attrFrame = newAttrFrameImpl();
				return Objects.nonNull(attrFrame);
			}else{
				return false;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * 新建属性面板的实现方法。
	 * <p> 该方法已经在调用方法处上锁，不用单独上锁。
	 * @return 新的主界面。
	 */
	 protected abstract AttrFrame newAttrFrameImpl();

	 /*
	  * (non-Javadoc)
	  * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#disposeAttrFrame()
	  */
	@Override
	public boolean disposeAttrFrame() {
		lock.writeLock().lock();
		try{
			if(Objects.nonNull(attrFrame)){
				if(disposeAttrFrameImpl()){
					attrFrame = null;
					return true;
				}else{
					return false;
				}
			}else {
				return false;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 释放属性面板的实现方法。
	 * <p> 该方法已在调用方法处上锁，不用单独上锁。
	 * @return 是否释放成功。
	 */
	protected abstract boolean disposeAttrFrameImpl();

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#hasAttrFrame()
	 */
	@Override
	public boolean hasAttrFrame() {
		lock.readLock().lock();
		try{
			return Objects.nonNull(attrFrame);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#getAttrFrameVisible()
	 */
	@Override
	public boolean getAttrFrameVisible() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(attrFrame)) return false;
			return attrFrame.isVisible();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#setAttrFrameVisible(boolean)
	 */
	@Override
	public boolean setAttrFrameVisible(boolean aFlag) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(attrFrame)) return false;
			attrFrame.setVisible(aFlag);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
}

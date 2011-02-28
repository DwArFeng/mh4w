package com.dwarfeng.jier.mh4w.core.view.ctrl;

import java.awt.Component;
import java.awt.Point;
import java.io.File;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import com.dwarfeng.dutil.basic.gui.swing.SwingUtil;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.view.gui.AttrFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.DateTypeFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.DetailFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.FailFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.MainFrame;

/**
 * 抽象主界面控制器。
 * <p> 主界面控制器的抽象实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class AbstractGuiController implements GuiController {
	
	/**同步锁*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	/**控制器中的主界面*/
	protected MainFrame mainFrame = null;
	/**控制器中的详细界面*/
	protected DetailFrame detailFrame = null;
	/**控制器中的属性界面*/
	protected AttrFrame attrFrame = null;
	/**控制器中的失败界面*/
	protected FailFrame failFrame = null;
	/**控制器中的日期类型界面*/
	protected DateTypeFrame dateTypeFrame = null;
	
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
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#updateMainFrameMutilang()
	 */
	@Override
	public boolean updateMainFrameMutilang() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(mainFrame)) return false;
			mainFrame.updateMutilang();
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#askFile4Open(java.io.File, javax.swing.filechooser.FileFilter[], boolean, boolean, int)
	 */
	@Override
	public File[] askFile4Open(File directory, FileFilter[] fileFilters, boolean acceptAllFileFilter, 
			boolean mutiSelectionEnabled,int fileSelectionMode, Locale locale) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(mainFrame)) return null;
			JFileChooser chooser = new JFileChooser();
			SwingUtil.setJFileChooserLocal(chooser, locale == null ? Locale.getDefault() : locale);
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
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#askFile4Save(java.io.File, javax.swing.filechooser.FileFilter[], boolean)
	 */
	@Override
	public File askFile4Save(File directory, FileFilter[] fileFilters, boolean acceptAllFileFilter, 
			String defaultFileExtension, Locale locale) {
		lock.writeLock().lock();
		try{
			Component component = null;
			
			if(Objects.nonNull(detailFrame) || detailFrame.isVisible()){
				component = detailFrame;
			}else if(Objects.nonNull(mainFrame) || mainFrame.isVisible()){
				component = mainFrame;
			}
			
			JFileChooser chooser = new JFileChooser();
			SwingUtil.setJFileChooserLocal(chooser, locale == null ? Locale.getDefault() : locale);
			chooser.setCurrentDirectory(directory);
			chooser.setAcceptAllFileFilterUsed(acceptAllFileFilter);
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if(Objects.nonNull(fileFilters)){
				for(FileFilter fileFilter : fileFilters){
					chooser.setFileFilter(fileFilter);
				}
			}

			int status = chooser.showSaveDialog(component);
			if(status == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				if(Objects.nonNull(defaultFileExtension) && file.getName().indexOf(".") < 0){
					String fileName = file.getName() + (defaultFileExtension.startsWith(".") ? defaultFileExtension : "." + defaultFileExtension);
					file = new File(file.getParentFile(), fileName);
				}
				return file;
			}else{
				return null;
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
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#knockCountFinished()
	 */
	@Override
	public boolean knockCountFinished() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(mainFrame)) return false;
			mainFrame.knockCountFinished();
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
			if(Objects.isNull(mainFrame)) return false;

			if(aFlag){
				Point dest = new Point(0,0);
				SwingUtilities.convertPointToScreen(dest, mainFrame);
				detailFrame.setBounds(dest.x + mainFrame.getWidth(), dest.y, 1000, 800);
			}
			detailFrame.setVisible(aFlag);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#getDetailFrameMutilang()
	 */
	@Override
	public Mutilang getDetailFrameMutilang() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(detailFrame)) return null;
			return detailFrame.getMutilang();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#updateDetailFrameMutilang()
	 */
	@Override
	public boolean updateDetailFrameMutilang() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(detailFrame)) return false;
			detailFrame.updateMutilang();
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
			if(aFlag){
				attrFrame.setLocationRelativeTo(mainFrame);
			}
			attrFrame.setVisible(aFlag);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#getAttrFrameMutilang()
	 */
	@Override
	public Mutilang getAttrFrameMutilang() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(attrFrame)) return null;
			return attrFrame.getMutilang();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#updateAttrFrameMutilang()
	 */
	@Override
	public boolean updateAttrFrameMutilang() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(attrFrame)) return false;
			attrFrame.updateMutilang();
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#newFailFrame()
	 */
	@Override
	public boolean newFailFrame() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(failFrame)){
				failFrame = newFailFrameImpl();
				return Objects.nonNull(failFrame);
			}else{
				return false;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * 新建失败面板的实现方法。
	 * <p> 该方法已经在调用方法处上锁，不用单独上锁。
	 * @return 新的主界面。
	 */
	 protected abstract FailFrame newFailFrameImpl();

	 
	 
	/*
	 *  (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#disposeFailFrame()
	 */
	@Override
	public boolean disposeFailFrame() {
		lock.writeLock().lock();
		try{
			if(Objects.nonNull(failFrame)){
				if(disposeFailFrameImpl()){
					failFrame = null;
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
	 * 释放失败面板的实现方法。
	 * <p> 该方法已在调用方法处上锁，不用单独上锁。
	 * @return 是否释放成功。
	 */
	protected abstract boolean disposeFailFrameImpl();

	/*
	 *  (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#hasFailFrame()
	 */
	@Override
	public boolean hasFailFrame() {
		lock.readLock().lock();
		try{
			return Objects.nonNull(failFrame);
		}finally {
			lock.readLock().unlock();
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#getFailFrameVisible()
	 */
	@Override
	public boolean getFailFrameVisible() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(mainFrame)) return false;
			return failFrame.isVisible();
		}finally {
			lock.readLock().unlock();
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#setFailFrameVisible(boolean)
	 */
	@Override
	public boolean setFailFrameVisible(boolean aFlag) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(failFrame)) return false;
			if(Objects.isNull(mainFrame)) return false;

			if(aFlag){
				Point dest = new Point(0,0);
				SwingUtilities.convertPointToScreen(dest, mainFrame);
				failFrame.setBounds(dest.x, dest.y + mainFrame.getHeight(), 427, 505);
			}
			failFrame.setVisible(aFlag);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 *  (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#getFailFrameMutilang()
	 */
	@Override
	public Mutilang getFailFrameMutilang() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(failFrame)) return null;
			return failFrame.getMutilang();
		}finally {
			lock.readLock().unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#updateFailFrameMutilang()
	 */
	@Override
	public boolean updateFailFrameMutilang() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(failFrame)) return false;
			failFrame.updateMutilang();
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#newDateTypeFrame()
	 */
	@Override
	public boolean newDateTypeFrame() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(dateTypeFrame)){
				dateTypeFrame = newDateTypeFrameImpl();
				return Objects.nonNull(dateTypeFrame);
			}else{
				return false;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 新建日期类型面板的实现方法。
	 * <p> 该方法已经在调用方法处上锁，不用单独上锁。
	 * @return 新的日期类型界面。
	 */
	protected abstract DateTypeFrame newDateTypeFrameImpl();

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#disposeDateTypeFrame()
	 */
	@Override
	public boolean disposeDateTypeFrame() {
		lock.writeLock().lock();
		try{
			if(Objects.nonNull(dateTypeFrame)){
				if(disposeDateTypeFrameImpl()){
					dateTypeFrame = null;
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
	 * 释放日期类型面板的实现方法。
	 * <p> 该方法已在调用方法处上锁，不用单独上锁。
	 * @return 是否释放成功。
	 */
	protected abstract boolean disposeDateTypeFrameImpl();

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#hasDateTypeFrame()
	 */
	@Override
	public boolean hasDateTypeFrame() {
		lock.readLock().lock();
		try{
			return Objects.nonNull(dateTypeFrame);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#getDateTypeFrameVisible()
	 */
	@Override
	public boolean getDateTypeFrameVisible() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(dateTypeFrame)) return false;
			return dateTypeFrame.isVisible();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#setDateTypeFrameVisible(boolean)
	 */
	@Override
	public boolean setDateTypeFrameVisible(boolean aFlag) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(dateTypeFrame)) return false;
			if(aFlag){
				dateTypeFrame.setLocationRelativeTo(mainFrame);
			}
			dateTypeFrame.setVisible(aFlag);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#getDateTypeFrameMutilang()
	 */
	@Override
	public Mutilang getDateTypeFrameMutilang() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(dateTypeFrame)) return null;
			return dateTypeFrame.getMutilang();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#updateDateTypeFrameMutilang()
	 */
	@Override
	public boolean updateDateTypeFrameMutilang() {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(dateTypeFrame)) return false;
			dateTypeFrame.updateMutilang();
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
}

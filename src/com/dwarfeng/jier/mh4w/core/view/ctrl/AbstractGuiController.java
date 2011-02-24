package com.dwarfeng.jier.mh4w.core.view.ctrl;

import java.awt.Point;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.view.gui.AttrFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.DetailFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.FailFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.MainFrame;

/**
 * �����������������
 * <p> ������������ĳ���ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class AbstractGuiController implements GuiController {
	
	/**ͬ����*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	/**�������е�������*/
	protected MainFrame mainFrame = null;
	/**�������е���ϸ����*/
	protected DetailFrame detailFrame = null;
	/**�������е����Խ���*/
	protected AttrFrame attrFrame = null;
	/**�������е�ʧ�ܽ���*/
	protected FailFrame failFrame = null;
	
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
	 * �½�������ʵ�ַ�����
	 * <p> �÷����Ѿ��ڵ��÷��������������õ���������
	 * @return �µ������档
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
	 * �ͷ�������ʵ�ַ�����
	 * <p> �÷������ڵ��÷��������������õ���������
	 * @return �Ƿ��ͷųɹ���
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
	 * �½���ϸ����ʵ�ַ�����
	 *  <p> �÷����Ѿ��ڵ��÷��������������õ���������
	 * @return �µ���ϸ���档
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
	 * �ͷ���ϸ����ʵ�ַ�����
	 * <p> �÷������ڵ��÷��������������õ���������
	 * @return �Ƿ��ͷųɹ���
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
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#setDetailFrameMutilang(com.dwarfeng.jier.mh4w.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setDetailFrameMutilang(Mutilang mutilang) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(detailFrame)) return false;
			return detailFrame.setMutilang(mutilang);
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
	 * �½���������ʵ�ַ�����
	 * <p> �÷����Ѿ��ڵ��÷��������������õ���������
	 * @return �µ������档
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
	 * �ͷ���������ʵ�ַ�����
	 * <p> �÷������ڵ��÷��������������õ���������
	 * @return �Ƿ��ͷųɹ���
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
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#setAttrFrameMutilang(com.dwarfeng.jier.mh4w.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setAttrFrameMutilang(Mutilang mutilang) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(attrFrame)) return false;
			boolean aFlag = attrFrame.setMutilang(mutilang);
			CT.trace(aFlag);
			return aFlag;
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
	 * �½�ʧ������ʵ�ַ�����
	 * <p> �÷����Ѿ��ڵ��÷��������������õ���������
	 * @return �µ������档
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
	 * �ͷ�ʧ������ʵ�ַ�����
	 * <p> �÷������ڵ��÷��������������õ���������
	 * @return �Ƿ��ͷųɹ���
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
	 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController#setFailFrameMutilang(com.dwarfeng.jier.mh4w.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setFailFrameMutilang(Mutilang mutilang) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(failFrame)) return false;
			return failFrame.setMutilang(mutilang);
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	
}

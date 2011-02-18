package com.dwarfeng.jier.mh4w.core.view.ctrl;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;

/**
 * 主界面控制器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface GuiController extends ExternalReadWriteThreadSafe{
	
	/**
	 * 新建一个主面板。
	 * @return 是否成功新建。
	 */
	public boolean newMainFrame();
	
	/**
	 * 释放控制器中的主面板。
	 * @return 是否成功释放。
	 */
	public boolean disposeMainFrame();
	
	/**
	 * 返回该控制器中是否拥有主面板实例。
	 * @return 是否拥有主面板实例。
	 */
	public boolean hasMainFrame();
	
	/**
	 * 获取主界面是否可见。
	 * @return 主界面是否可见。
	 */
	public boolean getMainFrameVisible();
	
	/**
	 * 设置主界面是否可见。
	 * @param aFlag 主界面是否可见。
	 * @return 是否设置成功。
	 */
	public boolean setMainFrameVisible(boolean aFlag);
	
	/**
	 * 获取该控制器中主面板的多语言接口。
	 * @return 该控制器中主面板的多语言接口。
	 */
	public Mutilang getMainFrameMutilang();
	
	/**
	 * 设置该控制器中主面板的多语言接口。
	 * @param mutilang 指定的多语言接口。
	 * @return 该操作是否对该对象造成了改变。
	 */
	public boolean setMainFrameMutilang(Mutilang mutilang);
	
	/**
	 * 向用户询问一个或数个文件。
	 * <p> 该方法是阻塞式的，在用户选择完文件之前，会一直阻塞。
	 * 因此，请不要在 EventQueue 线程中调用这个方法。
	 * @param directory 指定的根目录的位置。
	 * @param fileFilters 指定的文件筛选器。
	 * @param acceptAllFileFilter 是否允许选择全部文件。
	 * @param  mutiSelectionEnabled 是否允许选择多个文件。
	 * @param fileSelectionMode 文件选择模式。
	 * @return 用户选择的文件。
	 */
	public File[] askFile(File directory, FileFilter[] fileFilters, boolean acceptAllFileFilter, boolean mutiSelectionEnabled,
			int fileSelectionMode);
	
	
	/**
	 * 解除考勤文件面板的点击锁定。
	 * @return 是否成功执行。
	 */
	public boolean attendanceClickUnlock();
	
	/**
	 * 解除工时文件面本的点击锁定。
	 * @return 是否成功执行。
	 */
	public boolean workticketClickUnlock();
	
	/**
	 * 设置详细按钮的选择状态。
	 * @param value 指定的选择状态。
	 * @param isAdjusting 是否属于调整。
	 * @return 是否成功执行。
	 */
	public boolean setDetailButtonSelect(boolean value, boolean isAdjusting);
	
	/**
	 * 新建一个详细界面。
	 * @return 是否执行成功。
	 */
	public boolean newDetailFrame();
	
	/**
	 * 释放详细界面。
	 * @return 是否执行成功。
	 */
	public boolean disposeDetialFrame();
	
	/**
	 * 是否已经拥有详细界面实例。
	 * @return 是否拥有详细界面实例。
	 */
	public boolean hasDetailFrame();
	
	/**
	 * 返回详细界面是否可见。
	 * @return 详细界面是否可见。
	 */
	public boolean getDetailFrameVisible();
	
	/**
	 * 设置详细界面的可见性。
	 * @param aFlag 详细界面的可见性。
	 * @return 是否执行成功。
	 */
	public boolean setDetailFrameVisible(boolean aFlag);
	
}

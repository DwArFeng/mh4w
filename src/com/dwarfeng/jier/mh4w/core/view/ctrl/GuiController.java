package com.dwarfeng.jier.mh4w.core.view.ctrl;

import java.io.File;
import java.util.Locale;

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
	 * 更新该控制器中的主面板的多语言接口。
	 * @return 是否更新成功。
	 */
	public boolean updateMainFrameMutilang();
	
	/**
	 * 向用户询问一个或数个文件用于打开。
	 * <p> 该方法是阻塞式的，在用户选择完文件之前，会一直阻塞。
	 * 该方法允许在非 EventQueue 线程下调用。
	 * @param directory 指定的根目录的位置。
	 * @param fileFilters 指定的文件筛选器。
	 * @param acceptAllFileFilter 是否允许选择全部文件。
	 * @param  mutiSelectionEnabled 是否允许选择多个文件。
	 * @param fileSelectionMode 文件选择模式。
	 * @param locale 文件选择器显示的语言。
	 * @return 用户选择的文件。
	 */
	public File[] askFile4Open(File directory, FileFilter[] fileFilters, boolean acceptAllFileFilter,
			boolean mutiSelectionEnabled, int fileSelectionMode, Locale locale);
	
	/**
	 * 向用户询问一个文件用于保存
	 * <p> 该方法是阻塞式的，在用户选择完文件之前，会一直阻塞。
	 * 该方法允许在非 EventQueue 线程下调用。
	 * @param directory 指定的根目录的位置。
	 * @param fileFilters 指定的文件筛选器。
	 * @param acceptAllFileFilter 是否允许选择全部文件。
	 * @param defaultFileExtension 当文件没有扩展名的时候使用的默认扩展名。
	 * @param locale 文件选择器显示的语言。
	 * @return 用户选择的文件。
	 */
	public File askFile4Save(File directory, FileFilter[] fileFilters, boolean acceptAllFileFilter,
			String defaultFileExtension, Locale locale);
	
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
	 *通知视图统计过程已经结束。
	 * @return 是否成功通知。
	 */
	public boolean knockCountFinished();

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
	
	
	/**
	 * 获取该控制器中详细面板的多语言接口。
	 * @return 该控制器中详细面板的多语言接口。
	 */
	public Mutilang getDetailFrameMutilang();
	
	/**
	 * 更新该控制器重详细面板的多语言接口。
	 * @return 是否更新成功。
	 */
	public boolean updateDetailFrameMutilang();
	
	/**
	 * 新建一个属性界面。
	 * @return 是否执行成功。
	 */
	public boolean newAttrFrame();
	
	/**
	 * 释放属性界面。
	 * @return 是否执行成功。
	 */
	public boolean disposeAttrFrame();
	
	/**
	 * 是否已经拥有属性界面。
	 * @return 是否已经拥有属性界面。
	 */
	public boolean hasAttrFrame();
	
	/**
	 * 返回属性界面是否可见。
	 * @return 属性界面是否可见。
	 */
	public boolean getAttrFrameVisible();
	
	/**
	 * 设置属性界面的可见性。
	 * @param aFlag 属性界面。
	 * @return 是否执行成功。
	 */
	public boolean setAttrFrameVisible(boolean aFlag);
	
	/**
	 * 获取该控制器中属性面板的多语言接口。
	 * @return 该控制器中属性面板的多语言接口。
	 */
	public Mutilang getAttrFrameMutilang();
	
	/**
	 * 更新开控制器中属性面板的多语言接口。
	 * @return 是否更新成功。
	 */
	public boolean updateAttrFrameMutilang();
	
	/**
	 * 新建一个失败面板。
	 * @return 是否成功新建。
	 */
	public boolean newFailFrame();
	
	/**
	 * 释放控制器中的失败面板。
	 * @return 是否成功释放。
	 */
	public boolean disposeFailFrame();
	
	/**
	 * 返回控制器中是否拥有失败面板的实例。
	 * @return 是否拥有失败面板的实例。
	 */
	public boolean hasFailFrame();
	
	/**
	 * 获取失败面板是否可见。
	 * @return 失败面板是否可见。
	 */
	public boolean getFailFrameVisible();
	
	/**
	 * 设置失败面板是否可见。
	 * @param aFlag 失败面板是否可见。
	 * @return 是否设置成功。
	 */
	public boolean setFailFrameVisible(boolean aFlag);
	
	/**
	 * 获取失败面板中的多语言接口。
	 * @return 失败面板中的多语言接口。
	 */
	public Mutilang getFailFrameMutilang();
	
	/**
	 * 更新该控制器中失败面板的多语言接口。
	 * @return 是否更新成功。
	 */
	public boolean updateFailFrameMutilang();
	
	/**
	 * 新建一个日期类型面板。
	 * @return 是否成功新建。
	 */
	public boolean newDateTypeFrame();
	
	/**
	 * 释放控制器中的日期类型面板。
	 * @return 是否成功释放。
	 */
	public boolean disposeDateTypeFrame();
	
	/**
	 * 返回控制器中是否有日期类型面板的实例。
	 * @return 是否有日期类型面板的实例。
	 */
	public boolean hasDateTypeFrame();
	
	/**
	 * 返回控制器中的日期类型面板是否可见。
	 * @return 控制器中的日期类型面板是否可见。
	 */
	public boolean getDateTypeFrameVisible();
	
	/**
	 * 设置控制器中的日期类型面板的可见性。
	 * @param aFlag 控制器中的日期面板是否可见。
	 * @return 是否设置成功。
	 */
	public boolean setDateTypeFrameVisible(boolean aFlag);
	
	/**
	 * 获取日期类型面板中的多语言接口。
	 * @return 日期面板中的多语言接口。
	 */
	public Mutilang getDateTypeFrameMutilang();
	
	/**
	 * 更新该控制器中日期类型面板的多语言接口。
	 * @return 是否更新成功。
	 */
	public boolean updateDateTypeFrameMutilang();
	
}

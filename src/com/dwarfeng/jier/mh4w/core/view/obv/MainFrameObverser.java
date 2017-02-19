package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 主界面观察器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface MainFrameObverser extends Obverser{
	
	/**
	 * 通知界面的关闭按钮被点击。
	 */
	public void fireWindowClosing();
	
	/**
	 * 通知选择出勤文件。
	 */
	public void fireSelectAttendanceFile();
	
	/**
	 * 通知选择工票文件。
	 */
	public void fireSelectWorkticketFile();
	
	/**
	 * 通知统计被重置。
	 */
	public void fireCountReset();

	/**
	 * 通知隐藏详细信息界面。
	 */
	public void fireHideDetailFrame();

	/**
	 * 通知显示详细信息界面。
	 */
	public void fireShowDetailFrame();

	/**
	 * 通知开始统计。
	 */
	public void fireCount();

	/**
	 * 通知显示属性面板。
	 */
	public void fireShowAttrFrame();
	
}

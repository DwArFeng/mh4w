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
	
}

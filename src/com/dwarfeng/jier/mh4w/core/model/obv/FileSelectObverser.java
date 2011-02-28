package com.dwarfeng.jier.mh4w.core.model.obv;

import java.io.File;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 文件选择模型观察器。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface FileSelectObverser extends Obverser {

	/**
	 * 通知模型中的出勤文件发生了改变。
	 * @param oldValue 旧的出勤文件，可能为 <code>null</code>。
	 * @param newValue 新的初心文件，可能为 <code>null</code>。
	 */
	public void fireAttendanceFileChanged(File oldValue, File newValue);
	
	/**
	 * 通知模型中的工票文件发生了改变。
	 * @param oldValue 旧的工票文件，可能为 <code>null</code>。
	 * @param newValue 新的工票文件，可能为 <code>null</code>。
	 */
	public void fireWorkticketFileChanged(File oldValue, File newValue);
	
}

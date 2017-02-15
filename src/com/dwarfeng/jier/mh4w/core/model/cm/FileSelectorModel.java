package com.dwarfeng.jier.mh4w.core.model.cm;

import java.io.File;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.FileSelectorObverser;

/**
 * 文件选择器模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface FileSelectorModel extends ExternalReadWriteThreadSafe, ObverserSet<FileSelectorObverser>{
	
	/**
	 * 返回模型中的出勤文件。
	 * @return 文件中的出勤文件。
	 */
	public File getAttendanceFile();
	
	/**
	 * 设置模型中的出勤文件。
	 * @param file 指定的出勤文件，允许为 <code>null</code>。
	 * @return 该操作是否对模型造成了改动。
	 */
	public boolean setAttendanceFile(File file);
	
	/**
	 * 获取工票信息文件。
	 * @return 工票信息文件。
	 */
	public File getWorkticketFile();
	
	/**
	 * 设置模型中的工票信息文件。
	 * @param file 指定的工票信息文件，允许为 <code>null</code>。
	 * @return 该操作是否对模型造成了改动。
	 */
	public boolean setWorkticketFile(File file);
	
	/**
	 * 返回该模型是否就绪。
	 * <p> 当两个文件都不为 <code>null</code>的时候，认为模型就绪。
	 * @return 该模型是否就绪。
	 */
	public boolean isReady();

}

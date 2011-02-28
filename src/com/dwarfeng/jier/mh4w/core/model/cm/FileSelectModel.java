package com.dwarfeng.jier.mh4w.core.model.cm;

import java.io.File;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.FileSelectObverser;

/**
 * 文件选择模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface FileSelectModel extends ExternalReadWriteThreadSafe, ObverserSet<FileSelectObverser>{
	
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

}

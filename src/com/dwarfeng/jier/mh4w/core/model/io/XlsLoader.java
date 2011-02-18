package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.Closeable;

/**
 * xls读取接口。
 * @author DwArFeng
 * @since 1.8
 */
public interface XlsLoader extends Closeable{
	
	/**
	 * 获取指定单元格的纯文本。
	 * @param sheetIndex 工作表序号。
	 * @param row 指定的行。
	 * @param column 指定的列。
	 * @return 对应的文本。
	 */
	public String getStringAt(int sheetIndex, int row, int column);
	
	/**
	 * 获取指定工作表的行数。
	 * @param sheetIndex 工作表序号。
	 * @return 行数。
	 */
	public int getRows(int sheetIndex);
	
	/**
	 * 获取指定工作表的列数。
	 * @param sheetIndex 工作表序号。
	 * @return 列数。
	 */
	public int getColumns(int sheetIndex);
	
	/**
	 * 获取工作表数量。
	 * @return 工作表数量。
	 */
	public int getSheets();
	
}

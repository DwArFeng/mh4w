package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultOriginalAttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalAttendanceData;

/**
 * Xls 原始出勤数据取器。
 * <p> 利用 xls 文件读取原始出勤数据。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public class XlsOriginalAttendanceDataLoader extends StreamLoader<DataListModel<OriginalAttendanceData>>{

	private final String fileName;
	
	private final int row_start;
	private final int column_department;
	private final int column_workNumber;
	private final int column_name;
	private final int column_date;
	private final int column_shift;
	private final int column_record;

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @param fileName 考勤文件的名称。
	 * @param row_start 起始数据行。
	 * @param column_department 部门所在的列。
	 * @param column_workNumber 工号所在的列。
	 * @param column_name 姓名所在的列。
	 * @param column_date 日期所在的列。
	 * @param column_shift 班次所在的列。
	 * @param column_record 记录所在的列。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public XlsOriginalAttendanceDataLoader(InputStream in, String fileName, int row_start, int column_department, int column_workNumber,
			int column_name, int column_date, int column_shift, int column_record) {
		super(in);
		Objects.requireNonNull(fileName, "入口参数 fileName 不能为 null。");
		
		this.fileName = fileName;
		this.row_start = row_start;
		this.column_department = column_department;
		this.column_workNumber = column_workNumber;
		this.column_name = column_name;
		this.column_date = column_date;
		this.column_shift = column_shift;
		this.column_record = column_record;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(DataListModel<OriginalAttendanceData> originalAttendanceDatas) throws LoadFailedException {
		Objects.requireNonNull(originalAttendanceDatas, "入口参数 originalAttendanceDatas 不能为 null。");
		
		XlsLoader loader = null;
		try{
			loader = new JxlXlsLoader(in);
			
			for(int i = row_start - 1 ; i < loader.getRows(0) ; i ++){
				String workNumber = loader.getStringAt(0, i, column_workNumber);
				String department = loader.getStringAt(0, i, column_department);
				String name = loader.getStringAt(0, i, column_name);
				String date = loader.getStringAt(0, i, column_date);
				String shift = loader.getStringAt(0, i, column_shift);
				String attendanceRecord = loader.getStringAt(0, i, column_record);

				OriginalAttendanceData originalAttendanceData = new DefaultOriginalAttendanceData(fileName, 
						i + 1, workNumber, department, name, date, shift, attendanceRecord);
				originalAttendanceDatas.add(originalAttendanceData);
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("出勤表读取器 - 无法正确地向出勤列表模型中读取数据", e);
		}finally {
			if(Objects.nonNull(loader)){
				try {
					loader.close();
				} catch (IOException ignore) {
					//不会发生这个异常。
				}
			}
		}
	}

}

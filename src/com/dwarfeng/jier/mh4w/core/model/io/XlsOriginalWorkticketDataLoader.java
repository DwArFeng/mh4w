package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultOriginalWorkticketData;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalWorkticketData;

/**
 * Xls 原始工票数据取器。
 * <p> 利用 xls 文件读取原始工票数据。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class XlsOriginalWorkticketDataLoader extends StreamLoader<DataListModel<OriginalWorkticketData>>{

	private final String fileName;
	
	private final int row_start;
	private final int column_department;
	private final int column_workNumber;
	private final int column_name;
	private final Job[] jobs;
	
	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @param fileName 工票文件的名称。
	 * @param row_start 数据开始的行。
	 * @param column_department 部门所在的列。
	 * @param column_workNumber 工号所在的列。
	 * @param column_name 姓名所在的列。
	 * @param jobs 工作数组。
	 */
	public XlsOriginalWorkticketDataLoader(InputStream in, String fileName, int row_start, int column_department, int column_workNumber,
			int column_name, Job[] jobs) {
		super(in);
		Objects.requireNonNull(jobs, "入口参数 jobs 不能为 null。");
		Objects.requireNonNull(fileName, "入口参数 fileName 不能为 null。");

		this.fileName = fileName;
		this.row_start = row_start;
		this.column_department = column_department;
		this.column_workNumber = column_workNumber;
		this.column_name = column_name;
		this.jobs = jobs;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(DataListModel<OriginalWorkticketData> originalWorkticketDatas) throws LoadFailedException {
		Objects.requireNonNull(originalWorkticketDatas, "入口参数 originalWorkticketDatas 不能为 null。");
		
		XlsLoader loader = null;
		try{
			loader = new JxlXlsLoader(in);
			
			for(int i = row_start - 1 ; i < loader.getRows(0) ; i ++){
				String workNumber = loader.getStringAt(0, i, column_workNumber);
				String department = loader.getStringAt(0, i, column_department);
				String name = loader.getStringAt(0, i, column_name);
				
				for(Job job : jobs){
					String workticket = loader.getStringAt(0, i, job.getOriginalColumn());
					
					OriginalWorkticketData orWorkticketData = new DefaultOriginalWorkticketData(fileName, i + 1,
							workNumber, department, name, workticket, job);
					originalWorkticketDatas.add(orWorkticketData);
				}
				
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

package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Objects;

import com.dwarfeng.dutil.basic.io.SaveFailedException;
import com.dwarfeng.dutil.basic.io.StreamSaver;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.cm.JobModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.CountResult;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.WorkticketData;

/**
 * Xls 统计结果保存器。
 * <p> 用 xls 保存统计结果。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class XlsCountResultsSaver extends StreamSaver<XlsCountResultsSaver.CountResults> {
	
	private final Mutilang mutilang;

	/**
	 * 新实例。
	 * @param out 指定的输出流。
	 * @throws NullPointerException  入口参数为 <code>null</code>。
	 */
	public XlsCountResultsSaver(OutputStream out, Mutilang mutilang) {
		super(out);
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		this.mutilang = mutilang;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Saver#save(java.lang.Object)
	 */
	@Override
	public void save(CountResults countResults) throws SaveFailedException {
		Objects.requireNonNull(countResults, "入口参数 countResults 不能为 null。");
		
		XlsSaver saver = null;
		try{
			saver = new JxlXlsSaver(out);
			
			//--------------------------------第一张表--------------------------------
			saver.createSheet(0, getLabel(LabelStringKey.JxlCountResultsSaver_1));
			saver.setString(0, 0, 0, getLabel(LabelStringKey.JxlCountResultsSaver_2));
			saver.setString(0, 1, 0, formatLable(LabelStringKey.JxlCountResultsSaver_3, Calendar.getInstance().getTime().toString()));
			saver.mergeCell(0, 0, 0, 0, 6);
			saver.mergeCell(0, 1, 0, 1, 6);
			
			
			
		}catch (Exception e) {
			throw new SaveFailedException("统计结果保存器 - 无法正确地向指定的输出流保存统计结果", e);
		}finally {
			if(Objects.nonNull(saver)){
				try {
					saver.close();
				} catch (IOException ignore) {
					//不会发生这个异常。
				}
			}
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

	private String formatLable(LabelStringKey labelStringKey, Object... args){
		return String.format(mutilang.getString(labelStringKey.getName()), args);
	}
	
	/**
	 * 统计结果集合。
	 * @author DwArFeng
	 * @since 0.0.0-alpha
	 */
	public final static class CountResults{
		
		private final DataListModel<AttendanceData> attendanceDataModel;
		private final DataListModel<WorkticketData> workticketDataModel;
		private final DataListModel<CountResult> countResultModel;
		private final JobModel jobModel;
		
		/**
		 * 新实例。
		 * @param attendanceDataModel 指定的出勤数据模型。
		 * @param workticketDataModel 指定的工票数据模型。
		 * @param countResultModel 指定的统计结果模型。
		 * @throws NullPointerException 入口参数为 <code>null</code>。
		 */
		public CountResults(DataListModel<AttendanceData> attendanceDataModel,
				DataListModel<WorkticketData> workticketDataModel, DataListModel<CountResult> countResultModel, 
				JobModel jobModel) {
			Objects.requireNonNull(attendanceDataModel, "入口参数 attendanceDataModel 不能为 null。");
			Objects.requireNonNull(workticketDataModel, "入口参数 workticketDataModel 不能为 null。");
			Objects.requireNonNull(countResultModel, "入口参数 countResultModel 不能为 null。");
			Objects.requireNonNull(jobModel, "入口参数 jobModel 不能为 null。");

			this.attendanceDataModel = attendanceDataModel;
			this.workticketDataModel = workticketDataModel;
			this.countResultModel = countResultModel;
			this.jobModel = jobModel;
		}

		/**
		 * @return the attendanceDataModel
		 */
		public DataListModel<AttendanceData> getAttendanceDataModel() {
			return attendanceDataModel;
		}

		/**
		 * @return the workticketDataModel
		 */
		public DataListModel<WorkticketData> getWorkticketDataModel() {
			return workticketDataModel;
		}

		/**
		 * @return the countResultModel
		 */
		public DataListModel<CountResult> getCountResultModel() {
			return countResultModel;
		}

		/**
		 * @return the jobModel
		 */
		public JobModel getJobModel() {
			return jobModel;
		}
		
	}
	
}

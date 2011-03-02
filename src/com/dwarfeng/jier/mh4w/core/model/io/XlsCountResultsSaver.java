package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Objects;

import com.dwarfeng.dutil.basic.io.SaveFailedException;
import com.dwarfeng.dutil.basic.io.StreamSaver;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.cm.JobModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.io.XlsSaver.Alignment;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceOffset;
import com.dwarfeng.jier.mh4w.core.model.struct.CountResult;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.WorkticketData;
import com.dwarfeng.jier.mh4w.core.util.FormatUtil;

/**
 * Xls 统计结果保存器。
 * <p> 用 xls 保存统计结果。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class XlsCountResultsSaver extends StreamSaver<XlsCountResultsSaver.CountResults> {
	
	private final Mutilang mutilang;

	/**
	 * 新实例。
	 * @param out 指定的输出流。
	 * @param mutilang 指定的多语言接口。
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
			
			final int TITLE_SIZE = 20;
			final int NORMAL_SIZE = 10;
			final String NUMBER_FORMAT = "0.00";
			final int COLUMN_WIDTH_SHORT = 150;
			final int COLUMN_WIDTH_LONG = 300;
			
			//--------------------------------第一张表--------------------------------
			saver.createSheet(0, getLabel(LabelStringKey.JxlCountResultsSaver_1));
			saver.setString(0, 0, 0, getLabel(LabelStringKey.JxlCountResultsSaver_2));
			saver.setString(0, 1, 0, formatLable(LabelStringKey.JxlCountResultsSaver_3, Calendar.getInstance().getTime().toString()));
			saver.mergeCell(0, 0, 0, 0, 3 + countResults.getJobModel().size());
			saver.mergeCell(0, 1, 0, 1, 3 + countResults.getJobModel().size());
			saver.setCellFormat(0, 0, 0, true, TITLE_SIZE, Alignment.CENTER);
			saver.setCellFormat(0, 1, 0, false, NORMAL_SIZE, Alignment.RIGHT);
			
			saver.setString(0, 2, 0, getLabel(LabelStringKey.JxlCountResultsSaver_4));
			saver.setString(0, 2, 1, getLabel(LabelStringKey.JxlCountResultsSaver_5));
			saver.setString(0, 2, 2, getLabel(LabelStringKey.JxlCountResultsSaver_6));
			Iterator<Job> jobIterator = countResults.getJobModel().iterator();
			for(int i = 0 ; i < countResults.getJobModel().size() ; i ++){
				saver.setString(0, 2, 3 + i, formatLable(LabelStringKey.JxlCountResultsSaver_7, jobIterator.next().getName()));
			}
			saver.setString(0, 2, 3 + countResults.getJobModel().size(), getLabel(LabelStringKey.JxlCountResultsSaver_9));
			
			int writeRow;
			
			writeRow = 3;
			countResults.getCountResultModel().getLock().readLock().lock();
			try{
				for(CountResult data : countResults.getCountResultModel()){
					saver.setString(0, writeRow, 0, data.getPerson().getDepartment());
					saver.setString(0, writeRow, 1, data.getPerson().getWorkNumber());
					saver.setString(0, writeRow, 2, data.getPerson().getName());
					jobIterator = countResults.getJobModel().iterator();
					for(int i = 0 ; i < countResults.getJobModel().size() ; i ++){
						saver.setNumber(0, writeRow, 3 + i, data.getEquivalentWorkticket(jobIterator.next()));
					}
					saver.setNumber(0, writeRow, 3 + countResults.getJobModel().size(), data.getEquivalentWorkticket());
					saver.setRowHeight(0, writeRow, 17);
					writeRow ++;
				}
			}finally {
				countResults.getCountResultModel().getLock().readLock().unlock();
			}
			
			saver.setColumnWidth(0, 0, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(0, 1, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(0, 2, COLUMN_WIDTH_SHORT);
			for(int i = 0 ; i < countResults.getJobModel().size() ; i ++){
				saver.setColumnWidth(0, 3 + i, COLUMN_WIDTH_SHORT);
			}
			saver.setColumnWidth(0, 3 + countResults.getJobModel().size(), COLUMN_WIDTH_SHORT);
			
			saver.setColumnFormatAsString(0, 0, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(0, 1, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(0, 2, false, NORMAL_SIZE, Alignment.LEFT);
			for(int i = 0 ; i < countResults.getJobModel().size() ; i ++){
				saver.setColumnFormatAsNumber(0, 3 + i, NUMBER_FORMAT);
			}
			saver.setColumnFormatAsNumber(0, 3 + countResults.getJobModel().size(), NUMBER_FORMAT);
			
			//--------------------------------第二张表--------------------------------
			saver.createSheet(1, getLabel(LabelStringKey.JxlCountResultsSaver_8));
			saver.setString(1, 0, 0, getLabel(LabelStringKey.JxlCountResultsSaver_27));
			saver.setString(1, 0, 1, getLabel(LabelStringKey.JxlCountResultsSaver_28));
			saver.setString(1, 0, 2, getLabel(LabelStringKey.JxlCountResultsSaver_29));
			saver.setString(1, 0, 3, getLabel(LabelStringKey.JxlCountResultsSaver_30));
			saver.setString(1, 0, 4, getLabel(LabelStringKey.JxlCountResultsSaver_31));
			saver.setString(1, 0, 5, getLabel(LabelStringKey.JxlCountResultsSaver_32));
			saver.setString(1, 0, 6, getLabel(LabelStringKey.JxlCountResultsSaver_33));
			saver.setString(1, 0, 7, getLabel(LabelStringKey.JxlCountResultsSaver_34));
			saver.setString(1, 0, 8, getLabel(LabelStringKey.JxlCountResultsSaver_35));

			writeRow = 1;
			countResults.getCountResultModel().getLock().readLock().lock();
			try{
				for(CountResult data : countResults.getCountResultModel()){
					saver.setString(1, writeRow, 0, data.getPerson().getDepartment());
					saver.setString(1, writeRow, 1, data.getPerson().getWorkNumber());
					saver.setString(1, writeRow, 2, data.getPerson().getName());
					saver.setNumber(1, writeRow, 3, data.getOriginalWorkTime());
					saver.setNumber(1, writeRow, 4, data.getEquivalentWorkTime());
					saver.setNumber(1, writeRow, 5, data.getEquivalentWorkTimeOffset());
					saver.setNumber(1, writeRow, 6, data.getAmplifyCoefficient());
					saver.setNumber(1, writeRow, 7, data.getWorkticket());
					saver.setNumber(1, writeRow, 8, data.getEquivalentWorkticket());
					saver.setRowHeight(1, writeRow, 17);
					writeRow ++;
				}
			}finally {
				countResults.getCountResultModel().getLock().readLock().unlock();
			}
			
			saver.setColumnWidth(1, 0, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(1, 1, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(1, 2, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(1, 3, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(1, 4, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(1, 5, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(1, 6, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(1, 7, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(1, 8, COLUMN_WIDTH_SHORT);
			
			saver.setColumnFormatAsString(1, 0, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(1, 1, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(1, 2, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsNumber(1, 3, NUMBER_FORMAT);
			saver.setColumnFormatAsNumber(1, 4, NUMBER_FORMAT);
			saver.setColumnFormatAsNumber(1, 5, NUMBER_FORMAT);
			saver.setColumnFormatAsNumber(1, 6, NUMBER_FORMAT);
			saver.setColumnFormatAsNumber(1, 7, NUMBER_FORMAT);
			saver.setColumnFormatAsNumber(1, 8, NUMBER_FORMAT);
			
			//--------------------------------第三张表--------------------------------
			saver.createSheet(2, getLabel(LabelStringKey.JxlCountResultsSaver_20));
			saver.setString(2, 0, 0, getLabel(LabelStringKey.JxlCountResultsSaver_11));
			saver.setString(2, 0, 1, getLabel(LabelStringKey.JxlCountResultsSaver_12));
			saver.setString(2, 0, 2, getLabel(LabelStringKey.JxlCountResultsSaver_13));
			saver.setString(2, 0, 3, getLabel(LabelStringKey.JxlCountResultsSaver_14));
			saver.setString(2, 0, 4, getLabel(LabelStringKey.JxlCountResultsSaver_15));
			saver.setString(2, 0, 5, getLabel(LabelStringKey.JxlCountResultsSaver_16));
			saver.setString(2, 0, 6, getLabel(LabelStringKey.JxlCountResultsSaver_17));
			saver.setString(2, 0, 7, getLabel(LabelStringKey.JxlCountResultsSaver_18));
			saver.setString(2, 0, 8, getLabel(LabelStringKey.JxlCountResultsSaver_19));
			
			writeRow = 1;
			countResults.getAttendanceDataModel().getLock().readLock().lock();
			try{
				for(AttendanceData data : countResults.getAttendanceDataModel()){
					saver.setString(2, writeRow, 0, data.getPerson().getDepartment());
					saver.setString(2, writeRow, 1, data.getPerson().getWorkNumber());
					saver.setString(2, writeRow, 2, data.getPerson().getName());
					saver.setString(2, writeRow, 3, FormatUtil.formatCountDate(data.getCountDate()));
					saver.setString(2, writeRow, 4, data.getShift().getName());
					saver.setString(2, writeRow, 5, FormatUtil.formatTimeSection(data.getAttendanceRecord()));
					saver.setString(2, writeRow, 6, getLabel(data.getDateType().getLabelStringKey()));
					saver.setNumber(2, writeRow, 7, data.getOriginalWorkTime());
					saver.setNumber(2, writeRow, 8, data.getEquivalentWorkTime());
					saver.setRowHeight(2, writeRow, 17);
					writeRow ++;
				}
			}finally {
				countResults.getAttendanceDataModel().getLock().readLock().unlock();
			}
			
			saver.setColumnWidth(2, 0, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(2, 1, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(2, 2, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(2, 3, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(2, 4, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(2, 5, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(2, 6, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(2, 7, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(2, 8, COLUMN_WIDTH_SHORT);

			saver.setColumnFormatAsString(2, 0, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(2, 1, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(2, 2, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(2, 3, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(2, 4, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(2, 5, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(2, 6, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsNumber(2, 7, NUMBER_FORMAT);
			saver.setColumnFormatAsNumber(2, 8, NUMBER_FORMAT);
			
			//--------------------------------第四张表--------------------------------
			saver.createSheet(3, getLabel(LabelStringKey.JxlCountResultsSaver_21));
			saver.setString(3, 0, 0, getLabel(LabelStringKey.JxlCountResultsSaver_22));
			saver.setString(3, 0, 1, getLabel(LabelStringKey.JxlCountResultsSaver_23));
			saver.setString(3, 0, 2, getLabel(LabelStringKey.JxlCountResultsSaver_24));
			saver.setString(3, 0, 3, getLabel(LabelStringKey.JxlCountResultsSaver_25));
			saver.setString(3, 0, 4, getLabel(LabelStringKey.JxlCountResultsSaver_26));
			
			writeRow = 1;
			countResults.getWorkticketDataModel().getLock().readLock().lock();
			try{
				for(WorkticketData data : countResults.getWorkticketDataModel()){
					saver.setString(3, writeRow, 0, data.getPerson().getDepartment());
					saver.setString(3, writeRow, 1, data.getPerson().getWorkNumber());
					saver.setString(3, writeRow, 2, data.getPerson().getName());
					saver.setString(3, writeRow, 3, data.getJob().getName());
					saver.setNumber(3, writeRow, 4, data.getWorkticket());
					saver.setRowHeight(3, writeRow, 17);
					writeRow ++;
				}
			}finally {
				countResults.getWorkticketDataModel().getLock().readLock().unlock();
			}
			
			saver.setColumnWidth(3, 0, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(3, 1, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(3, 2, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(3, 3, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(3, 4, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(3, 5, COLUMN_WIDTH_SHORT);

			saver.setColumnFormatAsString(3, 0, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(3, 1, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(3, 2, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(3, 3, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsNumber(3, 4, NUMBER_FORMAT);
			saver.setColumnFormatAsNumber(3, 5, NUMBER_FORMAT);

			//--------------------------------第五张表--------------------------------
			saver.createSheet(4, getLabel(LabelStringKey.JxlCountResultsSaver_36));
			saver.setString(4, 0, 0, getLabel(LabelStringKey.JxlCountResultsSaver_37));
			saver.setString(4, 0, 1, getLabel(LabelStringKey.JxlCountResultsSaver_38));
			saver.setString(4, 0, 2, getLabel(LabelStringKey.JxlCountResultsSaver_39));
			saver.setString(4, 0, 3, getLabel(LabelStringKey.JxlCountResultsSaver_40));
			saver.setString(4, 0, 4, getLabel(LabelStringKey.JxlCountResultsSaver_41));
			
			writeRow = 1;
			countResults.getAttendanceOffsetModel().getLock().readLock().lock();
			try{
				for(AttendanceOffset data : countResults.getAttendanceOffsetModel()){
					saver.setString(4, writeRow, 0, data.getPerson().getDepartment());
					saver.setString(4, writeRow, 1, data.getPerson().getWorkNumber());
					saver.setString(4, writeRow, 2, data.getPerson().getName());
					saver.setNumber(4, writeRow, 3, data.getValue());
					saver.setString(4, writeRow, 4, data.getDescription());
					saver.setRowHeight(4, writeRow, 17);
					writeRow ++;
				}
			}finally {
				countResults.getAttendanceOffsetModel().getLock().readLock().unlock();
			}
			
			saver.setColumnWidth(4, 0, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(4, 1, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(4, 2, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(4, 3, COLUMN_WIDTH_SHORT);
			saver.setColumnWidth(4, 4, COLUMN_WIDTH_LONG);

			saver.setColumnFormatAsString(4, 0, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(4, 1, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsString(4, 2, false, NORMAL_SIZE, Alignment.LEFT);
			saver.setColumnFormatAsNumber(4, 3, NUMBER_FORMAT);
			saver.setColumnFormatAsString(4, 4, false, NORMAL_SIZE, Alignment.LEFT);
			
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
	 * @since 0.0.1-beta
	 */
	public final static class CountResults{
		
		private final DataListModel<AttendanceData> attendanceDataModel;
		private final DataListModel<WorkticketData> workticketDataModel;
		private final DataListModel<CountResult> countResultModel;
		private final JobModel jobModel;
		private final DataListModel<AttendanceOffset> attendanceOffsetModel;
		
		/**
		 * 新实例。
		 * @param attendanceDataModel 指定的出勤数据模型。
		 * @param workticketDataModel 指定的工票数据模型。
		 * @param countResultModel 指定的统计结果模型。
		 * @param jobModel 工作模型。
		 * @param attendanceOffsetModel 考勤补偿模型。
		 * @throws NullPointerException 入口参数为 <code>null</code>。
		 */
		public CountResults(DataListModel<AttendanceData> attendanceDataModel,
				DataListModel<WorkticketData> workticketDataModel, DataListModel<CountResult> countResultModel, 
				JobModel jobModel, DataListModel<AttendanceOffset> attendanceOffsetModel) {
			Objects.requireNonNull(attendanceDataModel, "入口参数 attendanceDataModel 不能为 null。");
			Objects.requireNonNull(workticketDataModel, "入口参数 workticketDataModel 不能为 null。");
			Objects.requireNonNull(countResultModel, "入口参数 countResultModel 不能为 null。");
			Objects.requireNonNull(jobModel, "入口参数 jobModel 不能为 null。");
			Objects.requireNonNull(attendanceOffsetModel, "入口参数 attendanceOffsetModel 不能为 null。");

			this.attendanceDataModel = attendanceDataModel;
			this.workticketDataModel = workticketDataModel;
			this.countResultModel = countResultModel;
			this.jobModel = jobModel;
			this.attendanceOffsetModel = attendanceOffsetModel;
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

		/**
		 * @return the attendanceOffsetModel
		 */
		public DataListModel<AttendanceOffset> getAttendanceOffsetModel() {
			return attendanceOffsetModel;
		}
		
	}
	
}

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
 * Xls ͳ�ƽ����������
 * <p> �� xls ����ͳ�ƽ����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class XlsCountResultsSaver extends StreamSaver<XlsCountResultsSaver.CountResults> {
	
	private final Mutilang mutilang;

	/**
	 * ��ʵ����
	 * @param out ָ�����������
	 * @throws NullPointerException  ��ڲ���Ϊ <code>null</code>��
	 */
	public XlsCountResultsSaver(OutputStream out, Mutilang mutilang) {
		super(out);
		Objects.requireNonNull(mutilang, "��ڲ��� mutilang ����Ϊ null��");
		this.mutilang = mutilang;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Saver#save(java.lang.Object)
	 */
	@Override
	public void save(CountResults countResults) throws SaveFailedException {
		Objects.requireNonNull(countResults, "��ڲ��� countResults ����Ϊ null��");
		
		XlsSaver saver = null;
		try{
			saver = new JxlXlsSaver(out);
			
			//--------------------------------��һ�ű�--------------------------------
			saver.createSheet(0, getLabel(LabelStringKey.JxlCountResultsSaver_1));
			saver.setString(0, 0, 0, getLabel(LabelStringKey.JxlCountResultsSaver_2));
			saver.setString(0, 1, 0, formatLable(LabelStringKey.JxlCountResultsSaver_3, Calendar.getInstance().getTime().toString()));
			saver.mergeCell(0, 0, 0, 0, 6);
			saver.mergeCell(0, 1, 0, 1, 6);
			
			
			
		}catch (Exception e) {
			throw new SaveFailedException("ͳ�ƽ�������� - �޷���ȷ����ָ�������������ͳ�ƽ��", e);
		}finally {
			if(Objects.nonNull(saver)){
				try {
					saver.close();
				} catch (IOException ignore) {
					//���ᷢ������쳣��
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
	 * ͳ�ƽ�����ϡ�
	 * @author DwArFeng
	 * @since 0.0.0-alpha
	 */
	public final static class CountResults{
		
		private final DataListModel<AttendanceData> attendanceDataModel;
		private final DataListModel<WorkticketData> workticketDataModel;
		private final DataListModel<CountResult> countResultModel;
		private final JobModel jobModel;
		
		/**
		 * ��ʵ����
		 * @param attendanceDataModel ָ���ĳ�������ģ�͡�
		 * @param workticketDataModel ָ���Ĺ�Ʊ����ģ�͡�
		 * @param countResultModel ָ����ͳ�ƽ��ģ�͡�
		 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
		 */
		public CountResults(DataListModel<AttendanceData> attendanceDataModel,
				DataListModel<WorkticketData> workticketDataModel, DataListModel<CountResult> countResultModel, 
				JobModel jobModel) {
			Objects.requireNonNull(attendanceDataModel, "��ڲ��� attendanceDataModel ����Ϊ null��");
			Objects.requireNonNull(workticketDataModel, "��ڲ��� workticketDataModel ����Ϊ null��");
			Objects.requireNonNull(countResultModel, "��ڲ��� countResultModel ����Ϊ null��");
			Objects.requireNonNull(jobModel, "��ڲ��� jobModel ����Ϊ null��");

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

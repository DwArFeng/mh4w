package com.dwarfeng.jier.mh4w.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import com.dwarfeng.dutil.basic.num.NumberUtil;
import com.dwarfeng.dutil.basic.num.unit.Time;
import com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel;
import com.dwarfeng.jier.mh4w.core.model.cm.FileSelectModel;
import com.dwarfeng.jier.mh4w.core.model.cm.JobModel;
import com.dwarfeng.jier.mh4w.core.model.io.XlsOriginalAttendanceDataLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XlsOriginalWorkticketDataLoader;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;

/**
 *��ͳ���йع��߷�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class CountUtil {

	/**
	 * �� xls ����е�����ת�����е���š�
	 * @param column �����ơ�
	 * @return �����ƶ�Ӧ����š�
	 */
	public static int columnString2Int(String column) {
		Objects.requireNonNull(column, "��ڲ��� input ����Ϊ null��");
		column = column.toUpperCase();
		if(column.matches("[^A-Z]")){
			throw new IllegalArgumentException("�ַ�ֻ�ܰ���A-Z");
		}
		
		int sum = 0;
		int one = Character.getNumericValue('A');
		
		for(int i = 0 ; i < column.length() ; i ++){
			sum *= 26;
			char ch = column.charAt(i);
			sum += Character.getNumericValue(ch) - one +1;
		}
		
		return sum - 1;
	}
	
	/**
	 * ����һ���µ� xls ԭʼ�������ݶ�ȡ����
	 * @param fileSelectModel ָ�����ļ�ѡ��ģ�͡�
	 * @param coreConfigModel ָ���ĺ�������ģ�͡�
	 * @return ��ָ����ģ����ɵ� xls ԭʼ�������ݶ�ȡ����
	 * @throws FileNotFoundException �ļ�δ�ҵ��쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static XlsOriginalAttendanceDataLoader newXlsOriginalAttendanceDataLoader(
			FileSelectModel fileSelectModel, CoreConfigModel coreConfigModel) throws FileNotFoundException{
		Objects.requireNonNull(fileSelectModel, "��ڲ��� fileSelectModel ����Ϊ null��");
		Objects.requireNonNull(coreConfigModel, "��ڲ��� coreConfigModel ����Ϊ null��");

		InputStream in = new FileInputStream(fileSelectModel.getAttendanceFile());
		String fileName = fileSelectModel.getAttendanceFile().getName();
		int row_start = coreConfigModel.getAttendanceStartRow();
		int column_department = coreConfigModel.getAttendanceDepartmentColumn();
		int column_workNumber = coreConfigModel.getAttendanceWorkNumberColumn();
		int column_name = coreConfigModel.getAttendacneNameColumn();
		int column_date = coreConfigModel.getAttendanceDateColumn();
		int column_shift = coreConfigModel.getAttendanceShiftColumn();
		int column_record = coreConfigModel.getAttendanceRecordColumn();
		
		return new XlsOriginalAttendanceDataLoader(in, fileName, row_start, column_department, 
				column_workNumber, column_name, column_date, column_shift, column_record);
	}
	
	/**
	 * ����һ���µ� xls ԭʼ����Ʊ�ݶ�ȡ����
	 * @param fileSelectModel ָ�����ļ�ѡ��ģ�͡�
	 * @param coreConfigModel ָ���ĺ�������ģ�͡�
	 * @param jobModel ����ģ�͡�
	 * @return ��ָ����ģ����ɵ� xls ԭʼ�������ݶ�ȡ����
	 * @throws FileNotFoundException �ļ�δ�ҵ��쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static XlsOriginalWorkticketDataLoader newXlsOriginalWorkticketDataLoader(FileSelectModel fileSelectModel,
			CoreConfigModel coreConfigModel, JobModel jobModel) throws FileNotFoundException {
		Objects.requireNonNull(fileSelectModel, "��ڲ��� fileSelectModel ����Ϊ null��");
		Objects.requireNonNull(coreConfigModel, "��ڲ��� coreConfigModel ����Ϊ null��");
		Objects.requireNonNull(jobModel, "��ڲ��� jobModel ����Ϊ null��");

		InputStream in = new FileInputStream(fileSelectModel.getWorkticketFile());
		String fileName = fileSelectModel.getWorkticketFile().getName();
		int row_start = coreConfigModel.getWorkticketStartRow();
		int column_department = coreConfigModel.getWorkticketDepartmentColumn();
		int column_workNumber = coreConfigModel.getWorkticketWorkNumberColumn();
		int column_name = coreConfigModel.getAttendacneNameColumn();
		Job[] jobs = new Job[jobModel.size()];
		int i = 0;
		for(Job job : jobModel){
			jobs[i++] = job;
		}

		return new XlsOriginalWorkticketDataLoader(in, fileName, row_start, column_department, column_workNumber,
				column_name, jobs);
	}
	
	/**
	 * ��ʱ���ʽ���� 12:45��ת���ɸ�ʱ������������������Сʱ��
	 * @param string ָ����ʱ���ı���
	 * @return ��ʱ���ʽ���� 12:45��ת���ɸ�ʱ������������������Сʱ��
	 */
	public static double string2Hour(String string){
		String[] strs = string.split(":");
		if(strs.length == 1){
			return Integer.parseInt(string);
		}else if(strs.length == 2){
			int hour = Integer.parseInt(strs[0]);
			int minute = Integer.parseInt(strs[1]);
			return NumberUtil.unitTrans((double) minute, Time.MIN, Time.HOR).doubleValue() + hour;
		}else{
			throw new IllegalArgumentException("ʱ�乤�� - ��Ч�Ĵ��������" + string);
		}
	}
	
	//��ֹ�ⲿʵ������
	private CountUtil(){}
}

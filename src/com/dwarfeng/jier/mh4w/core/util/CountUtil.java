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
 *与统计有关工具方法。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class CountUtil {

	/**
	 * 将 xls 表格中的列名转换成列的序号。
	 * @param column 列名称。
	 * @return 列名称对应的序号。
	 */
	public static int columnString2Int(String column) {
		Objects.requireNonNull(column, "入口参数 input 不能为 null。");
		column = column.toUpperCase();
		if(column.matches("[^A-Z]")){
			throw new IllegalArgumentException("字符只能包含A-Z");
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
	 * 返回一个新的 xls 原始出勤数据读取器。
	 * @param fileSelectModel 指定的文件选择模型。
	 * @param coreConfigModel 指定的核心配置模型。
	 * @return 由指定的模型组成的 xls 原始出勤数据读取器。
	 * @throws FileNotFoundException 文件未找到异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static XlsOriginalAttendanceDataLoader newXlsOriginalAttendanceDataLoader(
			FileSelectModel fileSelectModel, CoreConfigModel coreConfigModel) throws FileNotFoundException{
		Objects.requireNonNull(fileSelectModel, "入口参数 fileSelectModel 不能为 null。");
		Objects.requireNonNull(coreConfigModel, "入口参数 coreConfigModel 不能为 null。");

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
	 * 返回一个新的 xls 原始出工票据读取器。
	 * @param fileSelectModel 指定的文件选择模型。
	 * @param coreConfigModel 指定的核心配置模型。
	 * @param jobModel 工作模型。
	 * @return 由指定的模型组成的 xls 原始出勤数据读取器。
	 * @throws FileNotFoundException 文件未找到异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static XlsOriginalWorkticketDataLoader newXlsOriginalWorkticketDataLoader(FileSelectModel fileSelectModel,
			CoreConfigModel coreConfigModel, JobModel jobModel) throws FileNotFoundException {
		Objects.requireNonNull(fileSelectModel, "入口参数 fileSelectModel 不能为 null。");
		Objects.requireNonNull(coreConfigModel, "入口参数 coreConfigModel 不能为 null。");
		Objects.requireNonNull(jobModel, "入口参数 jobModel 不能为 null。");

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
	 * 将时间格式（如 12:45）转换成该时间相对于零点所经过的小时。
	 * @param string 指定的时间文本。
	 * @return 将时间格式（如 12:45）转换成该时间相对于零点所经过的小时。
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
			throw new IllegalArgumentException("时间工具 - 无效的传入参数：" + string);
		}
	}
	
	//禁止外部实例化。
	private CountUtil(){}
}

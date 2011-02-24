package com.dwarfeng.jier.mh4w.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Objects;

import com.dwarfeng.dutil.basic.num.NumberUtil;
import com.dwarfeng.dutil.basic.num.unit.Time;
import com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel;
import com.dwarfeng.jier.mh4w.core.model.cm.FileSelectModel;
import com.dwarfeng.jier.mh4w.core.model.cm.JobModel;
import com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel;
import com.dwarfeng.jier.mh4w.core.model.io.XlsOriginalAttendanceDataLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XlsOriginalWorkticketDataLoader;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultAttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultWorkticketData;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalAttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalWorkticketData;
import com.dwarfeng.jier.mh4w.core.model.struct.Shift;
import com.dwarfeng.jier.mh4w.core.model.struct.Staff;
import com.dwarfeng.jier.mh4w.core.model.struct.TimeSection;
import com.dwarfeng.jier.mh4w.core.model.struct.TransException;
import com.dwarfeng.jier.mh4w.core.model.struct.WorkticketData;

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
	
	/**
	 * 将原始考勤数据转换成考勤数据。
	 * @param rawData 指定的原始数据。
	 * @param shiftModel 班次模型。
	 * @return 由指定的原始数据转化成的考勤数据。
	 * @throws TransException 转化过程发生异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static AttendanceData transAttendanceData(OriginalAttendanceData rawData, ShiftModel shiftModel) throws TransException{
		Objects.requireNonNull(rawData, "入口参数 rawData 不能为 null。");
		Objects.requireNonNull(shiftModel, "入口参数 shiftModel 不能为 null。");

		try{
			Staff staff = transStaff(rawData.getWorkNumber(), rawData.getDepartment(), rawData.getName());
			CountDate countDate = transCountDate(rawData.getDate());
			Shift shift = transShift(rawData.getShift(), shiftModel);
			TimeSection attendanceRecord = transTimeSection(rawData.getAttendanceRecord());
			
			AttendanceData data = new DefaultAttendanceData(staff, countDate, shift, attendanceRecord);
			return data;
		}catch (Exception e) {
			throw new TransException("无法将指定的原始考勤数据转化为考勤数据", e);
		}
			
	}
	
	/**
	 * 将原始工票数据转换成工票数据。
	 * @param rawData 指定的原始工票数据。
	 * @return 由指定的原始工票数据转换成的工票数据。
	 * @throws TransException 转化过程发生异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static WorkticketData transWorkticketData(OriginalWorkticketData rawData) throws TransException{
		Objects.requireNonNull(rawData, "入口参数 rawData 不能为 null。");
		try{
			Staff staff = transStaff(rawData.getWorkNumber(), rawData.getDepartment(), rawData.getName());
			Job job = rawData.getJob();
			double workticket = Double.parseDouble(rawData.getWorkticket());
			
			return new DefaultWorkticketData(staff, job, workticket);
		}catch (Exception e) {
			throw new TransException("无法将指定的原始工票数据转化为工票数据", e);
		}
	}
	
	/**
	 * 将指定的字符转换为员工。
	 * @param workNumber 工号字符串。
	 * @param department 部门字符串。
	 * @param name 姓名字符串。
	 * @return 指定的字符转换的员工。
	 * @throws TransException 转换异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static Staff transStaff(String workNumber, String department, String name) throws TransException{
		Objects.requireNonNull(workNumber, "入口参数 workNumber 不能为 null。");
		Objects.requireNonNull(department, "入口参数 department 不能为 null。");
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");

		return new Staff(workNumber, department, name);
	}
	
	/**
	 * 将指定的字符串转换为日期。
	 * @param dateString 指定的日期字符串。
	 * @return 由指定的字符串转化而来的日期。
	 * @throws TransException 转换异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static CountDate transCountDate(String dateString) throws TransException{
		Objects.requireNonNull(dateString, "入口参数 dateString 不能为 null。");

		try{
			String[] strs = dateString.split("-");
			int year = Integer.parseInt(strs[0]);
			int month = Integer.parseInt(strs[1]);
			int day = Integer.parseInt(strs[2]);
			return new CountDate(year, month, day);
		}catch (Exception e) {
			throw new TransException("无法将指定的字符转换为字符串 : " + dateString, e);
		}
	}
	
	/**
	 * 将指定的字符串转化为班次。
	 * @param shift 指定的字符串。
	 * @param shiftModel 指定的班次模型。
	 * @return 由指定的字符串转化而成的班次。
	 * @throws TransException 转换异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static Shift transShift(String shift, ShiftModel shiftModel) throws TransException{
		Objects.requireNonNull(shiftModel, "入口参数 shiftModel 不能为 null。");
		
		try{
			for(Shift referanceShift : shiftModel){
				if(referanceShift.getName().equals(shift)){
					return referanceShift;
				}
			}
			throw new NoSuchElementException();
		}catch (Exception e) {
			throw new TransException("无法将指定的班次文本转换为班次" + shift, e);
		}
	}
	
	/**
	 * 将指定的字符串转换为时间区间。
	 * @param string 指定的字符串。
	 * @return 由指定的字符串转化而成的时间区间。
	 * @throws TransException 转换异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static TimeSection transTimeSection(String string) throws TransException{
		try{
			String[] strs = string.split("-");
			String[] f = strs[0].split(":");
			String[] l = strs[1].split(":");
			
			double start = Double.parseDouble(f[0]) + NumberUtil.unitTrans(Double.parseDouble(f[1]), Time.MIN, Time.HOR).doubleValue();
			double end = Double.parseDouble(l[0]) + NumberUtil.unitTrans(Double.parseDouble(l[1]), Time.MIN, Time.HOR).doubleValue();

			return new TimeSection(start, end);
		}catch (Exception e) {
			throw new TransException("无法将指定的时间区间文本转换为班次" + string, e);
		}
	}
	
	
	
	//禁止外部实例化。
	private CountUtil(){}
}

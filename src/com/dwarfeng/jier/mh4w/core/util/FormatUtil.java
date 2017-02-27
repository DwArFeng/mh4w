package com.dwarfeng.jier.mh4w.core.util;

import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;
import com.dwarfeng.jier.mh4w.core.model.struct.DataFromXls;
import com.dwarfeng.jier.mh4w.core.model.struct.Person;
import com.dwarfeng.jier.mh4w.core.model.struct.TimeSection;

/**
 * 格式化数据工具类。
 * <p> 用于将某些结构进行格式化输出。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class FormatUtil {

	/**
	 * 格式化输出员工。
	 * @param person 指定的员工。
	 * @return 员工的格式化输出。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static String formatPerson(Person person){
		Objects.requireNonNull(person, "入口参数 person 不能为 null。");
		return String.format("%s - %s - %s", person.getName(), person.getWorkNumber(), person.getDepartment());
	}
	
	/**
	 * 格式化输出时间区间。
	 * @param timeSection 指定的时间区间。
	 * @return 时间区间的格式化输出。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static String formatTimeSection(TimeSection timeSection){
		Objects.requireNonNull(timeSection, "入口参数 timeSection 不能为 null。");

		int a1 = (int) timeSection.getStart();
		int a2 = (int)(timeSection.getStart() * 60) % 60;
		int a3 = (int) timeSection.getEnd();
		int a4 =  (int)(timeSection.getEnd() * 60) % 60;
		
		return String.format("%02d:%02d - %02d:%02d", a1, a2, a3, a4);
	}
	
	/**
	 * 格式化 DataFromXls 对象。
	 * @param dataFromXls 指定的 DataFromXls 对象。
	 * @return DataFromXls 对象的格式化输出。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static String formatDataFromXls(DataFromXls dataFromXls){
		Objects.requireNonNull(dataFromXls, "入口参数 dataFromXls 不能为 null。");
		return String.format("%s:%d", dataFromXls.getFileName(), dataFromXls.getRow());
	}
	
	/**
	 * 格式化统计日期对象。
	 * @param countDate 指定的统计日期。
	 * @return 指定的日期对象的格式化输出。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static String formatCountDate(CountDate countDate){
		Objects.requireNonNull(countDate, "入口参数 countDate 不能为 null。");
		
		return String.format("%04d - %02d - %02d", countDate.getYear(), countDate.getMonth(), countDate.getDay());
	}
	
	/**
	 * 格式化双精度浮点数。
	 * @param value 指定的双精度浮点数。
	 * @return 指定的双精度浮点数的格式化输出。
	 */
	public static String formatDouble(double value){
		return String.format("%.2f", value);
	}
}

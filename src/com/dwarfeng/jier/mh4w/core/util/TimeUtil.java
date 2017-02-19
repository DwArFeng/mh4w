package com.dwarfeng.jier.mh4w.core.util;

/**
 * 有关于时间的工具。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class TimeUtil {
	
	/**
	 * 将时间格式（比如 12:45）转换成分钟。
	 * @param str 指定的时间格式。
	 * @return 时间格式对应的分钟。
	 */
	public static int parseMinute(String str){
		String[] strs = str.split(":");
		if(strs.length == 1){
			return Integer.parseInt(str);
		}else if(strs.length == 2){
			int hour = Integer.parseInt(strs[0]);
			int minute = Integer.parseInt(strs[1]);
			return hour * 60 + minute;
		}else{
			throw new IllegalArgumentException("时间工具 - 无效的传入参数：" + str);
		}
	}
	
	//禁止外部实例化。
	private TimeUtil(){}

}

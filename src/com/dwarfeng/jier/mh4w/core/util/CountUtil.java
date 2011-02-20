package com.dwarfeng.jier.mh4w.core.util;

import java.util.Objects;

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
	
	//禁止外部实例化。
	private CountUtil(){}
}

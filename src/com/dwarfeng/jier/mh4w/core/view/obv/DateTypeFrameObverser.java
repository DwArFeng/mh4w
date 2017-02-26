package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;

/**
 * 日期类型界面观察器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface DateTypeFrameObverser extends Obverser {

	/**
	 * 通知隐藏日期类型界面。
	 */
	public void fireHideDateTypeFrame();

	/**
	 * 通知提交指定的日期类型入口。
	 * @param key 入口的键。
	 * @param value 入口的值。
	 */
	public void fireSubmitDateTypeEntry(CountDate key, DateType value);

	/**
	 * 通知移除指定的日期类型入口。
	 * @param key 入口的键。
	 */
	public void fireRemoveDateTypeEntry(CountDate key);

	/**
	 * 通知清除日期类型入口。
	 */
	public void fireClearDateTypeEntry();

	/**
	 * 通知保存日期类型入口。
	 */
	public void fireSaveDateTypeEntry();

	/**
	 * 通知读取日期类型入口。
	 */
	public void fireLoadDateTypeEntry();

}

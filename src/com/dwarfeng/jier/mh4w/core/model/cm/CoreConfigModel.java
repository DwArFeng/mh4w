package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Locale;

/**
 * 核心配置模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface CoreConfigModel extends SyncConfigModel {
	
	/**
	 * 获取记录器多语言接口的当前语言。
	 * @return 记录器多语言接口当前的语言。
	 */
	public Locale getLoggerMutilangLocale();
	
	/**
	 * 获取标签多语言接口的当前语言。
	 * @return 标签多语言接口的当前语言。
	 */
	public Locale getLabelMutilangLocale();

}

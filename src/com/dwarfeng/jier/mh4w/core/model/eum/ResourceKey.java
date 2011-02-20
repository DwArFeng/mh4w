package com.dwarfeng.jier.mh4w.core.model.eum;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 有关于资源的键。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public enum ResourceKey implements Name{
	
	/**记录器设置*/
	LOGGER_SETTING("logger.setting"),
	
	/**主程序的配置*/
	CONFIGURATION_CORE("configuration.core"),
	
	/**模态配置*/
	CONFIGURATION_MODAL("configuration.modal"),
	
	/**记录器多语言化的设置*/
	MUTILANG_LOGGER_SETTING("mutilang.logger.setting"),
	
	/**记录器多语言化的设置*/
	MUTILANG_LABEL_SETTING("mutilang.label.setting"),
	
	/**工具信息*/
	TOOL_INFO("tool.info"),
	
	/**工具库*/
	TOOL_LIB("tool.lib"),
	
	/**工具数据*/
	TOOL_DATA("tool.data"),
	
	/**工具历史*/
	TOOL_HISTORY("tool.history"),
	
	/**班次信息*/
	SHIFT_SHIFTS("define.shifts")
	
	;

	private final String name;
	
	private ResourceKey(String name) {
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
}

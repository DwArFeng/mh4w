package com.dwarfeng.jier.mh4w.core.model.eum;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * �й�����Դ�ļ���
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public enum ResourceKey implements Name{
	
	/**��¼������*/
	LOGGER_SETTING("logger.setting"),
	
	/**�����������*/
	CONFIGURATION_CORE("configuration.core"),
	
	/**ģ̬����*/
	CONFIGURATION_MODAL("configuration.modal"),
	
	/**��¼�������Ի�������*/
	MUTILANG_LOGGER_SETTING("mutilang.logger.setting"),
	
	/**��¼�������Ի�������*/
	MUTILANG_LABEL_SETTING("mutilang.label.setting"),
	
	/**������Ϣ*/
	TOOL_INFO("tool.info"),
	
	/**���߿�*/
	TOOL_LIB("tool.lib"),
	
	/**��������*/
	TOOL_DATA("tool.data"),
	
	/**������ʷ*/
	TOOL_HISTORY("tool.history"),
	
	/**�����Ϣ*/
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

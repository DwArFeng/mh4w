package com.dwarfeng.jier.mh4w.core.model.eum;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.ConfigEntry;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.checker.NonNullConfigChecker;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultConfigEntry;

/**
 * ��������ö�١�
 * <p> ��ö�ټ�¼��������ʱ����Ҫ�����е����á�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public enum CoreConfig implements ConfigEntry{
	
	/**��¼����ʹ������*/
	MUTILANG_LOGGER("mutilang.logger", "", new NonNullConfigChecker()),
	
	/**��ǩ��ʹ������*/
	MUTILANG_LABEL("mutilang.label", "", new NonNullConfigChecker()),
	
	;
	
	private final ConfigEntry configEntry;
	
	private CoreConfig(String keyName, String defaultValue, ConfigChecker checker) {
		this.configEntry = new DefaultConfigEntry(keyName, defaultValue, checker);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigEntry#getConfigKey()
	 */
	@Override
	public ConfigKey getConfigKey() {
		return configEntry.getConfigKey();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigEntry#getConfigFirmProps()
	 */
	@Override
	public ConfigFirmProps getConfigFirmProps() {
		return configEntry.getConfigFirmProps();
	}

}

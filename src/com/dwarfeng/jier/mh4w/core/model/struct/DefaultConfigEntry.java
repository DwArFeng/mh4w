package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.ConfigEntry;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;

/**
 * 默认配置入口。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultConfigEntry implements ConfigEntry{
	
	private final String keyName;
	private final String defaultValue;
	private final ConfigChecker checker;
	private final ConfigFirmProps configFirmProps = new ConfigFirmProps() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dutil.develop.cfg.ConfigFirmProps#getDefaultValue()
		 */
		@Override
		public String getDefaultValue() {
			return defaultValue;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dutil.develop.cfg.ConfigFirmProps#getConfigChecker()
		 */
		@Override
		public ConfigChecker getConfigChecker() {
			return checker;
		}
	};
	
	/**
	 * 生成一个新的默认配置入口。
	 * @param keyName 配置键名称。
	 * @param defaultValue 默认值。
	 * @param checker 值检查器。
	 * @throws NullPointerException 配置键名称或者值检查器为 <code>null</code>。
	 * @throws IllegalArgumentException 指定的默认值不能通过指定的值检查器的检查。
	 */
	public DefaultConfigEntry(String keyName, String defaultValue, ConfigChecker checker) {
		Objects.requireNonNull(keyName, "入口参数 keyName 不能为 null。");
		Objects.requireNonNull(checker, "入口参数 checker 不能为 null。");
		
		if(checker.nonValid(defaultValue)) throw new IllegalArgumentException("指定的默认值不能通过值检查器");
		
		this.keyName = keyName;
		this.defaultValue = defaultValue;
		this.checker = checker;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigEntry#getConfigKey()
	 */
	@Override
	public ConfigKey getConfigKey() {
		return new ConfigKey(keyName);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.develop.cfg.ConfigEntry#getConfigFirmProps()
	 */
	@Override
	public ConfigFirmProps getConfigFirmProps() {
		return configFirmProps;
	}

}

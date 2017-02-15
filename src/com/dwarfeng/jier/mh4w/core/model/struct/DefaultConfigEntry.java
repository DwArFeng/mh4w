package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.ConfigEntry;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;

/**
 * Ĭ��������ڡ�
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
	 * ����һ���µ�Ĭ��������ڡ�
	 * @param keyName ���ü����ơ�
	 * @param defaultValue Ĭ��ֵ��
	 * @param checker ֵ�������
	 * @throws NullPointerException ���ü����ƻ���ֵ�����Ϊ <code>null</code>��
	 * @throws IllegalArgumentException ָ����Ĭ��ֵ����ͨ��ָ����ֵ������ļ�顣
	 */
	public DefaultConfigEntry(String keyName, String defaultValue, ConfigChecker checker) {
		Objects.requireNonNull(keyName, "��ڲ��� keyName ����Ϊ null��");
		Objects.requireNonNull(checker, "��ڲ��� checker ����Ϊ null��");
		
		if(checker.nonValid(defaultValue)) throw new IllegalArgumentException("ָ����Ĭ��ֵ����ͨ��ֵ�����");
		
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

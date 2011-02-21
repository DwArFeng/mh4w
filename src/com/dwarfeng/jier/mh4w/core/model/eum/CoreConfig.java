package com.dwarfeng.jier.mh4w.core.model.eum;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.ConfigEntry;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.checker.IntegerConfigChecker;
import com.dwarfeng.dutil.develop.cfg.checker.MatchConfigChecker;
import com.dwarfeng.dutil.develop.cfg.checker.NonNullConfigChecker;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultConfigEntry;

/**
 * ��������ö�١�
 * <p> ��ö�ټ�¼��������ʱ����Ҫ�����е����á�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public enum CoreConfig implements ConfigEntry{
	
	/**���ڱ�ĵ�һ��������*/
	ATTENDANCE_ROW_START("attendance.row.start", "2", new IntegerConfigChecker(1, Integer.MAX_VALUE), LabelStringKey.CoreConfig_3),
	/**���ڱ��в������ڵ���*/
	ATTENDANCE_COLUMN_DEPARTMENT("attendance.column.department", "A", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_4),
	/**���ڱ��й������ڵ���*/
	ATTENDANCE_COLUMN_WORKNUMBER("attendance.column.worknumber", "B", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_5),
	/**���ڱ����������ڵ���*/
	ATTENDANCE_COLUMN_NAME("attendance.column.name", "C", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_6),
	/**���ڱ����������ڵ���*/
	ATTENDANCE_COLUMN_DATE("attendance.column.date", "D", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_7),
	/**���ڱ��а�����ڵ���*/
	ATTENDANCE_COLUMN_SHIFT("attendance.column.shift", "E", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_8),
	/**���ڱ��м�¼���ڵ���*/
	ATTENDANCE_COLUMN_RECORD("attendance.column.record", "F", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_9),
	/**��Ʊ��ĵ�һ��������*/
	WORKTICKET_ROW_START("workticket.row.start", "2", new IntegerConfigChecker(1, Integer.MAX_VALUE), LabelStringKey.CoreConfig_10),
	/**��Ʊ���в������ڵ���*/
	WORKTICKET_COLUMN_DEPARTMENT("workticket.column.department", "A", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_11),
	/**��Ʊ���й������ڵ���*/
	WORKTICKET_COLUMN_WORKNUMBER("workticket.column.worknumber", "B", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_12),
	/**��Ʊ�����������ڵ���*/
	WORKTICKET_COLUMN_NAME("workticket.column.name", "C", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_13),
	/**��ǩ��ʹ������*/
	MUTILANG_LABEL("mutilang.label", "", new NonNullConfigChecker(), LabelStringKey.CoreConfig_1),
	/**��¼����ʹ������*/
	MUTILANG_LOGGER("mutilang.logger", "", new NonNullConfigChecker(), LabelStringKey.CoreConfig_2),
	
	;
	
	private final ConfigEntry configEntry;
	private final LabelStringKey labelStringKey;
	
	private CoreConfig(String keyName, String defaultValue, ConfigChecker checker, LabelStringKey labelStringKey) {
		this.configEntry = new DefaultConfigEntry(keyName, defaultValue, checker);
		this.labelStringKey = labelStringKey;
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
	
	/**
	 * ��ȡ�ú������õı�ǩ�ı���������ʾ��
	 * @return �������õı�ǩ�ı���
	 */
	public LabelStringKey getLabelStringKey(){
		return labelStringKey;
	}

}

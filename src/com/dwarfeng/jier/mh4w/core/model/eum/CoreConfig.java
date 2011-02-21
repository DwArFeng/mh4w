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
 * 程序配置枚举。
 * <p> 此枚举记录程序运行时所需要的所有的配置。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public enum CoreConfig implements ConfigEntry{
	
	/**考勤表的第一行数据行*/
	ATTENDANCE_ROW_START("attendance.row.start", "2", new IntegerConfigChecker(1, Integer.MAX_VALUE), LabelStringKey.CoreConfig_3),
	/**考勤表中部门所在的列*/
	ATTENDANCE_COLUMN_DEPARTMENT("attendance.column.department", "A", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_4),
	/**考勤表中工号所在的列*/
	ATTENDANCE_COLUMN_WORKNUMBER("attendance.column.worknumber", "B", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_5),
	/**考勤表中姓名所在的列*/
	ATTENDANCE_COLUMN_NAME("attendance.column.name", "C", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_6),
	/**考勤表中日期所在的列*/
	ATTENDANCE_COLUMN_DATE("attendance.column.date", "D", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_7),
	/**考勤表中班次所在的列*/
	ATTENDANCE_COLUMN_SHIFT("attendance.column.shift", "E", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_8),
	/**考勤表中记录所在的列*/
	ATTENDANCE_COLUMN_RECORD("attendance.column.record", "F", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_9),
	/**工票表的第一行数据行*/
	WORKTICKET_ROW_START("workticket.row.start", "2", new IntegerConfigChecker(1, Integer.MAX_VALUE), LabelStringKey.CoreConfig_10),
	/**工票表中部门所在的列*/
	WORKTICKET_COLUMN_DEPARTMENT("workticket.column.department", "A", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_11),
	/**工票表中工号所在的列*/
	WORKTICKET_COLUMN_WORKNUMBER("workticket.column.worknumber", "B", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_12),
	/**工票表中姓名所在的列*/
	WORKTICKET_COLUMN_NAME("workticket.column.name", "C", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_13),
	/**标签的使用语言*/
	MUTILANG_LABEL("mutilang.label", "", new NonNullConfigChecker(), LabelStringKey.CoreConfig_1),
	/**记录器的使用语言*/
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
	 * 获取该核心配置的标签文本，用于显示。
	 * @return 核心配置的标签文本。
	 */
	public LabelStringKey getLabelStringKey(){
		return labelStringKey;
	}

}

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
 * @since 0.0.1-beta
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
	/**考勤表中记录超过一天的关键字*/
	ATTENDANCE_KEYWORD_OVERDAY("attendance.keyword.overday", "次日", new NonNullConfigChecker(), LabelStringKey.CoreConfig_18),
    /**
     * 工票表的第一行数据行
     */
    WORKTICKET_ROW_START("workticket.row.start", "2", new IntegerConfigChecker(1, Integer.MAX_VALUE), LabelStringKey.CoreConfig_10),
    /**
     * 工票表中部门所在的列
     */
    WORKTICKET_COLUMN_DEPARTMENT("workticket.column.department", "A", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_11),
    /**
     * 工票表中工号所在的列
     */
    WORKTICKET_COLUMN_WORKNUMBER("workticket.column.worknumber", "B", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_12),
    /**
     * 工票表中姓名所在的列
     */
    WORKTICKET_COLUMN_NAME("workticket.column.name", "C", new MatchConfigChecker("[A-Z]+"), LabelStringKey.CoreConfig_13),
    /**
     * 正常上班的工时系数
     */
    COUNT_COEFFICIENT_SHIFT("count.coefficient.shift", "4.0", new MatchConfigChecker("^(\\-|\\+)?[\\d]{1,10}\\.[\\d]{1,10}$"), LabelStringKey.CoreConfig_14),
    /**
     * 一阶段拖班时的工作系数
     */
    COUNT_COEFFICIENT_EXTRA_PHASE1("count.coefficient.extra.phase1", "5.1", new MatchConfigChecker("^(\\-|\\+)?[\\d]{1,10}\\.[\\d]{1,10}$"), LabelStringKey.CoreConfig_15),
    /**
     * 二阶段拖班时的工作系数
     */
    COUNT_COEFFICIENT_EXTRA_PHASE2("count.coefficient.extra.phase2", "5.2", new MatchConfigChecker("^(\\-|\\+)?[\\d]{1,10}\\.[\\d]{1,10}$"), LabelStringKey.CoreConfig_19),
    /**
     * 三阶段拖班时的工作系数
     */
    COUNT_COEFFICIENT_EXTRA_PHASE3("count.coefficient.extra.phase3", "5.3", new MatchConfigChecker("^(\\-|\\+)?[\\d]{1,10}\\.[\\d]{1,10}$"), LabelStringKey.CoreConfig_20),
    /**
     * 四阶段拖班时的工作系数
     */
    COUNT_COEFFICIENT_EXTRA_PHASE4("count.coefficient.extra.phase4", "5.4", new MatchConfigChecker("^(\\-|\\+)?[\\d]{1,10}\\.[\\d]{1,10}$"), LabelStringKey.CoreConfig_21),
    /**
     * 周末上班的工时系数
     */
    COUNT_COEFFICIENT_WEEKEND("count.coefficient.weekend", "6.0", new MatchConfigChecker("^(\\-|\\+)?[\\d]{1,10}\\.[\\d]{1,10}$"), LabelStringKey.CoreConfig_16),
    /**
     * 节假日上班的工时系数
     */
    COUNT_COEFFICIENT_HOLIDAY("count.coefficient.holiday", "7.0", new MatchConfigChecker("^(\\-|\\+)?[\\d]{1,10}\\.[\\d]{1,10}$"), LabelStringKey.CoreConfig_17),
    /**
     * 标签的使用语言
     */
    MUTILANG_LABEL("mutilang.label", "", new NonNullConfigChecker(), LabelStringKey.CoreConfig_1),
    /**
     * 记录器的使用语言
     */
    MUTILANG_LOGGER("mutilang.logger", "", new NonNullConfigChecker(), LabelStringKey.CoreConfig_2),
    ;

    private final ConfigEntry configEntry;
    private final LabelStringKey labelStringKey;

    CoreConfig(String keyName, String defaultValue, ConfigChecker checker, LabelStringKey labelStringKey) {
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

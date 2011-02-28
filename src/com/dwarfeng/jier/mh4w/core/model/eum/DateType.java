package com.dwarfeng.jier.mh4w.core.model.eum;

/**
 * 日期类型。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public enum DateType {

	/**正常上班*/
	NORMAL(LabelStringKey.DateType_1),
	/**周末*/
	WEEKEND(LabelStringKey.DateType_2),
	/**法定假日*/
	HOLIDAY(LabelStringKey.DateType_3),
	
	;
	private final LabelStringKey labelStringKey;
	
	private DateType(LabelStringKey labelStringKey) {
		this.labelStringKey = labelStringKey;
	}

	/**
	 * 获取该核心配置的标签文本，用于显示。
	 * @return 核心配置的标签文本。
	 */
	public LabelStringKey getLabelStringKey(){
		return labelStringKey;
	}
}

package com.dwarfeng.jier.mh4w.core.model.eum;

/**
 * 失败类型枚举。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public enum FailType {

	/**数据结构出错*/
	DATA_STRUCT_FAIL(LabelStringKey.FailType_1),
	/**人员信息不一致*/
	PERSON_UNCONSISTENT(LabelStringKey.FailType_2),
	/**人员信息不匹配*/
	PERSON_UNMATCH(LabelStringKey.FailType_3),
	
	;
	
	private final LabelStringKey labelStringKey;
	
	private FailType(LabelStringKey labelStringKey) {
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

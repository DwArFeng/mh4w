package com.dwarfeng.jier.mh4w.core.model.eum;

/**
 * �������͡�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public enum DateType {

	/**�����ϰ�*/
	NORMAL(LabelStringKey.DateType_1),
	/**��ĩ*/
	WEEKEND(LabelStringKey.DateType_2),
	/**��������*/
	HOLIDAY(LabelStringKey.DateType_3),
	
	;
	private final LabelStringKey labelStringKey;
	
	private DateType(LabelStringKey labelStringKey) {
		this.labelStringKey = labelStringKey;
	}

	/**
	 * ��ȡ�ú������õı�ǩ�ı���������ʾ��
	 * @return �������õı�ǩ�ı���
	 */
	public LabelStringKey getLabelStringKey(){
		return labelStringKey;
	}
}

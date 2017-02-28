package com.dwarfeng.jier.mh4w.core.model.eum;

/**
 * ʧ������ö�١�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public enum FailType {

	/**���ݽṹ����*/
	DATA_STRUCT_FAIL(LabelStringKey.FailType_1),
	/**��Ա��Ϣ��һ��*/
	PERSON_UNCONSISTENT(LabelStringKey.FailType_2),
	/**��Ա��Ϣ��ƥ��*/
	PERSON_UNMATCH(LabelStringKey.FailType_3),
	
	;
	
	private final LabelStringKey labelStringKey;
	
	private FailType(LabelStringKey labelStringKey) {
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

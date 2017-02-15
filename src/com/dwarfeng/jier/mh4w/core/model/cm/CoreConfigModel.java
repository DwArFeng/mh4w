package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Locale;

/**
 * ��������ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface CoreConfigModel extends SyncConfigModel {
	
	/**
	 * ��ȡ��¼�������Խӿڵĵ�ǰ���ԡ�
	 * @return ��¼�������Խӿڵ�ǰ�����ԡ�
	 */
	public Locale getLoggerMutilangLocale();
	
	/**
	 * ��ȡ��ǩ�����Խӿڵĵ�ǰ���ԡ�
	 * @return ��ǩ�����Խӿڵĵ�ǰ���ԡ�
	 */
	public Locale getLabelMutilangLocale();

}

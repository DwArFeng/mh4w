package com.dwarfeng.jier.mh4w.core.model.obv;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.LoggerInfo;

/**
 * ��¼���۲�����
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface LoggerObverser extends Obverser{
	
	/**
	 * ֪ͨģ���еļ�¼�������ķ����˸ı䡣
	 * @param oldOne �ɵļ�¼�������ġ�
	 * @param newOne  �µļ�¼�������ġ�
	 */
	public void fireLoggerContextChanged(LoggerContext oldOne, LoggerContext newOne);
	
	/**
	 * ֪ͨģ���еļ�¼����Ϣ����ӡ�
	 * @param loggerInfo ����ӵļ�¼����Ϣ��
	 */
	public void fireLoggerInfoAdded(LoggerInfo loggerInfo);
	
	/**
	 * ֪ͨģ���еļ�¼����Ϣ���Ƴ���
	 * @param loggerInfo ���Ƴ��ļ�¼����Ϣ��
	 */
	public void fireLoggerInfoRemoved(LoggerInfo loggerInfo);
	
	/**
	 * ֪ͨģ���еļ�¼����Ϣ�������
	 */
	public void fireLoggerInfoCleared();

	/**
	 * ֪ͨģ�͸��¡�
	 */
	public void fireUpdated();
}

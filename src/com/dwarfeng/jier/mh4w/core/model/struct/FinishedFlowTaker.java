package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel;

/**
 * ��ɹ���ȡ������
 * <p> �����ں�̨ģ����ȡ����ɵĹ��̣����Ҽ�¼��ָ���� Logger ֮�С�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface FinishedFlowTaker extends ExternalReadWriteThreadSafe, MutilangSupported{

	/**
	 * ��ȡ����ɹ���ȡ�������еļ�¼����
	 * @return ��ɹ���ȡ�������еļ�¼����
	 */
	public Logger getLogger();
	
	/**
	 * ���ø���ɹ���ȡ�����еļ�¼����
	 * @param logger ָ���ļ�¼����
	 * @return �ò����Ƿ�Ըü�¼������˸ı䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean setLogger(Logger logger);
	
	/**
	 * ��ȡ�ù���ȡ�����еĺ�̨ģ�͡�
	 * @return �ù���ȡ�����еĺ�̨ģ�͡�
	 */
	public BackgroundModel getBackgroundModel();
	
	/**
	 * �رո���ɹ���ȡ������
	 * <p> ���ô˷�����ȡ������ֹͣ��ָ���ĺ�̨ģ����ȡ����ɵĹ��̡�
	 */
	public void shutdown();
	
}

package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel;

/**
 * ��ɹ���ȡ������
 * <p> �����ں�̨ģ����ȡ����ɵĹ��̣����Ҽ�¼��ָ���� Logger ֮�С�
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface FinishedFlowTaker extends ExternalReadWriteThreadSafe, MutilangSupported{

	/**
	 * ��ȡ����ɹ���ȡ�������еļ�¼����
	 * @return ��ɹ���ȡ�������еļ�¼����
	 */
	public Logger getLogger();
	
	/**
	 * ���¼�¼����
	 * <p> �÷���һ�������ڼ�¼��ģ�͸��º���ɹ���ȡ����������صĸ��¡�
	 */
	public void updateLogger();
	
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

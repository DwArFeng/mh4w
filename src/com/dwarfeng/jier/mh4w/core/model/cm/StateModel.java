package com.dwarfeng.jier.mh4w.core.model.cm;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.eum.CountState;
import com.dwarfeng.jier.mh4w.core.model.obv.StateObverser;

/**
 * ͳ��״̬ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface StateModel extends ExternalReadWriteThreadSafe, ObverserSet<StateObverser>{

	/**
	 * �����Ƿ�׼���ý���ͳ�ơ�
	 * @return �Ƿ�׼���ý���ͳ��
	 */
	public boolean isReadyForCount();
	
	/**
	 * �Ƿ�׼���ý���ͳ�ơ�
	 * @param aFlag �Ƿ�׼���á�
	 */
	public void setReadyForCount(boolean aFlag);
	
	/**
	 * ��ȡͳ��״̬��
	 * @return ͳ��״̬��
	 */
	public CountState getCountState();
	
	/**
	 * ����ͳ��״̬��
	 * @param countState ָ����ͳ��״̬��
	 * @return �ò����Ƿ�ı���ģ�ͱ���
	 */
	public boolean setCountState(CountState countState);
	
	/**
	 * ͳ�ƽ���Ƿ���ڡ�
	 * @return ͳ�ƽ���Ƿ���ڡ�
	 */
	public boolean isCountResultOutdated();
	
	/**
	 * ����ͳ�ƽ���Ƿ���ڡ�
	 * @param aFlag ͳ�ƽ���Ƿ���ڡ�
	 */
	public void setCountResultOutdated(boolean aFlag);
	
}

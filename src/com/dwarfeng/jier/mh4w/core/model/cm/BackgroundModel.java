package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.BackgroundObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Flow;

/**
 * ��̨ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface BackgroundModel extends ObverserSet<BackgroundObverser>, ExternalReadWriteThreadSafe, Iterable<Flow>{
	
	/**
	 * ���ظú�̨ģ�������ڴ�����̵�ִ��������
	 * <p> ע�⣺���ص�ִ���������Ӧ�����ڲ�ѯ״̬�������������������׳� {@link UnsupportedOperationException}��
	 * @return ��̨ģ���е�ִ��������
	 */
	public ExecutorService getExecutorService();
	
	/**
	 * ���̨ģ�����ύһ�����̡�
	 * <p> ��ָ���Ľ���Ϊ <code>null</code>������ģ�����Ѿ�������ָ���Ľ���ʱ���������κβ�����
	 * @param flow ָ���Ľ��̡�
	 * @return �ò����Ƿ��ģ������˸ı䡣
	 */
	public boolean submit(Flow flow);
	
	/**
	 * ���̨ģ�����ύָ�������е����й��̡�
	 * <p> ��ָ���Ľ���Ϊ <code>null</code>������ģ�����Ѿ�������ָ���Ľ���ʱ���������κβ�����
	 * @param c ���й�����ɵļ��ϡ�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 * @return �ò��������Ƿ��ģ������˸ı䡣
	 */
	public boolean submitAll(Collection<? extends Flow> c);
	
	/**
	 * ���ظú�̨�����Ƿ����ָ���Ĺ��̡�
	 * @param flow ָ���Ķ���
	 * @return �ú�̨ģ�����Ƿ����ָ���Ķ���
	 */
	public boolean contains(Flow flow);
	
	/**
	 * ���غ�̨�������Ƿ����ȫ����ָ������
	 * @param c ����ָ��������ɵļ��ϡ�
	 * @return ��̨ģ�����Ƿ�������е�ָ������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean containsAll(Collection<Flow> c);
	
	/**
	 * ���ظ�ģ���Ƿ�Ϊ�ա�
	 * @return ��ģ���Ƿ�Ϊ�ա�
	 */
	public boolean isEmpty();
	
	/**
	 * ���ظ�ģ�����Ƿ�����Ѿ���ɵĹ��̡�
	 * @return �Ƿ�����Ѿ���ɵĹ��̡�
	 */
	public boolean hasFinished();
	
	/**
	 * ����������Ѿ���ɵĹ��̶������û�У���ȴ���
	 * @return ������Ѿ���ɵĹ��̶���
	 * @throws InterruptedException �ȴ��������̱߳��жϡ�
	 */
	public Flow takeFinished() throws InterruptedException;
	
	/**
	 * ���ģ�������е��Ѿ���ɵĹ��̡�
	 * @return �÷����Ƿ�ı���ģ�ͱ���
	 */
	public boolean clearFinished();
	
	/**
	 * �رոú�̨ģ�͡�
	 * <p> ��̨ģ�ͱ��رպ󣬻�ܾ����й��̵��ύ�������Ѿ��ύ�Ĺ�������Ӱ�졣
	 */
	public void shutdown();
	
}

package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * ����ƽ̨�ü�¼����
 * <p> ��������Ϊ���� log4j �е� logger �������֡�
 * <p> �����������¼�йصķ�����
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface Logger {

	/**
	 * ���ü�¼վ���trace������
	 * @param message ָ������Ϣ��
	 */
	public void trace(String message);
	
	/**
	 * ���ü�¼վ���debug������
	 * @param message ָ������Ϣ��
	 */
	public void debug(String message);
	
	/**
	 * ���ü�¼վ���info������
	 * @param message ָ������Ϣ��
	 */
	public void info(String message);
	
	/**
	 * ���ü�¼վ���warn������
	 * @param message ָ������Ϣ��
	 */
	public void warn(String message);
	
	/**
	 * ���ü�¼վ���warn������
	 * @param message ָ������Ϣ��
	 * @param t ָ���Ŀ��׳�����һ�����̻߳��쳣�ĸ��ٶ�ջ��
	 */
	public void warn(String message, Throwable t);
	
	/**
	 * ���ü�¼վ���error������
	 * @param message ָ������Ϣ��
	 * @param t ָ���Ŀ��׳�����һ�����̻߳��쳣�ĸ��ٶ�ջ��
	 */
	public void error(String message, Throwable t);
	
	/**
	 * ���ü�¼վ���fatal������
	 * @param message ָ������Ϣ��
	 * @param t ָ���Ŀ��׳�����һ�����̻߳��쳣�ĸ��ٶ�ջ��
	 */
	public void fatal(String message, Throwable t);
}

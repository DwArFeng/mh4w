package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * ������۲�����
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface MainFrameObverser extends Obverser{
	
	/**
	 * ֪ͨ����Ĺرհ�ť�������
	 */
	public void fireWindowClosing();
	
	/**
	 * ֪ͨѡ������ļ���
	 */
	public void fireSelectAttendanceFile();
	
	/**
	 * ֪ͨѡ��Ʊ�ļ���
	 */
	public void fireSelectWorkticketFile();
	
	/**
	 * ֪ͨͳ�Ʊ����á�
	 */
	public void fireCountReset();

	/**
	 * ֪ͨ������ϸ��Ϣ���档
	 */
	public void fireHideDetailFrame();

	/**
	 * ֪ͨ��ʾ��ϸ��Ϣ���档
	 */
	public void fireShowDetailFrame();

	/**
	 * ֪ͨ��ʼͳ�ơ�
	 */
	public void fireCount();

	/**
	 * ֪ͨ��ʾ������塣
	 */
	public void fireShowAttrFrame();
	
}

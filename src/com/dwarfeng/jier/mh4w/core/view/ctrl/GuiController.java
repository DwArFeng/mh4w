package com.dwarfeng.jier.mh4w.core.view.ctrl;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;

/**
 * �������������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface GuiController extends ExternalReadWriteThreadSafe{
	
	/**
	 * �½�һ������塣
	 * @return �Ƿ�ɹ��½���
	 */
	public boolean newMainFrame();
	
	/**
	 * �ͷſ������е�����塣
	 * @return �Ƿ�ɹ��ͷš�
	 */
	public boolean disposeMainFrame();
	
	/**
	 * ���ظÿ��������Ƿ�ӵ�������ʵ����
	 * @return �Ƿ�ӵ�������ʵ����
	 */
	public boolean hasMainFrame();
	
	/**
	 * ��ȡ�������Ƿ�ɼ���
	 * @return �������Ƿ�ɼ���
	 */
	public boolean getMainFrameVisible();
	
	/**
	 * �����������Ƿ�ɼ���
	 * @param aFlag �������Ƿ�ɼ���
	 * @return �Ƿ����óɹ���
	 */
	public boolean setMainFrameVisible(boolean aFlag);
	
	/**
	 * ��ȡ�ÿ������������Ķ����Խӿڡ�
	 * @return �ÿ������������Ķ����Խӿڡ�
	 */
	public Mutilang getMainFrameMutilang();
	
	/**
	 * ���øÿ������������Ķ����Խӿڡ�
	 * @param mutilang ָ���Ķ����Խӿڡ�
	 * @return �ò����Ƿ�Ըö�������˸ı䡣
	 */
	public boolean setMainFrameMutilang(Mutilang mutilang);
	
	/**
	 * ���û�ѯ��һ���������ļ���
	 * <p> �÷���������ʽ�ģ����û�ѡ�����ļ�֮ǰ����һֱ������
	 * ��ˣ��벻Ҫ�� EventQueue �߳��е������������
	 * @param directory ָ���ĸ�Ŀ¼��λ�á�
	 * @param fileFilters ָ�����ļ�ɸѡ����
	 * @param acceptAllFileFilter �Ƿ�����ѡ��ȫ���ļ���
	 * @param  mutiSelectionEnabled �Ƿ�����ѡ�����ļ���
	 * @param fileSelectionMode �ļ�ѡ��ģʽ��
	 * @return �û�ѡ����ļ���
	 */
	public File[] askFile(File directory, FileFilter[] fileFilters, boolean acceptAllFileFilter, boolean mutiSelectionEnabled,
			int fileSelectionMode);
	
	
	/**
	 * ��������ļ����ĵ��������
	 * @return �Ƿ�ɹ�ִ�С�
	 */
	public boolean attendanceClickUnlock();
	
	/**
	 * �����ʱ�ļ��汾�ĵ��������
	 * @return �Ƿ�ɹ�ִ�С�
	 */
	public boolean workticketClickUnlock();
	
	/**
	 * ������ϸ��ť��ѡ��״̬��
	 * @param value ָ����ѡ��״̬��
	 * @param isAdjusting �Ƿ����ڵ�����
	 * @return �Ƿ�ɹ�ִ�С�
	 */
	public boolean setDetailButtonSelect(boolean value, boolean isAdjusting);
	
	/**
	 * �½�һ����ϸ���档
	 * @return �Ƿ�ִ�гɹ���
	 */
	public boolean newDetailFrame();
	
	/**
	 * �ͷ���ϸ���档
	 * @return �Ƿ�ִ�гɹ���
	 */
	public boolean disposeDetialFrame();
	
	/**
	 * �Ƿ��Ѿ�ӵ����ϸ����ʵ����
	 * @return �Ƿ�ӵ����ϸ����ʵ����
	 */
	public boolean hasDetailFrame();
	
	/**
	 * ������ϸ�����Ƿ�ɼ���
	 * @return ��ϸ�����Ƿ�ɼ���
	 */
	public boolean getDetailFrameVisible();
	
	/**
	 * ������ϸ����Ŀɼ��ԡ�
	 * @param aFlag ��ϸ����Ŀɼ��ԡ�
	 * @return �Ƿ�ִ�гɹ���
	 */
	public boolean setDetailFrameVisible(boolean aFlag);
	
}

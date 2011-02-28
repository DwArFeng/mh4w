package com.dwarfeng.jier.mh4w.core.view.ctrl;

import java.io.File;
import java.util.Locale;

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
	 * ���¸ÿ������е������Ķ����Խӿڡ�
	 * @return �Ƿ���³ɹ���
	 */
	public boolean updateMainFrameMutilang();
	
	/**
	 * ���û�ѯ��һ���������ļ����ڴ򿪡�
	 * <p> �÷���������ʽ�ģ����û�ѡ�����ļ�֮ǰ����һֱ������
	 * �÷��������ڷ� EventQueue �߳��µ��á�
	 * @param directory ָ���ĸ�Ŀ¼��λ�á�
	 * @param fileFilters ָ�����ļ�ɸѡ����
	 * @param acceptAllFileFilter �Ƿ�����ѡ��ȫ���ļ���
	 * @param  mutiSelectionEnabled �Ƿ�����ѡ�����ļ���
	 * @param fileSelectionMode �ļ�ѡ��ģʽ��
	 * @param locale �ļ�ѡ������ʾ�����ԡ�
	 * @return �û�ѡ����ļ���
	 */
	public File[] askFile4Open(File directory, FileFilter[] fileFilters, boolean acceptAllFileFilter,
			boolean mutiSelectionEnabled, int fileSelectionMode, Locale locale);
	
	/**
	 * ���û�ѯ��һ���ļ����ڱ���
	 * <p> �÷���������ʽ�ģ����û�ѡ�����ļ�֮ǰ����һֱ������
	 * �÷��������ڷ� EventQueue �߳��µ��á�
	 * @param directory ָ���ĸ�Ŀ¼��λ�á�
	 * @param fileFilters ָ�����ļ�ɸѡ����
	 * @param acceptAllFileFilter �Ƿ�����ѡ��ȫ���ļ���
	 * @param defaultFileExtension ���ļ�û����չ����ʱ��ʹ�õ�Ĭ����չ����
	 * @param locale �ļ�ѡ������ʾ�����ԡ�
	 * @return �û�ѡ����ļ���
	 */
	public File askFile4Save(File directory, FileFilter[] fileFilters, boolean acceptAllFileFilter,
			String defaultFileExtension, Locale locale);
	
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
	 *֪ͨ��ͼͳ�ƹ����Ѿ�������
	 * @return �Ƿ�ɹ�֪ͨ��
	 */
	public boolean knockCountFinished();

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
	
	
	/**
	 * ��ȡ�ÿ���������ϸ���Ķ����Խӿڡ�
	 * @return �ÿ���������ϸ���Ķ����Խӿڡ�
	 */
	public Mutilang getDetailFrameMutilang();
	
	/**
	 * ���¸ÿ���������ϸ���Ķ����Խӿڡ�
	 * @return �Ƿ���³ɹ���
	 */
	public boolean updateDetailFrameMutilang();
	
	/**
	 * �½�һ�����Խ��档
	 * @return �Ƿ�ִ�гɹ���
	 */
	public boolean newAttrFrame();
	
	/**
	 * �ͷ����Խ��档
	 * @return �Ƿ�ִ�гɹ���
	 */
	public boolean disposeAttrFrame();
	
	/**
	 * �Ƿ��Ѿ�ӵ�����Խ��档
	 * @return �Ƿ��Ѿ�ӵ�����Խ��档
	 */
	public boolean hasAttrFrame();
	
	/**
	 * �������Խ����Ƿ�ɼ���
	 * @return ���Խ����Ƿ�ɼ���
	 */
	public boolean getAttrFrameVisible();
	
	/**
	 * �������Խ���Ŀɼ��ԡ�
	 * @param aFlag ���Խ��档
	 * @return �Ƿ�ִ�гɹ���
	 */
	public boolean setAttrFrameVisible(boolean aFlag);
	
	/**
	 * ��ȡ�ÿ��������������Ķ����Խӿڡ�
	 * @return �ÿ��������������Ķ����Խӿڡ�
	 */
	public Mutilang getAttrFrameMutilang();
	
	/**
	 * ���¿����������������Ķ����Խӿڡ�
	 * @return �Ƿ���³ɹ���
	 */
	public boolean updateAttrFrameMutilang();
	
	/**
	 * �½�һ��ʧ����塣
	 * @return �Ƿ�ɹ��½���
	 */
	public boolean newFailFrame();
	
	/**
	 * �ͷſ������е�ʧ����塣
	 * @return �Ƿ�ɹ��ͷš�
	 */
	public boolean disposeFailFrame();
	
	/**
	 * ���ؿ��������Ƿ�ӵ��ʧ������ʵ����
	 * @return �Ƿ�ӵ��ʧ������ʵ����
	 */
	public boolean hasFailFrame();
	
	/**
	 * ��ȡʧ������Ƿ�ɼ���
	 * @return ʧ������Ƿ�ɼ���
	 */
	public boolean getFailFrameVisible();
	
	/**
	 * ����ʧ������Ƿ�ɼ���
	 * @param aFlag ʧ������Ƿ�ɼ���
	 * @return �Ƿ����óɹ���
	 */
	public boolean setFailFrameVisible(boolean aFlag);
	
	/**
	 * ��ȡʧ������еĶ����Խӿڡ�
	 * @return ʧ������еĶ����Խӿڡ�
	 */
	public Mutilang getFailFrameMutilang();
	
	/**
	 * ���¸ÿ�������ʧ�����Ķ����Խӿڡ�
	 * @return �Ƿ���³ɹ���
	 */
	public boolean updateFailFrameMutilang();
	
	/**
	 * �½�һ������������塣
	 * @return �Ƿ�ɹ��½���
	 */
	public boolean newDateTypeFrame();
	
	/**
	 * �ͷſ������е�����������塣
	 * @return �Ƿ�ɹ��ͷš�
	 */
	public boolean disposeDateTypeFrame();
	
	/**
	 * ���ؿ��������Ƿ���������������ʵ����
	 * @return �Ƿ���������������ʵ����
	 */
	public boolean hasDateTypeFrame();
	
	/**
	 * ���ؿ������е�������������Ƿ�ɼ���
	 * @return �������е�������������Ƿ�ɼ���
	 */
	public boolean getDateTypeFrameVisible();
	
	/**
	 * ���ÿ������е������������Ŀɼ��ԡ�
	 * @param aFlag �������е���������Ƿ�ɼ���
	 * @return �Ƿ����óɹ���
	 */
	public boolean setDateTypeFrameVisible(boolean aFlag);
	
	/**
	 * ��ȡ������������еĶ����Խӿڡ�
	 * @return ��������еĶ����Խӿڡ�
	 */
	public Mutilang getDateTypeFrameMutilang();
	
	/**
	 * ���¸ÿ������������������Ķ����Խӿڡ�
	 * @return �Ƿ���³ɹ���
	 */
	public boolean updateDateTypeFrameMutilang();
	
}

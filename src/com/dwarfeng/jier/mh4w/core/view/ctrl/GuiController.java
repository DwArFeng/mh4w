package com.dwarfeng.jier.mh4w.core.view.ctrl;

import java.awt.Component;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;

/**
 * ͼ�ν��������������
 * <p> ���ڿ���ͼ�ν������档
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface GuiController<T extends Component> extends ExternalReadWriteThreadSafe{

	/**
	 * ����һ����ʵ����
	 * <p> ���ǰһ��ʵ����û�б��ͷţ��򲻽����κβ��������� <code>false</code>��
	 * <p> �÷�����Ҫ�� Swing �¼����������С�
	 * @return �ò����Ƿ�������һ����ʵ����
	 */
	public boolean newInstance();
	
	/**
	 * ���ظÿ������Ƿ��Ѿ�ӵ����һ��ʵ����
	 * @return �Ƿ��Ѿ�ӵ����һ��ʵ����
	 */
	public boolean hasInstance();
	
	/**
	 * ��ȡ�ÿ�������ʵ����
	 * <p> ���û��ʵ�����򷵻� <code>null</code>��
	 * @return �ÿ�������ʵ����
	 */
	public T getInstance();
	
	/**
	 * �ͷ�ʵ����
	 * <p> �ͷ�ʵ���󣬽�ʵ������Ϊ <code>null</code>�� ͬʱ {@link #hasInstance()} ���������� <code>false</code>��
	 * <p> �����ʱ�������е�ʵ���Ѿ����ͷ��ˣ���ʲôҲ�������ҷ��� <code>false</code>��
	 * @return ʵ���Ƿ��ͷš�
	 */
	public boolean dispose();
	
	/**
	 * ���ظÿ�����ʵ���еĶ����Ƿ�Ϊ�ɼ��ġ�
	 * <p> �����ʱ��������û��ʵ�����򷵻� <code>false</code>��
	 * @return �ÿ�����ʵ���еĶ����Ƿ�Ϊ�ɼ��ġ�
	 */
	public boolean isVisible();
	
	/**
	 * ���ÿ������е�ʵ���Ƿ�ɼ���
	 * <p> �����������û��ʵ������ʲôҲ�������ҷ��� <code>false</code>��
	 * <p> �÷�����Ҫ�� Swing �¼����������С�
	 * @return �Ƿ�ɹ������á�
	 */
	public boolean setVisible(boolean aFlag);
	
	/**
	 * չʾ�ÿ������е�ʵ����
	 * <p> ����ÿ�������û��ʵ�������½�һ��ʵ����Ȼ�󽫴�ʵ����Ϊ�ɼ���
	 * �������������ʵ������ֱ�ӽ�����Ϊ�ɼ���
	 * <p> �÷�����Ҫ�� Swing �¼����������С�
	 */
	public void show();
	
}

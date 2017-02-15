package com.dwarfeng.jier.mh4w.core.view.gui;

import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;

/**
 * ���ж����Թ��ܵĽ��档
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface MutilangComponent {
	
	/**
	 * �������еı�ǩ��
	 * <p> �ö�������ʹ�����е������ı��ֶθ���Ϊ��ڶ����Խӿ���ָ�����ı���
	 * @param labelMutilang ������µı�ǩ�����Խӿڡ�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void refreshLabels(Mutilang mutilang);

}

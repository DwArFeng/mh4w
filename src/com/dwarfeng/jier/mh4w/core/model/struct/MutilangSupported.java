package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * ������֧�ֽӿڡ�
 * <p> ʵ�ָýӿ���ζ�Ÿö�����һ������Mutilang��ʵ�ֶ����Ի��Ķ���
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface MutilangSupported {
	
	/**
	 * ��ȡ�ö����еĶ����Խӿڡ�
	 * @return �ö����еĶ����Խӿڡ�
	 */
	public Mutilang getMutilang();
	
	/**
	 * ���¶����ԡ�
	 * <p> �÷���һ�������ڶ�����ģ�͸��º�֪ͨ������֧�ֽӿڽ�����صĸ��¡�
	 */
	public void updateMutilang();

}

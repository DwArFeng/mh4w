package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;

/**
 * �����۲�����
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface JobObverser extends Obverser {
	
	/**
	 * ָ֪ͨ���Ĺ�������ӡ�
	 * @param job ָ���Ĺ�����
	 */
	public void fireJobAdded(Job job);
	
	/**
	 * ָ֪ͨ���Ĺ������Ƴ���
	 * @param job ָ���Ĺ�����
	 */
	public void fireJobRemoved(Job job);
	
	/**
	 * ֪ͨ�ù���ģ����ա�
	 */
	public void fireJobCleared();
	
}

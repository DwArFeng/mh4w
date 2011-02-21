package com.dwarfeng.jier.mh4w.core.model.cm;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.JobObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;

/**
 * ����ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface JobModel extends ExternalReadWriteThreadSafe, ObverserSet<JobObverser>, Iterable<Job>{
	
	/**
	 * ��ģ��������ָ���Ĺ�����
	 * @param job ָ���Ĺ�����
	 * @return �ò����Ƿ�ı���ģ�ͱ���
	 */
	public boolean add(Job job);
	
	/**
	 * ��ģ�����Ƴ�ָ�����ƵĹ�����
	 * @param name ָ�������ơ�
	 * @return �ò����Ƿ�ı���ģ�ͱ���
	 */
	public boolean remove(String name);
	
	/**
	 * ��ģ�����Ƴ�����Ϊָ�����ƶ���Ĺ�����
	 * @param name ָ�������ƶ���
	 * @return �ò����Ƿ�ı���ģ�ͱ���
	 */
	public default boolean remove(Name name){
		return remove(name.getName());
	}
	
	/**
	 * ��ȡ��ģ�͵�������
	 * @return ��ģ�͵�������
	 */
	public int size();
	
	/**
	 * ��ȡģ�����Ƿ����ָ�����ƵĹ�����
	 * @param name ָ�������ơ�
	 * @return �Ƿ����ָ�����ƵĹ�����
	 */
	public boolean contains(String name);
	
	/**
	 * ��ȡģ�����Ƿ��������Ϊָ�����ƶ���Ĺ�����
	 * @param name ָ�������ƶ���
	 * @return �Ƿ��������Ϊָ�����ƶ���Ĺ�����
	 */
	public default boolean contains(Name name){
		return contains(name.getName());
	}
	
	/**
	 * ��ȡģ��������Ϊָ��ֵ�Ĺ�����
	 * <p> ���ģ���в�����ָ���Ĺ������򷵻� <code>null</code>��
	 * @param name  ָ�������ơ�
	 * @return ģ��������Ϊָ��ֵ�Ĺ�����
	 */
	public Job get(String name);
	
	/**
	 * ��ȡģ��������Ϊָ�������ƶ���Ĺ�����
	 * <p> ���ģ���в�����ָ���Ĺ������򷵻� <code>null</code>��
	 * @param name ָ�������ƶ���
	 * @return ģ��������Ϊָ�������ƶ���Ĺ�����
	 */
	public default Job get(Name name){
		return get(name.getName());
	}
	
	/**
	 * ���ģ���е��������ݡ�
	 */
	public void clear();

}

package com.dwarfeng.jier.mh4w.core.model.cm;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.ShiftObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Shift;

/**
 * ���ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface ShiftModel extends ExternalReadWriteThreadSafe, ObverserSet<ShiftObverser>, Iterable<Shift>{
	
	/**
	 * ��ģ��������ָ���İ�Ρ�
	 * @param shift ָ���İ�Ρ�
	 * @return �ò����Ƿ�ı���ģ�ͱ���
	 */
	public boolean add(Shift shift);
	
	/**
	 * ��ģ�����Ƴ�ָ�����Ƶİ�Ρ�
	 * @param name ָ�������ơ�
	 * @return �ò����Ƿ�ı���ģ�ͱ���
	 */
	public boolean remove(String name);
	
	/**
	 * ��ģ�����Ƴ�����Ϊָ�����ƶ���İ�Ρ�
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
	 * ��ȡģ�����Ƿ����ָ�����Ƶİ�Ρ�
	 * @param name ָ�������ơ�
	 * @return �Ƿ����ָ�����Ƶİ�Ρ�
	 */
	public boolean contains(String name);
	
	/**
	 * ��ȡģ�����Ƿ��������Ϊָ�����ƶ���İ�Ρ�
	 * @param name ָ�������ƶ���
	 * @return �Ƿ��������Ϊָ�����ƶ���İ�Ρ�
	 */
	public default boolean contains(Name name){
		return contains(name.getName());
	}
	
	/**
	 * ��ȡģ��������Ϊָ��ֵ�İ�Ρ�
	 * <p> ���ģ���в�����ָ���İ�Σ��򷵻� <code>null</code>��
	 * @param name  ָ�������ơ�
	 * @return ģ��������Ϊָ��ֵ�İ�Ρ�
	 */
	public Shift get(String name);
	
	/**
	 * ��ȡģ��������Ϊָ�������ƶ���İ�Ρ�
	 * <p> ���ģ���в�����ָ���İ�Σ��򷵻� <code>null</code>��
	 * @param name ָ�������ƶ���
	 * @return ģ��������Ϊָ�������ƶ���İ�Ρ�
	 */
	public default Shift get(Name name){
		return get(name.getName());
	}
	
	/**
	 * ���ģ���е��������ݡ�
	 */
	public void clear();

}

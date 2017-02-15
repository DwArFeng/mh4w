package com.dwarfeng.jier.mh4w.core.model.obv;

import java.util.Locale;
import java.util.Set;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangInfo;

/**
 * �����Թ۲�����
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface MutilangObverser extends Obverser{

	/**
	 * ֪ͨģ���������ָ�������ԡ�
	 * @param key ָ�������ԡ�
	 * @param value ��ָ�����Զ�Ӧ�Ķ�������Ϣ��
	 */
	public void fireEntryAdded(Locale key, MutilangInfo value);
	
	/**
	 * ֪ͨģ�����Ƴ���ָ�������ԡ�
	 * @param key ָ�������ԡ�
	 */
	public void fireEntryRemoved(Locale key);
	
	/**
	 *  ֪ͨģ����ָ�����ԵĶ�������Ϣ�����˸ı䡣
	 * @param key ָ�������ԡ�
	 * @param oldValue ָ�������Զ�Ӧ�ľ�������Ϣ��
	 * @param newValue ָ�������Զ�Ӧ����������Ϣ��
	 */
	public void fireEntryChanged(Locale key, MutilangInfo oldValue, MutilangInfo newValue);
	
	/**
	 * ֪ͨģ���е����ݱ������
	 */
	public void fireCleared();
	
	/**
	 * ֪ͨģ���е���֧�ֵļ����Ϸ����ı䡣
	 * @param oldValue �ɵ���֧�ּ����ϡ�
	 * @param newValue �µ���֧�ּ����ϡ�
	 */
	public void fireSupportedKeysChanged(Set<String> oldValue, Set<String> newValue);
	
	/**
	 * ֪ͨģ���еĵ�ǰ���Է����˸ı䡣
	 * @param oldValue �ɵĵ�ǰ���ԡ�
	 * @param newValue �µĵ�ǰ���ԡ�
	 */
	public void fireCurrentLocaleChanged(Locale oldValue, Locale newValue);
	
	/**
	 * ֪ͨģ���е�Ĭ�϶����Լ�ֵӳ�䷢���˸ı䡣
	 * @param oldValue �ɵĶ����Լ�ֵӳ�䡣
	 * @param newValue �µĶ����Լ�ֵӳ�䡣
	 */
	public void fireDefaultMutilangMapChanged(MutilangInfo oldValue, MutilangInfo newValue);
	
	/**
	 * ֪ͨģ���е�Ĭ���ı������ı䡣
	 * @param oldValue �ɵ�Ĭ���ı���
	 * @param newValue �µ�Ĭ���ı���
	 */
	public void fireDefaultVauleChanged(String oldValue, String newValue);
	
	/**
	 * ֪ͨģ���н����˸��²�����
	 */
	public void fireUpdated();
	
}

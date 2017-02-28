package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.MutilangObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangInfo;
import com.dwarfeng.jier.mh4w.core.model.struct.Updateable;

/**
 * ������ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface MutilangModel extends Map<Locale, MutilangInfo>, ObverserSet<MutilangObverser>, ExternalReadWriteThreadSafe, Updateable{
	
	/**
	 * ��ȡ������ģ������֧�ֵļ�ֵ���ϡ�
	 * <p> �ü����ǲ��ɸ��ĵģ����Ե�����༭�������׳� {@link UnsupportedOperationException}��
	 * @return ������ģ������֧�ֵļ����ϡ�
	 */
	public Set<String> getSupportedKeys();
	
	/**
	 * ���øö�����ģ������֧�ֵļ�ֵ���ϡ�
	 * @param names ָ���ļ�ֵ���ϡ�
	 * @return �ò����Ƿ�Ը�ģ������˸ı䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean setSupportedKeys(Set<String> names);
	
	/**
	 * ��ȡģ���е�ǰ�����ԣ�<code>null</code>����Ĭ�����ԡ�
	 * @return ģ���еĵ�ǰ���ԣ�<code>null</code>����Ĭ�����ԡ�
	 */
	public Locale getCurrentLocale();
	
	/**
	 * ����ģ���еĵ�ǰ���ԡ�
	 * <p> ��ڲ���ֻ��Ϊ <code>null</code> - ����Ĭ�����ԣ������Ǹ�ģ���а��������ԣ��� <code>containsKey(locale) == true</code>��
	 * ���򣬻��׳� {@link IllegalArgumentException}
	 * <p> �÷������᳢�Խ���ǰ������Ϊָ�������ԣ������׳��쳣�������ò��ɹ�ʱ������ false��
	 * @param locale ָ�������ԡ�
	 * @return �ò����Ƿ�Ը�ģ������˸ı䡣
	 * @throws IllegalArgumentException ָ�������Բ�Ϊ <code>null</code>,��ģ���в����������ԡ�
	 */
	public boolean setCurrentLocale(Locale locale);
	
	/**
	 * ��ȡģ���е�Ĭ�϶����Լ�ֵ��Ϣ��
	 * <p> �÷������ص�ǰ����Ϊ <code>null</code>������µĶ����Լ�ֵ��Ϣ��
	 * @return Ĭ�ϵĶ����Լ�ֵ��Ϣ��
	 */
	public MutilangInfo getDefaultMutilangInfo();
	
	/**
	 * ����ģ���е�Ĭ�϶����Լ�ֵ��Ϣ��
	 * @param mutilangInfo ָ���Ķ����Լ�ֵ��Ϣ��
	 * @return �ò����Ƿ�Ը�ģ������˸ı䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean setDefaultMutilangInfo(MutilangInfo mutilangInfo);
	
	/**
	 * ��ȡģ���еĶ����Լ�ֵӳ���Ĭ��ֵ��
	 * <p> ����ڶ�����ӳ���У��Ҳ�����Ӧ�ļ���ֵ����ô�ͷ��ظ�ֵ��
	 * @return ������ӳ��ֵ���Ĭ��ֵ��
	 */
	public String getDefaultValue();
	
	/**
	 * ����ģ���еĶ����Լ�ֵĬ��ֵ��
	 * @param value ָ����ֵ��
	 * @return �ò����Ƿ��ģ������˸ı䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean setDefaultValue(String value);
	
	/**
	 * ��ȡ��ģ���еĶ����Խӿڡ�
	 * @return ��ģ���еĶ����Խӿڡ�
	 */
	public Mutilang getMutilang();
	
	
}

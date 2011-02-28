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
 * 多语言模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface MutilangModel extends Map<Locale, MutilangInfo>, ObverserSet<MutilangObverser>, ExternalReadWriteThreadSafe, Updateable{
	
	/**
	 * 获取多语言模型中受支持的键值集合。
	 * <p> 该集合是不可更改的，尝试调用其编辑方法会抛出 {@link UnsupportedOperationException}。
	 * @return 多语言模型中受支持的键集合。
	 */
	public Set<String> getSupportedKeys();
	
	/**
	 * 设置该多语言模型中受支持的键值集合。
	 * @param names 指定的键值集合。
	 * @return 该操作是否对该模型造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean setSupportedKeys(Set<String> names);
	
	/**
	 * 获取模型中当前的语言，<code>null</code>代表默认语言。
	 * @return 模型中的当前语言，<code>null</code>代表默认语言。
	 */
	public Locale getCurrentLocale();
	
	/**
	 * 设置模型中的当前语言。
	 * <p> 入口参数只能为 <code>null</code> - 代表默认语言，或者是该模型中包含的语言，即 <code>containsKey(locale) == true</code>。
	 * 否则，会抛出 {@link IllegalArgumentException}
	 * <p> 该方法将会尝试将当前语言设为指定的语言，不会抛出异常，当设置不成功时，返回 false。
	 * @param locale 指定的语言。
	 * @return 该操作是否对该模型造成了改变。
	 * @throws IllegalArgumentException 指定的语言不为 <code>null</code>,且模型中不包含该语言。
	 */
	public boolean setCurrentLocale(Locale locale);
	
	/**
	 * 获取模型中的默认多语言键值信息。
	 * <p> 该方法返回当前语言为 <code>null</code>的情况下的多语言键值信息。
	 * @return 默认的多语言键值信息。
	 */
	public MutilangInfo getDefaultMutilangInfo();
	
	/**
	 * 设置模型中的默认多语言键值信息。
	 * @param mutilangInfo 指定的多语言键值信息。
	 * @return 该操作是否对该模型造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean setDefaultMutilangInfo(MutilangInfo mutilangInfo);
	
	/**
	 * 获取模型中的多语言键值映射的默认值。
	 * <p> 如果在多语言映射中，找不到对应的键的值，那么就返回该值。
	 * @return 多语言映键值射的默认值。
	 */
	public String getDefaultValue();
	
	/**
	 * 设置模型中的多语言键值默认值。
	 * @param value 指定的值。
	 * @return 该操作是否对模型造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean setDefaultValue(String value);
	
	/**
	 * 获取该模型中的多语言接口。
	 * @return 该模型中的多语言接口。
	 */
	public Mutilang getMutilang();
	
	
}

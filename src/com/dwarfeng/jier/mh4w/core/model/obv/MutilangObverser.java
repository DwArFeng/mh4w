package com.dwarfeng.jier.mh4w.core.model.obv;

import java.util.Locale;
import java.util.Set;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangInfo;

/**
 * 多语言观察器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface MutilangObverser extends Obverser{

	/**
	 * 通知模型中添加了指定的语言。
	 * @param key 指定的语言。
	 * @param value 与指定语言对应的多语言信息。
	 */
	public void fireEntryAdded(Locale key, MutilangInfo value);
	
	/**
	 * 通知模型中移除了指定的语言。
	 * @param key 指定的语言。
	 */
	public void fireEntryRemoved(Locale key);
	
	/**
	 *  通知模型中指定语言的多语言信息发生了改变。
	 * @param key 指定的语言。
	 * @param oldValue 指定的语言对应的旧语言信息。
	 * @param newValue 指定的语言对应的新语言信息。
	 */
	public void fireEntryChanged(Locale key, MutilangInfo oldValue, MutilangInfo newValue);
	
	/**
	 * 通知模型中的数据被清除。
	 */
	public void fireCleared();
	
	/**
	 * 通知模型中的受支持的键集合发生改变。
	 * @param oldValue 旧的受支持键集合。
	 * @param newValue 新的受支持键集合。
	 */
	public void fireSupportedKeysChanged(Set<String> oldValue, Set<String> newValue);
	
	/**
	 * 通知模型中的当前语言发生了改变。
	 * @param oldValue 旧的当前语言。
	 * @param newValue 新的当前语言。
	 */
	public void fireCurrentLocaleChanged(Locale oldValue, Locale newValue);
	
	/**
	 * 通知模型中的默认多语言键值映射发生了改变。
	 * @param oldValue 旧的多语言键值映射。
	 * @param newValue 新的多语言键值映射。
	 */
	public void fireDefaultMutilangMapChanged(MutilangInfo oldValue, MutilangInfo newValue);
	
	/**
	 * 通知模型中的默认文本发生改变。
	 * @param oldValue 旧的默认文本。
	 * @param newValue 新的默认文本。
	 */
	public void fireDefaultVauleChanged(String oldValue, String newValue);
	
	/**
	 * 通知模型中进行了更新操作。
	 */
	public void fireUpdated();
	
}

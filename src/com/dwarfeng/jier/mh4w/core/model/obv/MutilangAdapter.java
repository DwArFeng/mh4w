package com.dwarfeng.jier.mh4w.core.model.obv;

import java.util.Locale;
import java.util.Set;

import com.dwarfeng.jier.mh4w.core.model.struct.MutilangInfo;

/**
 * 多语言模型观察器适配器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class MutilangAdapter implements MutilangObverser {

	@Override
	public void fireEntryAdded(Locale key, MutilangInfo value) {	}
	@Override
	public void fireEntryRemoved(Locale key) {}
	@Override
	public void fireEntryChanged(Locale key, MutilangInfo oldValue, MutilangInfo newValue) {}
	@Override
	public void fireCleared() {}
	@Override
	public void fireSupportedKeysChanged(Set<String> oldValue, Set<String> newValue) {}
	@Override
	public void fireCurrentLocaleChanged(Locale oldValue, Locale newValue) {}
	@Override
	public void fireDefaultMutilangMapChanged(MutilangInfo oldValue, MutilangInfo newValue) {}
	@Override
	public void fireDefaultVauleChanged(String oldValue, String newValue) {}
	@Override
	public void fireUpdated() {}

}

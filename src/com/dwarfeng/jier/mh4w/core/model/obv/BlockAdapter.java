package com.dwarfeng.jier.mh4w.core.model.obv;

import java.util.Set;

/**
 * ×èµ²Ä£ĞÍ¹Û²ìÆ÷ÊÊÅäÆ÷¡£
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public abstract class BlockAdapter implements BlockObverser{

	@Override
	public void fireEntryAdded(String key, Set<String> value) {}
	@Override
	public void fireEntryRemoved(String key) {}
	@Override
	public void fireEntryChanged(String key, Set<String> oldValue, Set<String> newValue) {}
	@Override
	public void fireCleared() {}
	@Override
	public void fireUpdated() {}
	
}

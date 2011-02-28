package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.jier.mh4w.core.model.struct.Job;

/**
 * ¹¤×÷¹Û²ìÆ÷ÊÊÅäÆ÷¡£
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public abstract class JobAdapter implements JobObverser {

	@Override
	public void fireJobAdded(Job job) {}
	@Override
	public void fireJobRemoved(Job job) {}
	@Override
	public void fireJobCleared() {}
	
}
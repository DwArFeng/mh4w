package com.dwarfeng.jier.mh4w.core.model.cm;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.JobObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;

/**
 * 工作模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface JobModel extends ExternalReadWriteThreadSafe, ObverserSet<JobObverser>, Iterable<Job>{
	
	/**
	 * 向模型中增加指定的工作。
	 * @param job 指定的工作。
	 * @return 该操作是否改变了模型本身。
	 */
	public boolean add(Job job);
	
	/**
	 * 从模型中移除指定名称的工作。
	 * @param name 指定的名称。
	 * @return 该操作是否改变了模型本身。
	 */
	public boolean remove(String name);
	
	/**
	 * 从模型中移除名称为指定名称对象的工作。
	 * @param name 指定的名称对象。
	 * @return 该操作是否改变了模型本身。
	 */
	public default boolean remove(Name name){
		return remove(name.getName());
	}
	
	/**
	 * 获取该模型的数量。
	 * @return 该模型的数量。
	 */
	public int size();
	
	/**
	 * 获取模型中是否包含指定名称的工作。
	 * @param name 指定的名称。
	 * @return 是否包含指定名称的工作。
	 */
	public boolean contains(String name);
	
	/**
	 * 获取模型中是否包含名称为指定名称对象的工作。
	 * @param name 指定的名称对象。
	 * @return 是否包含名称为指定名称对象的工作。
	 */
	public default boolean contains(Name name){
		return contains(name.getName());
	}
	
	/**
	 * 获取模型中名称为指定值的工作。
	 * <p> 如果模型中不存在指定的工作，则返回 <code>null</code>。
	 * @param name  指定的名称。
	 * @return 模型中名称为指定值的工作。
	 */
	public Job get(String name);
	
	/**
	 * 获取模型中名称为指定的名称对象的工作。
	 * <p> 如果模型中不存在指定的工作，则返回 <code>null</code>。
	 * @param name 指定的名称对象。
	 * @return 模型中名称为指定的名称对象的工作。
	 */
	public default Job get(Name name){
		return get(name.getName());
	}
	
	/**
	 * 清除模型中的所有数据。
	 */
	public void clear();

}

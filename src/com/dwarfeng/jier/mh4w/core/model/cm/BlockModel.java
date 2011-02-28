package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Map;
import java.util.Set;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.BlockObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Block;
import com.dwarfeng.jier.mh4w.core.model.struct.Updateable;

/**
 * 阻挡模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface BlockModel extends Map<String, Set<String>>, ObverserSet<BlockObverser>, ExternalReadWriteThreadSafe, Updateable{
	
	/**
	 * 获取模型中的阻挡。
	 * @return 模型中的阻挡。
	 */
	public Block getBlock();

}

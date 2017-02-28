package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Map;
import java.util.Set;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.BlockObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Block;
import com.dwarfeng.jier.mh4w.core.model.struct.Updateable;

/**
 * �赲ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface BlockModel extends Map<String, Set<String>>, ObverserSet<BlockObverser>, ExternalReadWriteThreadSafe, Updateable{
	
	/**
	 * ��ȡģ���е��赲��
	 * @return ģ���е��赲��
	 */
	public Block getBlock();

}

package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.List;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateObverser;

/**
 * 列表数据模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface DataListModel<E> extends ObverserSet<ListOperateObverser<E>>, ExternalReadWriteThreadSafe, List<E>{

}

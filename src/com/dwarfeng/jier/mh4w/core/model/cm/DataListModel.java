package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.List;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateObverser;

/**
 * �б�����ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface DataListModel<E> extends ObverserSet<ListOperateObverser<E>>, ExternalReadWriteThreadSafe, List<E>{

}

package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Map;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.obv.DateTypeObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;

/**
 * 日期类型模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface DateTypeModel extends Map<CountDate, DateType>, ExternalReadWriteThreadSafe, ObverserSet<DateTypeObverser>{
	
}

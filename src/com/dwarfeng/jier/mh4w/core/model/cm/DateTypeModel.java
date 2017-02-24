package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Map;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.obv.DateTypeObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;

/**
 * 日期类型模型。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface DateTypeModel extends Map<CountDate, DateType>, ExternalReadWriteThreadSafe, ObverserSet<DateTypeObverser>{
	
}

package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Map;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.AttendanceOffsetObverser;

/**
 * ���ڲ���ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface AttendanceOffsetModel extends Map<String, Double>, ExternalReadWriteThreadSafe, ObverserSet<AttendanceOffsetObverser> {

}

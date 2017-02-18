package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.List;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData;

/**
 * ��������ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface AttendanceDataModel 
extends ExternalReadWriteThreadSafe, ObverserSet<ListOperateObverser<AttendanceData>>, List<AttendanceData> {

}

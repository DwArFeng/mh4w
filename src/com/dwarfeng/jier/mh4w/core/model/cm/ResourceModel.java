package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Map;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.struct.Resource;

/**
 * ��Դģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface ResourceModel extends Map<String, Resource>, ExternalReadWriteThreadSafe{

}

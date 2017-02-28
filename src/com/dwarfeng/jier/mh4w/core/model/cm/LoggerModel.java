package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Set;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.LoggerObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Logger;
import com.dwarfeng.jier.mh4w.core.model.struct.LoggerInfo;
import com.dwarfeng.jier.mh4w.core.model.struct.Updateable;

/**
 * �йؼ�¼��������ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface LoggerModel extends Set<LoggerInfo>, ObverserSet<LoggerObverser>, ExternalReadWriteThreadSafe, Updateable{
	
	/**
	 * ��ȡ��¼�������ġ�
	 * @return ��¼�������ġ�
	 */
	public LoggerContext getLoggerContext();
	
	/**
	 * ���ü�¼��������Ϊָ�������ġ�
	 * @param loggerContext ��¼�������ġ�
	 * @return �ò����Ƿ��ģ������˸ı䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public boolean setLoggerContext(LoggerContext loggerContext);
	
	/**
	 * ��ȡģ���еļ�¼����
	 * @return ģ���еļ�¼����
	 */
	public Logger getLogger();
		
}

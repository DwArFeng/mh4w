package com.dwarfeng.jier.mh4w.core.model.cm;

import java.io.File;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.FileSelectObverser;

/**
 * �ļ�ѡ��ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface FileSelectModel extends ExternalReadWriteThreadSafe, ObverserSet<FileSelectObverser>{
	
	/**
	 * ����ģ���еĳ����ļ���
	 * @return �ļ��еĳ����ļ���
	 */
	public File getAttendanceFile();
	
	/**
	 * ����ģ���еĳ����ļ���
	 * @param file ָ���ĳ����ļ�������Ϊ <code>null</code>��
	 * @return �ò����Ƿ��ģ������˸Ķ���
	 */
	public boolean setAttendanceFile(File file);
	
	/**
	 * ��ȡ��Ʊ��Ϣ�ļ���
	 * @return ��Ʊ��Ϣ�ļ���
	 */
	public File getWorkticketFile();
	
	/**
	 * ����ģ���еĹ�Ʊ��Ϣ�ļ���
	 * @param file ָ���Ĺ�Ʊ��Ϣ�ļ�������Ϊ <code>null</code>��
	 * @return �ò����Ƿ��ģ������˸Ķ���
	 */
	public boolean setWorkticketFile(File file);

}

package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ����ͬ������ģ�͡�
 * <p> ����ģ�͵��̰߳�ȫ�ĳ���ʵ�֡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public abstract class AbstractSyncConfigModel implements SyncConfigModel {

	/**ģ�͵�ͬ����д����*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe#getLock()
	 */
	@Override
	public ReadWriteLock getLock() {
		return lock;
	}

}

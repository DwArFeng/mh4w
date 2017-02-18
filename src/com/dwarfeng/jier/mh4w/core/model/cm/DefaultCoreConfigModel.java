package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Locale;

import com.dwarfeng.jier.mh4w.core.model.eum.CoreConfig;
import com.dwarfeng.jier.mh4w.core.util.LocaleUtil;

/**
 * Ĭ�Ϻ�������ģ�͡�
 * <p> ��������ģ�͵�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultCoreConfigModel extends DefaultSyncConfigModel implements CoreConfigModel{
	
	/**
	 * ��ʵ����
	 */
	public DefaultCoreConfigModel() {
		super(CoreConfig.values());
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getLoggerMutilangLocale()
	 */
	@Override
	public Locale getLoggerMutilangLocale() {
		lock.readLock().lock();
		try{
			return LocaleUtil.parseLocale(getValidValue(CoreConfig.MUTILANG_LOGGER.getConfigKey()));
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getLabelMutilangLocale()
	 */
	@Override
	public Locale getLabelMutilangLocale() {
		lock.readLock().lock();
		try{
			return LocaleUtil.parseLocale(getValidValue(CoreConfig.MUTILANG_LABEL.getConfigKey()));
		}finally {
			lock.readLock().unlock();
		}
	}
	
}
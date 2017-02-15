package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.dutil.basic.threads.NumberedThreadFactory;
import com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LoggerStringKey;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;

/**
 * Ĭ����ɹ���ȡ������
 * <p> ��ɹ���ȡ������Ĭ��ʵ�֡�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultFinishedFlowTaker implements FinishedFlowTaker {
	
	private static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("finished_flow_taker");
	
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final BackgroundModel backgroundModel;
	private final Thread thread = THREAD_FACTORY.newThread(new Taker());
	
	
	private Logger logger;
	private Mutilang mutilang;
	
	private boolean runFlag = true;
	
	/**
	 * ��ʵ����
	 * @param backgroundModel ָ���ĺ�̨ģ�͡�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultFinishedFlowTaker(BackgroundModel backgroundModel) {
		this(backgroundModel, Mh4wUtil.newDefaultLogger(), Constants.getDefaultLoggerMutilang());
	}
	
	/**
	 * ��ʵ����
	 * @param backgroundModel ָ���ĺ�̨ģ�͡�
	 * @param logger ָ���ļ�¼����
	 * @param mutilang ָ���Ķ����Խӿڡ�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultFinishedFlowTaker(BackgroundModel backgroundModel, Logger logger, Mutilang mutilang) {
		Objects.requireNonNull(backgroundModel, "��ڲ��� backgroundModel ����Ϊ null��");
		Objects.requireNonNull(logger, "��ڲ��� logger ����Ϊ null��");
		Objects.requireNonNull(mutilang, "��ڲ��� mutilang ����Ϊ null��");
		
		this.backgroundModel = backgroundModel;
		this.logger = logger;
		this.mutilang = mutilang;
		
		thread.start();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe#getLock()
	 */
	@Override
	public ReadWriteLock getLock() {
		return lock;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.FinishedFlowTaker#getLogger()
	 */
	@Override
	public Logger getLogger() {
		lock.readLock().lock();
		try{
			return this.logger;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.FinishedFlowTaker#setLogger(com.dwarfeng.jier.mh4w.core.model.struct.Logger)
	 */
	@Override
	public boolean setLogger(Logger logger) {
		Objects.requireNonNull(logger, "��ڲ��� logger ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.logger, logger)) return false;
			this.logger = logger;
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.FinishedFlowTaker#getBackgroundModel()
	 */
	@Override
	public BackgroundModel getBackgroundModel() {
		return this.backgroundModel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.FinishedFlowTaker#shutdown()
	 */
	@Override
	public void shutdown() {
		lock.writeLock().lock();
		try{
			this.runFlag = false;
			this.thread.interrupt();
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported#getMutilang()
	 */
	@Override
	public Mutilang getMutilang() {
		lock.readLock().lock();
		try{
			return this.mutilang;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported#setMutilang(com.dwarfeng.jier.mh4w.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setMutilang(Mutilang mutilang) {
		Objects.requireNonNull(mutilang, "��ڲ��� mutilang ����Ϊ null��");
		
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.mutilang, mutilang)) return false;
			this.mutilang = mutilang;
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private boolean isRun(){
		lock.readLock().lock();
		try{
			return runFlag;
		}finally {
			lock.readLock().unlock();
		}
	}

	private final class Taker implements Runnable{

		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			while(isRun()){
				try {
					Flow flow = backgroundModel.takeFinished();
					
					//��ȡ���ݡ�
					String message = flow.getMessage();
					Throwable throwable = null;
					String format = null;
					String str = null;
					Logger logger;
					
					lock.readLock().lock();
					try{
						throwable = flow.getThrowable();
						format = "%s_%s";
						str = Objects.isNull(throwable) ?
								mutilang.getString(LoggerStringKey.FinishedFlowTaker_1.getName()) :
								mutilang.getString(LoggerStringKey.FinishedFlowTaker_2.getName());
						logger = DefaultFinishedFlowTaker.this.logger;
					}finally {
						lock.readLock().unlock();
					}
	
					if(Objects.isNull(throwable)){
						logger.info(String.format(format, str, message));
					}else{
						logger.warn(String.format(format, str, message), throwable);
					}
				}catch (Exception e) {
					if(!(e instanceof InterruptedException)){
						String str = null;
						lock.readLock().lock();
						try{
							str = mutilang.getString(LoggerStringKey.FinishedFlowTaker_3.getName());
						}catch (Exception e1) {
							Mutilang tempMutilang = Constants.getDefaultLoggerMutilang();
							str = tempMutilang.getString(LoggerStringKey.FinishedFlowTaker_4.getName());
							logger.warn(str, e1);
							str = tempMutilang.getString(LoggerStringKey.FinishedFlowTaker_3.getName());
						}finally {
							lock.readLock().unlock();
						}
						logger.warn(str, e);
					}
				}
			}
		}
		
	}

}

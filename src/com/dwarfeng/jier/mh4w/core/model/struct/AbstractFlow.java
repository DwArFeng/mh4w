package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.jier.mh4w.core.model.obv.FlowObverser;

/**
 * �����ࡣ
 * <p> ������һ������ʵ�ֵĹ��̡�
 * <p> ��������Ա����ã�ӵ�н������ԣ����ҿ�����ע��Ĺ۲����㲥������Ϣ��
 * <br> ���̸����Ƿ�֪�����ȿɷ�Ϊȷ�����̺Ͳ�ȷ�����̣��������� {@link #isDeterminate()} ȷ����
 * <p> �۲������̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class AbstractFlow implements Flow{
	
	/**�۲�������*/
	protected final Set<FlowObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	/**ͬ����д��*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	/**д�����ж�״̬*/
	protected final Condition condition = lock.writeLock().newCondition();
	
	/**��ǰ�Ľ���*/
	private int progress;
	/**�ܽ���*/
	private int totleProgress;
	/**��ǰ�Ľ�����ȷ���Ļ��ǲ�ȷ����*/
	private boolean determinateFlag;
	/**��ǰ�Ĺ����Ƿ����ȡ��*/
	private boolean cancelableFlag;
	
	/**�����Ƿ����*/
	private boolean doneFlag = false;
	/**�����Ƿ�ȡ��*/
	private boolean cancelFlag = false;
	/**�йع��̵���Ϣ*/
	private String message = "";
	/**�йع��̵Ŀ��׳�����*/
	private Throwable throwable = null;
	
	/**
	 * ��ʵ����
	 * <p> ��ǰ����Ϊ0��
	 * <br> �ܽ���Ϊ0��
	 * <br> �ǲ�ȷ���Ĺ��̣�
	 * <br> �ǲ���ȡ���Ĺ��̡�
	 */
	public AbstractFlow() {
		this(0, 0, false, false);
	}
	
	/**
	 * ��ʵ����
	 * @param progress ָ���ĵ�ǰ���ȡ�
	 * @param totleProgress ָ�����ܽ��ȡ�
	 * @param determinateFlag ָ����ȷ���Ա�־��
	 * @param cancelableFlag ָ���Ŀ�ȡ����־��
	 */
	public AbstractFlow(int progress, int totleProgress, boolean determinateFlag, boolean cancelableFlag) {
		this.progress = progress;
		this.totleProgress = totleProgress;
		this.determinateFlag = determinateFlag;
		this.cancelableFlag = cancelableFlag;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Flow#getProgress()
	 */
	@Override
	public int getProgress() {
		lock.readLock().lock();
		try{
			return progress;
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ù��̵Ľ��ȡ�
	 * <p> ����Ӧ����ѭ���¹淶�� <code> 0 &lt;= progress &lt;= totleProgress </code>
	 * <p> �ù��̽��Զ��Ľ������Ϲ淶����ڲ���ת��Ϊ��ӽ��ĺ���ֵ��
	 * @param progress ָ���Ľ��ȡ�
	 * @return �ò����Ƿ�ı��˹��̱���
	 */
	protected boolean setProgress(int progress) {
		lock.writeLock().lock();
		try{
			if(this.progress == progress) return false;
			int oldValue = this.progress;
			progress = Math.max(0, progress);
			progress = Math.min(progress, totleProgress);
			this.progress = progress;
			fireProgressChanged(oldValue, progress);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireProgressChanged(int oldValue, int newValue) {
		for(FlowObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireProgressChanged(this, oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Flow#getTotleProgress()
	 */
	@Override
	public int getTotleProgress() {
		lock.readLock().lock();
		try{
			return totleProgress;
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ù����ܵĽ��ȡ�
	 * <p> ����Ӧ����ѭ���¹淶�� <code> 0 &lt;= progress &lt;= totleProgress </code>
	 * <p> �ù��̽��Զ��Ľ������Ϲ淶����ڲ���ת��Ϊ��ӽ��ĺ���ֵ��
	 * @param totleProgress ָ�����ܽ��ȡ�
	 * @return �ò����Ƿ�ñ��˹��̱���
	 */
	protected boolean setTotleProgress(int totleProgress) {
		lock.writeLock().lock();
		try{
			if(this.totleProgress == totleProgress) return false; 
			int oldValue = this.totleProgress;
			totleProgress = Math.max(0, totleProgress);
			totleProgress = Math.max(progress, totleProgress);
			this.totleProgress = totleProgress;
			fireTotleProgressChanged(oldValue, totleProgress);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireTotleProgressChanged(int oldValue, int newValue) {
		for(FlowObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireTotleProgressChanged(this, oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Flow#isDeterminate()
	 */
	@Override
	public boolean isDeterminate() {
		lock.readLock().lock();
		try{
			return determinateFlag;
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���øù����Ƿ�Ϊȷ�����̡�
	 * @param determinateFlag �ù����Ƿ�Ϊȷ�����̡�
	 * @return �ò����Ƿ�ı��˹��̱���
	 */
	protected boolean setDeterminate(boolean determinateFlag) {
		lock.writeLock().lock();
		try{
			if(this.determinateFlag == determinateFlag) return false;
			boolean oldValue = this.determinateFlag;
			this.determinateFlag = determinateFlag;
			fireDeterminateChanged(this, oldValue, determinateFlag);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireDeterminateChanged(Flow flow, boolean oldValue, boolean newValue) {
		for(FlowObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDeterminateChanged(this, oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Flow#isCancelable()
	 */
	@Override
	public boolean isCancelable(){
		lock.readLock().lock();
		try{
			return cancelableFlag;
		}finally {
			lock.readLock().unlock();
		}
	}
	
	/**
	 * ���øù����Ƿ��ܹ�ȡ����
	 * @param aFlag �Ƿ��ܹ�ȡ����
	 * @return �ò����Ƿ�ı��˹��̱���
	 */
	protected boolean setCancelable(boolean aFlag){
		lock.writeLock().lock();
		try{
			if(this.cancelableFlag == aFlag) return false;
			boolean oldValue = this.cancelableFlag;
			this.cancelableFlag = aFlag;
			fireCancelableChanged(oldValue, aFlag);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireCancelableChanged(boolean oldValue, boolean newValue) {
		for(FlowObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCancelableChanged(this, oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Flow#isCancel()
	 */
	@Override
	public boolean isCancel(){
		lock.readLock().lock();
		try{
			return cancelFlag;
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ȡ���ù��̡�
	 * @return �Ƿ�ȡ���ɹ���
	 */
	public boolean cancel(){
		lock.writeLock().lock();
		try{
			if(isCancelable()){
				cancelFlag = true;
				fireCanceled();
				return true;
			}else {
				return false;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCanceled() {
		for(FlowObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCanceled(this);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Flow#isDone()
	 */
	@Override
	public boolean isDone(){
		lock.readLock().lock();
		try{
			return doneFlag;
		}finally {
			lock.readLock().unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Flow#getMessage()
	 */
	@Override
	public String getMessage(){
		lock.readLock().lock();
		try{
			return message;
		}finally {
			lock.readLock().unlock();
		}
	}
	
	/**
	 * ���øù��̵���Ϣ��
	 * @param message �ù��̵���Ϣ��
	 * @return �÷����Ƿ���ɹ��̱���ĸı䡣
	 */
	protected boolean setMessage(String message){
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.message, message)) return false;
			String oldValue = this.message;
			this.message = message;
			fireMessageChanged(oldValue, message);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireMessageChanged(String oldValue, String newValue) {
		for(FlowObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireMessageChanged(this, oldValue, newValue);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Flow#getThrowable()
	 */
	@Override
	public Throwable getThrowable(){
		lock.readLock().lock();
		try{
			return throwable;
		}finally {
			lock.readLock().unlock();
		}
	}
	
	/**
	 * ���øù����еĿ��׳�����
	 * <p> ����Ϊ <code>null</code> ����û�п��׳�����
	 * @param throwable ָ���Ŀ��׳�����
	 * @return �÷����Ƿ�ı��˹��̶�����
	 */
	protected boolean setThrowable(Throwable throwable){
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.throwable, throwable)) return false;
			Throwable oldValue = this.throwable;
			this.throwable = throwable;
			fireThrowableChanged(oldValue, throwable);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireThrowableChanged(Throwable oldValue, Throwable newValue) {
		for(FlowObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireThrowableChanged(this, oldValue, newValue);
		}
	}

	private void done(){
		lock.writeLock().lock();
		try{
			doneFlag = true;
			fireDone();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireDone() {
		for(FlowObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDone(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Object call() {
		try{
			process();
		}catch (Exception e) {
			setThrowable(e);
		}
		done();
		lock.writeLock().lock();
		try{
			condition.signalAll();
		}finally {
			lock.writeLock().unlock();
		}
		return null;	
	}
	
	/**
	 * �ù��̶���Ĺ��̷�����
	 */
	protected abstract void process();

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe#getLock()
	 */
	@Override
	public ReadWriteLock getLock() {
		return this.lock;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<FlowObverser> getObversers() {
		lock.readLock().lock();
		try{
			return obversers;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(FlowObverser obverser) {
		lock.writeLock().lock();
		try{
			return obversers.add(obverser);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(FlowObverser obverser) {
		lock.writeLock().lock();
		try{
			return obversers.remove(obverser);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#clearObverser()
	 */
	@Override
	public void clearObverser() {
		lock.writeLock().lock();
		try{
			obversers.clear();
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Flow#waitFinished()
	 */
	@Override
	public void waitFinished() throws InterruptedException {
		lock.writeLock().lock();
		try{
			while(! isDone()){
				condition.await();
			}
		}finally {
			lock.writeLock().unlock();
		}
	}
	
}

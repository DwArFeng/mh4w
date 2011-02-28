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
 * 过程类。
 * <p> 代表着一个可以实现的过程。
 * <p> 过程类可以被调用，拥有进度属性，并且可以向注册的观察器广播进度信息。
 * <br> 过程根据是否知道进度可分为确定过程和不确定过程，该属性由 {@link #isDeterminate()} 确定。
 * <p> 观察器是线程安全的。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class AbstractFlow implements Flow{
	
	/**观察器集合*/
	protected final Set<FlowObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	/**同步读写锁*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	/**写锁的中断状态*/
	protected final Condition condition = lock.writeLock().newCondition();
	
	/**当前的进度*/
	private int progress;
	/**总进度*/
	private int totleProgress;
	/**当前的进度是确定的还是不确定的*/
	private boolean determinateFlag;
	/**当前的过程是否可以取消*/
	private boolean cancelableFlag;
	
	/**过程是否完成*/
	private boolean doneFlag = false;
	/**过程是否被取消*/
	private boolean cancelFlag = false;
	/**有关过程的信息*/
	private String message = "";
	/**有关过程的可抛出对象*/
	private Throwable throwable = null;
	
	/**
	 * 新实例。
	 * <p> 当前进度为0；
	 * <br> 总进度为0；
	 * <br> 是不确定的过程；
	 * <br> 是不可取消的过程。
	 */
	public AbstractFlow() {
		this(0, 0, false, false);
	}
	
	/**
	 * 新实例。
	 * @param progress 指定的当前进度。
	 * @param totleProgress 指定的总进度。
	 * @param determinateFlag 指定的确定性标志。
	 * @param cancelableFlag 指定的可取消标志。
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
	 * 设置过程的进度。
	 * <p> 进度应该遵循如下规范： <code> 0 &lt;= progress &lt;= totleProgress </code>
	 * <p> 该过程将自动的将不符合规范的入口参数转换为最接近的合理值。
	 * @param progress 指定的进度。
	 * @return 该操作是否改变了过程本身。
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
	 * 设置过程总的进度。
	 * <p> 进度应该遵循如下规范： <code> 0 &lt;= progress &lt;= totleProgress </code>
	 * <p> 该过程将自动的将不符合规范的入口参数转换为最接近的合理值。
	 * @param totleProgress 指定的总进度。
	 * @return 该操作是否该变了过程本身。
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
	 * 设置该过程是否为确定过程。
	 * @param determinateFlag 该过程是否为确定过程。
	 * @return 该操作是否改变了过程本身。
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
	 * 设置该过程是否能够取消。
	 * @param aFlag 是否能够取消。
	 * @return 该操作是否改变了过程本身。
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
	 * 取消该过程。
	 * @return 是否取消成功。
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
	 * 设置该过程的消息。
	 * @param message 该过程的消息。
	 * @return 该方法是否造成过程本身的改变。
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
	 * 设置该过程中的可抛出对象。
	 * <p> 设置为 <code>null</code> 代表没有可抛出对象。
	 * @param throwable 指定的可抛出对象。
	 * @return 该方法是否改变了过程对象本身。
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
	 * 该过程对象的过程方法。
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

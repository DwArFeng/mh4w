package com.dwarfeng.jier.mh4w.launch;

import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.dwarfeng.dutil.basic.prog.RuntimeState;
import com.dwarfeng.jier.mh4w.core.control.Mh4w;
import com.dwarfeng.jier.mh4w.core.control.obv.Mh4wAdapter;
import com.dwarfeng.jier.mh4w.core.control.obv.Mh4wObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.ProcessException;

/**
 * ������������
 * <p> ������������һ�� {@link Mh4w} ʵ������������
 * ��ʵ�����н���ʱ��������漴�˳���
 * @author DwArFeng
 * @since 1.1.0
 */
public class SingleletonLauncher {

	private final Mh4w mh4w;
	
	private final Lock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();
	private final Mh4wObverser mh4wObverser = new Mh4wAdapter() {
		@Override
		public void fireStateChanged(RuntimeState oldValue, RuntimeState newValue) {
			if(newValue.equals(RuntimeState.ENDED)){
				exitFlag = true;
				lock.lock();
				try{
					condition.signalAll();
				}finally {
					lock.unlock();
				}
			}
		}
	};
	
	private boolean exitFlag = false;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException ignore) {
			//�����е�����Ԫ�ؾ�֧����һ��ۣ���˲����ܳ����쳣��
		}
		
		try {
			new SingleletonLauncher().launchAndWait();
			System.exit(0);
		} catch (InterruptedException ignore) {
			//�ж�ҲҪ���ջ�������
		}
		
	}

	/**
	 * ��ʵ����
	 */
	public SingleletonLauncher() {
		mh4w = new Mh4w();
		mh4w.addObverser(mh4wObverser);
	}
	
	/**
	 * �������ȴ���ֱ�����������
	 * @throws InterruptedException �ڵȴ��Ĺ����б��жϡ�
	 */
	public void launchAndWait() throws InterruptedException{
		try{
			mh4w.start();
		} catch (ProcessException e) {
			if(Objects.nonNull(mh4w)){
				mh4w.getLogger().error("����δ����ȷ����", e);
				System.exit(12450);
			}else{
				System.exit(12451);
			}
		}
		
		lock.lock();
		try{
			while(!exitFlag){
				condition.await();
			}
		}finally {
			lock.unlock();
		}
	}
	
}

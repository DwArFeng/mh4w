package com.dwarfeng.jier.mh4w.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.dwarfeng.jier.mh4w.core.control.Mh4w;
import com.dwarfeng.jier.mh4w.core.model.struct.Logger;

/**
 * ���ڹ�ʱͳ������Ĺ����ࡣ
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class Mh4wUtil {
	
	/**
	 * ��ȡһ���µ��赲�ֵ�ָʾ����������
	 * @return �µ��赲�ֵ�ָʾ����������
	 */
	public final static InputStream newBlockDictionary(){
		return Mh4w.class.getResourceAsStream("/com/dwarfeng/jier/mh4w/resource/block_dictionary.xml");
	}
	
	/**
	 * ��ȡĬ�ϵļ�¼�������ġ�
	 * @return Ĭ�ϵļ�¼�������ġ�
	 */
	public final static LoggerContext newDefaultLoggerContext(){
		try {
			ConfigurationSource cs = new ConfigurationSource(Mh4w.class.getResourceAsStream("/com/dwarfeng/jier/mh4w/resource/defaultres/logger/setting.xml"));
			return Configurator.initialize(null, cs);
		} catch (IOException e) {
			e.printStackTrace();
			return (LoggerContext) LogManager.getContext();
		}
	}
	
	/**
	 * ��ȡĬ�ϼ�¼���ӿڡ�
	 * <p> �ü�¼���������κβ�����
	 * @return �µĳ�ʼ����¼���ӿڡ�
	 */
	public final static Logger newDefaultLogger(){
		return new InitialLogger();
	}
	
	/**
	 * ���¼����������һ��ָ���Ŀ����ж���
	 * @param runnable ָ���Ŀ����ж���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public final static void invokeInEventQueue(Runnable runnable){
		Objects.requireNonNull(runnable, "��ڲ��� runnable ����Ϊ null��");
		
		if(SwingUtilities.isEventDispatchThread()){
			runnable.run();
		}else{
			SwingUtilities.invokeLater(runnable);
		}
	}
	
	/**
	 * ��ʵ�����������һ��ָ���Ŀ����ж���
	 * <p> ��ָ���Ŀ����ж������н���֮ǰ����ǰ�߳̽���������״̬��
	 * @param runnable ָ���Ŀ����ж���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 * @throws InvocationTargetException <code>runnable</code>	����ʱ�׳��쳣��
	 * @throws InterruptedException ����ȴ��¼�ָ���߳�ִ���ִ�� <code>runnable.run()</code>ʱ���ж� 
	 */
	public final static void invokeAndWaitInEventQueue(Runnable runnable) throws InvocationTargetException, InterruptedException{
		Objects.requireNonNull(runnable, "��ڲ��� runnable ����Ϊ null��");
		
		if(SwingUtilities.isEventDispatchThread()){
			runnable.run();
		}else{
			SwingUtilities.invokeAndWait(runnable);
		}
	}
	
	
	

	/**
	 * Ĭ�ϼ�¼���ӿڡ�
	 * <p> �ü�¼���������κβ�����
	 * @author  DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class InitialLogger implements Logger{

		@Override
		public void trace(String message) {}
		@Override
		public void debug(String message) {}
		@Override
		public void info(String message) {}
		@Override
		public void warn(String message) {}
		@Override
		public void warn(String message, Throwable t) {}
		@Override
		public void error(String message, Throwable t) {}
		@Override
		public void fatal(String message, Throwable t) {}
		
	}

	//��ֹ�ⲿʵ����
	private Mh4wUtil(){}

}

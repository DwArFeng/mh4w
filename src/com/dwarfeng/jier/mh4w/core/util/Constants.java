package com.dwarfeng.jier.mh4w.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.jier.mh4w.core.model.eum.CoreConfig;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.eum.LoggerStringKey;
import com.dwarfeng.jier.mh4w.core.model.struct.LoggerInfo;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangInfo;
import com.dwarfeng.jier.mh4w.core.model.struct.ProcessException;

public final class Constants {

	
	private final static Set<LoggerInfo> defaultLoggerInfos;
	private final static String mutilangLabel = "��������";
	private final static MutilangInfo defaultMutilangInfo = new InnerMutilangInfo(mutilangLabel, new HashMap<>());
	private final static String missingString = "!�ı�ȱʧ";
	private final static ResourceBundle loggerMutilangResourceBundle = ResourceBundle.getBundle(
	"com.dwarfeng.jier.mh4w.resource.defaultres.mutilang.logger.default");
	private final static ResourceBundle labelMutilangResourceBundle = ResourceBundle.getBundle(
	"com.dwarfeng.jier.mh4w.resource.defaultres.mutilang.label.default");
	private final static MutilangInfo defaultLoggerMutilangInfo;
	private final static MutilangInfo defaultLabelMutilangInfo;
	private final static Mutilang defaultLoggerMutilang = new DefaultLoggerMutilang();
	private final static Mutilang defaultLabelMutilang = new DefaultLabelMutilang();
	private final static List<CoreConfig> coreConfigOrder = new ArrayList<>(CoreConfig.values().length);

	static{
		
		defaultLoggerInfos = new HashSet<>(Arrays.asList(new LoggerInfo[]{new InnerLoggerInfo("std.all")}));
		
		Map<String, String> loggerMutilangDefaultMap = new HashMap<>();
		for(Name name : LoggerStringKey.values()){
			try{
				loggerMutilangDefaultMap.put(name.getName(), loggerMutilangResourceBundle.getString(name.getName()));
			}catch (MissingResourceException e) {
				loggerMutilangDefaultMap.put(name.getName(), missingString);
			}
		}
		
		Map<String, String> labelMutilangDefaultMap = new HashMap<>();
		for(Name name : LabelStringKey.values()){
			try{
				labelMutilangDefaultMap.put(name.getName(), labelMutilangResourceBundle.getString(name.getName()));
			}catch (MissingResourceException e) {
				labelMutilangDefaultMap.put(name.getName(), missingString);
			}
		}
		
		defaultLoggerMutilangInfo = new InnerMutilangInfo(mutilangLabel, loggerMutilangDefaultMap);
		defaultLabelMutilangInfo = new InnerMutilangInfo(mutilangLabel, labelMutilangDefaultMap);
		
		coreConfigOrder.add(CoreConfig.ATTENDANCE_ROW_START);
		coreConfigOrder.add(CoreConfig.ATTENDANCE_COLUMN_DEPARTMENT);
		coreConfigOrder.add(CoreConfig.ATTENDANCE_COLUMN_WORKNUMBER);
		coreConfigOrder.add(CoreConfig.ATTENDANCE_COLUMN_NAME);
		coreConfigOrder.add(CoreConfig.ATTENDANCE_COLUMN_DATE);
		coreConfigOrder.add(CoreConfig.ATTENDANCE_COLUMN_SHIFT);
		coreConfigOrder.add(CoreConfig.ATTENDANCE_COLUMN_RECORD);
		//TODO ��Ҫ�������
		coreConfigOrder.add(CoreConfig.MUTILANG_LABEL);
		coreConfigOrder.add(CoreConfig.MUTILANG_LOGGER);
	}

	/**
	 * ��ȡĬ�ϵļ�¼�����Ƽ��ϡ�
	 * @return Ĭ�ϵļ�¼�����Ƽ��ϡ�
	 */
	public final static Set<LoggerInfo> getDefaultLoggerInfos(){
		return defaultLoggerInfos;
	}

	public final static MutilangInfo getDefaultMutilangInfo(){
		return defaultMutilangInfo;
	}

	/**
	 * ��ȡ�ı�ȱʧ�ֶΡ�
	 * @return �ı�ȱʧ�ֶΡ�
	 */
	public final static String getDefaultMissingString(){
		return missingString;
	}

	/**
	 * ��ȡĬ�ϵļ�¼����������Ϣ��
	 * @return Ĭ�ϵļ�¼����������Ϣ��
	 */
	public final static MutilangInfo getDefaultLoggerMutilangInfo(){
		return defaultLoggerMutilangInfo;
	}

	/**
	 * ��ȡĬ�ϵı�ǩ��������Ϣ��
	 * @return Ĭ�ϵı�ǩ��������Ϣ��
	 */
	public final static MutilangInfo getDefaultLabelMutilangInfo(){
		return defaultLabelMutilangInfo;
	}
	
	

	/**
	 * ��ȡ��¼�������Խӿڵ�֧�ּ����ϡ�
	 * @return ��¼�������Խӿڵ�֧�ּ����ϡ�
	 */
	public static Set<String> getLoggerMutilangSupportedKeys() {
		try {
			return Collections.unmodifiableSet(defaultLoggerMutilangInfo.getMutilangMap().keySet());
		} catch (ProcessException ignore) {
			//�����׳��쳣
			return null;
		}
	}

	/**
	 * ��ȡ��ǩ�����Խӿڵ�֧�ּ����ϡ�
	 * @return ��ǩ�����Խӿڵ�֧�ּ����ϡ�
	 */
	public static Set<String> getLabelMutilangSupportedKeys() {
		try {
			return Collections.unmodifiableSet(defaultLabelMutilangInfo.getMutilangMap().keySet());
		} catch (ProcessException ignore) {
			//�����׳��쳣
			return null;
		}
	}
	
	/**
	 * ��ȡĬ�ϵļ�¼�������Խӿڡ�
	 * @return Ĭ�ϵļ�¼�������Խӿڡ�
	 */
	public static Mutilang getDefaultLoggerMutilang(){
		return defaultLoggerMutilang;
	}
	
	/**
	 * ��ȡĬ�ϵı�ǩ�����Խӿڡ�
	 * @return Ĭ�ϵı�ǩ�����Խӿڡ�
	 */
	public static Mutilang getDefaultLabelMutilang(){
		return defaultLabelMutilang;
	}
	
	/**
	 * ��ȡ�������õ���ʾ˳��
	 * <p> ����ͼ�У����õ�˳��Ӧ�ð��ո��б�ָ����˳����ʾ��
	 * @return �������õ���ʾ˳��
	 */
	public static List<CoreConfig> getCoreConfigOrder(){
		return coreConfigOrder;
	}

	/**
	 * �ڲ���������Ϣ��
	 * <p> ��������Ϣ���ڲ�ʵ�֡�
	 * @author  DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class InnerMutilangInfo implements MutilangInfo {
		
		private final String label;
		private final Map<String, String> mutilangMap;
		
		public InnerMutilangInfo(String label, Map<String, String> mutilangMap) {
			Objects.requireNonNull(label, "��ڲ��� label ����Ϊ null��");
			Objects.requireNonNull(mutilangMap, "��ڲ��� mutilangMap ����Ϊ null��");
			
			this.label = label;
			this.mutilangMap = mutilangMap;
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.MutilangInfo#getLabel()
		 */
		@Override
		public String getLabel() {
			return this.label;
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.MutilangAttribute#getMutilangMap()
		 */
		@Override
		public Map<String, String> getMutilangMap() {
			return Collections.unmodifiableMap(mutilangMap);
		}
	
	}

	/**
	 * �ڲ���¼����Ϣ��
	 * <p> ��¼����Ϣ���ڲ�ʵ�֡�
	 * @author DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class InnerLoggerInfo implements LoggerInfo{
	
		private final String name;
		
		public InnerLoggerInfo(String name) {
			Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");
			this.name = name;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.LoggerInfo#getName()
		 */
		@Override
		public String getName() {
			return name;
		}
		
	}
	
	/**
	 * Ĭ�ϼ�¼�������Խӿڡ�
	 * <p> ʹ�ó��������õļ������ġ�
	 * @author  DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class DefaultLoggerMutilang implements Mutilang {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(java.lang.String)
		 */
		@Override
		public String getString(String key) {
			try {
				if(! getDefaultLoggerMutilangInfo().getMutilangMap().containsKey(key)){
					throw new IllegalArgumentException("�˶����Խӿڲ�֧�ָü�");
				}
				return getDefaultLoggerMutilangInfo().getMutilangMap().getOrDefault(key, getDefaultMissingString());
			} catch (ProcessException ignore) {
				//�����׳��쳣
				return getDefaultMissingString();
			}
		}
		
	}
	
	/**
	 * Ĭ�ϼ�¼�������Խӿڡ�
	 * <p> ʹ�ó��������õļ������ġ�
	 * @author DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class DefaultLabelMutilang implements Mutilang{

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(java.lang.String)
		 */
		@Override
		public String getString(String key) {
			try {
				if(! getDefaultLabelMutilangInfo().getMutilangMap().containsKey(key)){
					throw new IllegalArgumentException("�˶����Խӿڲ�֧�ָü�");
				}
				return getDefaultLabelMutilangInfo().getMutilangMap().getOrDefault(key, getDefaultMissingString());
			} catch (ProcessException ignore) {
				//�����׳��쳣
				return getDefaultMissingString();
			}
		}
		
	}

	//��ֹ�ⲿʵ����
	private Constants() {}
	
}

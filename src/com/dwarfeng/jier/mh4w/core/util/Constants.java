package com.dwarfeng.jier.mh4w.core.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.eum.LoggerStringKey;
import com.dwarfeng.jier.mh4w.core.model.struct.LoggerInfo;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangInfo;
import com.dwarfeng.jier.mh4w.core.model.struct.ProcessException;

public final class Constants {

	
	private final static Set<LoggerInfo> defaultLoggerInfos;
	private final static String mutilangLabel = "简体中文";
	private final static MutilangInfo defaultMutilangInfo = new InnerMutilangInfo(mutilangLabel, new HashMap<>());
	private final static String missingString = "!文本缺失";
	private final static ResourceBundle loggerMutilangResourceBundle = ResourceBundle.getBundle(
	"com.dwarfeng.jier.mh4w.resource.defaultres.mutilang.logger.default");
	private final static ResourceBundle labelMutilangResourceBundle = ResourceBundle.getBundle(
	"com.dwarfeng.jier.mh4w.resource.defaultres.mutilang.label.default");
	private final static MutilangInfo defaultLoggerMutilangInfo;
	private final static MutilangInfo defaultLabelMutilangInfo;

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
	}

	/**
	 * 获取默认的记录器名称集合。
	 * @return 默认的记录器名称集合。
	 */
	public final static Set<LoggerInfo> getDefaultLoggerInfos(){
		return defaultLoggerInfos;
	}

	public final static MutilangInfo getDefaultMutilangInfo(){
		return defaultMutilangInfo;
	}

	/**
	 * 获取文本缺失字段。
	 * @return 文本缺失字段。
	 */
	public final static String getDefaultMissingString(){
		return missingString;
	}

	/**
	 * 获取默认的记录器多语言信息。
	 * @return 默认的记录器多语言信息。
	 */
	public final static MutilangInfo getDefaultLoggerMutilangInfo(){
		return defaultLoggerMutilangInfo;
	}

	/**
	 * 获取默认的标签多语言信息。
	 * @return 默认的标签多语言信息。
	 */
	public final static MutilangInfo getDefaultLabelMutilangInfo(){
		return defaultLabelMutilangInfo;
	}

	/**
	 * 获取记录器多语言接口的支持键集合。
	 * @return 记录器多语言接口的支持键集合。
	 */
	public static Set<String> getLoggerMutilangSupportedKeys() {
		try {
			return Collections.unmodifiableSet(defaultLoggerMutilangInfo.getMutilangMap().keySet());
		} catch (ProcessException ignore) {
			//不会抛出异常
			return null;
		}
	}

	/**
	 * 获取标签多语言接口的支持键集合。
	 * @return 标签多语言接口的支持键集合。
	 */
	public static Set<String> getLabelMutilangSupportedKeys() {
		try {
			return Collections.unmodifiableSet(defaultLabelMutilangInfo.getMutilangMap().keySet());
		} catch (ProcessException ignore) {
			//不会抛出异常
			return null;
		}
	}

	/**
	 * 内部多语言信息。
	 * <p> 多语言信息的内部实现。
	 * @author  DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class InnerMutilangInfo implements MutilangInfo {
		
		private final String label;
		private final Map<String, String> mutilangMap;
		
		public InnerMutilangInfo(String label, Map<String, String> mutilangMap) {
			Objects.requireNonNull(label, "入口参数 label 不能为 null。");
			Objects.requireNonNull(mutilangMap, "入口参数 mutilangMap 不能为 null。");
			
			this.label = label;
			this.mutilangMap = mutilangMap;
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangInfo#getLabel()
		 */
		@Override
		public String getLabel() {
			return this.label;
		}
	
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangInfo#getMutilangMap()
		 */
		@Override
		public Map<String, String> getMutilangMap() {
			return Collections.unmodifiableMap(mutilangMap);
		}
	
	}

	/**
	 * 内部记录器信息。
	 * <p> 记录器信息的内部实现。
	 * @author DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class InnerLoggerInfo implements LoggerInfo{
	
		private final String name;
		
		public InnerLoggerInfo(String name) {
			Objects.requireNonNull(name, "入口参数 name 不能为 null。");
			this.name = name;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.struct.LoggerInfo#getName()
		 */
		@Override
		public String getName() {
			return name;
		}
		
	}

	//禁止外部实例化
	private Constants() {}
	
}

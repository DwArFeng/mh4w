package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.InputStream;
import java.util.Objects;
import java.util.Set;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.cm.LoggerModel;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultLoggerInfo;

/**
 * xml记录器模型读取器。
 * <p> 使用xml读取多语言模型。
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public final class XmlLoggerLoader extends StreamLoader<LoggerModel> {

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public XmlLoggerLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(LoggerModel loggerModel) throws LoadFailedException {
		Objects.requireNonNull(loggerModel, "入口参数 loggerModel 不能为 null。");

		try{
			ConfigurationSource cs = new ConfigurationSource(in);
			LoggerContext loggerContext =  Configurator.initialize(null, cs);
			Configuration cfg = loggerContext.getConfiguration();
			Set<String> loggerNames = cfg.getLoggers().keySet();
			
			loggerModel.setLoggerContext(loggerContext);
			for(String loggerName : loggerNames){
				loggerModel.add(new DefaultLoggerInfo(loggerName));
			}
		}catch (Exception e) {
			throw new LoadFailedException("无法向指定的记录器模型中读取流中的数据", e);
		}

	}

}

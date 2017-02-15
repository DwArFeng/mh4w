package com.dwarfeng.jier.mh4w.core.model.struct;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 默认多语言信息。
 * <p> 多语言信息的默认实现。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultMutilangInfo implements MutilangInfo {
	
	private final String label;
	private final File file;
	
	/**
	 * 新实例。
	 * @param label 指定的标签。
	 * @param file 指定的文件。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultMutilangInfo(String label, File file) {
		Objects.requireNonNull(label, "入口参数 label 不能为 null。");
		Objects.requireNonNull(file, "入口参数 file 不能为 null。");
		
		this.label = label;
		this.file = file;
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
	public Map<String, String> getMutilangMap() throws ProcessException {
		InputStream in = null;
		try{
			in = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(in);
			Map<String, String> mutilangMap = new HashMap<>();
			for(String key : properties.stringPropertyNames()){
				mutilangMap.put(key, properties.getProperty(key));
			}
			return mutilangMap;
		}catch (IOException e) {
			throw new ProcessException("读取数据时出现异常", e);
		}finally {
			if(Objects.nonNull(in)){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

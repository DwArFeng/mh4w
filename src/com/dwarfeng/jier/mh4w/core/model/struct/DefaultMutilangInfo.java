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
 * Ĭ�϶�������Ϣ��
 * <p> ��������Ϣ��Ĭ��ʵ�֡�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultMutilangInfo implements MutilangInfo {
	
	private final String label;
	private final File file;
	
	/**
	 * ��ʵ����
	 * @param label ָ���ı�ǩ��
	 * @param file ָ�����ļ���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultMutilangInfo(String label, File file) {
		Objects.requireNonNull(label, "��ڲ��� label ����Ϊ null��");
		Objects.requireNonNull(file, "��ڲ��� file ����Ϊ null��");
		
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
			throw new ProcessException("��ȡ����ʱ�����쳣", e);
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

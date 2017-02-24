package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.InputStream;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeDateTypeEntry;

/**
 * Xml �������Ͷ�ȡ����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlDateTypeLoader extends StreamLoader<Set<UnsafeDateTypeEntry>> {

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException  ��ڲ���Ϊ <code>null</code>��
	 */
	public XmlDateTypeLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(Set<UnsafeDateTypeEntry> unsafeDateTypeEntries) throws LoadFailedException {
		Objects.requireNonNull(unsafeDateTypeEntries, "��ڲ��� unsafeDateTypeEntries ����Ϊ null��");
		
		
	}

}

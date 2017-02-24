package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.InputStream;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeDateTypeEntry;

/**
 * Xml 日期类型读取器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlDateTypeLoader extends StreamLoader<Set<UnsafeDateTypeEntry>> {

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException  入口参数为 <code>null</code>。
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
		Objects.requireNonNull(unsafeDateTypeEntries, "入口参数 unsafeDateTypeEntries 不能为 null。");
		
		
	}

}

package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultUnsafeDateTypeEntry;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeDateTypeEntry;

/**
 * Xml 日期类型读取器。
 * @author DwArFeng
 * @since 0.0.1-beta
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
		
		try{
			SAXReader reader = new SAXReader();
			
			Element root = null;
			try{
				root = reader.read(in).getRootElement();
			}finally {
				in.close();
			}
			
			/*
			 * 根据 dom4j 的相关说明，此处转换是安全的。
			 */
			@SuppressWarnings("unchecked")
			List<Element> dates = (List<Element>)root.elements("date");
			
			next:
			for(Element date : dates){
				try{
					String year = date.attributeValue("year");
					String month = date.attributeValue("month");
					String day = date.attributeValue("day");
					String dateType = date.attributeValue("date_type");
					
					UnsafeDateTypeEntry unsafeDateTypeEntry = new DefaultUnsafeDateTypeEntry(year, month, day, dateType);
					unsafeDateTypeEntries.add(unsafeDateTypeEntry);
				}catch (Exception e) {
					continue next;
				}
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("不安全日期读取器 - 无法向指定的不安全日期入口集合中读取流中的数据", e);
		}
	}

}

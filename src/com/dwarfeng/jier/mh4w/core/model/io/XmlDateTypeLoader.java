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
 * Xml �������Ͷ�ȡ����
 * @author DwArFeng
 * @since 0.0.1-beta
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
		
		try{
			SAXReader reader = new SAXReader();
			
			Element root = null;
			try{
				root = reader.read(in).getRootElement();
			}finally {
				in.close();
			}
			
			/*
			 * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
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
			throw new LoadFailedException("����ȫ���ڶ�ȡ�� - �޷���ָ���Ĳ���ȫ������ڼ����ж�ȡ���е�����", e);
		}
	}

}

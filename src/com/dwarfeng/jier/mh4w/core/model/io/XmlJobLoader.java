package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultUnsafeJob;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeJob;

/**
 * Xml ������ȡ����
 * <p> ʹ�� xml ��ȡ����ȫ������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlJobLoader extends StreamLoader<Set<UnsafeJob>> {

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public XmlJobLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(Set<UnsafeJob> unsafeJobs) throws LoadFailedException {
		Objects.requireNonNull(unsafeJobs, "��ڲ��� unsafeJobs ����Ϊ null��");
		
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
			List<Element> jobs = (List<Element>)root.elements("job");
			
			next:
			for(Element job : jobs){
				try{
					String name = job.element("name").attributeValue("value");
					String valuePerHour = job.element("value-per-hour").attributeValue("value");
					String originalColumn = job.element("column").attributeValue("value");
					
					UnsafeJob unsafeJob = new DefaultUnsafeJob(name, valuePerHour, originalColumn);
					unsafeJobs.add(unsafeJob);
				}catch (Exception e) {
					continue next;
				}
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("����ȫ������ʷ��ȡ��-�޷���ָ���Ĳ���ȫ������ʷ�б��ж�ȡ���е�����", e);
		}
	}

}

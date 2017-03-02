package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultUnsafeAttendanceOffset;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset;

/**
 * Xml ���ڲ�����ȡ����
 * <p> ͨ�� xml ��ȡ���ڲ������ݡ�
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class XmlAttendanceOffsetLoader extends StreamLoader<Set<UnsafeAttendanceOffset>>{

	/**
	 * ��ʵ����
	 * @param in ��������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public XmlAttendanceOffsetLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(Set<UnsafeAttendanceOffset> unsafeAttendanceOffsets) throws LoadFailedException {
		Objects.requireNonNull(unsafeAttendanceOffsets, "��ڲ��� unsafeAttendanceOffsets ����Ϊ null��");
		
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
			List<Element> datas = (List<Element>)root.elements("data");
			
			next:
			for(Element data : datas){
				try{
					String workNumber = data.attributeValue("work_number");
					String name = data.attributeValue("name");
					String department = data.attributeValue("department");
					String value = data.attributeValue("value");
					String description = data.attributeValue("description");
					
					UnsafeAttendanceOffset unsafeAttendanceOffset = new DefaultUnsafeAttendanceOffset(name, department, workNumber, value, description);
					unsafeAttendanceOffsets.add(unsafeAttendanceOffset);
				}catch (Exception e) {
					continue next;
				}
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("����ȫ���ڶ�ȡ�� - �޷���ָ���Ĳ���ȫ������ڼ����ж�ȡ���е�����", e);
		}
	}

}

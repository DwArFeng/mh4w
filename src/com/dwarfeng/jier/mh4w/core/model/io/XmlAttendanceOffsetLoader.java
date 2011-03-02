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
 * Xml 考勤补偿读取器。
 * <p> 通过 xml 读取考勤补偿数据。
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class XmlAttendanceOffsetLoader extends StreamLoader<Set<UnsafeAttendanceOffset>>{

	/**
	 * 新实例。
	 * @param in 输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
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
		Objects.requireNonNull(unsafeAttendanceOffsets, "入口参数 unsafeAttendanceOffsets 不能为 null。");
		
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
			throw new LoadFailedException("不安全日期读取器 - 无法向指定的不安全日期入口集合中读取流中的数据", e);
		}
	}

}

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
 * Xml 工作读取器。
 * <p> 使用 xml 读取不安全工作。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlJobLoader extends StreamLoader<Set<UnsafeJob>> {

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
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
		Objects.requireNonNull(unsafeJobs, "入口参数 unsafeJobs 不能为 null。");
		
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
			throw new LoadFailedException("不安全工具历史读取器-无法向指定的不安全工具历史列表中读取流中的数据", e);
		}
	}

}

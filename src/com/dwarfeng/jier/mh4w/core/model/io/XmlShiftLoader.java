package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultUnsafeShift;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeShift;

/**
 * Xml 班次读取器。
 * <p> 通过 xml 读取不安全班次信息。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class XmlShiftLoader extends StreamLoader<Set<UnsafeShift>>{

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException  入口参数为 <code>null</code>。
	 */
	public XmlShiftLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(Set<UnsafeShift> unsafeShifts) throws LoadFailedException {
		Objects.requireNonNull(unsafeShifts, "入口参数 unsafeShifts 不能为 null。");

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
			List<Element> shifts = (List<Element>)root.elements("shift");
			
			next:
			for(Element shift : shifts){
				try{
					
					/*
					 * 根据 dom4j 的相关说明，此处转换是安全的。
					 */
					@SuppressWarnings("unchecked")
					List<Element> shiftSectionElements = (List<Element>)shift.elements("shift_section");
					
					/*
					 * 根据 dom4j 的相关说明，此处转换是安全的。
					 */
					@SuppressWarnings("unchecked")
					List<Element> restSectionElements = (List<Element>)shift.elements("rest_section");
					
					/*
					 * 根据 dom4j 的相关说明，此处转换是安全的。
					 */
					@SuppressWarnings("unchecked")
					List<Element> extraShiftSectionElements = (List<Element>)shift.elements("extra_shift_section");
					
					String name;
					String[][] shiftSections;
					String[][] restSections;
					String[][] extraShiftSections;
					
					name = shift.element("name").attributeValue("value");
					
					shiftSections = new String[shiftSectionElements.size()][2];
					for(int i = 0 ; i < shiftSections.length ; i ++){
						String start = shiftSectionElements.get(i).attributeValue("start");
						String end =  shiftSectionElements.get(i).attributeValue("end");
						shiftSections[i][0] = start;
						shiftSections[i][1] = end;
					}
					
					restSections = new String[restSectionElements.size()][2];
					for(int i = 0 ; i < restSections.length ; i ++){
						String start = restSectionElements.get(i).attributeValue("start");
						String end =  restSectionElements.get(i).attributeValue("end");
						restSections[i][0] = start;
						restSections[i][1] = end;
					}
					
					extraShiftSections = new String[extraShiftSectionElements.size()][2];
					for(int i = 0 ; i < extraShiftSections.length ; i ++){
						String start = extraShiftSectionElements.get(i).attributeValue("start");
						String end =  extraShiftSectionElements.get(i).attributeValue("end");
						extraShiftSections[i][0] = start;
						extraShiftSections[i][1] = end;
					}
					
					UnsafeShift unsafeShift = new DefaultUnsafeShift(name, shiftSections, restSections, extraShiftSections);
					unsafeShifts.add(unsafeShift);
					
				}catch (Exception e) {
					continue next;
				}
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("不安全工具历史读取器-无法向指定的不安全工具历史列表中读取流中的数据", e);
		}
		
	}

}

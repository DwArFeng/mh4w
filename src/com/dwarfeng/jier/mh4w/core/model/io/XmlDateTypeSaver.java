package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.dwarfeng.dutil.basic.io.SaveFailedException;
import com.dwarfeng.dutil.basic.io.StreamSaver;
import com.dwarfeng.jier.mh4w.core.model.cm.DateTypeModel;
import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;

/**
 * Xml 日期类型保存器。
 * <p> 使用 xml 对日期类型模型进行保存。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class XmlDateTypeSaver extends StreamSaver<DateTypeModel> {

	/**
	 * 新实例。
	 * @param out 指定的输出流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public XmlDateTypeSaver(OutputStream out) {
		super(out);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Saver#save(java.lang.Object)
	 */
	@Override
	public void save(DateTypeModel dateTypeModel) throws SaveFailedException {
		Objects.requireNonNull(dateTypeModel, "入口参数 dateTypeModel 不能为 null。");
		
		try{
			Element root = DocumentHelper.createElement("root");
			
			for(Map.Entry<CountDate, DateType> entry : dateTypeModel.entrySet()){
				Element history = DocumentHelper.createElement("date");
				history.addAttribute("year", entry.getKey().getYear() + "");
				history.addAttribute("month", entry.getKey().getMonth() + "");
				history.addAttribute("day", entry.getKey().getDay() + "");
				history.addAttribute("dateType", entry.getValue().toString());
				root.add(history);
			}
			
			Document document = DocumentHelper.createDocument(root);
			
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();
			outputFormat.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(out, outputFormat);
			
			try{
				writer.write(document);
			}finally {
				writer.close();
			}
			
		}catch (Exception e) {
			throw new SaveFailedException("工具历史保存器-无法向指定的流中保存工具历史模型的数据", e);
		}
	}

}

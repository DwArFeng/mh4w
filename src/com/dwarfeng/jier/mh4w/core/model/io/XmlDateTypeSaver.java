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
 * Xml �������ͱ�������
 * <p> ʹ�� xml ����������ģ�ͽ��б��档
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class XmlDateTypeSaver extends StreamSaver<DateTypeModel> {

	/**
	 * ��ʵ����
	 * @param out ָ�����������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
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
		Objects.requireNonNull(dateTypeModel, "��ڲ��� dateTypeModel ����Ϊ null��");
		
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
			throw new SaveFailedException("������ʷ������-�޷���ָ�������б��湤����ʷģ�͵�����", e);
		}
	}

}

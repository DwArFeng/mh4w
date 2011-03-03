package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.OutputStream;
import java.util.Objects;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.dwarfeng.dutil.basic.io.SaveFailedException;
import com.dwarfeng.dutil.basic.io.StreamSaver;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceOffset;

/**
 * Xml ���ڲ�����������
 * <p> ʹ�� xml ���濼�ڲ������ݡ�
 * @author DwArFeng
 * @since 1.0.0
 */
public final class XmlAttendanceOffsetSaver extends StreamSaver<DataListModel<AttendanceOffset>>{

	/**
	 * ��ʵ����
	 * @param out ָ�����������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public XmlAttendanceOffsetSaver(OutputStream out) {
		super(out);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Saver#save(java.lang.Object)
	 */
	@Override
	public void save(DataListModel<AttendanceOffset> attendanceOffsetModel) throws SaveFailedException {
		Objects.requireNonNull(attendanceOffsetModel, "��ڲ��� attendanceOffsetModel ����Ϊ null��");
		
		try{
			Element root = DocumentHelper.createElement("root");
			
			for(AttendanceOffset attendanceOffset : attendanceOffsetModel){
				Element data = DocumentHelper.createElement("data");
				data.addAttribute("work_number", attendanceOffset.getPerson().getWorkNumber());
				data.addAttribute("name", attendanceOffset.getPerson().getName());
				data.addAttribute("department", attendanceOffset.getPerson().getDepartment());
				data.addAttribute("value", attendanceOffset.getValue() + "");
				data.addAttribute("description", attendanceOffset.getDescription());

				root.add(data);
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

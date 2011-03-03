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
 * Xml 考勤补偿保存器。
 * <p> 使用 xml 保存考勤补偿数据。
 * @author DwArFeng
 * @since 1.0.0
 */
public final class XmlAttendanceOffsetSaver extends StreamSaver<DataListModel<AttendanceOffset>>{

	/**
	 * 新实例。
	 * @param out 指定的输出流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
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
		Objects.requireNonNull(attendanceOffsetModel, "入口参数 attendanceOffsetModel 不能为 null。");
		
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
			throw new SaveFailedException("工具历史保存器-无法向指定的流中保存工具历史模型的数据", e);
		}
	}

}

package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.control.Mh4w;
import com.dwarfeng.jier.mh4w.core.model.cm.ResourceModel;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultResource;

/**
 * xml��Դģ�Ͷ�ȡ����
 * <p> ʹ��xml��ȡ��Դģ�͡�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlResourceLoader extends StreamLoader<ResourceModel> {

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public XmlResourceLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(ResourceModel resourceModel) throws LoadFailedException {
		Objects.requireNonNull(resourceModel, "��ڲ��� resourceModel ����Ϊ null��");

		try{
			SAXReader reader = new SAXReader();
			Element root = reader.read(in).getRootElement();
			
			/*
			 * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
			 */
			@SuppressWarnings("unchecked")
			List<Element> infos = (List<Element>)root.elements("info");
			
			for(Element info : infos){
				String defString = info.attributeValue("default");
				String resString = info.attributeValue("path");
				String key = info.attributeValue("key");
				
				if(Objects.isNull(defString) || Objects.isNull(resString) || Objects.isNull(key)) {
					throw new LoadFailedException("�����ļ�ȱʧ����");
				}
				
				URL def = Mh4w.class.getResource(defString);
				
				if(Objects.isNull(def)){
					throw new LoadFailedException("��������Դ��·������ȷ");
				}
				
				File res = new File(resString);
				
				resourceModel.put(key, new DefaultResource(def, res));
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("�޷�����Դģ���ж�ȡ���е�����", e);
		}
		
	}
	
	

}

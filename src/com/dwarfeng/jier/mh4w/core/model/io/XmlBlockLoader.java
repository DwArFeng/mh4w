package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.cm.BlockModel;

/**
 * xml�赲ģ�Ͷ�ȡ����
 * <p> ʹ��xml��ȡ�赲ģ�͡�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public class XmlBlockLoader extends StreamLoader<BlockModel> {

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public XmlBlockLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(BlockModel blockModel) throws LoadFailedException {
		Objects.requireNonNull(blockModel, "��ڲ��� libraryModel ����Ϊ null��");
		
		try{
			SAXReader reader = new SAXReader();
			
			Element root = null;
			try{
				root = reader.read(in).getRootElement();
			}finally {
				in.close();
			}
			
			List<?> keyList = root.elements("key");
			for(Object obj1 : keyList){
				Element key = (Element) obj1;
				List<?> dictionaryList = key.elements("dictionary");
				
				String keyValue = key.attributeValue("value");
				Set<String> dictionarySet = new HashSet<>();
				for(Object obj2 : dictionaryList){
					Element dictionary = (Element) obj2;
					dictionarySet.add(dictionary.attributeValue("value"));
				}
				
				blockModel.put(keyValue, dictionarySet);
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("�޷���ָ���Ŀ�ģ���ж�ȡ���е�����", e);
		}

	}

}

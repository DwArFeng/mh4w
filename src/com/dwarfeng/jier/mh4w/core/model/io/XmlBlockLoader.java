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
 * xml阻挡模型读取器。
 * <p> 使用xml读取阻挡模型。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public class XmlBlockLoader extends StreamLoader<BlockModel> {

	/**
	 * 新实例。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
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
		Objects.requireNonNull(blockModel, "入口参数 libraryModel 不能为 null。");
		
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
			throw new LoadFailedException("无法向指定的库模型中读取流中的数据", e);
		}

	}

}

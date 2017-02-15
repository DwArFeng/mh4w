package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.cm.MutilangModel;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultMutilangInfo;
import com.dwarfeng.jier.mh4w.core.util.LocaleUtil;

/**
 * xml������ģ�Ͷ�ȡ����
 * <p> ʹ��xml��ȡ������ģ�͡�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class XmlMutilangLoader extends StreamLoader<MutilangModel> {

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public XmlMutilangLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(MutilangModel mutilangModel) throws LoadFailedException {
		Objects.requireNonNull(mutilangModel, "��ڲ��� mutilangModel ����Ϊ null��");
		
		try{
			SAXReader reader = new SAXReader();
			
			Element root = null;
			try{
				root = reader.read(in).getRootElement();
			}finally {
				in.close();
			}
			
			String rootDirStr = root.attributeValue("dir");
			if(Objects.isNull(rootDirStr)){
				throw new LoadFailedException("��Ԫ��ȱʧdir����");
			}
			
			File dir = new File(rootDirStr);
			
			/*
			 * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
			 * 	<!--ʹ�����µĸ�ʽ��<info language="zh" country="CN" variant="" label="��������" file="zh_CN.properties"></info>-->
			 */
			@SuppressWarnings("unchecked")
			List<Element> mutilangInfos = (List<Element>)root.elements("info");
			for(Element mutilangInfo : mutilangInfos){
				String locale = mutilangInfo.attributeValue("locale");
				String label = mutilangInfo.attributeValue("label");
				String filePath = mutilangInfo.attributeValue("file");
				
				if(
						Objects.nonNull(locale) &&
						Objects.nonNull(label) &&
						Objects.nonNull(filePath)
						){
					
					File targetFile = new File(dir, filePath);
					mutilangModel.put(LocaleUtil.parseLocale(locale), new DefaultMutilangInfo(label, targetFile));
				}
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("�޷���ָ���Ķ�����ģ���ж�ȡ���е�����", e);
		}

	}

}

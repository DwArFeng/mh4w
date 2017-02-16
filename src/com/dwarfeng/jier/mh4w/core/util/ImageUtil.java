package com.dwarfeng.jier.mh4w.core.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.dwarfeng.jier.mh4w.core.control.Mh4w;
import com.dwarfeng.jier.mh4w.core.model.eum.ImageKey;
import com.dwarfeng.jier.mh4w.core.model.eum.ImageSize;

public final class ImageUtil {
	
	

	/**
	 * ��ȡָ��ͼƬ����Ӧ��ͼƬ��
	 * <p> ͼƬ����������ָ���Ĵ�С��
	 * @param imageKey ָ����ͼƬ����
	 * @param imageSize ͼƬ�Ĵ�С��
	 * @return ָ����ͼƬ����Ӧ��ͼƬ��
	 */
	public final static Image getImage(ImageKey imageKey, ImageSize imageSize){
		Objects.requireNonNull(imageKey, "��ڲ��� imageKey ����Ϊ null��");
		Objects.requireNonNull(imageSize, "��ڲ��� imageSize ����Ϊ null��");
	
		try {
			BufferedImage image = ImageIO.read(Mh4w.class.getResource(imageKey.getName()));
			int width = imageSize.getWidth();
			int height = imageSize.getHeight();
			if(image.getHeight() == height && image.getWidth() == width){
				return image;
			}else{
				return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			}
		} catch (Exception e) {
			try {
				BufferedImage image = ImageIO.read(Mh4w.class.getResource(ImageKey.IMG_LOAD_FAILED.getName()));
				int width = imageSize.getWidth();
				int height = imageSize.getHeight();
				if(image.getHeight() == height && image.getWidth() == width){
					return image;
				}else{
					return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				}
			} catch (Exception e1) {
				return null;
			}
		}
	}

	/**
	 * ��ȡָ��ͼƬ����ָ����С���Ŷ�����ͼƬ��
	 * @param image ָ����ͼƬ��
	 * @param imageSize ��Ҫ���ŵ��Ĵ�С��
	 * @return ���ź����ͼƬ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public final static Image scaleImage(Image image, ImageSize imageSize){
		Objects.requireNonNull(image, "��ڲ��� image ����Ϊ null��");
		Objects.requireNonNull(imageSize, "��ڲ��� imageSize ����Ϊ null��");
	
		if(image.getWidth(null) == imageSize.getWidth() && image.getHeight(null) == imageSize.getHeight()) return image;
		
		int width = imageSize.getWidth();
		int height = imageSize.getHeight();
		return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	
	/**
	 * ������ͼƬ���ӵ��ײ�ͼƬ֮�ϡ�
	 * @param bottom �ײ�ͼƬ��
	 * @param top ����ͼƬ��
	 * @param imageSize ͼƬ�Ĵ�С��
	 * @return ����֮���ͼƬ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public final static Image overlayImage(Image bottom, Image top, ImageSize imageSize){
		Objects.requireNonNull(bottom, "��ڲ��� bottom ����Ϊ null��");
		Objects.requireNonNull(top, "��ڲ��� top ����Ϊ null��");
		Objects.requireNonNull(imageSize, "��ڲ��� imageSize ����Ϊ null��");

		BufferedImage bufferedImage = new BufferedImage(imageSize.getWidth(), imageSize.getWidth(), BufferedImage.TYPE_INT_ARGB);
		Image scaleBottom = scaleImage(bottom, imageSize);
		Image scaleTop = scaleImage(top, imageSize);
		
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
		g2d.drawImage(scaleBottom, 0, 0, null);
		g2d.drawImage(scaleTop, 0, 0, null);
		g2d.dispose();
		
		return bufferedImage;
	}

	//��ֹ�ⲿʵ����
	private ImageUtil() {}
	
}

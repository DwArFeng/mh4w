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
	 * 获取指定图片键对应的图片。
	 * <p> 图片将被调整到指定的大小。
	 * @param imageKey 指定的图片键。
	 * @param imageSize 图片的大小。
	 * @return 指定的图片键对应的图片。
	 */
	public final static Image getImage(ImageKey imageKey, ImageSize imageSize){
		Objects.requireNonNull(imageKey, "入口参数 imageKey 不能为 null。");
		Objects.requireNonNull(imageSize, "入口参数 imageSize 不能为 null。");
	
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
	 * 获取指定图片按照指定大小缩放而来的图片。
	 * @param image 指定的图片。
	 * @param imageSize 需要缩放到的大小。
	 * @return 缩放后的新图片。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public final static Image scaleImage(Image image, ImageSize imageSize){
		Objects.requireNonNull(image, "入口参数 image 不能为 null。");
		Objects.requireNonNull(imageSize, "入口参数 imageSize 不能为 null。");
	
		if(image.getWidth(null) == imageSize.getWidth() && image.getHeight(null) == imageSize.getHeight()) return image;
		
		int width = imageSize.getWidth();
		int height = imageSize.getHeight();
		return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	
	/**
	 * 将顶部图片叠加到底部图片之上。
	 * @param bottom 底部图片。
	 * @param top 顶部图片。
	 * @param imageSize 图片的大小。
	 * @return 叠加之后的图片。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public final static Image overlayImage(Image bottom, Image top, ImageSize imageSize){
		Objects.requireNonNull(bottom, "入口参数 bottom 不能为 null。");
		Objects.requireNonNull(top, "入口参数 top 不能为 null。");
		Objects.requireNonNull(imageSize, "入口参数 imageSize 不能为 null。");

		BufferedImage bufferedImage = new BufferedImage(imageSize.getWidth(), imageSize.getWidth(), BufferedImage.TYPE_INT_ARGB);
		Image scaleBottom = scaleImage(bottom, imageSize);
		Image scaleTop = scaleImage(top, imageSize);
		
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
		g2d.drawImage(scaleBottom, 0, 0, null);
		g2d.drawImage(scaleTop, 0, 0, null);
		g2d.dispose();
		
		return bufferedImage;
	}

	//禁止外部实例化
	private ImageUtil() {}
	
}

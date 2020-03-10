package com.ccc.roentgen;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	public static BufferedImage[] spritesheets = new BufferedImage[2];
	public static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	public static BufferedImage loadImage(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(classLoader.getResource(path));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(path);
		}
		return image;
	}

	public static void loadSpriteSheet(int i, String path) {
		spritesheets[i] = loadImage(path);
	}

	public static BufferedImage getSprite(int s, int x, int y, int w, int h) {
		if (spritesheets[s]!=null) {
			return spritesheets[s].getSubimage(x, y, w, h);
		} else {
			return null;
		}
	}

}

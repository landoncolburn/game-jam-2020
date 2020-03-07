package com.ccc.roentgen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Base extends GameObject {
	
	BufferedImage sprite;
	
	public Base() {
		super(-256, -256, 512, 512, false, ID.BASE);
		sprite = BufferedImageLoader.loadImage("base1.png");
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g, double p) {
		g.drawImage(sprite, x, y, w, h, null);
	}

}

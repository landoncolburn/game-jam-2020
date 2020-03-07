package com.ccc.roentgen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Enemy extends GameObject {
	
	private int velX = 2;
	private int velY = 2;
	
	private int count = 0;
	
	private BufferedImage sprite;

	public Enemy(int x, int y) {
		super(x, y, 64, 96, true, ID.ENEMY);
		sprite = BufferedImageLoader.loadImage("enemy.png");
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		count++;
		if(count>200) {
			velX *= -1;
			velY *= -1;
			count = 0;
		}
	}

	@Override
	public void render(Graphics g, double p) {
		g.drawImage(sprite, x, y, w, h, null);
	}

}

package com.ccc.roentgen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Enemy extends GameObject {
	
	private double velX = 0;
	private double velY = 0;
	private GameObject target;
	
	private BufferedImage sprite;

	public Enemy(int x, int y) {
		super(x, y, 64, 96, true, ID.ENEMY);
		sprite = BufferedImageLoader.loadImage("enemy.png");
		target = Game.gameInstance.handler.getByID(ID.PLAYER).get(0);
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		if(Math.abs(x-target.getX())<5) {
			velX = 0;
		} else if(x<target.getX()) {
			velX = 1;
		} else if(x>target.getX()) {
			velX = -1;
		}
		
		if(Math.abs(y-target.getY())<5) {
			velY = 0;
		} else if(y<target.getY()) {
			velY = 1;
		} else if(y>target.getY()) {
			velY = -1;
		}
	}

	@Override
	public void render(Graphics g, double p) {
		g.drawImage(sprite, x, y, w, h, null);
	}

}

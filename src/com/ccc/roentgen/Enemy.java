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
		if(!(pushingX || pushingY)) {
			if(Math.abs(getCX()-target.getX())<5) {
				velX = 0;
			} else if(getCX()<target.getX()) {
				velX = 1;
			} else if(getCX()>target.getX()) {
				velX = -1;
			}
			
			if(Math.abs(getCY()-target.getY())<5) {
				velY = 0;
			} else if(getCY()<target.getY()) {
				velY = 1;
			} else if(getCY()>target.getY()) {
				velY = -1;
			}
		} else {
			velX = pushX;
			velY = pushY;
			if(pushX == 0) {
				pushingX = false;
			} else if(pushX > 0){
				pushX--;
			} else if(pushX < 0){
				pushX++;
			}
			if(pushY == 0) {
				pushingY = false;
			} else if(pushY > 0){
				pushY--;
			} else if(pushY < 0){
				pushY++;
			}
		}
	}
	
	public void push(boolean vert, int push) {
		if(vert) {
			pushY = push;
			pushingY = true;
		} else {
			pushX = push;
			pushingX = true;
		}
	}

	@Override
	public void render(Graphics g, double p) {
		g.drawImage(sprite, x, y, w, h, null);
	}

}

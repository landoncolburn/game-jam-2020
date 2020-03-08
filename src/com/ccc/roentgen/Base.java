package com.ccc.roentgen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Base extends GameObject {
	
	BufferedImage sprite;
	
	private int baseHealth = 100;
	private int timer = 0;

	public Base() {//Base constructor
		super(-256, -256, 512, 512, false, ID.BASE);
		sprite = BufferedImageLoader.loadImage("base1.png");
	}

	@Override
	public void tick() {
		int count = 0;
		timer++;
		for(GameObject go : Game.gameInstance.handler.gameObjects) {
			if(go.getID() == ID.ENEMY) {
				count++;
			}
		}
		if(timer>120) {
			timer = 0;
			baseHealth -= count;
			System.out.println(baseHealth);
		}
		
	}

	@Override
	public void render(Graphics g, double p) {
		g.drawImage(sprite, x, y, w, h, null);
	}
	
}

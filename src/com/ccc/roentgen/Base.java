package com.ccc.roentgen;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Base extends GameObject {
	
	BufferedImage sprite;
	
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
				if(getBounds().contains(go.getX(), go.getY())) {
					count++;
				}
			}
		}
		if(timer>120) {
			timer = 0;
			Game.gameInstance.healthBar.removeHP(count*3);
			if(Game.gameInstance.beingDamaged) {
				Game.gameInstance.healthBar.removeHP(5);
			}
		}
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}

	@Override
	public void render(Graphics g, double p) {
		g.drawImage(sprite, x, y, w, h, null);
	}
	
}

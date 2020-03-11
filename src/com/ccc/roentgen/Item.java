package com.ccc.roentgen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Item extends GameObject{

	private ItemType type;
	private BufferedImage sprite;
	private int count = 0;
	
	public Item(int x, int y, ItemType type) { //Item constructor
		super(x, y, 32, 32, false, ID.ITEM);
		this.type = type;
		
		switch(type) {
		case BAT:
			sprite = BufferedImageLoader.getSprite(2, 0, 0, 32, 32);
			break;
		case HEART:
			sprite = BufferedImageLoader.getSprite(2, 32, 0, 32, 32);
			break;
		case TURRET:
			break;
		}
		
	};
	
	public ItemType getType() {
		return type;
	}

	@Override
	public void tick() {
		count++;
		int difX = Game.gameInstance.player.getX()-getCX();
		int difY = Game.gameInstance.player.getY()-getCY();
		if(count>50 && Math.abs(difX)<10) {
			if(Math.abs(difY)<10) {
				Game.gameInstance.handler.removeObject(this);
				Game.gameInstance.healthBar.addHP(20);
			}
		}
		if(Math.abs(difX)<100) {
			if(Math.abs(difY)<100) {
				if(difX>0) {
					x++;
				} else {
					x--;
				}
				if(difY>0) {
					y++;
				} else {
					y--;
				}
			}
		}
		if(count > 600) {
			Game.gameInstance.handler.removeObject(this);
		}
	}

	@Override
	public void render(Graphics g, double p) {
		if(count < 480 || count / 10 % 2 == 0) {
			g.drawImage(sprite, x, y, w, h, null);
		}
	}
}

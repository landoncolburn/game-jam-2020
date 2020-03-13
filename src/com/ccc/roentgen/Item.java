package com.ccc.roentgen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Item extends GameObject {
	
	private static final int TICKS_BEFORE_DESPAWN = 600;

	private ItemType type;
	private BufferedImage sprite;
	private int count = 0;
	
	public Item(int x, int y, ItemType type) { //Item constructor
		super(x, y, 32, 32, false, ID.ITEM);
		this.type = type;
		
		switch(this.type) {
		case BAT:
			sprite = BufferedImageLoader.getSprite(2, 0, 0, 32, 32);
			break;
		case HEART:
			sprite = BufferedImageLoader.getSprite(2, 32, 0, 32, 32);
			break;
		case TURRET:
			break;
		case COIN:
			sprite = BufferedImageLoader.getSprite(2, 64, 0, 32, 32);
			break;
		case AXE:
			sprite = BufferedImageLoader.getSprite(2, 96, 0, 32, 32);
			break;
		}
		
	}
	
	public ItemType getType() {
		return type;
	}
	
	public BufferedImage getSprite() {
		return sprite;
	}

	@Override
	public void tick() {
		count++;
		int difX = Game.gameInstance.player.getX()-getCX();
		int difY = Game.gameInstance.player.getY()-getCY();
		if(count>50 && Math.abs(difX)<10) {
			if(Math.abs(difY)<10) {
				Game.gameInstance.handler.removeObject(this);
				pickup();
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
		if(count > TICKS_BEFORE_DESPAWN) {
			Game.gameInstance.handler.removeObject(this);
		}
	}
	
	public void pickup() {
		if(type.useOnPickup) {
			use();
		} else if(type.isWeapon) {
			Game.gameInstance.inventory.add(new Weapon(type));
		} else {
			Game.gameInstance.inventory.add(this);
		}
	}
	
	public void use() {
		switch(type) {
		case HEART:
			Game.gameInstance.healthBar.addHP(20);
			break;
		case COIN:
			Game.gameInstance.inventory.changeCoins(1);
			break;
		default:
			break;
		}
	}
	
	@Override
	public String toString() {
		return "Item: " + getType();
	}

	@Override
	public void render(Graphics g, double p) {
		if(count < TICKS_BEFORE_DESPAWN - 120 || count / 10 % 2 == 0) {
			g.drawImage(sprite, x, y, w, h, null);
		}
	}
}

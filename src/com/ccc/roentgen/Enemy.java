package com.ccc.roentgen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Enemy extends GameObject {
	
	private double velX = 0;
	private double velY = 0;
	private GameObject target;
	
	private int d = 0;
	private int count = 0;
	private boolean dead = false;
	private int deadCount = 0;
	
	private int stunFrames = 0;
	
	private BufferedImage[] sprite = new BufferedImage[4];
	private int hp = 10;

	public Enemy(int x, int y) {
		super(x, y, 64, 96, true, ID.ENEMY);
		sprite[0] = BufferedImageLoader.getSprite(1, 0, 0, 32, 48);
		sprite[1] = BufferedImageLoader.getSprite(1, 32, 0, 32, 48);
		sprite[2] = BufferedImageLoader.getSprite(1, 64, 0, 32, 48);
		sprite[3] = BufferedImageLoader.getSprite(1, 96, 0, 32, 48);
		target = Game.gameInstance.handler.getByID(ID.PLAYER).get(0);
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		if(!dead && count++>20 && d != 2) {
			count = 0;
			if(d==0) {
				d = 1;
			} else {
				d = 0;
			}
		} else if(d == 2 && count>60) {
			d = 0;
			count = 0;
		} else if(dead){
			d = 3;
			if(deadCount++>10) {
				Game.gameInstance.handler.removeObject(this);
				if((Math.random()*10)>6) {
					Game.gameInstance.handler.addObject(new Item(getCX(), getCY(), ItemType.HEART));
				}
			}
		}
		boolean closeX = false,closeY = false;
		if(stunFrames > 0) {
			stunFrames--;
		}
		if(!dead && stunFrames<1 && !(pushingX || pushingY)) {
			if(Math.abs(getCX()-target.getX())>200 || Math.abs(getCY()-target.getY())>200) {
				if(Math.abs(getCX()-0)<2) {
					velX = 0;
				} else if(getCX()<0) {
					velX = 1;
				} else if(getCX()>0) {
					velX = -1;
				}
				
				if(Math.abs(getCY()-0)<2) {
					velY = 0;
				} else if(getCY()<0) {
					velY = 1;
				} else if(getCY()>0) {
					velY = -1;
				}
			} else {
				if(Math.abs(getCX()-target.getX())<5) {
					velX = 0;
					closeX = true;
				} else if(getCX()<target.getX()) {
					velX = 1;
				} else if(getCX()>target.getX()) {
					velX = -1;
				}
				
				if(Math.abs(getCY()-target.getY())<5) {
					velY = 0;
					closeY = true;
				} else if(getCY()<target.getY()) {
					velY = 1;
				} else if(getCY()>target.getY()) {
					velY = -1;
				}
			}
		} else if(!dead && (pushingX||pushingY)){
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
		if(closeX&&closeY) {
			Game.gameInstance.beingDamaged = true;
		} else {
			Game.gameInstance.beingDamaged = false;
		}
	}
	
	public void damage() {
		if(hp>0) {
			hp--;
		}
		if(hp<1) {
			kill();
		}
		d = 2;
		stunFrames = 30;
	}
	
	public void kill() {		
		dead = true;
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
		g.drawImage(sprite[d], x, y, w, h, null);
	}

}

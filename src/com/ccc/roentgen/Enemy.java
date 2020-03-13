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
	private int specialCooldown = 360;
	
	private int speed = 0;
	
	private int stunFrames = 0;
	private EnemyType type;
	
	private BufferedImage[] sprite = new BufferedImage[4];
	
	private Drop[] drops;

	public Enemy(int x, int y, EnemyType e) {
		super(x, y, 64, 96, true, ID.ENEMY);
		this.type = e;
		drops = type.getDrops();
		sprite = type.getFrames();
		speed = type.getSpeed();
		maxHP = type.getHP();
		hp = maxHP;
		target = Game.gameInstance.handler.getByID(ID.PLAYER).get(0);
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		if(specialCooldown--<=0) {
			specialCooldown = 840;
			special();
		}
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
				drop();
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
					velX = speed;
				} else if(getCX()>0) {
					velX = -speed;
				}
				
				if(Math.abs(getCY()-0)<2) {
					velY = 0;
				} else if(getCY()<0) {
					velY = speed;
				} else if(getCY()>0) {
					velY = -speed;
				}
			} else {
				if(Math.abs(getCX()-target.getX())<5) {
					velX = 0;
					closeX = true;
				} else if(getCX()<target.getX()) {
					velX = speed;
				} else if(getCX()>target.getX()) {
					velX = -speed;
				}
				
				if(Math.abs(getCY()-target.getY())<5) {
					velY = 0;
					closeY = true;
				} else if(getCY()<target.getY()) {
					velY = speed;
				} else if(getCY()>target.getY()) {
					velY = -speed;
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
	
	public void damage(int amount) {
		if(hp>0) {
			hp -= amount;
		}
		if(hp<1) {
			dead=true;
		}
		d = 2;
		stunFrames = 30;
	}
	
	public void drop() {
		for(Drop dr : drops) {
			if(Math.random()<dr.getProbability()) {
				Game.gameInstance.handler.addObject(
						new Item(getCX()+((int)(Math.random()*30)-15), getCY()+((int)(Math.random()*30)-15), dr.getType()));
			}
		}
	}
	
	public void special() {
		switch(type) {
		case NECROMANCER:
			Game.gameInstance.handler.addObject(new Enemy(x-50, y, EnemyType.SKELETON));
			Game.gameInstance.handler.addObject(new Enemy(x+50, y, EnemyType.SKELETON));
			Game.gameInstance.handler.addObject(new Enemy(x, y-50, EnemyType.SKELETON));
			Game.gameInstance.handler.addObject(new Enemy(x, y+50, EnemyType.SKELETON));
			break;
		case SKELETON:
			speed = 3;
			break;
		case ZOMBIE:
			break;
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
		g.drawImage(sprite[d], x, y, w, h, null);
		g.drawImage(healthBarInside, x+18, y+2, (28*hp/maxHP), 4, null);
		g.drawImage(healthBar, x+16, y, 32, 8, null);
	}

}

package com.ccc.roentgen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

	double lastX, lastY;
	int as = 0;
	int d = 0;
	int count = 0;
	private int range = 100;
	private int atckCool = 60;

	BufferedImage[][] animations = new BufferedImage[3][3];
	BufferedImage smoke = BufferedImageLoader.loadImage("smoke.png");
	
	boolean attacking = false;

	int velX = 0;
	int velY = 0;

	public Player(int x, int y) {//Player constructor
		super(x, y, 64, 96, true, ID.PLAYER);
		animations[0][0] = BufferedImageLoader.getSprite(64, 0, 32, 48);
		animations[0][1] = BufferedImageLoader.getSprite(96, 0, 32, 48);
		animations[0][2] = BufferedImageLoader.getSprite(128, 0, 32, 48);
		animations[1][0] = BufferedImageLoader.getSprite(160, 0, 32, 48);
		animations[1][1] = BufferedImageLoader.getSprite(192, 0, 32, 48);
		animations[1][2] = BufferedImageLoader.getSprite(224, 0, 32, 48);
		animations[2][0] = BufferedImageLoader.getSprite(256, 0, 32, 48);
		animations[2][1] = BufferedImageLoader.getSprite(288, 0, 32, 48);
		animations[2][2] = BufferedImageLoader.getSprite(320, 0, 32, 48);
	}

	@Override
	public void tick() {

		input();

		lastX = x;
		lastY = y;
		if((x+velX-32>-Game.gameInstance.levelSize.width/2) && (x+velX+32<Game.gameInstance.levelSize.width/2)) {
			x+=velX;
		}
		if((y+velY-32>-Game.gameInstance.levelSize.height/2) && (y+velY+64<Game.gameInstance.levelSize.height/2)) {
			y+=velY;
		}
		
		if(atckCool>0) {
			atckCool--;
		}
		if(atckCool<40) {
			attacking  = false;
		}

		count++;
		if(count > 50) {
			count = 0;	
		} else if(count > 40) {
			as = 1;
		} else if(count > 30) {
			as = 2;
		} else if(count > 20) {
			as = 1;
		} else if(count > 10) {
			as = 0;
		}
	}

	@Override
	public void render(Graphics g, double p) {
		g.setColor(Color.WHITE);
		g.drawImage(animations[d][as], (int) ((x - lastX) * p + lastX - w / 2), (int) ((y - lastY) * p + lastY - h / 2),
				w, h, null);
		if(attacking) {
			g.drawImage(smoke, x-32, y-32, 64, 64, null);
		}
	}

	public void input() {
		// North/South
		if (KeyInput.get(0) == Key.DOWN) {
			velY = -3;
		} else if (KeyInput.get(2) == Key.DOWN) {
			velY = 3;
		} else {
			velY = 0;
			attacking = true;
		}

		// East/West
		if (KeyInput.get(1) == Key.DOWN) {
			velX = -3;
			d = 2;
		} else if (KeyInput.get(3) == Key.DOWN) {
			velX = 3;
			d = 1;
		} else {
			velX = 0;
			d = 0;
		}
		
		if (KeyInput.get(4) == Key.DOWN && atckCool <= 0) {
			attack();
		}
	}
	
	public void attack() {
		atckCool = 60;
		attacking = true;
		Ellipse2D reach = new Ellipse2D.Double(x-range/2, y-range/2, range, range);
		for(GameObject go : Game.gameInstance.handler.getByID(ID.ENEMY)){
			if(reach.intersects(go.getBounds())) {
				if(go.getCX()>x) { //Enemy Right of Player
					go.push(false, 15);
				} else { //Enemy Left of Player
					go.push(false, -15);
				}
				if(go.getCY()>y) { //Enemy Below Player
					go.push(true, 15);
				} else { //Enemy Above Player
					go.push(true, -15);
				}
			}
		}
	}
}

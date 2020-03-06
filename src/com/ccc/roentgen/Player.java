package com.ccc.roentgen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

	double lastX, lastY;
	int as = 0;
	int d = 0;
	int count = 0;

	BufferedImage[][] animations = new BufferedImage[3][3];
	BufferedImage smoke = BufferedImageLoader.loadImage("smoke.png");
	
	boolean attacking = false;

	int velX = 0;
	int velY = 0;

	public Player(int x, int y) {
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
		g.drawString("x: " + x + " y: " + y, x-40, y-40);
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
		
		if (KeyInput.get(4) == Key.DOWN) {
			attacking = true;
		} else {
			attacking = false;
		}
	}
}

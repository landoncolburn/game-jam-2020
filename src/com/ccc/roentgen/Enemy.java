package com.ccc.roentgen;

import java.awt.Graphics;

public class Enemy extends GameObject {
	
	private int velX = 2;
	private int velY = 2;
	
	private int count = 0;

	public Enemy(int x, int y) {
		super(x, y, 32, 48, true, ID.ENEMY);
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		count++;
		if(count>200) {
			velX *= -1;
			velY *= -1;
			count = 0;
		}
	}

	@Override
	public void render(Graphics g, double p) {
		g.drawRect(x, y, w, h);
	}

}

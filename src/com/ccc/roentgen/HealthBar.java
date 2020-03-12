package com.ccc.roentgen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class HealthBar extends GameObject {
	
	private int hp = 100;
	private BufferedImage health,bar;

	public HealthBar(int x) {
		super(x/5, 10, 512, 48, false, ID.GUI);
		health = BufferedImageLoader.loadImage("health_bar_inside.png");
		bar = BufferedImageLoader.loadImage("health_bar.png");
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
	
	public void removeHP(int i) {
		if(hp>0) {
			hp -= i;
		}
//		if(hp<=0) {
//			death();
//		}
	}
	
	public void addHP(int i) {
		if(hp+i<100) {
			hp += i;
		} else {
			hp = 100;
		}
	}

	@Override
	public void render(Graphics g, double p) {
		if (hp>0) {
			g.drawImage(health, x + 2, y, (int) (w * hp / 100.0) - 2, h, null);
		}
		g.drawImage(bar, x, y, w, h, null);

	}
	
	public void death() {
		Game.gameInstance.stop();
	}

}

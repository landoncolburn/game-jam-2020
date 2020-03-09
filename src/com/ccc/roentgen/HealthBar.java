package com.ccc.roentgen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class HealthBar extends GameObject {
	
	private double percent = 1;
	private BufferedImage health,bar;

	public HealthBar(int x) {
		super(x/5, 10, 512, 32, false, ID.GUI);
		health = BufferedImageLoader.loadImage("health_bar_inside.png");
		bar = BufferedImageLoader.loadImage("health_bar.png");
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
	
	public void setPer(double p) {
		percent = p;
	}

	@Override
	public void render(Graphics g, double p) {
		g.drawImage(health, x, y, (int)(w*percent), h, null);
		g.drawImage(bar, x, y, w, h, null);

	}

}

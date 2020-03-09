package com.ccc.roentgen;

import java.awt.Color;
import java.awt.Graphics;

public class HealthBar extends GameObject {
	
	private double percent = 1;

	public HealthBar(int x) {
		super(x/5, 10, 3*(x/5), 40, false, ID.GUI);
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
		g.setColor(Color.RED);
		g.fillRect(x, y, (int)(w*percent), h);
		g.setColor(Color.RED);
		g.drawRect(x, y, w, h);

	}

}

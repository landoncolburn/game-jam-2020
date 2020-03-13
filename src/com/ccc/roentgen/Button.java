package com.ccc.roentgen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Rectangle;

public class Button extends GameObject {
	
	private BufferedImage[] sprite = new BufferedImage[2];
	private BufferedImage logo;
	private int index = 0;

	public Button(int x, int y) {
		super(x-80, y-32, 160, 64, false, ID.GUI);
		sprite[0] = BufferedImageLoader.loadImage("button.png");
		sprite[1] = BufferedImageLoader.loadImage("button_pushed.png");
		logo = BufferedImageLoader.loadImage("logo.png");
	}

	@Override
	public void tick() {
		Rectangle r  = getBounds();
		Point p = Game.gameInstance.mi.getPoint();
		if(p == null || r == null) {
			return;
		}
		Rectangle r2 = new Rectangle(r.x-(int)Game.gameInstance.camera.getX(), r.y-(int)Game.gameInstance.camera.getY(), r.height*2, r.width*2);
		if(r2.contains(p)) {
			index = 1;
			Game.gameInstance.handler.removeObject(this);
			Game.gameInstance.createWorld();
		} else {
			index = 0;
		}
	}

	@Override
	public void render(Graphics g, double p) {
		g.drawImage(sprite[index], x, y, w, h, null);
		g.drawImage(logo, x+80-171, y-200, null);
	}

}

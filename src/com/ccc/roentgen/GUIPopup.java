package com.ccc.roentgen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GUIPopup extends GameObject {
	
	private PopupType type;
	private int counter = 0;
	private int expiryCount;
	private BufferedImage image;

	public GUIPopup(int x, int y, PopupType t, int ex) {
		super(x, y, 0, 0, true, ID.GUI);
		this.type = t;
		this.expiryCount = ex;
		setImage();
	}

	private void setImage() {
		switch(type) {
			case NEXT:
				image = BufferedImageLoader.loadImage("next_wave.png");
				break;
			case DEAD:
				image = BufferedImageLoader.loadImage("death.png");
				break;
			case WIN:
				image = BufferedImageLoader.loadImage("winner.png");
				break;
		}
	}

	@Override
	public void tick() {
		counter++;
		if(counter>expiryCount) {
			Game.gameInstance.gui.removeObject(this);
		}
	}

	@Override
	public void render(Graphics g, double p) {
		g.drawImage(image, x, y, null);
	}

}

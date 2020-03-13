package com.ccc.roentgen;

import java.awt.Graphics;

public class GUIPopup extends GameObject {
	
	private String text;
	private int counter = 0;
	private int expiryCount;

	public GUIPopup(String text, int ex) {
		super(0, 0, 0, 0, true, ID.GUI);
		this.text = text;
		this.expiryCount = ex;
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
		g.setFont(Game.gameInstance.pixelFont);
		g.drawString(text, (Game.gameInstance.size.width - g.getFontMetrics().stringWidth(text))/2, Game.gameInstance.levelSize.height/4);
	}

}

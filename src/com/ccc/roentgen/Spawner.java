package com.ccc.roentgen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Spawner extends GameObject {
	
	private static final int MAX_TICKS_UNTIL_SPAWN = 2400;
	private static final int MIN_TICKS_UNTIL_SPAWN = 840;
	
	private BufferedImage image;
	
	private int ticksUntilSpawn;
	
	public Spawner(int x, int y) {
		super(x, y, 5, 5, true, ID.SPAWNER);
		image = BufferedImageLoader.loadImage("spawner.png");
		ticksUntilSpawn = (int)(Math.random() * MAX_TICKS_UNTIL_SPAWN);
	}
	
	private void updateTicksUntilSpawn() {
		ticksUntilSpawn = (int)(Math.random() * (MAX_TICKS_UNTIL_SPAWN - MIN_TICKS_UNTIL_SPAWN) + MIN_TICKS_UNTIL_SPAWN);
	}

	@Override
	public void tick() {
		if(WaveHandler.enemies.isEmpty()) { return; }
		if(ticksUntilSpawn > 0) {
			ticksUntilSpawn--;
		} 
		else {
			Game.gameInstance.handler.addObject(new Enemy(this.x, this.y, WaveHandler.enemies.poll()));
			updateTicksUntilSpawn();
		}
		
	}

	@Override
	public void render(Graphics g, double p) {
		if(image == null) {
			g.setColor(Color.BLACK);
			g.fillRect(x - 3, y - 3, 6, 6);
		} else {
			g.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
		}
	}
}

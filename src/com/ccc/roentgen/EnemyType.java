package com.ccc.roentgen;

import java.awt.image.BufferedImage;

public enum EnemyType {
	
	ZOMBIE{
		public int row = 0;
		public Drop[] getDrops() {
			Drop[] d = {
				new Drop(0.80, ItemType.COIN),
				new Drop(0.50, ItemType.HEART),
				new Drop(0.20, ItemType.BAT),
				new Drop(0.05, ItemType.AXE)
			};
			return d;
		}
		
		public BufferedImage[] getFrames() {
			BufferedImage[] d = {
				BufferedImageLoader.getSprite(1, 0, 48*row, 32, 48),
				BufferedImageLoader.getSprite(1, 32, 48*row, 32, 48),
				BufferedImageLoader.getSprite(1, 64, 48*row, 32, 48),
				BufferedImageLoader.getSprite(1, 96, 48*row, 32, 48)
			};
			return d;
		}

		public int getHP() {
			return 5;
		}

		public int getSpeed() {
			return 1;
		}
	},
	SKELETON{
		public int row = 1;
		public Drop[] getDrops() {
			Drop[] d = {
				new Drop(0.80, ItemType.COIN),
				new Drop(0.10, ItemType.HEART)
			};
			return d;
		}
		
		public BufferedImage[] getFrames() {
			BufferedImage[] d = {
				BufferedImageLoader.getSprite(1, 0, 48*row, 32, 48),
				BufferedImageLoader.getSprite(1, 32, 48*row, 32, 48),
				BufferedImageLoader.getSprite(1, 64, 48*row, 32, 48),
				BufferedImageLoader.getSprite(1, 96, 48*row, 32, 48)
			};
			return d;
		}

		public int getHP() {
			return 3;
		}

		public int getSpeed() {
			return 2;
		}
	},
	NECROMANCER{
		public int row = 2;
		public Drop[] getDrops() {
			Drop[] d = {
				new Drop(1.00, ItemType.COIN),
				new Drop(0.80, ItemType.COIN),
				new Drop(0.60, ItemType.COIN),
				new Drop(1.00, ItemType.HEART),
				new Drop(0.20, ItemType.AXE)
			};
			return d;
		}
		
		public BufferedImage[] getFrames() {
			BufferedImage[] d = {
				BufferedImageLoader.getSprite(1, 0, 48*row, 32, 48),
				BufferedImageLoader.getSprite(1, 32, 48*row, 32, 48),
				BufferedImageLoader.getSprite(1, 64, 48*row, 32, 48),
				BufferedImageLoader.getSprite(1, 96, 48*row, 32, 48)
			};
			return d;
		}

		public int getHP() {
			return 10;
		}

		public int getSpeed() {
			return 1;
		}
	};
	
	public abstract Drop[] getDrops();
	public abstract BufferedImage[] getFrames();
	public abstract int getHP();
	public abstract int getSpeed();
}

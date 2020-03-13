package com.ccc.roentgen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Inventory extends GameObject {
	
	private boolean canOpen;
	private boolean open;
	private int openingProgress;
	private Weapon equipped;
	private int selectedIndex;
	private Item[][] inv = new Item[3][5];
	private int coins = 0;
	private BufferedImage deselected, selected, coin;
	private boolean isTabUp = true;
	
	public Inventory() {
		super(580, 10, 500, 48, false, ID.GUI);
		open = false;
		canOpen = true;
		selectedIndex = 0;
		selected = BufferedImageLoader.loadImage("inventory_selected.png");
		deselected = BufferedImageLoader.loadImage("inventory_deselected.png");
		coin = BufferedImageLoader.getSprite(2, 64, 0, 32, 32);
	}
	
	public Weapon getWeapon() {
		return equipped;
	}
	
	public void select(int selectedIndex) {
		this.selectedIndex = selectedIndex;
		if(inv[0][selectedIndex] instanceof Weapon) {
			equipped = (Weapon)inv[0][selectedIndex];
		} else {
			equipped = null;
		}
	}
	
	public void add(Item i) {
		outer: for(int y = 0; y < inv.length; y++) {
			for(int x = 0; x < inv[y].length; x++) {
				if(inv[y][x] == null) {
					inv[y][x] = i;
					if(x == selectedIndex && y == 0) {
						select(x);
					}
					break outer;
				}
			}
		}
	}
	
	public Item getAt(int y, int x) {
		return inv[y][x];
	}
	
	public void read() {
		for(Item[] a : inv) {
			for(Item i : a) {
				System.out.println(i.toString());
			}
		}
	}
	
	public void remove(int y, int x) {
		inv[y][x] = null;
	}
	
	public Point searchFor(ItemType i) {
		for(int y = 0; y < inv.length; y++) {
			for(int x = 0; x < inv[y].length; x++) {
				if(i == inv[y][x].getType()) {
					return new Point(x, y);
				}
			}
		}
		return null;
	}
	
	public int getSize() {
		return inv.length * inv[0].length;
	}
	
	public int countType(ItemType i) {
		int count = 0;
		for (Item[] a: inv) {
			for(Item item : a) {
				if (item.getType() == i) {
					count++;
				}
			}
		}
		return count;
	}
	
	public void changeCoins(int i) {
		coins += i;
	}
	
	public int getCoins() {
		return coins;
	}

	@Override
	public void tick() {
		//check if 1 through 5 are pressed
		for(int i = 0; i < 5; i++) {
			if(KeyInput.get(6 + i) == Key.DOWN) {
				select(i);
			}
		}
		
		//Check if tab is pressed
		if(KeyInput.get(11) == Key.DOWN) {
			if(isTabUp) {
				isTabUp = false;
				select((selectedIndex+1)%5);
			}
		}
		
		if(KeyInput.get(11) == Key.UP) {
			isTabUp = true;
		}
		
		//check if e is pressed
		if(KeyInput.get(12) == Key.DOWN) {
			if(canOpen) {
				canOpen = false;
				open = !open;
			}
		} else {
			canOpen = true;
		}
		if(openingProgress > 0 && !open) {
			openingProgress--;
		} else if(openingProgress < 56 && open) {
			openingProgress++;
		}
	}

	@Override
	public void render(Graphics g, double p) {
		if(openingProgress > 0) {
			for(int j = 2; j > 0; j--) {
				for(int i = 0; i < 5; i++) {
					g.drawImage(deselected, x + (58 * i), y + (openingProgress * j), 48, 48, null);
					if(inv[j][i] != null) {
						g.drawImage(inv[j][i].getSprite(), x + 8 + (58 * i), y + 8 + (openingProgress * j), null);
					}
				}
			}
		}
		
		for(int i = 0; i < 5; i++) {
			if(i == selectedIndex) {
				g.drawImage(selected, x + (58 * i), y, 48, 48, null);
			} else {
				g.drawImage(deselected, x + (58 * i), y, 48, 48, null);
			}
			if(inv[0][i] != null) {
				g.drawImage(inv[0][i].getSprite(), x + 8 + (58 * i), y + 8, null);
			}
		}
		
		
		
		g.drawImage(deselected, 922, y, 48, 48, null);
		g.drawImage(coin, 930, y + 8, null);
		g.setFont(Game.gameInstance.pixelFont);
		g.setColor(Color.white);
		if(coins > 0) {
			g.drawString(Integer.toString(coins), 947, y + 35);
		} else {
			g.drawString("0", 947, y + 35);
		}
	}
}

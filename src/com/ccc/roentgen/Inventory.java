package com.ccc.roentgen;

import java.util.ArrayList;

public class Inventory {

	private ArrayList<Item> inv = new ArrayList<Item>();
	private int coins = 0;
	
	public Inventory() {
	};
	
	public void add(Item i) {
		inv.add(i);
	}
	
	public Item getAt(int i) {
		return inv.get(i);
	}
	
	public void read() {
		inv.forEach(System.out::println);
	}
	
	public void remove(int index) {
		inv.remove(index);
	}
	
	public int searchFor(ItemType i) {
		for (int j = 0; j < inv.size(); j++) {
			if (inv.get(j).getType() == i) {
				return j;
			}
		}
		return -1;
	}
	
	public int getSize() {
		return inv.size();
	}
	
	public int countType(ItemType i) {
		int count = 0;
		for (Item item: inv) {
			if (item.getType() == i) {
				count++;
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
}

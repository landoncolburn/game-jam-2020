package com.ccc.roentgen;

import java.util.ArrayList;

public class Inventory {

	ArrayList<Item> list = new ArrayList<Item>();
	
	public Inventory() {//Inventory constructor
		
		
	};
	
	public void add(Item i) {
		list.add(i);
	}
	
	public void remove(int index) {
		list.remove(index);
	}
	
	public int search(ItemID i) {
		for (int j = 0; j < list.size(); j++) {
			if (list.get(j).getID() == i) {
				return j;
			}
		}
		return -1;
	}
	
	public int count(ItemID i) {
		int count = 0;
		for (Item item: list) {
			if (item.getID() == i) {
				count++;
			}
		}
		return count;
	}
}

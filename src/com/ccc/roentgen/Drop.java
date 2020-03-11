package com.ccc.roentgen;

public class Drop {
	
	private double probability;
	private ItemType type;
	
	public Drop(double probability, ItemType type) {
		super();
		this.probability = probability;
		this.type = type;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	public ItemType getType() {
		return type;
	}
	public void setType(ItemType type) {
		this.type = type;
	}
	
}

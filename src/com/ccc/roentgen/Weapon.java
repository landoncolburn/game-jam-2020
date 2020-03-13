package com.ccc.roentgen;

public class Weapon extends Item {
	
	private int damage;
	private int range;
	private int cooldownTicks;

	public Weapon(ItemType type) {
		super(0, 0, type);
		switch(type) {
		case BAT:
			damage = 1;
			range = 150;
			cooldownTicks = 60;
			break;
		default:
			damage = 1;
			range = 100;
			cooldownTicks = 60;
			break;
		}
	}

	public int getDamage() {
		return damage;
	}

	public int getRange() {
		return range;
	}

	public int getCooldownTicks() {
		return cooldownTicks;
	}

}

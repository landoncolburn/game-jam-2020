package com.ccc.roentgen;

public enum ItemType {
	
	HEART(true, false),
	TURRET(false, false), 
	BAT(false, true), 
	COIN(true, false);
	
	public boolean useOnPickup, isWeapon;
	
	private ItemType(boolean useOnPickup, boolean isWeapon) {
		this.useOnPickup = useOnPickup;
		this.isWeapon = isWeapon;
	}
}

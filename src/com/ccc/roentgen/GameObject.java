package com.ccc.roentgen;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	protected int x, y, w, h;
	protected ID id;
	public boolean solid;

	public int pushX = 0;
	public int pushY = 0;
	public boolean pushingX = true;
	public boolean pushingY = true;

	public GameObject(int x, int y, int w, int h, boolean s, ID i) {//GameObject constructor
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.solid = s;
		this.id = i;
	}

	public abstract void tick();

	public abstract void render(Graphics g, double p);

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}
	
	public int getCX() {
		return x+w/2;
	}
	
	public int getCY() {
		return y+h/2;
	}

	public void setH(int h) {
		this.h = h;
	}

	public ID getID() {
		return id;
	}

	public void setID(ID id) {
		this.id = id;
	}
	
	public void damage() {
		//Purposely empty method stub
	}
	
	public void push(boolean vert, int push) {
		if(vert) {
			pushY = push;
			pushingY = true;
		} else {
			pushX = push;
			pushingX = true;
		}
	}

	public Rectangle getBounds() {//returns the bounds of a rectangle object
		return new Rectangle(getX(), getY(), getW(), getH());
	}
}

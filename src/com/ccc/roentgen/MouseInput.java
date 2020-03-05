package com.ccc.roentgen;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

	private Point mousePos;
	private Point mouseDown;

	public MouseInput() {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = e.getPoint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mousePos = e.getPoint();
	}

	public Point getPoint() {
		return mousePos;
	}
	
	public Point getDownPoint() {
		return mouseDown;
	}

}

package com.ccc.roentgen;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

	private Handler handler;
	private Point mousePos;

	public MouseInput(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mx = (e.getX());
		int my = (e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mousePos = e.getPoint();
	}

	public Point getPoint() {
		return mousePos;
	}

}

package com.ccc.roentgen;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseMotionInput extends MouseMotionAdapter {

	private Handler handler;
	private Point mousePos = null;

	public MouseMotionInput(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePos = e.getPoint();
	}

	public int getX() {
		return mousePos.x;
	}

	public int getY() {
		return mousePos.y;
	}

	public Point getPoint() {
		return mousePos;
	}

}

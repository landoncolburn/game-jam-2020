package com.ccc.roentgen;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	public static Key keys[] = { Key.UP, Key.UP, Key.UP, Key.UP, Key.UP };

	public KeyInput() {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W)
			set(0, Key.DOWN);
		if (key == KeyEvent.VK_A)
			set(1, Key.DOWN);
		if (key == KeyEvent.VK_S)
			set(2, Key.DOWN);
		if (key == KeyEvent.VK_D)
			set(3, Key.DOWN);
		if (key == KeyEvent.VK_SPACE)
			set(4, Key.DOWN);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W)
			set(0, Key.UP);
		if (key == KeyEvent.VK_A)
			set(1, Key.UP);
		if (key == KeyEvent.VK_S)
			set(2, Key.UP);
		if (key == KeyEvent.VK_D)
			set(3, Key.UP);
		if (key == KeyEvent.VK_SPACE)
			set(4, Key.UP);
	}

	public static void set(int i, Key k) {
		keys[i] = k;
	}

	public static Key get(int i) {
		return keys[i];
	}
}

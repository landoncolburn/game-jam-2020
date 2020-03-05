package com.ccc.roentgen;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Window {
	public Window(String title, Dimension size, Game game) {
		JFrame f = new JFrame(title);
		f.setPreferredSize(size);
		f.setMinimumSize(size);
		f.setMaximumSize(size);

		f.add(game);
		f.setResizable(false);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}

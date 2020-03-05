package com.ccc.roentgen;

import java.awt.Graphics;
import java.util.ArrayList;

public class GUIHandler {

	private ArrayList<GameObject> elements;

	public GUIHandler() {
		elements = new ArrayList<GameObject>();
	}

	public GameObject getObject(int i) {
		return elements.get(i);
	}

	public void addObject(GameObject object) {
		elements.add(object);
	}

	public void removeObject(GameObject object) {
		elements.remove(object);
	}

	public void render(Graphics g, double p) {
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).render(g, p);
		}
	}

	public void tick() {
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).tick();
		}
	}

}

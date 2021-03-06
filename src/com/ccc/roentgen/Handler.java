package com.ccc.roentgen;

import java.awt.Graphics;
import java.util.ArrayList;

public class Handler {

	public ArrayList<GameObject> gameObjects;

	public Handler() {//Handler constructor
		gameObjects = new ArrayList<GameObject>();
	}

	public GameObject getObject(int i) {
		return gameObjects.get(i);
	}

	public ArrayList<GameObject> getByID(ID i) {
		ArrayList<GameObject> go = new ArrayList<GameObject>();
		for (GameObject g : gameObjects) {
			if (g.getID() == i) {
				go.add(g);
			}
		}
		return go;
	}

	public void addObject(GameObject object) {
		gameObjects.add(object);
	}

	public void removeObject(GameObject object) {
		gameObjects.remove(object);
	}
	
	public void killAll() {
		for(GameObject go : gameObjects) {
			if(go.getID() == ID.ENEMY) {
				go.hp = 0;
			}
		}
	}

	public void render(Graphics g, double p) {
		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).render(g, p);
		}
	}

	public void tick() {
		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).tick();
		}
	}

}

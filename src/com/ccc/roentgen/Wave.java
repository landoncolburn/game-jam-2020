package com.ccc.roentgen;

import java.util.ArrayList;

public class Wave {
	
	private ArrayList<EnemySpawn> spawns;
	private int count;

	public Wave(ArrayList<EnemySpawn> s, int c) {
		this.spawns = s;
		this.count = c;
	}

	public ArrayList<EnemyType> getEnemies(){
		ArrayList<EnemyType> enemies = new ArrayList<EnemyType>();
		for(int i = 0; i<count; i++) {
			for(EnemySpawn s : spawns) {
				if(Math.random()<s.getSpawnRate()) {
					enemies.add(s.getEnemy());
				}
			}
		}
		return enemies;
	}
}

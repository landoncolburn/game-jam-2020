package com.ccc.roentgen;

public class EnemySpawn {
	
	private EnemyType enemy;
	private double spawnRate;

	public EnemyType getEnemy() {
		return enemy;
	}

	public void setEnemy(EnemyType enemy) {
		this.enemy = enemy;
	}

	public double getSpawnRate() {
		return spawnRate;
	}

	public void setSpawnRate(double spawnRate) {
		this.spawnRate = spawnRate;
	}

	public EnemySpawn(double p, EnemyType e) {
		this.spawnRate = p;
		this.enemy = e;
	}

}

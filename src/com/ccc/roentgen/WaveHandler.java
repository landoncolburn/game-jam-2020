package com.ccc.roentgen;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class WaveHandler {
	
	private static final int MAX_DISTANCE_TO_BORDER = 250;
	
	private int numSpawners = 4;
	
	private Spawner[] spawners;
	
	private int waveNumber;
	
	private LinkedList<EnemyType> enemysInRound;
	
	private int ticksUntilNextWave;
	
	private boolean waveOver;
	
	public WaveHandler() {
		int x, y;
		waveOver = false;
		this.waveNumber = 0;
		spawners = new Spawner[numSpawners];
		enemysInRound = new LinkedList<EnemyType>();
		
		for(int i = 0; i < numSpawners; i++) {
			do {
				x = (int)(Math.random() * Game.gameInstance.levelSize.width);
				y = (int)(Math.random() * Game.gameInstance.levelSize.height);
			} while(new Rectangle(MAX_DISTANCE_TO_BORDER, MAX_DISTANCE_TO_BORDER, Game.gameInstance.levelSize.width - MAX_DISTANCE_TO_BORDER, Game.gameInstance.levelSize.height - MAX_DISTANCE_TO_BORDER).contains(new Point(x, y)));
			
			spawners[i] = new Spawner(x - Game.gameInstance.levelSize.width / 2, y - Game.gameInstance.levelSize.height / 2);
			Game.gameInstance.handler.addObject(spawners[i]);
		}
	}
	
	public void tick() {
		if(!waveOver) {
			if(isWaveOver()) {
				ticksUntilNextWave = 600;
				waveOver = true;
			}
		} else {
			if(ticksUntilNextWave > 0) {
				ticksUntilNextWave--;
			} else {
				nextWave();
				waveOver = false;
			}
		}
	}
	
	private boolean isWaveOver() {
		for(Spawner s : spawners) {
			if(!s.isEmpty()) {
				return false;
			}
		}
		for(GameObject o : Game.gameInstance.handler.gameObjects) {
			if(o instanceof Enemy) {
				return false;
			}
		}
		return true;
	}
	
	public void nextWave() {
		enemysInRound = new LinkedList<EnemyType>();
		Scanner input;
		try {
			input = new Scanner(new File("src/waves.wavedata"));
			for(int i = 0; i < this.waveNumber; i++) {
				if(input.hasNextLine()) {
					input.nextLine();
				} else { return; }
			}
			
			String type;
			int amount;
			while(input.hasNext()) {
				type = input.next();
				if(type.equals(";")) { break; }
				amount = input.nextInt();
				for(EnemyType t : EnemyType.values()) {
					if(t.name().equals(type)) {
						for(int i = 0; i < amount; i++) {
							this.enemysInRound.add(t);
						}
						break;
					}
				}
			}
			input.close();;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("waves.wavedata not found, wave will not start");
		}
		
		while(enemysInRound.size() > 0) {
			spawners[(int)(Math.random() * numSpawners)].add(enemysInRound.remove((int)(Math.random() * enemysInRound.size())));
		}
		
		waveNumber++;
		System.out.printf("Starting wave %d%n", waveNumber);
	}
	
	public void kill() {
		for(Spawner s : spawners) {
			Game.gameInstance.handler.removeObject(s);
		}
	}
	
	public int getWaveNumber() {
		return this.waveNumber;
	}
	
	public void setWaveNumber(int newWaveNumber) {
		this.waveNumber = newWaveNumber;
	}
	
	public void setNumSpawners(int newNumSpawners) {
		this.numSpawners = newNumSpawners;
	}
	
}

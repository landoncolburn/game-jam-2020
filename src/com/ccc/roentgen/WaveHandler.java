package com.ccc.roentgen;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WaveHandler {
	
	private static final int MAX_DISTANCE_TO_BORDER = 250;
	
	private int numSpawners = 4;
	
	private Spawner[] spawners;
	private BufferedReader input;
	
	public static int waveNumber;
	
	private Queue<Wave> waves = new LinkedList<Wave>();
	public static Queue<EnemyType> enemies = new LinkedList<EnemyType>();
	
	private int ticksUntilNextWave;
	
	private boolean waveOver = false;
	private String suffix;
	
	private Function<String, Wave> parseWave = (s) -> {
		int count = Integer.parseInt(s.substring(s.indexOf("[")+1, s.indexOf("]")));
		String[] spawns = s.substring(s.indexOf("]")+1).split(",");
		EnemyType[] types = EnemyType.values();
		ArrayList<EnemySpawn> spawnsObj = new ArrayList<EnemySpawn>(); 
		for(String t : spawns) {
			for(EnemyType y : types) {
				if(t.contains(y.toString())) {
					double rate = Double.parseDouble(t.substring(t.indexOf("(")+1, t.indexOf(")")));
					spawnsObj.add(new EnemySpawn(rate, y));
				}
			}
		} 
		return new Wave(spawnsObj, count);
	};
	
	public WaveHandler() {
		int x, y;
		waveNumber = 1;
		spawners = new Spawner[numSpawners];
		
		for(int i = 0; i < numSpawners; i++) {
			do {
				x = (int)(Math.random() * Game.gameInstance.levelSize.width);
				y = (int)(Math.random() * Game.gameInstance.levelSize.height);
			} while(new Rectangle(MAX_DISTANCE_TO_BORDER, MAX_DISTANCE_TO_BORDER, Game.gameInstance.levelSize.width - (2 * MAX_DISTANCE_TO_BORDER), Game.gameInstance.levelSize.height - (2 * MAX_DISTANCE_TO_BORDER))
					.contains(new Point(x, y)));
			
			spawners[i] = new Spawner(x - Game.gameInstance.levelSize.width / 2, y - Game.gameInstance.levelSize.height / 2);
			Game.gameInstance.handler.addObject(spawners[i]);
		}
		
		try {
			InputStream is = getClass().getResourceAsStream("/waves.wavedata");
			input = new BufferedReader(new InputStreamReader(is));
			waves.addAll(input.lines().map(parseWave).collect(Collectors.toList()));
		} catch (Exception e) {
				e.printStackTrace();
				System.err.println("waves.wavedata not found, wave will not start");
		}
		nextWave();
	}
	
	public void tick() {
		if(!waveOver) {
			if(isWaveOver()) {
				if(waves.size() == 0) {
					Game.gameInstance.gui.addObject(new GUIPopup("You survived.", 360));
					Game.gameInstance.eBrake = true;
					return;
				}
				ticksUntilNextWave = 600;
				waveOver = true;
				if(waveNumber%10==2 && waveNumber%100!=12) {
					suffix = "nd";
				} else if(waveNumber%10==1 && waveNumber%100!=11) {
					suffix = "st";
				} else if(waveNumber%10==3 && waveNumber%100!=13) {
					suffix = "rd";
				} else {
					suffix = "th";
				}
				Game.gameInstance.gui.addObject(new GUIPopup("The " + waveNumber + suffix + " wave is coming, get ready", 360));
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
		if(!enemies.isEmpty()) {
			return false;
		}
		for(GameObject o : Game.gameInstance.handler.gameObjects) {
			if(o instanceof Enemy) {
				return false;
			}
		}
		return true;
	}
	
	public void nextWave() {
		if(waves.size() == 0) {
			return;
		}
		enemies.clear();
		enemies.addAll(waves.poll().getEnemies());
		
		waveNumber++;
		waveOver = false;
	}
	
	public void kill() {
		for(Spawner s : spawners) {
			Game.gameInstance.handler.removeObject(s);
		}
	}
	
	public int getWaveNumber() {
		return waveNumber;
	}
	
	public void setNumSpawners(int newNumSpawners) {
		this.numSpawners = newNumSpawners;
	}
	
}

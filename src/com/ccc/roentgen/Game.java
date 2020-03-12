package com.ccc.roentgen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.Queue;

import com.ccc.roentgen.audio.AudioManager;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 42l;
	private static double MS_PER_UPDATE = 120.0;

	public static Game gameInstance;
	public Handler handler;
	public GUIHandler gui;

	private Thread thread;
	private boolean running = false;
	private boolean paused = false;

	public Dimension size;
	public final Dimension levelSize;
	private int numOfZoms = 6;
	private Queue<Enemy> enemyQueue = new LinkedList<Enemy>();
	private int spawnCount;
	
	public MouseMotionInput mmi;
	public MouseInput mi;
	public KeyInput ki;
	public Camera camera;
	
	public HealthBar healthBar;
	public Player player;
	public Inventory inventory;
	public boolean beingDamaged = false;

	private TexturePaint paint = new TexturePaint(BufferedImageLoader.loadImage("backgroundtile.png"),
			new Rectangle(0, 0, 64, 64));

	public Game() {//Game Constructor
		
		AudioManager.initializeAudioManager();
		
		size = new Dimension(1000, 600);
		levelSize = new Dimension(1500, 1000);
		new Window("Roentgen", size, this);

		handler = new Handler();
		gui = new GUIHandler();
		gameInstance = this;

		mmi = new MouseMotionInput();
		mi = new MouseInput();
		ki = new KeyInput();

		camera = new Camera(0, 0);

		this.addMouseListener(mi);
		this.addKeyListener(ki);
		this.addMouseMotionListener(mmi);

		BufferedImageLoader.loadSpriteSheet(0, "spritesheet.png");
		BufferedImageLoader.loadSpriteSheet(1, "enemy.png");
		BufferedImageLoader.loadSpriteSheet(2, "itemsheet.png");
		
		start();

		createWorld();
	}

	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		System.out.println("Player has died.\nGame ending...");
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.requestFocus();
		final double GAME_HERTZ = 120.0;
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		final int MAX_UPDATES_BEFORE_RENDER = 5;
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime = System.nanoTime();

		final double TARGET_FPS = 120;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		while (running) {
			double now = System.nanoTime();
			int updateCount = 0;

			if (!paused) {
				while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
					tick();
					lastUpdateTime += TIME_BETWEEN_UPDATES;
					updateCount++;
				}

				if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
					lastUpdateTime = now - TIME_BETWEEN_UPDATES;
				}

				// Render. To do so, we need to calculate interpolation for a smooth render.
				float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
				render(interpolation);
				lastRenderTime = now;

				// Yield until it has been at least the target time between renders. This saves
				// the CPU from hogging.
				while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS
						&& now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
					Thread.yield();
					try {
						Thread.sleep(1);
					} catch (Exception e) {
					}
					now = System.nanoTime();
				}
			}
		}
	}

	public void render(double lag) {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		//////////////////////////////////
		/////// ----DRAW IN HERE----///////
		//////////////////////////////////

		g.setColor(new Color(92, 39, 39));
		g.fillRect(0, 0, size.width, size.height);

		g2d.translate(-camera.getX(), -camera.getY());

		g2d.setPaint(paint);
		g.fillRect(-levelSize.width/2, -levelSize.height/2, levelSize.width, levelSize.height);
		handler.render(g, lag / MS_PER_UPDATE);

		g2d.translate(camera.getX(), camera.getY());
		
		gui.render(g, lag / MS_PER_UPDATE);

		//////////////////////////////////
		g.dispose();
		bs.show();
	}

	// Runs every frame
	public void tick() {
		for (int i = 0; i < handler.gameObjects.size(); i++) {
			if (handler.gameObjects.get(i).getID() == ID.PLAYER) {
				camera.tick(handler.gameObjects.get(i));
			}
		}
		
		if(enemyQueue.size() == 0) {
			
		} else if(spawnCount<0) {
			spawnCount = (int)(Math.random()*1200)+600;
			handler.addObject(enemyQueue.poll());
		} else {
			spawnCount--;
		}

		handler.tick();
		gui.tick();
	}

	// Runs before first tick method
	public void createWorld() {
		handler.addObject(new Base());
		player = new Player(50, 50);
		inventory = new Inventory();
		handler.addObject(player);
		for(int i = 0; i < numOfZoms; i++) {
			enemyQueue.add(new Enemy((int)(Math.random()*2000)-1000, (int)(Math.random()*2000)-1000));
		}
		healthBar = new HealthBar(size.width);
		gui.addObject(healthBar);
	}

	public static void main(String[] args) {
		new Game();
	}
}

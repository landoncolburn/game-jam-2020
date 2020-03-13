package com.ccc.roentgen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferStrategy;
import java.io.File;

import com.ccc.roentgen.audio.AudioManager;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 42l;
	private static double MS_PER_UPDATE = 120.0;

	public static Game gameInstance;
	private Thread thread;
	
	public Handler handler;
	public GUIHandler gui;
	public WaveHandler waveHandler;

	private boolean running = false;
	private boolean paused = false;
	public boolean beingDamaged = false;
	public boolean eBrake = false;
	private boolean playerExist = false;

	public Dimension size;
	public final Dimension levelSize;
	
	public MouseMotionInput mmi;
	public MouseInput mi;
	public KeyInput ki;
	
	public Camera camera;
	public HealthBar healthBar;
	public Player player;
	public Inventory inventory;
	
	public Font pixelFont;
	private TexturePaint paint = new TexturePaint(BufferedImageLoader.loadImage("backgroundtile.png"),
			new Rectangle(0, 0, 64, 64));

	public Game() {
		
		AudioManager.initializeAudioManager();
		
		gameInstance = this;
		size = new Dimension(1000, 600);
		levelSize = new Dimension(1500, 1000);
		handler = new Handler();
		gui = new GUIHandler();
		mmi = new MouseMotionInput();
		mi = new MouseInput();
		ki = new KeyInput();
		camera = new Camera(0, 0);
		
		new Window("Roentgen", size, this);

		this.addMouseListener(mi);
		this.addKeyListener(ki);
		this.addMouseMotionListener(mmi);
		
		try {
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/pixelsix00.ttf")).deriveFont(16f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		BufferedImageLoader.loadSpriteSheet(0, "spritesheet.png");
		BufferedImageLoader.loadSpriteSheet(1, "enemy.png");
		BufferedImageLoader.loadSpriteSheet(2, "itemsheet.png");
		
		start();
		addMainMenu();
//		createWorld();
	}

	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
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
				float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
				render(interpolation);
				lastRenderTime = now;

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
		if(eBrake) {
			return;
		}
		for (int i = 0; i < handler.gameObjects.size(); i++) {
			if (handler.gameObjects.get(i).getID() == ID.PLAYER) {
				camera.tick(handler.gameObjects.get(i));
				playerExist = true;
			}
		}
		if(!playerExist) camera.tick(0, 0);
		if(waveHandler != null) waveHandler.tick();
		handler.tick();
		gui.tick();
	}

	// Runs before first tick method
	public void createWorld() {
		healthBar = new HealthBar(size.width);
		player = new Player(50, 50);
		inventory = new Inventory();
		handler.addObject(new Base());
		waveHandler = new WaveHandler();
		handler.addObject(player);
		gui.addObject(healthBar);
		gui.addObject(inventory);
	}
	
	public void addMainMenu() {
		handler.addObject(new Button(0, 0));
	}

	public static void main(String[] args) {
		new Game();
	}
}

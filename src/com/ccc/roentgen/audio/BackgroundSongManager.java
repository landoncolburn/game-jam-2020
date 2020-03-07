package com.ccc.roentgen.audio;

import java.util.ArrayList;

public class BackgroundSongManager extends Thread {
	
	private static final int UPDATES_PER_SECOND = 8;
	private static final int SECOND_IN_NANOS = 1000000000;
	
	private static final boolean LOOP_BY_DEFAULT = true;
	
	private ArrayList<String> queue;
	private BackgroundSong playing;
	
	private volatile boolean stop;
	
	protected BackgroundSongManager() {
		this.queue = new ArrayList<String>();
		this.playing = null;
		this.stop = false;
	}
	
	/**
	 * Starts thread to manage background music
	 */
	@Override
	public void run() {
		
		long currentTime = System.nanoTime();
		long lastCheckTime = currentTime;
		
		while(true) {
			if(currentTime - lastCheckTime > SECOND_IN_NANOS / UPDATES_PER_SECOND) {
				if(this.stop) {
					synchronized(this.queue) {
						this.queue = new ArrayList<String>();
					}
					this.playing.stop();
					this.playing = null;
					this.stop = false;
				}
				
				if(playing == null) {
					if(this.queue.size() > 0) {
						synchronized(queue) {
							playing = new BackgroundSong(queue.get(0));
							queue.remove(0);
						}
						if(playing.getFailed()) {
							playing = null;
						}
					}
					else {
						try {
							sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				else if(!playing.isActive()) {
					if(LOOP_BY_DEFAULT && this.queue.size() == 0) {
						playing = new BackgroundSong(playing.getTitle());
					}
					else {
						playing = null;
					}
				}
				
				lastCheckTime = System.nanoTime();
				
			}
			
			currentTime = System.nanoTime();
			
		}
	}
	
	/**
	 * Adds specified song or songs to queue for background music.
	 * Does not require file extension or path.
	 * 
	 * @param 	titles 	the titles of the songs to be added to the queue
	 */
	protected void addToQueue(String... titles) {
		synchronized(this.queue) {
			for(String title : titles) {
				this.queue.add(title);
			}
		}
	}
	
	/**
	 * Stops all background music, thread stays alive.
	 */
	protected void stopSongs() {
		this.stop = true;
	}
}

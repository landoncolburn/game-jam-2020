package com.ccc.roentgen.audio;

public class AudioManager {
	
	private static BackgroundSongManager songManager;
	
	/**
	 * Must be called before any audio is played.
	 * Initializes and starts the BackgroundSongManager and SFXManager.
	 */
	public static void initializeAudioManager() {
		songManager = new BackgroundSongManager();
		songManager.start();
	}
	
	/**
	 * Adds specified song or songs to queue for background music.
	 * Does not require file extension or path.
	 * 
	 * @param 	titles 	the titles of the songs to be added to the queue
	 */
	public static void addToSongQueue(String... titles) {
		songManager.addToQueue(titles);
	}
	
	/**
	 * Stops all background music, thread stays alive.
	 */
	public static void stopBackgroundMusic() {
		songManager.stopSongs();
	}
	
}

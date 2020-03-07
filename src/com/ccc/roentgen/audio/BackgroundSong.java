package com.ccc.roentgen.audio;

import java.io.File;
import java.io.FileNotFoundException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BackgroundSong {
	
	private boolean failed;
	
	private String title;
	
	private FloatControl gainControl;
	
	private Clip clip;
	
	public BackgroundSong(String title) {
		this.title = title;
		
		//LOCATION OF AUDIO FILE IN PROJECT
		String path = "src/audio/songs/" + this.title + ".wav";
		
		//System.out.printf("Starting playback of %s%n", title);
		
		try {
			File soundFile = new File(path);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format = ais.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			this.clip = (Clip)AudioSystem.getLine(info);
			this.clip.open(ais);
			this.gainControl = (FloatControl)(this.clip.getControl(FloatControl.Type.MASTER_GAIN));
			this.clip.start();
			this.failed = false;
		} catch(FileNotFoundException e) {
			System.err.printf("System cannot find song %s1 from path %s2%nEnding song%n", this.title, path);
			this.failed = true;
		} catch(UnsupportedAudioFileException e) {
			System.err.printf("Song %s has unsupported file format%nEnding song%n", this.title);
			this.failed = true;
		} catch(Exception e) {
			e.printStackTrace();
			this.failed = true;
		}
	}
	
	/**
	 * Retrieves song title, not formatted as filepath.
	 * 
	 * @return	song title
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Sets the volume for the clip playback.
	 * 
	 * @param 	volume 	Negative values will give quieter than default, positive will give louder than default.
	 * @return 			true if clip is currently rendering audio, false otherwise.
	 * @see 			FloatControl#setValue(float)
	 */
	public void setVolume(float volume) {
		this.gainControl.setValue(volume);
	}
	
	/**
	 * Boolean for whether the clip is currently rendering audio.
	 * 
	 * @return 	true if clip is currently rendering audio, false otherwise.
	 * @see 	Clip.isActive()
	 */
	public boolean isActive() {
		return clip.isActive();
	}
	
	/**
	 * Boolean for whether the object successfully initialized.
	 * 
	 * @return 	true if initialized successfully, false otherwise.
	 */
	public boolean getFailed() {
		return this.failed;
	}
	
	/**
	 * Stops playback and closes clip.
	 * 
	 * @see Clip#stop()
	 * @see Clip#close()
	 */
	public void stop() {
		clip.stop();
		clip.close();
	}
}

package com.ccc.roentgen.audio;

import java.io.File;
import java.io.FileNotFoundException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect {
	public SoundEffect(String name) {
		
		//LOCATION OF AUDIO FILE IN PROJECT
		String path = "src/audio/sfx/" + name + ".wav";
		
		//System.out.printf("Starting playback of %s%n", title);
		
		try {
			File soundFile = new File(path);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format = ais.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip)AudioSystem.getLine(info);
			clip.open(ais);
			clip.start();
		} catch(FileNotFoundException e) {
			System.err.printf("System cannot find sound %s1 from path %s2%n", name, path);
		} catch(UnsupportedAudioFileException e) {
			System.err.printf("Sound %s has unsupported file format%n", name);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

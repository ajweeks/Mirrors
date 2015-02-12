package ca.liqwidice.mirrors.utils;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public enum Sound {
	SELECT("res/sounds/select.wav");

	public static float volume = -9.0f;
	public final static float MAX_VOLUME = 6.0f;
	public final static float MIN_VOLUME = -24.0f;

	private Clip clip;
	private FloatControl control;

	Sound(String filename) {
		try {
			AudioInputStream inStream = AudioSystem.getAudioInputStream(new File(filename));
			clip = AudioSystem.getClip();
			clip.open(inStream);
			control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}

	}

	public static void louder() {
		volume = Math.min(volume + 3, MAX_VOLUME);
	}

	public static void quieter() {
		volume = Math.max(volume - 3, MIN_VOLUME);
	}

	/** Returns the current volume level as a percentage */
	public static int getVolPercent() {
		return (int) (100.0 - Math.abs((volume - 6.0) / (MAX_VOLUME - MIN_VOLUME)) * 100);
	}

	public void play() {
		if (volume <= MIN_VOLUME) return; //No sound
		control.setValue((float) volume);

		if (clip.isRunning()) clip.stop();
		clip.setFramePosition(0);
		clip.start();
	}

}

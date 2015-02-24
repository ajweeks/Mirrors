package ca.liqwidice.mirrors.level;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Levels {

	public static synchronized Level load(int level, boolean original) {
		Level l = null;

		String path;
		if (original) path = "levels/original/" + level + ".ser";
		else path = "levels/" + level + ".ser";

		try (FileInputStream finStream = new FileInputStream(path);
				ObjectInputStream oinStream = new ObjectInputStream(finStream);) {
			l = (Level) oinStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			l = new Level(level); // if for some reason the file was deleted, or never created, or can't be read for some reason, create a blank one
		}

		return l;
	}

	public static synchronized Level load(int level) {
		return load(level, false);
	}

	/** Loads all of the levels */
	public static synchronized Level[] loadAll(int numOfLevels) {
		Level[] levels = new Level[numOfLevels];
		for (int i = 0; i < numOfLevels; i++) {
			levels[i] = load(i);
		}
		return levels;
	}

	/** If original is true, this will overwrite the default level */
	public static synchronized void save(Level level, boolean original) {
		String path = "levels/" + (original ? "original/" : "") + level.level + ".ser";
		try (FileOutputStream fos = new FileOutputStream(path); ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(level);
			System.out.println("Level " + level.level + " saved!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static synchronized void save(Level level) {
		save(level, false);
	}

	public static synchronized void saveAll(Level[] levels) {
		for (int i = 0; i < levels.length; i++) {
			save(levels[i]);
		}
	}
}

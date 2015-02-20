package ca.liqwidice.mirrors.level;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Levels {

	public static Level load(int level) {
		Level l = null;

		try (FileInputStream finStream = new FileInputStream("levels/" + level + ".ser");
				ObjectInputStream oinStream = new ObjectInputStream(finStream);) {
			l = (Level) oinStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return l;
	}

	/** Loads all of the levels */
	public static Level[] loadAll(int numOfLevels) {
		Level[] levels = new Level[numOfLevels];
		for (int i = 0; i < numOfLevels; i++) {
			levels[i] = load(i);
		}
		return levels;
	}

	public static void save(Level level) {
		System.out.println("Level " + level.level + " saved!");
		try (FileOutputStream fos = new FileOutputStream("levels/" + level.level + ".ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(level);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveAll(Level[] levels) {
		for (int i = 0; i < levels.length; i++) {
			save(levels[i]);
		}
	}
}

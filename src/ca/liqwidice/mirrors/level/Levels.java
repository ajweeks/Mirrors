package ca.liqwidice.mirrors.level;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Levels {

	public static synchronized Level load(int level, boolean original) {
		Level l = null;

		String path = "levels/" + (original ? "original/" : "") + level + ".ser";

		try (FileInputStream finStream = new FileInputStream(path);
				ObjectInputStream oinStream = new ObjectInputStream(finStream);) {
			l = (Level) oinStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			if (original == false) {
				// if for some reason the file was deleted, or never created, or can't be read for some reason, first try to get the original
				l = load(level, true);
			} else {
				// we wanted the original, but there isn't one, so just make a new one
				l = new Level(level);
				System.out.println("New level made! " + level);
			}
		}

		return l;
	}

	/** Overwrite (or create) the default level */
	public static synchronized void save(Level level, boolean original) {
		String path = "levels/" + (original ? "original/" : "") + level.level + ".ser";
		try (FileOutputStream fos = new FileOutputStream(path); ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(level);
			System.out.println(path + " saved!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static synchronized void saveAll(Level[] levels, boolean original) {
		for (int i = 0; i < levels.length; i++) {
			save(levels[i], original);
		}
	}
}

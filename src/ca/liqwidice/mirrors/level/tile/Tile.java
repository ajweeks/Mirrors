package ca.liqwidice.mirrors.level.tile;

import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;

import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.level.Level;

/** Represents one square on the game board */
public abstract class Tile implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int BLANK_ID = 0;
	public static final int MIRROR_ID = 1;
	public static final int POINTER_ID = 2;
	public static final int RECEPTOR_ID = 3;

	public static final int WIDTH = 64;

	protected int x, y;
	protected int id;
	protected Level level;
	protected Image image;
	protected ArrayList<Laser> lasers; // stores this tile's laser objects

	public Tile(int x, int y, int id, Level level) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.level = level;
		this.lasers = new ArrayList<>();
	}

	public void pollInput() {} // gets called every time there's user input

	public void update(double delta) {} // gets called every frame

	public abstract void render(int x, int y, Graphics g);

	public abstract void addLaser(Laser laser);

	public void removeLaser(Laser laser) {
		if (this.lasers.contains(laser)) {
			this.lasers.remove(laser);
		} else {
			System.err.println("Attempt to remove a laser that doesn't exist! " + laser);
		}
	}

	public void removeAllLasers() {
		lasers.removeAll(lasers);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public abstract Tile copy(int x, int y);

	public int getID() {
		return id;
	}

}

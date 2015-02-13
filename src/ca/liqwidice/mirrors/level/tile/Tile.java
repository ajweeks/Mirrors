package ca.liqwidice.mirrors.level.tile;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.level.Level;

/** Represents one square on the game board */
public abstract class Tile {
	protected static Image imgBlank = null;
	protected static Image imgMirror = null;
	protected static Image imgPointer = null;
	protected static Image imgReceptor = null;
	protected static Image imgLaserR = null;

	protected static Image imgBuffer = null;
	protected static Image imgLouder = null;
	protected static Image imgQuieter = null;

	public static final int WIDTH = 107;

	protected int x, y;
	protected Level level;
	protected Image image;
	protected ArrayList<Laser> lasers; // stores this tile's laser objects

	public Tile(int x, int y, Level level) {
		this.x = x;
		this.y = y;
		this.level = level;
		this.lasers = new ArrayList<>();
	}

	public void pollInput() {} // gets called every frame

	public void update(double delta) {} // gets called whenever there is a mouse click

	// LATER add tick() method for animations?

	public abstract void render(int x, int y, Graphics g);

	public abstract void addLaser(Laser laser);

	public void reset() {
		this.lasers = new ArrayList<>();
	}

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

}

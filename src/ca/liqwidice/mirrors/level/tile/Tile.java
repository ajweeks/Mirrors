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

	public static final String BLANK_ID = "Blank";
	public static final String MIRROR_ID = "Mirror";
	public static final String POINTER_ID = "Pointer";
	public static final String RECEPTOR_ID = "Receptor";

	public static final int WIDTH = 64;

	protected int x, y;
	protected String id;
	protected Level level;
	protected Image image;
	protected ArrayList<Laser> lasers; // stores this tile's laser objects

	public Tile(int x, int y, String id, Level level) {
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

	public String getID() {
		return id;
	}

}

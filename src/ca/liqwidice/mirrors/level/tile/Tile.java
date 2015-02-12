package ca.liqwidice.mirrors.level.tile;

import java.awt.Graphics;
import java.awt.Image;

import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.level.Level;

/** Represents one square on the game board */
public abstract class Tile {
	public static final int BLANK_ID = 0; // LATER make this an enum
	public static final int MIRROR_ID = 1;
	public static final int POINTER_ID = 2;
	public static final int RECEPTOR_ID = 3;

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
	protected int id;
	protected Laser laser; // stores this tile's laser object (there can only be one, for now at least)

	public Tile(int id, int x, int y, Level level) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.level = level;
		this.laser = Laser.NULL;
	}

	//	/** Returns whether or not Tile t has a laser that is  */
	//	protected boolean isShiningAtUs(Tile t) {
	//		if (t.laser == null) return false;
	//		Direction towardsUsFromtThem = t.y < this.y ? Direction.SOUTH : t.y > this.y ? Direction.NORTH
	//				: t.x < this.x ? Direction.EAST : Direction.WEST;
	//		if (t.laser.getDirExiting() == towardsUsFromtThem) return true;
	//		return false;
	//	}

	protected Tile[] getNeighbors() {
		Tile[] neighbors = new Tile[4];

		neighbors[0] = level.getTile(x, y - 1);
		neighbors[1] = level.getTile(x + 1, y);
		neighbors[2] = level.getTile(x, y + 1);
		neighbors[3] = level.getTile(x - 1, y);

		return neighbors;
	}

	public abstract void pollInput(); // gets called every frame

	public abstract void update(double delta); // gets called whenever there is input

	// LATER add tick() method for animations?
	
	public abstract void render(int x, int y, Graphics g);

	public void reset() {
		this.laser = Laser.NULL;
	}

	public abstract void addLaser(Laser laser);

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}

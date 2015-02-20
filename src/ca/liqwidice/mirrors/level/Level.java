package ca.liqwidice.mirrors.level;

import java.awt.Graphics;
import java.io.Serializable;

import ca.liqwidice.mirrors.input.Mouse;
import ca.liqwidice.mirrors.level.tile.BlankTile;
import ca.liqwidice.mirrors.level.tile.PointerTile;
import ca.liqwidice.mirrors.level.tile.ReceptorTile;
import ca.liqwidice.mirrors.level.tile.Tile;

public class Level implements Serializable {
	private static final long serialVersionUID = 1L;

	// How far to offset the rendering of the game grid
	public static final int xo = 150;
	public static final int yo = 0;

	public Tile[][] tiles;
	public int width, height;
	public int level;
	public boolean completed = false;

	public Level(int level) {
		this.height = 8; // LATER add variably-sized w and h
		this.width = 10;
		tiles = new Tile[height][width];
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[y].length; x++) {
				tiles[y][x] = new BlankTile(x, y, this); // TEMpORARY line
			}
		}
		this.level = level;
		updatePointerTiles(1.0d);
		updateAllNonPointerTiles(1.0d);
	}

	public void update(double delta, boolean levelEditingMode) {
		if (levelEditingMode == false) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					tiles[y][x].pollInput(); // poll for input every frame
				}
			}
		}

		if (Mouse.leftClicked || Mouse.rightClicked) { // but only update the board when there is a change (efficiency!!)
			removeAllLasers();
			updatePointerTiles(delta);
			updateAllNonPointerTiles(delta);

			if (allReceptorsOn()) {
				this.completed = true;
			} else {
				this.completed = false;
			}
		}
	}

	public void render(Graphics g) {
		for (int y = 0; y < height; y++) {
			int yy = yo + y * Tile.WIDTH;
			for (int x = 0; x < width; x++) {
				int xx = xo + x * Tile.WIDTH;
				tiles[y][x].render(xx, yy, g);
			}
		}
	}

	public Tile getTile(int x, int y) {
		if (y < 0 || y >= height || x < 0 || x >= width) return null;
		return tiles[y][x];
	}

	/** Returns the tile under the mouse this frame OR null if mouse is not above a tile */
	public Tile getTileUnderMouse() {
		if (Mouse.x > xo && Mouse.x < xo + (width + 1) * Tile.WIDTH) {
			if (Mouse.y > yo && Mouse.y < yo + (height + 1) * Tile.WIDTH) {
				int x = (Mouse.x - xo) / Tile.WIDTH;
				int y = (Mouse.y - yo) / Tile.WIDTH;
				return tiles[y][x];
			}
		}
		return null;
	}

	private void updateAllNonPointerTiles(double delta) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (tiles[y][x] instanceof PointerTile == false) {
					tiles[y][x].update(delta);
				}
			}
		}
	}

	private void updatePointerTiles(double delta) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (tiles[y][x] instanceof PointerTile) {
					tiles[y][x].update(delta);
				}
			}
		}
	}

	private boolean allReceptorsOn() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (tiles[y][x] instanceof ReceptorTile) {
					if (((ReceptorTile) tiles[y][x]).isOn() == false) return false; // If any of the receptor tiles are off return false
				}
			}
		}
		return true;
	}

	private void removeAllLasers() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[y][x].removeAllLasers();
			}
		}
	}
}

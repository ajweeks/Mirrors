package ca.liqwidice.mirrors.level;

import java.awt.Graphics;

import ca.liqwidice.mirrors.input.Mouse;
import ca.liqwidice.mirrors.level.tile.PointerTile;
import ca.liqwidice.mirrors.level.tile.ReceptorTile;
import ca.liqwidice.mirrors.level.tile.Tile;

public class Level {

	// How far to offset the rendering of the game grid
	public static final int xo = 150;
	public static final int yo = 0;

	public Tile[][][] levels = Levels.getTiles(this); // This object contains all of the levels in the game.. LATER decide if moving this into separate objects would be better

	public Tile[][] tiles;
	public int width, height;
	public int level;
	public boolean completed = false;

	public Level(int level) {
		this.tiles = Levels.getTiles(this)[level];
		this.level = level;
		this.height = tiles.length;
		this.width = tiles[0].length;
		updatePointerTiles(1.0d);
		updateAllNonPointerTiles(1.0d);
	}

	public void update(double delta) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[y][x].pollInput(); // poll for input every frame
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

	public void reset() {
		this.tiles = Levels.getTiles(this)[level];
		completed = false;
		updatePointerTiles(1.0d);
		updateAllNonPointerTiles(1.0d);
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

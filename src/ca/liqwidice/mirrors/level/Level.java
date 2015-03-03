package ca.liqwidice.mirrors.level;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import ca.liqwidice.mirrors.Game;
import ca.liqwidice.mirrors.input.Mouse;
import ca.liqwidice.mirrors.level.tile.BlankTile;
import ca.liqwidice.mirrors.level.tile.PointerTile;
import ca.liqwidice.mirrors.level.tile.Receptor;
import ca.liqwidice.mirrors.level.tile.ReceptorTile;
import ca.liqwidice.mirrors.level.tile.Tile;
import ca.liqwidice.mirrors.state.GameState;

public class Level implements Serializable {
	private static final long serialVersionUID = 1L;

	// How far to offset the rendering of the game grid
	public static final int xo = 150;
	public static final int yo = 0;

	public Tile[][] tiles;
	public static int width = 10, height= 8;
	// LATER add variably-sized w and h

	public int level;
	private boolean completedNow = false; // Is true if every receptor tile is currently lit
	public boolean completedClicked = false; // Is only true when completed is true this frame, and was NOT true last frame
	public boolean completedEver = false; // Gets set to true once completedNow gets set to true. Only gets set to false when all levels are reset.

	public Level(int level) {
		tiles = new Tile[height][width];
		clear(); // instantiate the level with blank tiles
		this.level = level;
		updatePointerTiles(1.0d);
		updateAllNonPointerTiles(1.0d);

		this.completedNow = true; // instantiate to true so that when loading a level that has been completed already, the winscreen popup isn't shown
	}

	public void update(double delta) {
		if (GameState.levelEditingMode == false) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					tiles[y][x].pollInput(); // poll for input every frame
				}
			}
		}

		if (Mouse.leftClicked || Mouse.rightClicked) { // only update the board when there is a change (efficiency!!)
			removeAllLasers();
			updatePointerTiles(delta);
			updateAllNonPointerTiles(delta);
		}
	}

	public boolean checkCompleted() {
		boolean completedBefore = completedNow;

		if (allReceptorsOn()) completedNow = true;
		else completedNow = false;

		if (completedNow == true && completedBefore == false) completedClicked = true;
		else completedClicked = false;

		if (completedNow) completedEver = true;

		return completedClicked;
	}

	public void render(Graphics g) {
		for (int y = 0; y < height; y++) {
			int yy = yo + y * Tile.WIDTH;
			for (int x = 0; x < width; x++) {
				int xx = xo + x * Tile.WIDTH;
				tiles[y][x].render(xx, yy, g);
			}
		}

		if (Mouse.x > xo && Mouse.x < xo + width * Tile.WIDTH && Mouse.y > yo && Mouse.y < yo + height * Tile.WIDTH) {
			int xx = (Mouse.x - xo) / Tile.WIDTH;
			int yy = (Mouse.y - yo) / Tile.WIDTH;

			if (GameState.levelEditingMode) {
				g.setColor(Color.BLACK);
				Tile t = tiles[yy][xx];
				int xxo = Mouse.x > Game.SIZE.width / 2 ? -100 : 0;
				g.drawString("" + t.getID(), Mouse.x + xxo + 10, Mouse.y);
				if (t instanceof ReceptorTile) {
					g.drawString("" + ((ReceptorTile) t).getDirection(), Mouse.x + xxo + 10, Mouse.y - 20);
					Receptor[] receptors = ((ReceptorTile) t).getReceptors();
					for (int i = 0; i < receptors.length; i++) {
						g.drawString("" + receptors[i].getDirection(), Mouse.x + xxo + 10, Mouse.y + (i + 1) * 20);
						g.drawString("" + receptors[i].getLaser().getDirEntering(), Mouse.x + xxo + 60, Mouse.y
								+ (i + 1) * 20);
					}
				}
			}
		}
	}

	public Tile getTile(int x, int y) {
		if (y < 0 || y >= height || x < 0 || x >= width) return null;
		return tiles[y][x];
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

	public void clear() {
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[y].length; x++) {
				tiles[y][x] = new BlankTile(x, y, this);
			}
		}
		this.completedNow = false;
		this.completedClicked = false;
	}

	/** Copy all of the new level's fields into us */
	public void copy(Level level) {
		this.tiles = level.tiles;
		this.completedNow = level.completedNow;
	}

	public void setCompletedEver(boolean completedEver) {
		this.completedEver = completedEver;
	}

	public boolean isCompletedEver() {
		return completedEver;
	}

}

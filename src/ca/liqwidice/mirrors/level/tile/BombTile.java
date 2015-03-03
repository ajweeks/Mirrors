package ca.liqwidice.mirrors.level.tile;

import java.awt.Graphics;

import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.level.Level;
// LATER implement bomb tile
public class BombTile extends Tile {
	private static final long serialVersionUID = 1L;

	/** If a laser touches a bomb, the player must restart the level */
	public BombTile(int x, int y, String id, Level level) {
		super(x, y, id, level);
	}

	@Override
	public void render(int x, int y, Graphics g) {

	}

	@Override
	public void addLaser(Laser laser) {

	}

	@Override
	public Tile copy(int x, int y) {
		return null;
	}

}

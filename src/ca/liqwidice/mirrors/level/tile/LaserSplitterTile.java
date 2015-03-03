package ca.liqwidice.mirrors.level.tile;

import java.awt.Graphics;

import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.level.Level;
// LATER implement laser splitter tile
public class LaserSplitterTile extends MirrorTile {
	private static final long serialVersionUID = 1L;
		
	/**
	 * Like a mirror, but reflects in both directions
	 * */
	public LaserSplitterTile(int x, int y, int id, Level level) {
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

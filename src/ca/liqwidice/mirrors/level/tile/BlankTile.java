package ca.liqwidice.mirrors.level.tile;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.level.Level;

public class BlankTile extends Tile {

	public BlankTile(int x, int y, Level level) {
		super(Tile.BLANK_ID, x, y, level);
	}

	@Override
	public void update(double delta) {

	}

	@Override
	public void render(int x, int y, Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, WIDTH, WIDTH);

		if (this.laser != Laser.NULL) {
			this.laser.render(x, y, g);
		}
	}

	@Override
	public void addLaser(Laser laser) {
		this.laser = laser;
		laser.setDirExiting(laser.getDirEntering().opposite()); // simply let it pass through us
	}
}

package ca.liqwidice.mirrors.level.tile;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.level.Level;

public class BlankTile extends Tile {

	public BlankTile(int x, int y, Level level) {
		super(x, y, level);
	}

	@Override
	public void render(int x, int y, Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, WIDTH, WIDTH);

		for (Laser l : lasers) {
			l.render(x, y, g);
		}
	}

	@Override
	public void addLaser(Laser laser) {
		this.lasers.add(laser);
		this.lasers.get(lasers.size() - 1).setDirExiting(laser.getDirEntering().opposite()); // simply let it pass through us
	}

}

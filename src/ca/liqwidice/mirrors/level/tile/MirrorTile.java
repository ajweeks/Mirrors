package ca.liqwidice.mirrors.level.tile;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.input.Mouse;
import ca.liqwidice.mirrors.level.Direction;
import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.level.Level;

public class MirrorTile extends Tile {

	public static final int FS = 0; //the mirror is angled like a forward slash, from the SW corner to the NE (/)
	public static final int BS = 1; //the mirror is angled like a backward slash, from the NW corner to the SE (\)

	protected int direction = FS;

	public MirrorTile(int x, int y, Level level) {
		super(x, y, level);
	}

	@Override
	public void pollInput() {
		if (Mouse.leftClicked && Mouse.isInside(this)) switchDirection();
	}

	@Override
	public void render(int x, int y, Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, WIDTH, WIDTH);

		g.setColor(Color.GRAY);
		if (direction == FS) g.drawLine(x, y + WIDTH, x + WIDTH, y);
		else if (direction == BS) g.drawLine(x, y, x + WIDTH, y + WIDTH);

		for (Laser l : lasers) {
			l.render(x, y, g);
		}
	}

	@Override
	public void addLaser(Laser laser) {
		this.lasers.add(laser);

		// determine the exiting direction
		Direction exiting = Direction.NULL;
		if (this.direction == FS) {
			if (laser.getDirEntering() == Direction.NORTH) exiting = Direction.WEST;
			else if (laser.getDirEntering() == Direction.WEST) exiting = Direction.NORTH;
			else if (laser.getDirEntering() == Direction.EAST) exiting = Direction.SOUTH;
			else if (laser.getDirEntering() == Direction.SOUTH) exiting = Direction.EAST;
		} else if (this.direction == BS) {
			if (laser.getDirEntering() == Direction.NORTH) exiting = Direction.EAST;
			else if (laser.getDirEntering() == Direction.EAST) exiting = Direction.NORTH;
			else if (laser.getDirEntering() == Direction.WEST) exiting = Direction.SOUTH;
			else if (laser.getDirEntering() == Direction.SOUTH) exiting = Direction.WEST;
		}
		this.lasers.get(lasers.size() - 1).setDirExiting(exiting);
	}

	@Override
	public void reset() {
		this.direction = FS;
		removeAllLasers();
	}

	public void switchDirection() {
		this.direction = (this.direction + 1) % 2; // alternate between 0 and 1
	}
}

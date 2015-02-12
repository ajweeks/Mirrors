package ca.liqwidice.mirrors.level.tile;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.input.Mouse;
import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.level.Level;

public class MirrorTile extends Tile {

	public static final int FS = 0; //the mirror is angled like a forward slash, from the SW corner to the NE (/)
	public static final int BS = 1; //the mirror is angled like a backward slash, from the NW corner to the SE (\)

	protected int direction = FS;

	public MirrorTile(int x, int y, Level level) {
		super(Tile.MIRROR_ID, x, y, level);
	}

	public void update(double delta) {
		// LATER set direction exiting
		if (Mouse.leftClicked && Mouse.isInside(this)) switchDirection();
	}

	public void render(int x, int y, Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, WIDTH, WIDTH);

		g.setColor(Color.GRAY);
		if (direction == FS) g.drawLine(x, y, x + WIDTH, y + WIDTH);
		else g.drawLine(x, y + WIDTH, x + WIDTH, y);

		if (laser != null) {
			g.setColor(laser.getColour());

			switch (laser.getDirEntering()) {
			case NORTH:
				// Top to center
				g.drawLine(x + WIDTH / 2, y, x + WIDTH / 2, y + WIDTH / 2);
				if (this.direction == FS) {
					// Left to center
					g.drawLine(x, y + WIDTH / 2, x + WIDTH / 2, y + WIDTH / 2);
				} else {
					// Right to center
					g.drawLine(x + WIDTH / 2, y + WIDTH / 2, x + WIDTH, y + WIDTH / 2);
				}
				break;
			case SOUTH:
				// Bottom to center
				g.drawLine(x + WIDTH / 2, y + WIDTH / 2, x + WIDTH / 2, y + WIDTH);
				if (this.direction == FS) {
					//Right to center
					g.drawLine(x + WIDTH / 2, y + WIDTH / 2, x + WIDTH, y + WIDTH / 2);
				} else {
					// Left to center
					g.drawLine(x, y + WIDTH / 2, x + WIDTH / 2, y + WIDTH / 2);
				}
				break;
			case EAST:
				// Left to center
				g.drawLine(x, y + WIDTH / 2, x + WIDTH / 2, y + WIDTH / 2);
				if (this.direction == FS) {
					// Top to center
					g.drawLine(x + WIDTH / 2, y, x + WIDTH / 2, y + WIDTH / 2);
				} else {
					// Bottom to center
					g.drawLine(x + WIDTH / 2, y + WIDTH / 2, x + WIDTH / 2, y + WIDTH);
				}
				break;
			case WEST:
				//Right to center
				g.drawLine(x + WIDTH / 2, y + WIDTH / 2, x + WIDTH, y + WIDTH / 2);
				if (this.direction == FS) {
					// Bottom to center
					g.drawLine(x + WIDTH / 2, y + WIDTH / 2, x + WIDTH / 2, y + WIDTH);
				} else {
					// Top to center
					g.drawLine(x + WIDTH / 2, y, x + WIDTH / 2, y + WIDTH / 2);
				}
				break;
			case NULL:
				break;
			}

			if (this.laser != Laser.NULL) {
				this.laser.render(x, y, g);
			}
		}
	}

	@Override
	public void addLaser(Laser laser) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void reset() {
		this.direction = FS;
	}

	public void switchDirection() {
		this.direction = (this.direction + 1) % 2; // alternate between 0 and 1
	}
}

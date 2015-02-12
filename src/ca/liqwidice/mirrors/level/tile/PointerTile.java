package ca.liqwidice.mirrors.level.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import ca.liqwidice.mirrors.input.Mouse;
import ca.liqwidice.mirrors.level.Direction;
import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.level.Level;

public class PointerTile extends Tile { //Symbolizes a laser pointer, a source of light

	protected Direction direction = Direction.NORTH;
	private boolean on;
	private Color colour;

	public PointerTile(int x, int y, Color colour, Level level) {
		super(Tile.POINTER_ID, x, y, level);
		this.colour = colour;
		this.laser = new Laser(Direction.NORTH, colour);
		setOn(true);
	}

	@Override
	public void pollInput() {
		if (Mouse.leftClicked && Mouse.isInside(this)) {
			setOn(!this.on);
		}

		if (Mouse.rightClicked && Mouse.isInside(this)) {
			this.direction = this.direction.cw();
			this.laser.setDirExiting(this.direction);
		}
	}

	@Override
	public void update(double delta) {
		// Start the updating chain
		boolean[] checkedTiles = new boolean[level.height * level.width]; /* LATER add a boolean value to each tile instead of creating this every frame (possibly multiple times!) */
		Direction nextDir = direction; // the direction towards the next tile (which is initially our direction)
		int xx = x + nextDir.offset[0];
		int yy = y + nextDir.offset[1];

		do {
			if (xx < 0 || xx >= level.width || yy < 0 || yy >= level.height) break; // The next direction is leading us into the wall

			if (checkedTiles[xx + yy * level.width] == true) return; // we've already checked this tile (this line avoids infinite loops)
			else checkedTiles[xx + yy * level.width] = true;

			Tile nextTile = level.getTile(xx, yy); // get the tile to be updated
			if (nextTile == null) return; // we hit a wall

			if (this.on) {
				// Find the next direction *after* setting the new laser object
				nextTile.addLaser(new Laser(nextDir.opposite(), laser.getColour()));
				nextDir = nextTile.laser.getDirExiting(); 
			} else {
				// Find the next direction *before* setting the new laser object (or else we won't be able to remove lasers properly)
				nextDir = nextTile.laser.getDirExiting();
				nextTile.addLaser(Laser.NULL);
			}

			xx = nextTile.getX() + nextDir.offset[0];
			yy = nextTile.getY() + nextDir.offset[1];

		} while (nextDir != Direction.NULL);

	}

	@Override
	public void render(int x, int y, Graphics g) {
		if (on) g.setColor(Color.ORANGE);
		else g.setColor(Color.GRAY);
		g.fillRect(x, y, WIDTH, WIDTH);

		g.setColor(Color.WHITE);
		int x1 = 15;
		int x2 = 85;
		int y1 = 15;
		int y2 = 85;
		switch (direction) {
		case NORTH:
			g.fillPolygon(new Polygon(new int[] { x + x1, x + x2, x + (x2 - x1) / 2 + x1 }, new int[] { y + y2, y + y2,
					y + y1 }, 3));
			break;
		case EAST:
			g.fillPolygon(new Polygon(new int[] { x + x1, x + x1, x + x2 }, new int[] { y + y1, y + y2,
					y + (y2 - y1) / 2 + y1 }, 3));
			break;
		case SOUTH:
			g.fillPolygon(new Polygon(new int[] { x + x1, x + x2, x + (x2 - x1) / 2 + x1 }, new int[] { y + y1, y + y1,
					y + y2 }, 3));
			break;
		case WEST:
			g.fillPolygon(new Polygon(new int[] { x + x2, x + x2, x + x1 }, new int[] { y + y1, y + y2,
					y + (y2 - y1) / 2 + y1 }, 3));
			break;
		case NULL:
			break;
		}

		if (this.laser != Laser.NULL) {
			this.laser.render(x, y, g);
		}
	}

	private void setOn(boolean on) {
		this.on = on;
		if (this.on) this.laser = new Laser(Direction.NULL, direction, colour);
		else this.laser = Laser.NULL;
	}

	@Override
	public void addLaser(Laser laser) {
		// Obviously we aren't going to add a laser to this, WE ARE THE LASER SOURCE
	}

	@Override
	public void reset() {
		this.direction = Direction.NORTH;
		this.on = true;
		this.laser = new Laser(Direction.NULL, colour);
		this.laser.setDirExiting(this.direction);
	}

	public Direction getDirection() {
		return direction;
	}
}

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
	private boolean on = true;
	private Color colour;

	public PointerTile(int x, int y, Color colour, Level level) {
		super(Tile.POINTER_ID, x, y, level);
		this.colour = colour;
		this.laser = new Laser(Direction.NORTH, colour);
	}

	@Override
	public void update(double delta) {
		if (Mouse.leftClicked && Mouse.isInside(this)) {
			this.on = !this.on;
			if (this.on) this.laser = new Laser(Direction.NULL, colour);
			else this.laser = Laser.NULL;
		}

		if (Mouse.rightClicked && Mouse.isInside(this)) {
			this.direction = this.direction.cw();
		}

		// Start the updating chain

		// FIXME put this in a method that only gets called when the game board gets clicked

		boolean[] checkedTiles = new boolean[level.height * level.width]; /* LATER add a boolean value to each tile instead of creating this every frame (possibly multiple times!) */
		Direction nextDir = direction; // the direction towards the next tile (which is initially our direction)
		int xx = x + nextDir.offset[0];
		int yy = y + nextDir.offset[1];
		do {
			if (xx < 0 || xx >= level.width || yy < 0 || yy >= level.height) {
				break; // The next direction is leading us into the wall
			}
			// iterate throughout the grid instead of going to each individual tile
			if (checkedTiles[xx + yy * level.width] == true) return; // we've already checked this tile
			else checkedTiles[xx + yy * level.width] = true;

			Tile nextTile = level.getTile(xx, yy); // get the tile to be updated
			if (nextTile == null) return; // we hit a wall

			if (this.on) nextTile.addLaser(new Laser(nextDir.opposite(), laser.getColour()));
			else nextTile.addLaser(Laser.NULL);

			if (nextTile.laser != Laser.NULL) {
				nextDir = nextTile.laser.getDirExiting();
			} else {
				nextDir = Direction.NULL;
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
		default:
			break;
		}

		if (this.laser != Laser.NULL) {
			this.laser.render(x, y, g);
		}
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
	}

	public Direction getDirection() {
		return direction;
	}
}

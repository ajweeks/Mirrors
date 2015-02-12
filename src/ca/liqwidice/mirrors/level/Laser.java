package ca.liqwidice.mirrors.level;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.level.tile.Tile;

public class Laser {

	public static final Laser NULL = new Laser(Direction.NULL, Color.black);

	private static int BEAM_WIDTH = 25;

	// LATER allow squares to have multiple lasers entering/exiting them without using 4 different laser objects)

	private Direction entering; // The side of the mirror that the laser is entering on (Should only be Direction.NULL on tiles that emit lasers (just pointers atm))
	private Direction exiting; // The side of the mirror that the laser is coming out of (Direction.NULL if it doesn't exit because it is being blocked or absorbed)

	private Color colour; // LATER use laser textures

	public Laser(Direction entering, Color colour) {
		this.entering = entering;
		this.exiting = Direction.NULL;
		this.colour = colour;
	}

	public void render(int x, int y, Graphics g) {
		g.setColor(colour);
		if (entering == Direction.EAST || exiting == Direction.EAST) {
			g.fillRect(x + Tile.WIDTH / 2, y + Tile.WIDTH / 2 - BEAM_WIDTH / 2, Tile.WIDTH / 2, BEAM_WIDTH);

		} else if (entering == Direction.NORTH || exiting == Direction.NORTH) {
			g.fillRect(x + Tile.WIDTH / 2 - BEAM_WIDTH / 2, y, BEAM_WIDTH, Tile.WIDTH / 2);

		} else if (entering == Direction.SOUTH || exiting == Direction.SOUTH) {
			g.fillRect(x + Tile.WIDTH / 2 - BEAM_WIDTH / 2, y + Tile.WIDTH / 2, BEAM_WIDTH, Tile.WIDTH);

		} else if (entering == Direction.WEST || exiting == Direction.WEST) {
			g.fillRect(x, y + Tile.WIDTH / 2 - BEAM_WIDTH / 2, Tile.WIDTH / 2, BEAM_WIDTH);
		}
	}

	public void setDirExiting(Direction exiting) {
		this.exiting = exiting;
	}

	public void setDirEntering(Direction entering) {
		this.entering = entering;
	}

	public Direction getDirEntering() {
		return entering;
	}

	public Direction getDirExiting() {
		return exiting;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}

	public Color getColour() {
		return colour;
	}

}

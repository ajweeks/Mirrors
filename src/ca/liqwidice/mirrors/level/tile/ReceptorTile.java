package ca.liqwidice.mirrors.level.tile;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.input.Mouse;
import ca.liqwidice.mirrors.level.Direction;
import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.level.Level;

/**
 * Represents a goal, the player must get the laser (which is shot out of the PointerTile) to this tile to win
 * This particular ReceptorTile can only be activated by lasers entering from one direction, all others are ignored
 */
public class ReceptorTile extends Tile {

	private boolean on; //LATER make this an integer, representing all inputs 
	private Direction direction = Direction.NORTH; // The direction our receptor is facing LATER add different types of receptors (2-way, colour coded, all way, etc.)

	public ReceptorTile(int x, int y, Level level) {
		super(x, y, level);
		this.on = false;
	}

	@Override
	public void pollInput() {
		if (Mouse.rightClicked && Mouse.isInside(this)) {
			this.direction = this.direction.cw();
		}
	}

	@Override
	public void update(double delta) {
		this.on = false;
		for (int i = 0; i < lasers.size(); i++) {
			if (lasers.get(i).getDirEntering() == this.direction) {
				this.on = true;
			}
		}
		if (!this.on) {
			removeAllLasers();
		}
	}

	@Override
	public void render(int x, int y, Graphics g) {
		if (this.on) g.setColor(Color.GREEN);
		else g.setColor(Color.RED);
		g.fillRect(x, y, WIDTH, WIDTH);

		g.setColor(Color.WHITE);
		switch (direction) {
		case NORTH:
			g.fillOval(x + 38, y + 10, 30, 30);
			break;
		case EAST:
			g.fillOval(x + 68, y + 38, 30, 30);
			break;
		case SOUTH:
			g.fillOval(x + 38, y + 68, 30, 30);
			break;
		case WEST:
			g.fillOval(x + 15, y + 38, 30, 30);
			break;
		case NULL:
		default:
			System.err.println("Invalid direction on receptor tile at " + x + ", " + y + ": " + direction.toString());
			break;
		}

		// DON'T RENDER THE LASER!! WE ONLY HAVE ONE SO WE KNOW WHEN THE PLAYER HAS WOOONN!!
		// if (this.laser != Laser.NULL) {
		//     this.laser.render(x, y, g);
		// }
	}

	@Override
	public void addLaser(Laser laser) {
		this.lasers.add(laser);
		// Note: don't assign the exiting direction to anything, keep it as NULL so the laser will stop here
	}

	@Override
	public void reset() {
		this.direction = Direction.NORTH;
		this.on = false;
		removeAllLasers();
	}

	public boolean isOn() {
		return on;
	}
}

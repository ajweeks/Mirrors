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
		super(Tile.RECEPTOR_ID, x, y, level);
		setOn(false);
	}

	@Override
	public void pollInput() {
		if (Mouse.rightClicked && Mouse.isInside(this)) {
			this.direction = this.direction.cw();
		}
	}
	
	@Override
	public void update(double delta) {


		if (this.laser != Laser.NULL) {
			setOn(this.laser.getDirEntering() == this.direction);
		}
	}

	@Override
	public void render(int x, int y, Graphics g) {
		if (on) g.setColor(Color.GREEN);
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
		this.laser = laser;
	}

	@Override
	public void reset() {
		this.direction = Direction.NORTH;
		setOn(false);
	}

	public boolean isOn() {
		return on;
	}

	private void setOn(boolean on) {
		this.on = on;
		if (this.on == false) this.laser = Laser.NULL;
	}

}

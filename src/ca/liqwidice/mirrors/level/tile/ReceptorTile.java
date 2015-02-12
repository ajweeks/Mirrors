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

	//LATER add colours to lasers!
	private boolean on; //LATER make this an integer, representing all inputs 
	private Direction direction = Direction.NORTH; // The direction our receptor is facing LATER add different types of receptors (2-way, colour coded, all way, etc.)

	/**
	 *    Laser colours
	 * 
	 *  0b00000001 R 0x01
	 *  0b00000010 G 0x02
	 *  0b00000100 B 0x04
	 *  0b00001000 Y 0x08
	 *  0b00010000 O 0x10
	 *  0b00100000 P 0x20
	 *  0b01000000 V 0x40
	 *  0b10000000 C 0x80
	 *  
	 *  0b00100010 G+P 0x22 
	 */

	public ReceptorTile(int x, int y, Level level) {
		super(Tile.RECEPTOR_ID, x, y, level);
	}

	@Override
	public void update(double delta) {
		if (Mouse.leftClicked && Mouse.isInside(this)) {
			this.on = !this.on;
		}

		if (Mouse.rightClicked && Mouse.isInside(this)) {
			this.direction = this.direction.cw();
		}

		// Check if neighbors are sending any lasers our way
		Tile[] neighbors = getNeighbors();
		if (neighbors[0] != null) { // Tile above
			if (neighbors[0].laser != null) {
				if (neighbors[0].laser.getDirExiting() == this.direction.opposite()) {
					this.on = true;
				}
			}
		}
	}

	@Override
	public void render(int x, int y, Graphics g) {
		// background
		if (on) g.setColor(Color.GREEN);
		else g.setColor(Color.RED);
		g.fillRect(x, y, WIDTH, WIDTH);

		// receptor
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

		// laser
		if (this.laser != Laser.NULL) {
			this.laser.render(x, y, g);
		}
	}

	@Override
	public void addLaser(Laser laser) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void reset() {
		this.direction = Direction.NORTH;
		this.on = false;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

}

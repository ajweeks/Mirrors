package ca.liqwidice.mirrors.level.tile;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.Colours;
import ca.liqwidice.mirrors.input.Mouse;
import ca.liqwidice.mirrors.level.Direction;
import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.level.Level;
import ca.liqwidice.mirrors.state.GameState;

/**
 * Represents a goal, the player must get the laser (which is shot out of the PointerTile) to this tile to win
 * This particular ReceptorTile can only be activated by lasers entering from one direction, all others are ignored
 */
public class ReceptorTile extends Tile {
	private static final long serialVersionUID = 1L;

	private Receptor[] receptors = new Receptor[4]; // One receptor for each direction, always initialize to at least a Receptor.NULL, or an actual receptor
	private boolean locked = false;
	private Direction direction;

	public ReceptorTile(int x, int y, Level level) {
		super(x, y, RECEPTOR_ID, level);
		this.direction = Direction.NORTH;
		receptors = new Receptor[] { //
		new Receptor(Direction.NORTH, Color.RED), // N
				Receptor.NULL, // E
				Receptor.NULL, // S
				Receptor.NULL }; // W
	}

	@Override
	public void pollInput() {
		if (GameState.levelEditingMode) {
			if (Mouse.isInside(this)) {

				int xx = (Mouse.x - Level.xo) % Tile.WIDTH;
				int yy = (Mouse.y - Level.yo) % Tile.WIDTH;

				if (Mouse.leftClicked) { // left clicks add receptors or change colours

					if (xx + yy <= Tile.WIDTH) { // NW
						if (xx >= yy) { // NE
							updateReceptor(Direction.NORTH.sub(direction));
						} else { // SW
							updateReceptor(Direction.WEST.sub(direction));
						}
					} else { // SE
						if (xx >= yy) { // NE
							updateReceptor(Direction.EAST.sub(direction));
						} else { // SW
							updateReceptor(Direction.SOUTH.sub(direction));
						}
					}

					// re-implement the ability to lock lasers? or nah?..
				} else if (Mouse.rightClicked) { // right clicks rotate objects in level editing mode
					this.direction = this.direction.cw();
				}
			}
		} else if (Mouse.leftClicked && Mouse.isInside(this)) {
			if (this.locked == false) {
				this.direction = this.direction.cw();
			}
		}
	}

	private void updateReceptor(Direction dir) {
		Receptor r = receptors[dir.encode()];
		if (r.getDirection() == Direction.NULL) {
			receptors[dir.encode()] = new Receptor(dir, Color.RED);
		}
		r.setColour(Colours.nextColour(r.getColour(), true));
		if (numOfReceptors() > 1 && r.getColour() == Color.RED) receptors[dir.encode()] = Receptor.NULL;
	}

	private int numOfReceptors() {
		int num = 0;
		for (int i = 0; i < receptors.length; i++) {
			if (receptors[i].getDirection() != Direction.NULL) num++;
		}
		return num;
	}

	@Override
	public void update(double delta) {
		for (int i = 0; i < receptors.length; i++) {
			receptors[i].update(direction, delta);
		}
	}

	@Override
	public void render(int x, int y, Graphics g) {
		if (locked) g.setColor(Color.LIGHT_GRAY);
		else if (isOn()) g.setColor(Color.GREEN);
		else g.setColor(Color.WHITE);
		g.fillRect(x, y, WIDTH, WIDTH);

		for (int i = 0; i < receptors.length; i++) {
			receptors[i].render(direction, x, y, g);
		}
	}

	@Override
	public void addLaser(Laser laser) {
		// There probably is a better way to do this, but this works for now
		if (direction == Direction.EAST || direction == Direction.WEST) receptors[laser.getDirEntering().sub(direction)
				.encode()].setLaser(laser);
		else receptors[laser.getDirEntering().add(direction).encode()].setLaser(laser);
		this.lasers.add(laser);
	}

	@Override
	public void removeAllLasers() {
		// We have to override this  method because this tile itself will never actually have any lasers
		// It just has receptors, which have lasers
		for (int i = 0; i < receptors.length; i++) {
			receptors[i].removeLaser();
		}
		this.lasers.removeAll(lasers);
	}

	public boolean isOn() {
		for (int i = 0; i < receptors.length; i++) {
			if (receptors[i].getDirection() != Direction.NULL && receptors[i].isOn() == false) return false;
		}
		return true;
	}

	@Override
	public Tile copy(int x, int y) {
		return new ReceptorTile(x, y, level);
	}

	public Receptor[] getReceptors() {
		return receptors;

	}

	public Direction getDirection() {
		return direction;
	}

	public boolean isLocked() {
		return locked;
	}
}

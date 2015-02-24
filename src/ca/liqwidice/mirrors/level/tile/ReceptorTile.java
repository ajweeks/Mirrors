package ca.liqwidice.mirrors.level.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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

	public transient static BufferedImage N, Nl, E, El, S, Sl, W, Wl;

	static {
		try {
			N = ImageIO.read(new File("res/receptorN.png"));
			Nl = ImageIO.read(new File("res/receptor_lockedN.png"));
			E = ImageIO.read(new File("res/receptorE.png"));
			El = ImageIO.read(new File("res/receptor_lockedE.png"));
			S = ImageIO.read(new File("res/receptorS.png"));
			Sl = ImageIO.read(new File("res/receptor_lockedS.png"));
			W = ImageIO.read(new File("res/receptorW.png"));
			Wl = ImageIO.read(new File("res/receptor_lockedW.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean on; //LATER make this an integer, representing all inputs 
	private Direction direction; // The direction our receptor is facing LATER add different types of receptors (2-way, colour coded, all way, etc.)
	private boolean locked = false;

	public ReceptorTile(int x, int y, int direction, Level level) {
		super(x, y, RECEPTOR_ID, level);
		this.direction = Direction.decode(direction);
		this.on = false;
	}

	@Override
	public void pollInput() {
		if (Mouse.leftClicked && Mouse.isInside(this)) {
			if (this.locked == false) this.direction = this.direction.cw();
		}
		if (GameState.levelEditingMode && Mouse.rightClicked && Mouse.isInside(this)) {
			this.locked = !this.locked;
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
		if (this.on) g.setColor(Color.YELLOW);
		else g.setColor(Color.WHITE);
		g.fillRect(x, y, WIDTH, WIDTH);

		switch (direction) {
		case NORTH:
			if (locked) g.drawImage(Nl, x, y, null);
			else g.drawImage(N, x, y, null);
			break;
		case EAST:
			if (locked) g.drawImage(El, x, y, null);
			else g.drawImage(E, x, y, null);
			break;
		case SOUTH:
			if (locked) g.drawImage(Sl, x, y, null);
			else g.drawImage(S, x, y, null);
			break;
		case WEST:
			if (locked) g.drawImage(Wl, x, y, null);
			else g.drawImage(W, x, y, null);
			break;
		case NULL:
		default:
			break;
		}
	}

	@Override
	public void addLaser(Laser laser) {
		this.lasers.add(laser);
		// Note: don't assign the exiting direction to anything, keep it as NULL so the laser will stop here
	}

	public boolean isOn() {
		return on;
	}

	@Override
	public Tile copy(int x, int y) {
		return new ReceptorTile(x, y, direction.encode(), level);
	}
}

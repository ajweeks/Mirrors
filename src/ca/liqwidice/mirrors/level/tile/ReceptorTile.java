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

/**
 * Represents a goal, the player must get the laser (which is shot out of the PointerTile) to this tile to win
 * This particular ReceptorTile can only be activated by lasers entering from one direction, all others are ignored
 */
public class ReceptorTile extends Tile {
	private static final long serialVersionUID = 1L;

	public static BufferedImage N, E, S, W;

	static {
		try {
			N = ImageIO.read(new File("res/receptorN.png"));
			E = ImageIO.read(new File("res/receptorE.png"));
			S = ImageIO.read(new File("res/receptorS.png"));
			W = ImageIO.read(new File("res/receptorW.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean on; //LATER make this an integer, representing all inputs 
	private Direction direction; // The direction our receptor is facing LATER add different types of receptors (2-way, colour coded, all way, etc.)

	public ReceptorTile(int x, int y, int direction, Level level) {
		super(x, y, level);
		this.direction = Direction.decode(direction);
		this.on = false;
	}

	// LATER add lockable field to prevent the player from rotating this at certain times

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
		if (this.on) g.setColor(Color.YELLOW);
		else g.setColor(Color.WHITE);
		g.fillRect(x, y, WIDTH, WIDTH);

		switch (direction) {
		case NORTH:
			g.drawImage(N, x, y, null);
			break;
		case EAST:
			g.drawImage(E, x, y, null);
			break;
		case SOUTH:
			g.drawImage(S, x, y, null);
			break;
		case WEST:
			g.drawImage(W, x, y, null);
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
	public Tile copy() {
		return new ReceptorTile(x, y, direction.encode(), level);
	}
}

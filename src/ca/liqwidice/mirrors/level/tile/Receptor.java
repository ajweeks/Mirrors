package ca.liqwidice.mirrors.level.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import ca.liqwidice.mirrors.level.Direction;
import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.state.GameState;

public class Receptor implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Receptor NULL = new Receptor(Direction.NULL, Color.BLACK);

	public static BufferedImage[] images;

	static {
		try {
			images = new BufferedImage[] { //
			ImageIO.read(new File("res/receptors/N_WHITE.png")), // 0
					ImageIO.read(new File("res/receptors/E_WHITE.png")), //
					ImageIO.read(new File("res/receptors/S_WHITE.png")), //
					ImageIO.read(new File("res/receptors/W_WHITE.png")), //
					ImageIO.read(new File("res/receptors/N_RED.png")), // 4
					ImageIO.read(new File("res/receptors/E_RED.png")), //
					ImageIO.read(new File("res/receptors/S_RED.png")), //
					ImageIO.read(new File("res/receptors/W_RED.png")), //
					ImageIO.read(new File("res/receptors/N_GREEN.png")), // 8
					ImageIO.read(new File("res/receptors/E_GREEN.png")), //
					ImageIO.read(new File("res/receptors/S_GREEN.png")), //
					ImageIO.read(new File("res/receptors/W_GREEN.png")), //
					ImageIO.read(new File("res/receptors/N_BLUE.png")), // 12
					ImageIO.read(new File("res/receptors/E_BLUE.png")), //
					ImageIO.read(new File("res/receptors/S_BLUE.png")), //
					ImageIO.read(new File("res/receptors/W_BLUE.png")), //
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean on; // True if there is a laser shining into us
	private Direction direction; // The direction our receptor is facing
	private Color colour;
	private Laser laser = Laser.NULL; // The laser that is shining into us (Or Laser.NULL if there is none)

	public Receptor(Direction direction, Color colour) {
		this.direction = direction;
		this.colour = colour;
		this.on = false;
	}

	public void update(Direction direction, double delta) {
		if (this.direction == Direction.NULL) return;
		this.on = false;
		if (laser != Laser.NULL) {
			if (laser.getDirEntering() == this.direction.add(direction) && colourTurnsMeOn(laser.getColour())) {
				this.on = true;
			}
		}

		if (!this.on) {
			this.laser = Laser.NULL;
		}
	}

	public void render(Direction direction, int x, int y, Graphics g) {
		if (this.direction == Direction.NULL) return;

		g.drawImage(images[getColourIndex() * 4 + this.direction.add(direction).encode()], x, y, null);
		// don't render lasers, for now at least
	}

	public void setLaser(Laser laser) {
		if (this.direction == Direction.NULL) return;
		this.laser = laser;
		// Note: don't assign the exiting direction to anything, keep it as NULL so the laser will stop here
	}

	public void removeLaser() {
		if (this.direction == Direction.NULL) return;
		this.laser = Laser.NULL;
	}

	/** Returns whether or not Colour colour will turn this receptor on */
	private boolean colourTurnsMeOn(Color colour) {
		if (this.direction == Direction.NULL) return false;
		if (this.colour.equals(Color.WHITE)) return true;
		if (colour.equals(this.colour)) return true;
		return false;
	}

	private int getColourIndex() {
		if (colour.equals(Color.WHITE)) {
			return 0;
		} else if (colour.equals(Color.RED)) {
			return 1;
		} else if (colour.equals(Color.GREEN)) {
			return 2;
		} else if (colour.equals(Color.BLUE)) {
			return 3;
		} else {
			return 0;
		}

	}

	/** Returns whether or not  */
	public boolean setColour(Color colour) {
		if (this.direction == Direction.NULL) {
			if (GameState.levelEditingMode) { // there wasn't a receptor here before, but we want one now!
				return true;
			} else return false;
		}
		this.colour = colour;
		return false;
	}

	public Color getColour() {
		return colour;
	}

	public Laser getLaser() {
		return laser;
	}
	
	public boolean isOn() {
		return on;
	}

	public Direction getDirection() {
		return direction;
	}

}

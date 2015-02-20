package ca.liqwidice.mirrors.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import ca.liqwidice.mirrors.level.tile.Tile;

public class Laser implements Serializable {
	private static final long serialVersionUID = 1L;

	public static BufferedImage R, G, B, O;
	
	static {
		try {
			R = ImageIO.read(new File("res/laser_red.png"));
			G = ImageIO.read(new File("res/laser_green.png"));
			B = ImageIO.read(new File("res/laser_blue.png"));
			O = ImageIO.read(new File("res/laser_red.png")); // LATER add orange sprite
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static final Laser NULL = new Laser(Direction.NULL, Direction.NULL, Color.BLACK);

	private static int BEAM_WIDTH = 15;

	private Direction entering; // The side of the mirror that the laser is entering on (Should only be Direction.NULL on tiles that emit lasers (just pointers atm))
	private Direction exiting; // The side of the mirror that the laser is coming out of (Direction.NULL if it doesn't exit because it is being blocked or absorbed)

	private Color colour;

	public Laser(Direction entering, Direction exiting, Color colour) {
		this.entering = entering;
		this.exiting = exiting;
		this.colour = colour;
	}

	public Laser(Direction entering, Color colour) {
		this(entering, Direction.NULL, colour);
	}

	public void render(int x, int y, Graphics g) {
		int[] N = new int[] { x + Tile.WIDTH / 2 - BEAM_WIDTH / 2, y, BEAM_WIDTH, Tile.WIDTH / 2 + 1 };
		int[] E = new int[] { x + Tile.WIDTH / 2, y + Tile.WIDTH / 2 - BEAM_WIDTH / 2, Tile.WIDTH / 2 + 1, BEAM_WIDTH };
		int[] S = new int[] { x + Tile.WIDTH / 2 - BEAM_WIDTH / 2, y + Tile.WIDTH / 2, BEAM_WIDTH, Tile.WIDTH / 2 + 1 };
		int[] W = new int[] { x, y + Tile.WIDTH / 2 - BEAM_WIDTH / 2, Tile.WIDTH / 2 + 1, BEAM_WIDTH };

		// LATER render lasers using sprites
		
		g.setColor(colour);
		if (entering == Direction.NORTH || exiting == Direction.NORTH) g.fillRect(N[0], N[1], N[2], N[3]);
		if (entering == Direction.EAST || exiting == Direction.EAST) g.fillRect(E[0], E[1], E[2], E[3]);
		if (entering == Direction.SOUTH || exiting == Direction.SOUTH) g.fillRect(S[0], S[1], S[2], S[3]);
		if (entering == Direction.WEST || exiting == Direction.WEST) g.fillRect(W[0], W[1], W[2], W[3]);

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

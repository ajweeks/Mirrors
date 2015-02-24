package ca.liqwidice.mirrors.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Laser implements Serializable {
	private static final long serialVersionUID = 1L;

	public transient static BufferedImage[][] LASER_IMAGES;

	public transient static int RED_INDEX = 0, GREEN_INDEX = 1, BLUE_INDEX = 2;
	public transient static int N = 0, E = 1, S = 2, W = 3;

	static {
		try {
			LASER_IMAGES = new BufferedImage[][] {
					{ ImageIO.read(new File("res/lasers/laser_redN.png")),
							ImageIO.read(new File("res/lasers/laser_redE.png")),
							ImageIO.read(new File("res/lasers/laser_redS.png")),
							ImageIO.read(new File("res/lasers/laser_redW.png")) },
					{ ImageIO.read(new File("res/lasers/laser_greenN.png")),
							ImageIO.read(new File("res/lasers/laser_greenE.png")),
							ImageIO.read(new File("res/lasers/laser_greenS.png")),
							ImageIO.read(new File("res/lasers/laser_greenW.png")) },
					{ ImageIO.read(new File("res/lasers/laser_blueN.png")),
							ImageIO.read(new File("res/lasers/laser_blueE.png")),
							ImageIO.read(new File("res/lasers/laser_blueS.png")),
							ImageIO.read(new File("res/lasers/laser_blueW.png")) } };
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final Laser NULL = new Laser(Direction.NULL, Direction.NULL, Color.RED);

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

	public int index() {
		if (colour.equals(Color.RED)) return RED_INDEX;
		else if (colour.equals(Color.GREEN)) return GREEN_INDEX;
		else return BLUE_INDEX;
	}

	public void render(int x, int y, Graphics g) {
		if (entering == Direction.NORTH || exiting == Direction.NORTH)
			g.drawImage(LASER_IMAGES[index()][N], x, y, null);
		if (entering == Direction.EAST || exiting == Direction.EAST) g.drawImage(LASER_IMAGES[index()][E], x, y, null);
		if (entering == Direction.SOUTH || exiting == Direction.SOUTH)
			g.drawImage(LASER_IMAGES[index()][S], x, y, null);
		if (entering == Direction.WEST || exiting == Direction.WEST) g.drawImage(LASER_IMAGES[index()][W], x, y, null);

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

}

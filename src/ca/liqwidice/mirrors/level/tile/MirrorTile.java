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

public class MirrorTile extends Tile {
	private static final long serialVersionUID = 1L;

	public transient static BufferedImage NW, NE; // MIRRORS
	public transient static BufferedImage[] CORNER_LASERS;

	public transient static final int RED = 0, GREEN = 1, BLUE = 2;

	static {
		try {
			NW = ImageIO.read(new File("res/mirrorBS.png"));
			NE = ImageIO.read(new File("res/mirrorFS.png"));

			CORNER_LASERS = new BufferedImage[] { //
			ImageIO.read(new File("res/lasers/laser_redNW.png")), // 0
					ImageIO.read(new File("res/lasers/laser_redNE.png")), // 1
					ImageIO.read(new File("res/lasers/laser_redSE.png")), // 2
					ImageIO.read(new File("res/lasers/laser_redSW.png")), // 3

					ImageIO.read(new File("res/lasers/laser_greenNW.png")), // 4
					ImageIO.read(new File("res/lasers/laser_greenNE.png")), // 5
					ImageIO.read(new File("res/lasers/laser_greenSE.png")), // 6
					ImageIO.read(new File("res/lasers/laser_greenSW.png")), // 7

					ImageIO.read(new File("res/lasers/laser_blueNW.png")), // 8
					ImageIO.read(new File("res/lasers/laser_blueNE.png")), // 9
					ImageIO.read(new File("res/lasers/laser_blueSE.png")), // 10
					ImageIO.read(new File("res/lasers/laser_blueSW.png")) };// 11
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final int FS = 0; // the mirror is angled like a forward slash, from the SW corner to the NE (/)
	public static final int BS = 1; // the mirror is angled like a backward slash, from the NW corner to the SE (\)

	protected int direction;

	public MirrorTile(int x, int y, int direction, Level level) {
		super(x, y, MIRROR_ID, level);
		this.direction = direction;
	}

	@Override
	public void pollInput() {
		if (Mouse.leftClicked && Mouse.isInside(this)) {
			switchDirection();
		}
	}

	@Override
	public void render(int x, int y, Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, WIDTH, WIDTH);

		//				for (Laser l : lasers) {
		//					l.render(x, y, g);
		//				}

		if (direction == FS) g.drawImage(NE, x, y, null);
		else if (direction == BS) g.drawImage(NW, x, y, null);

		for (Laser l : lasers) {
			if (direction == FS) {
				if (l.getDirEntering() == Direction.EAST || l.getDirEntering() == Direction.SOUTH) {
					g.drawImage(CORNER_LASERS[l.getColourIndex() * 4 + 2], x, y, null);
				} else {
					g.drawImage(CORNER_LASERS[l.getColourIndex() * 4 + 0], x, y, null);
				}
			} else if (direction == BS) {
				if (l.getDirEntering() == Direction.EAST || l.getDirEntering() == Direction.NORTH) {
					g.drawImage(CORNER_LASERS[l.getColourIndex() * 4 + 1], x, y, null);
				} else {
					g.drawImage(CORNER_LASERS[l.getColourIndex() * 4 + 3], x, y, null);
				}
			}
		}

	}

	@Override
	public void addLaser(Laser laser) {
		this.lasers.add(laser);

		// determine the exiting direction
		Direction exiting = Direction.NULL;
		if (this.direction == FS) {
			if (laser.getDirEntering() == Direction.NORTH) exiting = Direction.WEST;
			else if (laser.getDirEntering() == Direction.WEST) exiting = Direction.NORTH;
			else if (laser.getDirEntering() == Direction.EAST) exiting = Direction.SOUTH;
			else if (laser.getDirEntering() == Direction.SOUTH) exiting = Direction.EAST;
		} else if (this.direction == BS) {
			if (laser.getDirEntering() == Direction.NORTH) exiting = Direction.EAST;
			else if (laser.getDirEntering() == Direction.EAST) exiting = Direction.NORTH;
			else if (laser.getDirEntering() == Direction.WEST) exiting = Direction.SOUTH;
			else if (laser.getDirEntering() == Direction.SOUTH) exiting = Direction.WEST;
		}
		this.lasers.get(lasers.size() - 1).setDirExiting(exiting);
	}

	public void switchDirection() {
		this.direction = (this.direction + 1) % 2; // alternate between 0 and 1
	}

	@Override
	public Tile copy(int x, int y) {
		return new MirrorTile(x, y, direction, level);
	}
}

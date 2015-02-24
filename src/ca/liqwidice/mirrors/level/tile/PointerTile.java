package ca.liqwidice.mirrors.level.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import ca.liqwidice.mirrors.input.Mouse;
import ca.liqwidice.mirrors.level.Direction;
import ca.liqwidice.mirrors.level.Laser;
import ca.liqwidice.mirrors.level.Level;
import ca.liqwidice.mirrors.state.GameState;

public class PointerTile extends Tile { //Symbolizes a laser pointer, a source of light
	private static final long serialVersionUID = 1L;

	public transient static BufferedImage N, E, S, W;

	static {
		try {
			N = ImageIO.read(new File("res/pointerN.png"));
			E = ImageIO.read(new File("res/pointerE.png"));
			S = ImageIO.read(new File("res/pointerS.png"));
			W = ImageIO.read(new File("res/pointerW.png"));

			// LATER  fix image loading when exporting to a jar
			//			N = ImageIO.read(new File(PointerTile.class.getResource("res/pointerN.png").toURI()));
			//			E = ImageIO.read(new File(PointerTile.class.getResource("res/pointerE.png").toURI()));
			//			S = ImageIO.read(new File(PointerTile.class.getResource("res/pointerS.png").toURI()));
			//			W = ImageIO.read(new File(PointerTile.class.getResource("res/pointerW.png").toURI()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Direction direction = Direction.NORTH;
	private boolean on;
	private Color colour;

	public PointerTile(int x, int y, int direction, Color colour, Level level) {
		super(x, y, POINTER_ID, level);
		this.colour = colour;
		this.lasers = new ArrayList<>();
		this.on = true;
		this.direction = Direction.decode(direction);
		this.lasers.add(new Laser(Direction.NULL, this.direction, this.colour));
	}

	@Override
	public void pollInput() {
		if (Mouse.leftClicked && Mouse.isInside(this)) {
			this.on = !this.on;
			if (this.on) {
				if (GameState.levelEditingMode) this.colour = nextColour(); // cycle colours every time on is toggled
				this.lasers.add(new Laser(Direction.NULL, direction, colour));
			} else removeAllLasers();
		}

		if (Mouse.rightClicked && Mouse.isInside(this)) {
			this.direction = this.direction.cw();
			if (this.lasers.size() > 0) this.lasers.get(0).setDirExiting(this.direction);
		}
	}

	private Color nextColour() {
		if (this.colour.equals(Color.RED)) return Color.GREEN;
		else if (this.colour.equals(Color.GREEN)) return Color.BLUE;
		else if (this.colour.equals(Color.BLUE)) return Color.ORANGE;
		if (this.colour.equals(Color.ORANGE)) return Color.RED;
		else return Color.red;
	}

	@Override
	public void update(double delta) {
		if (on) {
			if (lasers.size() == 0) this.lasers.add(new Laser(Direction.NULL, direction, colour));
		} else return;// If we aren't shining light, there's no reason to update anything

		// Start the updating chain
		int[] checkedTiles = new int[level.height * level.width]; // 0 = not checked, 1 = checked once, 2 = checked twice (max)
		Direction nextDir = direction; // the direction towards the next tile (which is initially our direction)
		int xx = x + nextDir.offset[0];
		int yy = y + nextDir.offset[1];

		do {
			if (xx < 0 || xx >= level.width || yy < 0 || yy >= level.height) break; // The next direction is leading us into the wall
			if (checkedTiles[xx + yy * level.width] >= 2) return; // we've already checked this tile twice (this line avoids infinite loops)
			else checkedTiles[xx + yy * level.width]++;

			Tile nextTile = level.getTile(xx, yy); // get the tile to be updated
			if (nextTile == null) break; // we hit a wall

			if (nextTile instanceof PointerTile) { // Add all other opaque tiles here
				break;
			}
			// Find the next direction *after* setting the new laser object
			nextTile.addLaser(new Laser(nextDir.opposite(), colour));
			nextDir = nextTile.lasers.get(nextTile.lasers.size() - 1).getDirExiting();

			xx = nextTile.getX() + nextDir.offset[0];
			yy = nextTile.getY() + nextDir.offset[1];

		} while (nextDir != Direction.NULL);

	}

	@Override
	public void render(int x, int y, Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, WIDTH, WIDTH);

		for (Laser l : lasers) {
			l.render(x, y, g);
		}

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
		default:
			break;
		}
	}

	@Override
	public void addLaser(Laser laser) {

	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public Tile copy(int x, int y) {
		return new PointerTile(x, y, direction.encode(), colour, level);
	}

}

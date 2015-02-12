package ca.liqwidice.mirrors.input;

import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import ca.liqwidice.mirrors.level.Level;
import ca.liqwidice.mirrors.level.tile.Tile;

public class Mouse implements MouseMotionListener, MouseListener {

	public static boolean leftDown, rightDown, leftClicked, rightClicked;
	public static int x, y;

	public Mouse(Canvas canvas) {
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
	}

	//@formatter:off
	public static boolean isInside(Tile t) {
		return  x > Level.xo + t.getX() * Tile.WIDTH && 
				x < Level.xo + t.getX() * Tile.WIDTH + Tile.WIDTH && 
				y > Level.yo + t.getY() * Tile.WIDTH && 
				y < Level.yo + t.getY() * Tile.WIDTH + Tile.WIDTH;
	}
	//@formatter:on

	public static void releaseAll() {
		leftDown = false;
		leftClicked = false;
		rightDown = false;
		rightClicked = false;
	}

	public static void update() {
		leftClicked = false;
		rightClicked = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (!leftDown) leftClicked = true;
			else leftClicked = false;
			leftDown = true;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			if (!rightDown) rightClicked = true;
			else rightClicked = false;
			rightDown = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			leftClicked = false;
			leftDown = false;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			rightClicked = false;
			rightDown = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousePressed(e); //register the click
		mouseMoved(e); //register the movement
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}

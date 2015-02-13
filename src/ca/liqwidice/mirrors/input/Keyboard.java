package ca.liqwidice.mirrors.input;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	public static boolean esc; // is true if the ESC key is down this frame and wasn't down last frame
	private static boolean ctrl;
	public static boolean ctrl_w;

	public Keyboard(Canvas canvas) {
		canvas.addKeyListener(this);
	}

	public static void update() {
		esc = false;
		ctrl_w = false;
	}

	private void keyChanged(KeyEvent event, boolean down) {
		switch (event.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			esc = down;
			break;
		case KeyEvent.VK_CONTROL:
			ctrl = down;
			break;
		case KeyEvent.VK_W:
			ctrl_w = down && ctrl;
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyChanged(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyChanged(e, false);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

}

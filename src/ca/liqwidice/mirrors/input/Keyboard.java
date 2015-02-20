package ca.liqwidice.mirrors.input;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	public static boolean esc;
	private static boolean ctrl;
	public static boolean ctrl_w;
	public static boolean ctrl_e;
	public static boolean ctrl_e_tt;

	public Keyboard(Canvas canvas) {
		canvas.addKeyListener(this);
	}

	public static void update() {
		if (ctrl_e_tt) ctrl_e = !ctrl_e;
		ctrl_e_tt = false;
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
		case KeyEvent.VK_E:
			ctrl_e_tt = down && ctrl;
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

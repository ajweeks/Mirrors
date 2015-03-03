package ca.liqwidice.mirrors;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import ca.liqwidice.mirrors.input.Keyboard;
import ca.liqwidice.mirrors.input.Mouse;
import ca.liqwidice.mirrors.state.BasicState;
import ca.liqwidice.mirrors.state.MainMenuState;
import ca.liqwidice.mirrors.state.StateManager;

public class Game extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final Dimension SIZE = new Dimension(790, 512);
	public static final String TITLE = "Mirrors";
	public static final String VERSION = "0.1.2b";

	public static final Font font16 = new Font("consolas", Font.BOLD, 16);
	public static final Font font32 = font16.deriveFont(32.0f);

	private StateManager sm;
	private Canvas canvas;

	private int fps = 0;
	private int frames = 0;

	private boolean running = false;

	public Game() {
		super(TITLE);

		canvas = new Canvas();
		canvas.setSize(SIZE);
		canvas.setFocusable(true);

		//initialize input classes
		new Keyboard(canvas);
		new Mouse(canvas);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				stopGame();
			}

			public void windowOpened(WindowEvent e) {}

			public void windowIconified(WindowEvent e) {}

			public void windowDeiconified(WindowEvent e) {}

			public void windowDeactivated(WindowEvent e) {}

			public void windowClosed(WindowEvent e) {}

			public void windowActivated(WindowEvent e) {}
		});
		setSize(WIDTH, HEIGHT);
		add(canvas);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setFocusable(true);
		setVisible(true);
		canvas.requestFocus();

		sm = new StateManager(new MainMenuState(this));

		running = true;
	}

	public static void main(String[] args) {
		new Thread(new Game()).start();
	}

	private void update(double delta) {
		if (Keyboard.ctrl_w) stopGame();
		if (Keyboard.esc) enterPreviousState();
		sm.update(delta);
		Mouse.update();
		Keyboard.update();
	}

	private void render() {
		BufferStrategy buffer = canvas.getBufferStrategy();
		if (buffer == null) {
			canvas.createBufferStrategy(2);
			return;
		}
		Graphics g = buffer.getDrawGraphics();

		//Clear the screen
		g.setColor(Colours.OFF_BLACK);
		g.fillRect(0, 0, SIZE.width, SIZE.height);

		sm.render(g);

		g.dispose();
		buffer.show();
	}

	@Override
	public void run() {
		long before = System.nanoTime();
		double delta = 0.0d;
		double elapsed = 0.0d;
		while (running) {
			long now = System.nanoTime();
			delta = now - before;
			elapsed += delta;
			before = now;

			if (elapsed > 1_000_000_000) {
				elapsed -= 1_000_000_000;
				fps = frames;
				frames = 0;
				setTitle(TITLE + "  |  " + fps + "fps");
			}

			update(delta);
			frames++;
			render();

			try {
				//LATER make a better time loop
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}

	public void enterGameState(BasicState state) {
		sm.enterState(state);
	}

	public void enterPreviousState() {
		sm.enterPreviousState();
	}

	public BasicState getCurrentState() {
		return sm.currentState();
	}

	public BasicState getPreviousState() {
		return sm.getPreviousState();
	}

	public synchronized void stopGame() {
		this.running = false;
		sm.destroy();
		this.setVisible(false);
		System.exit(0);
	}
}

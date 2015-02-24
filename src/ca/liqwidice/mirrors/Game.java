package ca.liqwidice.mirrors;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import ca.liqwidice.mirrors.input.Keyboard;
import ca.liqwidice.mirrors.input.Mouse;
import ca.liqwidice.mirrors.state.BasicState;
import ca.liqwidice.mirrors.state.MainMenuState;
import ca.liqwidice.mirrors.state.StateManager;

public class Game extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	public static boolean debug = false;

	public static final Dimension SIZE = new Dimension(790, 512);
	public static final String TITLE = "Mirrors";
	public static final String VERSION = "0.0.2a";

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

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		g.setColor(Color.BLACK);
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
		stopGame();
		System.exit(0);
	}

	public void enterGameState(BasicState state) {
		sm.enterState(state);
	}

	public void enterPreviousState() {
		sm.enterPreviousState();
	}

	public synchronized void stopGame() {
		sm.destroy(); //call destroy on all states
		this.running = false;
	}
}

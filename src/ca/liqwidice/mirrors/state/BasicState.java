package ca.liqwidice.mirrors.state;

import java.awt.Graphics;

import ca.liqwidice.mirrors.Game;

public abstract class BasicState {

	protected static Game game;

	public BasicState(Game game) {
		BasicState.game = game;
	}

	public abstract void init();

	public abstract void update(double delta);

	public abstract void render(Graphics g);

	public abstract int getID();

	public abstract void destroy();

}

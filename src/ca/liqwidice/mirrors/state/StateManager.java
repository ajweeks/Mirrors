package ca.liqwidice.mirrors.state;

import java.awt.Graphics;
import java.util.ArrayList;

import ca.liqwidice.mirrors.input.Keyboard;
import ca.liqwidice.mirrors.input.Mouse;
import ca.liqwidice.mirrors.utils.Sound;

public class StateManager {

	public static final int MAIN_MENU_STATE_ID = 0;
	public static final int GAME_STATE_ID = 1;
	public static final int CONFIRM_QUIT_STATE_ID = 2;
	public static final int MENU_STATE_ID = 3;
	public static final int LEVEL_SELECT_STATE_ID = 4;

	private ArrayList<BasicState> states;

	public StateManager(BasicState startingState) {
		states = new ArrayList<>();
		states.add(startingState);
		states.get(0).init();
	}

	public void update(double delta) {
		states.get(states.size() - 1).update(delta);
	}

	public void render(Graphics g) {
		states.get(states.size() - 1).render(g);
	}

	public BasicState currentState() {
		return states.get(states.size() - 1);
	}

	public void enterState(BasicState state) {
		states.add(state);
		states.get(states.size() - 1).init();
		Sound.SELECT.play();
		Mouse.releaseAll();
	}

	public void enterPreviousState() {
		if (states.size() > 1) {
			Sound.SELECT.play();
			states.get(states.size() - 1).destroy();
			states.remove(states.size() - 1);
		}
		// Release all inputs
		Keyboard.releaseAll();
		Mouse.releaseAll();
	}

	public void destroy() { //To be called just before the game is closed (mostly to deal with open Threads)
		for (int i = 0; i < states.size(); i++) {
			states.get(i).destroy();
		}
	}

}

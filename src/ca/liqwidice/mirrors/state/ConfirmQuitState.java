package ca.liqwidice.mirrors.state;

import java.awt.Graphics;

import ca.liqwidice.mirrors.Game;
import ca.liqwidice.mirrors.button.Button;
import ca.liqwidice.mirrors.button.ButtonManager;

public class ConfirmQuitState extends MenuState {

	private static final String BTN_QUIT = "Quit";
	private static final String BTN_CANCEL = "Cancel";

	private ButtonManager manager;

	public ConfirmQuitState(Game game) {
		super(game);
		manager = new ButtonManager();

		manager.addButton(new Button(200, 300, 200, 100, BTN_QUIT));
		manager.addButton(new Button(200, 450, 200, 100, BTN_CANCEL));
	}

	@Override
	public void update(double delta) {

		manager.updateAll(delta);
		if (manager.getButton(BTN_QUIT).clicked) {
			game.stopGame();
		}
	}

	@Override
	public void render(Graphics g) {

		manager.renderAll(g);
	}

	public int getID() {
		return StateManager.CONFIRM_QUIT_STATE_ID;
	}
}

package ca.liqwidice.mirrors.state;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.Game;
import ca.liqwidice.mirrors.button.Button;
import ca.liqwidice.mirrors.button.ButtonManager;
import ca.liqwidice.mirrors.input.Keyboard;
import ca.liqwidice.mirrors.level.Level;
import ca.liqwidice.mirrors.utils.Sound;

public class LevelSelectState extends BasicState {

	public static final String BTN_BACK = "Back";
	public static final String[] LEVELS_LABELS = { "", "Level one", "Level two", "Level three", "Level four",
			"Level five", "Level six" };

	public LevelSelectState(Game game) {
		super(game);
	}

	private ButtonManager manager;

	@Override
	public void init() {
		manager = new ButtonManager();
		manager.addButton(new Button(160, 40, 100, 40, BTN_BACK));
		for(int i = 1; i <= 6; i++) {
			manager.addButton(new Button(160, 90 + i * 50, 100, 40, LEVELS_LABELS[i], true, new Color(25, 35, 150), new Color(35, 45, 180), new Color(0,0,0)));
		}
	}

	@Override
	public void update(double delta) {
		if (Keyboard.esc) {
			Sound.SELECT.play();
			game.enterPreviousState();
		}

		manager.updateAll(delta);

		if (manager.getButton(BTN_BACK).clicked) {
			game.enterPreviousState();
		}

		for (int i = 1; i < LEVELS_LABELS.length; i++) {
			if (manager.getButton(LEVELS_LABELS[i]).clicked) {
				game.enterGameState(new GameState(game, new Level(i)));
			}
		}
	}

	/** Check for mouse inside of level button and enter correct level if clicked, otherwise set hover to true */
	@Override
	public void render(Graphics g) {
		manager.renderAll(g);
	}

	@Override
	public int getID() {
		return StateManager.LEVEL_SELECT_STATE_ID;
	}

	@Override
	public void destroy() {

	}
}

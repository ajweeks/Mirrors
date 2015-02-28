package ca.liqwidice.mirrors.state;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.Colours;
import ca.liqwidice.mirrors.Game;
import ca.liqwidice.mirrors.button.Button;
import ca.liqwidice.mirrors.button.ButtonManager;
import ca.liqwidice.mirrors.button.LevelButton;
import ca.liqwidice.mirrors.level.Levels;

public class LevelSelectState extends BasicState {

	public static final String BTN_BACK = "Back";

	public LevelSelectState(Game game) {
		super(game);
	}

	private static ButtonManager manager;

	@Override
	public void init() {
		manager = new ButtonManager();
		manager.addButton(new Button(160, 40, 100, 40, BTN_BACK));
		for (int i = 0; i < 20; i++) {
			manager.addButton(new LevelButton(160 + (i / 7) * 110, 115 + i * 50 + (i / 7) * -(7 * 50), 100, 40,
					"Level " + (i + 1), Levels.load(i + 1), Colours.uncompleted_level, Colours.completed_level,
					Color.WHITE, i < 7));
		}
	}

	@Override
	public void update(double delta) {
		manager.updateAll(delta);

		if (manager.getButton(BTN_BACK).clicked) {
			game.enterPreviousState();
		}

		for (int i = 1; i < manager.getSize(); i++) {
			if (manager.getButton("Level " + i).clicked) {
				game.enterGameState(new GameState(game, ((LevelButton) manager.getButton("Level " + i)).getLevel()));
			}
		}
	}

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

	/** Returns null if there is no level found, otherwise a new GameState object that has the Level level */
	public static GameState getGameState(int level) {
		if (manager.getButton("Level " + level) == Button.NULL) return null;
		return new GameState(game, ((LevelButton) manager.getButton("Level " + level)).getLevel());
	}

}

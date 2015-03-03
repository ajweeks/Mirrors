package ca.liqwidice.mirrors.state;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.Colours;
import ca.liqwidice.mirrors.Game;
import ca.liqwidice.mirrors.button.Button;
import ca.liqwidice.mirrors.button.ButtonManager;
import ca.liqwidice.mirrors.button.LevelButton;
import ca.liqwidice.mirrors.level.Level;
import ca.liqwidice.mirrors.level.Levels;
import ca.liqwidice.mirrors.utils.Sound;

public class LevelSelectState extends BasicState {

	public static final int NUM_OF_LEVELS = 21;
	public static final String BACK = "Back";
	public static final String RESET_ALL = "Reset All";

	public LevelSelectState(Game game) {
		super(game);
	}

	private static ButtonManager manager;
	private int highestLevelUnlocked = 7;

	@Override
	public void init() {
		manager = new ButtonManager();
		manager.addButton(new Button(160, 40, 100, 40, BACK));
		manager.addButton(new Button(Game.SIZE.width - 120, 40, 100, 40, RESET_ALL));
		for (int i = 1; i <= NUM_OF_LEVELS; i++) {
			// LATER possibly move the level loading into a per-level system to reduce lag when entering this state
			manager.addButton(new LevelButton(160 + (i - 1) / 7 * 110, 70 + i * 50 + (i - 1) / 7 * -(7 * 50), 100, 40,
					"Level " + i, Levels.load(i, false), Colours.uncompleted_level, Colours.completed_level,
					Color.WHITE, i <= highestLevelUnlocked));
		}
	}

	@Override
	public void update(double delta) {
		manager.updateAll(delta);

		for (int i = 1; i <= highestLevelUnlocked; i++) {
			LevelButton b = (LevelButton) manager.getButton("Level " + i);

			b.enabled = true;
			if (i == highestLevelUnlocked && b.getLevel().isCompletedEver()) {
				for (int j = i; j <= Math.min(i + 7, NUM_OF_LEVELS); j++) {
					manager.getButton("Level " + j).enabled = true;
				}
				highestLevelUnlocked += 7;
				if (highestLevelUnlocked > NUM_OF_LEVELS) highestLevelUnlocked = NUM_OF_LEVELS;
			} else {
				if (b.getLevel().isCompletedEver() == false) break;
			}
		}

		if (manager.getButton(BACK).clicked) {
			game.enterPreviousState();
			Sound.SELECT.play();
		} else if (manager.getButton(RESET_ALL).clicked) {
			resetAll();
			Sound.SELECT.play();
		}

		for (int i = 1; i <= NUM_OF_LEVELS; i++) { // LATER check if this works for last lvl
			if (manager.getButton("Level " + i).clicked) {
				game.enterGameState(getGameState(i));
				Sound.SELECT.play();
			}
		}
	}

	public void setCompleted(int level) {
		((LevelButton) manager.getButton("Level " + level)).getLevel().setCompletedEver(true);
	}

	public void resetAll() {
		highestLevelUnlocked = 7;
		for (int i = 1; i <= NUM_OF_LEVELS; i++) {
			LevelButton btn = (LevelButton) manager.getButton("Level " + i);
			btn.getLevel().copy(Levels.load(i, true));
			btn.getLevel().setCompletedEver(false);
			Levels.save(btn.getLevel(), false);
			btn.enabled = i <= highestLevelUnlocked;
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
	public synchronized void destroy() {

	}

	/** Returns null if there is no level found, otherwise a new GameState object that has the Level level */
	public GameState getGameState(int level) {
		if (manager.getButton("Level " + level) == Button.NULL) return null;
		if (level > highestLevelUnlocked) return null;
		Level lvl = ((LevelButton) manager.getButton("Level " + level)).getLevel();
		//		lvl.copy(Levels.load(level));
		return new GameState(game, lvl);
	}

}

package ca.liqwidice.mirrors.state;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.Game;
import ca.liqwidice.mirrors.button.Button;
import ca.liqwidice.mirrors.button.ButtonManager;
import ca.liqwidice.mirrors.utils.Sound;

public class MainMenuState extends BasicState {

	public static final String BTN_LEVEL_SELECT = "Level Select";
	public static final String BTN_QUIT = "Quit";
	public static final String BTN_LOUDER = "Louder";
	public static final String BTN_QUIETER = "Quieter";

	private ButtonManager manager;

	public MainMenuState(Game game) {
		super(game);
	}

	@Override
	public void init() {
		manager = new ButtonManager();
		manager.addButton(new Button(362, 190, 134, 48, BTN_LEVEL_SELECT));
		manager.addButton(new Button(675, 17, 80, 40, BTN_QUIETER));
		manager.addButton(new Button(760, 17, 80, 40, BTN_LOUDER));
		manager.addButton(new Button(389, 270, 80, 40, BTN_QUIT));
	}

	@Override
	public void update(double delta) {
		manager.updateAll(delta);

		if (Sound.volume == Sound.MIN_VOLUME) {
			manager.getButton(BTN_QUIETER).enabled = false;
		} else {
			manager.getButton(BTN_QUIETER).enabled = true;
		}
		if (Sound.volume == Sound.MAX_VOLUME) { // Not necessary, but prevents a sound from being played if we are already at max vol 
			manager.getButton(BTN_LOUDER).enabled = false;
		} else {
			manager.getButton(BTN_LOUDER).enabled = true;
		}
		if (manager.getButton(BTN_LEVEL_SELECT).clicked) {
			Sound.SELECT.play();
			game.enterGameState(new LevelSelectState(game));
		} else if (manager.getButton(BTN_LOUDER).clicked) {
			Sound.louder();
			Sound.SELECT.play();
		} else if (manager.getButton(BTN_QUIETER).clicked) {
			Sound.quieter();
			Sound.SELECT.play();
		} else if (manager.getButton(BTN_QUIT).clicked) {
			game.stopGame();
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		if (Sound.SELECT.available) g.drawString("Volume: " + Sound.getVolPercent() + "%", 715, 76);
		else g.drawString("Couldn't load sounds! :(", 705, 76);
		// LATER add a cool-looking volume slider to replace boring text percentages

		manager.renderAll(g);
	}

	@Override
	public int getID() {
		return StateManager.MAIN_MENU_STATE_ID;
	}

	@Override
	public void destroy() {

	}
}

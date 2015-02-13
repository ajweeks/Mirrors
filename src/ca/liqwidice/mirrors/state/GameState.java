package ca.liqwidice.mirrors.state;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.Colours;
import ca.liqwidice.mirrors.Game;
import ca.liqwidice.mirrors.button.Button;
import ca.liqwidice.mirrors.button.ButtonManager;
import ca.liqwidice.mirrors.level.Level;
import ca.liqwidice.mirrors.utils.Sound;

public class GameState extends BasicState {

	private final String MAINMENU = "Main Menu";
	private final String LEVEL_SELECT = "Level Select";
	private final String RESET = "Reset";
	private final String QUIT = "Quit";

	private ButtonManager manager;
	private Level level;

	public GameState(Game game, Level level) {
		super(game);
		this.level = level;
	}

	@Override
	public void init() {
		manager = new ButtonManager();
		manager.addButton(new Button(10, 35, 125, 40, MAINMENU));
		manager.addButton(new Button(10, 90, 135, 40, LEVEL_SELECT));
		manager.addButton(new Button(10, 145, 80, 40, RESET));
		manager.addButton(new Button(10, 200, 65, 40, QUIT));
	}

	@Override
	public void update(double delta) {
		// Update game board
		level.update(delta);

		// Update all buttons
		manager.updateAll(delta);

		// Act on button presses
		if (manager.getButton(MAINMENU).clicked) {
			game.enterPreviousState();
			game.enterPreviousState(); // Not totally failproof, but should work if we don't overhaul anything ... 
		} else if (manager.getButton(LEVEL_SELECT).clicked) {
			game.enterPreviousState();
		} else if (manager.getButton(RESET).clicked) {
			Sound.SELECT.play();
			level.reset();
		} else if (manager.getButton(QUIT).clicked) {
			game.stopGame();
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Colours.sidebar);
		g.fillRect(0, 0, 150, Game.SIZE.height);

		g.setColor(Color.MAGENTA);
		g.fillRect(0, 0, Level.xo, 30);
		
		g.setColor(Color.WHITE);
		g.setFont(Game.font32);
		g.drawString("Level " + level.level, 10, 25);
		
		manager.renderAll(g);

		level.render(g);
	}

	@Override
	public int getID() {
		return StateManager.GAME_STATE_ID;
	}

	@Override
	public void destroy() {

	}

}

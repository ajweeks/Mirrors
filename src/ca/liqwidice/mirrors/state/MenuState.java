package ca.liqwidice.mirrors.state;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.Game;
import ca.liqwidice.mirrors.button.Button;
import ca.liqwidice.mirrors.button.ButtonManager;

public class MenuState extends BasicState {

	public static final String BTN_RESUME = "Resume";
	public static final String BTN_LEVEL_SELECT = "Level Select";
	public static final String BTN_QUIT = "Quit";

	ButtonManager manager;

	public MenuState(Game game) {
		super(game);
	}

	@Override
	public void init() {
		manager = new ButtonManager();
		manager.addButton(new Button(200, 100, 200, 100, BTN_RESUME));
		manager.addButton(new Button(200, 250, 200, 100, BTN_LEVEL_SELECT));
		manager.addButton(new Button(200, 400, 200, 100, BTN_QUIT));

	}

	@Override
	public void update(double delta) {

		manager.updateAll(delta);
	}

	@Override
	public void render(Graphics g) {
		g.setFont(Game.font16);

		g.setColor(Color.darkGray);
		g.drawString("Menu", 411, 142);
		g.setColor(Color.white);
		g.drawString("Menu", 409, 140);

		manager.renderAll(g);
	}

	@Override
	public int getID() {
		return StateManager.MENU_STATE_ID;
	}

	@Override
	public void destroy() {

	}

}

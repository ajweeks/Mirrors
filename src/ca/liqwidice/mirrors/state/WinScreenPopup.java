package ca.liqwidice.mirrors.state;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.button.Button;
import ca.liqwidice.mirrors.button.ButtonManager;

public class WinScreenPopup {

	public static final String PREV = "Previous";
	public static final String NEXT = "Next";
	public static final String RETRY = "Retry";
	public static final String EXIT = "Exit";

	private ButtonManager manager;

	private int x, y, w, h;

	public WinScreenPopup(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		manager = new ButtonManager();
		manager.addButton(new Button(x + 20, y + h / 2 - 32, 140, 65, PREV));
		manager.addButton(new Button(x + w - 20 - 140, y + h / 2 - 32, 140, 65, NEXT));
		manager.addButton(new Button(x + w / 2 - 40, y + 3 * h / 4 - 15, 80, 30, RETRY));
		manager.addButton(new Button(x + w / 2 - 40, y + 1 * h / 4 - 15, 80, 30, EXIT));
	}

	public String update(double delta) {
		manager.updateAll(delta);

		if (manager.getButton(PREV).clicked) return PREV;
		else if (manager.getButton(NEXT).clicked) return NEXT;
		else if (manager.getButton(RETRY).clicked) return RETRY;
		else if (manager.getButton(EXIT).clicked) return EXIT;

		return "";
	}

	public void render(Graphics g) {
		g.setColor(new Color(140, 40, 80, 225));
		g.fillRect(x, y, w, h);

		g.setColor(Color.WHITE);
		g.drawString("Level Complete!", x + w / 2 - 65, y + 15);

		manager.renderAll(g);
	}

}

package ca.liqwidice.mirrors.button;

import java.awt.Graphics;
import java.util.ArrayList;

public class ButtonManager {
	ArrayList<Button> buttons;

	public ButtonManager() {
		buttons = new ArrayList<>();
	}

	public void addButton(Button b) {
		buttons.add(b);
	}

	public void updateAll(double delta) {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).update(delta);
		}
	}

	public void renderAll(Graphics g) {
		for (Button b : buttons) {
			b.render(g);
		}
	}

	/** Returns the button with the text field text, or Button.NULL if there are no buttons with that text */
	public Button getButton(String text) {
		for (int i = 0; i < buttons.size(); i++) {
			if (buttons.get(i).text.equals(text)) return buttons.get(i);
		}
		return Button.NULL;
	}

	/** Returns the number of buttons this manager is in control of */
	public int getSize() {
		return buttons.size();
	}

}

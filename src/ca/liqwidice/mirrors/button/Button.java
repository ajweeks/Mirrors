package ca.liqwidice.mirrors.button;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.Colours;
import ca.liqwidice.mirrors.Game;
import ca.liqwidice.mirrors.input.Mouse;

/** Not actually a button class, just a place to hold a square's x, y, width, height, & hover */
public class Button {
	public static final Button NULL = new Button(0, 0, 0, 0, "null", true); //used instead of the null keyword, to be safe

	public int x, y, width, height;
	public boolean hover, clicked, enabled, visible;
	public boolean drawText;
	public Color bgCol, hovCol, textCol;
	public String text;

	public Button(int x, int y, int width, int height, String text, boolean drawText, Color bgCol, Color textCol) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.drawText = drawText;
		this.hover = false;
		this.enabled = true;
		this.visible = true;
		this.bgCol = bgCol;
		this.hovCol = bgCol.darker();
		this.textCol = textCol;
	}

	public Button(int x, int y, int width, int height, String text, boolean drawText) {
		this(x, y, width, height, text, drawText, Colours.purple, Color.WHITE);
	}

	public Button(int x, int y, int width, int height, String text) {
		this(x, y, width, height, text, true);
	}

	public void setColours(Color bgCol, Color textCol) {
		this.bgCol = bgCol;
		this.hovCol = bgCol.darker();
		this.textCol = textCol;
	}

	public void render(Graphics g, Color colour, Color textCol) {
		if (visible == false) return;
		g.setColor(colour);
		g.fillRect(x, y, width, height);
		if (drawText) {
			g.setColor(textCol);
			g.setFont(Game.font16);
			int x = (int) (this.x + width / 2 - g.getFontMetrics().getStringBounds(text, g).getWidth() / 2);
			int y = this.y + height / 2 - g.getFontMetrics().getHeight() / 2;
			g.drawString(text, x, y + 15);
		}
	}

	public void render(Graphics g) {
		Color colour;
		if (enabled == false) colour = Colours.disabledButton;
		else if (hover) colour = hovCol;
		else colour = bgCol;

		render(g, colour, textCol);
	}

	public void update(double delta) {
		if (enabled == false || visible == false) return;
		if (Mouse.x > x && Mouse.y > y && Mouse.x < x + width && Mouse.y < y + height) {
			hover = true;
			clicked = Mouse.leftClicked;
		} else {
			hover = false;
			clicked = false;
		}
	}

	public void SetDrawText(boolean drawText) {
		this.drawText = drawText;
	}
}

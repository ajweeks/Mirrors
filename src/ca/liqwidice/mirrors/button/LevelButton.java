package ca.liqwidice.mirrors.button;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.Colours;
import ca.liqwidice.mirrors.Game;
import ca.liqwidice.mirrors.level.Level;

public class LevelButton extends Button {

	private Level level;

	Color uncompleted, completed;

	public LevelButton(int x, int y, int width, int height, String text, Level level, Color uncompleted,
			Color completed, Color textCol, boolean unlocked) {
		super(x, y, width, height, text, true, level.isCompleted() ? completed : uncompleted, textCol);
		this.level = level;
		this.uncompleted = uncompleted;
		this.completed = completed;
		this.enabled = unlocked;
	}

	@Override
	public void update(double delta) {
		super.update(delta);
		if (level.isCompleted()) this.bgCol = completed;
		else this.bgCol = uncompleted;
	}

	@Override
	public void render(Graphics g) {
		if (enabled == false) g.setColor(Colours.disabledButton);
		else if (level.isCompleted()) {
			if (hover) g.setColor(this.completed.darker());
			else g.setColor(this.completed);
		} else {
			if (hover) g.setColor(this.uncompleted.darker());
			else g.setColor(this.uncompleted);
		}

		g.fillRect(x, y, width, height);

		if (drawText) {
			g.setColor(textCol);
			g.setFont(Game.font16);
			int x = (int) (this.x + width / 2 - g.getFontMetrics().getStringBounds(text, g).getWidth() / 2);
			int y = this.y + height / 2 - g.getFontMetrics().getHeight() / 2;
			g.drawString(text, x, y + 15);
		}
	}

	public Level getLevel() {
		return level;
	}
}

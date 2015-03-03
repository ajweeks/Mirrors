package ca.liqwidice.mirrors;

import java.awt.Color;

public class Colours {
	public static final Color RED = Color.RED;
	public static final Color GREEN = Color.GREEN;
	public static final Color BLUE = Color.BLUE;
	public static final Color WHITE = Color.WHITE;
	public static final Color OFF_BLACK = new Color(11,11,11);

	public static Color purple = new Color(125, 22, 97);
	public static Color sidebar = new Color(70, 21, 50);
	public static Color disabledButton = new Color(65, 65, 65);

	public static Color uncompleted_level = new Color(180, 41, 7);
	public static Color completed_level = new Color(19, 122, 7);

	/** Cycle through the available laser colours (R->G->B->W) */
	public static Color nextColour(Color colour, boolean useWhite) {
		if (colour.equals(RED)) return GREEN;
		else if (colour.equals(GREEN)) return BLUE;
		else if (colour.equals(BLUE)) {
			if (useWhite) return WHITE;
			else return RED;
		} else if (colour.equals(WHITE)) return RED;
		else return RED;
	}
}

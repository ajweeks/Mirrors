package ca.liqwidice.mirrors.button;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageButton extends Button {

	public static BufferedImage louder;
	public static BufferedImage quieter;

	static {
		try {
			louder = ImageIO.read(new File("res/louder.png"));
			quieter = ImageIO.read(new File("res/quieter.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	BufferedImage image;

	public ImageButton(int x, int y, int width, int height, String text, BufferedImage image) {
		super(x, y, width, height, text);
		this.image = image;
	}

	@Override
	public void render(Graphics g) {
		if (enabled && visible) g.drawImage(image, x, y, null);
	}
}

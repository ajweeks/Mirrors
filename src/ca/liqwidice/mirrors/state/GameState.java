package ca.liqwidice.mirrors.state;

import java.awt.Color;
import java.awt.Graphics;

import ca.liqwidice.mirrors.Colours;
import ca.liqwidice.mirrors.Game;
import ca.liqwidice.mirrors.button.Button;
import ca.liqwidice.mirrors.button.ButtonManager;
import ca.liqwidice.mirrors.input.Keyboard;
import ca.liqwidice.mirrors.input.Mouse;
import ca.liqwidice.mirrors.level.Level;
import ca.liqwidice.mirrors.level.Levels;
import ca.liqwidice.mirrors.level.tile.BlankTile;
import ca.liqwidice.mirrors.level.tile.MirrorTile;
import ca.liqwidice.mirrors.level.tile.PointerTile;
import ca.liqwidice.mirrors.level.tile.ReceptorTile;
import ca.liqwidice.mirrors.level.tile.Tile;
import ca.liqwidice.mirrors.utils.Sound;

public class GameState extends BasicState {

	private final String LEVEL_SELECT = "Level Select";
	private final String RESET = "Reset";
	private final String QUIT = "Quit";
	private final String SAVE = "Save";
	private final String CLEAR = "Clear";

	private ButtonManager manager;
	private Level level;
	private Tile[] selectionTiles;
	private int selectedTileIndex = 0;


	public static boolean levelEditingMode = false;

	public GameState(Game game, Level level) {
		super(game);
		this.level = level;
		this.selectionTiles = new Tile[] { new BlankTile(20, 50, level), new MirrorTile(20, 50 + Tile.WIDTH, 0, level),
				new PointerTile(20, 50 + Tile.WIDTH * 2, 0, Color.RED, level),
				new ReceptorTile(20, 50 + Tile.WIDTH * 3, 0, level) };
	}

	@Override
	public void init() {
		manager = new ButtonManager();
		manager.addButton(new Button(10, 40, 135, 40, LEVEL_SELECT));
		manager.addButton(new Button(10, 95, 80, 40, RESET));
		manager.addButton(new Button(10, 150, 65, 40, QUIT));
		manager.addButton(new Button(10, Game.SIZE.height - 75, 100, 40, SAVE));
		manager.addButton(new Button(10, Game.SIZE.height - 185, 100, 40, CLEAR));
		manager.getButton(CLEAR).visible = false;
		manager.getButton(SAVE).visible = false;
	}

	@Override
	public void update(double delta) {
		if (Keyboard.ctrl_e) levelEditingMode = true;
		else levelEditingMode = false;

		manager.getButton(SAVE).visible = levelEditingMode;
		manager.getButton(CLEAR).visible = levelEditingMode;

		if (levelEditingMode && (Mouse.leftClicked || Mouse.rightClicked)) {
			int xx = Mouse.x - 20;
			int yy = Mouse.y - 50;
			if (xx > 0 && xx < Tile.WIDTH && yy > 0 && yy < Tile.WIDTH * selectionTiles.length) {
				selectedTileIndex = yy / Tile.WIDTH;
			}

			if (Mouse.x >= Level.xo && Mouse.y >= Level.yo) {
				xx = (Mouse.x - Level.xo) / Tile.WIDTH;
				yy = (Mouse.y - Level.yo) / Tile.WIDTH;
				if (xx >= 0 && xx < level.width && yy >= 0 && yy < level.height) {
					Tile t = level.tiles[yy][xx];
					if (t.getID() == selectionTiles[selectedTileIndex].getID()) {
						level.tiles[yy][xx].pollInput();
					} else {
						level.tiles[yy][xx] = selectionTiles[selectedTileIndex].copy(xx, yy);
					}
				}
			}

			manager.getButton(SAVE).update(delta);
			manager.getButton(CLEAR).update(delta);
			
		} else {
			manager.updateAll(delta);
		}

		level.update(delta, levelEditingMode);

		if (manager.getButton(LEVEL_SELECT).clicked) {
			game.enterPreviousState();
		} else if (manager.getButton(RESET).clicked) {
			Sound.SELECT.play();
			level = Levels.load(level.level, true);
			Levels.save(level);
		} else if (manager.getButton(QUIT).clicked) {
			game.stopGame();
		} else if (manager.getButton(SAVE).clicked) {
			Levels.save(level, true);
		} else if (manager.getButton(CLEAR).clicked) {
			level.clear();
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

		if (levelEditingMode) {
			g.drawString("EDITING", 10, Game.SIZE.height - 8);

			g.setColor(Color.BLACK);
			g.fillRect(10, 50 + selectedTileIndex * Tile.WIDTH, Tile.WIDTH + 20, Tile.WIDTH);

			for (int i = 0; i < selectionTiles.length; i++) {
				Tile t = selectionTiles[i];
				t.render(t.getX(), t.getY(), g);
			}

			manager.getButton(SAVE).render(g);
			manager.getButton(CLEAR).render(g);
		} else {
			manager.renderAll(g);
		}

		level.render(g);

		if (levelEditingMode) {
			g.setColor(Color.BLACK);
			for (int y = 0; y < level.tiles.length; y++) {
				for (int x = 0; x < level.tiles[y].length; x++) {
					g.drawRect(x * Tile.WIDTH + Level.xo, y * Tile.WIDTH + Level.yo, Tile.WIDTH, Tile.WIDTH);
				}
			}
		}


	}

	@Override
	public int getID() {
		return StateManager.GAME_STATE_ID;
	}

	@Override
	public void destroy() {

	}
		Levels.save(level);
}

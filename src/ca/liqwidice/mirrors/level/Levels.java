package ca.liqwidice.mirrors.level;

import java.awt.Color;

import ca.liqwidice.mirrors.level.tile.BlankTile;
import ca.liqwidice.mirrors.level.tile.MirrorTile;
import ca.liqwidice.mirrors.level.tile.PointerTile;
import ca.liqwidice.mirrors.level.tile.ReceptorTile;
import ca.liqwidice.mirrors.level.tile.Tile;

public class Levels {
	//@formatter:off
	private static int[][][] tiles = {
		
		/**
		 * 
		 * BLANK    - 0
		 * MIRROR   - 1
		 * POINTER  - 2
		 * RECEPTOR - 3
		 * 
		 */
		
	{ },// <- keep this as an offset, so that level one is at index [1], level two at [2] etc.
	
	/** LATER add a way to specify initial dir / col, maybe with an interior array
	*   eg. instead of 2, use { 2, 1, 5 } which means a green laser pointer facing north?
	*   OR make a level editor cmon
	*/
	{   { 2, 0, 1, 0, 0, 0},
		{ 0, 0, 0, 0, 0, 0},
		{ 0, 0, 0, 0, 0, 0},
		{ 0, 0, 0, 0, 0, 0},
		{ 0, 0, 1, 0, 0, 3},
	},
    {   { 0, 0, 1, 1, 0, 0},
		{ 0, 1, 2, 2, 1, 0},
		{ 1, 2, 3, 3, 2, 1},
		{ 0, 1, 2, 2, 1, 0},
		{ 0, 0, 1, 1, 0, 0}
	},
	{   { 0, 0, 2, 0, 0, 0},
		{ 0, 0, 0, 0, 0, 0},
		{ 2, 0, 3, 0, 0, 2},
		{ 0, 0, 0, 0, 0, 0},
		{ 0, 0, 2, 0, 0, 0}
	},
	{
		{ 0, 0, 1, 0, 0, 1},
		{ 0, 0, 0, 0, 0, 0},
		{ 1, 0, 0, 0, 0, 2},
		{ 3, 0, 0, 0, 0, 0},
		{ 1, 0, 2, 0, 0, 0}
	},
	{
		{ 2, 0, 0, 0, 0, 1},
		{ 1, 0, 0, 0, 0, 1},
		{ 1, 0, 0, 0, 0, 1},
		{ 1, 0, 0, 0, 0, 1},
		{ 1, 0, 0, 0, 0, 3}
	},
	{
		{ 3, 2, 2, 2, 2, 2},
		{ 2, 0, 0, 0, 0, 0},
		{ 2, 0, 0, 0, 0, 0},
		{ 2, 0, 0, 0, 0, 0},
		{ 2, 0, 0, 0, 0, 0}
	}};
	//@formatter:on

	public static Tile[][][] getTiles(Level level) {
		Tile[][][] result = new Tile[tiles.length][tiles[1].length][tiles[1][0].length];
		for (int l = 0; l < tiles.length; l++) {
			for (int y = 0; y < tiles[l].length; y++) {
				for (int x = 0; x < tiles[l][y].length; x++) {
					switch (tiles[l][y][x]) {
					case 0:
						result[l][y][x] = new BlankTile(x, y, level);
						break;
					case 1:
						result[l][y][x] = new MirrorTile(x, y, level);
						break;
					case 2:
						result[l][y][x] = new PointerTile(x, y, Color.GREEN, level);
						break;
					case 3:
						result[l][y][x] = new ReceptorTile(x, y, level);
						break;
					default:
						System.err.println("Unknown tile type in level class! " + tiles[y][x] + " x: " + x + ", y: "
								+ y);
						result[l][y][x] = new BlankTile(x, y, level);
					}
				}
			}
		}
		return result;
	}

	// LATER add saving / loading of levels here

}

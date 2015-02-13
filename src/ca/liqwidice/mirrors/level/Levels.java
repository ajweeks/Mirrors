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
		 * BLANK    : 00-09 {
		 *  0 = BLANK
		 * }
		 * MIRROR   : 10-19 {
		 * 	10 = FS
		 * 	11 = BS
		 * }
		 * POINTER  : 20-29 {
		 * 	20 = N
		 * 	21 = S
		 * 	22 = E
		 * 	23 = W
		 * }
		 * RECEPTOR : 30-29 {
		 *  30 = N
		 *  31 = E
		 *  32 = S
		 *  33 = W
		 * }
		 * 
		 */
		
	{ },// <- keep this as an offset, so that level one is at index [1], level two at [2] etc.
	
	/** LATER add a way to specify initial dir / col, maybe with an interior array
	*   eg. instead of 2, use { 2, 1, 5 } which means a green laser pointer facing north?
	*   OR make a level editor already
	*/
	{   { 20, 21, 22, 23, 24, 25},
		{ 00, 00, 00, 00, 00, 00},
		{ 00, 00, 00, 00, 00, 00},
		{ 00, 00, 00, 00, 00, 00},
		{ 26, 27, 30, 31, 32, 33},
	},
    {   { 00, 00, 01, 01, 00, 00},
		{ 00, 01, 02, 02, 01, 00},
		{ 01, 02, 03, 03, 02, 01},
		{ 00, 01, 02, 02, 01, 00},
		{ 00, 00, 01, 01, 00, 00}
	},
	{   { 00, 00, 02, 00, 00, 00},
		{ 00, 00, 00, 00, 00, 00},
		{ 02, 00, 03, 00, 00, 02},
		{ 00, 00, 00, 00, 00, 00},
		{ 00, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},        
	{
		{ 02, 00, 00, 00, 00, 01},
		{ 01, 00, 00, 00, 00, 01},
		{ 01, 00, 00, 00, 00, 01},
		{ 01, 00, 00, 00, 00, 01},
		{ 01, 00, 00, 00, 00, 03}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	},
	{
		{ 00, 00, 01, 00, 00, 01},
		{ 00, 00, 00, 00, 00, 00},
		{ 01, 00, 00, 00, 00, 02},
		{ 03, 00, 00, 00, 00, 00},
		{ 01, 00, 02, 00, 00, 00}
	}
	};
	//@formatter:on

	public static Tile[][][] getTiles(Level level) {
		Tile[][][] result = new Tile[tiles.length][tiles[1].length][tiles[1][0].length];
		for (int l = 0; l < tiles.length; l++) {
			for (int y = 0; y < tiles[l].length; y++) {
				for (int x = 0; x < tiles[l][y].length; x++) {
					int i = tiles[l][y][x];
					if (i >= 0 && i <= 9) { // BLANK
						result[l][y][x] = new BlankTile(x, y, level);

					} else if (i >= 10 && i <= 19) { // MIRROR
						if (i == 10) {
							result[l][y][x] = new MirrorTile(x, y, MirrorTile.FS, level);
						} else if (i == 11) {
							result[l][y][x] = new MirrorTile(x, y, MirrorTile.BS, level);
						}
					} else if (i >= 20 && i <= 29) { // POINTER
						if (i == 20) {
							result[l][y][x] = new PointerTile(x, y, Direction.NORTH.encode(), Color.GREEN, level);
						} else if (i == 21) {
							result[l][y][x] = new PointerTile(x, y, Direction.EAST.encode(), Color.GREEN, level);
						} else if (i == 22) {
							result[l][y][x] = new PointerTile(x, y, Direction.SOUTH.encode(), Color.GREEN, level);
						} else if (i == 23) {
							result[l][y][x] = new PointerTile(x, y, Direction.WEST.encode(), Color.GREEN, level);

						} else if (i == 24) {
							result[l][y][x] = new PointerTile(x, y, Direction.EAST.encode(), Color.RED, level);
						} else if (i == 25) {
							result[l][y][x] = new PointerTile(x, y, Direction.SOUTH.encode(), Color.RED, level);
						} else if (i == 26) {
							result[l][y][x] = new PointerTile(x, y, Direction.WEST.encode(), Color.RED, level);
						} else if (i == 27) {
							result[l][y][x] = new PointerTile(x, y, Direction.WEST.encode(), Color.RED, level);
						}
					} else if (i >= 30 && i <= 39) { // RECEPTOR
						if (i == 30) {
							result[l][y][x] = new ReceptorTile(x, y, Direction.NORTH.encode(), level);
						} else if (i == 31) {
							result[l][y][x] = new ReceptorTile(x, y, Direction.EAST.encode(), level);
						} else if (i == 32) {
							result[l][y][x] = new ReceptorTile(x, y, Direction.SOUTH.encode(), level);
						} else if (i == 33) {
							result[l][y][x] = new ReceptorTile(x, y, Direction.WEST.encode(), level);
						}

					} else {
						System.err.println("Unknown tile type in level class! " + tiles[y][x] + " x: " + x + ", y: "
								+ y);
						result[l][y][x] = new BlankTile(x, y, level);
					}
				}
			}
		}
		return result;
	}
	
	public static int numOfLevels() {
		return tiles.length - 1;
	}

	// LATER add saving / loading of levels here

}

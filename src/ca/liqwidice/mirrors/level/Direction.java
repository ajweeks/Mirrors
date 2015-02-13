package ca.liqwidice.mirrors.level;

public enum Direction {
	NORTH(new int[] { 0, -1 }), SOUTH(new int[] { 0, 1 }), EAST(new int[] { 1, 0 }), WEST(new int[] { -1, 0 }), NULL(
			new int[] { 0, 0 });

	public int[] offset;

	Direction(int[] offset) {
		this.offset = offset;
	}

	public Direction opposite() {
		switch (this) {
		case NORTH:
			return SOUTH;
		case EAST:
			return WEST;
		case SOUTH:
			return NORTH;
		case WEST:
			return EAST;
		case NULL:
		default:
			return NULL;
		}

	}

	public int encode() {
		switch (this) {
		case NORTH:
			return 0;
		case EAST:
			return 1;
		case SOUTH:
			return 2;
		case WEST:
			return 3;
		default:
			return -1;
		}
	}

	public static Direction decode(int dir) {
		switch (dir) {
		case 0:
			return NORTH;
		case 1:
			return EAST;
		case 2:
			return SOUTH;
		case 3:
			return WEST;
		case -1:
		default:
			return NULL;
		}
	}

	/** Return the next direction if the tile is rotating clockwise */
	public Direction cw() {
		switch (this) {
		case NORTH:
			return EAST;
		case EAST:
			return SOUTH;
		case SOUTH:
			return WEST;
		case WEST:
			return NORTH;
		case NULL:
		default:
			return NULL;
		}
	}

	/** Return the next direction if the tile is rotating counter-clockwise*/
	public Direction ccw() {
		switch (this) {
		case NORTH:
			return WEST;
		case WEST:
			return SOUTH;
		case SOUTH:
			return EAST;
		case EAST:
			return NORTH;
		case NULL:
		default:
			return NULL;
		}
	}
}
package ca.liqwidice.mirrors.level;

public enum Direction {
	NORTH(new int[] { 0, -1 }), SOUTH(new int[] { 0, 1 }), EAST(new int[] { 1, 0 }), WEST(new int[] { -1, 0 }), NULL(
			new int[] { 0, 0 });

	public int[] offset;

	Direction(int[] offset) {
		this.offset = offset;
	}

	public Direction add(Direction dir) {
		return decode((this.encode() + dir.encode()) % 4);
	}

	public Direction sub(Direction dir) {
		int result = this.encode() - dir.encode();
		if (result < 0) return decode((4 + result) % 4);
		else return decode(result);
	}

	public Direction opposite() {
		return decode((this.encode() + 2) % 4);
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
		return decode((this.encode() + 1) % 4);
	}

	/** Return the next direction if the tile is rotating counter-clockwise*/
	public Direction ccw() {
		// I think there's a cleaner way to do this, fix later
		int result = this.encode() - 1;
		if (result < 0) return decode((4 + result) % 4);
		else return decode(result);
	}
}
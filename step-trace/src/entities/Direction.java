package entities;

import utils.RegExp;

public class Direction extends AbstractEntity implements Cloneable {
	
	public static final String _DIRECTION = "DIRECTION";
	private String x, y, z;
	
	public String getX() {
		return x;
	}

	public String getY() {
		return y;
	}

	public String getZ() {
		return z;
	}

	private String n = "0.0000000000000000000";
	private String m = "-0.0000000000000000000";
	
	// DIRECTION ( 'NONE',  ( 0.0000000000000000000, 1.000000000000000000, 0.0000000000000000000 
	public Direction(String lineId) {
		super(lineId);
		String dirVal = linesMap.get(lineId);
		String dir[] = RegExp.getValueBetweenDoubleParentheses(dirVal).split(",");
		x = dir[0].trim();
		if (x.equals(m)) x = n;
		y = dir[1].trim();
		if (y.equals(m)) y = n;
		z = dir[2].trim();
		if (z.equals(m)) z = n;
		// System.out.println(getCounterDirection());
	}
	
	@Override
	public String getEntityName() {
		return _DIRECTION;
	}
	
	@Override
	public String toString() {
		return getLineId() + ": " + getEntityName() + ' ' + x + ", " + y + ", " + z;
	}
	
	public Direction getCounterDirection() {
		Direction c = null;
		try {
			c = (Direction) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (!c.x.equals(n)) {
			c.x = c.x.startsWith("-") ? c.x.substring(1) : "-".concat(c.x);
		}
		if (!c.y.equals(n)) {
			c.y = c.y.startsWith("-") ? c.y.substring(1) : "-".concat(c.y);
		}
		if (!c.z.equals(n)) {
			c.z = c.z.startsWith("-") ? c.z.substring(1) : "-".concat(c.z);
		}
		return c;
	}
	
	@Override
	public boolean equals(Object obj) {
		Direction d = (Direction) obj;
		Direction opposite = d.getCounterDirection();
		return (x.equals(d.x) && y.equals(d.y) && z.equals(d.z)) || (x.equals(opposite.x) && y.equals(opposite.y) && z.equals(opposite.z));
	}
	
	@Override
	public int hashCode() {
		return 1;
	}

}

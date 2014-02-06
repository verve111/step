package entities;

import java.math.BigDecimal;

import utils.RegExp;

public class Direction extends AbstractEntity implements Cloneable {

	public static final String _DIRECTION = "DIRECTION";
	private float x, y, z;

	// DIRECTION ( 'NONE', ( 0.0000000000000000000, 1.000000000000000000,
	// 0.0000000000000000000
	public Direction(String lineId) {
		super(lineId);
		String dirVal = linesMap.get(lineId);
		String dir[] = RegExp.getValueBetweenDoubleParentheses(dirVal).split(",");
		String x = dir[0].trim();
		String y = dir[1].trim();
		String z = dir[2].trim();
		this.x = toFloat(x);
		this.y = toFloat(y);
		this.z = toFloat(z);
	}

	private float toFloat(String s) {
		return new BigDecimal(s).setScale(7, BigDecimal.ROUND_HALF_UP).floatValue();
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
		c.x *= -1;
		c.y *= -1;
		c.z *= -1;
		return c;
	}

	@Override
	public boolean equals(Object obj) {
		Direction d = (Direction) obj;
		Direction opposite = d.getCounterDirection();
		return (x == d.x && y == d.y && z == d.z) || (x == opposite.x && y == opposite.y && z == opposite.z);
	}

	@Override
	public int hashCode() {
		return 1;
	}

	public boolean isPerpendicular(Direction d2) {
		double res = (x * d2.x) + (y * d2.y) + (z * d2.z);
		return res == 0;
	}

	public boolean isYOriented() {
		return (x == 0) && (Math.abs(y) == 1) && (z == 0);
	}

	public boolean isZXOriented() {
		return y == 0;
	}
}

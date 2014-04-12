package entities;

import utils.CommonUtils;
import utils.RegExp;

public class Direction extends AbstractEntity implements Cloneable {

	public static final String _DIRECTION = "DIRECTION";
	private float x, y, z;
	private IGeometry geometry;

	// DIRECTION ( 'NONE', ( 0.0000000000000000000, 1.000000000000000000,
	// 0.0000000000000000000
	public Direction(String lineId, IGeometry geometry) {
		super(lineId);
		this.geometry = geometry;
		String dirVal = linesMap.get(lineId);
		String dir[] = RegExp.getValueBetweenDoubleParentheses(dirVal).split(",");
		String x = dir[0].trim();
		String y = dir[1].trim();
		String z = dir[2].trim();
		this.x = CommonUtils.toFloat(x);
		this.y = CommonUtils.toFloat(y);
		this.z = CommonUtils.toFloat(z);
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
		if (geometry != null && geometry instanceof Plane || geometry instanceof CylindricalSurface) {
			return (x == 0) && (Math.abs(y) == 1) && (z == 0);
		} 			
		return false;
	}
	
	public boolean isXOriented() {
		if (geometry != null && geometry instanceof Plane || geometry instanceof CylindricalSurface) {
			return (Math.abs(x) == 1) && (y == 0) && (z == 0);
		} 			
		return false;
	}
	
	// for front plane back plane only
	public boolean isZOriented() {
		if (geometry != null && (geometry instanceof Plane || geometry instanceof CylindricalSurface)) {
			return (x == 0) && (Math.abs(z) == 1) && (y == 0);
		} 
		return false;
	}

	public boolean isZXOriented() {
		if (geometry != null && (geometry instanceof Plane || geometry instanceof CylindricalSurface)) {
			return y == 0;
		} 
		return false;
	}
	
	public boolean isXYOriented() {
		if (geometry != null && geometry instanceof CylindricalSurface) {
			return x == 0 && y == 0 && Math.abs(z) == 1;
		} else if (geometry != null && geometry instanceof Plane) {
			return z == 0;
		} 
		return false;
	}
	
	public float getDotProduct(Direction d) {
		return CommonUtils.toFloat(this.x * d.x + this.y * d.y + this.z * d.z);
	}
	
	public float getDotProduct(float x, float y, float z) {
		return CommonUtils.toFloat(this.x * x + this.y * y + this.z * z);
	}

	public IGeometry getGeometry() {
		return geometry;
	}

}

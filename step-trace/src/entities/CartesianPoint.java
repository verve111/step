package entities;

import utils.CommonUtils;
import utils.RegExp;

public class CartesianPoint extends AbstractEntity {
	
	public static final String _CARTESIAN_POINT = "CARTESIAN_POINT";
	private float x, y, z;
	
	// CARTESIAN_POINT ( 'NONE',  ( 0.0000000000000000000, 0.0000000000000000000, 0.0000000000000000000))
	public CartesianPoint(String lineId) {
		super(lineId);
		String cpVal = linesMap.get(lineId);
		String koord[] = RegExp.getValueBetweenDoubleParentheses(cpVal).split(",");
		String x = koord[0].trim();
		String y = koord[1].trim();
		String z = koord[2].trim();
		this.x = CommonUtils.toFloat(x);
		this.y = CommonUtils.toFloat(y);
		this.z = CommonUtils.toFloat(z);
	}
	
	@Override
	public String getEntityName() {
		return _CARTESIAN_POINT;
	}
	
	@Override
	public String toString() {
		return getLineId() + ": " + getEntityName() + ' ' + x + ", " + y + ", " + z;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}
	
	@Override
	public boolean equals(Object o) {
		CartesianPoint cp = (CartesianPoint) o;
		return this.x == cp.x && this.y == cp.y && this.z == cp.z;
	}

}

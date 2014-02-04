package entities;

import utils.RegExp;

public class CartesianPoint extends AbstractEntity {
	
	public static final String _CARTESIAN_POINT = "CARTESIAN_POINT";
	private String x, y, z;
	private static final String n = "0.0000000000000000000";
	private static final String m = "-0.0000000000000000000";
	
	// CARTESIAN_POINT ( 'NONE',  ( 0.0000000000000000000, 0.0000000000000000000, 0.0000000000000000000))
	public CartesianPoint(String lineId) {
		super(lineId);
		String cpVal = linesMap.get(lineId);
		String koord[] = RegExp.getValueBetweenDoubleParentheses(cpVal).split(",");
		x = koord[0].trim();
		y = koord[1].trim();
		z = koord[2].trim();
		//System.out.println(this);
	}
	
	@Override
	public String getEntityName() {
		return _CARTESIAN_POINT;
	}
	
	@Override
	public String toString() {
		return getLineId() + ": " + getEntityName() + ' ' + x + ", " + y + ", " + z;
	}

	public String getX() {
		return x;
	}

	public String getY() {
		return y;
	}

	public String getZ() {
		return z;
	}
	
	/**
	 * i==1 x
	 * i==2 y
	 * i==3 z
	 */
	public boolean isNull(int i) {
		if (i == 1) {
			return x.equals(n) || x.equals(m); 
		} else if (i == 2) {
			return y.equals(n) || y.equals(m);
		} else if (i == 3) {
			return z.equals(n) || z.equals(m);
		}
		throw new RuntimeException(":: isNull");
	}

}

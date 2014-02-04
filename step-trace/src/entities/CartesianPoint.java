package entities;

import utils.RegExp;

public class CartesianPoint extends AbstractEntity {
	
	public static final String _CARTESIAN_POINT = "CARTESIAN_POINT";
	private String x, y, z;
	
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

}

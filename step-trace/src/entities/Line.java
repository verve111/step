package entities;

import utils.RegExp;
import utils.CommonUtils;

public class Line extends AbstractEntity implements EdgeGeometry {
	
	public final static String _LINE = "LINE";
	private CartesianPoint cp;
	private Direction dir;
	
	// LINE ( 'NONE', #171, #73 ) ;
	// name, point, vector
	public Line(String lineId) {
		super(lineId);
		String lineVal = linesMap.get(lineId);
		String pointId = RegExp.getParameter(lineVal, 2, 3);
		cp = new CartesianPoint(pointId);
		// VECTOR ( 'NONE', #172, 1000.000000000000000 ) ;
		dir = new Direction(CommonUtils.getDirectionIdByVectorId(RegExp.getParameter(lineVal, 3, 3), linesMap), this);
	}

	public Direction getDirection() {
		return dir;
	}

	@Override
	public String getEntityName() {
		return _LINE;
	}

	public CartesianPoint getCp() {
		return cp;
	}


}

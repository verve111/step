package entities;

import utils.RegExp;
import utils.CommonUtils;

public class Ellipse extends AbstractEntity implements EdgeGeometry {
	
	public final static String _ELLIPSE = "ELLIPSE";
	private Axis2Placement3D axis2;
	private float radius1;
	private float radius2;
	
	// ELLIPSE('',#5741,13.4720785636979,6.75);
	// name, axis2placment3d, radius
	// axis2placment3d - direction from the point in the middle-top of the arc 
	public Ellipse(String lineId) {
		super(lineId);
		String lineVal = linesMap.get(lineId);
		String asix2Id = RegExp.getParameter(lineVal, 2, 4);
		axis2 = new Axis2Placement3D(asix2Id, this);
		radius1 = CommonUtils.toFloat(RegExp.getParameter(lineVal, 3, 4));
		radius2 = CommonUtils.toFloat(RegExp.getParameter(lineVal, 4, 4));
	}

	public Direction getDirection() {
		// actually never used
		return axis2.getAxis();
	}

	@Override
	public String getEntityName() {
		return _ELLIPSE;
	}

	public float getRadius1() {
		return radius1;
	}

	public float getRadius2() {
		return radius2;
	}
}

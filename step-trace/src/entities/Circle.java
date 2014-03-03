package entities;

import utils.RegExp;
import utils.CommonUtils;

public class Circle extends AbstractEntity implements EdgeGeometry {
	
	public final static String _CIRCLE = "CIRCLE";
	private Axis2Placement3D axis2;
	private float radius;
	
	// CIRCLE ( 'NONE', #28, 8.717678822833152100 ) ;
	// name, axis2placment3d, radius
	// axis2placment3d - direction from the point in the middle-top of the arc 
	public Circle(String lineId) {
		super(lineId);
		String lineVal = linesMap.get(lineId);
		String asix2Id = RegExp.getParameter(lineVal, 2, 3);
		axis2 = new Axis2Placement3D(asix2Id, this);
		radius = CommonUtils.toFloat(RegExp.getParameter(lineVal, 3, 3));
	}

	public Direction getDirection() {
		// actually never used
		return axis2.getAxis();
	}

	@Override
	public String getEntityName() {
		return _CIRCLE;
	}

	public float getRadius() {
		return radius;
	}

}

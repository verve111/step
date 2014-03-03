package entities;

import utils.RegExp;

public class Plane extends AbstractEntity implements SurfaceGeometry {
	public static final String _PLANE = "PLANE";
	
	private Axis2Placement3D axis2Placement3D;

	// PLANE ( 'NONE',  #5 ) ;
	public Plane(String lineId) {
		super(lineId);
		String planeVal = linesMap.get(lineId);
		axis2Placement3D = new Axis2Placement3D(RegExp.getParameter(planeVal, 2, 2), this);
	}
	
	@Override
	public String getEntityName() {
		return _PLANE;
	}

	public Axis2Placement3D getAxis2Placement3D() {
		return axis2Placement3D;
	}

	@Override
	public Direction getDirection() {
		return axis2Placement3D.getAxis();
	}
}

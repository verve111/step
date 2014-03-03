package entities;

import utils.RegExp;
import utils.CommonUtils;

public class ConicalSurface extends AbstractEntity implements SurfaceGeometry {
	
	public static final String _CONICAL_SURFACE = "CONICAL_SURFACE";
	
	private Axis2Placement3D axis2Placement3D;
	private float radius;
	private float semiAngle;

	// CONICAL_SURFACE ( 'NONE', #284, 9.469296489893132200, 0.7853981633974501700 ) ;
	public ConicalSurface(String lineId) {
		super(lineId);
		String surfVal = linesMap.get(lineId);
		axis2Placement3D = new Axis2Placement3D(RegExp.getParameter(surfVal, 2, 4), this);
		radius = CommonUtils.toFloat(RegExp.getParameter(surfVal, 3, 4));
		semiAngle = CommonUtils.toFloat(RegExp.getParameter(surfVal, 4, 4));
	}
	
	@Override
	public String getEntityName() {
		return _CONICAL_SURFACE;
	}

	public Axis2Placement3D getAxis2Placement3D() {
		return axis2Placement3D;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public float getSemiAngle() {
		return semiAngle;
	}
	
	@Override
	public Direction getDirection() {
		return axis2Placement3D.getAxis();
	}
}

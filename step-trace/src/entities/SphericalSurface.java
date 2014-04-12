package entities;

import utils.RegExp;
import utils.CommonUtils;

public class SphericalSurface extends AbstractEntity implements SurfaceGeometry {
	
	public static final String _SPHERICAL_SURFACE = "SPHERICAL_SURFACE";
	
	private Axis2Placement3D axis2Placement3D;
	private float radius;

	// SPHERICAL_SURFACE('',#169,6.0);
	public SphericalSurface(String lineId) {
		super(lineId);
		String surfVal = linesMap.get(lineId);
		axis2Placement3D = new Axis2Placement3D(RegExp.getParameter(surfVal, 2, 3), this);
		radius = CommonUtils.toFloat(RegExp.getParameter(surfVal, 3, 3));
	}
	
	@Override
	public String getEntityName() {
		return _SPHERICAL_SURFACE;
	}

	public Axis2Placement3D getAxis2Placement3D() {
		return axis2Placement3D;
	}
	
	public float getRadius() {
		return radius;
	}
	
	@Override
	public Direction getDirection() {
		return axis2Placement3D.getAxis();
	}
}

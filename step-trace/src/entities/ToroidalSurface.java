package entities;

import utils.RegExp;
import utils.CommonUtils;

public class ToroidalSurface extends AbstractEntity implements SurfaceGeometry {
	
	public static final String _TOROIDAL_SURFACE = "TOROIDAL_SURFACE";
	
	private Axis2Placement3D axis2Placement3D;
	private float radius;
	private float semiAngle;

	// TOROIDAL_SURFACE('',#99,-0.0821624036539057,3.0);
	public ToroidalSurface(String lineId) {
		super(lineId);
		String surfVal = linesMap.get(lineId);
		axis2Placement3D = new Axis2Placement3D(RegExp.getParameter(surfVal, 2, 4), this);
		radius = CommonUtils.toFloat(RegExp.getParameter(surfVal, 3, 4));
		semiAngle = CommonUtils.toFloat(RegExp.getParameter(surfVal, 4, 4));
	}
	
	@Override
	public String getEntityName() {
		return _TOROIDAL_SURFACE;
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

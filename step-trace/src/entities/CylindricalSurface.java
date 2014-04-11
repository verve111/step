package entities;

import utils.RegExp;
import utils.CommonUtils;

public class CylindricalSurface extends AbstractEntity implements SurfaceGeometry {
	
	public static final String _CYLINDRICAL_SURFACE = "CYLINDRICAL_SURFACE";
	
	private Axis2Placement3D axis2Placement3D;
	private float radius;

	// CYLINDRICAL_SURFACE ( 'NONE', #18, 8.717678822833153900 ) ;
	public CylindricalSurface(String lineId) {
		super(lineId);
		String surfVal = linesMap.get(lineId);
		axis2Placement3D = new Axis2Placement3D(RegExp.getParameter(surfVal, 2, 3), this);
		radius = CommonUtils.toFloat(RegExp.getParameter(surfVal, 3, 3));
	}
	
	@Override
	public String getEntityName() {
		return _CYLINDRICAL_SURFACE;
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

	@Override
	public boolean equals(Object o) {
		CylindricalSurface c = (CylindricalSurface) o;
		if (this.radius == c.radius && this.axis2Placement3D.equals(c.getAxis2Placement3D())) {
			return true;
		}
		return false;
	}
	
}

package entities;

import utils.RegExp;


public class Axis2Placement3D extends AbstractEntity {
	
	public static final String _AXIS2_PLACEMENT_3D = "AXIS2_PLACEMENT_3D";
	private CartesianPoint cp;
	private Direction axis;
	private Direction ref_direction;
	
	// AXIS2_PLACEMENT_3D ( 'NONE', #135, #136, #137 ) ;
	// name, point, axis, ref_direction
	public Axis2Placement3D(String lineId, IGeometry surfGeom) {
		super(lineId);
		String axis2Val = linesMap.get(lineId);
		cp = new CartesianPoint(RegExp.getParameter(axis2Val, 2, 4));
		axis = new Direction(RegExp.getParameter(axis2Val, 3, 4), surfGeom);
		ref_direction = new Direction(RegExp.getParameter(axis2Val, 4, 4), surfGeom);		
	}
	
	@Override
	public String getEntityName() {
		return _AXIS2_PLACEMENT_3D;
	}

	public CartesianPoint getCartesianPoint() {
		return cp;
	}

	public Direction getAxis() {
		return axis;
	}

	public Direction getRef_direction() {
		return ref_direction;
	}
	
	@Override
	public boolean equals(Object o) {
		Axis2Placement3D a = (Axis2Placement3D) o;
		boolean res = this.axis.equals(a.axis) && this.ref_direction.equals(a.ref_direction) && this.cp.equals(a.cp);
		if (res) {
			//System.out.println(a.cp);
		}
		return res;
	}
}

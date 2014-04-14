package entities;

public class FaceBound extends FaceBoundAbstract {

	public FaceBound(String lineId) {
		super(lineId);
	}


	public static final String _FACE_BOUND = "FACE_BOUND";

	public AdvancedFace getAdjacentCylinder() {
		if (this.isCircle()) {
			for (AdvancedFace af : getAdjacents()) {
				if (af.getSurfGeometry() instanceof CylindricalSurface) {
					return af;
				}
			}
		} 
		return null;
	}

	public boolean isAdjacentMarkedAsThroughHole() {
		return getAdjacentCylinder() != null && getAdjacentCylinder().isThroughHole;
	}
	
	public void markAdjacentAsThroughHole() {
		if (getAdjacentCylinder() != null) {
			getAdjacentCylinder().isThroughHole = true;
		}
	}
	
	@Override
	public String getEntityName() {
		return _FACE_BOUND;
	}

}

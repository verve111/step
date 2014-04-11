package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import keepers.CartesianPointKeeper;
import utils.RegExp;

public class ClosedShell extends AbstractEntity {

	public final static String _CLOSED_SHELL = "CLOSED_SHELL";
	private List<AdvancedFace> list = new ArrayList<AdvancedFace>();
	
	public List<AdvancedFace> getAdvancedFaces() {
		return list;
	}

	// CLOSED_SHELL ( 'NONE', ( #22, #19, #24, #23, #21, #20 ) ) 
	public ClosedShell(String lineId) {
		super(lineId);
		String val = RegExp.getValueBetweenDoubleParentheses(linesMap.get(lineId));
		for (String advFaceId : Arrays.asList(val.split(","))) {
			AdvancedFace af = new AdvancedFace(advFaceId.trim());
			boolean isDuplicate = false;
			if (af.getSurfGeometry() instanceof CylindricalSurface) {
				for (AdvancedFace afInner : list) {
					List<EdgeCurve> l1 = af.getFaceOuterBound().getAllCircleEdgeCurves();
					List<EdgeCurve> l2 = afInner.getFaceOuterBound().getAllCircleEdgeCurves();
					if (afInner.getSurfGeometry() instanceof CylindricalSurface
							&& afInner.getSurfGeometry().equals(af.getSurfGeometry())
							&& l1.size() == 2
							&& l2.size() == 2
							&& (l1.get(0).getEdgeGeometry().getDirection().equals(l2.get(0).getEdgeGeometry().getDirection()) || l1.get(0)
									.getEdgeGeometry().getDirection().equals(l2.get(1).getEdgeGeometry().getDirection()))
							&& (l1.get(1).getEdgeGeometry().getDirection().equals(l2.get(0).getEdgeGeometry().getDirection())
							|| l1.get(1).getEdgeGeometry().getDirection().equals(l2.get(1).getEdgeGeometry().getDirection()))) {
						isDuplicate = true;
						break;
					}
				}
			}
			if (!isDuplicate) {
				list.add(af);
			}
		}
	}

	@Override
	public String getEntityName() {
		return _CLOSED_SHELL;
	}
	
	/*public void getParallel() {
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			for (AdvancedFace aFinner : list) {
				if (af.getLineId().equals(aFinner.getLineId())) {
					continue;
				}
				Axis2Placement3D a2p3DInner = aFinner.getSurfGeometry().getAxis2Placement3D();
				if (a2p3D.getAxis().equals(a2p3DInner.getAxis()) && a2p3D.getRef_direction().equals(a2p3DInner.getRef_direction())) {
					System.out.println("da " + af.getLineId() + ", " + aFinner.getLineId());
				}
			}
		}
	}*/
	
	public AdvancedFace getBottomPlane() {
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			if (af.getSurfGeometry() instanceof Plane
					&& a2p3D.getCartesianPoint().getY() == CartesianPointKeeper.getMaxShapeMeasures().minY && a2p3D.getAxis().isYOriented()) {
				return af;
			}
		}
		return null;
	}
	
	public AdvancedFace getFrontPlane() {
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			if (af.getSurfGeometry() instanceof Plane
					&& a2p3D.getCartesianPoint().getZ() == CartesianPointKeeper.getMaxShapeMeasures().maxZ && a2p3D.getAxis().isZOriented()) {
				return af;
			}
		}
		return null;
	}
	
	public AdvancedFace getBackPlane() {
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			if (af.getSurfGeometry() instanceof Plane
					&& a2p3D.getCartesianPoint().getZ() == CartesianPointKeeper.getMaxShapeMeasures().minZ && a2p3D.getAxis().isZOriented()) {
				return af;
			}
		}
		return null;
	}
	
	public AdvancedFace getTopPlane() {
		AdvancedFace toppest = null;
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			if (a2p3D.getAxis().isYOriented() && af.getSurfGeometry() instanceof Plane) {
				if (toppest == null || toppest.getSurfGeometry().getAxis2Placement3D().getCartesianPoint().getY() < a2p3D.getCartesianPoint().getY()) {
					toppest = af;
				}
			}
		}
		return toppest;
	}
	
	public int getYOrientedPlaneFacesCount() {
		int res = 0;
		Set<Float> set = new HashSet<Float>();
		for (AdvancedFace af : list) {
			if (af.getSurfGeometry().getDirection().isYOriented() && af.getSurfGeometry() instanceof Plane) {
				float yValue = af.getSurfGeometry().getAxis2Placement3D().getCartesianPoint().getY();
				if (!set.contains(yValue)) {
					res++;
				}
				set.add(yValue);
			}
		}		
		return res;
	}
	
	public boolean hasYOrientedCylindricalSurface() {
		for (AdvancedFace af : list) {
			if (af.getSurfGeometry().getDirection().isYOriented() && af.getSurfGeometry() instanceof CylindricalSurface) {
				return true;
			}
		}		
		return false;
	}
	
	public AdvancedFace getAdvancedFaceByFaceBoundId(String id) {
		for (AdvancedFace af : list) {
			if (af.getFaceOuterBound().getLineId().equals(id)) {
				return af;
			}
		}
		return null;
	}
	
	public boolean isAllPlanes() {
		boolean res = true;
		for (AdvancedFace af : list) {
			res &= af.isPlane();
		}
		if (res) {
			System.out.println("all faces are planes");
		}
		return res;
	}
	
	// without holes that orto to Z-Plane
	public List<AdvancedFace> getCylindricalSurfacesOrtoToZPlane() {
		List<AdvancedFace> res = new ArrayList<AdvancedFace>();
		for (AdvancedFace af : list) {
			if (af.getSurfGeometry() instanceof CylindricalSurface && af.getSurfGeometry().getDirection().isZOriented()) {
				res.add(af);
			}
		}
		return res;
	}
	
	public int countCylindricalSurfacesOrtoToZPlaneWithoutInner() {
		int i = getCylindricalSurfacesOrtoToZPlane().size();
		return hasHoleOrtoToZPlane() ? i - 1 : i;
	}
	
	private boolean hasHoleOrtoToZPlane() {
		return getBackPlane().getFaceInnerBound().size() > 0 && getBackPlane().getFaceInnerBound().get(0).isCircle()
				&& getFrontPlane().getFaceInnerBound().size() > 0 && getFrontPlane().getFaceInnerBound().get(0).isCircle();
	}

}

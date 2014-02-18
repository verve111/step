package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import keepers.CircularList;

import utils.RegExp;

public class AdvancedFace extends AbstractEntity {
	
	public static final String _ADVANCED_FACE = "ADVANCED_FACE";
	private List<FaceBound> list = new ArrayList<FaceBound>();
	private SurfaceGeometry surfGeometry;
	private ClosedShell cs;
	
	// ADVANCED_FACE ( 'NONE', ( #2 ), #130, .F. )
	// second param almost always only one
	public AdvancedFace(String advFaceLineId, ClosedShell cs) {
		super(advFaceLineId);
		this.cs = cs;
		String advFaceVal = linesMap.get(advFaceLineId);
		String faceBoundLineNums[] = RegExp.getValueBetweenDoubleParentheses(advFaceVal).split(",");
		for (String faceBoundLineNum : Arrays.asList(faceBoundLineNums)) {
			String faceBoundLineVal = linesMap.get(faceBoundLineNum.trim());
			if (faceBoundLineVal.startsWith(FaceOuterBound._FACE_OUTER_BOUND)) {
				list.add(new FaceOuterBound(faceBoundLineNum, advFaceLineId));
			} else {
				System.out.println("___not found face outer bound");
			}
		}
		String surfGeometryLineNum = RegExp.getParameter(advFaceVal, 3, 4);
		String surfGeometryLineVal = linesMap.get(surfGeometryLineNum);
		if (surfGeometryLineVal.startsWith(Plane._PLANE)) {
			surfGeometry = new Plane(surfGeometryLineNum);
		} else if (surfGeometryLineVal.startsWith(CylindricalSurface._CYLINDRICAL_SURFACE)) {
			surfGeometry = new CylindricalSurface(surfGeometryLineNum);
		} else if (surfGeometryLineVal.startsWith(ConicalSurface._CONICAL_SURFACE)) {
			surfGeometry = new ConicalSurface(surfGeometryLineNum);			
		} else {
			System.out.println("___not found surface geometry " + surfGeometryLineNum);
		}
		getSortedEdgeCurves();
	}
	
	public List<FaceBound> getFaceBoundList() {
		return list;
	}
		
	@Override
	public String getEntityName() {
		return _ADVANCED_FACE;
	}
	
	public SurfaceGeometry getSurfGeometry() {
		return surfGeometry;
	}
	
	public List<AdvancedFace> getAdjacents() {
		List<AdvancedFace> res = new ArrayList<AdvancedFace>();
		List<EdgeCurve> li = getEdgeCurves();
		for (EdgeCurve ec : li) {
			for (String ref : ec.getOuterRefs()) {
				if (!ref.equals(this.getLineId())) {
					res.add(cs.getAdvancedFaceById(ref));
				}
			}
		}
		return res;
	}
	
	public boolean areAdjacentsXZOriented() {
		boolean res = true;
		for (AdvancedFace af : getAdjacents()) {
			if (af.getSurfGeometry() instanceof CylindricalSurface) {
				if (af.getSurfGeometry().getAxis2Placement3D().getAxis().isZXOrientedForCylindricalSurface()) {
					continue;
				} else {
					System.out.println("WARN: areAdjacentsXZOriented for cylindrical surface");
				}
			}
			res &= af.getSurfGeometry().getAxis2Placement3D().getAxis().isZXOriented();
		}
		return res;
	}
	
	public List<EdgeCurve> getEdgeCurves() {
		if (list.size() > 1) {
			throw new RuntimeException("getSortedEdgeCurves :: more than one");
		}
		return list.get(0).getEdgeLoop().getEdgeCurves();
	}
	
	public CircularList<EdgeCurve> getSortedEdgeCurves() {
		List<EdgeCurve> unsorted = getEdgeCurves();
		CircularList<EdgeCurve> sorted = new CircularList<EdgeCurve>();
		if (unsorted.size() < 1) {
			System.out.println("Warning: getSortedEdgeCurves");
			return null;
		}
		EdgeCurve curr = unsorted.get(0);
		sorted.add(curr);
		for (int i = 1; i < unsorted.size(); i++) {
			curr = getNextEdge(curr, unsorted);
			sorted.add(curr);
		}
		return sorted;
	}
	
	private EdgeCurve getNextEdge(EdgeCurve curr, List<EdgeCurve> unsorted) {
		for (EdgeCurve ec : unsorted) {
			if (!ec.getLineId().equals(curr.getLineId())) {
				if (curr.getEndPoint().getLineId().equals(ec.getStartPoint().getLineId())) {
					return ec;
				} else if (curr.getEndPoint().getLineId().equals(ec.getEndPoint().getLineId())) {
					return ec.getClonedRotatedEdge();
				}
			}
		}
		throw new RuntimeException("getNextEdge not found");
	}
	
	public boolean isRectangle() {
		boolean res = true;
		CircularList<EdgeCurve> li = getSortedEdgeCurves();
		if (li.isAllLines() && li.size() == 4) {
			for (int i = 0; i < li.size(); i++) {
				res = res && li.get(i).getEdgeGeometry().getDirection().isPerpendicular(li.getNext(i).getEdgeGeometry().getDirection());
			}
		} else {
			res = false;
		}
		return res;
	}
	
	public boolean isRightAngledTriangle() {
		CircularList<EdgeCurve> li = getSortedEdgeCurves();
		if (li.isAllLines() && li.size() == 3) {
			for (int i = 0; i < li.size(); i++) {
				if (li.get(i).getEdgeGeometry().getDirection().isPerpendicular(li.getNext(i).getEdgeGeometry().getDirection())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isCircularAndOrtogonal() {
		boolean res = true;
		CircularList<EdgeCurve> li = getSortedEdgeCurves();
		if (!li.isAllLines() && li.size() > 3) {
			for (int i = 0; i < li.size(); i++) {
				if (li.get(i).getEdgeGeometry() instanceof Circle || li.getNext(i).getEdgeGeometry() instanceof Circle) {
					continue;
				}
				res = res && li.get(i).getEdgeGeometry().getDirection().isPerpendicular(li.getNext(i).getEdgeGeometry().getDirection());
			}
		} else {
			res = false;
		}
		return res;
	}
	
	public boolean isAllAnglesTheSame() {
		boolean res = true;
		CircularList<EdgeCurve> li = getSortedEdgeCurves();
		float angle = 0;
		if (li.isAllLines() && li.size() > 4 && li.size() < 30) {
			for (int i = 0; i < li.size(); i++) {
				float product = li.get(i).getEdgeGeometry().getDirection().getDotProduct(li.getNext(i).getEdgeGeometry().getDirection());
				if (i == 0) {
					angle = product;
				} else {
					if (angle != product) {
						return false;
					}
				}
			}
		} else {
			res = false;
		}
		return res;
	}
	
	public ClosedShell getClosedShell() {
		return cs;
	}
	
	public boolean hasTopChamfers() {
		boolean res = true;
		float angle = 0;
		int i = 0;
		for (AdvancedFace af : getAdjacents()) {
			if (!af.isPlane()) {
				continue;
			}
			// positive dot product for acute (sharp angle), positive for obtuse
			float currAngle = af.getSurfGeometry().getAxis2Placement3D().getAxis().getDotProduct(0, 1, 0);
			if (i++ == 0) {
				angle = currAngle;
			} else {
				res &= (angle == currAngle && angle > 0);
			}
		}
		return res;
	}
	
	public boolean isPlane() {
		return getSurfGeometry() instanceof Plane;
	}
}

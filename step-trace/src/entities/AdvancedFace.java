package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		} else {
			System.out.println("___not found surface geometry");
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
	
	public List<EdgeCurve> getEdgeCurves() {
		if (list.size() > 1) {
			throw new RuntimeException("getSortedEdgeCurves :: more than one");
		}
		return list.get(0).getEdgeLoop().getEdgeCurves();
	}
	
	public List<EdgeCurve> getSortedEdgeCurves() {
		List<EdgeCurve> unsorted = getEdgeCurves();
		List<EdgeCurve> sorted = new ArrayList<EdgeCurve>();
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
		List<EdgeCurve> li = getSortedEdgeCurves();
		if (li.size() == 4) {
			for (int i = 0; i < 3; i++) {
				res = res && li.get(i).getEdgeGeometry().getDirection().isPerpendicular(li.get(i + 1).getEdgeGeometry().getDirection());
			}
		} else {
			res = false;
		}
		return res;
	}
	
	public boolean isRightAngledTriangle() {
		List<EdgeCurve> li = getSortedEdgeCurves();
		if (li.size() == 3) {
			for (int i = 0; i < 3; i++) {
				if (li.get(i).getEdgeGeometry().getDirection().isPerpendicular(li.get(i == 2 ? 0 : i + 1).getEdgeGeometry().getDirection())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean areAdjacentsXZOriented() {
		boolean res = true;
		List<EdgeCurve> li = getEdgeCurves();
		for (EdgeCurve ec : li) {
			for (String ref : ec.getOuterRefs()) {
				if (!ref.equals(this.getLineId())) {
					AdvancedFace adjacentAF = cs.getAdvancedFaceById(ref);
					res &= adjacentAF.getSurfGeometry().getAxis2Placement3D().getAxis().isZXOriented();
				}
			}
		}
		return res;
	}

}

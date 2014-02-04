package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import utils.RegExp;

public class AdvancedFace extends AbstractEntity {
	
	public static final String _ADVANCED_FACE = "ADVANCED_FACE";
	private List<FaceBound> list = new ArrayList<FaceBound>();
	private SurfaceGeometry surfGeometry;
	
	// ADVANCED_FACE ( 'NONE', ( #2 ), #130, .F. )
	// second param almost always only one
	public AdvancedFace(String lineId) {
		super(lineId);
		String advFaceVal = linesMap.get(lineId);
		String faceBoundLineNums[] = RegExp.getValueBetweenDoubleParentheses(advFaceVal).split(",");
		for (String faceBoundLineNum : Arrays.asList(faceBoundLineNums)) {
			String faceBoundLineVal = linesMap.get(faceBoundLineNum.trim());
			if (faceBoundLineVal.startsWith(FaceOuterBound._FACE_OUTER_BOUND)) {
				list.add(new FaceOuterBound(faceBoundLineNum));
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
	
	public LinkedList<EdgeCurve> getSortedEdgeCurves() {
		LinkedList<EdgeCurve> sorted = new LinkedList<EdgeCurve>();
		if (list.size() > 1) {
			throw new RuntimeException("getSortedEdgeCurves :: more than one");
		}
		List<EdgeCurve> unsorted = list.get(0).getEdgeLoop().getEdgeCurves();
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
		for (EdgeCurve ec : sorted) {
			System.out.println(ec.getStartPoint() + " " + ec.getEndPoint());
		}
		System.out.println("____");
		return null;
	}
	
	private EdgeCurve getNextEdge(EdgeCurve curr, List<EdgeCurve> unsorted) {
		for (EdgeCurve ec : unsorted) {
			if (!ec.getLineId().equals(curr.getLineId())) {
				if (curr.getEndPoint().getLineId().equals(ec.getStartPoint().getLineId())
						|| curr.getEndPoint().getLineId().equals(ec.getEndPoint().getLineId())) {
					return ec;
				}
			}
		}
		throw new RuntimeException("getNextEdge not found");
	}
	
	
	public boolean isRectangle() {
		if (list.size() == 4) {
			for (FaceBound fb : list) {
				// fb.getEdgeLoop().getEdgeCurve().getEdgeGeometry()
			}
		}
		return false;
	}

}

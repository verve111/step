package entities;

import java.util.ArrayList;
import java.util.List;

import utils.RegExp;

import keepers.CircularList;
import keepers.ClosedShellKeeper;

public abstract class FaceBoundAbstract extends AbstractEntity {

	private EdgeLoop el;
	
	// FACE_OUTER_BOUND ( 'NONE', #101, .T. )
	// or
	// FACE_BOUND ( 'NONE', #101, .T. ) 
	// FACE_BOUND == inner bound
	public FaceBoundAbstract(String lineId) {
		super(lineId);
		String faceBoundVal = linesMap.get(lineId);
		el = new EdgeLoop(RegExp.getParameter(faceBoundVal, 2, 3), lineId);
	}

	public EdgeLoop getEdgeLoop() {
		return el;
	} 
	
	public List<EdgeCurve> getEdgeCurves() {
		return el.getEdgeCurves();
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
	
	public List<AdvancedFace> getAdjacents() {
		List<AdvancedFace> res = new ArrayList<AdvancedFace>();
		List<EdgeCurve> li = getEdgeCurves();
		for (EdgeCurve ec : li) {
			for (String ref : ec.getOuterRefs()) {
				if (!ref.equals(this.getLineId())) {
					res.add(ClosedShellKeeper.get().getAdvancedFaceByFaceBoundId(ref));
				}
			}
		}
		return res;
	}
	
	public boolean areAdjacentsXZOriented() {
		boolean res = true;
		for (AdvancedFace af : getAdjacents()) {
			res &= af.getSurfGeometry().getDirection().isZXOriented();
		}
		return res;
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
	
	public boolean isTriangle() {
		CircularList<EdgeCurve> li = getSortedEdgeCurves();
		if (li.isAllLines() && li.size() == 3) {
			/*for (int i = 0; i < li.size(); i++) {
				if (li.get(i).getEdgeGeometry().getDirection().isPerpendicular(li.getNext(i).getEdgeGeometry().getDirection())) {
					return true;
				}
			}*/
			return true;
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
	
	public boolean hasTopChamfers() {
		boolean res = true;
		float angle = 0;
		int i = 0;
		for (AdvancedFace af : getAdjacents()) {
			if (!af.isPlane()) {
				continue;
			}
			// positive dot product for acute (sharp angle), positive for obtuse
			float currAngle = af.getSurfGeometry().getDirection().getDotProduct(0, 1, 0);
			if (i++ == 0) {
				angle = currAngle;
			} else {
				res &= (angle == currAngle && angle > 0);
			}
		}
		return res;
	}
	
}

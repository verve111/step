package entities;

import java.util.ArrayList;
import java.util.List;

import keepers.CartesianPointKeeper;

import utils.RegExp;
import utils.CommonUtils;

public class EdgeCurve extends AbstractEntity implements Cloneable {

	public static final String _EDGE_CURVE = "EDGE_CURVE";
	private List<String> outerRefs = new ArrayList<String>();
	private EdgeGeometry eg;
	private CartesianPoint cp1, cp2;
	
	// EDGE_CURVE ( 'NONE', #94, #96, #170, .T. ) ;
	// name, vertex_point1, vertex_point2, edge_geometry
	public EdgeCurve(String lineId) {
		super(lineId);
		String edgeCurveVal = linesMap.get(lineId);
		// VERTEX_POINT ( 'NONE', #191 ) 
		cp1 = CartesianPointKeeper.getCartesianPoint(CommonUtils.getCartesianPointIdFromVertexPointId(
				RegExp.getParameter(edgeCurveVal, 2, 5), linesMap));
		cp2 = CartesianPointKeeper.getCartesianPoint(CommonUtils.getCartesianPointIdFromVertexPointId(
				RegExp.getParameter(edgeCurveVal, 3, 5), linesMap));
		String edgeGeomId = RegExp.getParameter(edgeCurveVal, 4, 5);
		String edgeGeomVal = linesMap.get(edgeGeomId);
		if (edgeGeomVal.startsWith(Line._LINE)) {
			eg = new Line(edgeGeomId);
		} else if (edgeGeomVal.startsWith(Circle._CIRCLE)) {
			eg = new Circle(edgeGeomId);
		} else if (edgeGeomVal.startsWith(Ellipse._ELLIPSE)) {
			eg = new Ellipse(edgeGeomId);			
		} else if (edgeGeomVal.startsWith("B_SPLINE_CURVE_WITH_KNOTS") || edgeGeomVal.startsWith("(B_SPLINE_CURVE")) {
			// empty
		} else {
			System.out.println("___not found edge geometry " + edgeGeomId);
		}
	}
	
	public void addOuterRef(String entityId) {
		outerRefs.add(entityId);
	}
	
	public List<String> getOuterRefs() {
		return outerRefs;
	}

	@Override
	public String getEntityName() {
		return _EDGE_CURVE;
	}
	
	@Override
	public String toString() {
		return getLineId() + ", refs: " + CommonUtils.join(outerRefs, ", ");
	}
	
	public EdgeGeometry getEdgeGeometry() {
		return eg;
	}

	public CartesianPoint getStartPoint() {
		return cp1;
	}

	public CartesianPoint getEndPoint() {
		return cp2;
	}
	
	public EdgeCurve getClonedRotatedEdge() {
		EdgeCurve ec = null;
		try {
			ec = (EdgeCurve)this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		ec.cp1 = this.cp2;
		ec.cp2 = this.cp1;
		return ec;
	}
}
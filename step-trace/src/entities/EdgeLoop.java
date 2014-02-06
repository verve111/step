package entities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import keepers.EdgeCurveKeeper;

import utils.RegExp;

public class EdgeLoop extends AbstractEntity {

	public static final String _EDGE_LOOP = "EDGE_LOOP";
	private List<EdgeCurve> edgeCurves;
	
	// EDGE_LOOP ( 'NONE', ( #111, #113, #112, #114 ) )
	// name, (set)
	public EdgeLoop(String lineId, String advFaceLineId) {
		super(lineId);
		edgeCurves = new ArrayList<EdgeCurve>();
		String edgeLoopVal = linesMap.get(lineId);
		for (String orientedEdgeId : Arrays.asList(RegExp.getValueBetweenDoubleParentheses(edgeLoopVal).split(","))) {
			// ORIENTED_EDGE ( 'NONE', *, *, #30, .T. ) ;
			String orientedEdgeLine = linesMap.get(orientedEdgeId.trim());
			String edgeCurveId = RegExp.getParameter(orientedEdgeLine, 3, 4);
			EdgeCurve ec = EdgeCurveKeeper.getEdgeCurve(edgeCurveId);
			ec.addOuterRef(advFaceLineId);
			edgeCurves.add(ec);
		}
	}
	
	@Override
	public String getEntityName() {
		return _EDGE_LOOP;
	}

	public List<EdgeCurve> getEdgeCurves() {
		return edgeCurves;
	}
}

package entities;

import utils.RegExp;


public class FaceOuterBound extends AbstractEntity implements FaceBound {

	public static final String _FACE_OUTER_BOUND = "FACE_OUTER_BOUND";
	private EdgeLoop el;
	
	// FACE_OUTER_BOUND ( 'NONE', #101, .T. ) 
	public FaceOuterBound(String lineId) {
		super(lineId);
		String fobVal = linesMap.get(lineId);
		el = new EdgeLoop(RegExp.getParameter(fobVal, 2, 3));
	}
	
	@Override
	public String getEntityName() {
		return _FACE_OUTER_BOUND;
	}

	public EdgeLoop getEdgeLoop() {
		return el;
	}

}

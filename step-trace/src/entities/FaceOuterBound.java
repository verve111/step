package entities;

import utils.RegExp;


public class FaceOuterBound extends FaceBoundAbstract {

	public static final String _FACE_OUTER_BOUND = "FACE_OUTER_BOUND";
	
	// FACE_OUTER_BOUND ( 'NONE', #101, .T. ) 
	public FaceOuterBound(String lineId, String advFaceLineId) {
		super(lineId);
		String fobVal = linesMap.get(lineId);
		el = new EdgeLoop(RegExp.getParameter(fobVal, 2, 3), advFaceLineId);
	}
	
	@Override
	public String getEntityName() {
		return _FACE_OUTER_BOUND;
	}


}

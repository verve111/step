package entities;

import utils.RegExp;


public class FaceBound extends FaceBoundAbstract {

	public static final String _FACE_BOUND = "FACE_BOUND";

	
	// FACE_BOUND ( 'NONE', #101, .T. ) 
	// FACE_BOUND == inner bound
	public FaceBound(String lineId, String advFaceLineId) {
		super(lineId);
		String fobVal = linesMap.get(lineId);
		el = new EdgeLoop(RegExp.getParameter(fobVal, 2, 3), advFaceLineId);
	}
	
	@Override
	public String getEntityName() {
		return _FACE_BOUND;
	}

}

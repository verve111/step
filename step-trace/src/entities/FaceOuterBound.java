package entities;


public class FaceOuterBound extends AbstractEntity implements FaceBound {

	public static final String _FACE_OUTER_BOUND = "FACE_OUTER_BOUND";
	
	// FACE_OUTER_BOUND ( 'NONE', #101, .T. ) 
	public FaceOuterBound(String lineId) {
		super(lineId);
	}
	
	@Override
	public String getEntityName() {
		return _FACE_OUTER_BOUND;
	}

}

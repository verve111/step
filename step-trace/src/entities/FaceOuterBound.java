package entities;

public class FaceOuterBound extends FaceBoundAbstract {

	public static final String _FACE_OUTER_BOUND = "FACE_OUTER_BOUND";
	
	public FaceOuterBound(String lineId) {
		super(lineId);
	}
	
	@Override
	public String getEntityName() {
		return _FACE_OUTER_BOUND;
	}
}

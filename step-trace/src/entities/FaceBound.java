package entities;

public class FaceBound extends FaceBoundAbstract {

	public FaceBound(String lineId) {
		super(lineId);
	}


	public static final String _FACE_BOUND = "FACE_BOUND";

	
	@Override
	public String getEntityName() {
		return _FACE_BOUND;
	}

}

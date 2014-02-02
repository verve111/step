package entities;


public class Axis2Placement3D extends AbstractEntity {
	
	public static final String _AXIS2_PLACEMENT_3D = "AXIS2_PLACEMENT_3D";
	
	public Axis2Placement3D(String lineId) {
		super(lineId);
		System.out.println(getEntityName() + lineId);
		
	}
	
	@Override
	public String getEntityName() {
		return _AXIS2_PLACEMENT_3D;
	}
}

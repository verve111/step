package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.RegExp;

public class ClosedShell extends AbstractEntity {

	public final static String _CLOSED_SHELL = "CLOSED_SHELL";
	private List<AdvancedFace> list = new ArrayList<AdvancedFace>();
	
	public List<AdvancedFace> getAdvancedFace() {
		return list;
	}

	// CLOSED_SHELL ( 'NONE', ( #22, #19, #24, #23, #21, #20 ) ) 
	public ClosedShell(String lineId) {
		super(lineId);
		String val = RegExp.getValueBetweenDoubleParentheses(linesMap.get(lineId));
		for (String advFaceId : Arrays.asList(val.split(","))) {
			list.add(new AdvancedFace(advFaceId.trim()));
		}
	}

	@Override
	public String getEntityName() {
		return _CLOSED_SHELL;
	}
	
	public void getParallel() {
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			for (AdvancedFace aFinner : list) {
				if (af.getLineId().equals(aFinner.getLineId())) {
					continue;
				}
				Axis2Placement3D a2p3DInner = aFinner.getSurfGeometry().getAxis2Placement3D();
				if (a2p3D.getAxis().equals(a2p3DInner.getAxis()) && a2p3D.getRef_direction().equals(a2p3DInner.getRef_direction())) {
					//System.out.println("da " + af.getLineId() + ", " + aFinner.getLineId());
				}
			}
		}
	}
	
	public AdvancedFace getBottom() {
		for (AdvancedFace af : list) {
			Axis2Placement3D a2p3D = af.getSurfGeometry().getAxis2Placement3D();
			if (a2p3D.getCartesianPoint().isNull(2) && a2p3D.getAxis().isYOriented()) {
				return af;
			}
		}
		return null;
	}

}

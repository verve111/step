package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.RegExp;

public class AdvancedFace extends AbstractEntity {
	
	public static final String _ADVANCED_FACE = "ADVANCED_FACE";
	private List<FaceBound> list = new ArrayList<FaceBound>();
	private SurfaceGeometry surfGeometry;
	
	// ADVANCED_FACE ( 'NONE', ( #2 ), #130, .F. )
	public AdvancedFace(String lineId) {
		super(lineId);
		String advFaceVal = linesMap.get(lineId);
		String faceBoundLineNums[] = RegExp.getValueBetweenDoubleParentheses(advFaceVal).split(",");
		for (String faceBoundLineNum : Arrays.asList(faceBoundLineNums)) {
			String faceBoundLineVal = linesMap.get(faceBoundLineNum.trim());
			if (faceBoundLineVal.startsWith(FaceOuterBound._FACE_OUTER_BOUND)) {
				list.add(new FaceOuterBound(faceBoundLineNum));
			} else {
				System.out.println("___not found face outer bound");
			}
		}
		String surfGeometryLineNum = RegExp.getParameter(advFaceVal, 3, 4);
		String surfGeometryLineVal = linesMap.get(surfGeometryLineNum);
		if (surfGeometryLineVal.startsWith(Plane._PLANE)) {
			surfGeometry = new Plane(surfGeometryLineNum);
		} else {
			System.out.println("___not found surface geometry");
		}
	}
		
	@Override
	public String getEntityName() {
		return _ADVANCED_FACE;
	}
	
	public SurfaceGeometry getSurfGeometry() {
		return surfGeometry;
	}

}

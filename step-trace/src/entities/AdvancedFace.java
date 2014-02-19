package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.RegExp;

public class AdvancedFace extends AbstractEntity {
	
	public static final String _ADVANCED_FACE = "ADVANCED_FACE";
	private List<FaceBoundAbstract> list = new ArrayList<FaceBoundAbstract>();
	private SurfaceGeometry surfGeometry;
	
	// ADVANCED_FACE ( 'NONE', ( #2 ), #130, .F. )
	// second param almost always only one
	public AdvancedFace(String advFaceLineId) {
		super(advFaceLineId);
		String advFaceVal = linesMap.get(advFaceLineId);
		String faceBoundLineNums[] = RegExp.getValueBetweenDoubleParentheses(advFaceVal).split(",");
		for (String faceBoundLineNum : Arrays.asList(faceBoundLineNums)) {
			faceBoundLineNum = faceBoundLineNum.trim();
			String faceBoundLineVal = linesMap.get(faceBoundLineNum.trim());
			if (faceBoundLineVal.startsWith(FaceOuterBound._FACE_OUTER_BOUND)) {
				list.add(new FaceOuterBound(faceBoundLineNum));
			} else if (faceBoundLineVal.startsWith(FaceBound._FACE_BOUND)) {
				list.add(new FaceBound(faceBoundLineNum));
			} else {
				System.out.println("___not found face outer bound " + faceBoundLineNum);
			}
		}
		String surfGeometryLineNum = RegExp.getParameter(advFaceVal, 3, 4);
		String surfGeometryLineVal = linesMap.get(surfGeometryLineNum);
		if (surfGeometryLineVal.startsWith(Plane._PLANE)) {
			surfGeometry = new Plane(surfGeometryLineNum);
		} else if (surfGeometryLineVal.startsWith(CylindricalSurface._CYLINDRICAL_SURFACE)) {
			surfGeometry = new CylindricalSurface(surfGeometryLineNum);
		} else if (surfGeometryLineVal.startsWith(ConicalSurface._CONICAL_SURFACE)) {
			surfGeometry = new ConicalSurface(surfGeometryLineNum);			
		} else {
			System.out.println("___not found surface geometry " + surfGeometryLineNum);
		}
	}
	
	@Override
	public String getEntityName() {
		return _ADVANCED_FACE;
	}
	
	public SurfaceGeometry getSurfGeometry() {
		return surfGeometry;
	}
	
	public boolean isPlane() {
		return getSurfGeometry() instanceof Plane;
	}
	
	public FaceBoundAbstract getFaceOuterBound() {
		int i = 0;
		FaceBoundAbstract res = null;
		for (FaceBoundAbstract fba : list) {
			if (fba instanceof FaceOuterBound) {
				i++;
				res = fba;
			}
		}
		if (i > 1) {
			throw new RuntimeException("getFaceOuterBound > 1 outer bounds");
		}
		return res;
	}

	public List<FaceBoundAbstract> getFaceInnerBound() {
		List<FaceBoundAbstract> res = new ArrayList<FaceBoundAbstract>();
		for (FaceBoundAbstract fba : list) {
			if (fba instanceof FaceBound) {
				res.add(fba);
			}
		}
		return res;
	}
}

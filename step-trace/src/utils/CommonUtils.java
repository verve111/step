package utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import keepers.CartesianPointKeeper;
import keepers.EdgeCurveKeeper;
import entities.AdvancedFace;
import entities.CartesianPoint;
import entities.ClosedShell;
import entities.FaceOuterBound;

public class CommonUtils {
	
	public static final String _PATH_FLAT = "c:/1/nonrot_flat/";
	public static final String _PATH_CUB = "c:/1/nonrot_cubic/";
	public static final String _PATH_LONG = "c:/1/nonrot_long/";
	public static final String _PATH_ROTAT = "c:/1/rotat/";
	public static final String _PATH_PRODUCTION = "c:/2/";

	public static String join(List<String> list, String delim) {
		StringBuilder sb = new StringBuilder();
		String loopDelim = "";
		for (String s : list) {
			sb.append(loopDelim);
			sb.append(s);
			loopDelim = delim;
		}
		return sb.toString();
	}
	
	public static String getCartesianPointIdFromVertexPointId(String vpId, Map<String, String> linesMap) {
		// VERTEX_POINT ( 'NONE', #191 ) 
		return RegExp.getParameter(linesMap.get(vpId), 2, 2);
	}
	
	public static String getDirectionIdByVectorId(String vecId, Map<String, String> linesMap) {
		// VECTOR ( 'NONE', #172, 1000.000000000000000 ) ; 
		return RegExp.getParameter(linesMap.get(vecId), 2, 3);
	}
	
	public static float toFloat(String s) {
		return new BigDecimal(s).setScale(7, BigDecimal.ROUND_HALF_UP).floatValue();
	}
	
	public static float toFloat(float s) {
		return new BigDecimal(s).setScale(6, BigDecimal.ROUND_HALF_UP).floatValue();
	}
	
	public static boolean isOrtoParallelep(ClosedShell cs) {
		boolean res = true;
		for (AdvancedFace af : cs.getAdvancedFaces()) {
			res &= af.getFaceOuterBound().isRectangle();
		}
		return res;
	}
	
	public static boolean arePlanesEqualAlongZ(FaceOuterBound fob, FaceOuterBound fob2) {
		int vertexAmount = fob.getAllPoints().size();
		int i = 0;
		for (CartesianPoint p : fob.getAllPoints()) {
			for (CartesianPoint pInner : fob2.getAllPoints()) {
				if (p.getX() == pInner.getX() && p.getY() == pInner.getY()) {
					i++;
					continue;
				}
			}
		}
		return vertexAmount == i;
	}
	
	public static void clearMaps() {
		EdgeCurveKeeper.clearAll();
		CartesianPointKeeper.clearAll();
	}

}

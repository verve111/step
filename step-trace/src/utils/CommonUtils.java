package utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import keepers.CartesianPointKeeper;
import keepers.EdgeCurveKeeper;

import entities.AdvancedFace;
import entities.ClosedShell;

public class CommonUtils {
	
	public static final String _PATH = "c:/1/nonrot_cubic/"; 

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
	
	public static void clearMaps() {
		EdgeCurveKeeper.clearAll();
		CartesianPointKeeper.clearAll();
	}

}

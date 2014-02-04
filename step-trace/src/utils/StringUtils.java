package utils;

import java.util.List;
import java.util.Map;

public class StringUtils {

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

}

package keepers;

import java.util.HashMap;
import java.util.Map;

import entities.EdgeCurve;

public class EdgeCurveKeeper {
	
	private static Map<String, EdgeCurve> map = new HashMap<String, EdgeCurve>();
	
	public static EdgeCurve getEdgeCurve(String lineNum) {
		EdgeCurve res = map.get(lineNum);
		if (res == null) {
			res = new EdgeCurve(lineNum);
			map.put(lineNum, res);
		}
		return res;
	}
	
	public static int size() {
		return map.size();
	}
	
	public static void clearAll() {
		map.clear();
	}
	
}

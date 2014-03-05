package keepers;

import java.util.HashMap;
import java.util.Map;

import entities.CartesianPoint;

public class CartesianPointKeeper {
	
	private static Map<String, CartesianPoint> map = new HashMap<String, CartesianPoint>();
	private static MaxMeasures maxMeasures;

	public static CartesianPoint getCartesianPoint(String lineNum) {
		CartesianPoint res = map.get(lineNum);
		if (res == null) {
			res = new CartesianPoint(lineNum);
			map.put(lineNum, res);
		}
		return res;
	}
	
	public static Map<String, CartesianPoint> getMap() {
		return map;
	}
	
	public static MaxMeasures getMaxShapeMeasures() {
		if (maxMeasures != null) {
			return maxMeasures;
		}
		CartesianPoint p = map.values().iterator().next();
		float minX = p.getX(), maxX = p.getX(), minY = p.getY(), maxY = p.getY(), minZ = p.getZ(), maxZ = p.getZ();
		for (CartesianPoint cp : map.values()) {
			if (cp.getX() < minX) {
				minX = cp.getX();
			} else if (cp.getX() > maxX) {
				maxX = cp.getX();
			}
			if (cp.getY() < minY) {
				minY = cp.getY();
			} else if (cp.getY() > maxY) {
				maxY = cp.getY();
			}
			if (cp.getZ() < minZ) {
				minZ = cp.getZ();
			} else if (cp.getZ() > maxZ) {
				maxZ = cp.getZ();
			}
		}
		maxMeasures = new MaxMeasures(maxZ - minZ, maxY - minY, maxX - minX, minY, minZ, maxZ);
		return maxMeasures;
	}
	
	public static void clearAll() {
		maxMeasures = null;
		map.clear();
	}
}


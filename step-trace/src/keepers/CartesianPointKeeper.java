package keepers;

import java.util.HashMap;
import java.util.Map;

import entities.CartesianPoint;

public class CartesianPointKeeper {
	
	private static Map<String, CartesianPoint> map = new HashMap<String, CartesianPoint>();
	
	public static CartesianPoint getCartesianPoint(String lineNum) {
		CartesianPoint res = map.get(lineNum);
		if (res == null) {
			res = new CartesianPoint(lineNum);
			map.put(lineNum, res);
		}
		return res;
	}
	
	/*public static Map<String, CartesianPoint> getMap() {
		return map;
	}*/
	
	public static MaxMeasures getMaxShapeMeasures() {
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
		return new MaxMeasures(maxX - minX, maxY - minY, maxZ - minZ);
	}
	
	public static void clearAll() {
		map.clear();
	}

}


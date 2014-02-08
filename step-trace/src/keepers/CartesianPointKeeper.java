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
		double minX = Double.valueOf(p.getX()), maxX = Double.valueOf(p.getX()), minY = Double.valueOf(p.getY()), maxY = Double.valueOf(p
				.getY()), minZ = Double.valueOf(p.getZ()), maxZ = Double.valueOf(p.getZ());
		for (CartesianPoint cp : map.values()) {
			if (Double.valueOf(cp.getX()) < minX) {
				minX = Double.valueOf(cp.getX());
			} else if (Double.valueOf(cp.getX()) > maxX) {
				maxX = Double.valueOf(cp.getX());
			}
			if (Double.valueOf(cp.getY()) < minY) {
				minY = Double.valueOf(cp.getY());
			} else if (Double.valueOf(cp.getY()) > maxY) {
				maxY = Double.valueOf(cp.getY());
			}			
			if (Double.valueOf(cp.getZ()) < minZ) {
				minZ = Double.valueOf(cp.getZ());
			} else if (Double.valueOf(cp.getZ()) > maxZ) {
				maxZ = Double.valueOf(cp.getZ());
			}
		}
		return new MaxMeasures(maxX - minX, maxY - minY, maxZ - minZ);
	}
	
	public static void clearAll() {
		map.clear();
	}

}


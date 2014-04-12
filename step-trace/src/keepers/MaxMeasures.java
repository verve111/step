package keepers;

public class MaxMeasures {
	public double maxWidth, maxLength, maxHeight;
	public float minY, minZ, maxZ, minX;

	public MaxMeasures(double maxLength, double maxHeight, double maxWidth, float minY, float minZ, float maxZ, float minX) {
		this.maxLength = maxLength > maxWidth ? maxLength : maxWidth;
		this.maxHeight = maxHeight;
		this.maxWidth = maxWidth < maxLength ? maxWidth : maxLength;
		this.minY = minY;
		this.minZ = minZ;
		this.maxZ = maxZ;
		this.minX = minX;
	}
	
	@Override
	public String toString() {
		return "maxLength " + maxLength + ", maxWidth " + maxWidth + ", maxHeight " + maxHeight;
	}
}
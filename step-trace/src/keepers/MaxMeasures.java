package keepers;

public class MaxMeasures {
	public double maxWidth, maxLength, maxHeight;
	public float minY, minZ, maxZ;

	public MaxMeasures(double maxLength, double maxHeight, double maxWidth, float minY, float minZ, float maxZ) {
		this.maxLength = maxLength;
		this.maxHeight = maxHeight;
		this.maxWidth = maxWidth;
		this.minY = minY;
		this.minZ = minZ;
		this.maxZ = maxZ;
	}
}
import keepers.CartesianPointKeeper;
import keepers.MaxMeasures;
import utils.StepFileReader;
import entities.AdvancedFace;
import entities.ClosedShell;

public class Main {
	
	private static ClosedShell cs;

	public static void main(String[] arr) {
		System.out.println("-----start ");
		StepFileReader sfr = new StepFileReader("c:/1/triangle.STEP");
		cs = new ClosedShell(sfr.getClosedShellLineId());
		AdvancedFace bottom = cs.getBottom();
		if (bottom != null) {
			// non rotational
			System.out.println("non rotational");
			MaxMeasures m = CartesianPointKeeper.getMaxShapeMeasures();
			if (m.maxLength / m.maxWidth <= 3 && m.maxLength / m.maxHeight >= 4) {
				// flat
				System.out.println("flat");
				if (bottom.isRectangle()) {
					System.out.println("bottom rectangle");
					if (cs.getAdvancedFaces().size() == 6) {
						if (isOrtoParallelep()) {
							System.out.println("final: " + "60x0x");
							return;
						}
					}
				} else if (bottom.isRightAngledTriangle()) {
					System.out.println("bottom irightAngledTriangle");
					if (bottom.areAdjacentsXZOriented()) {
						System.out.println("final: " + "61x0x");
						return;
					}
				}
			} else if (m.maxLength / m.maxWidth > 3) {
				// long
				System.out.println("long");
			} else if (m.maxLength / m.maxWidth <= 3 && m.maxLength / m.maxHeight < 4) {
				// cubic
				System.out.println("cubic");
			}
		}
		System.out.println("-----done. ");
	}
	
	private static boolean isOrtoParallelep() {
		boolean res = true;
		for (AdvancedFace af : cs.getAdvancedFaces()) {
			res &= af.isRectangle();
		}
		return res;
	}

}



import keepers.CartesianPointKeeper;
import keepers.MaxMeasures;
import utils.CommonUtils;
import utils.StepFileReader;
import entities.AdvancedFace;
import entities.ClosedShell;

public class Main {
	
	public static void main(String[] arr) {
		System.out.println("-----start ");
		StepFileReader sfr = new StepFileReader(CommonUtils._PATH + "circular_and_ortogonal.STEP");
		ClosedShell cs = new ClosedShell(sfr.getClosedShellLineId());
		AdvancedFace bottom = cs.getBottom();
		if (bottom != null) {
			// non rotational
			System.out.println("non rotational");
			MaxMeasures m = CartesianPointKeeper.getMaxShapeMeasures();
			if (m.maxLength / m.maxWidth <= 3 && m.maxLength / m.maxHeight >= 4) {
				// flat
				System.out.println("flat");
				if (bottom.isRectangle()) {
					System.out.println("bottom: rectangle");
					if (cs.getAdvancedFaces().size() == 6) {
						if (CommonUtils.isOrtoParallelep(cs)) {
							System.out.println("final: " + "60x0x");
							return;
						}
					}
				} else if (bottom.isRightAngledTriangle()) {
					System.out.println("bottom: rightAngledTriangle");
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
	
}



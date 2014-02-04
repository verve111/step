import keepers.CartesianPointKeeper;
import keepers.MaxMeasures;
import utils.StepFileReader;
import entities.AdvancedFace;
import entities.ClosedShell;

public class Main {

	public static void main(String[] arr) {
		System.out.println("-----start ");
		StepFileReader sfr = new StepFileReader("c:/1/parallelep.STEP");
		ClosedShell cs = new ClosedShell(sfr.getClosedShellLineId());
		if (cs.getBottom() != null) {
			// non rotational
			System.out.println("non rotational");
			MaxMeasures m = CartesianPointKeeper.getMaxShapeMeasures();
			if (m.maxLength / m.maxWidth <= 3 && m.maxLength / m.maxHeight >= 4) {
				// flat
				System.out.println("flat");
				if (cs.getBottom().isRectangle()) {
					System.out.println("bottom rectangle");
					if (cs.getAdvancedFace().size() == 6) {
						for (AdvancedFace af : cs.getAdvancedFace()) {
							System.out.println("isrect " + af.isRectangle());
						}
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
		// cs.getBottom();

		System.out.println("-----done. ");
	}

}

import keepers.CartesianPointKeeper;
import keepers.MaxMeasures;
import utils.CommonUtils;
import utils.StepFileReader;
import entities.AdvancedFace;
import entities.ClosedShell;

public class Main {
	
	public static void main(String[] arr) {
		System.out.println("-----start ");
		int firstDigit = -1, secondDigit = -1, thirdDigit = -1, fourthDigit = -1;
		StepFileReader sfr = new StepFileReader(CommonUtils._PATH + "main-hole.STEP");
		ClosedShell cs = new ClosedShell(sfr.getClosedShellLineId());
		AdvancedFace bottom = cs.getBottomPlane();
		if (bottom != null) {
			// non rotational
			System.out.println("non rotational");
			MaxMeasures m = CartesianPointKeeper.getMaxShapeMeasures();
			if (m.maxLength / m.maxWidth <= 3 && m.maxLength / m.maxHeight >= 4) {
				// flat
				firstDigit = 6;
				System.out.println("flat");
				if (bottom.isRectangle()) {
					System.out.println("bottom: rectangle");
					if (bottom.areAdjacentsXZOriented()) {
						secondDigit = 0;
					}
				} else if (bottom.isRightAngledTriangle()) {
					System.out.println("bottom: rightAngledTriangle");
					if (bottom.areAdjacentsXZOriented()) {
						secondDigit = 1;
					}
				} else if (bottom.isAllAnglesTheSame()) {
					System.out.println("bottom: same angled");
					if (bottom.areAdjacentsXZOriented()) {
						secondDigit = 2;
					}
				} else if (bottom.isCircularAndOrtogonal()) {
					System.out.println("bottom: CircularAndOrtogonal");
					if (bottom.areAdjacentsXZOriented()) {
						secondDigit = 3;
					}
				} else if (bottom.areAdjacentsXZOriented()) {
					secondDigit = 4;
				} else {
					secondDigit = 9;
				}
				if (0 <= secondDigit && secondDigit <= 4) {
					int k = cs.getYOrientedFacesCount(); 
					if (k == 2) {
						if (cs.getTopPlane().hasTopChamfers()) {
							fourthDigit = 1;
							System.out.println("machining: has chambers");
						} else {
							fourthDigit = 0;
							System.out.println("machining: no machining");
						}
					} else if (k == 3) {
						fourthDigit = 2;
						System.out.println("machining: stepped 2");
					} else if (k > 3) {
						fourthDigit = 3;
						System.out.println("machining: stepped > 2");
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
		System.out.println("-----done: " + firstDigit + secondDigit + thirdDigit + fourthDigit + "x");
	}
	
}



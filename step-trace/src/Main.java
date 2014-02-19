import keepers.CartesianPointKeeper;
import keepers.ClosedShellKeeper;
import keepers.MaxMeasures;
import utils.CommonUtils;
import utils.StepFileReader;
import entities.AdvancedFace;
import entities.ClosedShell;
import entities.FaceBoundAbstract;

public class Main {
	
	public static void main(String[] arr) {
		System.out.println("-----start ");
		int firstDigit = -1, secondDigit = -1, thirdDigit = -1, fourthDigit = -1;
		StepFileReader sfr = new StepFileReader(CommonUtils._PATH + "holes4-chamfers.STEP");
		ClosedShell cs = new ClosedShell(sfr.getClosedShellLineId());
		ClosedShellKeeper.set(cs);
		AdvancedFace bottom = cs.getBottomPlane();
		if (bottom != null) {
			// non rotational
			System.out.println("non rotational");
			MaxMeasures m = CartesianPointKeeper.getMaxShapeMeasures();
			if (m.maxLength / m.maxWidth <= 3 && m.maxLength / m.maxHeight >= 4) {
				// flat
				firstDigit = 6;
				System.out.println("flat");
				if (bottom.getFaceOuterBound().isRectangle()) {
					System.out.println("bottom: rectangle");
					if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
						secondDigit = 0;
					}
				} else if (bottom.getFaceOuterBound().isRightAngledTriangle()) {
					System.out.println("bottom: rightAngledTriangle");
					if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
						secondDigit = 1;
					}
				} else if (bottom.getFaceOuterBound().isAllAnglesTheSame()) {
					System.out.println("bottom: same angled");
					if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
						secondDigit = 2;
					}
				} else if (bottom.getFaceOuterBound().isCircularAndOrtogonal()) {
					System.out.println("bottom: CircularAndOrtogonal");
					if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
						secondDigit = 3;
					}
				} else if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
					secondDigit = 4;
				} else {
					secondDigit = 9;
				}
				if (0 <= secondDigit && secondDigit <= 9) {
					int k = cs.getYOrientedFacesCount(); 
					if (k == 2) {
						if (cs.getTopPlane().getFaceOuterBound().hasTopChamfers()) {
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
				if (0 <= secondDigit && secondDigit <= 9) {
					int innerBounds = bottom.getFaceInnerBound().size();
					if (innerBounds == 0) {
						thirdDigit = 0;
					} else if (innerBounds == 1) {
						for (FaceBoundAbstract faceBound : bottom.getFaceInnerBound()) {
							if (faceBound.areAdjacentsXZOriented()) {
								thirdDigit = 1;
								System.out.println("one principal bore");
							}
						}
					} else if (innerBounds == 2) {
						boolean res = true;
						for (FaceBoundAbstract faceBound : bottom.getFaceInnerBound()) {
							res &= faceBound.areAdjacentsXZOriented();
						}
						if (res) {
							thirdDigit = 4;
							System.out.println("two principal bores, parallel");							
						}
					} else if (innerBounds > 2) {
						boolean res = true;
						for (FaceBoundAbstract faceBound : bottom.getFaceInnerBound()) {
							res &= faceBound.areAdjacentsXZOriented();
						}
						if (res) {
							thirdDigit = 5;
							System.out.println("several principal bores, parallel");							
						} else {
							thirdDigit = 6;
							System.out.println("several principal bores, non parallel");
						}
					} else {
						thirdDigit = 9;
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



package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import keepers.CartesianPointKeeper;
import keepers.EdgeCurveKeeper;
import keepers.MaxMeasures;

import org.junit.After;
import org.junit.Test;

import utils.CommonUtils;
import utils.StepFileReader;
import entities.AdvancedFace;
import entities.ClosedShell;

public class NonRotationalFlat {
	
	private AdvancedFace getBottom(String fileName) {
		StepFileReader sfr = new StepFileReader(CommonUtils._PATH + fileName);
		ClosedShell cs = new ClosedShell(sfr.getClosedShellLineId());
		AdvancedFace bottom = cs.getBottom();
		assertNotNull("Bottom plane is not found", bottom);
		MaxMeasures m = CartesianPointKeeper.getMaxShapeMeasures();
		assertTrue("Not a flat shape", (m.maxLength / m.maxWidth <= 3 && m.maxLength / m.maxHeight >= 4));
		return bottom;
	}
	
	@Test	
	public void ortoParallelepiped() {
		AdvancedFace bottom = getBottom("orto_parallelepiped.STEP");
		assertTrue("Amount of faces != 6", bottom.getClosedShell().getAdvancedFaces().size() == 6);
		assertTrue("Not an ortoParallelepiped", CommonUtils.isOrtoParallelep(bottom.getClosedShell()));
	}

	@Test	
	public void ortoParallelepipedRotatedBottom() {
		AdvancedFace bottom = getBottom("orto_parallelepiped_rotated_bottom.STEP");
		assertTrue("Amount of faces != 6", bottom.getClosedShell().getAdvancedFaces().size() == 6);
		assertTrue("Not an ortoParallelepiped", CommonUtils.isOrtoParallelep(bottom.getClosedShell()));
	}
	
	@Test	
	public void rightAngledTriangle() {
		assertTrue("Not a rightangled triangle", getBottom("right_triangle.STEP").isRightAngledTriangle());
	}
	
	@Test	
	public void circularAndOrtogonal() {
		assertTrue("Not a CircularAndOrtogonal", getBottom("circular_and_ortogonal.STEP").isCircularAndOrtogonal());
	}
	
	@Test	
	public void circularAndOrtogonalRotatedBottom() {
		assertTrue("Not a CircularAndOrtogonal", getBottom("circular_and_ortogonal_rotated_bottom.STEP").isCircularAndOrtogonal());
	}
	
	@Test	/* shestiugolnik*/
	public void hexahedron() {
		assertTrue("Not a hexahedron", getBottom("hexahedron.STEP").isAllAnglesTheSame());
	}
	
	@After
	public void clearStatics() {
		EdgeCurveKeeper.clearAll();
		CartesianPointKeeper.clearAll();
	}
}

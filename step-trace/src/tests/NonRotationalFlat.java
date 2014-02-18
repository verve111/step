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
	
	private ClosedShell cs;
	
	private AdvancedFace getBottom(String fileName) {
		StepFileReader sfr = new StepFileReader(CommonUtils._PATH + fileName);
		cs = new ClosedShell(sfr.getClosedShellLineId());
		AdvancedFace bottom = cs.getBottomPlane();
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
		AdvancedFace b = getBottom("orto_parallelepiped_rotated_bottom.STEP");
		assertTrue("Not an ortoParallelepiped", b.isRectangle());
		assertTrue("Not XZ oriented", b.areAdjacentsXZOriented());
	}
	
	@Test	
	public void rightAngledTriangle() {
		AdvancedFace b = getBottom("right_triangle.STEP");
		assertTrue("Not a rightangled triangle", b.isRightAngledTriangle());
		assertTrue(b.areAdjacentsXZOriented());
	}
	
	@Test	
	public void circularAndOrtogonal() {
		AdvancedFace b = getBottom("circular_and_ortogonal.STEP");
		assertTrue("Not a CircularAndOrtogonal", b.isCircularAndOrtogonal());
		assertTrue(b.areAdjacentsXZOriented());
	}
	
	@Test	
	public void circularAndOrtogonalRotatedBottom() {
		AdvancedFace b = getBottom("circular_and_ortogonal_rotated_bottom.STEP");
		assertTrue("Not a CircularAndOrtogonal", b.isCircularAndOrtogonal());
		assertTrue(b.areAdjacentsXZOriented());
	}
	
	@Test	/* shestiugolnik*/
	public void hexahedron() {
		AdvancedFace b = getBottom("hexahedron.STEP");
		assertTrue("Not a hexahedron", b.isAllAnglesTheSame());
		assertTrue(b.areAdjacentsXZOriented());
	}
	
	@Test
	public void twoStepped() {
		AdvancedFace b = getBottom("two-stepped.STEP");
		assertTrue("Not two-stepped", cs.getYOrientedFacesCount() == 3);
		assertTrue(b.areAdjacentsXZOriented());
	}
	
	@Test
	public void threeStepped() {
		AdvancedFace b = getBottom("three-stepped.STEP");
		assertTrue("Not three-stepped", cs.getYOrientedFacesCount() == 4);
		assertTrue(b.areAdjacentsXZOriented());
	}
	
	@Test
	public void chamfers() {
		AdvancedFace b = getBottom("chamfers.STEP");
		assertTrue("Top should not be stepped", cs.getYOrientedFacesCount() == 2);
		assertTrue(b.areAdjacentsXZOriented());
		assertTrue(cs.getTopPlane().hasTopChamfers());
	}
	
	@Test
	public void chamfersCircularAndOrto() {
		AdvancedFace b = getBottom("chamfers_circular_orto.STEP");
		assertTrue("Top should not be stepped", cs.getYOrientedFacesCount() == 2);
		assertTrue(b.areAdjacentsXZOriented());
		assertTrue(cs.getTopPlane().hasTopChamfers());
	}
	
	@After
	public void clearStatics() {
		EdgeCurveKeeper.clearAll();
		CartesianPointKeeper.clearAll();
	}
}

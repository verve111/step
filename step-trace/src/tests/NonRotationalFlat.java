package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import keepers.CartesianPointKeeper;
import keepers.MaxMeasures;
import main.Main;

import org.junit.After;
import org.junit.Test;

import utils.CommonUtils;
import utils.StepFileReader;
import entities.AdvancedFace;
import entities.ClosedShell;
import entities.FaceBoundAbstract;

public class NonRotationalFlat {
	
	private ClosedShell cs;
	
	private AdvancedFace getBottom(String fileName) {
		StepFileReader sfr = new StepFileReader(CommonUtils._PATH_FLAT + fileName);
		cs = new ClosedShell(sfr.getClosedShellLineId());
		AdvancedFace bottom = cs.getBottomPlane();
		assertNotNull("Bottom plane is not found", bottom);
		MaxMeasures m = CartesianPointKeeper.getMaxShapeMeasures();
		assertTrue("Not a flat shape", (m.maxLength / m.maxWidth <= 3 && m.maxLength / m.maxHeight >= 4));
		return bottom;
	}
	
	@Test	
	public void ortoParallelepiped() {
		getBottom("orto_parallelepiped.STEP");
		assertTrue("Amount of faces != 6", cs.getAdvancedFaces().size() == 6);
		assertTrue("Not an ortoParallelepiped", CommonUtils.isOrtoParallelep(cs));
	}

	@Test	
	public void ortoParallelepipedRotatedBottom() {
		AdvancedFace b = getBottom("orto_parallelepiped_rotated_bottom.STEP");
		assertTrue("Not an ortoParallelepiped", b.getFaceOuterBound().isRectangle());
		assertTrue("Not XZ oriented", b.getFaceOuterBound().areAdjacentsXZOriented());
	}
	
	@Test	
	public void rightAngledTriangle() {
		AdvancedFace b = getBottom("right_triangle.STEP");
		assertTrue("Not a rightangled triangle", b.getFaceOuterBound().isTriangle());
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
	}
	
	@Test	
	public void circularAndOrtogonal() {
		AdvancedFace b = getBottom("circular_and_ortogonal.STEP");
		assertTrue("Can not be curved machining", !cs.hasUpperMachining());
		assertTrue("Not a CircularAndOrtogonal", b.getFaceOuterBound().isCircularAndOrtogonal());
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
	}
	
	@Test	
	public void circularAndOrtogonalRotatedBottom() {
		AdvancedFace b = getBottom("circular_and_ortogonal_rotated_bottom.STEP");
		assertTrue("Not a CircularAndOrtogonal", b.getFaceOuterBound().isCircularAndOrtogonal());
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
	}
	
	@Test	/* shestiugolnik*/
	public void hexahedron() {
		AdvancedFace b = getBottom("hexahedron.STEP");
		assertTrue("Not a hexahedron", b.getFaceOuterBound().isAllAnglesTheSame());
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
	}
	
	@Test
	public void twoStepped() {
		AdvancedFace b = getBottom("two-stepped.STEP");
		assertTrue("Not two-stepped", cs.getYOrientedPlaneFacesCount() == 3);
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
	}
	
	@Test
	public void threeStepped() {
		AdvancedFace b = getBottom("three-stepped.STEP");
		assertTrue("Not three-stepped", cs.getYOrientedPlaneFacesCount() == 4);
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
	}
	
	@Test
	public void chamfers() {
		AdvancedFace b = getBottom("chamfers.STEP");
		assertTrue("Top should not be stepped", cs.getYOrientedPlaneFacesCount() == 2);
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
		assertTrue(cs.getTopPlane().getFaceOuterBound().hasTopChamfers());
	}
	
	@Test
	public void chamfersCircularAndOrto() {
		AdvancedFace b = getBottom("chamfers_circular_orto.STEP");
		assertTrue("Top should not be stepped", cs.getYOrientedPlaneFacesCount() == 2);
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
		assertTrue(cs.getTopPlane().getFaceOuterBound().hasTopChamfers());
	}
	
	@Test	
	public void oneHole() {
		AdvancedFace b = getBottom("main-hole.STEP");
		assertTrue(cs.getThroughHolesCount() == 1);
		for (FaceBoundAbstract faceBound : b.getFaceInnerBound()) {
			assertTrue(faceBound.areAdjacentsXZOriented());
		}
	}
	
	@Test	
	public void oneHoleThroughXNormal() {
		getBottom("basic with through hole z.STEP");
		assertTrue(cs.getThroughHolesCount() == 1);
	}
	
	@Test	
	public void twoHoles() {
		AdvancedFace b = getBottom("holes2.STEP");
		assertTrue(cs.getThroughHolesCount() == 2);
		for (FaceBoundAbstract faceBound : b.getFaceInnerBound()) {
			assertTrue(faceBound.areAdjacentsXZOriented());
		}
	}
	
	@Test	
	public void fourHoles() {
		AdvancedFace b = getBottom("holes4.STEP");
		assertTrue(cs.getThroughHolesCount() == 4);
		for (FaceBoundAbstract faceBound : b.getFaceInnerBound()) {
			assertTrue(faceBound.areAdjacentsXZOriented());
		}
	}
	
	@Test
	public void groove() {
		getBottom("groove flat non rot.STEP");
		assertTrue("grooves not found", Main.hasGroove(false, cs));
	}
	
	@Test
	public void grooveSimple() {
		getBottom("simple groove.STEP");
		assertTrue("grooves not found", Main.hasGroove(false, cs));
	}
	
	@Test	
	public void curvedTop() {
		AdvancedFace b = getBottom("curved top.STEP");
		assertTrue("Is not curved machining", cs.hasUpperMachining());
		assertTrue("Not a rectangle", b.getFaceOuterBound().isRectangle());
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
	}
	
	@After
	public void clearStatics() {
		CommonUtils.clearMaps();
	}
}

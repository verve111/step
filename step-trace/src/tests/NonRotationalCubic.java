package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import keepers.CartesianPointKeeper;
import keepers.MaxMeasures;

import org.junit.After;
import org.junit.Test;

import utils.CommonUtils;
import utils.StepFileReader;
import entities.AdvancedFace;
import entities.ClosedShell;
import entities.FaceBoundAbstract;

public class NonRotationalCubic {
	
	private ClosedShell cs;
	
	private AdvancedFace getBottom(String fileName) {
		StepFileReader sfr = new StepFileReader(CommonUtils._PATH_CUB + fileName);
		cs = new ClosedShell(sfr.getClosedShellLineId());
		AdvancedFace bottom = cs.getBottomPlane();
		assertNotNull("Bottom plane is not found", bottom);
		MaxMeasures m = CartesianPointKeeper.getMaxShapeMeasures();
		assertTrue("Not a flat shape", (m.maxLength / m.maxWidth <= 3 && m.maxLength / m.maxHeight < 4));
		return bottom;
	}
	
	@Test	
	public void basic() {
		getBottom("cubic_basic.STEP");
		assertTrue("Amount of faces != 6", cs.getAdvancedFaces().size() == 6);
		assertTrue("Not an ortoParallelepiped", CommonUtils.isOrtoParallelep(cs));
		assertTrue("Can not be stepped", cs.getYOrientedPlaneFacesCount() == 2);
		assertTrue("Can not have chamfers", !cs.getTopPlane().getFaceOuterBound().hasTopChamfers());		
	}
	
	@Test	
	public void triangle() {
		AdvancedFace b = getBottom("triangle.STEP");
		assertTrue("Not a triangle", b.getFaceOuterBound().isTriangle());
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
	}
	
	@Test	
	public void rectPrisms() {
		AdvancedFace b = getBottom("orto prisms.STEP");
		assertTrue("Not a rect prisms", b.getFaceOuterBound().isRecangPrismatic());
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
		AdvancedFace b = getBottom("stepped.STEP");
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
	public void curvedTop() {
		AdvancedFace b = getBottom("curved top surface.STEP");
		assertTrue("Is not curved machining", cs.hasUpperMachining());
		assertTrue("Not a rectangle", b.getFaceOuterBound().isRectangle());
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
	}
	
	
	@Test	
	public void oneHole() {
		AdvancedFace b = getBottom("one-bore.STEP");
		assertTrue(cs.getThroughHolesCount() == 1);
		for (FaceBoundAbstract faceBound : b.getFaceInnerBound()) {
			assertTrue(faceBound.areAdjacentsXZOriented());
		}
	}
	
	@Test	
	public void twoHoles() {
		AdvancedFace b = getBottom("two-bores.STEP");
		assertTrue(cs.getThroughHolesCount() == 2);
		for (FaceBoundAbstract faceBound : b.getFaceInnerBound()) {
			assertTrue(faceBound.areAdjacentsXZOriented());
		}
	}
	
	@Test	
	public void threeHoles() {
		AdvancedFace b = getBottom("three-bores.STEP");
		assertTrue(cs.getThroughHolesCount() == 3);
		for (FaceBoundAbstract faceBound : b.getFaceInnerBound()) {
			assertTrue(faceBound.areAdjacentsXZOriented());
		}
	}
	
	@After
	public void clearStatics() {
		CommonUtils.clearMaps();
	}

}

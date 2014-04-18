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
import entities.FaceOuterBound;

public class NonRotationalLong {
	private ClosedShell cs;

	private void initClosedShell(String fileName) {
		StepFileReader sfr = new StepFileReader(CommonUtils._PATH_LONG + fileName);
		cs = new ClosedShell(sfr.getClosedShellLineId());
		MaxMeasures m = CartesianPointKeeper.getMaxShapeMeasures();
		assertTrue("Not a long shape", (m.maxLength / m.maxWidth > 3));
	}

	private AdvancedFace getFront() {
		AdvancedFace front = cs.getFrontPlane();
		assertNotNull("Front plane is not found", front);
		return front;
	}

	private AdvancedFace getBack() {
		AdvancedFace back = cs.getBackPlane();
		assertNotNull("Back plane is not found", back);
		return back;
	}
	
	private AdvancedFace getBottom() {
		AdvancedFace bottom = cs.getBottomPlane();
		assertNotNull("Bottom plane is not found", bottom);
		return bottom;
	}

	@Test
	public void basic() {
		initClosedShell("rectang basic.STEP");
		FaceOuterBound frontFob = getFront().getFaceOuterBound();
		FaceOuterBound backFob = getBack().getFaceOuterBound();
		FaceOuterBound bottomFob = getBottom().getFaceOuterBound();
		assertTrue("Front plane can not be circle", !frontFob.isCircle());
		assertTrue("Back plane can not be circle", !backFob.isCircle());
		assertTrue("Not equal planes", CommonUtils.arePlanesEqualAlongZ(frontFob, backFob));
		assertTrue("Not a rectangular", frontFob.isRectangle() && backFob.isRectangle() && bottomFob.isRectangle());
	}

	@Test
	public void trianle() {
		initClosedShell("triangle.STEP");
		FaceOuterBound frontFob = getFront().getFaceOuterBound();
		FaceOuterBound backFob = getBack().getFaceOuterBound();
		assertTrue("Front plane can not be circle", !frontFob.isCircle());
		assertTrue("Back plane can not be circle", !backFob.isCircle());
		assertTrue("Not equal planes", CommonUtils.arePlanesEqualAlongZ(frontFob, backFob));
		assertTrue(
				"Not a triangle",
				frontFob.isTriangle() && backFob.isTriangle() && frontFob.areAdjacentsXYOriented()
						&& backFob.areAdjacentsXYOriented());

	}
	
	@Test
	public void rectangVaryingCrossSection() {
		initClosedShell("rectang varying cross section.STEP");
		FaceOuterBound frontFob = getFront().getFaceOuterBound();
		FaceOuterBound backFob = getBack().getFaceOuterBound();
		assertTrue("Front plane can not be circle", !frontFob.isCircle());
		assertTrue("Back plane can not be circle", !backFob.isCircle());
		assertTrue("Not equal planes", !CommonUtils.arePlanesEqualAlongZ(frontFob, backFob));
		assertTrue("Not a rectangle", frontFob.isRectangle() && backFob.isRectangle());
	}
	
	@Test
	public void triangVaryingCrossSection() {
		initClosedShell("triangle varying cross section.STEP");
		FaceOuterBound frontFob = getFront().getFaceOuterBound();
		FaceOuterBound backFob = getBack().getFaceOuterBound();
		assertTrue("Front plane can not be circle", !frontFob.isCircle());
		assertTrue("Back plane can not be circle", !backFob.isCircle());
		assertTrue("Not equal planes", !CommonUtils.arePlanesEqualAlongZ(frontFob, backFob));
		assertTrue("Not a triangle", frontFob.isTriangle() && backFob.isTriangle());
	}
	
	@Test
	public void hexaVaryingCrossSection() {
		initClosedShell("hexa varying cross section.STEP");
		FaceOuterBound frontFob = getFront().getFaceOuterBound();
		FaceOuterBound backFob = getBack().getFaceOuterBound();
		assertTrue("Front plane can not be circle", !frontFob.isCircle());
		assertTrue("Back plane can not be circle", !backFob.isCircle());
		assertTrue("Not equal planes", !CommonUtils.arePlanesEqualAlongZ(frontFob, backFob));
	}
	
	@Test
	public void twoStepped() {
		initClosedShell("two-stepped.STEP");
		FaceOuterBound frontFob = getFront().getFaceOuterBound();
		FaceOuterBound backFob = getBack().getFaceOuterBound();
		FaceOuterBound bottomFob = getBottom().getFaceOuterBound();
		assertTrue("Front plane can not be circle", !frontFob.isCircle());
		assertTrue("Back plane can not be circle", !backFob.isCircle());
		assertTrue("Not equal planes", CommonUtils.arePlanesEqualAlongZ(frontFob, backFob));
		assertTrue("Not a rectangular", frontFob.isRectangle() && backFob.isRectangle() && bottomFob.isRectangle());
		AdvancedFace b = getBottom();
		assertTrue("Not two-stepped", cs.getYOrientedPlaneFacesCount() == 3);
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
	}
	
	@Test
	public void oneHole() {
		initClosedShell("one-hole.STEP");
		FaceOuterBound frontFob = getFront().getFaceOuterBound();
		FaceOuterBound backFob = getBack().getFaceOuterBound();
		FaceOuterBound bottomFob = getBottom().getFaceOuterBound();
		assertTrue("Front plane can not be circle", !frontFob.isCircle());
		assertTrue("Back plane can not be circle", !backFob.isCircle());
		assertTrue("Not equal planes", CommonUtils.arePlanesEqualAlongZ(frontFob, backFob));
		assertTrue("Not a rectangular", frontFob.isRectangle() && backFob.isRectangle() && bottomFob.isRectangle());
		AdvancedFace b = getBottom();
		assertTrue(cs.getThroughHolesCount() == 1);
		for (FaceBoundAbstract faceBound : b.getFaceInnerBound()) {
			assertTrue(faceBound.areAdjacentsXZOriented());
		}
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
	}

	@Test
	public void twoHoles() {
		initClosedShell("two-holes.STEP");
		FaceOuterBound frontFob = getFront().getFaceOuterBound();
		FaceOuterBound backFob = getBack().getFaceOuterBound();
		FaceOuterBound bottomFob = getBottom().getFaceOuterBound();
		assertTrue("Front plane can not be circle", !frontFob.isCircle());
		assertTrue("Back plane can not be circle", !backFob.isCircle());
		assertTrue("Not equal planes", CommonUtils.arePlanesEqualAlongZ(frontFob, backFob));
		assertTrue("Not a rectangular", frontFob.isRectangle() && backFob.isRectangle() && bottomFob.isRectangle());
		AdvancedFace b = getBottom();
		assertTrue(cs.getThroughHolesCount() == 2);
		for (FaceBoundAbstract faceBound : b.getFaceInnerBound()) {
			assertTrue(faceBound.areAdjacentsXZOriented());
		}
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
	}
	
	@Test
	public void threeHoles() {
		initClosedShell("three-holes.STEP");
		FaceOuterBound frontFob = getFront().getFaceOuterBound();
		FaceOuterBound backFob = getBack().getFaceOuterBound();
		FaceOuterBound bottomFob = getBottom().getFaceOuterBound();
		assertTrue("Front plane can not be circle", !frontFob.isCircle());
		assertTrue("Back plane can not be circle", !backFob.isCircle());
		assertTrue("Not equal planes", CommonUtils.arePlanesEqualAlongZ(frontFob, backFob));
		assertTrue("Not a rectangular", frontFob.isRectangle() && backFob.isRectangle() && bottomFob.isRectangle());
		AdvancedFace b = getBottom();
		assertTrue(cs.getThroughHolesCount() == 3);
		for (FaceBoundAbstract faceBound : b.getFaceInnerBound()) {
			assertTrue(faceBound.areAdjacentsXZOriented());
		}
		assertTrue(b.getFaceOuterBound().areAdjacentsXZOriented());
	}
	
	@Test
	public void chamfers() {
		initClosedShell("chamfers.STEP");
		FaceOuterBound frontFob = getFront().getFaceOuterBound();
		FaceOuterBound backFob = getBack().getFaceOuterBound();
		FaceOuterBound bottomFob = getBottom().getFaceOuterBound();
		assertTrue("Front plane can not be circle", !frontFob.isCircle());
		assertTrue("Back plane can not be circle", !backFob.isCircle());
		assertTrue("Not equal planes", CommonUtils.arePlanesEqualAlongZ(frontFob, backFob));
		assertTrue("Not a rectangular", frontFob.isRectangle() && backFob.isRectangle() && bottomFob.isRectangle());
		assertTrue(cs.getTopPlane().getFaceOuterBound().hasTopChamfers());
	}
	
	
	@After
	public void clearStatics() {
		CommonUtils.clearMaps();
	}
}

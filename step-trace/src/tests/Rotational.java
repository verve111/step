package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import main.Main;

import org.junit.After;
import org.junit.Test;

import utils.CommonUtils;
import utils.StepFileReader;
import entities.AdvancedFace;
import entities.ClosedShell;

public class Rotational {
	
	private ClosedShell cs;

	private void initClosedShell(String fileName) {
		StepFileReader sfr = new StepFileReader(CommonUtils._PATH_ROTAT + fileName);
		cs = new ClosedShell(sfr.getClosedShellLineId());
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
	
	private AdvancedFace getLeft() {
		AdvancedFace back = cs.getLeftPlane();
		assertNotNull("Left plane is not found", back);
		return back;
	}
	
	@Test
	public void basic() {
		initClosedShell("basic.STEP");
		assertNotNull("Front plane must exist", getFront());
		assertNotNull("Back plane must exist", getBack());
		assertTrue("CylindricalSurfaces count != 1", cs.getCylindricalSurfacesWithoutThroughHoles().size() == 1);
	}

	@Test
	public void grooveRotational() {
		initClosedShell("groove rotational.STEP");
		assertNotNull("Front plane must exist", getFront());
		assertNotNull("Back plane must exist", getBack());		
		assertTrue("CylindricalSurfaces count != 1", cs.getCylindricalSurfacesWithoutThroughHoles().size() == 1);
		assertTrue("grooves not found", Main.hasGroove(true, cs));
	}
	
	@Test
	public void grooveRotBothSides() {
		initClosedShell("groove rot both sides.STEP");
		assertNotNull("Front plane must exist", getFront());
		assertNotNull("Back plane must exist", getBack());
		assertTrue("CylindricalSurfaces count != 3", cs.getCylindricalSurfacesWithoutThroughHoles().size() == 3);
		assertTrue("grooves not found", Main.hasGroove(true, cs));
	}
	
	@Test
	public void rotatSmoothInner() {
		initClosedShell("rotat smooth inner.STEP");
		AdvancedFace frontFob = getFront();
		AdvancedFace backFob = getBack();
		assertNotNull("Front plane must exist", frontFob);
		assertNotNull("Back plane must exist", backFob);
		assertTrue("CylindricalSurfaces count != 1", cs.getCylindricalSurfacesWithoutThroughHoles().size() == 1);
		assertTrue("Front bores != 1", frontFob.getFaceInnerBound().size() == 1);
		assertTrue("Front bores != circle", frontFob.getFaceInnerBound().get(0).isCircle());
		assertTrue("Back bores != 1", backFob.getFaceInnerBound().size() == 1);
		assertTrue("Back bores != circle", backFob.getFaceInnerBound().get(0).isCircle());
		assertTrue("No inner hole found", cs.getThroughHolesCount() == 1);
	}
	
	@Test
	public void rotatSmoothInnerXOriented() {
		initClosedShell("x oriented with inner.STEP");
		AdvancedFace left = getLeft();
		assertNotNull("Left plane must exist", left);
		assertTrue("CylindricalSurfaces count != 1", cs.getCylindricalSurfacesWithoutThroughHoles().size() == 1);
		assertTrue("Left bores != 1", left.getFaceInnerBound().size() == 1);
		assertTrue("Left bores != circle", left.getFaceInnerBound().get(0).isCircle());
	}
	
	@Test
	public void rotSteppedToBothInner() {
		initClosedShell("rot stepped to both inner.STEP");
		AdvancedFace frontFob = getFront();
		AdvancedFace backFob = getBack();
		assertNotNull("Front plane must exist", frontFob);
		assertNotNull("Back plane must exist", backFob);
		assertTrue("CylindricalSurfaces count != 3", cs.getCylindricalSurfacesWithoutThroughHoles().size() == 3);
		assertTrue("Front bores != 1", frontFob.getFaceInnerBound().size() == 1);
		assertTrue("Front bores != circle", frontFob.getFaceInnerBound().get(0).isCircle());
		assertTrue("Back bores != 1", backFob.getFaceInnerBound().size() == 1);
		assertTrue("Back bores != circle", backFob.getFaceInnerBound().get(0).isCircle());
		assertTrue("No inner hole found", cs.getThroughHolesCount() == 1);
	}
	
	@After
	public void clearStatics() {
		CommonUtils.clearMaps();
	}

}

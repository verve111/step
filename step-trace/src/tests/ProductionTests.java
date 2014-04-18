package tests;

import static org.junit.Assert.assertTrue;
import main.Main;

import org.junit.After;
import org.junit.Test;

import utils.CommonUtils;

public class ProductionTests {
	private final static String _PATH = CommonUtils._PATH_PRODUCTION; 
	
	@Test
	public void rotationalYOriented3CylindersWithBottomHole() {
		assertTrue(Main.mainProcedure(_PATH + "1.32-351-7/32-351-7.STEP", true).equals("24001"));
	}
	
	@Test
	public void nonrotLongOneHoleOneGrooveMachining() {
		assertTrue(Main.mainProcedure(_PATH + "2.clamp_04050-05/clamp_04050-05.STP", true).equals("72170"));
	}
	
	@Test
	public void nonrotFlatOneHoleBigCircleMachining() {
		assertTrue(Main.mainProcedure(_PATH + "3.clamp_04030-05/clamp_04030-05.STP", true).equals("60170"));
	}
	
	@Test
	public void rotSmoothWithOneEndSteppedHole() {
		assertTrue(Main.mainProcedure(_PATH + "4.32-230-10-40/32-230-10-40.stp", true).equals("21001"));
	}	
	
	@Test
	public void nonrotFlatOneHoleUpperMachiningGroove() {
		assertTrue(Main.mainProcedure(_PATH + "5.clamp_04070-06/clamp_04070-06.stp", true).equals("69050"));
	}
	
	@Test
	public void rotOneThroughHoleWithChamfers() {
		assertTrue(Main.mainProcedure(_PATH + "6.214654-6/214654-6.stp", true).equals("21100"));
	}
	
	@Test
	public void rotOneSteppedToOneHoleWithChamfers() {
		assertTrue(Main.mainProcedure(_PATH + "7.goupille_din_7978_6_x_20/goupille_din_7978_6_x_20.stp", true).equals("21000"));
	}	
	
	
	@After
	public void clearStatics() {
		CommonUtils.clearMaps();
	}
}

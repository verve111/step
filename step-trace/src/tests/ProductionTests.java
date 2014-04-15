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
		assertTrue(Main.mainProcedure(_PATH + "32-351-7/32-351-7.STEP", true).equals("20000"));
	}
	
	@After
	public void clearStatics() {
		CommonUtils.clearMaps();
	}
}

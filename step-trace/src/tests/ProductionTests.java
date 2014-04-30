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
	public void rotSmoothOneHoleWithChamfers() {
		assertTrue(Main.mainProcedure(_PATH + "7.goupille_din_7978_6_x_20/goupille_din_7978_6_x_20.stp", true).equals("21000"));
	}
	
	@Test
	public void rotSmooth() {
		assertTrue(Main.mainProcedure(_PATH + "8.pin_08940-03x20/pin_08940-03x20.stp", true).equals("21000"));
	}
	
	@Test
	public void rotSmooth2() {
		assertTrue(Main.mainProcedure(_PATH + "9.pin_a_0_6x2_-_nf_e_27-484/pin_a_0_6x2_-_nf_e_27-484.stp", true).equals("21000"));
	}	

	@Test
	public void rotSteppedToOneEndWithChamfers() {
		assertTrue(Main.mainProcedure(_PATH + "10.608-16-4/608-16-4.stp", true).equals("21000"));
	}	

	@Test
	public void rotSteppedToOneEndWithAuxiliary() {
		assertTrue(Main.mainProcedure(_PATH + "11.19772om4_0_5-3/19772om4_0_5-3.stp", true).equals("24001"));
	}	

	@Test
	public void nut() {
		assertTrue(Main.mainProcedure(_PATH + "12.215617-3/215617-3.stp", true).equals("10100"));
	}	
	
	@Test
	public void nut2() {
		assertTrue(Main.mainProcedure(_PATH + "13.23-205-8/23-205-8.stp", true).equals("11100"));
	}
	
	@Test
	public void nutWithWideBottom() {
		assertTrue(Main.mainProcedure(_PATH + "14.626094/626094.stp", true).equals("10100"));
	}

	@Test
	public void nonrotFlat4PerpHoles() {
		assertTrue(Main.mainProcedure(_PATH + "15.gn_910_9-220/gn_910_9-220.stp", true).equals("72570"));
	}
	
	@Test
	public void nonrotFlatGroove() {
		assertTrue(Main.mainProcedure(_PATH + "16.clamp_04080-06/clamp_04080-06.stp", true).equals("72070"));
	}
	
	@Test
	public void nonrotFlatGrooveUpperMach() {
		assertTrue(Main.mainProcedure(_PATH + "17.clamp_04010-05/clamp_04010-05.stp", true).equals("60070"));
	}
	
	@Test
	public void nonrotLongNoBores() {
		assertTrue(Main.mainProcedure(_PATH + "18.clamp_04150-08/clamp_04150-08.stp", true).equals("72070"));
	}
	
	@Test
	public void nonrotLongWithBore() {
		assertTrue(Main.mainProcedure(_PATH + "19.2370_022/2370_022.stp", true).equals("72170"));
	}
	
	@Test
	public void nonrotLongWithBore2() {
		assertTrue(Main.mainProcedure(_PATH + "20.bride_etagee_2321_730/bride_etagee_2321_730.stp", true).equals("75120"));
	}
	
	@Test
	public void nonrotCubicRifledWithHoles() {
		assertTrue(Main.mainProcedure(_PATH + "21.halderv_eh_1582_600/halderv_eh_1582_600.stp", true).equals("85171"));
	}
	
	@Test
	public void nonrotCubicRifledWithHoles2() {
		assertTrue(Main.mainProcedure(_PATH + "22.halderv_eh_1582_800/halderv_eh_1582_800.stp", true).equals("85170"));
	}	
	
	@Test
	public void nonrotCubicRifledWithHoles3() {
		assertTrue(Main.mainProcedure(_PATH + "23.halderv_eh_1583_300/halderv_eh_1583_300.stp", true).equals("85171"));
	}
	
	@Test
	public void nonrotWrong1() {
		assertTrue(Main.mainProcedure(_PATH + "24.c1-662-04-08-7/c1-662-04-08-7.stp", true).equals("8-1-1-10"));
	}
	
	@Test
	public void longNut() {
		assertTrue(Main.mainProcedure(_PATH + "25.coupling_nut_119030055010/coupling_nut_119030055010.stp", true).equals("10100"));
	}
	
	@Test
	public void longManyCylinders() {
		assertTrue(Main.mainProcedure(_PATH + "27.fcs_-_10-6___20-22/fcs_-_10-6___20-22.stp", true).equals("24000"));
	}
	
	@Test
	public void longManyCylindersAuxHole() {
		assertTrue(Main.mainProcedure(_PATH + "28.fcd_-_25-6___31_5-35/fcd_-_25-6___31_5-35.stp", true).equals("14001"));
	}
	
	@Test
	public void long2Cylinders() {
		assertTrue(Main.mainProcedure(_PATH + "30.26100-00800855/26100-00800855.stp", true).equals("24000"));
	}
	
	@Test
	public void long2Cylinders2() {
		assertTrue(Main.mainProcedure(_PATH + "31.26101-01001057/26101-01001057.stp", true).equals("14000"));
	}
	
	@Test
	public void cupWithLongBottom() {
		assertTrue(Main.mainProcedure(_PATH + "32.27750-03502040/27750-03502040.stp", true).equals("75070"));
	}
	
	@Test
	public void longManyCylinders2() {
		assertTrue(Main.mainProcedure(_PATH + "34.431e10000/431e10000.stp", true).equals("24000"));
	}
	
	@Test
	public void longManyCylinders3() {
		assertTrue(Main.mainProcedure(_PATH + "35.431r10000/431r10000.stp", true).equals("14001"));
	}
	
	@Test
	public void nonrotCircularAndOrtoWithBores() {
		assertTrue(Main.mainProcedure(_PATH + "36.ag_bc_750/ag_bc_750.stp", true).equals("63420"));
	}
	
	@Test
	public void nonrotRectWithSteppedBores() {
		assertTrue(Main.mainProcedure(_PATH + "37.ag_bp_750/ag_bp_750.stp", true).equals("60030"));
	}
	
	@Test
	public void nonrotRectWithSteppedBores2() {
		assertTrue(Main.mainProcedure(_PATH + "38.ag_bpr_750/ag_bpr_750.stp", true).equals("60530"));
	}
	
	@Test
	public void nonrotRectWithSteppedBores3() {
		assertTrue(Main.mainProcedure(_PATH + "40.ag_mw_750/ag_mw_750.stp", true).equals("80120"));
	}
	
	@After
	public void clearStatics() {
		CommonUtils.clearMaps();
	}
}

import utils.StepFileReader;
import entities.ClosedShell;

public class Main {

	public static void main(String[] arr) {
		StepFileReader sfr = new StepFileReader("c:/1/parallelep.STEP");
		ClosedShell cs = new ClosedShell(sfr.getClosedShellLineId());
		cs.getParallel();
		System.out.println("done. " );
	}
	
}



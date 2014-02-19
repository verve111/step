package keepers;

import entities.ClosedShell;

public class ClosedShellKeeper {
	
	private static ClosedShell cs;
	
	public static ClosedShell get() {
		return cs;
	}
	
	public static void set(ClosedShell cs) {
		ClosedShellKeeper.cs = cs;
	}

}

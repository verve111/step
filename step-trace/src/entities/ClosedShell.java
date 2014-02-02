package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.RegExp;

public class ClosedShell extends AbstractEntity {

	public final static String _CLOSED_SHELL = "CLOSED_SHELL";
	private List<AdvancedFace> list = new ArrayList<AdvancedFace>();
	
	// CLOSED_SHELL ( 'NONE', ( #22, #19, #24, #23, #21, #20 ) ) 
	public ClosedShell(String lineId) {
		super(lineId);
		String val = RegExp.getValueBetweenDoubleParentheses(linesMap.get(lineId));
		for (String advFaceId : Arrays.asList(val.split(","))) {
			list.add(new AdvancedFace(advFaceId.trim()));
		}
	}

	@Override
	public String getEntityName() {
		return _CLOSED_SHELL;
	}

}

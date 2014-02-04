package utils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import keepers.EdgeCurveKeeper;

import entities.AbstractEntity;
import entities.ClosedShell;

public class StepFileReader {

	private Map<String, String> linesMap;

	public StepFileReader(String fileName) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		linesMap = new HashMap<String, String>();
		try {
			String line = null;
			try {
				line = br.readLine();
				while (line != null) {
					if (line.startsWith("#")) {
						String arr[] = line.split("=");
						// lineNum == '#10'
						String lineNum = arr[0].trim();
						// lineVal == 'AXIS2_PLACEMENT_3D ( 'NONE', #135, #136, #137 )'
						String lineVal = arr[1].substring(0, arr[1].length() - 1).trim();						
						linesMap.put(lineNum, lineVal);
					}
					line = br.readLine();
				}
			} finally {
				br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		AbstractEntity.linesMap = linesMap;
	}
	
	public Map<String, String> getLinesMap() {
		return linesMap;
	}	
	
	public String getClosedShellLineId() {
		int i = 0;
		for (Entry<String, String> e : linesMap.entrySet()) {
			if (e.getValue().startsWith(ClosedShell._CLOSED_SHELL)) {
				if (++i > 1) {
					throw new RuntimeException("more than one CLOSED_SHELL");
				}
				return e.getKey();
			}
		}		
		return null;
	}

}

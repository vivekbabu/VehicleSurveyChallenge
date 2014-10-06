package in.vehicle.survey.linecross.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Constants that represent each hose of the sensor 
 */
public class HoseConstants {
	public static final char HOSE_A = 'A';
	public static final char HOSE_B = 'B';
		
	private static final List<Character> lines = new ArrayList<Character>(
			Arrays.asList(HOSE_A, HOSE_B));

	public static final List<Character> getLines() {
		return lines;
	}
}

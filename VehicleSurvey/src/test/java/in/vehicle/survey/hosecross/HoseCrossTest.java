package in.vehicle.survey.hosecross;

import in.vehicle.survey.hosecross.HoseCross;

import org.junit.Assert;
import org.junit.Test;

public class HoseCrossTest {
	@Test
	public void testGetters() {
		char line = 'A';
		long timestamp = 12121212;

		HoseCross lineCross = new HoseCross('A', 12121212);
		
		Assert.assertEquals(line, lineCross.getHose());
		Assert.assertEquals(timestamp, lineCross.getTimestamp());
	}

	@Test
	public void testToString() {
		char line = 'A';
		long timestamp = 12121212;

		HoseCross lineCross = new HoseCross('A', 12121212);

		Assert.assertEquals("[Hose:" + line + ",Timestamp=" + timestamp + "]",
				lineCross.toString());

	}
}

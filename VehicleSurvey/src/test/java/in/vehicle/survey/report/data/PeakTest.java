package in.vehicle.survey.report.data;

import in.vehicle.survey.utils.CalendarUtils;

import org.junit.Assert;
import org.junit.Test;

public class PeakTest {
	@Test
	public void testGetters() {
		int day = 1;
		int hour = 1;
		int peakValue = 530;

		Peak peak = new Peak(day, hour, peakValue);

		Assert.assertEquals(day, peak.getDay());
		Assert.assertEquals(hour, peak.getHour());
		Assert.assertEquals(peakValue, peak.getPeak());
	}

	@Test
	public void testToString() {
		int day = 1;
		int hour = 1;
		int peakValue = 530;

		Peak peak = new Peak(day, hour, peakValue);

		Assert.assertEquals(
				"[Day:" + day + ",Hour of the day:"
						+ CalendarUtils.getTimeInHourMinuteFormat(hour)
						+ ",Peak:" + peakValue + "]", peak.toString());

	}

}

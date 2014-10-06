package in.vehicle.survey.utils;


import org.junit.Assert;
import org.junit.Test;

public class CalendarUtilsTest {
	@Test
	public void testGetTimeInHourMinuteFormat() {
		long TWO_MINUTES = 2 * 60 * 1000;
		Assert.assertEquals("00:02", CalendarUtils.getTimeInHourMinuteFormat(TWO_MINUTES));
	}
}

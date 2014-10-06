package in.vehicle.survey.reports;

import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;

import org.junit.Assert;
import org.junit.Test;

public class HourAverageDistanceReportTest {
	
	@Test
	public void testGetters() {
		HourAverageDistanceReport hourAverageDistanceReport = getHourAverageDistanceReport();
		Assert.assertEquals(10, hourAverageDistanceReport.getDisplayPostion());
		Assert.assertEquals(ReportConstants.AVERAGE_DISTANCE_BETWEEN_VEHICLES_FOR_EACH_HOUR_IN_METRES,
				hourAverageDistanceReport.getNameOfReport());
		Assert.assertEquals(TimeConstants.MILLIS_PER_HOUR,
				hourAverageDistanceReport.getTimeFrameOfReport());
	}

	private HourAverageDistanceReport getHourAverageDistanceReport() {
		return new HourAverageDistanceReport();
	}
}

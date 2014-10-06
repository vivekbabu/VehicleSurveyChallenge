package in.vehicle.survey.reports;

import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;

import org.junit.Assert;
import org.junit.Test;

public class HalfHourAverageDistanceReportTest {
	@Test
	public void testGetters() {
		HalfHourAverageDistanceReport halfHourAverageDistanceReport = getHalfHourAverageDistanceReport();
		Assert.assertEquals(11, halfHourAverageDistanceReport.getDisplayPostion());
		Assert.assertEquals(ReportConstants.AVERAGE_DISTANCE_BETWEEN_VEHICLES_FOR_EACH_HALF_HOUR_IN_METRES,
				halfHourAverageDistanceReport.getNameOfReport());
		Assert.assertEquals(TimeConstants.MILLIS_PER_HALF_HOUR,
				halfHourAverageDistanceReport.getTimeFrameOfReport());
	}

	private HalfHourAverageDistanceReport getHalfHourAverageDistanceReport() {
		return new HalfHourAverageDistanceReport();
	}
}

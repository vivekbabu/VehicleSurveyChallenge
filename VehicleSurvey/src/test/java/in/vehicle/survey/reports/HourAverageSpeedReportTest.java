package in.vehicle.survey.reports;

import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;

import org.junit.Assert;
import org.junit.Test;

public class HourAverageSpeedReportTest {


	@Test
	public void testGetters() {
		HourAverageSpeedReport hourAverageSpeedReport = getHourAverageSpeedReport();
		Assert.assertEquals(9, hourAverageSpeedReport.getDisplayPostion());
		Assert.assertEquals(ReportConstants.AVERAGE_SPEED_PER_HOUR,
				hourAverageSpeedReport.getNameOfReport());
		Assert.assertEquals(TimeConstants.MILLIS_PER_HOUR,
				hourAverageSpeedReport.getTimeFrameOfReport());
	}

	private HourAverageSpeedReport getHourAverageSpeedReport() {
		return new HourAverageSpeedReport();
	}

}

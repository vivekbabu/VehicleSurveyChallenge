package in.vehicle.survey.reports;

import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;

import org.junit.Assert;
import org.junit.Test;

public class HourAverageCountReportTest {
	
	@Test
	public void testGetters() {
		HourAverageCountReport hourAverageCountReport = new HourAverageCountReport();
		Assert.assertEquals(TimeConstants.MILLIS_PER_HOUR, hourAverageCountReport.getTimeFrameOfReport());
		Assert.assertEquals(6, hourAverageCountReport.getDisplayPostion());
		Assert.assertEquals(ReportConstants.AVERAGE_COUNT_PER_HOUR, hourAverageCountReport.getNameOfReport());
	}
}

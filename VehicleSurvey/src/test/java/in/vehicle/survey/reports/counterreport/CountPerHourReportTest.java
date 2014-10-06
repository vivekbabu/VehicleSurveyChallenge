package in.vehicle.survey.reports.counterreport;

import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;

import org.junit.Assert;
import org.junit.Test;

public class CountPerHourReportTest {
	@Test
	public void testGetters() {
		CountPerHourReport countPerHourReport = new CountPerHourReport();
		Assert.assertEquals(TimeConstants.MILLIS_PER_HOUR,
				countPerHourReport.getTimeFrameOfReport());
		Assert.assertEquals(2, countPerHourReport.getDisplayPostion());
		Assert.assertEquals(ReportConstants.VEHICLE_COUNT_PER_HOUR,
				countPerHourReport.getNameOfReport());
	}
}

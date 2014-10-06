package in.vehicle.survey.reports.counterreport;

import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;

import org.junit.Assert;
import org.junit.Test;

public class CountPerHalfHourReportTest {
	
	@Test
	public void testGetters() {
		CountPerHalfHourReport countPerHalfHourReport = new CountPerHalfHourReport();
		Assert.assertEquals(TimeConstants.MILLIS_PER_HALF_HOUR,
				countPerHalfHourReport.getTimeFrameOfReport());
		Assert.assertEquals(3, countPerHalfHourReport.getDisplayPostion());
		Assert.assertEquals(ReportConstants.VEHICLE_COUNT_PER_HALF_HOUR,
				countPerHalfHourReport.getNameOfReport());
	}
}

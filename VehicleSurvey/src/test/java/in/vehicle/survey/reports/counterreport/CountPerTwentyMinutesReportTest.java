package in.vehicle.survey.reports.counterreport;

import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;

import org.junit.Assert;
import org.junit.Test;

public class CountPerTwentyMinutesReportTest {
	
	@Test
	public void testGetters() {
		CountPerTwentyMinutesReport countPerTwentyMinutesReport = new CountPerTwentyMinutesReport();
		Assert.assertEquals(TimeConstants.MILLIS_PER_TWENTY_MINUTES,
				countPerTwentyMinutesReport.getTimeFrameOfReport());
		Assert.assertEquals(4, countPerTwentyMinutesReport.getDisplayPostion());
		Assert.assertEquals(ReportConstants.VEHICLE_COUNT_PER_TWENTY_MINUTES,
				countPerTwentyMinutesReport.getNameOfReport());
	}
}

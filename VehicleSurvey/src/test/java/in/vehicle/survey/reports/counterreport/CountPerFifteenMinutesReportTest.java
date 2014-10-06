package in.vehicle.survey.reports.counterreport;

import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;

import org.junit.Assert;
import org.junit.Test;

public class CountPerFifteenMinutesReportTest {

	@Test
	public void testGetters() {
		CountPerFifteenMinutesReport countPerFifteenMinutesReport = new CountPerFifteenMinutesReport();
		Assert.assertEquals(TimeConstants.MILLIS_PER_FIFTEEN_MINUTES,
				countPerFifteenMinutesReport.getTimeFrameOfReport());
		Assert.assertEquals(5, countPerFifteenMinutesReport.getDisplayPostion());
		Assert.assertEquals(ReportConstants.VEHICLE_COUNT_PER_FIFTEEN_MINUTES,
				countPerFifteenMinutesReport.getNameOfReport());
	}
}

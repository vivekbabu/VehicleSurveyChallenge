package in.vehicle.survey.reports;

import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;

import org.junit.Assert;
import org.junit.Test;

public class PeakHalfDayCountReportTest {
	
	@Test
	public void testGetters() {
		PeakHalfDayCountReport peakHalfDayCountReport = getPeakHalfDayCountReport();
		Assert.assertEquals(8, peakHalfDayCountReport.getDisplayPostion());
		Assert.assertEquals(ReportConstants.PEAK_HALF_DAY_TRAFFIC,
				peakHalfDayCountReport.getNameOfReport());
		Assert.assertEquals(TimeConstants.MILLIS_PER_HALF_DAY,
				peakHalfDayCountReport.getTimeFrameOfReport());
	}

	private PeakHalfDayCountReport getPeakHalfDayCountReport() {
		return new PeakHalfDayCountReport();
	}
}

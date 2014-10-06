package in.vehicle.survey.reports;

import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;
import in.vehicle.survey.reports.PeakHourCountReport;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class PeakHourCountReportTest {

	@Test
	public void testGetters() {
		PeakHourCountReport peakHourCountReport = getPeakHourCountReport();
		Assert.assertEquals(7, peakHourCountReport.getDisplayPostion());
		Assert.assertEquals(ReportConstants.PEAK_HOUR_TRAFFIC,
				peakHourCountReport.getNameOfReport());
		Assert.assertEquals(TimeConstants.MILLIS_PER_HOUR,
				peakHourCountReport.getTimeFrameOfReport());
	}

	private PeakHourCountReport getPeakHourCountReport() {
		return new PeakHourCountReport();
	}

}

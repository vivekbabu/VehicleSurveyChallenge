package in.vehicle.survey.reports;

import in.vehicle.survey.abstractreports.AbstractPeakTimeReport;
import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;

/**
 * Report for Peak volume half day of all the days in each lane
 */
public class PeakHalfDayCountReport extends AbstractPeakTimeReport implements
		VehicleSurveyReport {
	public String getNameOfReport() {
		return ReportConstants.PEAK_HALF_DAY_TRAFFIC;
	}

	public int getDisplayPostion() {
		return 8;
	}

	public long getTimeFrameOfReport() {
		return TimeConstants.MILLIS_PER_HALF_DAY;
	}
}

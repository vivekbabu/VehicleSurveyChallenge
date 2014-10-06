package in.vehicle.survey.reports;


import in.vehicle.survey.abstractreports.AbstractPeakTimeReport;
import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;

/**
 * Report for Peak volume hour of all the days in each lane
 */
public class PeakHourCountReport extends AbstractPeakTimeReport implements VehicleSurveyReport {

	public String getNameOfReport() {
		return ReportConstants.PEAK_HOUR_TRAFFIC;
	}

	public int getDisplayPostion() {
		return 7;
	}

	public long getTimeFrameOfReport() {
		return TimeConstants.MILLIS_PER_HOUR;
	}

}

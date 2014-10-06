package in.vehicle.survey.reports;

import in.vehicle.survey.abstractreports.AbstractTimeAverageSpeedReport;
import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;

/**
 * Report for Hourly speed distribution of cars in each lane
 */
public class HourAverageSpeedReport extends AbstractTimeAverageSpeedReport
		implements VehicleSurveyReport {

	public String getNameOfReport() {
		return ReportConstants.AVERAGE_SPEED_PER_HOUR;
	}

	public int getDisplayPostion() {
		return 9;
	}

	public long getTimeFrameOfReport() {
		return TimeConstants.MILLIS_PER_HOUR;
	}
}

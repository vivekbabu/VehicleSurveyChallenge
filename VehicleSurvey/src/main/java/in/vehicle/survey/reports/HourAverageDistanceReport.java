package in.vehicle.survey.reports;

import in.vehicle.survey.abstractreports.AbstractTimeAverageDistanceReport;
import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;

/**
 * Report for Hourly Rough distance between cars in each lane
 */
public class HourAverageDistanceReport extends
		AbstractTimeAverageDistanceReport implements VehicleSurveyReport {

	public String getNameOfReport() {
		return ReportConstants.AVERAGE_DISTANCE_BETWEEN_VEHICLES_FOR_EACH_HOUR_IN_METRES;
	}

	public int getDisplayPostion() {
		return 10;
	}

	public long getTimeFrameOfReport() {
		return TimeConstants.MILLIS_PER_HOUR;
	}

}

package in.vehicle.survey.reports;

import in.vehicle.survey.abstractreports.AbstractTimeAverageDistanceReport;
import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;
/**
 * Report for Half hourly Rough distance  between vehicles in each lane 
 */
public class HalfHourAverageDistanceReport extends
		AbstractTimeAverageDistanceReport implements VehicleSurveyReport {

	public String getNameOfReport() {
		return ReportConstants.AVERAGE_DISTANCE_BETWEEN_VEHICLES_FOR_EACH_HALF_HOUR_IN_METRES;
	}

	public int getDisplayPostion() {
		return 11;
	}

	public long getTimeFrameOfReport() {
		return TimeConstants.MILLIS_PER_HALF_HOUR;
	}

}

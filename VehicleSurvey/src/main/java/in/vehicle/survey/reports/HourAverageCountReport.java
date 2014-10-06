package in.vehicle.survey.reports;

import in.vehicle.survey.abstractreports.AbstractTimeAverageCountReport;
import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;
/**
 * Report for Average hourly vehicle count across all days in each lane
 */
public class HourAverageCountReport extends AbstractTimeAverageCountReport
		implements VehicleSurveyReport {

	public String getNameOfReport() {
		return ReportConstants.AVERAGE_COUNT_PER_HOUR;
	}

	public int getDisplayPostion() {
		return 6;
	}

	public long getTimeFrameOfReport() {
		return TimeConstants.MILLIS_PER_HOUR;
	}

}

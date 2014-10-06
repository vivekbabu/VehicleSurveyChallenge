package in.vehicle.survey.reports.counterreport;

import in.vehicle.survey.abstractreports.AbstractCountPerTimeReport;
import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;
import in.vehicle.survey.reports.VehicleSurveyReport;

/**
 * Report for count of vehicles per twenty minutes for all days in each lane
 */
public class CountPerTwentyMinutesReport extends AbstractCountPerTimeReport implements
		VehicleSurveyReport {

	public String getNameOfReport() {
		return ReportConstants.VEHICLE_COUNT_PER_TWENTY_MINUTES;
	}

	public int getDisplayPostion() {
		return 4;
	}

	public long getTimeFrameOfReport() {
		return TimeConstants.MILLIS_PER_TWENTY_MINUTES;
	}

}

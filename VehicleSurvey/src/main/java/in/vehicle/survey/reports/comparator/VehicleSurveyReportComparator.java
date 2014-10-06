package in.vehicle.survey.reports.comparator;

import in.vehicle.survey.reports.VehicleSurveyReport;

import java.util.Comparator;

/**
 * This comparator is used to sort or compare vehicle survey reports. The
 * comparison is based on the display position. The display position value for
 * each report can be varied to reorder the position of the report in the
 * console
 */
public class VehicleSurveyReportComparator implements
		Comparator<VehicleSurveyReport> {
	/**
	 * Compares the two vehicle survey reports. 
	 * Returns -1 if report 1 should come before 2
	 * Returns 1 if report 1 should come after 2
	 * 
	 */
	public int compare(VehicleSurveyReport report1, VehicleSurveyReport report2) {
		return Integer.valueOf(report1.getDisplayPostion()).compareTo(
				report2.getDisplayPostion());
	}

}

package in.vehicle.survey.abstractreports;

import java.util.List;

import in.vehicle.survey.allvehicledata.AllVehiclesData;
import in.vehicle.survey.laneresolver.LaneResolverFactory;
import in.vehicle.survey.report.data.Peak;
import in.vehicle.survey.reports.VehicleSurveyReport;

/**
 * This is the abstract class for generating report for peak count of vehicles for
 * any time periods. Override the getTimeFrame method to specify the required
 * time periods
 */
public abstract class AbstractPeakTimeReport implements VehicleSurveyReport {

	public void generateAndDisplayReport(AllVehiclesData allVehiclesData) {
		List<Character> lanes = LaneResolverFactory.getLanes();
		long timeFrameOfReport = getTimeFrameOfReport();
		for (Character lane : lanes) {
			Peak peak = allVehiclesData.getPeakTime(lane, timeFrameOfReport);
			System.out.println("Peak traffic for lane " + lane + ": " + peak);
		}

	}
}

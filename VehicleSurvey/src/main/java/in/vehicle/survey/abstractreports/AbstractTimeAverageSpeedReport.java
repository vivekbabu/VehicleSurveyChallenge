package in.vehicle.survey.abstractreports;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import in.vehicle.survey.allvehicledata.AllVehiclesData;
import in.vehicle.survey.laneresolver.LaneResolverFactory;
import in.vehicle.survey.linecross.constants.TimeConstants;
import in.vehicle.survey.report.data.Average;
import in.vehicle.survey.reports.VehicleSurveyReport;
import in.vehicle.survey.utils.CalendarUtils;

/**
 * This is the abstract class for generating report for average speed of
 * vehicles for any time periods. Override the getTimeFrame method to specify
 * the required time periods
 */
public abstract class AbstractTimeAverageSpeedReport implements
		VehicleSurveyReport {

	public void generateAndDisplayReport(AllVehiclesData allVehiclesData) {
		List<Character> lanes = LaneResolverFactory.getLanes();
		long timeFrameOfReport = getTimeFrameOfReport();

		System.out.print("|Time|");

		Map<Character, Map<Long, Average>> averageSpeedOfVehicle = new TreeMap<Character, Map<Long, Average>>();

		for (Character lane : lanes) {
			System.out.print("Lane " + lane + "|");

			averageSpeedOfVehicle.put(lane,
					allVehiclesData.getAverageSpeed(lane, timeFrameOfReport));
		}
		long startTime = 0;
		while(startTime < TimeConstants.MILLIS_PER_DAY) {
			System.out.println("");

			System.out.print("| "
					+ CalendarUtils.getTimeInHourMinuteFormat(startTime)
					+ "  |");

			for (Character lane : lanes) {
				Average averageResult = averageSpeedOfVehicle.get(lane).get(
						Long.valueOf(startTime));

				System.out.print("" + averageResult.getAverage() + "|");
			}
			startTime = startTime + timeFrameOfReport;
		}
		System.out.println();
	}
}

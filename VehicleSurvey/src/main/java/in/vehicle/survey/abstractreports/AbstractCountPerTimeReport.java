package in.vehicle.survey.abstractreports;

import java.util.List;
import java.util.Map;

import in.vehicle.survey.allvehicledata.AllVehiclesData;
import in.vehicle.survey.laneresolver.LaneResolverFactory;
import in.vehicle.survey.linecross.constants.TimeConstants;
import in.vehicle.survey.reports.VehicleSurveyReport;
import in.vehicle.survey.utils.CalendarUtils;
import in.vehicle.survey.utils.PrintUtils;
import in.vehicle.survey.vehicle.VehicleData;

/**
 * This is the abstract class for generating report for count of vehicles for
 * any time periods. Override the getTimeFrame method to specify the required
 * time periods
 */
public abstract class AbstractCountPerTimeReport implements VehicleSurveyReport {

	public void generateAndDisplayReport(AllVehiclesData allVehiclesData) {
		List<Integer> days = allVehiclesData.getDaysHavingTraffic();
		List<Character> lanes = LaneResolverFactory.getLanes();
		long timeFrameOfReport = getTimeFrameOfReport();
		for (Integer day : days) {
			PrintUtils.printDividerLine();
			System.out.println("Day " + day);
			PrintUtils.printDividerLine();
			System.out.print("|Time|");

			for (Character lane : lanes) {
				System.out.print("Lane " + lane + "|");
			}

			System.out.println("");
			long startTime = 0;
			while (startTime < TimeConstants.MILLIS_PER_DAY) {
				System.out.print("| "
						+ CalendarUtils.getTimeInHourMinuteFormat(startTime)
						+ "  |");

				for (Character lane : lanes) {
					Map<Integer, List<VehicleData>> vehicleDataInTime = allVehiclesData
							.getVehicleDataInTime(lane, startTime, startTime
									+ timeFrameOfReport - 1);
					List<VehicleData> vehiculeRecords = vehicleDataInTime
							.get(day);
					int number = 0;
					if (vehiculeRecords != null) {
						number = vehiculeRecords.size();

					}
					System.out.print("" + number + "|");

				}
				System.out.println("");
				startTime = startTime + timeFrameOfReport;
			}

		}
	}
}

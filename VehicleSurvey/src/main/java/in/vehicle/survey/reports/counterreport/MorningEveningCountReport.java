package in.vehicle.survey.reports.counterreport;

import java.util.List;
import java.util.Map;

import in.vehicle.survey.allvehicledata.AllVehiclesData;
import in.vehicle.survey.laneresolver.LaneResolverFactory;
import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;
import in.vehicle.survey.reports.VehicleSurveyReport;
import in.vehicle.survey.utils.PrintUtils;
import in.vehicle.survey.vehicle.VehicleData;
/**
 * Report for count of vehicles in morning and evening for all days in each lane
 */
public class MorningEveningCountReport implements VehicleSurveyReport {

	public String getNameOfReport() {
		return ReportConstants.MORNING_VERSUS_EVENING_VEHICLE_COUNT;
	}

	public int getDisplayPostion() {
		return 1;
	}

	public void generateAndDisplayReport(AllVehiclesData allVehiclesData) {
		List<Integer> days = allVehiclesData.getDaysHavingTraffic();
		List<Character> directions = LaneResolverFactory.getLanes();
		long timeFrameOfReport = getTimeFrameOfReport();

		for (Character lane : directions) {

			Map<Integer, List<VehicleData>> morningCount = allVehiclesData
					.getVehicleDataInTime(lane, 0, timeFrameOfReport - 1);
			Map<Integer, List<VehicleData>> eveningCount = allVehiclesData
					.getVehicleDataInTime(lane, timeFrameOfReport,
							2 * timeFrameOfReport - 1);
			System.out.println("Lane " + lane);
			PrintUtils.printDividerLine();
			System.out.println("|Day|Morning|Evening|");
			PrintUtils.printDividerLine();

			for (Integer day : days) {
				int morning = 0;
				int evening = 0;
				if (morningCount.get(day) != null) {
					morning = morningCount.get(day).size();
				}

				if (eveningCount.get(day) != null) {
					evening = eveningCount.get(day).size();
				}
				System.out.println("|" + day + "|" + morning + "|" + evening
						+ "|");
			}

			PrintUtils.printDividerLine();
			System.out.println("");

		}
	}

	public long getTimeFrameOfReport() {
		return TimeConstants.MILLIS_PER_HALF_DAY;
	}

}

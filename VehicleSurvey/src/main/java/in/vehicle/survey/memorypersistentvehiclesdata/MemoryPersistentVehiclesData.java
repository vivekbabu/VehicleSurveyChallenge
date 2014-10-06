package in.vehicle.survey.memorypersistentvehiclesdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import in.vehicle.survey.allvehicledata.AllVehiclesData;
import in.vehicle.survey.hosecross.HoseCross;
import in.vehicle.survey.laneresolver.LaneResolverFactory;
import in.vehicle.survey.linecross.constants.CalculationConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;
import in.vehicle.survey.report.data.Average;
import in.vehicle.survey.report.data.Peak;
import in.vehicle.survey.vehicle.VehicleData;

/**
 * In memory implementation of {@link AllVehiclesData}
 */
public class MemoryPersistentVehiclesData implements AllVehiclesData {

	private List<VehicleData> allVehicleRecords = new ArrayList<VehicleData>();

	public void add(VehicleData vehicleData) {
		allVehicleRecords.add(vehicleData);
	}

	public List<VehicleData> getAllVehiclesData() {
		return Collections.unmodifiableList(allVehicleRecords);
	}

	public Map<Integer, List<VehicleData>> getVehicleDataInTime(char lane,
			long start, long end) {
		Map<Integer, List<VehicleData>> matchingRecords = new TreeMap<Integer, List<VehicleData>>();

		for (VehicleData vehicleData : allVehicleRecords) {
			if (!vehicleData.getHoseCrosses().isEmpty()) {
				List<HoseCross> hoseCorsses = vehicleData.getHoseCrosses();
				long firstHoseCrossTime = hoseCorsses.get(0).getTimestamp();

				if (LaneResolverFactory.getLaneResolver(lane)
						.isreadingMatchingTheLane(hoseCorsses) //
						&& (firstHoseCrossTime >= start && firstHoseCrossTime < end)) {
					Integer day = Integer.valueOf(vehicleData.getDay());
					List<VehicleData> existingRecordsForTheDay = matchingRecords
							.get(day);
					if (existingRecordsForTheDay == null) {
						existingRecordsForTheDay = new ArrayList<VehicleData>();
						matchingRecords.put(day, existingRecordsForTheDay);
					}
					existingRecordsForTheDay.add(vehicleData);
				}
			}
		}
		return matchingRecords;
	}

	public List<Integer> getDaysHavingTraffic() {
		List<Integer> daysHavingTraffic = new ArrayList<Integer>();
		for (VehicleData vehicleData : allVehicleRecords) {
			Integer day = vehicleData.getDay();
			if (!daysHavingTraffic.contains(day))
				daysHavingTraffic.add(day);
		}
		return daysHavingTraffic;
	}

	public Peak getPeakTime(char lane, long timeFrame) {
		Peak currentPeak = new Peak(1, 0, 0);
		for (Integer day : getDaysHavingTraffic()) {
			long currentTime = 0;
			while (currentTime < TimeConstants.MILLIS_PER_DAY) {
				Map<Integer, List<VehicleData>> found = getVehicleDataInTime(
						lane, currentTime, currentTime + timeFrame - 1);
				List<VehicleData> vehiculeRecords = found.get(day);
				int number = vehiculeRecords == null ? 0 : vehiculeRecords
						.size();
				if (number > currentPeak.getPeak()) {
					currentPeak = new Peak(day, currentTime, number);
				}
				currentTime = currentTime + timeFrame;
			}
		}

		return currentPeak;
	}

	public Map<Long, Average> getAverageCount(char lane, long timeFrame) {
		Map<Long, Average> averageResults = new TreeMap<Long, Average>();

		int days = getDaysHavingTraffic().size();
		long currentTime = 0;
		while (currentTime < TimeConstants.MILLIS_PER_DAY) {
			Map<Integer, List<VehicleData>> vehicleDataInGivenTime = getVehicleDataInTime(
					lane, currentTime, currentTime + timeFrame - 1);
			long sum = 0;
			for (Map.Entry<Integer, List<VehicleData>> entry : vehicleDataInGivenTime
					.entrySet()) {
				sum += entry.getValue() == null ? 0 : entry.getValue().size();
			}
			averageResults.put(Long.valueOf(currentTime), new Average(
					currentTime, sum / days));
			currentTime = currentTime + timeFrame;
		}

		return averageResults;
	}

	public Map<Long, Average> getAverageSpeed(char lane, long timeFrame) {
		Map<Long, Average> averageResults = new TreeMap<Long, Average>();
		long currentTime = 0;
		while (currentTime < TimeConstants.MILLIS_PER_DAY) {
			Map<Integer, List<VehicleData>> vehicleDataInGivenTime = getVehicleDataInTime(
					lane, currentTime, currentTime + timeFrame - 1);
			long totalSpeed = 0;
			int count = 0;
			for (Map.Entry<Integer, List<VehicleData>> entry : vehicleDataInGivenTime
					.entrySet()) {
				for (VehicleData vehicleData : entry.getValue()) {
					long time = findTimeBetweenSubsequentLineCross(vehicleData);

					totalSpeed += getSpeed(time);

					count++;
				}
			}
			if (count == 0) {
				averageResults.put(Long.valueOf(currentTime), new Average(
						currentTime, 0));
			} else {
				averageResults.put(Long.valueOf(currentTime), new Average(
						currentTime, totalSpeed / count));
			}
			currentTime = currentTime + timeFrame;
		}

		return averageResults;
	}

	public Map<Long, Average> getAverageDistance(char lane, long timeFrame) {
		Map<Long, Average> averageResults = new TreeMap<Long, Average>();
		long startTime = 0;
		while (startTime < TimeConstants.MILLIS_PER_DAY) {
			Map<Integer, List<VehicleData>> vehicleDataInGivenTime = getVehicleDataInTime(
					lane, startTime, startTime + timeFrame - 1);
			long totalDistance = 0;
			int count = 0;
			for (Map.Entry<Integer, List<VehicleData>> entry : vehicleDataInGivenTime
					.entrySet()) {
				if (entry.getValue().isEmpty()) {
					continue;
				}

				Iterator<VehicleData> vehicaleDataIterator = entry.getValue()
						.iterator();
				VehicleData previousVehicle = vehicaleDataIterator.next();
				while (vehicaleDataIterator.hasNext()) {
					VehicleData vehicleData = vehicaleDataIterator.next();

					long time = findTimeBetweenTwoVehicles(previousVehicle,
							vehicleData);

					totalDistance += getDistanceInMetres(time);

					count++;

					previousVehicle = vehicleData;
				}
			}

			if (count == 0) {
				averageResults.put(Long.valueOf(startTime), new Average(
						startTime, 0));
			} else {
				averageResults.put(Long.valueOf(startTime), new Average(
						startTime, totalDistance / count));
			}
			startTime = startTime + timeFrame;
		}
		return averageResults;
	}

	private float getSpeed(long time) {
		return ((CalculationConstants.WHEEL_BASE_OF_VEHICLE / 1000) * TimeConstants.MILLIS_PER_HOUR)
				/ time;
	}

	protected long findTimeBetweenSubsequentLineCross(VehicleData vehicleData) {
		HoseCross initialHoseCross = null;
		long time = 0;
		for (HoseCross hoseCross : vehicleData.getHoseCrosses()) {
			if (initialHoseCross == null) {
				initialHoseCross = hoseCross;
			} else if (hoseCross.getHose() == initialHoseCross.getHose()) {
				time = hoseCross.getTimestamp()
						- initialHoseCross.getTimestamp();
			}

		}
		return time;
	}

	private float getDistanceInMetres(long time) {
		return (CalculationConstants.AVERAGE_SPEED_OF_VEHICLE_PER_HOUR * 1000 * time)
				/ TimeConstants.MILLIS_PER_HOUR;
	}

	protected long findTimeBetweenTwoVehicles(VehicleData firstVehicle,
			VehicleData secondVehicle) {
		return secondVehicle.getHoseCrosses().get(0).getTimestamp()
				- firstVehicle.getHoseCrosses().get(0).getTimestamp();
	}

}

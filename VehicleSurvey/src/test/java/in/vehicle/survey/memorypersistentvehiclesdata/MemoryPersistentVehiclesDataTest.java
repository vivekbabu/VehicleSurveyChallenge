package in.vehicle.survey.memorypersistentvehiclesdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import in.vehicle.survey.hosecross.HoseCross;
import in.vehicle.survey.linecross.constants.TimeConstants;
import in.vehicle.survey.report.data.Average;
import in.vehicle.survey.report.data.Peak;
import in.vehicle.survey.vehicle.VehicleData;

public class MemoryPersistentVehiclesDataTest {
	private enum AverageEnum {
		COUNT, SPEED, DISTANCE
	}

	@Test
	public void testAddVehicleData() {
		List<VehicleData> allVehicleRecords = new ArrayList<VehicleData>();
		MemoryPersistentVehiclesData memoryPersistentVehiclesData = new MemoryPersistentVehiclesData();
		VehicleData vehicleData = new VehicleData(1);
		allVehicleRecords.add(vehicleData);
		memoryPersistentVehiclesData.add(vehicleData);
		Assert.assertEquals(allVehicleRecords,
				memoryPersistentVehiclesData.getAllVehiclesData());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetAllVehiclesReturnsUnmodifiableList() {
		MemoryPersistentVehiclesData memoryPersistentVehiclesData = new MemoryPersistentVehiclesData();
		memoryPersistentVehiclesData.add(new VehicleData(1));
		memoryPersistentVehiclesData.getAllVehiclesData().add(
				new VehicleData(2));
	}

	@Test
	public void testGetVehicleDataInTime() {
		MemoryPersistentVehiclesData memoryPersistentVehiclesData = new MemoryPersistentVehiclesData();
		Map<Integer, List<VehicleData>> matchingMap = addMatchingVehicleData(memoryPersistentVehiclesData);
		Assert.assertEquals(matchingMap,
				memoryPersistentVehiclesData.getVehicleDataInTime('A', 1, 5));
	}

	@Test
	public void testGetDaysHavingTraffic() {
		MemoryPersistentVehiclesData memoryPersistentVehiclesData = new MemoryPersistentVehiclesData();
		List<Integer> fourDays = addFourDayVehicleData(memoryPersistentVehiclesData);
		Assert.assertEquals(fourDays,
				memoryPersistentVehiclesData.getDaysHavingTraffic());
	}

	@Test
	public void testGetPeakTime() {
		MemoryPersistentVehiclesData memoryPersistentVehiclesData = new MemoryPersistentVehiclesData() {
			@Override
			public Map<Integer, List<VehicleData>> getVehicleDataInTime(
					char lane, long start, long end) {
				Map<Integer, List<VehicleData>> vehicleDataInTime = new HashMap<Integer, List<VehicleData>>();

				vehicleDataInTime.put(
						1,
						new ArrayList<VehicleData>(Arrays
								.asList(new VehicleData(1))));
				vehicleDataInTime.put(
						2,
						new ArrayList<VehicleData>(Arrays.asList(
								new VehicleData(2), new VehicleData(2))));
				if (start == TimeConstants.MILLIS_PER_HOUR) {
					return vehicleDataInTime;
				} else
					return new HashMap<Integer, List<VehicleData>>();
			}

			@Override
			public List<Integer> getDaysHavingTraffic() {
				return new ArrayList<Integer>(Arrays.asList(1, 2));
			}
		};
		Peak peakTime = memoryPersistentVehiclesData.getPeakTime('A',
				TimeConstants.MILLIS_PER_HOUR);
		assertPeakMatchesExpectedValue(peakTime);

	}

	@Test
	public void testGetAverageCount() {
		MemoryPersistentVehiclesData memoryPersistentVehiclesData = getMemoryPersistentVehicleData(AverageEnum.COUNT);
		Map<Long, Average> averageCount = memoryPersistentVehiclesData
				.getAverageCount('Á', TimeConstants.MILLIS_PER_HOUR);
		assertAveragesAreCalculatedProperly(averageCount, AverageEnum.COUNT);
	}

	@Test
	public void testGetAverageSpeed() {
		MemoryPersistentVehiclesData memoryPersistentVehiclesData = getMemoryPersistentVehicleData(AverageEnum.SPEED);
		Map<Long, Average> averageSpeed = memoryPersistentVehiclesData
				.getAverageSpeed('A', TimeConstants.MILLIS_PER_HOUR);
		assertAveragesAreCalculatedProperly(averageSpeed, AverageEnum.SPEED);

	}

	@Test
	public void testGetAverageDistance() {
		MemoryPersistentVehiclesData memoryPersistentVehiclesData = getMemoryPersistentVehicleData(AverageEnum.DISTANCE);
		Map<Long, Average> averageSpeed = memoryPersistentVehiclesData
				.getAverageDistance('A', TimeConstants.MILLIS_PER_HOUR);
		assertAveragesAreCalculatedProperly(averageSpeed, AverageEnum.DISTANCE);

	}

	@Test
	public void testFindTimeBetweenSubsequentLineCross() {
		VehicleData vehicleData = new VehicleData(1);
		vehicleData.addHoseCross(new HoseCross('A', 100));
		vehicleData.addHoseCross(new HoseCross('B', 150));
		vehicleData.addHoseCross(new HoseCross('A', 200));
		vehicleData.addHoseCross(new HoseCross('B', 250));
		MemoryPersistentVehiclesData memoryPersistentVehiclesData = new MemoryPersistentVehiclesData();
		long timeBetweenSubsequentLineCross = memoryPersistentVehiclesData
				.findTimeBetweenSubsequentLineCross(vehicleData);
		Assert.assertEquals(100, timeBetweenSubsequentLineCross);
	}

	@Test
	public void testFindTimeBetweenTwoVehicles() {
		VehicleData vehicleData1 = new VehicleData(1);
		vehicleData1.addHoseCross(new HoseCross('A', 100));
		VehicleData vehicleData2 = new VehicleData(1);
		vehicleData2.addHoseCross(new HoseCross('A', 200));
		MemoryPersistentVehiclesData memoryPersistentVehiclesData = new MemoryPersistentVehiclesData();
		long timeBetweenTwoVehicles = memoryPersistentVehiclesData
				.findTimeBetweenTwoVehicles(vehicleData1, vehicleData2);
		Assert.assertEquals(100, timeBetweenTwoVehicles);
	}

	private MemoryPersistentVehiclesData getMemoryPersistentVehicleData(
			final AverageEnum averageEnum) {
		MemoryPersistentVehiclesData memoryPersistentVehiclesData = new MemoryPersistentVehiclesData() {
			public List<Integer> getDaysHavingTraffic() {
				return new ArrayList<Integer>(Arrays.asList(1, 2, 3));
			}

			public Map<Integer, List<VehicleData>> getVehicleDataInTime(
					char lane, long start, long end) {
				Map<Integer, List<VehicleData>> vehicleDataInTime = new HashMap<Integer, List<VehicleData>>();

				vehicleDataInTime.put(
						1,
						new ArrayList<VehicleData>(Arrays.asList(
								new VehicleData(1), new VehicleData(1))));
				vehicleDataInTime.put(
						2,
						new ArrayList<VehicleData>(Arrays.asList(
								new VehicleData(2), new VehicleData(2),
								new VehicleData(2))));
				vehicleDataInTime.put(
						3,
						new ArrayList<VehicleData>(Arrays.asList(
								new VehicleData(3), new VehicleData(3),
								new VehicleData(3), new VehicleData(3))));
				if (start == TimeConstants.MILLIS_PER_HOUR) {
					return vehicleDataInTime;
				} else
					return new HashMap<Integer, List<VehicleData>>();
			}

			@Override
			protected long findTimeBetweenSubsequentLineCross(
					VehicleData vehiculeRecord) {
				if (averageEnum == AverageEnum.SPEED) {
					return 1000;
				}
				return super.findTimeBetweenSubsequentLineCross(vehiculeRecord);

			}

			@Override
			protected long findTimeBetweenTwoVehicles(VehicleData firstVehicle,
					VehicleData secondVehicle) {
				if (averageEnum == AverageEnum.DISTANCE) {
					return 600;
				}
				return super.findTimeBetweenTwoVehicles(firstVehicle,
						secondVehicle);
			}

		};
		return memoryPersistentVehiclesData;
	}

	private void assertAveragesAreCalculatedProperly(
			Map<Long, Average> averageCount, AverageEnum averageEnum) {
		for (Map.Entry<Long, Average> entry : averageCount.entrySet()) {
			if (entry.getKey() != TimeConstants.MILLIS_PER_HOUR) {
				Assert.assertEquals(0.0f, entry.getValue().getAverage(), 0.0f);
			} else {
				switch (averageEnum) {
				case COUNT:
					Assert.assertEquals(3.0f, entry.getValue().getAverage(),
							0.0f);
					break;
				case SPEED:
					Assert.assertEquals(9.0f, entry.getValue().getAverage(),
							0.0f);
					break;
				case DISTANCE:
					Assert.assertEquals(10.0f, entry.getValue().getAverage(),
							0.0f);
					break;
				default:
				}
			}
		}
	}

	private void assertPeakMatchesExpectedValue(Peak peakTime) {
		Assert.assertEquals(2, peakTime.getDay());
		Assert.assertEquals(TimeConstants.MILLIS_PER_HOUR, peakTime.getHour());
		Assert.assertEquals(2, peakTime.getPeak());
	}

	private Map<Integer, List<VehicleData>> addMatchingVehicleData(
			MemoryPersistentVehiclesData memoryPersistentVehiclesData) {
		Map<Integer, List<VehicleData>> matchingVehicleDataList = new HashMap<Integer, List<VehicleData>>();

		VehicleData matchingVehicleData1 = new VehicleData(1);
		matchingVehicleData1.addHoseCross(new HoseCross('A', 2));
		matchingVehicleData1.addHoseCross(new HoseCross('A', 3));
		matchingVehicleDataList
				.put(1,
						new ArrayList<VehicleData>(Arrays
								.asList(matchingVehicleData1)));
		memoryPersistentVehiclesData.add(matchingVehicleData1);

		VehicleData nonMatchingVehicleData1 = new VehicleData(1);
		nonMatchingVehicleData1.addHoseCross(new HoseCross('A', 8));
		memoryPersistentVehiclesData.add(nonMatchingVehicleData1);

		VehicleData nonMatchingVehicleData2 = new VehicleData(1);
		nonMatchingVehicleData1.addHoseCross(new HoseCross('A', 8));
		nonMatchingVehicleData2.addHoseCross(new HoseCross('B', 9));
		memoryPersistentVehiclesData.add(nonMatchingVehicleData2);

		VehicleData matchingVehicleData2 = new VehicleData(1);
		matchingVehicleData2.addHoseCross(new HoseCross('A', 4));
		matchingVehicleData2.addHoseCross(new HoseCross('A', 5));
		matchingVehicleDataList.get(1).add(matchingVehicleData2);
		memoryPersistentVehiclesData.add(matchingVehicleData2);

		return matchingVehicleDataList;
	}

	private List<Integer> addFourDayVehicleData(
			MemoryPersistentVehiclesData memoryPersistentVehiclesData) {
		List<Integer> days = new ArrayList<Integer>();
		for (int i = 1; i <= 4; i++) {
			days.add(i);
			if (i == 3) {
				memoryPersistentVehiclesData.add(new VehicleData(i));
				memoryPersistentVehiclesData.add(new VehicleData(i));
			} else {
				memoryPersistentVehiclesData.add(new VehicleData(i));
			}

		}
		return days;
	}

}

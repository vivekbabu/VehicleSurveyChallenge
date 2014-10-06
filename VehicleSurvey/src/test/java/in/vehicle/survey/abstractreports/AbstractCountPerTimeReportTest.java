package in.vehicle.survey.abstractreports;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import in.vehicle.survey.allvehicledata.AllVehiclesData;
import in.vehicle.survey.laneresolver.LaneResolverFactory;
import in.vehicle.survey.linecross.constants.TimeConstants;
import in.vehicle.survey.vehicle.VehicleData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class AbstractCountPerTimeReportTest {

	@Rule
	public final StandardOutputStreamLog log = new StandardOutputStreamLog();

	@Test
	@PrepareForTest(LaneResolverFactory.class)
	public void testGenerateReport() {
		List<Integer> daysHavingTraffic = new ArrayList<Integer>(
				Arrays.asList(1));
		List<Character> laneList = new ArrayList<Character>(Arrays.asList('A'));
		Map<Integer, List<VehicleData>> vehicleDataMap = new HashMap<Integer, List<VehicleData>>();
		vehicleDataMap.put(
				1,
				new ArrayList<VehicleData>(Arrays.asList(new VehicleData(1),
						new VehicleData(1), new VehicleData(1))));
		AllVehiclesData allVehiclesData = Mockito.mock(AllVehiclesData.class);
		mockStatic(LaneResolverFactory.class);
		when(allVehiclesData.getDaysHavingTraffic()).thenReturn(
				daysHavingTraffic);
		when(LaneResolverFactory.getLanes()).thenReturn(laneList);
		
		when(
				allVehiclesData.getVehicleDataInTime('A', 3600000,
						2 * 3600000 - 1)).thenReturn(vehicleDataMap);

		AbstractCountPerTimeReport abstractCountPerTimeReport = getAbstractCountPerTimeReport();
		abstractCountPerTimeReport.generateAndDisplayReport(allVehiclesData);
		String reportPrinted = log.getLog();
		Assert.assertTrue(reportPrinted.contains("|3|"));
		Assert.assertEquals(1, StringUtils.countMatches(reportPrinted, "|3|"));
		Assert.assertEquals(23, StringUtils.countMatches(reportPrinted, "|0|"));
	}

	private AbstractCountPerTimeReport getAbstractCountPerTimeReport() {
		return new AbstractCountPerTimeReport() {

			public String getNameOfReport() {
				return "";
			}

			public int getDisplayPostion() {
				return 1;
			}

			public long getTimeFrameOfReport() {
				return TimeConstants.MILLIS_PER_HOUR;
			}

		};
	}
}

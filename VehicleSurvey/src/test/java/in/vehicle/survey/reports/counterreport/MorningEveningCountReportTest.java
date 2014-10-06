package in.vehicle.survey.reports.counterreport;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.vehicle.survey.allvehicledata.AllVehiclesData;
import in.vehicle.survey.laneresolver.LaneResolverFactory;
import in.vehicle.survey.linecross.constants.ReportConstants;
import in.vehicle.survey.linecross.constants.TimeConstants;
import in.vehicle.survey.vehicle.VehicleData;

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
public class MorningEveningCountReportTest {
	@Rule
	public final StandardOutputStreamLog log = new StandardOutputStreamLog();
	@Test
	public void testGetters() {
		MorningEveningCountReport morningEveningCountReport = getMorningEveningCountReport();
		Assert.assertEquals(TimeConstants.MILLIS_PER_HALF_DAY,
				morningEveningCountReport.getTimeFrameOfReport());
		Assert.assertEquals(1, morningEveningCountReport.getDisplayPostion());
		Assert.assertEquals(ReportConstants.MORNING_VERSUS_EVENING_VEHICLE_COUNT,
				morningEveningCountReport.getNameOfReport());
	}


	@Test
	@PrepareForTest(LaneResolverFactory.class)
	public void testGenerateReport() {
		List<Integer> daysHavingTraffic = new ArrayList<Integer>(
				Arrays.asList(1));
		List<Character> laneList = new ArrayList<Character>(Arrays.asList('A'));
		Map<Integer, List<VehicleData>> vehicleDataMapMorning = new HashMap<Integer, List<VehicleData>>();
		vehicleDataMapMorning.put(
				1,
				new ArrayList<VehicleData>(Arrays.asList(new VehicleData(1),
						new VehicleData(1), new VehicleData(1))));
		Map<Integer, List<VehicleData>> vehicleDataMapEvening = new HashMap<Integer, List<VehicleData>>();
		vehicleDataMapEvening.put(
				1,
				new ArrayList<VehicleData>(Arrays.asList(new VehicleData(1),
						new VehicleData(1))));
		AllVehiclesData allVehiclesData = Mockito.mock(AllVehiclesData.class);
		mockStatic(LaneResolverFactory.class);
		when(allVehiclesData.getDaysHavingTraffic()).thenReturn(
				daysHavingTraffic);
		when(LaneResolverFactory.getLanes()).thenReturn(laneList);
		
		when(
				allVehiclesData.getVehicleDataInTime('A', 0,
						TimeConstants.MILLIS_PER_HALF_DAY - 1)).thenReturn(vehicleDataMapMorning);
		when(
				allVehiclesData.getVehicleDataInTime('A', TimeConstants.MILLIS_PER_HALF_DAY,
						TimeConstants.MILLIS_PER_DAY - 1)).thenReturn(vehicleDataMapEvening);


		MorningEveningCountReport morningEveningCountReport = getMorningEveningCountReport();
		morningEveningCountReport.generateAndDisplayReport(allVehiclesData);
		String reportPrinted = log.getLog();
		Assert.assertTrue(reportPrinted.contains("|1|3|2|"));
		Assert.assertEquals(StringUtils.countMatches(reportPrinted, "\n"),7);
	}
	
	private MorningEveningCountReport getMorningEveningCountReport() {
		return new MorningEveningCountReport();
	}
}

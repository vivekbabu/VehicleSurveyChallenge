package in.vehicle.survey.abstractreports;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import in.vehicle.survey.allvehicledata.AllVehiclesData;
import in.vehicle.survey.laneresolver.LaneResolverFactory;
import in.vehicle.survey.linecross.constants.TimeConstants;
import in.vehicle.survey.report.data.Peak;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class AbstractPeakTimeReportTest {
	private static final String PEAK_TRAFFIC_FOR_LANE = "Peak traffic for lane ";
	@Rule
	public final StandardOutputStreamLog log = new StandardOutputStreamLog();
	
	@Test
	@PrepareForTest(LaneResolverFactory.class)
	public void testGenerateReport() {
		Peak peakForLaneA = new Peak(1,1,20);
		Peak peakForLaneB = new Peak(2,1,20);
		List<Character> laneList = new ArrayList<Character>(Arrays.asList('A','B'));
		AllVehiclesData allVehiclesData = Mockito.mock(AllVehiclesData.class);
		mockStatic(LaneResolverFactory.class);
		when(LaneResolverFactory.getLanes()).thenReturn(laneList);
		when(allVehiclesData.getPeakTime('A',3600000)).thenReturn(
				peakForLaneA);
		when(allVehiclesData.getPeakTime('B',3600000)).thenReturn(
				peakForLaneB);

		AbstractPeakTimeReport peakHourCountReport = getAbstractPeakTimeReport();
		peakHourCountReport.generateAndDisplayReport(allVehiclesData);
		StringBuffer expectedOutput = new StringBuffer(PEAK_TRAFFIC_FOR_LANE);
		expectedOutput.append("A: ").append(peakForLaneA).append(System.getProperty("line.separator"));
		expectedOutput.append(PEAK_TRAFFIC_FOR_LANE).append("B: ").append(peakForLaneB);
		Assert.assertEquals(expectedOutput.toString().trim(), log.getLog().trim());
	}
	
	private AbstractPeakTimeReport getAbstractPeakTimeReport() {
		return new AbstractPeakTimeReport(){

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

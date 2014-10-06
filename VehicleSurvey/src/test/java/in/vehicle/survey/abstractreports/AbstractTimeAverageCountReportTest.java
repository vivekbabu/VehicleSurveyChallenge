package in.vehicle.survey.abstractreports;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

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

import in.vehicle.survey.allvehicledata.AllVehiclesData;
import in.vehicle.survey.laneresolver.LaneResolverFactory;
import in.vehicle.survey.linecross.constants.TimeConstants;
import in.vehicle.survey.report.data.Average;

@RunWith(PowerMockRunner.class)
public class AbstractTimeAverageCountReportTest {
	@Rule
	public final StandardOutputStreamLog log = new StandardOutputStreamLog();
	
	@Test
	@PrepareForTest(LaneResolverFactory.class)
	public void testGenerateReport() {
		Map<Long, Average> averageSpeedForLaneA = new HashMap<Long, Average>();
		averageSpeedForLaneA.put(TimeConstants.MILLIS_PER_HOUR, new Average(TimeConstants.MILLIS_PER_HOUR, 30.0f));
		for (long time =0 ; time < TimeConstants.MILLIS_PER_DAY; time+= TimeConstants.MILLIS_PER_HOUR) {
			if(time!=TimeConstants.MILLIS_PER_HOUR) {
				averageSpeedForLaneA.put(time, new Average(time, 0));
			}
			
		}
		List<Character> laneList = new ArrayList<Character>(Arrays.asList('A'
				));
		AllVehiclesData allVehiclesData = Mockito.mock(AllVehiclesData.class);
		mockStatic(LaneResolverFactory.class);
		when(LaneResolverFactory.getLanes()).thenReturn(laneList);
		when(allVehiclesData.getAverageCount('A', 3600000))
				.thenReturn(averageSpeedForLaneA);
		AbstractTimeAverageCountReport timeAverageCountReport = getAbstractTimeAverageCountReport();
		timeAverageCountReport.generateAndDisplayReport(allVehiclesData);
		String reportPrinted = log.getLog();
		Assert.assertTrue(reportPrinted.contains("| 01:00  |30|"));
		Assert.assertEquals(23, StringUtils.countMatches(reportPrinted, "|0|"));
		
	}
	
	private AbstractTimeAverageCountReport getAbstractTimeAverageCountReport() {
		return new AbstractTimeAverageCountReport(){

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

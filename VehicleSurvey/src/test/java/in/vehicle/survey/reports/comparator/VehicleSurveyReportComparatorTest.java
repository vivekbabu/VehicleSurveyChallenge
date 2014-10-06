package in.vehicle.survey.reports.comparator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import in.vehicle.survey.reports.VehicleSurveyReport;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class VehicleSurveyReportComparatorTest {

	@Test
	
	@Parameters({"1,2,-1", "2,1,1", "1,1,0"})
	public void testCompare(int firstValue, int secondValue, int result) {
		VehicleSurveyReport firstVehicleSurveyReport = mock(VehicleSurveyReport.class);
		VehicleSurveyReport secondVehicleSurveyReport = mock(VehicleSurveyReport.class);
		VehicleSurveyReportComparator vehicleSurveyReportComparator = new VehicleSurveyReportComparator();
		when(firstVehicleSurveyReport.getDisplayPostion()).thenReturn(firstValue);
		when(secondVehicleSurveyReport.getDisplayPostion()).thenReturn(secondValue);
		Assert.assertEquals(result, vehicleSurveyReportComparator.compare(
				firstVehicleSurveyReport, secondVehicleSurveyReport));
	}
}

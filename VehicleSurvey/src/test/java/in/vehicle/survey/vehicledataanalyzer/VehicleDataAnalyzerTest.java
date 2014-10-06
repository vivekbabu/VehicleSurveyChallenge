package in.vehicle.survey.vehicledataanalyzer;

import static org.mockito.Mockito.when;
import in.vehicle.survey.allvehicledata.AllVehiclesData;
import in.vehicle.survey.hosecross.exception.InvalidHoseCrossException;
import in.vehicle.survey.linecross.constants.Messages;
import in.vehicle.survey.reports.PeakHourCountReport;
import in.vehicle.survey.reports.VehicleSurveyReport;
import in.vehicle.survey.vehicledatareader.VehicleDataReader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Matchers.*;

@RunWith(JUnitParamsRunner.class)
public class VehicleDataAnalyzerTest {
	@Rule
	public final StandardOutputStreamLog log = new StandardOutputStreamLog();

	@Test
	public void testInvalidHoseExceptionIsLogged()
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		String exceptionMessage = "The line number 1 contains an invalid input";
		VehicleDataAnalyzer vehicleDataAnalyzer = getVehicleDataAnalyzer(
				exceptionMessage, true, true);
		vehicleDataAnalyzer.analyzeFile(new String[1]);
		Assert.assertEquals(exceptionMessage, log.getLog().trim());
	}

	@Test
	public void testProperLogMessageOnNoFileSpecified()
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		String exceptionMessage = Messages.NO_FILE_SPECIFIED;
		VehicleDataAnalyzer vehicleDataAnalyzer = getVehicleDataAnalyzer(
				exceptionMessage, false, true);
		vehicleDataAnalyzer.analyzeFile(new String[1]);
		Assert.assertEquals(exceptionMessage, log.getLog().trim());
	}

	@Test
	public void testProperLogMessageOnNoInvalidFileSpecified()
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		String exceptionMessage = Messages.SPECIFIED_FILE_DOES_NOT_EXIST;
		VehicleDataAnalyzer vehicleDataAnalyzer = getVehicleDataAnalyzer(
				exceptionMessage, true, false);
		vehicleDataAnalyzer.analyzeFile(new String[1]);
		Assert.assertEquals(exceptionMessage, log.getLog().trim());
	}

	@Test
	public void testGetReports() throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		VehicleDataAnalyzer vehicleDataAnalyzer = new VehicleDataAnalyzer();
		List<VehicleSurveyReport> reports = vehicleDataAnalyzer.getReports();
		Set<Integer> positions = new TreeSet<Integer>();
		Set<String> reportNames = new TreeSet<String>();
		for (VehicleSurveyReport report : reports) {
			positions.add(report.getDisplayPostion());
			reportNames.add(report.getNameOfReport());
		}
		Assert.assertEquals(reports.size(), positions.size());
		Assert.assertEquals(reports.size(), reportNames.size());
	}

	@Test
	@Parameters({ "true", "false" })
	public void testCheckIfDataFileExists(boolean exists) {
		File mockedFile = Mockito.mock(File.class);
		VehicleDataAnalyzer vehicleDataAnalyzer = new VehicleDataAnalyzer();
		when(mockedFile.exists()).thenReturn(exists);
		Assert.assertEquals(exists,
				vehicleDataAnalyzer.checkIfDataFileExists(mockedFile));

	}
	
	
	@Test
	public void testIfReportGenerationMethodsAreCalled() throws InvalidHoseCrossException {
		final VehicleDataReader vehicleDataReader = Mockito.mock(VehicleDataReader.class);
		final AllVehiclesData allVehiclesData = Mockito.mock(AllVehiclesData.class);
		final File file = Mockito.mock(File.class);
		final String nameOfReport = "Name";
		final String reportContent = "Line Printed";
		VehicleDataAnalyzer vehicleDataAnalyzer = new VehicleDataAnalyzer() {
			protected VehicleDataReader getVehicleDataReader() {
				return vehicleDataReader;
			};
			
			@Override
			protected boolean checkIfArgsContainFileDetails(String[] args) {
				return true;
			}
			
			@Override
			protected boolean checkIfDataFileExists(File dataFile) {
				return true;
			}
			
			@Override
			protected File getFileToAnalyze(String[] args) {
				return file;
			}
			
			@Override
			protected List<VehicleSurveyReport> getReports() {
				List<VehicleSurveyReport> vehicleSurveyReports= new ArrayList<VehicleSurveyReport>();
				vehicleSurveyReports.add(new PeakHourCountReport() {
					@Override
					public String getNameOfReport() {
						return nameOfReport;
					}
					
					public void generateAndDisplayReport(AllVehiclesData allVehiclesData) {
						System.out.println(reportContent);
					};
				});
				return vehicleSurveyReports;
			}
		};
		when(vehicleDataReader.readVehicleDataFromFile(any(File.class))).thenReturn(allVehiclesData);
		vehicleDataAnalyzer.analyzeFile(new String[1]);
		String logContent = log.getLog();
		Assert.assertTrue(StringUtils.contains(logContent, reportContent));
		Assert.assertTrue(StringUtils.contains(logContent, nameOfReport));
		
		
	}

	private VehicleDataAnalyzer getVehicleDataAnalyzer(final String message,
			final boolean argsContainsFile, final boolean dataFileExists) {
		VehicleDataAnalyzer vehicleDataAnalyzer = new VehicleDataAnalyzer() {

			@Override
			protected boolean checkIfArgsContainFileDetails(String[] args) {
				return argsContainsFile;
			}

			@Override
			protected File getFileToAnalyze(String[] args) {
				return new File("");
			}

			@Override
			protected boolean checkIfDataFileExists(File dataFile) {
				return dataFileExists;
			}

			@Override
			protected VehicleDataReader getVehicleDataReader() {
				return new VehicleDataReader() {
					@Override
					public AllVehiclesData readVehicleDataFromFile(File file)
							throws InvalidHoseCrossException {
						throw new InvalidHoseCrossException(1, message);
					}
				};
			}

		};
		return vehicleDataAnalyzer;
	}

}

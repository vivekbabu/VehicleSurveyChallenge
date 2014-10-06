package in.vehicle.survey.vehicledataanalyzer;

import in.vehicle.survey.allvehicledata.AllVehiclesData;
import in.vehicle.survey.hosecross.exception.InvalidHoseCrossException;
import in.vehicle.survey.linecross.constants.Messages;
import in.vehicle.survey.memorypersistentvehiclesdata.MemoryPersistentVehiclesData;
import in.vehicle.survey.reports.HalfHourAverageDistanceReport;
import in.vehicle.survey.reports.HourAverageCountReport;
import in.vehicle.survey.reports.HourAverageDistanceReport;
import in.vehicle.survey.reports.HourAverageSpeedReport;
import in.vehicle.survey.reports.PeakHalfDayCountReport;
import in.vehicle.survey.reports.PeakHourCountReport;
import in.vehicle.survey.reports.VehicleSurveyReport;
import in.vehicle.survey.reports.comparator.VehicleSurveyReportComparator;
import in.vehicle.survey.reports.counterreport.CountPerFifteenMinutesReport;
import in.vehicle.survey.reports.counterreport.CountPerHalfHourReport;
import in.vehicle.survey.reports.counterreport.CountPerHourReport;
import in.vehicle.survey.reports.counterreport.CountPerTwentyMinutesReport;
import in.vehicle.survey.reports.counterreport.MorningEveningCountReport;
import in.vehicle.survey.utils.PrintUtils;
import in.vehicle.survey.vehicledatareader.VehicleDataReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is the main class that runs the analyzer. It takes the filename to be
 * analyzed from the standard input. It calls the vehicle data reader class's
 * read method to read the contents of the file and convert it into proper data
 * structure. The parsed data is provided to report generators, which print the
 * analysis to the standard output
 * 
 * @author Vivek
 * 
 */
public class VehicleDataAnalyzer {
	public static void main(String[] args) {
		VehicleDataAnalyzer vehicleDataAnalyzer = new VehicleDataAnalyzer();
		vehicleDataAnalyzer.analyzeFile(args);
	}

	/**
	 * Analyses the file provided in the args. If no file specified or if the
	 * file is not found, outputs the proper error messages
	 * 
	 * @param args the arguements passed to main program
	 */
	public void analyzeFile(String[] args) {
		if (!checkIfArgsContainFileDetails(args)) {
			System.out.println(Messages.NO_FILE_SPECIFIED);
			return;
		}
		File dataFile = getFileToAnalyze(args);
		if (!checkIfDataFileExists(dataFile)) {
			System.out.println(Messages.SPECIFIED_FILE_DOES_NOT_EXIST);
			return;
		}
		VehicleDataReader vehicleDataReader = getVehicleDataReader();
		AllVehiclesData allVehiclesData = null;
		try {
			allVehiclesData = vehicleDataReader
					.readVehicleDataFromFile(dataFile);
			List<VehicleSurveyReport> reports = getReports();
			Collections.sort(reports, new VehicleSurveyReportComparator());
			for (VehicleSurveyReport report : reports) {
				PrintUtils.printDividerLine();
				System.out.println("Report :" + report.getNameOfReport());
				PrintUtils.printDividerLine();
				report.generateAndDisplayReport(allVehiclesData);
				PrintUtils.printDividerLine();
			}

		} catch (InvalidHoseCrossException e) {
			System.out.println(e.getMessage());
		}

	}

	protected List<VehicleSurveyReport> getReports() {

		List<VehicleSurveyReport> reports = new ArrayList<VehicleSurveyReport>();
		reports.add(new HourAverageCountReport());
		reports.add(new MorningEveningCountReport());
		reports.add(new CountPerHourReport());
		reports.add(new CountPerHalfHourReport());
		reports.add(new CountPerFifteenMinutesReport());
		reports.add(new CountPerTwentyMinutesReport());
		reports.add(new PeakHourCountReport());
		reports.add(new PeakHalfDayCountReport());
		reports.add(new HourAverageDistanceReport());
		reports.add(new HourAverageSpeedReport());
		reports.add(new HalfHourAverageDistanceReport());

		return reports;
	}

	/**
	 * Checks if the given file exists
	 * 
	 * @param dataFile
	 *            the file to be checked
	 * @return true if the file exists, false otherwise
	 */
	protected boolean checkIfDataFileExists(File dataFile) {
		if (!dataFile.exists()) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the instance of vehicle data reader
	 * 
	 * @return the instance of vehicle data reader
	 */
	protected VehicleDataReader getVehicleDataReader() {
		VehicleDataReader vehicleDataReader = new VehicleDataReader();
		vehicleDataReader
				.setAllVehiclesData(new MemoryPersistentVehiclesData());
		return vehicleDataReader;
	}

	/**
	 * Checks if the args contain the filename to be parsed
	 * 
	 * @param args
	 *            the program arguements
	 * @return true if the file name is present, false otherwise.
	 */
	protected boolean checkIfArgsContainFileDetails(String[] args) {
		if (args == null || args.length < 1) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the file to be analyzed
	 * @param args arguements passed to main program
	 * @return {@link File}
	 */
	protected File getFileToAnalyze(String[] args) {
		return new File(args[0]);
	}

}

package in.vehicle.survey.vehicledatareader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.vehicle.survey.allvehicledata.AllVehiclesData;
import in.vehicle.survey.hosecross.HoseCross;
import in.vehicle.survey.hosecross.exception.InvalidHoseCrossException;
import in.vehicle.survey.linecross.constants.HoseConstants;
import in.vehicle.survey.vehicle.VehicleData;

/**
 * This class reads the file and returns the parsed data as
 * {@link AllVehiclesData}
 * 
 */
public class VehicleDataReader {
	private AllVehiclesData allVehiclesData;

	public void setAllVehiclesData(AllVehiclesData allVehiclesData) {
		this.allVehiclesData = allVehiclesData;
	}

	public AllVehiclesData readVehicleDataFromFile(final File file)
			throws InvalidHoseCrossException {
		AllVehiclesData vehicleData = null;
		try {
			vehicleData = getVehicleData(file);
		} catch (InstantiationException e) {
			printInternalErrorOccured(e);
		} catch (IllegalAccessException e) {
			printInternalErrorOccured(e);
		} catch (IOException e) {
			printInternalErrorOccured(e);
		} catch (InvalidHoseCrossException e) {
			throw e;
		} catch (Exception e) {
			printInternalErrorOccured(e);
		}
		return vehicleData;
	}

	protected AllVehiclesData getVehicleData(File file)
			throws InstantiationException, IllegalAccessException,
			InvalidHoseCrossException, IOException, Exception {

		BufferedReader bufferedFileReader = null;
		long fileLineNumber = 0;
		String fileLine = null;
		int day = 1;
		AllVehiclesData allVehicleDataInstance = null;

		allVehicleDataInstance = allVehiclesData.getClass().newInstance();
		bufferedFileReader = new BufferedReader(new FileReader(file));

		VehicleData vehicleData = null;
		HoseCross lastReadHoseCross = null;

		Pattern vehicleLineCrossPattern = Pattern.compile("([a-zA-Z])(\\d+)");

		try {
			while ((fileLine = bufferedFileReader.readLine()) != null) {
				fileLineNumber++;
				if (!fileLine.isEmpty()) {
					Matcher matcher = vehicleLineCrossPattern.matcher(fileLine);
					if (matcher.matches()) {
						HoseCross hossCorss = getHoseCross(fileLineNumber,
								fileLine, matcher);
						if (vehicleData == null
								|| (vehicleData.getHoseCrosses().size() == 4)) {
							if (lastReadHoseCross != null
									&& hossCorss.getTimestamp() < lastReadHoseCross
											.getTimestamp()) {
								day++;
							}
							vehicleData = new VehicleData(day)
									.addHoseCross(hossCorss);
							lastReadHoseCross = hossCorss;
						} else {
							if (vehicleData.getHoseCrosses().size() == 1
									&& lastReadHoseCross.getHose() == hossCorss
											.getHose()
									&& hossCorss.getHose() == HoseConstants.HOSE_A) {
								vehicleData.addHoseCross(hossCorss);
								vehicleData = null;
								lastReadHoseCross = hossCorss;
								continue;
							} else if ((lastReadHoseCross.getHose() == HoseConstants.HOSE_A && hossCorss
									.getHose() == HoseConstants.HOSE_B)
									|| (lastReadHoseCross.getHose() == HoseConstants.HOSE_B && hossCorss
											.getHose() == HoseConstants.HOSE_A)) {
								lastReadHoseCross = hossCorss;
								vehicleData.addHoseCross(hossCorss);
								continue;
							} else {
								throwInvalidHoseCrossException(fileLineNumber,
										fileLine);
							}
						}
					}
					allVehicleDataInstance.add(vehicleData);
				}

			}
		} catch (NumberFormatException e) {
			throwInvalidHoseCrossException(fileLineNumber, fileLine);
		} finally {
			closeTheBufferReader(bufferedFileReader);
		}
		return allVehicleDataInstance;
	}

	private void closeTheBufferReader(BufferedReader bufferedFileReader) {
		try {
			bufferedFileReader.close();
		} catch (IOException e) {
			System.out.println("Error occured during closing the file");
		}
	}

	private HoseCross getHoseCross(long fileLineNumber, String fileLine,
			Matcher matcher) throws InvalidHoseCrossException {
		long timeStamp = Long.parseLong(matcher.group(2));
		char hose = matcher.group(1).toUpperCase().charAt(0);
		if (!HoseConstants.getLines().contains(hose)) {
			throwInvalidHoseCrossException(fileLineNumber, fileLine);
		}

		HoseCross hossCorss = new HoseCross(hose, timeStamp);
		return hossCorss;
	}

	private void throwInvalidHoseCrossException(long fileLineNumber,
			String fileLine) throws InvalidHoseCrossException {
		throw new InvalidHoseCrossException(fileLineNumber, "The line number "
				+ fileLineNumber + " in file contains an invalid hose cross "
				+ fileLine);
	}

	private void printInternalErrorOccured(Exception e) {
		System.out.println("Internal Error occured " + e.getClass() + " "
				+ e.getMessage());
	}

}

package in.vehicle.survey.reports;

import in.vehicle.survey.allvehicledata.AllVehiclesData;

public interface VehicleSurveyReport {

	/**
	 * Gets the name of the report
	 * 
	 * @return the name of the report
	 */
	public String getNameOfReport();

	/**
	 * The position in the list of reports to be generated and displayed
	 * 
	 * @return position in the list of reports to be generated and displayed
	 */
	public int getDisplayPostion();

	/**
	 * Generate the report and print it
	 * 
	 * @param allVehiclesData the complete data parsed from the input.
	 */
	public void generateAndDisplayReport(AllVehiclesData allVehiclesData);
	
	/**
	 * Get time frame of the report. Eg: Hourly, half hourly etc
	 * @return the time frame of the report
	 */
	public long getTimeFrameOfReport();
	

}

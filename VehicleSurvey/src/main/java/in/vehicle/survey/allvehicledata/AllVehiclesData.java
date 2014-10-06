package in.vehicle.survey.allvehicledata;

import java.util.List;
import java.util.Map;

import in.vehicle.survey.report.data.Average;
import in.vehicle.survey.report.data.Peak;
import in.vehicle.survey.vehicle.VehicleData;

/**
 * This interface has various methods which are used by the report generators to
 * generate the necessary reports
 */
public interface AllVehiclesData {
	/**
	 * Adds a vehicle data object to the list
	 * 
	 * @param vehicleData
	 *            the vehicle data object to be added
	 */
	public void add(VehicleData vehicleData);

	/**
	 * Gets the entire vehicle records parsed.
	 * 
	 * @return {@link List} of {@link VehicleData}
	 */
	public List<VehicleData> getAllVehiclesData();

	/**
	 * Returns the list of days having vehicle data
	 * 
	 * @return {@link List} of {@link Integer} representing the days
	 */
	public List<Integer> getDaysHavingTraffic();

	/**
	 * Gets vehicle records in the given period for the given lane
	 * 
	 * @param lane
	 *            the lane
	 * @param start
	 *            starting time
	 * @param end
	 *            ending time
	 * @return {@link Map} of day to {@link List} of {@link VehicleData}
	 */
	public Map<Integer, List<VehicleData>> getVehicleDataInTime(char lane,
			long start, long end);

	/**
	 * Gets the peak traffic in the given lane for given time period
	 * 
	 * @param lane
	 *            the lane
	 * @param periodLength
	 *            the length of the period
	 * @return {@link Peak} count for that particular time period
	 */
	public Peak getPeakTime(char lane, long periodLength);

	/**
	 * Get average count of vehicles in the given period for the given lane
	 * 
	 * @param lane
	 *            the lane
	 * @param periodLength
	 *            length of period
	 * @return {@link Average} count for that particular period
	 */
	public Map<Long, Average> getAverageCount(char lane, long periodLength);

	/**
	 * Get average speed of vehicles in the given period for the given lane
	 * 
	 * @param lane
	 *            the lane
	 * @param periodLength
	 *            length of period
	 * @return {@link Average} speed for that particular period
	 */
	public Map<Long, Average> getAverageSpeed(char lane, long periodLength);

	/**
	 * Get average distance between vehicles in the given period for the given
	 * lane
	 * 
	 * @param lane
	 *            the lane
	 * @param periodLength
	 *            length of period
	 * @return {@link Average} distance between vehicles for that particular
	 *         period
	 */
	public Map<Long, Average> getAverageDistance(char lane, long periodLength);

}

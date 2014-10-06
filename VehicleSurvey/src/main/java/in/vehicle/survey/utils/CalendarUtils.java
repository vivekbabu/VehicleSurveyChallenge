package in.vehicle.survey.utils;

import in.vehicle.survey.linecross.constants.TimeConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This is a utility class that contains the methods for doing date formatting
 */
public class CalendarUtils {
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			TimeConstants.HOUR_MINUTE);

	/**
	 * Converts the given time of day in timestamp into HH:mm format
	 * 
	 * @param timestamp
	 *            the time of the day in millis
	 * @return the given time of day in timestamp into HH:mm format
	 */
	public static String getTimeInHourMinuteFormat(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return dateFormatter.format(new Date(calendar.getTimeInMillis()
				+ timestamp));
	}
}

package in.vehicle.survey.report.data;

import in.vehicle.survey.utils.CalendarUtils;

/**
 * Class that represents a peak
 */
public class Peak {
	private int day;
	private long hourOfTheDay;
	private int peak;

	public Peak(int day, long hourOfTheDay, int peak) {
		this.day = day;
		this.hourOfTheDay = hourOfTheDay;
		this.peak = peak;
	}

	public int getDay() {
		return day;
	}

	public long getHour() {
		return hourOfTheDay;
	}

	public int getPeak() {
		return peak;
	}

	@Override
	public String toString() {
		StringBuilder lineCrossStringBuilder = new StringBuilder();
		lineCrossStringBuilder.append("[").append("Day:").append(day);
		lineCrossStringBuilder.append(",Hour of the day:").append(
				CalendarUtils.getTimeInHourMinuteFormat(hourOfTheDay));
		lineCrossStringBuilder.append(",Peak:").append(peak);
		lineCrossStringBuilder.append("]");

		return lineCrossStringBuilder.toString();
	}

}

package in.vehicle.survey.report.data;
/**
 * Class that represents an average
 */
public class Average {
	private long hourOfTheDay;

    private float average;

    public Average( long hour, float average )
    {
        this.hourOfTheDay = hour;
        this.average = average;
    }

    public long getHourOfTheDay() {
		return hourOfTheDay;
	}
    
    public float getAverage() {
		return average;
	}
    
	public String toString() {
		StringBuilder lineCrossStringBuilder = new StringBuilder();

		lineCrossStringBuilder.append("[").append("Hour of the day:").append(hourOfTheDay);
		lineCrossStringBuilder.append(",Average:").append(average);
		lineCrossStringBuilder.append("]");

		return lineCrossStringBuilder.toString();
	}
}

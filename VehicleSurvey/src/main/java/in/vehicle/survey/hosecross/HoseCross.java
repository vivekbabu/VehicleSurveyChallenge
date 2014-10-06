package in.vehicle.survey.hosecross;

/**
 * This class represent one crossing of the counter line by a pair of vehicle
 * tyres Eg: A268981 will be represented as line : A, timestamp : 268981 (Time
 * in milliseconds since midnight of that day) So A268981 represents a pair of
 * tires driving by at 12:04:28am
 */
public class HoseCross {

	private char hose;
	private long timestamp;
	
	public HoseCross(char line, long timestamp) {
		this.hose = line;
		this.timestamp = timestamp;
	}

	public char getHose() {
		return hose;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		StringBuilder lineCrossStringBuilder = new StringBuilder();

		lineCrossStringBuilder.append("[").append("Hose:").append(hose);
		lineCrossStringBuilder.append(",Timestamp=").append(timestamp);
		lineCrossStringBuilder.append("]");

		return lineCrossStringBuilder.toString();
	}
}

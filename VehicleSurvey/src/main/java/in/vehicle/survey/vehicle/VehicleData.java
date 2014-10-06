package in.vehicle.survey.vehicle;

import in.vehicle.survey.hosecross.HoseCross;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
/**
 * Represents one vehicle passing through the sensor in any lane
 */
public class VehicleData {
	private int day;
	private List<HoseCross> hoseCrosses = new ArrayList<HoseCross>();

	public VehicleData(int day) {
		this.day = day;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setLineCrosses(List<HoseCross> lineCrosses) {
		if (lineCrosses != null) {
			this.hoseCrosses = lineCrosses;
		} else {
			this.hoseCrosses = new ArrayList<HoseCross>();
		}
	}

	public List<HoseCross> getHoseCrosses() {
		return Collections.unmodifiableList(hoseCrosses);
	}

	public VehicleData addHoseCross(HoseCross lineCross) {
		if (lineCross != null) {
			this.hoseCrosses.add(lineCross);
		}
		return this;

	}

	@Override
	public String toString() {
		StringBuilder lineCrossStringBuilder = new StringBuilder();

		lineCrossStringBuilder.append("[").append("Day:").append(day);
		lineCrossStringBuilder.append(",LineCrosses:").append(hoseCrosses);
		lineCrossStringBuilder.append("]");

		return lineCrossStringBuilder.toString();
	}

}

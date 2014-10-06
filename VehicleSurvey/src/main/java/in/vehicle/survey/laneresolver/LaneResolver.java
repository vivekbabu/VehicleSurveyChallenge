package in.vehicle.survey.laneresolver;

import in.vehicle.survey.hosecross.HoseCross;

import java.util.List;

public interface LaneResolver {
	/**
	 * Returns true if the given list of hose crosses matches the given lane
	 * 
	 * @param hoseCrosses
	 *            the {@link List} of {@link HoseCross}es
	 * @return {@link Boolean} true if the given hoss crosses matches the given
	 *         lane
	 */
	boolean isreadingMatchingTheLane(List<HoseCross> hoseCrosses);
	
	/**
	 * Returns the lane which the current resolver checks if matching
	 * @return {@link Character} lane identifier
	 */
	char getLaneName();
}

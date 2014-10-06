package in.vehicle.survey.laneresolver;

import in.vehicle.survey.hosecross.HoseCross;
import in.vehicle.survey.linecross.constants.HoseConstants;
import in.vehicle.survey.linecross.constants.Messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LaneResolverFactory {
	private static final LaneResolver LANE_A_RESOVER = new LaneResolver() {
		public char getLaneName() {
			return 'A';
		};

		public boolean isreadingMatchingTheLane(List<HoseCross> lineCrosses) {
			if (lineCrosses.size() != 2)
				return false;
			else {
				return lineCrosses.get(0).getHose() == HoseConstants.HOSE_A
						&& lineCrosses.get(1).getHose() == HoseConstants.HOSE_A;
			}
		}
	};
	private static final LaneResolver LANE_B_RESOVER = new LaneResolver() {
		public char getLaneName() {
			return 'B';
		};

		public boolean isreadingMatchingTheLane(List<HoseCross> lineCrosses) {
			if (lineCrosses.size() != 4)
				return false;
			else {
				return lineCrosses.get(0).getHose() == HoseConstants.HOSE_A
						&& lineCrosses.get(1).getHose() == HoseConstants.HOSE_B
						&& lineCrosses.get(2).getHose() == HoseConstants.HOSE_A
						&& lineCrosses.get(3).getHose() == HoseConstants.HOSE_B;
			}
		}
	};

	private static final List<Character> lanes = new ArrayList<Character>(
			Arrays.asList(LANE_A_RESOVER.getLaneName(),
					LANE_B_RESOVER.getLaneName()));

	/**
	 * Gets the lane resolver for the given lane
	 * 
	 * @param lane
	 *            the lane for which lane resolver is returned
	 * @return {@link LaneResolver} for the given lane
	 */
	public static LaneResolver getLaneResolver(char lane) {
		switch (lane) {
		case 'A':
			return LANE_A_RESOVER;
		case 'B':
			return LANE_B_RESOVER;
		default:
			throw new IllegalArgumentException(Messages.INVALID_LANE_NUMBER);
		}
	}

	/**
	 * Gets the lanes in which measurements are made
	 * 
	 * @return the {@link List} of {@link Character} represnting the lanes
	 */
	public static List<Character> getLanes() {
		return lanes;

	}

}

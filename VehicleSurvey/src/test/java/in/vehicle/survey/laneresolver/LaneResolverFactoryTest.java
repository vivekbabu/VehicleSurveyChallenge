package in.vehicle.survey.laneresolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import in.vehicle.survey.hosecross.HoseCross;
import in.vehicle.survey.laneresolver.LaneResolver;
import in.vehicle.survey.laneresolver.LaneResolverFactory;
import in.vehicle.survey.linecross.constants.Messages;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class LaneResolverFactoryTest {
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	@Parameters({ "A,AA,true", "A,AB,false", "A,BA,false","A,B,false", "B,ABAB,true",
			"B,B,false","B,BABA,false","B,ACAB, false","B,ABCB,false","B,ABAC,false" })
	public void testIfProperLaneResolversAreReturnedByFactory(char lane,
			String lineCrosses, boolean expectedResult) {
		LaneResolver laneResolver = LaneResolverFactory
				.getLaneResolver(lane);

		List<HoseCross> laneData = getLaneData(lane, lineCrosses);
		boolean isreadingMatchingTheLane = laneResolver
				.isreadingMatchingTheLane(laneData);
		Assert.assertEquals(expectedResult, isreadingMatchingTheLane);
	}
	
	@Test
	public void testIfFactoryThrowsExceptionForInvalidLaneNumber() {
		
		char invalidLaneNumber = 'C';
		
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage(Messages.INVALID_LANE_NUMBER);
		LaneResolverFactory
				.getLaneResolver(invalidLaneNumber);
		
		
	}
	
	@Test
	public void testIfFactoryReturnsProperLaneInfos() {
		
		List<Character> lanes = new ArrayList<Character>(Arrays.asList('A','B'));
		Assert.assertEquals(lanes, LaneResolverFactory.getLanes());
		
		
	}

	private List<HoseCross> getLaneData(int laneNumber, String lines) {
		List<HoseCross> laneData = new ArrayList<HoseCross>();
		Random random = new Random();
		for (int index = 0; index < lines.length(); index++) {
			laneData.add(new HoseCross(lines.charAt(index), random.nextLong()));
		}

		return laneData;
	}
}
package in.vehicle.survey.report.data;

import org.junit.Assert;
import org.junit.Test;

public class AverageTest {
	@Test
	public void testGetters() {

		int hour = 1;
		float averageValue = 22.5f;
		Average average = new Average(hour, averageValue);

		Assert.assertEquals(hour, average.getHourOfTheDay());
		Assert.assertEquals(averageValue, average.getAverage(), 0.0f);
	}

	@Test
	public void testToString() {
		int hour = 1;
		float averageValue = 22.5f;

		Average average = new Average(hour, averageValue);

		Assert.assertEquals("[Hour of the day:" + hour + ",Average:"
				+ averageValue + "]", average.toString());

	}
}

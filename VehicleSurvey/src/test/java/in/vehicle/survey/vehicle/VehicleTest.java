package in.vehicle.survey.vehicle;

import java.util.ArrayList;
import java.util.List;

import in.vehicle.survey.hosecross.HoseCross;

import org.junit.Assert;
import org.junit.Test;

public class VehicleTest {
	@Test
	public void testConstructor() {
		int day = 1;

		VehicleData vehicle = new VehicleData(day);

		Assert.assertEquals(day, vehicle.getDay());
		Assert.assertEquals(new ArrayList<HoseCross>(),
				vehicle.getHoseCrosses());
	}

	@Test
	public void testGetters() {
		HoseCross lineCross = new HoseCross('A', 121212);
		List<HoseCross> lineCrosses = new ArrayList<HoseCross>();
		lineCrosses.add(lineCross);

		VehicleData vehicle = new VehicleData(1);
		vehicle.setLineCrosses(lineCrosses);

		Assert.assertEquals(lineCrosses, vehicle.getHoseCrosses());
	}

	@Test
	public void testAddLineCross() {
		HoseCross lineCross = new HoseCross('A', 121212);
		List<HoseCross> lineCrosses = new ArrayList<HoseCross>();
		lineCrosses.add(lineCross);

		VehicleData vehicle = new VehicleData(1);
		vehicle.addHoseCross(lineCross);

		Assert.assertEquals(lineCrosses, vehicle.getHoseCrosses());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testLineCrossesListIsUnmodifiable() {
		HoseCross lineCross = new HoseCross('A', 121212);

		VehicleData vehicle = new VehicleData(1);

		List<HoseCross> lineCrosses = vehicle.getHoseCrosses();
		lineCrosses.add(lineCross);
	}

	@Test
	public void testSetDay() {
		int day = 2;
		VehicleData vehicle = new VehicleData(1);
		vehicle.setDay(day);

		Assert.assertEquals(day, vehicle.getDay());
	}

	@Test
	public void testNullLineCrossSetter() {
		int day = 2;
		VehicleData vehicle = new VehicleData(day);
		vehicle.setLineCrosses(null);
		Assert.assertEquals(new ArrayList<HoseCross>(),
				vehicle.getHoseCrosses());
		vehicle.addHoseCross(null);
		Assert.assertEquals(new ArrayList<HoseCross>(),
				vehicle.getHoseCrosses());
	}

	@Test
	public void testToString() {
		int day = 2;
		VehicleData vehicle = new VehicleData(day);
		HoseCross lineCross = new HoseCross('A', 12121212);
		vehicle.addHoseCross(lineCross);
		Assert.assertEquals(
				"[Day:" + day + ",LineCrosses:" + "[" +lineCross.toString() + "]"+ "]",
				vehicle.toString());

	}
}

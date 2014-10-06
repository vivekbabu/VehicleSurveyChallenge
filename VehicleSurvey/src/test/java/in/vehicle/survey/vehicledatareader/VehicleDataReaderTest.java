package in.vehicle.survey.vehicledatareader;

import in.vehicle.survey.allvehicledata.AllVehiclesData;
import in.vehicle.survey.hosecross.exception.InvalidHoseCrossException;
import in.vehicle.survey.laneresolver.LaneResolverFactory;
import in.vehicle.survey.memorypersistentvehiclesdata.MemoryPersistentVehiclesData;
import in.vehicle.survey.vehicle.VehicleData;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class VehicleDataReaderTest {
	private static final String EXCEPTION_OCCURED = "Exception Occured";
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	@Rule
	public final StandardOutputStreamLog log = new StandardOutputStreamLog();
	@Test
	public void testWithSimpleData() throws Exception  {
		File file = getFile("simpledata.txt");
		VehicleDataReader dataReader = new VehicleDataReader();
		dataReader.setAllVehiclesData(new MemoryPersistentVehiclesData());
		AllVehiclesData readVehicleDataFromFile = dataReader.readVehicleDataFromFile(file);
		List<VehicleData> allVehiclesData = readVehicleDataFromFile.getAllVehiclesData();
		Assert.assertEquals(3, allVehiclesData.size());
		Assert.assertTrue(LaneResolverFactory.getLaneResolver('A').isreadingMatchingTheLane(allVehiclesData.get(0).getHoseCrosses()));
		Assert.assertTrue(LaneResolverFactory.getLaneResolver('A').isreadingMatchingTheLane(allVehiclesData.get(1).getHoseCrosses()));
		Assert.assertTrue(LaneResolverFactory.getLaneResolver('B').isreadingMatchingTheLane(allVehiclesData.get(2).getHoseCrosses()));
	}
	
	@Test
	public void testWithSimpleDataNewDay() throws Exception {
		File file = getFile("simpledatawithnewday.txt");
		VehicleDataReader dataReader = new VehicleDataReader();
		dataReader.setAllVehiclesData(new MemoryPersistentVehiclesData());
		AllVehiclesData readVehicleDataFromFile = dataReader.readVehicleDataFromFile(file);
		List<VehicleData> allVehiclesData = readVehicleDataFromFile.getAllVehiclesData();
		Assert.assertEquals(2, allVehiclesData.size());
		Assert.assertEquals(allVehiclesData.get(0).getDay(), 1);
		Assert.assertEquals(allVehiclesData.get(1).getDay(), 2);
	}
	
	@Test
	public void testWithLongTimeStamp() throws Exception {
		expectedEx.expect(InvalidHoseCrossException.class);
		expectedEx.expectMessage("The line number 1 in file contains an invalid hose cross A10098672100986721009867210098672101120121012012012012");
		File file = getFile("simpledatalargetimestamp.txt");
		VehicleDataReader dataReader = new VehicleDataReader();
		dataReader.setAllVehiclesData(new MemoryPersistentVehiclesData());
		dataReader.readVehicleDataFromFile(file);
	}
	
	@Test
	public void testWithInvalidHose() throws Exception{
		expectedEx.expect(InvalidHoseCrossException.class);
		expectedEx.expectMessage("The line number 1 in file contains an invalid hose cross C12112");
		File file = getFile("simpledatainvalidhose.txt");
		VehicleDataReader dataReader = new VehicleDataReader();
		dataReader.setAllVehiclesData(new MemoryPersistentVehiclesData());
		dataReader.readVehicleDataFromFile(file);
	}
	
	@Test
	public void testWithInvalidCombination() throws InstantiationException, IllegalAccessException, IOException, InvalidHoseCrossException, Exception  {
		expectedEx.expect(InvalidHoseCrossException.class);
		expectedEx.expectMessage("The line number 4 in file contains an invalid hose cross A98333");
		File file = getFile("simpledatainvalidcombination.txt");
		VehicleDataReader dataReader = new VehicleDataReader();
		dataReader.setAllVehiclesData(new MemoryPersistentVehiclesData());
		dataReader.readVehicleDataFromFile(file);
	}
	
	@Test
	@Parameters({ "1", "2", "3", "4" })
	public void testWithExceptions(int exceptionNumber)
			throws InstantiationException, IllegalAccessException, IOException,
			InvalidHoseCrossException, Exception {
		final Class<? extends Exception> excpetionThrown = getException(exceptionNumber);
		VehicleDataReader vehicleDataReader = new VehicleDataReader() {
			@Override
			protected AllVehiclesData getVehicleData(File file)
					throws Exception {
				Exception exception = new IOException();
				try {
					exception = excpetionThrown.getConstructor(String.class)
							.newInstance(EXCEPTION_OCCURED);
				} catch (IllegalArgumentException e) {
				} catch (InvocationTargetException e) {
				} catch (NoSuchMethodException e) {
				} catch (SecurityException e) {
				}
				throw exception;

			}
		};

		vehicleDataReader.readVehicleDataFromFile(new File(""));
		Assert.assertEquals("Internal Error occured class "  + excpetionThrown.getName()+ " " + EXCEPTION_OCCURED , log.getLog().trim());
		
	}

	private Class<? extends Exception> getException(int exceptionNumber) {
		switch (exceptionNumber) {
		case 1:
			return InstantiationException.class;
		case 2:
			return IllegalAccessException.class;
		case 3:
			return IOException.class;
		default:
			return Exception.class;
		}
	}

	
	private File getFile(String fileName) throws URISyntaxException {
		String baseDirectory  = getBaseDirectory();
		URL fileURL = Thread.currentThread().getContextClassLoader().getResource(baseDirectory + fileName);
		
		return new File(fileURL.toURI());
	}

	private String getBaseDirectory() {
		return "in/survey/data/";
	}
}

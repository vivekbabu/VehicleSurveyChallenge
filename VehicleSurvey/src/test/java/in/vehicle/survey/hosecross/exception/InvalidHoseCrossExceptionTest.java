package in.vehicle.survey.hosecross.exception;

import in.vehicle.survey.hosecross.exception.InvalidHoseCrossException;

import org.junit.Assert;
import org.junit.Test;

public class InvalidHoseCrossExceptionTest {

	@Test
	public void testGetters() {
		long fileLineNumber = 20;
		String message = "Invalid line identifier C found in line";
		
		InvalidHoseCrossException invalidLineCrossException = new InvalidHoseCrossException(fileLineNumber, message);
		
		Assert.assertEquals(fileLineNumber, invalidLineCrossException.getLineNumber());
		Assert.assertEquals(message, invalidLineCrossException.getMessage());
		
	}
}

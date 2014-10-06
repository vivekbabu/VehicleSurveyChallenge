package in.vehicle.survey.hosecross.exception;

/**
 * This is the exception that is thrown when the input file has a line
 * which has an invalid reading. The reading should start with either a 'A' or 'B'
 *
 */
public class InvalidHoseCrossException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7986167226851189482L;
	
	private long fileLineNumber;
	
	public InvalidHoseCrossException(long fileLineNumber, String message) {
		super(message);
		this.fileLineNumber = fileLineNumber;
	}

	public long getLineNumber() {
		return fileLineNumber;
	}

}

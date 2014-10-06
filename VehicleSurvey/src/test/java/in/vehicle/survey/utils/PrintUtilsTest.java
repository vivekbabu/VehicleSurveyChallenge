package in.vehicle.survey.utils;

import in.vehicle.survey.linecross.constants.ReportConstants;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;

public class PrintUtilsTest {
	@Rule
	public final StandardOutputStreamLog log = new StandardOutputStreamLog();
	
	@Test
	public void testPrintDividerLine() {
		PrintUtils.printDividerLine();
		Assert.assertEquals(ReportConstants.DIVIDER_LINE, log.getLog().trim());
	}
}

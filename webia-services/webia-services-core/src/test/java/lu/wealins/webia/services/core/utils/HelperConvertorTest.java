package lu.wealins.webia.services.core.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;

import org.junit.Test;

public class HelperConvertorTest {

	@Test
	public void toStringBigDecimalInt() {
		// Arrange
		BigDecimal value = new BigDecimal(123456.12345);
		int scale = 2;
		// Act
		String result = HelperConvertor.toString(value, scale);
		// Assert
		assertEquals("123456.12", result);
		assertFalse("123456.123".equalsIgnoreCase(result));
	}
	
	

}

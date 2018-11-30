package lu.wealins.liability.services.core.utils;

import org.junit.Assert;
import org.junit.Test;

public class IsinUtilsTest {

	@Test
	public void testIsValidCode() {
		// the check digit is verified using Luhn algorithm
		// https://en.wikipedia.org/wiki/Luhn_algorithm
		Assert.assertTrue(IsinUtils.isValidCode("US0378331005"));
		Assert.assertTrue(IsinUtils.isValidCode("US0373831009"));
		Assert.assertFalse(IsinUtils.isValidCode("D56000543287"));
		Assert.assertTrue(IsinUtils.isValidCode("AU0000XVGZA3"));
		Assert.assertTrue(IsinUtils.isValidCode("AU0000VXGZA3"));
		Assert.assertTrue(IsinUtils.isValidCode("GB0002634946"));
		Assert.assertFalse(IsinUtils.isValidCode("US0373831005"));
	}

}

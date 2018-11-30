package lu.wealins.liability.services.core.business.impl;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import lu.wealins.liability.services.core.business.exceptions.PolicyIdFormatException;

@RunWith(MockitoJUnitRunner.class)
public class PolicyIdHelperTest {

	@InjectMocks
	private PolicyIdHelper classUnderTest;
	
	
	@Test
	public void getPolicyRootId() {
		Assert.assertEquals("PCO15454", classUnderTest.getPolicyRootId("PCO15454     "));
		Assert.assertEquals("PCO15454", classUnderTest.getPolicyRootId("PCO15454/0     "));
	}

	@Test(expected = PolicyIdFormatException.class)
	public void getPolicyRootIdDuplicateChar() {
		classUnderTest.getPolicyRootId("PCO15454/0/1     ");
	}

	@Test(expected = PolicyIdFormatException.class)
	public void getPolicyRootIdInvalidIncrement() {
		classUnderTest.getPolicyRootId("PCO15454/A     ");
	}

	@Test
	public void createNextId() {
		Assert.assertEquals("PCO15454/2", classUnderTest.createNextId(Arrays.asList("PCO15454     ", "PCO15454/0     ", "PCO15454/1     "), "PCO15454"));
		Assert.assertEquals("PCO15454/0", classUnderTest.createNextId(Arrays.asList("PCO15454     "), "PCO15454"));
	}
}

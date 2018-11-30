package lu.wealins.webia.core.service.helper;

import java.math.BigDecimal;
import java.math.MathContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MoneyOutFeesHelperTest {

	@InjectMocks
	private MoneyOutFeesHelper classUnderTest;

	@Test
	public void testTransactionFees() {
		assertEqualsDecimal(BigDecimal.valueOf(50), classUnderTest.getBrokerFees(BigDecimal.valueOf(10), BigDecimal.valueOf(5)));
		assertEqualsDecimal(BigDecimal.valueOf(0), classUnderTest.getBrokerFees(BigDecimal.valueOf(0), BigDecimal.valueOf(0)));
	}

	private void assertEqualsDecimal(BigDecimal expected, BigDecimal actual) {
		Assert.assertEquals(actual.round(new MathContext(2)), expected.round(new MathContext(2)));
	}
}

package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;

@RunWith(MockitoJUnitRunner.class)
public class PrimeDetailsGeneratorTest {

	@InjectMocks
	private PrimeDetailsGenerator classUnderTest;

	@Test
	public void generateWithPreviousDetails() {
		TransactionTaxDTO transactionTax = new TransactionTaxDTO();

		Date trnDate = new GregorianCalendar(2017, Calendar.NOVEMBER, 17).getTime();

		transactionTax.setTransactionDate(trnDate);
		transactionTax.setTransactionNetAmount(BigDecimal.valueOf(500000));
		transactionTax.setTransactionTaxId(10003L);

		TransactionTaxDetailsDTO previousDetail = new TransactionTaxDetailsDTO();
		previousDetail.setId(10001L);
		Date sixteenMarch = new GregorianCalendar(2015, Calendar.MARCH, 16).getTime();
		previousDetail.setPremiumDate(sixteenMarch);
		previousDetail.setPremiumValueBefore(BigDecimal.valueOf(0));
		previousDetail.setPremiumValueAfter(BigDecimal.valueOf(495587));
		previousDetail.setSplitPercent(BigDecimal.valueOf(100));

		List<TransactionTaxDetailsDTO> result = classUnderTest.generateTransactionTaxDetails(transactionTax, Arrays.asList(previousDetail), true);

		Assertions.assertThat(result).hasSize(2);

		TransactionTaxDetailsDTO replicatedTaxDetails = result.stream()
				.filter(detail -> detail.getPremiumDate().equals(sixteenMarch))
				.findFirst().orElseThrow(() -> new AssertionError("No tax detail was found for the date " + sixteenMarch));

		Assert.assertEquals(Long.valueOf(10003), replicatedTaxDetails.getTransactionTaxId());
		assertEqualsDecimal(BigDecimal.valueOf(495587), replicatedTaxDetails.getPremiumValueBefore());
		assertEqualsDecimal(BigDecimal.valueOf(495587), replicatedTaxDetails.getPremiumValueAfter());
		assertEqualsDecimal(BigDecimal.valueOf(49.77), replicatedTaxDetails.getSplitPercent());
		Assert.assertNull(replicatedTaxDetails.getCapitalGainAmount());

		TransactionTaxDetailsDTO newTaxDetails = result.stream()
				.filter(detail -> detail.getPremiumDate().equals(trnDate))
				.findFirst().orElseThrow(() -> new AssertionError("No tax detail was found for the date " + trnDate));

		Assert.assertEquals(Long.valueOf(10003), newTaxDetails.getTransactionTaxId());
		assertEqualsDecimal(BigDecimal.ZERO, newTaxDetails.getPremiumValueBefore());
		assertEqualsDecimal(BigDecimal.valueOf(500000), newTaxDetails.getPremiumValueAfter());
		assertEqualsDecimal(BigDecimal.valueOf(50.22162804), newTaxDetails.getSplitPercent());
		Assert.assertNull(newTaxDetails.getCapitalGainAmount());
	}

	@Test
	public void generateWithoutPreviousDetails() {
		TransactionTaxDTO transactionTax = new TransactionTaxDTO();

		Date trnDate = new GregorianCalendar(2017, Calendar.NOVEMBER, 17).getTime();

		transactionTax.setTransactionDate(trnDate);
		transactionTax.setTransactionNetAmount(BigDecimal.valueOf(2.12));
		transactionTax.setTransactionTaxId(10001L);

		List<TransactionTaxDetailsDTO> details = Collections.emptyList();

		List<TransactionTaxDetailsDTO> result = classUnderTest.generateTransactionTaxDetails(transactionTax, details, true);

		Assertions.assertThat(result).hasSize(1);
		Assert.assertEquals(Long.valueOf(10001), result.get(0).getTransactionTaxId());
		Assert.assertEquals(trnDate, result.get(0).getPremiumDate());
		assertEqualsDecimal(BigDecimal.ZERO, result.get(0).getPremiumValueBefore());
		assertEqualsDecimal(BigDecimal.valueOf(2.12), result.get(0).getPremiumValueAfter());
		Assert.assertNull(result.get(0).getCapitalGainAmount());
	}

	private void assertEqualsDecimal(BigDecimal expected, BigDecimal actual) {
		Assertions.assertThat(actual.round(new MathContext(2))).isEqualByComparingTo(expected.round(new MathContext(2)));
	}

	@Test
	public void supportsType() {
		Assert.assertTrue(classUnderTest.supportsType("PREM"));
		Assert.assertFalse(classUnderTest.supportsType("ANY"));
	}
}

package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Calendar;
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
public class RachatDetailsGeneratorTest {

	@InjectMocks
	private RachatDetailsGenerator classUnderTest;

	@Test
	public void rachatEnGain() {
		TransactionTaxDTO transactionTax = new TransactionTaxDTO();

		Date trnDate = new GregorianCalendar(2018, Calendar.FEBRUARY, 5).getTime();

		transactionTax.setTransactionDate(trnDate);
		transactionTax.setTransactionNetAmount(BigDecimal.valueOf(25000));
		transactionTax.setTransactionTaxId(10020L);
		transactionTax.setPolicyValue(BigDecimal.valueOf(1004521.21));

		TransactionTaxDetailsDTO previousDetail1 = new TransactionTaxDetailsDTO();
		Date sixteenMarch = new GregorianCalendar(2015, Calendar.MARCH, 16).getTime();
		previousDetail1.setPremiumDate(sixteenMarch);
		previousDetail1.setPremiumValueBefore(BigDecimal.valueOf(495587));
		previousDetail1.setPremiumValueAfter(BigDecimal.valueOf(495587));
		previousDetail1.setSplitPercent(BigDecimal.valueOf(49.78));

		TransactionTaxDetailsDTO previousDetail2 = new TransactionTaxDetailsDTO();
		Date seventeenNovember = new GregorianCalendar(2017, Calendar.NOVEMBER, 17).getTime();
		previousDetail2.setPremiumDate(seventeenNovember);
		previousDetail2.setPremiumValueBefore(BigDecimal.valueOf(0));
		previousDetail2.setPremiumValueAfter(BigDecimal.valueOf(500000));
		previousDetail2.setSplitPercent(BigDecimal.valueOf(50.22));

		List<TransactionTaxDetailsDTO> result = classUnderTest.generateTransactionTaxDetails(transactionTax, Arrays.asList(previousDetail1, previousDetail2),true);

		Assertions.assertThat(result).hasSize(2);

		TransactionTaxDetailsDTO replicatedTaxDetails1 = result.stream()
				.filter(detail -> detail.getPremiumDate().equals(sixteenMarch))
				.findFirst().orElseThrow(() -> new AssertionError("No tax detail was found for the date " + sixteenMarch));

		Assert.assertEquals(Long.valueOf(10020), replicatedTaxDetails1.getTransactionTaxId());
		assertEqualsDecimal(BigDecimal.valueOf(495587), replicatedTaxDetails1.getPremiumValueBefore());
		assertEqualsDecimal(BigDecimal.valueOf(483252.68), replicatedTaxDetails1.getPremiumValueAfter());
		assertEqualsDecimal(BigDecimal.valueOf(49.78), replicatedTaxDetails1.getSplitPercent());
		assertEqualsDecimal(BigDecimal.valueOf(110.68), replicatedTaxDetails1.getCapitalGainAmount());

		TransactionTaxDetailsDTO replicatedTaxDetails2 = result.stream()
				.filter(detail -> detail.getPremiumDate().equals(seventeenNovember))
				.findFirst().orElseThrow(() -> new AssertionError("No tax detail was found for the date " + seventeenNovember));

		Assert.assertEquals(Long.valueOf(10020), replicatedTaxDetails2.getTransactionTaxId());
		Assert.assertEquals(BigDecimal.valueOf(500000), replicatedTaxDetails2.getPremiumValueBefore());
		assertEqualsDecimal(BigDecimal.valueOf(487556.26), replicatedTaxDetails2.getPremiumValueAfter());
		assertEqualsDecimal(BigDecimal.valueOf(50.22), replicatedTaxDetails2.getSplitPercent());
		assertEqualsDecimal(BigDecimal.valueOf(111.67), replicatedTaxDetails2.getCapitalGainAmount());
	}

	@Test
	public void rachatEnPerte() {
		TransactionTaxDTO transactionTax = new TransactionTaxDTO();

		Date trnDate = new GregorianCalendar(2018, Calendar.FEBRUARY, 5).getTime();

		transactionTax.setTransactionDate(trnDate);
		transactionTax.setTransactionNetAmount(BigDecimal.valueOf(25000));
		transactionTax.setTransactionTaxId(10020L);
		transactionTax.setPolicyValue(BigDecimal.valueOf(804521.21));

		TransactionTaxDetailsDTO previousDetail1 = new TransactionTaxDetailsDTO();
		Date sixteenMarch = new GregorianCalendar(2015, Calendar.MARCH, 16).getTime();
		previousDetail1.setPremiumDate(sixteenMarch);
		previousDetail1.setPremiumValueBefore(BigDecimal.valueOf(495587));
		previousDetail1.setPremiumValueAfter(BigDecimal.valueOf(495587));
		previousDetail1.setSplitPercent(BigDecimal.valueOf(49.78));

		TransactionTaxDetailsDTO previousDetail2 = new TransactionTaxDetailsDTO();
		Date seventeenNovember = new GregorianCalendar(2017, Calendar.NOVEMBER, 17).getTime();
		previousDetail2.setPremiumDate(seventeenNovember);
		previousDetail2.setPremiumValueBefore(BigDecimal.valueOf(0));
		previousDetail2.setPremiumValueAfter(BigDecimal.valueOf(500000));
		previousDetail2.setSplitPercent(BigDecimal.valueOf(50.22));

		List<TransactionTaxDetailsDTO> result = classUnderTest.generateTransactionTaxDetails(transactionTax, Arrays.asList(previousDetail1, previousDetail2),true);

		Assertions.assertThat(result).hasSize(2);

		TransactionTaxDetailsDTO replicatedTaxDetails1 = result.stream()
				.filter(detail -> detail.getPremiumDate().equals(sixteenMarch))
				.findFirst().orElseThrow(() -> new AssertionError("No tax detail was found for the date " + sixteenMarch));

		Assert.assertEquals(Long.valueOf(10020), replicatedTaxDetails1.getTransactionTaxId());
		assertEqualsDecimal(BigDecimal.valueOf(495587), replicatedTaxDetails1.getPremiumValueBefore());
		assertEqualsDecimal(BigDecimal.valueOf(483142.00), replicatedTaxDetails1.getPremiumValueAfter());
		assertEqualsDecimal(BigDecimal.valueOf(49.78), replicatedTaxDetails1.getSplitPercent());
		assertEqualsDecimal(BigDecimal.valueOf(-2955.56), replicatedTaxDetails1.getCapitalGainAmount());

		TransactionTaxDetailsDTO replicatedTaxDetails2 = result.stream()
				.filter(detail -> detail.getPremiumDate().equals(seventeenNovember))
				.findFirst().orElseThrow(() -> new AssertionError("No tax detail was found for the date " + seventeenNovember));

		Assert.assertEquals(Long.valueOf(10020), replicatedTaxDetails2.getTransactionTaxId());
		assertEqualsDecimal(BigDecimal.valueOf(500000), replicatedTaxDetails2.getPremiumValueBefore());
		assertEqualsDecimal(BigDecimal.valueOf(487445.00), replicatedTaxDetails2.getPremiumValueAfter());
		assertEqualsDecimal(BigDecimal.valueOf(50.22), replicatedTaxDetails2.getSplitPercent());
		assertEqualsDecimal(BigDecimal.valueOf(-2981.69), replicatedTaxDetails2.getCapitalGainAmount());
	}

	private void assertEqualsDecimal(BigDecimal expected, BigDecimal actual) {
		Assertions.assertThat(actual.round(new MathContext(2))).isEqualByComparingTo(expected.round(new MathContext(2)));
	}

	@Test
	public void supportsType() {
		Assert.assertTrue(classUnderTest.supportsType("ANY"));
		Assert.assertFalse(classUnderTest.supportsType("PREM"));
	}
}

package lu.wealins.webia.services.core.service.impl;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;

@RunWith(MockitoJUnitRunner.class)
public class DetailsBridgeGeneratorTest {

	@InjectMocks
	DetailsBridgeGenerator classUnderTest;
	
	@Mock
	private TransactionTaxDetailsGenerator detailsGenerator;

	@Before
	public void before() {
		classUnderTest.setDetailsGenerators(Arrays.asList(detailsGenerator));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void nominalCase() {
		TransactionTaxDTO transactionTax = Mockito.mock(TransactionTaxDTO.class);
		List<TransactionTaxDetailsDTO> details = Mockito.mock((List.class));
		Mockito.when(detailsGenerator.supportsType("mockType")).thenReturn(true);
		Mockito.when(transactionTax.getTransactionType()).thenReturn("mockType");
		List<TransactionTaxDetailsDTO> mockAnswer = Mockito.mock(List.class);
		Mockito.when(detailsGenerator.generateTransactionTaxDetails(transactionTax, details, true)).thenReturn(mockAnswer);

		Assert.assertEquals(mockAnswer, classUnderTest.generateTransactionTaxDetails(transactionTax, details,true));
	}

	@SuppressWarnings("unchecked")
	@Test(expected = IllegalStateException.class)
	public void noGeneratorFound() {
		TransactionTaxDTO transactionTax = Mockito.mock(TransactionTaxDTO.class);
		Mockito.when(detailsGenerator.supportsType("mockType")).thenReturn(false);
		Mockito.when(transactionTax.getTransactionType()).thenReturn("mockType");

		classUnderTest.generateTransactionTaxDetails(transactionTax, Mockito.mock(List.class), true);
	}
}

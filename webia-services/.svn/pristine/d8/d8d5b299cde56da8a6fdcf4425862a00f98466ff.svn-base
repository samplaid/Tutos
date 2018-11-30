package lu.wealins.webia.services.core.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;
import lu.wealins.webia.services.core.service.TransactionTaxDetailsService;
import lu.wealins.webia.services.core.service.TransactionTaxService;

@RunWith(MockitoJUnitRunner.class)
public class TaxBatchServiceImplTest {

	@InjectMocks
	private TaxBatchServiceImpl classUnderTest;

	@Mock
	private TransactionTaxService transactionTaxService;

	@Mock
	private TransactionTaxDetailsService detailsService;

	@Mock
	private TaxBatchSaver taxBatchSaver;

	@Mock
	private DetailsBridgeGenerator detailsBridgeGenerator;

	@SuppressWarnings("unchecked")
	@Test
	@Ignore
	public void createTransactionTaxDetails() {
		TransactionTaxDTO mockTransactionTax1 = Mockito.mock(TransactionTaxDTO.class);
		TransactionTaxDTO mockTransactionTax2 = Mockito.mock(TransactionTaxDTO.class);
		List<TransactionTaxDTO> transactionTaxList = Arrays.asList(mockTransactionTax1, mockTransactionTax2);

		Mockito.when(mockTransactionTax1.getTransactionTaxId()).thenReturn(1L);
		Mockito.when(mockTransactionTax1.getPreviousTransactionId()).thenReturn(3L);
		List<TransactionTaxDetailsDTO> mockedPreviousDetails = Mockito.mock(List.class);
		Mockito.when(detailsService.getByTransactionTaxId(3L)).thenReturn(mockedPreviousDetails);
		List<TransactionTaxDetailsDTO> mockedGeneratedDetails1 = Mockito.mock(List.class);
		Mockito.when(detailsBridgeGenerator.generateTransactionTaxDetails(mockTransactionTax1, mockedPreviousDetails,true)).thenReturn(mockedGeneratedDetails1);

		Mockito.when(mockTransactionTax2.getTransactionTaxId()).thenReturn(2L);
		Mockito.when(mockTransactionTax2.getPreviousTransactionId()).thenReturn(null);
		List<TransactionTaxDetailsDTO> mockedGeneratedDetails2 = Mockito.mock(List.class);
		Mockito.when(detailsBridgeGenerator.generateTransactionTaxDetails(mockTransactionTax2, null,true)).thenReturn(mockedGeneratedDetails2);
	
		Mockito.when(transactionTaxService.updateTransactions(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(1L));
		Mockito.when(transactionTaxService.getNewTransactions()).thenReturn(transactionTaxList);

		List<Long> result = classUnderTest.createTransactionTaxDetails();

		Assertions.assertThat(result).containsExactly(1L);
		Mockito.verify(taxBatchSaver).saveDetails(Mockito.eq(mockedGeneratedDetails1), Mockito.eq(1L), Mockito.any(Consumer.class), Mockito.any(Consumer.class));
		Mockito.verify(taxBatchSaver).saveDetails(Mockito.eq(mockedGeneratedDetails2), Mockito.eq(2L), Mockito.any(Consumer.class), Mockito.any(Consumer.class));
		Mockito.verifyNoMoreInteractions(taxBatchSaver);
	}

}

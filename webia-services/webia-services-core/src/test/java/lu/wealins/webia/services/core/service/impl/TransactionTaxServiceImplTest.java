package lu.wealins.webia.services.core.service.impl;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.enums.TransactionTaxOrigin;
import lu.wealins.common.dto.webia.services.enums.TransactionTaxStatus;
import lu.wealins.webia.services.core.mapper.TransactionTaxMapper;
import lu.wealins.webia.services.core.persistence.entity.TransactionTaxEntity;
import lu.wealins.webia.services.core.persistence.repository.TransactionTaxRepository;

@RunWith(MockitoJUnitRunner.class)
public class TransactionTaxServiceImplTest {

	@InjectMocks
	private TransactionTaxServiceImpl classUnderTest;

	@Mock
	private TransactionTaxRepository transactionTaxRepository;

	@Mock
	private TransactionTaxMapper transactionMapper;

	@Test
	public void markAsUpdatedDataBackup() {
		TransactionTaxEntity mock = Mockito.mock(TransactionTaxEntity.class);
		// Mockito.when(mock.getStatus()).thenReturn(TransactionTaxStatus.NEW.getStatusNumber());
		Mockito.when(transactionTaxRepository.findOne(1L)).thenReturn(mock);

		classUnderTest.markAsUpdated(1L);
		Mockito.verify(mock).setStatus(TransactionTaxStatus.NEW.getStatusNumber());
	}

	@Test
	public void markAsUpdatedPrime() {
		TransactionTaxEntity mock = Mockito.mock(TransactionTaxEntity.class);
		// Mockito.when(mock.getStatus()).thenReturn(TransactionTaxStatus.NEW.getStatusNumber());
		// Mockito.when(mock.getTransactionType()).thenReturn(TransactionTaxType.PREM.getCode());
		Mockito.when(transactionTaxRepository.findOne(1L)).thenReturn(mock);

		classUnderTest.markAsUpdated(1L);
		Mockito.verify(mock).setStatus(TransactionTaxStatus.NEW.getStatusNumber());
	}

	@Test
	public void markAsUpdatedRachat() {
		TransactionTaxEntity mock = Mockito.mock(TransactionTaxEntity.class);
		// Mockito.when(mock.getStatus()).thenReturn(TransactionTaxStatus.NEW.getStatusNumber());
		// Mockito.when(mock.getTransactionType()).thenReturn("WITH");
		Mockito.when(transactionTaxRepository.findOne(1L)).thenReturn(mock);

		classUnderTest.markAsUpdated(1L);
		Mockito.verify(mock).setStatus(TransactionTaxStatus.NEW.getStatusNumber());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getNewTransactions() {
		List<Integer> expectedStatusList = Arrays.asList(TransactionTaxStatus.CALCULATED_EDITION.getStatusNumber());
		List<TransactionTaxEntity> mockEntities = Mockito.mock(List.class);
		List<TransactionTaxDTO> mockDtos = Mockito.mock(List.class);
		Mockito.when(transactionMapper.asTransactionTaxDTOs(mockEntities)).thenReturn(mockDtos);

		Mockito.when(transactionTaxRepository.findAllByStatusInAndOrigin(expectedStatusList,
				TransactionTaxOrigin.DALI.name())).thenReturn(mockEntities);

		List<TransactionTaxDTO> result = classUnderTest.getNewTransactions();

		Assert.assertEquals(mockDtos, result);
	}


}

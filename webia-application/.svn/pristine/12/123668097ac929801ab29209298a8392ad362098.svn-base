package lu.wealins.webia.core.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.TaxBatchResponse;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.PolicyOrigin;

@RunWith(MockitoJUnitRunner.class)
public class TaxBatchServiceImplTest {

	@InjectMocks
	private TaxBatchServiceImpl classUnderTest;

	@Mock
	private RestClientUtils restClientUtils;

	@Mock
	private EditingService editingService;

	@Mock
	DaliServiceImpl daliService;

	@Test
	public void createTransactionTaxDetails() {
		String product = "4 for Life";
		TaxBatchResponse response = new TaxBatchResponse();
		response.setTransactionTaxIds(Arrays.asList(1L));
		Mockito.when(restClientUtils.post("webia/transactionTax/details", null, TaxBatchResponse.class)).thenReturn(response);
		Mockito.when(daliService.initEventPlusValueBeforeAndAfter(1L)).thenReturn(product);

		CreateEditingRequest request = new CreateEditingRequest();
		request.setPolicyOrigin(PolicyOrigin.DALI.name());
		request.setDocumentType(DocumentType.ANNEX_FISC);
		request.setTransactionTax(1L);
		request.setProduct(product);

		classUnderTest.createTransactionTaxDetails();
		ArgumentCaptor<CreateEditingRequest> argumentCaptor = ArgumentCaptor.forClass(CreateEditingRequest.class);
		Mockito.verify(editingService).generateSurrenderDoc(argumentCaptor.capture());

		CreateEditingRequest capturedArgument = argumentCaptor.<CreateEditingRequest>getValue();
		assertEquals(capturedArgument.getDocumentType(), DocumentType.ANNEX_FISC);
		assertTrue(capturedArgument.getTransactionTax().equals(1L));
		assertTrue(capturedArgument.getPolicyOrigin().equals(PolicyOrigin.DALI.name()));
		Mockito.verifyNoMoreInteractions(editingService);
	}

}

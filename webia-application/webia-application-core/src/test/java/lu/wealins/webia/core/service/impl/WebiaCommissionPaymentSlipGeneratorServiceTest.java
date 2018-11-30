/**
 * 
 */
package lu.wealins.webia.core.service.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import lu.wealins.common.dto.webia.services.StatementDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.webia.core.service.WebiaCommissionsService;
import lu.wealins.webia.core.service.helper.FileHelper;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.UpdateEditingRequestRequest;
import lu.wealins.webia.ws.rest.request.UpdateEditingRequestResponse;

/**
 * @author ORO
 *
 */
@RunWith(PowerMockRunner.class)
public class WebiaCommissionPaymentSlipGeneratorServiceTest {
	private static final String WEBIA_GET_PAYMENT_SLIP_DATA = "webia/paymentCommissionSlip/";
	private static final String UPDATE_EDITING_REQUEST = "updateEditingRequest";

	@InjectMocks
	private WebiaCommissionPaymentSlipGeneratorServiceImpl webiaCommissionPaymentSlipGeneratorService;

	@Mock
	private FileHelper fileHelper;

	@Mock
	private WebiaCommissionsService commissionsService;

	@Mock
	private RestClientUtils restClientUtils;

	/**
	 * Test method for {@link lu.wealins.webia.core.service.impl.WebiaCommissionPaymentSlipGeneratorServiceImpl#generatePaymentSlip(javax.ws.rs.core.SecurityContext, java.lang.Long)}.
	 */
	@Test
	public void testGeneratePaymentSlipLong() {

	}

	/**
	 * Test method for
	 * {@link lu.wealins.webia.core.service.impl.WebiaCommissionPaymentSlipGeneratorServiceImpl#generatePaymentSlip(javax.ws.rs.core.SecurityContext, lu.wealins.webia.ws.rest.request.EditingRequest)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGeneratePaymentSlipEditingRequest() throws Exception {
		EditingRequest editingRequest = new EditingRequest();
		editingRequest.setId(12L);
		editingRequest.setStatement("168");

		String path = "src/test/resources/mock/files/generated/commissions/";

		File directory = new File(path + DateFormatUtils.format(new Date(), "yyyy-MM-dd"));

		if (!directory.exists()) {
			directory.mkdirs();
		}

		Field csvFileLocation = PowerMockito.field(webiaCommissionPaymentSlipGeneratorService.getClass(), "csvFileLocation");
		csvFileLocation.setAccessible(true);
		csvFileLocation.set(webiaCommissionPaymentSlipGeneratorService, path);

		UpdateEditingRequestResponse editingRequestResponse = new UpdateEditingRequestResponse();
		editingRequestResponse.setRequest(editingRequest);

		PowerMockito.when(restClientUtils.post(Mockito.eq(UPDATE_EDITING_REQUEST), Mockito.any(UpdateEditingRequestRequest.class), Mockito.eq(UpdateEditingRequestResponse.class)))
				.thenReturn(editingRequestResponse);

		PowerMockito.when(commissionsService.getStatement(editingRequest.getStatement())).thenReturn(createStatementMock(editingRequest.getStatement()));

		MultivaluedMap<String, Object> query = new MultivaluedHashMap<>();
		query.putSingle("statementId", editingRequest.getStatement());
		Mockito.when(restClientUtils.get(Mockito.eq(WEBIA_GET_PAYMENT_SLIP_DATA), Mockito.eq("findBy"), Mockito.eq(query), Mockito.eq(new GenericType<List<TransferDTO>>() {
		})))
				.thenReturn(createCommissionPaymentSlipGeneration(Long.parseLong(editingRequest.getStatement())));

		EditingRequest updatedRequest = webiaCommissionPaymentSlipGeneratorService.generatePaymentSlip(editingRequest);

		Assert.assertNotNull(updatedRequest.getOutputStreamPath());
		Assert.assertFalse(updatedRequest.getOutputStreamPath().isEmpty());

		File out = new File(updatedRequest.getOutputStreamPath() + ".csv");
		Assert.assertNotNull(out.exists());

		out.delete();
		directory.delete();
	}

	private List<TransferDTO> createCommissionPaymentSlipGeneration(Long statementId) {
		List<TransferDTO> transferDTOs = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			TransferDTO transfer = new TransferDTO();
			transfer.setLibDonOrd(RandomStringUtils.random(9, true, true));
			transfer.setTransferId(RandomUtils.nextLong());

			if (i == 2) {
				transfer.setLibDonOrd(RandomStringUtils.random(9, true, true) + "            ");
			}

			if (i == 3) {
				transfer.setLibDonOrd("");
			}

			if (i == 4) {
				transfer.setLibDonOrd(null);
			}

			transfer.setStatementId(statementId);
			transfer.setTrfMt(new BigDecimal(RandomUtils.nextInt()));
			transfer.setTrfCurrency(RandomStringUtils.random(3, 'E', 'U', 'R', 'S', 'D'));
			transfer.setTrfComm(RandomStringUtils.randomAlphanumeric(15));
			transfer.setIbanDonOrd(RandomStringUtils.random(9, true, true));
			transfer.setSwiftDonOrd(RandomStringUtils.random(9, true, true));
			transfer.setIbanBenef(RandomStringUtils.random(9, true, true));
			transfer.setSwiftBenef(RandomStringUtils.random(9, true, true));
			transfer.setLibBenef(RandomStringUtils.random(9, true, true));
			transferDTOs.add(transfer);
		}

		return transferDTOs;
	}

	private StatementDTO createStatementMock(String id) {
		StatementDTO statementDTO = new StatementDTO();
		statementDTO.setStatementId(Long.parseLong(id));
		statementDTO.setStatementType("ENTRY");
		statementDTO.setPeriod("201806");
		return statementDTO;
	}

}

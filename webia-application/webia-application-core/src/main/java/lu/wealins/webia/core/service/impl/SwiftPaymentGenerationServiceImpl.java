package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AgentContactDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.webia.services.EditingRequestStatus;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.SWIFTTransferDTO;
import lu.wealins.editing.common.webia.Account;
import lu.wealins.editing.common.webia.AmountType;
import lu.wealins.editing.common.webia.BankTransfer;
import lu.wealins.editing.common.webia.BankTransfer.Transfer;
import lu.wealins.editing.common.webia.BankTransfer.Transfer.DebitAccount;
import lu.wealins.editing.common.webia.CorrespondenceAddress;
import lu.wealins.editing.common.webia.CoverLetter;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Documents;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.editing.common.webia.PersonLight;
import lu.wealins.webia.core.mapper.PersonMapper;
import lu.wealins.webia.core.mapper.TransferMapper;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.SwiftPaymentGenerationService;
import lu.wealins.webia.core.service.LiabilityAgentContactService;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.TransferService;
import lu.wealins.webia.core.service.helper.FollowUpDocumentContentHelper;
import lu.wealins.webia.core.utils.Constantes;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.EditingUser;

@Service
@Transactional(readOnly = true)
public class SwiftPaymentGenerationServiceImpl implements SwiftPaymentGenerationService {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SwiftPaymentGenerationServiceImpl.class);

	private final String PARTIAL_SURRENDER = "Partial surrender";
	
	/**
	 * Services
	 */
	@Autowired
	private DocumentService documentGenerationService;

	@Autowired
	private TransferService transferService;
	
	@Autowired
	private LiabilityAgentContactService liabilityAgentContactService;
	
	@Autowired
	private LiabilityAgentService liabilityAgentService;
	
	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private TransferMapper transferMapper; 
	
	@Autowired
	private FollowUpDocumentContentHelper followUpDocumentContentHelper;
	
	/**
	 * Env. variable
	 */
	@Value("${editique.csv.path}")
	private String filePath;

	@Override
	public EditingRequest generateSwiftPayment(SecurityContext context, EditingRequest editingRequest) {

		try {
			Assert.notNull(editingRequest, "The edition request can not be null");

			editingRequest.setStatus(EditingRequestStatus.IN_PROGRESS);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

			EditingUser creationUser = editingRequest.getCreationUser();

			Assert.notNull(creationUser, "The creation user of the document generation request cannot be null.");
			Assert.isTrue(StringUtils.isNotBlank(editingRequest.getTransferIds()), "The transfers list cannot be null.");

			String documentPath = getPath(editingRequest);
			String saveResponse = documentGenerationService.saveCsvDocument( documentPath,createDatas(editingRequest));

			editingRequest.setInputStreamPath(saveResponse);
			editingRequest.setOutputStreamPath(saveResponse);
			editingRequest.setStatus(EditingRequestStatus.GENERATED);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

		} catch (Exception e) {
			editingRequest.setStatus(EditingRequestStatus.IN_ERROR);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

			LOGGER.error("Error occurred during the SWIFT Payment generation for transferIds "+ editingRequest.getTransferIds() , e);
		}

		return editingRequest;
	}


	private String getPath(EditingRequest editingRequest) {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constantes.YYYYMMDDHHMMSSSSS);
		String today = date.format(formatter);
		String documentPath = filePath + "/CSV_SWIFT-" + editingRequest.getId().toString() + "-" + today + ".csv";
		return documentPath;
	}

	
	private List<SWIFTTransferDTO> createDatas(EditingRequest editingRequest) {
				
		// retrieve the Transfers data related to the request (for a same bank/currency/beneficiary )
		String[] transferIdsString = editingRequest.getTransferIds().replaceAll(" ", "").split(",");
		List<Long> transferIds = new ArrayList<Long>();
		for (int i = 0; i < transferIdsString.length; i++) {
			transferIds.add(Long.valueOf(transferIdsString[i]));
		}
		List<TransferDTO> transferDTOs = (List<TransferDTO>) transferService.getTransfers(transferIds);
		List<SWIFTTransferDTO> transfertToSave = transferMapper.asSWIFTTransferDTO(transferDTOs);
		
		return transfertToSave;
	}
	
}

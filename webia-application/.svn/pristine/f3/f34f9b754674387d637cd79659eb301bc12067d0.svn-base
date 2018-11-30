package lu.wealins.webia.core.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.core.SecurityContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jboss.resteasy.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.webia.services.BatchUploadResponse;
import lu.wealins.common.dto.webia.services.CommissionToPayDTO;
import lu.wealins.common.dto.webia.services.CommissionToPayWrapperDTO;
import lu.wealins.common.dto.webia.services.DocumentIdReservationRequest;
import lu.wealins.common.dto.webia.services.DocumentIdReservationResponse;
import lu.wealins.common.dto.webia.services.EditingRequestStatus;
import lu.wealins.common.dto.webia.services.StatementDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.TransferWrapperDTO;
import lu.wealins.editing.common.webia.AmountType;
import lu.wealins.editing.common.webia.CommissionType;
import lu.wealins.editing.common.webia.Commissions;
import lu.wealins.editing.common.webia.Commissions.CommissionsSet;
import lu.wealins.editing.common.webia.Commissions.CommissionsSet.CommissionLine;
import lu.wealins.editing.common.webia.Commissions.Payments;
import lu.wealins.editing.common.webia.Commissions.Payments.Payment;
import lu.wealins.editing.common.webia.CorrespondenceAddress;
import lu.wealins.editing.common.webia.CoverLetter;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Documents;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.editing.common.webia.PersonLight;
import lu.wealins.webia.core.exceptions.FileUploadException;
import lu.wealins.webia.core.mapper.PersonMapper;
import lu.wealins.webia.core.mapper.UserMapper;
import lu.wealins.webia.core.service.CommissionStatementFilenetService;
import lu.wealins.webia.core.service.DocumentGenerationCommissionsService;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.WebiaCommissionsService;
import lu.wealins.webia.core.service.helper.FileHelper;
import lu.wealins.webia.core.utils.CommissionUtils;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.EditingUser;
import lu.wealins.webia.ws.rest.request.SaveDocumentIntoFileNetRequest;
import lu.wealins.webia.ws.rest.request.TranscoType;
import lu.wealins.webia.ws.rest.request.UpdateEditingRequestRequest;
import lu.wealins.webia.ws.rest.request.UpdateEditingRequestResponse;

@Service
@Transactional(readOnly = true)
public class DocumentGenerationCommissionsServiceImpl implements DocumentGenerationCommissionsService {

	private static final String COMMISSI = "COMMISSI";
	private static final String EMPTY_STRING = "";
	private static final Double EMPTY_DOUBLE = 0D;
	private static final String OPCVM_TYPE = "OPCVM";
	private static final String ADM_TYPE = "ADM";
	private static final String SURR_TYPE = "SURR";
	private static final String SWITCH_TYPE = "SWITCH";
	private static final String ENTRY_TYPE = "ENTRY";
	
	private static final String PDF_MIMETYPE = "application/pdf";
	private static final String EXCEL_MIMETYPE = "application/vnd.ms-excel";

	/**
	 * The logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(DocumentGenerationCommissionsServiceImpl.class);

	/**
	 * Utils
	 */
	@Autowired
	private RestClientUtils restClientUtils;

	/**
	 * Services
	 */
	@Autowired
	private WebiaCommissionsService webiaCommissionsService;
	@Autowired
	private LiabilityAgentService liabilityAgentService;

	@Autowired
	private CommissionStatementFilenetService commissionStatementFilenetService;

	@Autowired
	private FileHelper fileHelper;

	/**
	 * Mappers
	 */
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PersonMapper personMapper;

	@Autowired
	private DocumentService documentGenerationService;

	private String generatePath;

	private Map<Integer, String> mapSubBroker = null;

	private static final String ACTIVE_1 = "1";

	/**
	 * Constants
	 */
	private static final String UPDATE_EDITING_REQUEST = "updateEditingRequest";

	/**
	 * Env. variable
	 */
	@Value("${editique.xml.path}")
	private String filePath;

	@Override
	public EditingRequest generateDocumentCommissions(SecurityContext context, EditingRequest editingRequest) {

		try {
			Assert.notNull(editingRequest, "The edition request can not be null");
			mapSubBroker = new HashMap<Integer, String>();
			/**
			 * Set the request in progress
			 */
			editingRequest.setStatus(EditingRequestStatus.IN_PROGRESS);
			editingRequest = updateEditingRequest(editingRequest);

			String statementId = editingRequest.getStatement();
			String agentId = editingRequest.getAgent();
			EditingUser creationUser = editingRequest.getCreationUser();

			LocalDateTime date = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
			String formatedDate = date.format(formatter);
			generatePath = filePath + "/" + statementId + "_" + formatedDate;

			Assert.notNull(statementId, "The statement ID can not be null");
			Assert.notNull(agentId, "The agent ID can not be null");
			Assert.notNull(creationUser, "The creation user of the document generation request can not be null");

			LOG.info("Generating transfers list for statementId : " + statementId);
			LOG.info("Generating transfers list for agentId : " + agentId);
			TransferWrapperDTO transferWrapperDTO = webiaCommissionsService.getTransfersReady(agentId, statementId);
			StatementDTO statementDTO = webiaCommissionsService.getStatement(statementId);

			List<Payments> payments = getPaymentList(transferWrapperDTO);
			List<CommissionsSet> commisionsSet = getCommissionsSetList(transferWrapperDTO);
			AgentDTO agent = liabilityAgentService.getAgent(agentId);

			String language = getLanguage(agent);
			String email = getMailAdresse(agent);

			/**
			 * Reserve document id on fileNet
			 */
			editingRequest = reserveDocIdAndUpdateRequest(editingRequest, agent, statementDTO);
			String fileNetDocumentId = editingRequest.getGedDocumentId();

			String saveResponse = generateCommissions(creationUser, statementDTO, agent, payments, commisionsSet, language, email, fileNetDocumentId);

			generateXlsDocument(statementDTO, agent, commisionsSet, editingRequest);

			/**
			 * Update the request
			 */
			editingRequest.setInputStreamPath(saveResponse);
			editingRequest.setStatus(EditingRequestStatus.XML_GENERATED);
			editingRequest = updateEditingRequest(editingRequest);

		} catch (Exception e) {
			editingRequest.setStatus(EditingRequestStatus.IN_ERROR);
			editingRequest = updateEditingRequest(editingRequest);

			LOG.error("Error during generating the commissions for the statement : " + editingRequest.getStatement(), e);
		}

		return editingRequest;
	}
	
	@Override
	public EditingRequest reserveDocIdAndUpdateRequest(EditingRequest editingRequest) {
		try {
			Assert.notNull(editingRequest, "The edition request can not be null");

			String agentId = editingRequest.getAgent();
			String statementId = editingRequest.getStatement();
			Assert.notNull(statementId, "The statement ID can not be null");
			Assert.notNull(agentId, "The agent ID can not be null");
			
			StatementDTO statementDTO = webiaCommissionsService.getStatement(statementId);
			AgentDTO agent = liabilityAgentService.getAgent(agentId);
			
			/**
			 * Reserve document id on fileNet
			 */
			editingRequest = reserveDocIdAndUpdateRequest(editingRequest, agent, statementDTO);
		} catch (Exception e) {
			editingRequest.setStatus(EditingRequestStatus.IN_ERROR);
			LOG.error("Error during reservation FileNet DocId for commissions for the statement : " + editingRequest.getStatement(), e);
		}		
		return editingRequest;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String uploadDocument(SaveDocumentIntoFileNetRequest uploadRequest) {
		Assert.notNull(uploadRequest);
		return commissionStatementFilenetService.uploadDocumentIntoFileNet(uploadRequest).getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BatchUploadResponse batchUploadDocument(List<SaveDocumentIntoFileNetRequest> uploadRequests) {
		BatchUploadResponse result = new BatchUploadResponse();

		for (SaveDocumentIntoFileNetRequest uploadRequest : uploadRequests) {
			try {
				String documentId = uploadDocument(uploadRequest);

				if (StringUtils.isNotBlank(documentId)) {
					result.getSuccess().add("The document " + uploadRequest.getDocumentTitle() + " was successful uploaded with id: " + documentId);
					renameFile(uploadRequest, ".uploaded");
				} else {
					throw new FileUploadException("The id returned during the upload document function is null or empty for document" + uploadRequest.getDocumentTitle());
				}

			} catch (Exception e) {
				String error = "The upload of document " + uploadRequest.getDocumentTitle() + " was in failure.";
				result.getFailure().add(error);
				LOG.error(error, e);
			}

		}

		return result;
	}

	/**
	 * @param uploadRequest
	 */
	private void renameFile(SaveDocumentIntoFileNetRequest uploadRequest, String suffixExtension) {
		File fileEntry = fileHelper.toFile(uploadRequest.getDocumentPath());
		fileEntry.renameTo(new File(fileEntry.getAbsoluteFile() + suffixExtension));
	}

	private EditingRequest reserveDocIdAndUpdateRequest(EditingRequest editingRequest, AgentDTO agent, StatementDTO statementDTO) {
		String gedId = null;
		String agentCrmId = agent.getCrmRefererence();
		if (agentCrmId != null && !agentCrmId.isEmpty()) {
			String fileDate = null;
			if (editingRequest.getEventDate()!=null){
				fileDate = DateUtil.formatDate(editingRequest.getEventDate(), "yyyyMMddHHmmss");
			} else {
				fileDate = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
			}
			String docTitle = statementDTO.getStatementId() + "_" + agent.getAgtId() + "_" + fileDate + ".pdf";
			gedId = reserveFileNetId("WLSCommissionStatement", agentCrmId, getCommissionStartDate(statementDTO), CommissionUtils.asSapCommissionTypes(statementDTO.getStatementType()),
					docTitle, PDF_MIMETYPE);
			editingRequest.setGedClass("WLSCommissionStatement");
			editingRequest.setGedDocumentId(gedId);
		} else {
			LOG.error("Impossible to insert the generated file into FileNet, the agent " + agent.getAgtId() + " doesn't have CRM id");
		}
		return editingRequest;
	}

	private String reserveFileNetId(String fileNetDocumentClass, String agentId, Date effectiveDate, String commissionType, String docTitle, String contentType) {
		DocumentIdReservationRequest reserveDocumentIdRequest = new DocumentIdReservationRequest();
		reserveDocumentIdRequest.setDocumentClass(fileNetDocumentClass);
		reserveDocumentIdRequest.setEffectiveDate(effectiveDate);
		reserveDocumentIdRequest.setAgentId(agentId);
		reserveDocumentIdRequest.setCommissionType(commissionType);
		reserveDocumentIdRequest.setDocumentTitle(docTitle);
		reserveDocumentIdRequest.setMimeType(contentType);
		DocumentIdReservationResponse reserveDocumentIdResponse = commissionStatementFilenetService.reserveDocumentId(reserveDocumentIdRequest);
		return reserveDocumentIdResponse.getId();
	}

	private void generateXlsDocument(StatementDTO statementDTO, AgentDTO agent, List<CommissionsSet> commisionsSet, EditingRequest editingRequest) {

		try (Workbook wb = new HSSFWorkbook()) {
			Sheet sheet = wb.createSheet();

			createHeader(sheet);

			CreationHelper createHelper = wb.getCreationHelper();
			DataFormat format = wb.createDataFormat();
			CellStyle cellStyleDate = wb.createCellStyle();
			cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
			CellStyle cellStylePercentage = wb.createCellStyle();
			cellStylePercentage.setDataFormat(format.getFormat("0.000000"));
			CellStyle cellStylePrice = wb.createCellStyle();
			cellStylePrice.setDataFormat(format.getFormat("0.00"));
			int rowNum = 1;

			for (int index = 0; index < commisionsSet.size(); index++) {
				CommissionsSet commissionsSet = commisionsSet.get(index);
				for (CommissionLine commissionLine : commissionsSet.getCommissionLines()) {

					int colNum = 0;
					Row row = sheet.createRow(rowNum++);

					// Agent id
					Cell cell = row.createCell(colNum++);
					cell.setCellValue(StringUtils.defaultIfEmpty(agent.getAgtId(), EMPTY_STRING));
					// Agent name
					cell = row.createCell(colNum++);
					cell.setCellValue(StringUtils.defaultIfEmpty(agent.getName(), EMPTY_STRING));
					// SubPartner id
					cell = row.createCell(colNum++);
					cell.setCellValue(StringUtils.defaultIfEmpty(mapSubBroker.get(index), EMPTY_STRING));
					// SubPartner name
					cell = row.createCell(colNum++);
					cell.setCellValue(StringUtils.defaultIfEmpty(commissionsSet.getSubPartner(), EMPTY_STRING));
					// Policy id
					cell = row.createCell(colNum++);
					cell.setCellValue(StringUtils.defaultIfEmpty(commissionLine.getContractNumber(), EMPTY_STRING));
					// Effective date
					cell = row.createCell(colNum++);
					cell.setCellValue(commissionLine.getValueDate());
					cell.setCellStyle(cellStyleDate);
					// Type of transaction
					cell = row.createCell(colNum++);
					if (commissionsSet.getCommissionType() != null) {
						cell.setCellValue(StringUtils.defaultIfEmpty(commissionsSet.getCommissionType().value(), EMPTY_STRING));
					} else {
						cell.setCellValue(EMPTY_STRING);
					}

					// Currency
					cell = row.createCell(colNum++);
					cell.setCellValue(StringUtils.defaultIfEmpty(commissionsSet.getCurrency(), EMPTY_STRING));
					// Commission
					cell = row.createCell(colNum++);
					cell.setCellValue(commissionLine.getCommissionAmount() != null ? commissionLine.getCommissionAmount().getValue().doubleValue() : EMPTY_DOUBLE);
					cell.setCellStyle(cellStylePrice);
					// Percentage
					cell = row.createCell(colNum++);
					cell.setCellValue(commissionLine.getPercentage() != null ? commissionLine.getPercentage().doubleValue() : EMPTY_DOUBLE);
					cell.setCellStyle(cellStylePercentage);
					// Prime or policy value
					cell = row.createCell(colNum++);
					if (commissionLine.getOperationAmount() != null && commissionLine.getOperationAmount().getValue() != null) {
						cell.setCellValue(commissionLine.getOperationAmount().getValue().doubleValue());
					} else {
						cell.setCellValue(EMPTY_DOUBLE);
					}

					cell.setCellStyle(cellStylePrice);
					/*
					 * REMOVE BEGIN AND END DATE // Early date cell = row.createCell(colNum++); cell.setCellValue(getCommissionStartDate(statementDTO)); cell.setCellStyle(cellStyleDate); // Early date
					 * cell = row.createCell(colNum++); cell.setCellValue(getCommissionEndDate(statementDTO)); cell.setCellStyle(cellStyleDate);
					 */
				}
			}

			String path = generatePath + ".xls";

			try (FileOutputStream outputStream = new FileOutputStream(path)) {
				wb.write(outputStream);
				
				LOG.info("Trying to upload xls generated file into FileNet");
				String gedId = null;				
				SaveDocumentIntoFileNetRequest saveXlsIntoFileNetRequest = new SaveDocumentIntoFileNetRequest();
				
				saveXlsIntoFileNetRequest.setAgentId(agent.getCrmRefererence());
				saveXlsIntoFileNetRequest.setContentType(EXCEL_MIMETYPE);
				saveXlsIntoFileNetRequest.setDocumentClass(editingRequest.getGedClass());
				saveXlsIntoFileNetRequest.setDocumentPath(path);
				saveXlsIntoFileNetRequest.setDocumentStatus("Active");
				String fileDate = null;
				if (editingRequest.getEventDate() != null){
					fileDate = DateUtil.formatDate(editingRequest.getEventDate(), "yyyyMMddHHmmss");
				} else {
					fileDate = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
				}
				String docTitle = editingRequest.getStatement() + "_" + editingRequest.getAgent() + "_" + fileDate + ".xls";
				saveXlsIntoFileNetRequest.setDocumentTitle(docTitle);
				gedId = reserveFileNetId("WLSCommissionStatement", agent.getCrmRefererence(), getCommissionStartDate(statementDTO), CommissionUtils.asSapCommissionTypes(statementDTO.getStatementType()),
						docTitle, EXCEL_MIMETYPE);
				saveXlsIntoFileNetRequest.setGedId(gedId);
				saveXlsIntoFileNetRequest.setEffectiveDate(getCommissionStartDate(statementDTO));
				saveXlsIntoFileNetRequest.setCommissionType(CommissionUtils.asSapCommissionTypes(statementDTO.getStatementType()));
				commissionStatementFilenetService.saveDocumentIntoFileNet(saveXlsIntoFileNetRequest);
			} catch (Exception e) {
				LOG.error("Cannot write in the file path " + path + " " + e.getMessage(), e);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

	}

	private void createHeader(Sheet sheet) {
		int colNum = 0;
		Row row = sheet.createRow(0);

		// Agent id
		Cell cell = row.createCell(colNum++);
		cell.setCellValue("Agent");
		cell = row.createCell(colNum++);
		cell.setCellValue("Name of Agent");
		cell = row.createCell(colNum++);
		cell.setCellValue("Sub-partner");
		cell = row.createCell(colNum++);
		cell.setCellValue("Name of sub-partner");
		cell = row.createCell(colNum++);
		cell.setCellValue("Policy");
		cell = row.createCell(colNum++);
		cell.setCellValue("Effective date");
		cell = row.createCell(colNum++);
		cell.setCellValue("Type of transaction");
		cell = row.createCell(colNum++);
		cell.setCellValue("Currency");
		cell = row.createCell(colNum++);
		cell.setCellValue("Commission");
		cell = row.createCell(colNum++);
		cell.setCellValue("Percentage");
		cell = row.createCell(colNum++);
		cell.setCellValue("Prime or Policy Value");
		/*
		 * REMOVE BEGIN AND END DATE // cell = row.createCell(colNum++); cell.setCellValue("Early period date"); cell = row.createCell(colNum++); cell.setCellValue("End period date");
		 */
	}

	private String getSubPartnerNameOrEmpty(String subPartnerId) {
		if (subPartnerId != null) {
			AgentDTO subPartner = null;
			try {
				subPartner = this.liabilityAgentService.getAgent(subPartnerId);
			} catch (Exception e) {
				LOG.error("The subPartner not found " + e);
			}

			return StringUtils.defaultIfEmpty(subPartner.getName(), subPartnerId);
		}

		return EMPTY_STRING;
	}

	private CoverLetter createCoverLetter(AgentDTO agent, String language, String email) {
		CoverLetter coverLetter = new CoverLetter();

		AgentContactLiteDTO contactPerson = getAgentContact(agent);
		PersonLight assetManagerPerson = personMapper.asPersonLight(contactPerson);

		CorrespondenceAddress correspondenceAddress = personMapper.asCorrespondenceAddress(agent);

		if (email != null) {
			correspondenceAddress.setEmail(email);
		}

		if (assetManagerPerson != null) {
			correspondenceAddress.setPersons(Arrays.asList(assetManagerPerson));
			coverLetter.setTitleId(assetManagerPerson.getTitleId());
		}

		coverLetter.setCorrespondenceAddress(correspondenceAddress);
		coverLetter.setLanguage(StringUtils.isNotBlank(language) ? language : "EN");

		return coverLetter;
	}

	private List<Payments> getPaymentList(TransferWrapperDTO transferWrapperDTO) {
		return createPayments(transferWrapperDTO.getTransferDTO());
		// return transferWrapperDTO.getTransferDTO().stream().map(x -> createPayments(x)).collect(Collectors.toList());
	}

	private List<CommissionsSet> getCommissionsSetList(TransferWrapperDTO transferWrapperDTO) {
		List<CommissionsSet> commissionsSetList = new ArrayList<CommissionsSet>();
		int index = 0;
		for (TransferDTO transferDTO : transferWrapperDTO.getTransferDTO()) {
			// Search commission with transfer id
			CommissionToPayWrapperDTO commissionToPayWrapperDTO = webiaCommissionsService.getCommissionsReady(String.valueOf(transferDTO.getTransferId()));

			List<CommissionToPayDTO> commissionToPayDTOs = commissionToPayWrapperDTO.getCommissionToPayEntities();

			if (!CollectionUtils.isEmpty(commissionToPayDTOs)) {
				List<CommissionToPayDTO> commissionToPayDTOsWithoutSubBroker = new ArrayList<CommissionToPayDTO>();
				List<CommissionToPayDTO> commissionToPayDTOsWithSubBroker = new ArrayList<CommissionToPayDTO>();

				for (CommissionToPayDTO com : commissionToPayDTOs) {
					if (com.getSubAgentId() == null) {
						commissionToPayDTOsWithoutSubBroker.add(com);
					} else {
						commissionToPayDTOsWithSubBroker.add(com);
					}
				}

				Map<String, List<CommissionToPayDTO>> commissionsGroupBySubBroker = commissionToPayDTOsWithSubBroker.stream().collect(Collectors.groupingBy(CommissionToPayDTO::getSubAgentId));

				if (!CollectionUtils.isEmpty(commissionToPayDTOsWithoutSubBroker)) {
					commissionsGroupBySubBroker.put("null", commissionToPayDTOsWithoutSubBroker);
				}

				for (Map.Entry<String, List<CommissionToPayDTO>> entry : commissionsGroupBySubBroker.entrySet()) {
					String subBroker = entry.getKey();
					List<CommissionToPayDTO> commissionsList = entry.getValue();

					Map<String, List<CommissionToPayDTO>> commissionsGroupByType = commissionsList.stream().collect(Collectors.groupingBy(CommissionToPayDTO::getComType));

					groupByForTypeADM(commissionsGroupByType);

					for (Map.Entry<String, List<CommissionToPayDTO>> comGroupByType : commissionsGroupByType.entrySet()) {
						List<CommissionToPayDTO> commissionsListGroupByType = comGroupByType.getValue();
						// Create commissions set
						CommissionsSet commissionsSet = createCommissionsSet(transferDTO, subBroker);
						BigDecimal calculAmout = BigDecimal.ZERO;
						List<CommissionLine> commissionLineList = new ArrayList<CommissionLine>();
						for (CommissionToPayDTO commissionToPayDTO : commissionsListGroupByType) {
							CommissionLine commissionLine = createCommissionLine(commissionToPayDTO);
							commissionLineList.add(commissionLine);
							calculAmout = calculAmout.add(commissionToPayDTO.getComAmount());
						}

						AmountType amountType = new AmountType();
						amountType.setCurrencyCode(transferDTO.getTrfCurrency());
						amountType.setValue(calculAmout);
						commissionsSet.setTotalAmount(amountType);
						commissionsSet.setCommissionLines(commissionLineList);
						commissionsSetList.add(index, commissionsSet);
						mapSubBroker.put(index, "null".equals(subBroker) ? null : subBroker);
						index++;
					}
				}
			}
		}

		return commissionsSetList;
	}

	private static <T> void groupByForTypeADM(Map<String, List<T>> commissionsGroupByType) {
		List<T> constructAdmTypeList = new ArrayList<T>();
		boolean isTypeAdm = false;

		if (commissionsGroupByType.containsKey(ADM_TYPE)) {
			constructAdmTypeList = commissionsGroupByType.get(ADM_TYPE);
			isTypeAdm = true;
		}

		if (commissionsGroupByType.containsKey(SURR_TYPE)) {
			constructAdmTypeList.addAll(commissionsGroupByType.get(SURR_TYPE));
			commissionsGroupByType.remove(SURR_TYPE);
			isTypeAdm = true;
		}
		if (commissionsGroupByType.containsKey(SWITCH_TYPE)) {
			constructAdmTypeList.addAll(commissionsGroupByType.get(SWITCH_TYPE));
			commissionsGroupByType.remove(SWITCH_TYPE);
			isTypeAdm = true;
		}

		if (isTypeAdm) {
			commissionsGroupByType.remove(ADM_TYPE);
			commissionsGroupByType.put(ADM_TYPE, constructAdmTypeList);
		}

	}

	private String generateCommissions(EditingUser creationUser, StatementDTO statementDTO, AgentDTO agent, List<Payments> payments, List<CommissionsSet> commisionsSet, String language,
			String email, String fileNetDocumentId) {

		Header header = generateHeader(creationUser, DocumentType.COMMISSIONS, language, fileNetDocumentId);

		Document document = generateDocument(header);
		CoverLetter coverLetter = createCoverLetter(agent, language, email);
		document.setCoverLetter(coverLetter);

		Commissions commissions = createCommissions(agent, statementDTO);

		commissions.setPayments(payments);
		commissions.setCommissionsSets(commisionsSet);
		document.setCommissions(commissions);

		Documents documents = new Documents();
		List<Document> docs = new ArrayList<>();

		docs.add(document);
		documents.setDocuments(docs);

		/**
		 * Save as XML
		 */
		String path = generatePath + ".xml";

		return saveXmlDocuments(documents, path);
	}

	private Date getCommissionStartDate(StatementDTO statementDTO) {
		Calendar cal = Calendar.getInstance();
		int year = Integer.valueOf(statementDTO.getPeriod().substring(0, 4));
		cal.set(Calendar.YEAR, year);
		if (ADM_TYPE.equals(statementDTO.getStatementType()) || OPCVM_TYPE.equals(statementDTO.getStatementType()) || SURR_TYPE.equals(statementDTO.getStatementType())
				|| SWITCH_TYPE.equals(statementDTO.getStatementType())) {
			if (statementDTO.getPeriod().startsWith("Q1", 4)) {
				cal.set(Calendar.MONTH, Calendar.JANUARY);
			}
			if (statementDTO.getPeriod().startsWith("Q2", 4)) {
				cal.set(Calendar.MONTH, Calendar.APRIL);
			}
			if (statementDTO.getPeriod().startsWith("Q3", 4)) {
				cal.set(Calendar.MONTH, Calendar.JULY);
			}
			if (statementDTO.getPeriod().startsWith("Q4", 4)) {
				cal.set(Calendar.MONTH, Calendar.OCTOBER);
			}
		}
		if (ENTRY_TYPE.equals(statementDTO.getStatementType())) {
			int mounth = Integer.valueOf(statementDTO.getPeriod().substring(4, 6));
			cal.set(Calendar.MONTH, mounth - 1);
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	private Date getCommissionEndDate(StatementDTO statementDTO) {
		Calendar cal = Calendar.getInstance();
		int year = Integer.valueOf(statementDTO.getPeriod().substring(0, 4));
		cal.set(Calendar.YEAR, year);
		if (ADM_TYPE.equals(statementDTO.getStatementType()) || OPCVM_TYPE.equals(statementDTO.getStatementType()) || SURR_TYPE.equals(statementDTO.getStatementType())
				|| SWITCH_TYPE.equals(statementDTO.getStatementType())) {
			if (statementDTO.getPeriod().startsWith("Q1", 4)) {
				cal.set(Calendar.MONTH, Calendar.MARCH);
			}
			if (statementDTO.getPeriod().startsWith("Q2", 4)) {
				cal.set(Calendar.MONTH, Calendar.JUNE);
			}
			if (statementDTO.getPeriod().startsWith("Q3", 4)) {
				cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
			}
			if (statementDTO.getPeriod().startsWith("Q4", 4)) {
				cal.set(Calendar.MONTH, Calendar.DECEMBER);
			}
		}
		if (ENTRY_TYPE.equals(statementDTO.getStatementType())) {
			int mounth = Integer.valueOf(statementDTO.getPeriod().substring(4, 6));
			cal.set(Calendar.MONTH, mounth - 1);
		}

		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	private Commissions createCommissions(AgentDTO agent, StatementDTO statementDTO) {
		Commissions commissions = new Commissions();

		commissions.setBeginDate(getCommissionStartDate(statementDTO));
		commissions.setEndDate(getCommissionEndDate(statementDTO));
		commissions.setPartner(agent.getName());
		commissions.setPartnerNumber(agent.getAgtId());
		return commissions;
	}

	private List<Payments> createPayments(Collection<TransferDTO> transferDTOs) {
		List<Payments> paymentsList = new ArrayList<Payments>();

		Map<String, List<TransferDTO>> transferGroupByType = transferDTOs.stream().collect(Collectors.groupingBy(TransferDTO::getTransferType));

		groupByForTypeADM(transferGroupByType);

		for (Map.Entry<String, List<TransferDTO>> entry : transferGroupByType.entrySet()) {
			String type = entry.getKey();
			List<TransferDTO> transferByType = entry.getValue();
			Payments payments = new Payments();
			payments.setOperationCode(type);
			List<Payment> paymentList = new ArrayList<Payment>();
			for (TransferDTO transferDTO : transferByType) {
				Payment payment = new Payment();
				AmountType amountType = new AmountType();
				amountType.setCurrencyCode(transferDTO.getTrfCurrency());
				amountType.setValue(transferDTO.getTrfMt());
				payment.setAmount(amountType);
				payment.setIban(transferDTO.getIbanBenef() != null ? transferDTO.getIbanBenef().trim() : null);
				paymentList.add(payment);
			}
			payments.setPayments(paymentList);
			paymentsList.add(payments);
		}
		return paymentsList;
	}

	private CommissionsSet createCommissionsSet(TransferDTO transferDTO, String subBroker) {
		CommissionsSet commissionsSet = new CommissionsSet();

		switch (transferDTO.getTransferType()) {
		case "ADM":
			commissionsSet.setCommissionType(CommissionType.ENCOURS);
			break;
		case "SURR":
			commissionsSet.setCommissionType(CommissionType.ENCOURS);
			break;
		case "SWITCH":
			commissionsSet.setCommissionType(CommissionType.ENCOURS);
			break;
		case "ENTRY":
			commissionsSet.setCommissionType(CommissionType.ENTRY);
			break;
		case "OPCVM":
			commissionsSet.setCommissionType(CommissionType.RETRO);
			break;
		default:
			break;
		}

		commissionsSet.setCurrency(transferDTO.getTrfCurrency());
		if (!"null".equals(subBroker)) {
			commissionsSet.setSubPartner(getSubPartnerNameOrEmpty(subBroker));
		}

		return commissionsSet;
	}

	private CommissionLine createCommissionLine(CommissionToPayDTO commissionToPayDTO) {
		CommissionLine commissionLine = new CommissionLine();

		AmountType comAmount = new AmountType();
		comAmount.setCurrencyCode(commissionToPayDTO.getComCurrency());
		comAmount.setValue(commissionToPayDTO.getComAmount());

		commissionLine.setCommissionAmount(comAmount);
		commissionLine.setContractNumber(commissionToPayDTO.getPolicyId());

		commissionLine.setIsin(commissionToPayDTO.getCodeIsin());

		AmountType operationAmount = new AmountType();
		operationAmount.setCurrencyCode(commissionToPayDTO.getComCurrency());
		operationAmount.setValue(commissionToPayDTO.getComBase());
		commissionLine.setOperationAmount(operationAmount);
		commissionLine.setOperationCode(commissionToPayDTO.getComType());
		commissionLine.setOperationLabel(commissionToPayDTO.getComType());
		commissionLine.setPercentage(commissionToPayDTO.getComRate());
		commissionLine.setValueDate(commissionToPayDTO.getComDate());

		return commissionLine;
	}

	private String saveXmlDocuments(Documents mandate, String path) {

		try {
			JAXBContext jc = JAXBContext.newInstance("lu.wealins.editing.common.webia");
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			File file = new File(path);
			marshaller.marshal(mandate, file);

			return file.getAbsolutePath();

		} catch (JAXBException e) {
			throw new IllegalStateException("Cannot save " + path + ".", e);
		}

	}

	private EditingRequest updateEditingRequest(EditingRequest editingRequest) {

		UpdateEditingRequestRequest updateEditingRequestRequest = new UpdateEditingRequestRequest();
		updateEditingRequestRequest.setRequest(editingRequest);
		UpdateEditingRequestResponse updateEditingRequestResponse = restClientUtils.post(UPDATE_EDITING_REQUEST, updateEditingRequestRequest, UpdateEditingRequestResponse.class);
		return updateEditingRequestResponse.getRequest();
	}

	private Document generateDocument(Header header) {
		Document document = new Document();
		document.setDocumentType(header.getDocumentType());
		document.setHeader(header);
		return document;
	}

	private Header generateHeader(EditingUser creationUser, DocumentType documentType, String language, String fileNetDocumentId) {
		Header header = new Header();
		header.setDocumentType(documentType.name());
		header.setGenerationDate(new Date());
		header.setLanguage(StringUtils.isNotBlank(language) ? language : "EN");
		header.setRegion(StringUtils.isNotBlank(language) ? language : "EN");

		// /!\ We have to remove from the fileNet id the characters { and } because the bare code interpreter doesn't support these characters
		// => If on the future this bare code is used to retrieve the document on filenet, we will find another bare code interpreter
		header.setUniqueDocumentID(fileNetDocumentId != null ? fileNetDocumentId.replaceAll("[{,}]", "") : "TST");
		header.setUser(userMapper.asUser(creationUser));
		return header;
	}

	private String getLanguage(AgentDTO agentDTO) {
		AgentContactLiteDTO agentContactCommission = getAgentContact(agentDTO);
		String res = null;

		// the language is the transcoded document language of the agent's contact with the function COMMISSI
		if (agentContactCommission != null) {
			if (agentContactCommission.getContact() != null && agentContactCommission.getContact().getDocumentationLanguage() != null) {
				res = documentGenerationService.getTransco(TranscoType.DOCUMENT_LANGUAGE, agentContactCommission.getContact().getDocumentationLanguage().toString());
			}
			if (!StringUtils.isEmpty(res)) {
				return res;
			}

		}

		// if not defined, it's the transcoded documentation language of the agent
		if (agentDTO.getDocumentationLanguage() != null) {
			res = documentGenerationService.getTransco(TranscoType.DOCUMENT_LANGUAGE, agentDTO.getDocumentationLanguage().toString());
			if (!StringUtils.isEmpty(res)) {
				return res;
			}
		}

		// By default it's english
		return "EN";
	}

	private String getMailAdresse(AgentDTO agentDTO) {
		AgentContactLiteDTO agent = getAgentContact(agentDTO);
		if (agent != null) {
			if (agent.getContact() != null) {
				return agent.getContact().getEmail();
			}
		}

		if (agentDTO.getDocumentationLanguage() != null) {
			if (agentDTO.getEmail() != null) {
				return agentDTO.getEmail();
			}
		}

		return null;
	}

	private AgentContactLiteDTO getAgentContact(AgentDTO agent) {
		for (AgentContactLiteDTO agentContact : agent.getAgentContacts()) {
			if (COMMISSI.equals(agentContact.getContactFunction())) {
				if (agentContact.getStatus() != null && ACTIVE_1.equals(agentContact.getStatus().trim())) {
					return agentContact;
				}
			}
		}
		return null;
	}	

}
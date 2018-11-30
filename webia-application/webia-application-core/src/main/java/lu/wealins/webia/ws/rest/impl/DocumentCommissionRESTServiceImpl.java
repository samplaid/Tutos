package lu.wealins.webia.ws.rest.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentDataForTransfersResponse;
import lu.wealins.common.dto.webia.services.AgentIdListResponse;
import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;
import lu.wealins.common.dto.webia.services.BatchUploadRequest;
import lu.wealins.common.dto.webia.services.BatchUploadResponse;
import lu.wealins.common.dto.liability.services.BrokerProcessDTO;
import lu.wealins.common.dto.webia.services.StatementDTO;
import lu.wealins.common.dto.webia.services.StatementRequest;
import lu.wealins.common.dto.webia.services.StatementResponse;
import lu.wealins.common.dto.webia.services.StatementUpdateStatusRequest;
import lu.wealins.common.dto.webia.services.StatementWrapperDTO;
import lu.wealins.common.dto.webia.services.enums.StatementStatus;
import lu.wealins.editing.common.webia.CommissionType;
import lu.wealins.editing.common.webia.Commissions;
import lu.wealins.editing.common.webia.Documents;
import lu.wealins.webia.core.mapper.StatementDTOMapper;
import lu.wealins.webia.core.service.DocumentGenerationCommissionsService;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.WebiaCommissionsService;
import lu.wealins.webia.core.service.helper.FileHelper;
import lu.wealins.webia.core.service.impl.LiabilityCommissionService;
import lu.wealins.webia.core.utils.FilesUtils;
import lu.wealins.webia.ws.rest.DocumentCommissionRESTService;
import lu.wealins.webia.ws.rest.request.SaveDocumentIntoFileNetRequest;

@Component
public class DocumentCommissionRESTServiceImpl implements DocumentCommissionRESTService {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentCommissionRESTServiceImpl.class);
	
	private static final int MAX_FIELD_499 = 499;

	@Autowired
	private DocumentGenerationCommissionsService documentGenerationCommissionsService;

	@Autowired
	private FileHelper fileHelper;

	@Autowired
	private LiabilityAgentService liabilityAgentService;
	
	@Autowired
	private WebiaCommissionsService webiaCommissionsService;

	@Autowired
	private LiabilityCommissionService liabilityCommissionService;
	
	@Autowired
	private WebiaApplicationParameterService webiaApplicationParameterService;
	
	@Autowired
	private EditingService editingService;

	@Autowired
	private StatementDTOMapper statementDTOMapper;
	
	@Override
	public Response batchUploadCommissionPDF(SecurityContext context, BatchUploadRequest request) {

		if (request == null ||
				StringUtils.isBlank(request.getSourcePath()) ||
				StringUtils.isBlank(request.getTargetPath())) {
			return Response.noContent().build();
		}


		// Read files in outputPath directory
		Collection<File> sourceFiles = FilesUtils.listFiles(fileHelper.toFile(request.getSourcePath()), request.getFilenamePattern(), request.getDirPattern());

		// For each file in source path, check the corresponding XML file in target path
		// and create an file upload request.
		List<SaveDocumentIntoFileNetRequest> uploadRequests = createUploadDocumentRequest(sourceFiles, request);

		List<SaveDocumentIntoFileNetRequest> filteredRequests = uploadRequests.stream().filter(uploadRequest -> StringUtils.isNotBlank(uploadRequest.getAgentId()))
				.collect(Collectors.toList());

		BatchUploadResponse results = new BatchUploadResponse();

		if (!filteredRequests.isEmpty()) {
			results = documentGenerationCommissionsService.batchUploadDocument(filteredRequests);
		}

		// Handle errors
		List<SaveDocumentIntoFileNetRequest> errorRequests = uploadRequests.stream().filter(uploadRequest -> StringUtils.isBlank(uploadRequest.getAgentId()))
				.collect(Collectors.toList());

		List<String> failure = new ArrayList<String>();
		errorRequests.forEach(errorRequest -> {
			failure.add("The document " + errorRequest.getDocumentTitle() + " was in failure.");
		});

		results.getFailure().addAll(failure);
		return  Response.ok().entity(results).build();
	}


	/**
	 * For each file in source files, check the corresponding {@code XML} file and create the upload document request.
	 * 
	 * @param sourceFiles the source files
	 * @return a set of requests object
	 */
	private List<SaveDocumentIntoFileNetRequest> createUploadDocumentRequest(Collection<File> sourceFiles, BatchUploadRequest request) {
		Assert.notNull(sourceFiles);
		List<SaveDocumentIntoFileNetRequest> requests = new ArrayList<>();

		for (File sourceFile : sourceFiles) {
			String baseName = FilenameUtils.getBaseName(sourceFile.getName());

			// Find xml file
			File xmlFile = FilesUtils.getFile(baseName + ".xml", request.getTargetPath());

			// read the XML,
			Documents documents = null;
			try {
				if (xmlFile != null && xmlFile.exists()) {
					documents = unmarshal(xmlFile);
				}
			} catch (JAXBException e) {
				LOG.error("The documents xml object is null related to file {}. FullTrace: {}", xmlFile.getName(), e.getMessage());
			}

			// create request based on the documents
			SaveDocumentIntoFileNetRequest uploadRequest = createUploadRequest(documents, sourceFile.getName(), sourceFile.getAbsolutePath(), request.getContentType(), request.getDocumentClassId());
			requests.add(uploadRequest);
		}

		return requests;
	}



	/**
	 * @param documents
	 * @return
	 */
	private SaveDocumentIntoFileNetRequest createUploadRequest(Documents documents, String fileName, String sourcePath, String contentType, String documentClassId) {
		Assert.notNull(StringUtils.stripToNull(contentType), "The document content type must not be null");
		Assert.notNull(StringUtils.stripToNull(sourcePath), "The documents source path must not be null");
		Assert.notNull(StringUtils.stripToNull(fileName), "The document title must not be null");
		Assert.notNull(StringUtils.stripToNull(documentClassId), "The document class id must not be null");

		SaveDocumentIntoFileNetRequest uploadRequest = new SaveDocumentIntoFileNetRequest();
		uploadRequest.setContentType(contentType);
		uploadRequest.setDocumentClass(documentClassId);
		uploadRequest.setDocumentPath(sourcePath);
		uploadRequest.setDocumentStatus("Active");
		uploadRequest.setDocumentTitle(fileName);

		if (documents != null) {
			if (!documents.getDocuments().isEmpty()) {
				Commissions commissions = documents.getDocuments().get(0).getCommissions();
				if (commissions != null) {
					AgentDTO agent = liabilityAgentService.getAgent(commissions.getPartnerNumber());
					if (agent != null) {
						if (agent.getCrmRefererence() != null) {
							uploadRequest.setAgentId(agent.getCrmRefererence());
							uploadRequest.setEffectiveDate(commissions.getBeginDate());
							if (!commissions.getCommissionsSets().isEmpty()) {
								String commissionType = toRightCommissionType(commissions.getCommissionsSets().get(0).getCommissionType());
								uploadRequest.setCommissionType(commissionType);
							} else {
								LOG.warn("The commissions sets in documents xml object is empty. Thus, the commission type is empty.");
							}
						} else {
							LOG.warn("The agent with id " + commissions.getPartnerNumber() + " does not have CRM id.");
						}
					} else {
						LOG.warn("The agent with id " + commissions.getPartnerNumber() + " does not exist.");
					}
				} else {
					LOG.warn("The commissions in documents xml object is null. Thus, the agentId (PartnerNumber) is empty.");
				}
			} else {
				LOG.warn("The documents in documents xml object is empty. Thus, the agentId (PartnerNumber) is empty.");
			}
		} else {
			LOG.warn("The documents xml object is null. Thus, the agentId (PartnerNumber) is empty.");
		}

		return uploadRequest;
	}

	/**
	 * @param commissionType
	 * @return
	 */
	private String toRightCommissionType(CommissionType commissionType) {
		String result = "UNKNOWN";
		switch (commissionType) {
		case ENCOURS:
			result = "PORTFOLIO";
			break;
		case RETRO:
			result = "PORTFOLIO";
			break;
		case ENTRY:
			result = "ENTRY";
			break;
		default:
			break;
		}
		return result;
	}


	/**
	 * @param baseName
	 * @param targetPath
	 * @return
	 * @throws JAXBException
	 */
	private Documents unmarshal(File file) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Documents.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (Documents) jaxbUnmarshaller.unmarshal(file);
	}

	

	@Override
	public StatementResponse generateStatementCom(SecurityContext context, StatementRequest statementRequest) throws Exception {
		// Statement Id and Broker are optional in the request
		StatementResponse response = new StatementResponse();

		if (statementRequest == null ||  
				StringUtils.isBlank(statementRequest.getPeriod()) ||
				StringUtils.isBlank(statementRequest.getType())) {
			response.setError("Bad request");
			return response;
		}

		StatementDTO statement = null;
		Long statementId = statementRequest.getStatementId();
		
		if (Objects.isNull(statementId)) {
			statement = createStatementAndProcess(statementRequest);

			if (Objects.nonNull(statement)) {
				statementId = statement.getStatementId();
			}
		}
		
		// try generate document
		if (Objects.nonNull(statementId)) {
			AgentIdListResponse agentsList = webiaCommissionsService.getDistinctTransfersBrokerByStatementId(statementId);

			for (String agentId : agentsList.getAgents()) {
				LOG.info("Create editing request for statement " + statementId.toString() + " and transfer " + agentId);
				editingService.createCommissionRequest(statementId.toString(), agentId);
			}

			// Create request for commission payment slip generation
			editingService.createCommissionPaymentSlipRequest(statementId.toString());
		}

		if (Objects.isNull(statement) && Objects.nonNull(statementId)) {
			statement = webiaCommissionsService.getStatement(statementId.toString());
		}

		response.setStatement(statement);
		return  response;
	}

	
	/**
	 * Create statement and process commissions
	 * 
	 * @param statement
	 * @return
	 * @throws Exception
	 */
	private StatementDTO createStatementAndProcess(StatementRequest statementRequest) throws Exception {
		StatementDTO statement = null;
		try {
			// Create statement
			StatementResponse createStatement = webiaCommissionsService.createStatement(statementRequest);
			if (createStatement == null || createStatement.getStatement() == null){
				throw new Exception("Error during creation of a new entry in table WEBIA.STATEMENT");
			}
			statement = createStatement.getStatement();
			StatementWrapperDTO statementPushDTO = statementDTOMapper.asStatementWrapperDTO(statement);
			
			// Receipt broker with match parameters
			List<BrokerProcessDTO> availableBroker = webiaCommissionsService.getAvailableBrokerCommission(statementPushDTO);
			LOG.info("Process for " + availableBroker.size() + " broker");
			// Get param Wealins BROKER
			LOG.info("Get param Wealins BROKER");
			ApplicationParameterDTO wealinsBroker = webiaApplicationParameterService.getApplicationParameter("WEALINS_BROKER_ID");
			
			// Receipt broker information that desired report (AGENTS.DESIRED_REPORT=1)
			AgentDataForTransfersResponse dataAvailableBrokerResponse = liabilityCommissionService.getInfoAvailableBrokerCommission(availableBroker, wealinsBroker);
			LOG.info("Process for " + dataAvailableBrokerResponse.getAgents().size() + " broker desire report");
			
			dataAvailableBrokerResponse.setStatementWrapperDTO(statementPushDTO);
			
			// Send broker information list and process transfer
			webiaCommissionsService.processCommissionForCreateTransers(dataAvailableBrokerResponse);
			
			// Update statement status
			StatementUpdateStatusRequest statementUpdateStatusRequest = new StatementUpdateStatusRequest();
			statementUpdateStatusRequest.setId(statement.getStatementId());
			statementUpdateStatusRequest.setStatus(StatementStatus.REQUEST.name());
			webiaCommissionsService.updateStatementStatus(statementUpdateStatusRequest);
			
			LOG.info("Successfully GenerateStatementComJob");
		} catch (Exception e) {
			LOG.error(e.getMessage());

			if (statement != null) {
				try {
					// Update statement status when error has been throw 
					StatementUpdateStatusRequest statementUpdateStatusRequest = new StatementUpdateStatusRequest();
					statementUpdateStatusRequest.setId(statement.getStatementId());
					statementUpdateStatusRequest.setStatus(StatementStatus.FAILED.name());
					if (e.getMessage() != null) {
						statementUpdateStatusRequest.setMessage(e.getMessage().length() <= MAX_FIELD_499 ? e.getMessage() : e.getMessage().substring(0, MAX_FIELD_499));
					}					
					webiaCommissionsService.updateStatementStatus(statementUpdateStatusRequest);
				} catch (Exception e1) {
					LOG.error("Error during update statement status ", e1);
				}
			}			
		}
		return webiaCommissionsService.getStatement(statement.getStatementId().toString());
	}

}

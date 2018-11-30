/**
* 
*/
package lu.wealins.webia.core.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
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
import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.webia.services.AcceptanceReportDTO;
import lu.wealins.common.dto.webia.services.CodeLabelDTO;
import lu.wealins.common.dto.webia.services.EditingRequestStatus;
import lu.wealins.common.dto.webia.services.enums.CheckType;
import lu.wealins.common.security.token.SecurityContextThreadLocal;
import lu.wealins.editing.common.webia.AcceptanceReport;
import lu.wealins.editing.common.webia.AcceptanceReport.Group;
import lu.wealins.editing.common.webia.AcceptanceReport.Group.Item;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Documents;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.webia.core.service.AcceptanceReportGeneratorService;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.utils.Constantes;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.EditingUser;

/**
 * This implements the interface {@link AcceptanceReportGeneratorRESTService} to realize the acceptance report.
 * 
 * @author oro
 *
 */
@Service
@Transactional
public class AcceptanceReportGeneratorServiceImpl implements AcceptanceReportGeneratorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AcceptanceReportGeneratorServiceImpl.class);

	private static final String SCORE_DESCRIPTION = "score";
	private static final String WHITE_SPACE = " ";

	private static final String WEBIA_ACCEPTANCE_REPORT_RS = "webia/acceptanceReport";
	private static final String WEBIA_WORKFLOW_ITEM_ID_RS = "webia/workflow";
	private static final String WEBIA_CODE_LABEL_RS = "webia/codeLabel";

	/**
	 * Env. variable
	 */
	@Value("${editique.xml.path}")
	private String filePath;

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private DocumentService documentGenerationService;

	@Autowired
	private LiabilityWorkflowService workflowService;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public EditingRequest generateDocumentAcceptanceReport(SecurityContext context, EditingRequest editingRequest) {

		try {
			Assert.notNull(editingRequest, "The edition request can not be null");

			editingRequest.setStatus(EditingRequestStatus.IN_PROGRESS);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

			EditingUser creationUser = editingRequest.getCreationUser();

			Assert.notNull(creationUser, "The creation user of the document generation request cannot be null.");
			Assert.notNull(StringUtils.trimToNull(editingRequest.getPolicy()), "The policy id cannot be null.");
			Assert.notNull(StringUtils.trimToNull(editingRequest.getProduct()), "The product cannot be null.");
			Assert.notNull(editingRequest.getWorkflowItemId(), "The workflow item id cannot be null.");

			LocalDateTime date = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constantes.YYYYMMDDHHMMSSSSS);
			String today = date.format(formatter);
			String documentPath = createDocumentPath(editingRequest.getPolicy(), editingRequest.getProduct(), today);
			String saveResponse = documentGenerationService.saveXmlDocuments(createDocument(editingRequest), documentPath);

			editingRequest.setInputStreamPath(saveResponse);
			editingRequest.setStatus(EditingRequestStatus.XML_GENERATED);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

		} catch (Exception e) {
			editingRequest.setStatus(EditingRequestStatus.IN_ERROR);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

			LOGGER.error("Error occurred during the acceptation document report generation for product " + editingRequest.getProduct() + " and policy " + editingRequest.getPolicy(), e);
		}

		return editingRequest;
	}

	/**
	 * @param editingRequest
	 * @return
	 */
	private Documents createDocument(EditingRequest editingRequest) {
		Documents documentParentNode = new Documents();
		List<Document> documents = new ArrayList<>();
		LOGGER.info("Creating acceptance document report related to the product " + editingRequest.getProduct() + " and policy " + editingRequest.getPolicy());

		Document document = new Document();
		document.setDocumentType(DocumentType.ACCEPTANCE_REPORT.val());
		document.setHeader(createHeader(editingRequest));
		document.setAcceptanceReport(createAcceptanceNode(editingRequest));
		documents.add(document);
		documentParentNode.setDocuments(documents);
		return documentParentNode;
	}

	/**
	 * Create the acceptance report header
	 * 
	 * @return
	 */
	private Header createHeader(EditingRequest editingRequest) {
		return documentGenerationService.generateHeader(editingRequest.getCreationUser(), DocumentType.ACCEPTANCE_REPORT, "FR", null, UUID.randomUUID());
	}

	/**
	 * @param step
	 * @param mappedItem
	 * @return
	 */
	private AcceptanceReport createAcceptanceNode(EditingRequest editingRequest) {
		Long workFlowItemId = editingRequest.getWorkflowItemId();
		AcceptanceReport acceptanceReport = new AcceptanceReport();
		acceptanceReport.setPolicyId(editingRequest.getPolicy());
		acceptanceReport.setOperationId(workFlowItemId.toString());


		WorkflowGeneralInformationDTO workflowInfo = getWorkflowInfo(workFlowItemId);

		if (workflowInfo == null) {
			throw new IllegalStateException("Workflow information linked to the id " + workFlowItemId + " is not found.");
		}

		acceptanceReport.setOperation(workflowInfo.getActionOther());
		acceptanceReport.setStep(workflowInfo.getCurrentStepName());

		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams = new MultivaluedHashMap<>();
		queryParams.add("workflowId", workFlowItemId);
		List<AcceptanceReportDTO> acceptanceReportsDto = restClientUtils.get(WEBIA_ACCEPTANCE_REPORT_RS, "/find", queryParams, new GenericType<List<AcceptanceReportDTO>>() {
		});

		Map<String, List<AcceptanceReportDTO>> acceptanceByLabel = acceptanceReportsDto.stream()
				.map(item -> {

					// Set an empty label for all item whose label is null or empty to avoid null pointer and to have items in the map
					if (StringUtils.isBlank(item.getLabelDesciption())) {
						item.setLabelDesciption(WHITE_SPACE);
					}
					return item;
				})
				.collect(Collectors.groupingBy(AcceptanceReportDTO::getLabelDesciption));

		acceptanceByLabel.forEach((label, acceptanceReports) -> {
			Group group = new Group();

			if (WHITE_SPACE.equals(label)) {
				group.setLabel(StringUtils.EMPTY);
			} else {
				group.setLabel(label);
			}

			acceptanceReports.stream().forEach(acceptanceItem -> {
				Item item = new Item();
				item.setLabel(acceptanceItem.getCheckDesciption());

				item.setValue(selectItemValue(acceptanceItem));
				item.setValue2(getOtherInformations(acceptanceItem));
				group.getItems().add(item);
			});

			acceptanceReport.getGroups().add(group);
		});

		return acceptanceReport;
	}

	/**
	 * Append the other informations in the report data and return it as one text.
	 * 
	 * @param acceptanceItem the acceptance report
	 * @return the informations
	 */
	private String getOtherInformations(AcceptanceReportDTO acceptanceItem) {
		StringBuilder infoBuilder = new StringBuilder();

		if (StringUtils.isNotBlank(acceptanceItem.getScore())) {
			infoBuilder.append(acceptanceItem.getScore());
			infoBuilder.append(StringUtils.SPACE);
		}

		return infoBuilder.toString();
	}

	/**
	 * Retrieve the current step name of the workflow
	 * 
	 * @param workFlowItemId the workflow id
	 * @return Empty if not found
	 */
	private WorkflowGeneralInformationDTO getWorkflowInfo(Long workFlowItemId) {
		String usrId = SecurityContextThreadLocal.get().getKeycloakSecurityContext().getToken().getPreferredUsername();
		return workflowService.getWorkflowGeneralInformation(workFlowItemId + "", usrId);
	}

	/**
	 * Select the value to be used from the acceptance report object
	 * 
	 * @param acceptanceReport the acceptance report
	 * @return Empty if all values are empty or null in the acceptance object
	 */
	private String selectItemValue(AcceptanceReportDTO acceptanceReport) {
		String value = StringUtils.EMPTY;

		// Complete this list if data type: amount and date. Still now data type like text, no-yes/na and number is taken into account.
		if (CheckType.YES_NO_NA.getType().equalsIgnoreCase(acceptanceReport.getCheckType())
				|| CheckType.YES_NO.getType().equalsIgnoreCase(acceptanceReport.getCheckType())) {
			value = acceptanceReport.getDataValueYesNoNa();
		} else if (CheckType.NUMBER.getType().equalsIgnoreCase(acceptanceReport.getCheckType())) {

			if (acceptanceReport.getDataValueNumber() != null) {

				if (SCORE_DESCRIPTION.equalsIgnoreCase(acceptanceReport.getCheckDesciption())
						&& SCORE_DESCRIPTION.equalsIgnoreCase(acceptanceReport.getLabelDesciption())) {
					value = String.valueOf(acceptanceReport.getDataValueNumber().intValue());
				} else {
					value = acceptanceReport.getDataValueNumber().toString();
				}
			}

		} else if (CheckType.TEXT.getType().equalsIgnoreCase(acceptanceReport.getCheckType())) {
			value = acceptanceReport.getDataValueText();
		} else if (CheckType.LIST.getType().equalsIgnoreCase(acceptanceReport.getCheckType())) {

			Collection<CodeLabelDTO> codeLabelDtos = restClientUtils.get(WEBIA_CODE_LABEL_RS, "/" + acceptanceReport.getCheckCode(), new MultivaluedHashMap<>(), new GenericType<List<CodeLabelDTO>>() {
			});

			CodeLabelDTO codeLabel = codeLabelDtos.stream()
					.filter(item -> StringUtils.isNotBlank(item.getCode()) && item.getCode().trim().equalsIgnoreCase(StringUtils.trimToNull(acceptanceReport.getDataValueText())))
					.findFirst().orElse(null);

			if (codeLabel != null) {
				value = codeLabel.getLabel();
			} else {
				value = acceptanceReport.getDataValueText();
			}
		}

		// Append comment if exists
		if (StringUtils.isNotBlank(acceptanceReport.getCommentIf())) {

			if (StringUtils.isNotBlank(value)) {
				value += StringUtils.SPACE;
			}

			value += acceptanceReport.getCommentIf();
		}

		return value;
	}

	private String createDocumentPath(String policy, String product, String today) {
		StringBuilder pathBuilder = new StringBuilder();
		pathBuilder.append(filePath);
		pathBuilder.append("/");
		pathBuilder.append(product);
		pathBuilder.append("_");
		pathBuilder.append(policy);
		pathBuilder.append("_");
		pathBuilder.append(today);
		pathBuilder.append(".xml");
		return pathBuilder.toString();
	}

}

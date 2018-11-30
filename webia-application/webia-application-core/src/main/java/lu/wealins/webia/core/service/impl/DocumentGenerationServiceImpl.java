package lu.wealins.webia.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.webia.services.SepaDocumentRequest;
import lu.wealins.webia.core.service.AcceptanceReportGeneratorService;
import lu.wealins.webia.core.service.CommissionStatementFilenetService;
import lu.wealins.webia.core.service.DocumentGenerationCommissionsService;
import lu.wealins.webia.core.service.DocumentGenerationService;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.FaxPaymentGenerationService;
import lu.wealins.webia.core.service.MandatGestionDocumentGenerationService;
import lu.wealins.webia.core.service.SepaPaymentGenerationService;
import lu.wealins.webia.core.service.SwiftPaymentGenerationService;
import lu.wealins.webia.core.service.WebiaCommissionPaymentSlipGeneratorService;
import lu.wealins.webia.core.service.document.AdditionalPremiumDocumentGenerationService;
import lu.wealins.webia.core.service.document.EnoughCashDocumentService;
import lu.wealins.webia.core.service.document.FaxSecuritiesTransferDocumentGenerationService;
import lu.wealins.webia.core.service.document.FaxSurrenderDepositBankDocumentGenerationService;
import lu.wealins.webia.core.service.document.PolicyBenefChangeService;
import lu.wealins.webia.core.service.document.PolicyBrokerChangeService;
import lu.wealins.webia.core.service.document.PolicyScheduleDocumentGenerationService;
import lu.wealins.webia.core.service.document.TotalSurrenderDocumentGenerationService;
import lu.wealins.webia.core.service.document.WithdrawalAccountingNoteDocumentGenerationService;
import lu.wealins.webia.core.service.document.WithdrawalDocumentGenerationService;
import lu.wealins.webia.core.service.helper.FileHelper;
import lu.wealins.webia.core.service.helper.SEPAHelper;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.SaveDocumentIntoFileNetRequest;
import lu.wealins.webia.ws.rest.request.SaveDocumentIntoFileNetResponse;

@Service
public class DocumentGenerationServiceImpl implements DocumentGenerationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentGenerationCommissionsServiceImpl.class);

	@Value("${editique.xml.path}")
	private String filePath;
	
	@Autowired
	private PolicyScheduleDocumentGenerationService policyScheduleDocumentGenerationService;

	@Autowired
	private PolicyBenefChangeService policyBenefChangeService;

	@Autowired
	private MandatGestionDocumentGenerationService mandatGestionDocumentGenerationService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private DocumentGenerationCommissionsService documentGenerationCommissionsService;

	@Autowired
	private AcceptanceReportGeneratorService acceptanceReportGeneratorService;

	@Autowired
	@Qualifier("SurrrenderDaliDocumentGenerationService")
	SurrrenderDaliDocumentGenerationServiceImpl surrenderDaliDocumentService;

	@Autowired
	@Qualifier("SurrrenderLissiaDocumentGenerationService")
	SurrrenderLissiaDocumentGenerationServiceImpl surrenderLissiaDocumentService;

	@Autowired
	private CommissionStatementFilenetService commissionStatementFilenetService;
	
	@Autowired
	private FileHelper fileHelper;
	
	@Autowired
	private SEPAHelper sepaHelper;

	@Autowired
	private AdditionalPremiumDocumentGenerationService additionalPremiumDocumentGenerationService;

	@Autowired
	private PolicyBrokerChangeService policyBrokerChangeService;

	@Autowired
	private WebiaCommissionPaymentSlipGeneratorService paymentSlipGeneratorService;
	
	@Autowired
	private WithdrawalDocumentGenerationService withdrawalDocumentGenerationService;

	@Autowired
	private WithdrawalAccountingNoteDocumentGenerationService withdrawalAccountingNoteDocumentGenerationService;

	@Autowired
	private FaxSecuritiesTransferDocumentGenerationService faxSecuritiesTransferDocumentGenerationService;
	
	@Autowired
	private FaxPaymentGenerationService faxPaymentGenerationService;
	
	@Autowired
	private SepaPaymentGenerationService sepaPaymentGenerationService;
	
	@Autowired
	private SwiftPaymentGenerationService swiftPaymentGenerationService;
	
	@Autowired
	private EnoughCashDocumentService enoughCashDocumentService;

	@Autowired
	private TotalSurrenderDocumentGenerationService totalSurrenderDocumentGenerationService;

	@Autowired
	private FaxSurrenderDepositBankDocumentGenerationService faxSurrenderDepositBankDocumentGenerationService;

	@Override
	public EditingRequest generateManagementMandate(SecurityContext context, EditingRequest request) {
		return mandatGestionDocumentGenerationService.generateDocumentMandatGestion(context, request);
	}

	@Override
	public EditingRequest generateDocument(SecurityContext context, EditingRequest initialRequest) {
		EditingRequest updatedRequest = null;
		DocumentType type = initialRequest.getDocumentType();
		if (Objects.nonNull(type)) {
			boolean needToCallScriptura = true;
			switch (type) {
			case MANAGEMENT_MANDATE:
				updatedRequest = mandatGestionDocumentGenerationService.generateDocumentMandatGestion(context,
						initialRequest);
				break;
			case MANAGEMENT_MANDATE_END:
				updatedRequest = mandatGestionDocumentGenerationService.generateDocumentMandatGestionEnd(context,
						initialRequest);
				break;
			case POLICY_SCHEDULE:
				updatedRequest = policyScheduleDocumentGenerationService.generateDocumentPolicy(context,
						initialRequest);
				break;
			case COMMISSIONS:
				updatedRequest = documentGenerationCommissionsService.generateDocumentCommissions(context,
						initialRequest);
				break;
			case COMMISSION_PAYMENT_SLIP:
				updatedRequest = paymentSlipGeneratorService.generatePaymentSlip(initialRequest);

				if (StringUtils.isNotBlank(updatedRequest.getTransferIds())) {
					// SEPA generation using the datas of commission payment slip
					SepaDocumentRequest sepaRequest = new SepaDocumentRequest();
					List<Long> ids = Stream.of(updatedRequest.getTransferIds().replaceAll(" ", "").split(","))
							.map(id -> Long.parseLong(id)).collect(Collectors.toList());
					sepaRequest.setIds(ids);
					sepaHelper.executeDocuments(context, sepaRequest);
				} else {
					LOGGER.error("Document SEPA cannot be generated. No transafer id list is emtpy.");
				}

				needToCallScriptura = false;
				break;
			case ACCEPTANCE_REPORT:
				updatedRequest = acceptanceReportGeneratorService.generateDocumentAcceptanceReport(context,
						initialRequest);
				break;
			case CHANGE_BENEF:
				updatedRequest = policyBenefChangeService.generateDocumentPolicy(context, initialRequest);
				break;
			case SURRENDER:
				updatedRequest = surrenderLissiaDocumentService.generateDocumentSurrender(context, initialRequest);
				break;
			case ANNEX_FISC:
				updatedRequest = surrenderDaliDocumentService.generateDocumentSurrender(context, initialRequest);
				break;
			case ADD_PREMIUM:
				updatedRequest = additionalPremiumDocumentGenerationService.generateDocumentPolicy(context,
						initialRequest);
				break;
			case CHANGE_BROKER:
				updatedRequest = policyBrokerChangeService.generateDocumentPolicy(context, initialRequest);
				break;
			case WITHDRAWAL:
				updatedRequest = withdrawalDocumentGenerationService.generateDocumentPolicy(context, initialRequest);
				break;
			case WITHDRAWAL_ACCOUNTING_NOTE:
				updatedRequest = withdrawalAccountingNoteDocumentGenerationService.generateDocumentPolicy(context, initialRequest);
				break;
			case FAX_SECURITIES_TRANSFER:
				updatedRequest = faxSecuritiesTransferDocumentGenerationService.generateDocumentPolicy(context, initialRequest);
				break;
			case FAX_PAYMENT:
				updatedRequest = faxPaymentGenerationService.generateFaxPayment(context, initialRequest);
				break;
			case SWIFT_PAYMENT:
				updatedRequest = swiftPaymentGenerationService.generateSwiftPayment(context, initialRequest);
				needToCallScriptura = false;
				break;
			case SEPA_PAYMENT:
				updatedRequest = sepaPaymentGenerationService.generateSepaPayment(context, initialRequest);
				needToCallScriptura = false;
				break;
			case WITHDRAWAL_FOLLOWUP:
				updatedRequest = enoughCashDocumentService.generateDocumentPolicy(context, initialRequest);
				break;
			case TOTAL_SURRENDER:
				updatedRequest = totalSurrenderDocumentGenerationService.generateDocumentPolicy(context, initialRequest);
				break;
			case FAX_SURRENDER_DEPOSIT_BANK:
				updatedRequest = faxSurrenderDepositBankDocumentGenerationService.generateDocumentPolicy(context, initialRequest);
				break;
			default:
				break;
			}
	
			if (needToCallScriptura) {
				updatedRequest = documentService.generateFile(context, updatedRequest).getRequest();
			}
	
			if (Objects.nonNull(updatedRequest) && StringUtils.isNotBlank(updatedRequest.getGedDocumentId())) {
				saveDocumentIntoFileNet(updatedRequest);
			}
		}

		return updatedRequest;
	}

	private SaveDocumentIntoFileNetResponse saveDocumentIntoFileNet(EditingRequest editingRequest) {
		SaveDocumentIntoFileNetRequest request = new SaveDocumentIntoFileNetRequest();
		request.setDocumentClass(editingRequest.getGedClass());
		String path = fileHelper.toFile(editingRequest.getOutputStreamPath()).getPath();
		LOGGER.info("Editing Path: {}", path);
		request.setDocumentPath(path);
		request.setDocumentStatus("Active");
		String fileDate = null;
		if (editingRequest.getEventDate()!=null){
			fileDate = DateUtil.formatDate(editingRequest.getEventDate(), "yyyyMMddHHmmss");
		} else {
			fileDate = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
		}
		request.setDocumentTitle(editingRequest.getStatement() + "_" + editingRequest.getAgent() + "_" + fileDate + ".pdf");
		request.setGedId(editingRequest.getGedDocumentId());
		request.setContentType("application/pdf");
		return commissionStatementFilenetService.saveDocumentIntoFileNet(request);
	}

	@Override
	public EditingRequest uploadDocument(EditingRequest initialRequest) {
		EditingRequest updatedRequest = null;
		DocumentType type = initialRequest.getDocumentType();

		switch (type) {
		case COMMISSIONS:
			updatedRequest = documentGenerationCommissionsService.reserveDocIdAndUpdateRequest(initialRequest);
			break;
		default:
			break;
		}

		if (updatedRequest.getGedDocumentId() != null) {
			saveDocumentIntoFileNet(updatedRequest);
		}

		return updatedRequest;
	}
}

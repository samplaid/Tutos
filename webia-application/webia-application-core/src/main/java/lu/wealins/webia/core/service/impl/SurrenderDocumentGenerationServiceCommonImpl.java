package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.webia.services.EditingRequestStatus;
import lu.wealins.common.dto.webia.services.SurrenderReportResultDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;
import lu.wealins.editing.common.webia.AmountType;
import lu.wealins.editing.common.webia.CorrespondenceAddress;
import lu.wealins.editing.common.webia.CoverLetter;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Documents;
import lu.wealins.editing.common.webia.Endorsement;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.editing.common.webia.MailingAddress;
import lu.wealins.editing.common.webia.PersonLight;
import lu.wealins.editing.common.webia.Policy;
import lu.wealins.editing.common.webia.PolicyHolder;
import lu.wealins.editing.common.webia.SurrenderDetails;
import lu.wealins.editing.common.webia.SurrenderDetails.FiscalDataFrance;
import lu.wealins.editing.common.webia.SurrenderDetails.FiscalDataFrance.CalculationDetails;
import lu.wealins.editing.common.webia.SurrenderDetails.FiscalDataFrance.ContractDuration;
import lu.wealins.editing.common.webia.SurrenderDetails.FiscalDataFrance.PlusValueAmountAfter;
import lu.wealins.editing.common.webia.SurrenderDetails.FiscalDataFrance.PlusValueAmountBefore;
import lu.wealins.webia.core.mapper.PersonMapper;
import lu.wealins.webia.core.mapper.PolicyMapper;
import lu.wealins.webia.core.mapper.TransactionTaxMapper;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.SurrenderDocumentGenerationService;
import lu.wealins.webia.core.service.WebiaTransactionTaxService;
import lu.wealins.webia.core.utils.Constantes;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.EditingUser;
import lu.wealins.webia.ws.rest.request.PolicyOrigin;

@Component
public class SurrenderDocumentGenerationServiceCommonImpl implements SurrenderDocumentGenerationService {

	/**
	 * The logger
	 */
	private static final Logger log = LoggerFactory.getLogger(SurrenderDocumentGenerationServiceCommonImpl.class);

	/**
	 * Services
	 */
	@Autowired
	private LiabilityPolicyService policyService;

	@Autowired
	@Qualifier("SurrenderDocumentService")
	PolicyDocumentService policyDocumentService;

	@Autowired
	private WebiaTransactionTaxService transactionTaxService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private RestClientUtils restClientUtils;

	/**
	 * Mappers
	 */
	@Autowired
	private PersonMapper personMapper;

	@Autowired
	private PolicyMapper policyMapper;

	@Autowired
	private TransactionTaxMapper transactionTaxMapper;

	/**
	 * Constants
	 */
	private static final String DEFAULT_TITLE_ID = "MME_MR";
	private static final String NOT_YET_IMPLEMENTED_FOR_NON_LISSIA_POLICIES = "The generation of the documents for the French tax is not yet implemented on non-Lissia policies";
	private static final Date PRIME_DATE_FOR_PLUS_VALUE = DateUtil.parseYYYYMMDDDate("2017/09/27");

	private static final String RHSSO_USER = "rhsso/users/usernamecontext";

	/**
	 * Env. variable
	 */
	@Value("${editique.xml.path}")
	private String filePath;

	@Override
	public EditingRequest generateDocumentSurrender(SecurityContext context, EditingRequest editingRequest) {

		try {

			log.info("##################################################### THE user is "
					+ context.getUserPrincipal().getName());
			Assert.notNull(editingRequest, "The edition request can not be null");

			/**
			 * Set the request in progress
			 */
			editingRequest.setStatus(EditingRequestStatus.IN_PROGRESS);
			editingRequest = documentService.updateEditingRequest(editingRequest);

			Long transactionTaxId = editingRequest.getTransactionTax();
			EditingUser creationUser = editingRequest.getCreationUser();

			Documents documents = new Documents();
			List<Document> docs = new ArrayList<>();

			String language = "FR";
			TransactionTaxDTO transactionTax = transactionTaxService.getTransactionTax(transactionTaxId);

			String policyId = transactionTax.getPolicy().trim();
			PolicyOrigin origin = transactionTax.getOrigin() != null ? PolicyOrigin.valueOf(transactionTax.getOrigin())
					: null;

			log.info("Generating surrender document for policy : " + policyId);

			Endorsement endorsement = generateEndorsement(transactionTax);

			CoverLetter coverLetter = null;
			List<MailingAddress> mailingAddresses = null;
			coverLetter = generateCoverLetter(transactionTax);
			mailingAddresses = generateMailingAddress(transactionTax);
			updateEndorsementByPolicy(endorsement, transactionTax, editingRequest);
			updateCoverLetterByPolicy(coverLetter, transactionTax);

			creationUser = updateCreationUserByEmail(creationUser, transactionTax);
			Header header = generateHeader(creationUser, language);

			Document document = documentService.generateDocument(header, coverLetter, mailingAddresses);
			document.setEndorsement(endorsement);
			docs.add(document);
			documents.setDocuments(docs);

			LocalDateTime date = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constantes.YYYYMMDDHHMMSSSSS);
			String formatedDate = date.format(formatter);
			policyId = org.apache.commons.lang3.StringUtils.replaceChars(policyId, "/", "_");
			String path = filePath + "/" + policyId.trim() + "_" + formatedDate + ".xml";
			String saveResponse = documentService.saveXmlDocuments(documents, path);

			/**
			 * Update the request
			 */
			editingRequest.setInputStreamPath(saveResponse);
			editingRequest.setStatus(EditingRequestStatus.XML_GENERATED);
			editingRequest = documentService.updateEditingRequest(editingRequest);

		} catch (Exception e) {
			// editingRequest.setStatus(EditingRequestStatus.IN_ERROR);
			// editingRequest =
			// documentGenerationService.updateEditingRequest(editingRequest);

			log.error("Error during generating the surrender doc for the policy : " + editingRequest.getPolicy(), e);
		}

		return editingRequest;
	}

	/**
	 * Init creation user with his email.
	 * 
	 * @param creationUser
	 * @param transactionTax
	 * @return
	 */
	protected EditingUser updateCreationUserByEmail(EditingUser creationUser, TransactionTaxDTO transactionTax) {
		if (!StringUtils.isBlank(transactionTax.getUser())) {
			log.info("Now using the user of the transactionTax " + transactionTax.getOriginId() + " - user : "
					+ transactionTax.getUser());
			creationUser = new EditingUser();
			String email = documentService.getMailFromTrigram(transactionTax.getUser().trim());
			creationUser.setEmail(email);
		}
		return creationUser;
	}

	/**
	 * Update existing endorsement.
	 * 
	 * @param endorsement
	 * @param policy
	 */
	protected Endorsement updateEndorsementByPolicy(Endorsement endorsement, TransactionTaxDTO transactionTax,
			EditingRequest editingRequest) {
		if (endorsement == null || transactionTax == null) {
			return endorsement;
		}

		PolicyDTO policyDTO = policyService.getPolicy(transactionTax.getPolicy());
		Policy policy = policyMapper.asPolicy(policyDTO);
		policy.setContractType(documentService.getContractType(policyDTO.getProduct()));
		policy.setProduct(getProduct(editingRequest));
		endorsement.setPolicy(policy);
		return endorsement;
	}

	protected String getProduct(EditingRequest request) {
		return request == null ? StringUtils.EMPTY : request.getProduct();
	}

	/**
	 * Update existing cover letter.
	 * 
	 * @param coverLetter
	 * @param policy
	 */
	protected CoverLetter updateCoverLetterByPolicy(CoverLetter coverLetter, TransactionTaxDTO transactionTax) {
		if (coverLetter == null || transactionTax == null) {
			return coverLetter;
		}

		PolicyDTO policyDTO = policyService.getPolicy(transactionTax.getPolicy());
		Policy policy = policyMapper.asPolicy(policyDTO);
		policy.setContractType(documentService.getContractType(policyDTO.getProduct()));
		coverLetter.setPolicy(policy);
		return coverLetter;
	}

	/**
	 * @param creationUser
	 * @param language
	 * @return
	 */
	protected Header generateHeader(EditingUser creationUser, String language) {
		Header header = documentService.generateHeader(creationUser, DocumentType.SURRENDER, language, null, null);
		return header;
	}

	protected Endorsement generateEndorsement(TransactionTaxDTO transactionTaxDTO) {
		Endorsement endorsement = new Endorsement();
		endorsement.setEventType(transactionTaxDTO.getTransactionType());
		endorsement.setSurrenderDetails(generateSurrenderDetails(transactionTaxDTO));
		return endorsement;
	}

	private SurrenderDetails generateSurrenderDetails(TransactionTaxDTO transactionTaxDTO) {
		SurrenderDetails surrenderDetails = new SurrenderDetails();
		surrenderDetails.setFiscalDataFrance(generateFiscalDataFrance(transactionTaxDTO));
		return surrenderDetails;
	}

	public FiscalDataFrance generateFiscalDataFrance(TransactionTaxDTO transactionTaxDTO) {
		FiscalDataFrance fiscalDataFrance = new FiscalDataFrance();
		fiscalDataFrance.setContractDuration(computeContractDuration(transactionTaxDTO));

		Collection<TransactionTaxDetailsDTO> transactionTaxDetails = transactionTaxService
				.getTransactionTaxDetails(transactionTaxDTO.getTransactionTaxId());

		/**
		 * PlusValueAmountBefore
		 */
		List<TransactionTaxDetailsDTO> transactionTaxDetailsBefore = transactionTaxDetails.stream().filter(
				transactionTaxDetail -> (transactionTaxDetail.getPremiumDate().before(PRIME_DATE_FOR_PLUS_VALUE))
						&& transactionTaxDetail.getCapitalGainAmount() != null)
				.collect(Collectors.toList());
		Optional<BigDecimal> transactionTaxDetailsBeforeAmount = transactionTaxDetailsBefore.stream()
				.map(ttbt -> ttbt.getCapitalGainAmount()).reduce(BigDecimal::add);

		if (transactionTaxDetailsBeforeAmount.isPresent()) {
			PlusValueAmountBefore plusValueAmountBefore = new PlusValueAmountBefore();
			plusValueAmountBefore.setCurrencyCode(transactionTaxDTO.getCurrency());
			plusValueAmountBefore.setDate(PRIME_DATE_FOR_PLUS_VALUE);
			plusValueAmountBefore.setValue(transactionTaxDetailsBeforeAmount.get());

			fiscalDataFrance.setPlusValueAmountBefores(Arrays.asList(plusValueAmountBefore));
		}

		/**
		 * PlusValueAmountAfter
		 */
		List<TransactionTaxDetailsDTO> transactionTaxDetailsAfterOrEquals = transactionTaxDetails.stream()
				.filter(transactionTaxDetail -> (transactionTaxDetail.getPremiumDate().after(PRIME_DATE_FOR_PLUS_VALUE)
						|| transactionTaxDetail.getPremiumDate().equals(PRIME_DATE_FOR_PLUS_VALUE))
						&& transactionTaxDetail.getCapitalGainAmount() != null)
				.collect(Collectors.toList());
		Optional<BigDecimal> transactionTaxDetailsAfterAmount = transactionTaxDetailsAfterOrEquals.stream()
				.map(ttbt -> ttbt.getCapitalGainAmount()).reduce(BigDecimal::add);

		if (transactionTaxDetailsAfterAmount.isPresent()) {
			PlusValueAmountAfter plusValueAmountAfter = new PlusValueAmountAfter();
			plusValueAmountAfter.setCurrencyCode(transactionTaxDTO.getCurrency());
			plusValueAmountAfter.setDate(PRIME_DATE_FOR_PLUS_VALUE);
			plusValueAmountAfter.setValue(transactionTaxDetailsAfterAmount.get());

			fiscalDataFrance.setPlusValueAmountAfter(plusValueAmountAfter);
		}

		/**
		 * NetSurrenderAmount
		 */
		AmountType netSurrenderAmount = new AmountType();
		netSurrenderAmount.setCurrencyCode(transactionTaxDTO.getCurrency());
		netSurrenderAmount.setValue(transactionTaxDTO.getTransactionNetAmount());
		fiscalDataFrance.setNettoSurrenderAmount(netSurrenderAmount);

		fiscalDataFrance.setPolicyEffectDate(transactionTaxDTO.getPolicyEffectDate());

		fiscalDataFrance.setCalculationDetails(generateCalculationDetails(transactionTaxDTO));
		return fiscalDataFrance;
	}

	private CalculationDetails generateCalculationDetails(TransactionTaxDTO transactionTaxDTO) {
		SurrenderReportResultDTO SurrenderReportResultDTO = transactionTaxService
				.getTransactionTaxReportResult(transactionTaxDTO.getTransactionTaxId());

		CalculationDetails calculationDetails = transactionTaxMapper.asCalculationDetails(SurrenderReportResultDTO);
		return calculationDetails;
	}

	private CoverLetter generateCoverLetter(List<PolicyHolder> holders) {
		CoverLetter coverLetter = new CoverLetter();
		if (holders != null && !holders.isEmpty()) {
			PolicyHolder holder = holders.get(0);
			CorrespondenceAddress correspondenceAddress = personMapper.holderAsCorrespondenceAddress(holder);
			// PersonLight holderAsPersonLight =
			// personMapper.asPersonLight(holder);
			List<PersonLight> holderAsPersonLightList = personMapper.holderAsPersonLightList(holders);
			correspondenceAddress.setPersons(holderAsPersonLightList);
			coverLetter.setCorrespondenceAddress(correspondenceAddress);

		}
		coverLetter.setTitleId(computeTitleId(holders));

		return coverLetter;
	}

	protected CoverLetter generateCoverLetter(TransactionTaxDTO transactionTax) {
		return generateCoverLetter(generateHolders(transactionTax));
	}

	private List<MailingAddress> generateMailingAddress(PolicyDTO policyDTO) {
		List<MailingAddress> mailingAddresses = policyDocumentService.generateMailingAddress(policyDTO);
		return mailingAddresses;
	}

	protected List<MailingAddress> generateMailingAddress(TransactionTaxDTO transactionTax) {
		PolicyDTO policyDTO = policyService.getPolicy(transactionTax.getPolicy());
		return generateMailingAddress(policyDTO);
	}

	private ContractDuration computeContractDuration(TransactionTaxDTO transactionTaxDTO) {
		ContractDuration contractDuration = new ContractDuration();
		transactionTaxDTO.setTransactionDate(DateUtils.addDays(transactionTaxDTO.getTransactionDate(), 1));
		LocalDate policyEffectiveDate = toLocalDate(transactionTaxDTO.getPolicyEffectDate());
		LocalDate transactionDate = toLocalDate(transactionTaxDTO.getTransactionDate());

		Period diff = Period.between(policyEffectiveDate, transactionDate);

		contractDuration.setDays(diff.getDays());
		contractDuration.setMonths(diff.getMonths());
		contractDuration.setYears(diff.getYears());

		return contractDuration;
	}

	private LocalDate toLocalDate(Date date) {
		Instant instant = date.toInstant();
		return instant.atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private String computeTitleId(List<PolicyHolder> holderAsPersonLightList) {
		HashMap<String, Integer> genderCount = new HashMap<>();
		genderCount.put("MME", 0);
		genderCount.put("MR", 0);
		for (PolicyHolder personLight : holderAsPersonLightList) {
			String titleId = personLight.getTitleId();
			if (titleId != null) {
				Integer gender = genderCount.get(personLight.getTitleId());
				if (gender != null) {
					genderCount.put(personLight.getTitleId(), ++gender);
				}
			}
		}

		if (genderCount.get("MME") > 0 && genderCount.get("MR") == 0) {
			if (genderCount.get("MME") > 1) {
				return "MMES";
			}
			return "MME";
		}

		if (genderCount.get("MME") == 0 && genderCount.get("MR") > 0) {
			if (genderCount.get("MR") > 1) {
				return "MRS";
			}
			return "MR";
		}

		if (genderCount.get("MME") > 0 && genderCount.get("MR") > 0) {
			return "MME_MR";
		}

		return DEFAULT_TITLE_ID;
	}

	private List<PolicyHolder> generateHolders(Collection<PolicyHolderDTO> holdersDTO) {
		List<PolicyHolder> holders = new ArrayList<>();
		int i = 0;
		for (PolicyHolderDTO holderDTO : holdersDTO) {
			PolicyHolder holder = personMapper.asPolicyHolder(holderDTO);

			// If no rank defined
			// -> BareOwner => rank = 0
			// -> !BareOwner => rank = 1
			int order;
			if (BooleanUtils.isTrue(holder.isBareOwner())) {
				order = 0;
			} else {
				order = ++i;
			}
			holder.setOrder(order);

			holder.setLastRanking(holderDTO.getDeathSuccessor() || holderDTO.getLifeSuccessor());

			holders.add(holder);
		}
		return holders;
	}

	protected List<PolicyHolder> generateHolders(TransactionTaxDTO transactionTax) {
		PolicyDTO policyDTO = policyService.getPolicy(transactionTax.getPolicy());
		return generateHolders(policyDTO.getPolicyHolders());
	}

	// PolicyHolderDTO firstPolicyHolder =
	// policyDTO.getPolicyHolders().iterator().next();

}

/**
 * 
 */
package lu.wealins.webia.core.service.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.FrenchTaxPolicyTransactionDTO;
import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.CreateEditingResponse;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsRequest;
import lu.wealins.webia.core.mapper.TransactionTaxMapper;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.LiabilityCountryService;
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.LiabilityTransactionTaxService;
import lu.wealins.webia.core.service.TaxBatchService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.WebiaTransactionTaxService;
import lu.wealins.webia.ws.rest.request.DocumentGenerationResponse;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.PolicyOrigin;

/**
 * @author NGA
 *
 */
@Component
public class FrenchTaxHelper {
	private static final Date PRIME_DATE_FOR_PLUS_VALUE = DateUtil.parseYYYYMMDDDate("2017/09/27");
	SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

	SimpleDateFormat messageFormater = new SimpleDateFormat("dd/MM/YYYY");

	private static final Logger LOGGER = LoggerFactory.getLogger(FrenchTaxHelper.class);

	@Autowired
	private WebiaTransactionTaxService webiaTransactionService;

	@Autowired
	TaxBatchService taxService;

	@Autowired
	private EditingService editingService;

	@Autowired
	private LiabilityFundTransactionService liabilityTransactionService;

	@Autowired
	private LiabilityTransactionTaxService transactionTaxService;

	@Autowired
	private WebiaTransactionTaxService webiaTransactionTaxService;

	@Autowired
	private TransactionTaxMapper transactionTaxMapper;

	@Autowired
	LiabilityCountryService countryService;

	@Autowired
	private WebiaApplicationParameterService applicationParameterService;

	private static final String FRENCH_TAX_COUNTRIES = "FRENCH_TAX_COUNTRIES";
	private static final String FRENCH_TAX_BEGINNING_DATE = "TAX_DOC_GENERATE_START_DATE";


	/**
	 * Indicate that policy is eligible for French taxation.
	 * 
	 * @param policyIsoCountryCode
	 *            the policy countryisoCode.
	 * @return true if policy is eligible false otherwise.
	 */
	public Optional<String> isPolicyFrenchTaxable(String policy) {

		List<String> policyCountryCodes = countryService.getPolicyCountries(policy);

		if (policyCountryCodes == null || policyCountryCodes.isEmpty()) {
			return Optional.empty();
		}

		List<String> policyIsoCountryCodes = policyCountryCodes.stream()
				.filter(countryIsoCode -> !StringUtils.isBlank(countryIsoCode))
				.map(country -> StringUtils.strip(country)).collect(Collectors.toList());

		List<String> frenchTaxCountries = applicationParameterService.getApplicationParameters(FRENCH_TAX_COUNTRIES)
				.stream()
				.filter(countryIsoCode -> !StringUtils.isBlank(countryIsoCode))
				.map(country -> StringUtils.strip(country))
				.collect(Collectors.toList());

		if (frenchTaxCountries == null || frenchTaxCountries.isEmpty()) {
			return Optional.empty();
		}

		return frenchTaxCountries.stream()
				.filter(eligibleCountryIso2Code -> policyIsoCountryCodes.contains(eligibleCountryIso2Code)).findFirst();
	}


	public boolean isDateAfterFrenchTaxBeginningDate(Date effectiveDate) {

		ApplicationParameterDTO BeginnningDateDTO = applicationParameterService
				.getApplicationParameter(FRENCH_TAX_BEGINNING_DATE);

		if (BeginnningDateDTO == null) {
			return true;
		}
		Date begginingDate = null;
		try {
			begginingDate = formater.parse(BeginnningDateDTO.getValue());
		} catch (ParseException e) {
			LOGGER.error(
					"Error occured when parsing configured beginning date for French tax report the required format is yyyy-MM-dd ");
		}
		if (Objects.isNull(begginingDate) || Objects.isNull(effectiveDate)) {
			return false;
		}
		LocalDate effectiveDateLocaldate = effectiveDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate beginingDateInLocalDate = begginingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return !effectiveDateLocaldate.isBefore(beginingDateInLocalDate);
	}

	/**
	 * Indicate that event can be reported.
	 * 
	 * @param eventType
	 *            The event type code
	 * @return true if event can be reported false otherwise.
	 */
	public boolean isEventTypeCanBeReported(Integer eventType) {
		boolean isEventCanBeSurrenderReported = new Integer(4).equals(eventType) || new Integer(21).equals(eventType)
				|| new Integer(15).equals(eventType);
		return isEventCanBeSurrenderReported;
	}

	/**
	 * Process French tax data from Lissia and generate Editing request.
	 * <p>
	 * Get all transactions from Lissia, extract selected transactions by eventDate,
	 * extract TransactionsTax and TransactionTaxSetails, Save them , and generate
	 * corresponding editingRequest
	 * 
	 * @param policy
	 *            the policy id.
	 * @param eventDate
	 *            the transaction eventDate of policy.
	 * @return DocumentGenerationResponse
	 */
	public DocumentGenerationResponse processFrenchTaxDataAndGenerateEditingRequest(String policy, Date eventDate, Boolean isFrenchTaxable) {
		DocumentGenerationResponse response = new DocumentGenerationResponse();
		List<FrenchTaxPolicyTransactionDTO> frenchTaxTransactions = getAllLissiaTransactionsOfPolicy(policy);
		if (frenchTaxTransactions == null || frenchTaxTransactions.isEmpty()) {
			response.setErrorMessage("No transaction  found for policy policy : " + policy);
			return response;
		}

		if(isFrenchTaxable == null) {
			response.setErrorMessage("Can not determined type of transaction, dont know if transaction is French taxable or not : " + policy);
			return response;	
		}
		
		Optional<FrenchTaxPolicyTransactionDTO> selectedTransactionId = getSelectedTransactions(eventDate,
				frenchTaxTransactions);
		if (!selectedTransactionId.isPresent() || Objects.isNull(selectedTransactionId.get().getLastTransactionId())) {
			response.setErrorMessage(
					"Transaction or origin Id not found for policy : " + policy + " at effective date : " + eventDate);
			return response;
		}

		TransactionTaxDTO selectedTransaction = transactionTaxMapper.asTransactionTaxDTO(selectedTransactionId.get());
		calculateAndSaveFrenchTaxData(eventDate, frenchTaxTransactions, selectedTransaction, isFrenchTaxable);
		TransactionTaxDTO TransactionTaxSelected = getSavedSelectedTransactionTax(selectedTransaction);
		CreateEditingResponse editingResponse = generateFrenchTaxEditingRequest(TransactionTaxSelected, isFrenchTaxable.booleanValue());
		EditingRequest initialRequest = editingService.getEditingRequestById(editingResponse.getRequestId());
		response.setRequest(initialRequest);
		String succesMessage = "The tax certificate letter of the transaction on " 
				+ messageFormater.format(eventDate)
				+ " is being generated. You will receive the document within a few minute in your mailbox. ";
		response.setSuccessMessage(succesMessage);
		return response;
	}

	/**
	 * Generate French tax edition Request,that should be process by batch.
	 * <p>
	 * the Report is generated by batch specific job, This method only provide and
	 * create edition request.
	 * 
	 * @param TransactionTaxSelected
	 *            The transactionTax corresponding to transaction event selected.
	 * @return editing Response.
	 */
	private CreateEditingResponse generateFrenchTaxEditingRequest(TransactionTaxDTO TransactionTaxSelected, boolean isFrenchTaxable) {
		CreateEditingRequest request = new CreateEditingRequest();
		request.setTransactionTax(TransactionTaxSelected.getTransactionTaxId());
		request.setPolicyOrigin(PolicyOrigin.LISSIA.name());
		
		if(!isFrenchTaxable) {
			request.setDocumentType(DocumentType.WITHDRAWAL);
		}else {
			request.setDocumentType(DocumentType.SURRENDER);
		}
		
		
		CreateEditingResponse editingResponse = editingService
				.generateSurrenderDoc(request);
		return editingResponse;
	}

	/**
	 * Retrieve saved transactionTax from unsaved one.
	 * 
	 * @param the
	 *            unsaved TransactionTax
	 * @return the saved corresponding TrnsactionTax
	 */
	private TransactionTaxDTO getSavedSelectedTransactionTax(TransactionTaxDTO unSavedTransactionTax) {
		TransactionTaxDTO savedTransactionTax = webiaTransactionService
				.getTransactionTaxByOriginId(unSavedTransactionTax.getOriginId().longValue());
		return savedTransactionTax;
	}

	/**
	 * Calculate and saved transactionTax and TransactionTax details.
	 * 
	 * @param eventDate
	 *            the event date of transaction
	 * @param frenchTaxTransactions
	 *            all the transactions from that impacts surrender report process.
	 * @param the
	 *            unsaved transactionTax corresponding to original selected
	 *            Transaction
	 */
	public void calculateAndSaveFrenchTaxData(Date eventDate,
			List<FrenchTaxPolicyTransactionDTO> frenchTaxTransactions, TransactionTaxDTO selectedTransaction, Boolean isFrenchTaxable) {
		List<TransactionTaxDTO> allTransactionTaxBefore = frenchTaxTransactions.stream()
				.filter(transaction -> formater.format(eventDate)
						.compareTo(formater.format(transaction.getEffectiveDate())) > 0)
				.sorted(Comparator.comparing(FrenchTaxPolicyTransactionDTO::getEffectiveDate))
				.map(transaction -> transactionTaxMapper.asTransactionTaxDTO(transaction)).collect(Collectors.toList());

		selectedTransaction.setStatus(2);
		allTransactionTaxBefore.add(selectedTransaction);

		List<TransactionTaxDTO> transactionsToinsert = allTransactionTaxBefore.stream()
				.sorted(Comparator.comparing(TransactionTaxDTO::getTransactionDate)).collect(Collectors.toList());

		List<TransactionTaxDTO> insertedTransactionTaxs = transactionTaxService
				.insertTransactionTax(transactionsToinsert);

		TransactionTaxDetailsRequest transactionRequest = new TransactionTaxDetailsRequest();
		transactionRequest.setTransactionTax(insertedTransactionTaxs);
		transactionRequest.setFrenchTaxable(isFrenchTaxable);
		taxService.createTransactionTaxDetails(transactionRequest);
	}

	/**
	 * Get from all eligible Lissia transactions, the one corresponding to
	 * eventDate, which is the selected transaction.
	 * 
	 * @param eventDate
	 *            the eventDate of selected transaction
	 * @param frenchTaxTransactions
	 *            all Eligible transactions
	 * @return Optional that contains
	 */
	private Optional<FrenchTaxPolicyTransactionDTO> getSelectedTransactions(Date eventDate,
			List<FrenchTaxPolicyTransactionDTO> frenchTaxTransactions) {
		Optional<FrenchTaxPolicyTransactionDTO> selectedTransactionId = frenchTaxTransactions.stream()
				.filter(transaction -> formater.format(eventDate)
						.compareTo(formater.format(transaction.getEffectiveDate())) == 0)
				.findFirst();
		return selectedTransactionId;
	}

	/**
	 * Get all transactions from Lissia that impacts French tax process.
	 * 
	 * @param policy
	 *            the policy Id
	 * @return French tax transactions eligible for French tax process.
	 */
	private List<FrenchTaxPolicyTransactionDTO> getAllLissiaTransactionsOfPolicy(String policy) {
		return (List<FrenchTaxPolicyTransactionDTO>) liabilityTransactionService
				.getPolicyTransactionsForFrenchTax(policy);
	}

	/***
	 * Get plus value before of transactions details.
	 * 
	 * @param transactionTaxDetails
	 * @return optional of plus value after if any or Null otherwise
	 */
	public Optional<BigDecimal> processPlusValueBefore(Collection<TransactionTaxDetailsDTO> transactionTaxDetails) {
	
	List<TransactionTaxDetailsDTO> transactionTaxDetailsBefore = transactionTaxDetails.stream().filter(
			transactionTaxDetail -> (transactionTaxDetail.getPremiumDate().before(PRIME_DATE_FOR_PLUS_VALUE))
					&& transactionTaxDetail.getCapitalGainAmount() != null)
			.collect(Collectors.toList());

		return transactionTaxDetailsBefore.stream()
			.map(ttbt -> ttbt.getCapitalGainAmount()).reduce(BigDecimal::add);
	}

	/***
	 * Get plus value after of transactions details.
	 * 
	 * @param transactionTaxDetails
	 * @return optional of plus value after if any or Null otherwise
	 */
	public Optional<BigDecimal> processPlusValueAfter(Collection<TransactionTaxDetailsDTO> transactionTaxDetails) {
		List<TransactionTaxDetailsDTO> transactionTaxDetailsAfterOrEquals = transactionTaxDetails.stream()
				.filter(transactionTaxDetail -> (transactionTaxDetail.getPremiumDate().after(PRIME_DATE_FOR_PLUS_VALUE)
						|| transactionTaxDetail.getPremiumDate().equals(PRIME_DATE_FOR_PLUS_VALUE))
						&& transactionTaxDetail.getCapitalGainAmount() != null)
				.collect(Collectors.toList());
		return transactionTaxDetailsAfterOrEquals.stream().map(ttbt -> ttbt.getCapitalGainAmount())
				.reduce(BigDecimal::add);
	}

	/***
	 * Get Plus value before and after transaction tax by ID.
	 * 
	 * @param transactionTaxId
	 * @return
	 */
	public Pair<BigDecimal, BigDecimal> processPlusValue(Long transactionTaxId) {
		Collection<TransactionTaxDetailsDTO> transactionTaxDetails = webiaTransactionTaxService
				.getTransactionTaxDetails(transactionTaxId);
		return Pair.of(processPlusValueBefore(transactionTaxDetails).orElse(null),
				processPlusValueAfter(transactionTaxDetails).orElse(null));
	}
	
	public TransactionTaxDTO getTransactionTax(String policy, Date eventDate, boolean isFrenchTaxable) {
		List<FrenchTaxPolicyTransactionDTO> frenchTaxTransactions = getAllLissiaTransactionsOfPolicy(policy);
		/*
		 * if (frenchTaxTransactions == null || frenchTaxTransactions.isEmpty()) { response.setErrorMessage("No transaction  found for policy policy : " + policy); return response; }
		 */

		Optional<FrenchTaxPolicyTransactionDTO> selectedTransactionId = getSelectedTransactions(eventDate,
				frenchTaxTransactions);
		/*
		 * if (!selectedTransactionId.isPresent() || Objects.isNull(selectedTransactionId.get().getLastTransactionId())) { response.setErrorMessage( "Transaction or origin Id not found for policy : "
		 * + policy + " at effective date : " + eventDate); return response; }
		 */

		TransactionTaxDTO selectedTransaction = transactionTaxMapper.asTransactionTaxDTO(selectedTransactionId.get());
		calculateAndSaveFrenchTaxData(eventDate, frenchTaxTransactions, selectedTransaction, isFrenchTaxable);
		TransactionTaxDTO TransactionTaxSelected = getSavedSelectedTransactionTax(selectedTransaction);

		return TransactionTaxSelected;
	}

}

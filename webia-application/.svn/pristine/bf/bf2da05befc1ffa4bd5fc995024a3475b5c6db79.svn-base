package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.webia.services.EditingRequestStatus;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.editing.common.webia.sepa.AccountIdentification4Choice;
import lu.wealins.editing.common.webia.sepa.ActiveOrHistoricCurrencyAndAmount;
import lu.wealins.editing.common.webia.sepa.AmountType3Choice;
import lu.wealins.editing.common.webia.sepa.BranchAndFinancialInstitutionIdentification4;
import lu.wealins.editing.common.webia.sepa.CashAccount16;
import lu.wealins.editing.common.webia.sepa.ChargeBearerType1Code;
import lu.wealins.editing.common.webia.sepa.CreditTransferTransactionInformation10;
import lu.wealins.editing.common.webia.sepa.CustomerCreditTransferInitiationV03;
import lu.wealins.editing.common.webia.sepa.Document;
import lu.wealins.editing.common.webia.sepa.FinancialInstitutionIdentification7;
import lu.wealins.editing.common.webia.sepa.GenericFinancialIdentification1;
import lu.wealins.editing.common.webia.sepa.GroupHeader32;
import lu.wealins.editing.common.webia.sepa.PartyIdentification32;
import lu.wealins.editing.common.webia.sepa.PaymentIdentification1;
import lu.wealins.editing.common.webia.sepa.PaymentInstructionInformation3;
import lu.wealins.editing.common.webia.sepa.PaymentMethod3Code;
import lu.wealins.editing.common.webia.sepa.PaymentTypeInformation19;
import lu.wealins.editing.common.webia.sepa.PostalAddress6;
import lu.wealins.editing.common.webia.sepa.Priority2Code;
import lu.wealins.editing.common.webia.sepa.RemittanceInformation5;
import lu.wealins.editing.common.webia.sepa.ServiceLevel8Choice;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.SepaPaymentGenerationService;
import lu.wealins.webia.core.service.TransferService;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.EditingUser;

@Service
@Transactional(readOnly = true)
public class SepaPaymentGenerationServiceImpl implements SepaPaymentGenerationService {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SepaPaymentGenerationServiceImpl.class);

	private static final String GRAMMAR_001 = "001";
	private static final String WEALINS = "WEALINS S.A.";
	private static final String WEBIA = "WEBIA";
	private static final String SEPA = "SEPA";
	private static final String LU = "LU";
	private static final String NOTPROVIDED = "NOTPROVIDED";
	private static final String ADDRESS = "ADDRESS";
	
	// MathUtilityService
	public static BigDecimal ZERO = new BigDecimal(BigInteger.ZERO);
	public static int DEFAULT_SCALE = 20;
	public static int SCALE_FOR_AMOUNT = 2;
	
	private static List<String> address = new ArrayList<>();

	/**
	 * Services
	 */
	@Autowired
	private DocumentService documentGenerationService;

	@Autowired
	private TransferService transferService;
	
	@Autowired
	private LiabilityFundService liabilityFundService;

	// @Value("${sepa.payment.path}")
	@Value("${editique.xml.path}")
	private String sepaFileLocation;

	@Override
	public EditingRequest generateSepaPayment(SecurityContext context, EditingRequest editingRequest) {

		try {
			Assert.notNull(editingRequest, "The edition request can not be null");

			editingRequest.setStatus(EditingRequestStatus.IN_PROGRESS);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

			EditingUser creationUser = editingRequest.getCreationUser();

			Assert.notNull(creationUser, "The creation user of the document generation request cannot be null.");
			Assert.isTrue(StringUtils.isNotBlank(editingRequest.getTransferIds()), "The transfers list cannot be null.");

			// retrieve the Transfers data related to the request (for a same is FID/is wealins dedicated/payment date / swift )
			String[] transferIdsString = editingRequest.getTransferIds().replaceAll(" ", "").split(",");
			List<Long> transferIds = new ArrayList<Long>();
			for (int i = 0; i < transferIdsString.length; i++) {
				transferIds.add(Long.valueOf(transferIdsString[i]));
			}
			Collection<TransferDTO> transferDTOs = transferService.getTransfers(transferIds);
			TransferDTO first = transferDTOs.iterator().next();
			int fileNumber = 0;

			// Generate suffix SEPA file
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			StringBuilder path = new StringBuilder(isFid(first) ? "FID/SEPA_" : "HORS_FID/SEPA_");
			path.append(GRAMMAR_001).append("_").append(sdf.format(new Date())).append("_").append(StringUtils.trim(getSwift(first)));
			path.append("_").append(fileNumber);
			path.append(".xml");
			
			String documentPath = sepaFileLocation + FilenameUtils.separatorsToSystem(path.toString());
			String saveResponse = documentGenerationService.saveXmlDocument(createDocument(editingRequest, transferDTOs), documentPath);

			editingRequest.setOutputStreamPath(saveResponse);
			editingRequest.setStatus(EditingRequestStatus.GENERATED);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

		} catch (Exception e) {
			editingRequest.setStatus(EditingRequestStatus.IN_ERROR);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

			LOGGER.error("Error occurred during the acceptation document report generation for product " + editingRequest.getProduct() + " and policy "
					+ editingRequest.getPolicy(), e);
		}

		return editingRequest;
	}

	private Document createDocument(EditingRequest editingRequest, Collection<TransferDTO> transferDTOs) {
		LOGGER.info("Creating sepa payment related to the following list of Transfer Ids :" + editingRequest.getTransferIds());

		// Generate sepa for the credit transfer initiation (pain.001)
		Document document = new Document();
		try {
			document = generateCustomerCreditTransferInitiationV03(transferDTOs);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		return document;
	}
	
	private boolean isFid(TransferDTO transferDTO) {
		FundDTO fund = liabilityFundService.getFund(transferDTO.getFdsId());
		return fund != null && FundSubType.FID.name().equals(fund.getFundSubType());
	}
	
	private String getSwift(TransferDTO transferDTO) {
		return transferDTO.getSwiftDonOrd();
	}

	private Document generateCustomerCreditTransferInitiationV03(Collection<TransferDTO> paymentsGrouped) throws DatatypeConfigurationException {

		Document response = new Document();

		CustomerCreditTransferInitiationV03 customerCreditTransferInitiation = new CustomerCreditTransferInitiationV03();

		// GroupHeader
		customerCreditTransferInitiation.setGrpHdr(createHeader(paymentsGrouped));
		// PaymentInformation
		customerCreditTransferInitiation.setPmtInves(createPaymentInformation(paymentsGrouped));

		response.setCstmrCdtTrfInitn(customerCreditTransferInitiation);

		return response;
	}

	/**
	 * Create the header. Set of characteristics shared by all transactions included in the message. Mandatory.
	 * 
	 * @param paymentsGrouped The group of payments
	 * @return The header
	 * @throws DatatypeConfigurationException If a date cannot be converted to a gregorian calendar
	 */
	private GroupHeader32 createHeader(Collection<TransferDTO> paymentsGrouped) throws DatatypeConfigurationException {
		GroupHeader32 header = new GroupHeader32();

		// MessageIdentification
		header.setMsgId(createMsgId(paymentsGrouped));
		// CreationDateTime: Date and Time at which the message was created
		// (YYYY-MM-DDThh:mm:ss). Mandatory.
		header.setCreDtTm(createXMLGregorianCalendarWithTIme(new Date()));
		// Number of individual transactions contained in a message. Mandatory.
		header.setNbOfTxs(String.valueOf(paymentsGrouped.size()));
		// InitiatingParty
		header.setInitgPty(createPartyIdentification(paymentsGrouped));

		return header;
	}

	/**
	 * Point-to-point reference assigned by the instructing party and sent to the next party in the chain in order to unambiguously identify the message.
	 * 
	 * @param paymentsGrouped The group of payments. Mandatory.
	 * @return The message id (must be unique).
	 */
	private String createMsgId(Collection<TransferDTO> paymentsGrouped) {
		TransferDTO firstPayment = paymentsGrouped.iterator().next();

		if (firstPayment != null) {
			return WEALINS + firstPayment.getTransferId();
		}

		return null;
	}

	/**
	 * Return a date with the xml gregorian calendar representation (with the following format: YYYY-MM-DDThh:mm:ss)
	 * 
	 * @param date The date
	 * @return The xml gregorian calendar
	 * @throws DatatypeConfigurationException If the date cannot be converted to a gregorian calendar
	 */
	private XMLGregorianCalendar createXMLGregorianCalendarWithTIme(Date date) throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();

		c.setTime(date);

		return DatatypeFactory.newInstance().newXMLGregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH),
				c.get(Calendar.HOUR), c.get(Calendar.MINUTE), c.get(Calendar.SECOND), DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
	}

	/**
	 * Party initiating the payment. This can be either the debtor or a party initiating the payment on behalf of the debtor. Mandatory.
	 * 
	 * Rules : Either Name or Identification of the initiating party or both must be used. For Luxembourg companies, Identification (within Other) shall contain for example the â€œVAT numberâ€� or RCS
	 * number; The issuer of this identification is not mandatory. No business control is applied on the field InitiatingParty.
	 * 
	 * @return The party initiating the payment.
	 */
	private PartyIdentification32 createPartyIdentification(Collection<TransferDTO> paymentsGrouped) {
		PartyIdentification32 partyIdentification = new PartyIdentification32();

		partyIdentification.setNm(WEALINS);

		return partyIdentification;
	}

	/**
	 * Set of characteristics that applies to the debit side of the payment transactions as well as one or several Transaction Information Blocks. Mandatory.
	 * 
	 * @param paymentsGrouped The group of payments
	 * @return The payment information.
	 * @throws DatatypeConfigurationException If one date cannot be converted to the gregorian calendar format.
	 */
	private List<PaymentInstructionInformation3> createPaymentInformation(Collection<TransferDTO> paymentsGrouped)
			throws DatatypeConfigurationException {

		List<PaymentInstructionInformation3> paymentInstructions = new ArrayList<PaymentInstructionInformation3>();
		Map<String, Collection<TransferDTO>> paymentsByCurrencyMap = groupPaymentsByCurrency(paymentsGrouped);
		for (Collection<TransferDTO> paymentsByCurrency : paymentsByCurrencyMap.values()) {
			Map<String, Collection<TransferDTO>> paymentsByPrincipalIbanMap = groupPaymentsByPrincipalIban(paymentsByCurrency);
			for (Collection<TransferDTO> paymentsByPrincipalIban : paymentsByPrincipalIbanMap.values()) {
				// one payment instruction by fid
				Iterator<TransferDTO> iterator = paymentsByPrincipalIban.iterator();
				while (iterator.hasNext()) {
					TransferDTO currentPayment = iterator.next();
					if (isFidPayment(currentPayment)) {
						paymentInstructions.add(createPaymentInstructions(Arrays.asList(currentPayment)));
						iterator.remove();
					}
				}
				if (CollectionUtils.isNotEmpty(paymentsByPrincipalIban)) {
					paymentInstructions.add(createPaymentInstructions(paymentsByPrincipalIban));
				}
			}
		}

		return paymentInstructions;
	}

	private Map<String, Collection<TransferDTO>> groupPaymentsByCurrency(Collection<TransferDTO> payments) {
		Map<String, Collection<TransferDTO>> paymentsByCurrency = new HashMap<>();

		for (TransferDTO payment : payments) {
			String key = payment.getTrfCurrency();
			Collection<TransferDTO> values = paymentsByCurrency.get(key);
			if (CollectionUtils.isEmpty(values)) {
				values = new ArrayList<>();
				paymentsByCurrency.put(key, values);
			}

			values.add(payment);
		}

		return paymentsByCurrency;
	}

	private Map<String, Collection<TransferDTO>> groupPaymentsByPrincipalIban(Collection<TransferDTO> payments) {
		Map<String, Collection<TransferDTO>> paymentsByPrincipalIban = new HashMap<>();

		for (TransferDTO payment : payments) {
			String key = payment.getIbanDonOrd();
			Collection<TransferDTO> values = paymentsByPrincipalIban.get(key);
			if (CollectionUtils.isEmpty(values)) {
				values = new ArrayList<>();
				paymentsByPrincipalIban.put(key, values);
			}

			values.add(payment);
		}

		return paymentsByPrincipalIban;
	}

	private boolean isFidPayment(TransferDTO payment) {
		FundDTO fund = liabilityFundService.getFund(payment.getFdsId());
		return fund != null && FundSubType.FID.name().equals(fund.getFundSubType());
	}

	private PaymentInstructionInformation3 createPaymentInstructions(Collection<TransferDTO> payments) throws DatatypeConfigurationException {

		PaymentInstructionInformation3 paymentInstruction = new PaymentInstructionInformation3();
		TransferDTO payment = payments.iterator().next();

		// PaymentInformationIdentification
		paymentInstruction.setPmtInfId(createPaymentInformationIdentification(payment));
		// PaymentMethod - Transfer. Mandatory
		paymentInstruction.setPmtMtd(PaymentMethod3Code.TRF);
		paymentInstruction.setBtchBookg(Boolean.FALSE);
		// NumberOfTransactions - Number of individual transactions contained in a payment information block. Optional 
		paymentInstruction.setNbOfTxs(String.valueOf(payments.size()));
		// ControlSum
		paymentInstruction.setCtrlSum(createControlSum(payments));
		// PaymentTypeInformation
		paymentInstruction.setPmtTpInf(createPaymentTypeInformation());
		// RequestedExecutionDate
		paymentInstruction.setReqdExctnDt(createRequestedExecutionDate(payment));
		// Debtor
		paymentInstruction.setDbtr(createDebtor(payment));
		// DebtorAccount
		paymentInstruction.setDbtrAcct(createDebtorAccount(payment));
		// DebtorAgent
		paymentInstruction.setDbtrAgt(createDebtorAgent(payment));

		paymentInstruction.setChrgBr(ChargeBearerType1Code.SLEV);

		paymentInstruction.setCdtTrfTxInves(createCreditTransferTransactionInformation(payments));
		
		return paymentInstruction;
	}

	/**
	 * Reference assigned by a sending party in order to unambiguously identify
	 * the payment information block within the message.
	 * 
	 * @param payment
	 *            The payment
	 * @return the payment information.
	 */
	private String createPaymentInformationIdentification(TransferDTO payment) {
		SimpleDateFormat formatDateWithoutHours = new SimpleDateFormat("yyyy-MM-dd");

		Date paymentDt = payment.getTrfDt() != null ? payment.getTrfDt() : new Date();

		return WEBIA + "/" + payment.getTransferId() + "/" + formatDateWithoutHours.format(paymentDt);
	}

	/**
	 * Total of all individual amounts included in a payment information block,
	 * irrespective of currencies. Optional.
	 * 
	 * @param payments
	 *            The payments
	 * @return Total of all individual amounts
	 */
	private BigDecimal createControlSum(Collection<TransferDTO> payments) {
		BigDecimal sum = BigDecimal.ZERO;

		for (TransferDTO payment : payments) {
			sum = add(sum, payment.getTrfMt());
		}

		return scaleAmount(sum);
	}
	
	/**
	 * <p>Adds <code>amount2</code> to <code>amount1</code>.<br/>
	 * Passing a null amount1 will result in a null value<br/>
	 * Passing a null amount2 will result in amount1.</p>
	 * 
	 * @param amount1 the amount to work on
	 * @param amount2 the amount to add to amount1
	 * @return a new object with the computed value
	 */
	private static BigDecimal add(BigDecimal amount1, BigDecimal amount2) {
		
		if(amount1 == null)
			return null;
		
		if(amount2 == null)
			return amount1;

		amount1 = ensureScale(amount1);
		amount2 = ensureScale(amount2);
		amount1 = amount1.add(amount2);
		return isZero(amount1) ? ZERO : amount1;
	}
	
	/**
	 * Returns a new BigDecimal for the given <code>value</code>
	 * if its scale is less than <code>DEFAULT_SCALE</code>, or the same
	 * BigDecimal if it is greater.
	 * 
	 * @param value the value to work on
	 * @return a BigDecimal with at least the <code>DEFAULT_SCALE</code>
	 */
	private static BigDecimal ensureScale(BigDecimal value) {
		
		if(value.scale() < DEFAULT_SCALE)
			return value.setScale(DEFAULT_SCALE);
		
		return value;
	}
	
	/**
	 * Returns a boolean value indicating whether the given <code>amount</code>
	 * is equal to <code>ZERO</code> (precisely 0.00)
	 * 
	 * @param amount the amount to check
	 * @return a boolean value
	 */
	public static boolean isZero(BigDecimal amount) {
		return amount != null && ZERO.compareTo(amount) == 0;
	}
	
	/**
	 * Scales the given <code>amount</code> to {@link BigDecimal#ROUND_HALF_UP} with a scale set to {@link MathUtilityService#SCALE_FOR_AMOUNT}.
	 * @param amount the amount to scale
	 * @return a new scaled amount
	 */
	public static BigDecimal scaleAmount(BigDecimal amount) {
		if (amount == null) {
			return null;
		}
		
		BigDecimal m = amount.setScale(SCALE_FOR_AMOUNT, BigDecimal.ROUND_HALF_UP);
		return m;
	}

	/**
	 * Set of elements which specifies the type of transaction more in detail.
	 * Rule: If PaymentMethod is CHK, the PaymentTypeInformation is not allowed.
	 * Optional.
	 * 
	 * @return The type of transaction more in detail.
	 */
	private PaymentTypeInformation19 createPaymentTypeInformation() {
		PaymentTypeInformation19 paymentTypeInformation = new PaymentTypeInformation19();

		/*
		 * InstructionPriority - Indicates the degree of urgency the initiating
		 * party would like the Debtorâ€™s Bank to apply to the processing of
		 * the instruction.
		 */
		paymentTypeInformation.setInstrPrty(Priority2Code.NORM);
		// ServiceLevel
		paymentTypeInformation.setSvcLvl(createServiceLevel());

		return paymentTypeInformation;
	}

	/**
	 * Agreement or rules according to which the transaction must be processed.
	 * Optional.
	 * 
	 * @return The service level
	 */
	private ServiceLevel8Choice createServiceLevel() {
		ServiceLevel8Choice serviceLevel = new ServiceLevel8Choice();

		// Code
		// serviceLevel.setCd(SEPA);
		// Proprietary identification of a particular bankâ€™s service level
		// agreed upon beforehand
		serviceLevel.setPrtry(SEPA);

		return serviceLevel;
	}

	/**
	 * Date at which the initiating party request the debtor agent to process
	 * the payment.
	 * 
	 * @param payment
	 *            The payment
	 * @return payment date.
	 * @throws DatatypeConfigurationException
	 *             If the date cannot converted to the xml gregorian calendar
	 *             format
	 */
	private XMLGregorianCalendar createRequestedExecutionDate(TransferDTO payment) throws DatatypeConfigurationException {

		Date requestedExecutionDate = new Date();
		if (payment.getTrfDt() != null && payment.getTrfDt().after(requestedExecutionDate)) {
			requestedExecutionDate = payment.getTrfDt();
		}

		return createXMLGregorianCalendar(requestedExecutionDate);

	}

	/**
	 * Return a date with the xml gregorian calendar representation (with the
	 * following format: YYYY-MM-DDThh:mm:ss)
	 * 
	 * @param date
	 *            The date
	 * @return The xml gregorian calendar
	 * @throws DatatypeConfigurationException
	 *             If the date cannot be converted to a gregorian calendar
	 */
	private XMLGregorianCalendar createXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();

		c.setTime(date);

		return DatatypeFactory.newInstance().newXMLGregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH),
				DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED,
				DatatypeConstants.FIELD_UNDEFINED);
	}

	/**
	 * Party owing an amount of money to the (ultimate) creditor. Mandatory.
	 * 
	 * @param payment
	 *            The payment.
	 * @return The debtor.
	 */
	private PartyIdentification32 createDebtor(TransferDTO payment) {
		PartyIdentification32 debtor = new PartyIdentification32();

		PostalAddress6 postalAddress = new PostalAddress6();
		debtor.setNm(WEALINS);
		postalAddress.setCtry(LU);
		postalAddress.setAdrLines(retrieveAddressValue());

		debtor.setPstlAdr(postalAddress);

		return debtor;
	}

	private List<String> retrieveAddressValue() {
		if (address == null) {
			address = Arrays.asList(retrieveParameterValue(ADDRESS));
		}
		return address;
	}

	private String retrieveParameterValue(String code) {
		if (code == null) {
			throw new IllegalArgumentException("Application parameter code cannot be null.");
		}
		/*ConstantRequest paramConstantRequest = new ConstantRequest();
		paramConstantRequest.setCode(code);

		ApplicationParameterResponse applicationParameter = constantServices.getOneApplicationParameter(paramConstantRequest);

		List<ApplicationParameter> applicationParameters = applicationParameter.getApplicationParameters();

		if (CollectionUtils.isEmpty(applicationParameters)) {
			throw new IllegalStateException("There is no parameter for the code " + code);
		}
		if (applicationParameters.size() > 1) {
			throw new IllegalStateException("There are duplicate parameters for the code " + code);
		}

		ApplicationParameter singleParameter = applicationParameters.iterator().next();

		return singleParameter.getValue();*/
		return code;
	}

	/**
	 * Unambiguous identification of the account of the debtor to which a debit
	 * entry will be made as a result of the transaction.
	 * 
	 * @return the debtor account.
	 * 
	 * @param payment
	 *            The payment.
	 */
	private CashAccount16 createDebtorAccount(TransferDTO payment) {
		CashAccount16 debtorAccount = new CashAccount16();

		AccountIdentification4Choice accountIdentification = new AccountIdentification4Choice();

		accountIdentification.setIBAN(payment.getIbanDonOrd().replaceAll("\\s+", ""));

		debtorAccount.setId(accountIdentification);

		return debtorAccount;
	}

	/**
	 * Financial institution servicing an account for the debtor. Mandatory
	 * 
	 * @param payment
	 * @return
	 */
	private BranchAndFinancialInstitutionIdentification4 createDebtorAgent(TransferDTO payment) {

		BranchAndFinancialInstitutionIdentification4 debtorAgent = new BranchAndFinancialInstitutionIdentification4();
		
		debtorAgent.setFinInstnId(createFinancialInstitutionId(payment));

		return debtorAgent;
	}

	/**
	 * Financial institution servicing an account for the debtor. Rules: The BIC
	 * is optional for national SEPA transactions as of 01/02/2014. The BIC is
	 * mandatory for EU/EEA cross-border transactions until 31 January 2016 and
	 * it will continue to be mandatory for non-EU /EEA cross-border SEPA
	 * transactions. If field <BIC> is not used, then only â€˜NOTPROVIDEDâ€™ is
	 * allowed in the field "Identification"
	 * 
	 * @param payment
	 *            The payment
	 * @return The financial institution id.
	 */
	private FinancialInstitutionIdentification7 createFinancialInstitutionId(TransferDTO payment) {
		FinancialInstitutionIdentification7 financialInstitutionId = new FinancialInstitutionIdentification7();
		
		if (payment.getSwiftDonOrd() != null) {
			financialInstitutionId.setBIC(payment.getSwiftDonOrd());
		} else {
			GenericFinancialIdentification1 other = new GenericFinancialIdentification1();
			other.setId(NOTPROVIDED);
			financialInstitutionId.setOthr(other);
		}
		
		return financialInstitutionId;
	}

	/**
	 * Set of elements providing information which is specific for the
	 * individual transaction(s) included into the message. Mandatory. The
	 * following elements must not be used for a SEPA Credit Transfer:
	 * <ul>
	 * <li>- EquivalentAmount <EqvtAmt></li>
	 * <li>- ExchangeRateInformation <XchgRateInf></li>
	 * <li>- ChequeInstruction <ChqInstr></li>
	 * <li>- IntermediaryAgent1 <IntrmyAgt1></li>
	 * </ul>
	 * 
	 * @param payments
	 *            The payments
	 * @return
	 */
	private List<CreditTransferTransactionInformation10> createCreditTransferTransactionInformation(Collection<TransferDTO> payments) {
		List<CreditTransferTransactionInformation10> creditTransferTransactions = new ArrayList<>();

		for (TransferDTO payment : payments) {
			CreditTransferTransactionInformation10 creditTransferTransaction = new CreditTransferTransactionInformation10();

			// creditTransferTransaction.setPmtTpInf(createPaymentTypeInformation());
			// PaymentIdentification
			creditTransferTransaction.setPmtId(createPaymentIdentification(payment));
			// Amount
			creditTransferTransaction.setAmt(cratePaymentAmount(payment));
			// For SEPA Credit Transfers only â€œSLEVâ€� is allowed.
			// creditTransferTransaction.setChrgBr(ChargeBearerType1Code.SLEV);
			// Creditor
			creditTransferTransaction.setCdtr(createCreditor(payment));
			// Creditor account
			creditTransferTransaction.setCdtrAcct(createCreditorAccount(payment));
			// CreditorAgent
			creditTransferTransaction.setCdtrAgt(createCreditorAgent(payment));
			// Remittanceinformation
			creditTransferTransaction.setRmtInf(createRemittanceinformation(payment));

			creditTransferTransactions.add(creditTransferTransaction);
		}

		return creditTransferTransactions;
	}

	/**
	 * Set of elements as a reference for a payment instruction. Mandatory.
	 * 
	 * @param payment
	 *            The payment.
	 * @return The payment identification
	 */
	private PaymentIdentification1 createPaymentIdentification(TransferDTO payment) {

		PaymentIdentification1 paymentIdentification = new PaymentIdentification1();

		/**
		 * Unique identification assigned by the initiating party in order to
		 * unambiguously identify each transaction. This identification is
		 * passed on, unchanged, throughout the entire end-to-end chain.
		 */
		paymentIdentification.setEndToEndId(WEBIA + "/" + payment.getTransferId());

		return paymentIdentification;
	}

	/**
	 * Amount of money to be moved between the debtor and creditor. Mandatory.
	 * <ul>
	 * <li>- Amount must be greater than 0.</li>
	 * <li>- The fractional part is linked to the currency (ISO 4217) and is
	 * less or equal than 5 digit.</li>
	 * <li>- A meaningless fractional part is not mandatory; i.e: â€œ10â€� can
	 * be written â€œ10â€� or â€œ10.â€� Or â€œ10.0â€� or â€œ10.00â€�.</li>
	 * <li>- Maximum length of this field is 18 characters (currency and decimal
	 * separator included).</li>
	 * </ul>
	 * 
	 * @param payment
	 *            The payment.
	 * @return The payment amount
	 */
	private AmountType3Choice cratePaymentAmount(TransferDTO payment) {

		if (payment.getTrfMt().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalStateException("Payment (id: " + payment.getTransferId() + ", amount: " + payment.getTrfMt()
					+ ") amount must be greater than 0.");
		}

		AmountType3Choice amount = new AmountType3Choice();

		ActiveOrHistoricCurrencyAndAmount amountAndCurrency = new ActiveOrHistoricCurrencyAndAmount();
		amountAndCurrency.setValue(payment.getTrfMt());
		amountAndCurrency.setCcy(payment.getTrfCurrency());

		amount.setInstdAmt(amountAndCurrency);

		return amount;
	}

	/**
	 * Party to which an amount of money is due.
	 * 
	 * @param payment
	 *            The payment.
	 * @return The creditor.
	 */
	private PartyIdentification32 createCreditor(TransferDTO payment) {
		PartyIdentification32 creditor = new PartyIdentification32();

		creditor.setNm(payment.getLibBenef());

		return creditor;
	}

	/**
	 * Unambiguous identification of the creditorâ€™s account to which a credit
	 * entry will be booked asa result of the payment transaction.
	 * 
	 * @param payment
	 *            The payment.
	 * @return The creditor account.
	 */
	private CashAccount16 createCreditorAccount(TransferDTO payment) {
		CashAccount16 creditorAccount = new CashAccount16();

		AccountIdentification4Choice accountIdentification = new AccountIdentification4Choice();

		accountIdentification.setIBAN(payment.getIbanBenef().replaceAll("\\s+", ""));

		creditorAccount.setId(accountIdentification);

		return creditorAccount;
	}

	/**
	 * Unique identification of the Financial institution servicing the account
	 * of the creditor.
	 * 
	 * @param payment
	 *            The payment.
	 * @return The creditor agent.
	 */
	private BranchAndFinancialInstitutionIdentification4 createCreditorAgent(TransferDTO payment) {
		BranchAndFinancialInstitutionIdentification4 creditorAgent = new BranchAndFinancialInstitutionIdentification4();

		FinancialInstitutionIdentification7 financialInstitutionId = new FinancialInstitutionIdentification7();

		financialInstitutionId.setBIC(payment.getSwiftBenef());

		creditorAgent.setFinInstnId(financialInstitutionId);

		return creditorAgent;
	}

	/**
	 * Information which makes it possible to match a payment with the items it
	 * is supposed to settle, e.g. commercial invoices within an account
	 * receivable system. This information can be structured or unstructured.
	 * 
	 * @param payment
	 *            The payment.
	 * @return The remittance information
	 */
	private RemittanceInformation5 createRemittanceinformation(TransferDTO payment) {
		RemittanceInformation5 remittanceInformation = new RemittanceInformation5();

		List<String> value = Arrays.asList(payment.getTrfComm());
		remittanceInformation.setUstrds(value);

		return remittanceInformation;
	}

}

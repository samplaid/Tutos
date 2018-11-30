package lu.wealins.webia.core.service.document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.FundTransactionDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.editing.common.webia.AmountType;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Endorsement;
import lu.wealins.editing.common.webia.Policy;
import lu.wealins.editing.common.webia.PolicyHolder;
import lu.wealins.editing.common.webia.SurrenderDetails;
import lu.wealins.editing.common.webia.SurrenderDetails.FiscalDataFrance;
import lu.wealins.editing.common.webia.SurrenderDetails.PayAccount;
import lu.wealins.webia.core.service.LiabilityTransactionService;
import lu.wealins.webia.core.service.WebiaTransactionTaxService;
import lu.wealins.webia.core.service.document.trait.PolicyBasedDocumentGenerationService;
import lu.wealins.webia.core.service.document.trait.TransactionFormBasedDocument;
import lu.wealins.webia.core.service.helper.FrenchTaxHelper;
import lu.wealins.webia.core.service.helper.IbanHelper;
import lu.wealins.webia.core.service.impl.PolicyDocumentService;
import lu.wealins.webia.core.service.impl.SurrenderDocumentGenerationServiceCommonImpl;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Service
public class TotalSurrenderDocumentGenerationService extends PolicyDocumentService implements PolicyBasedDocumentGenerationService, TransactionFormBasedDocument {

	private static final String PENALTY = "PENALTY";

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private LiabilityTransactionService transactionService;

	@Autowired
	private FrenchTaxHelper frenchTaxHelper;

	@Autowired
	private SurrenderDocumentGenerationServiceCommonImpl surrenderDocumentGenerationServiceCommonImpl;

	@Autowired
	private WebiaTransactionTaxService webiaTransactionTaxService;

	private static final Logger logger = LoggerFactory.getLogger(WithdrawalDocumentGenerationService.class);

	private static final Integer EVENT = 4;

	@Autowired
	private IbanHelper ibanHelper;

	@Override
	protected Document createSpecificDocument(EditingRequest editingRequest, PolicyDTO policyDTO, String productCountry, String language, String userId) {
		Endorsement endorsement = generateEndorsement(policyDTO, productCountry, editingRequest.getWorkflowItemId());
		Policy policy = endorsement.getPolicy();

		Document document = createPolicyDocument(editingRequest.getWorkflowItemId(), policyDTO, productCountry, language, policy, userId);
		document.setEndorsement(endorsement);
		return document;
	}

	private Endorsement generateEndorsement(PolicyDTO policyDTO, String productCountry, Long workflowItemId) {
		Endorsement endorsement = new Endorsement();

		TransactionFormDTO transactionFormDTO = retrieveTransactionForm(restClientUtils, workflowItemId);

		List<PolicyHolder> holders = generateHolders(policyDTO.getPolicyHolders());
		Policy policy = getXmlPolicy(policyDTO, holders);
		endorsement.setPolicy(policy);

		Collection<TransactionDTO> transactions = transactionService.getActiveTransactionByPolicyAndEventTypeAndDate(policyDTO.getPolId(), EVENT, transactionFormDTO.getEffectiveDate());
		Collection<FundTransactionDTO> fundTransactions = transactions.stream().map(x -> x.getFundTransactions()).flatMap(Set::stream).filter(x -> x.getEventType() == EVENT)
				.collect(Collectors.toList());

		// Disinvestment
		endorsement.setDisinvestments(generateDisinvestments(fundTransactions, policy));
		// situation
		endorsement.setSituations(getPolicyValuation(policy, transactions.stream().findAny().get().getEffectiveDate()));

		// EffectDate
		endorsement.setEffectDate(transactions.stream().findAny().get().getEffectiveDate());

		// endorsement.setEventId(transaction.getTrnId().toString());
		endorsement.setEventType(getDocumentType().name());

		// surrender
		endorsement.setSurrenderDetails(generateSurrenderDetails(fundTransactions, transactionFormDTO, policyDTO, transactions));


		return endorsement;
	}

	private SurrenderDetails generateSurrenderDetails(Collection<FundTransactionDTO> fundTransactions, TransactionFormDTO transactionFormDTO, PolicyDTO policyDTO,
			Collection<TransactionDTO> transactions) {
		SurrenderDetails surrenderDetails = new SurrenderDetails();
		// payAccount
		surrenderDetails.setPayAccounts(getPayAccounts(transactionFormDTO));
		// Total premium paid
		surrenderDetails.setTotalPremiumPaid(generateTotalpremiums(policyDTO));

		TransactionTaxDTO transactionTaxSelected = getTransactionTax(policyDTO, transactionFormDTO.getEffectiveDate());
		Collection<TransactionTaxDetailsDTO> transactiontaxDetails = webiaTransactionTaxService.getTransactionTaxDetails(transactionTaxSelected.getTransactionTaxId());

		// TODO surrenderAmount
		surrenderDetails.setAmount(getSurrenderAmout(transactionTaxSelected));
		// country
		if (policyDTO.getPolicyHolders().size() > 0) {
			surrenderDetails.setCountryOfResidence(policyDTO.getPolicyHolders().stream().findFirst().get().getHomeAddress().getCountry());
		}
		// Surrender fees
		surrenderDetails.setSurrenderFeesAmount(generateSurrenderFeesAmount(transactions));
		// contract management fees

		surrenderDetails.setManagementFeesAmount(generateManagementFees(transactions));

		// surrenderDetails.setCapitalGainsTax(generateCapitalGainsTax(transactionTaxSelected));

		// Valeur du contrat avant rachat partiel
		surrenderDetails.setContractValueBefore(generateContractValueBefore(transactionTaxSelected));

		// Plus/moins-values réalisées
		surrenderDetails.setEffectiveCapitalGains(generateEffectiveCapitalGains(transactiontaxDetails));

		//
		// surrenderDetails.setNetAmountPaidByInsurer(generateNetAmountPaidByInsurer(transactionTaxSelected));

		// Montant net transféré
		surrenderDetails.setNettoAmount(generateNettoAmount(transactionTaxSelected));

		// La part de la prime remboursée
		surrenderDetails.setReimbursedPremium(generateReimbursedPremium(transactiontaxDetails));

		// Montant total de la prime restante avant rachat partiel
		surrenderDetails.setRemainingPremiumBefore(generateRemainingPremiumBefore(transactiontaxDetails));

		surrenderDetails.setFiscalDataFrance(generateFiscalDataFrance(policyDTO, transactionTaxSelected));

		return surrenderDetails;
	}

	private AmountType generateRemainingPremiumBefore(Collection<TransactionTaxDetailsDTO> transactiontaxDetails) {
		AmountType amount = new AmountType();
		BigDecimal valueBefore = transactiontaxDetails
				.stream()
				.map(x -> x.getPremiumValueBefore())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		amount.setValue(valueBefore);

		return amount;
	}

	private AmountType generateReimbursedPremium(Collection<TransactionTaxDetailsDTO> transactiontaxDetails) {
		AmountType amount = new AmountType();
		BigDecimal valueBefore = transactiontaxDetails
				.stream()
				.map(x -> x.getPremiumValueBefore())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal valueAfter = transactiontaxDetails
				.stream()
				.map(x -> x.getPremiumValueAfter())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		amount.setValue(valueBefore.subtract(valueAfter));
		return amount;
	}

	private AmountType generateNettoAmount(TransactionTaxDTO transactionTaxSelected) {
		AmountType feeAmount = new AmountType();
		feeAmount.setValue(transactionTaxSelected.getTransactionNetAmount());
		feeAmount.setCurrencyCode(transactionTaxSelected.getCurrency());

		return feeAmount;
	}

	private AmountType generateNetAmountPaidByInsurer(TransactionTaxDTO transactionTaxSelected) {
		// TODO Auto-generated method stub
		return null;
	}

	private AmountType generateEffectiveCapitalGains(Collection<TransactionTaxDetailsDTO> transactiontaxDetails) {
		AmountType amount = new AmountType();
		BigDecimal capitalGain = transactiontaxDetails
				.stream()
				.filter(x -> x.getCapitalGainAmount() != null)
				.map(x -> x.getCapitalGainAmount())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		amount.setValue(capitalGain);

		return null;
	}

	private AmountType generateContractValueBefore(TransactionTaxDTO transactionTaxSelected) {
		AmountType amount = new AmountType();
		amount.setValue(transactionTaxSelected.getPolicyValue());
		amount.setCurrencyCode(transactionTaxSelected.getCurrency());
		return amount;
	}

	private AmountType generateCapitalGainsTax(TransactionTaxDTO transactionTaxSelected) {
		// TODO Auto-generated method stub
		return null;
	}

	private FiscalDataFrance generateFiscalDataFrance(PolicyDTO policyDTO, TransactionTaxDTO transactionTax) {

		if (frenchTaxHelper.isPolicyFrenchTaxable(policyDTO.getPolId()).isPresent()) {
			return surrenderDocumentGenerationServiceCommonImpl.generateFiscalDataFrance(transactionTax);
		} else{
			frenchTaxHelper.getTransactionTax(policyDTO.getPolId(), transactionTax.getTransactionDate(), false);
			return null;
		}
	}

	private TransactionTaxDTO getTransactionTax(PolicyDTO policyDTO, Date effectiveDate) {
		TransactionTaxDTO transactionTaxSelected = new TransactionTaxDTO();

		if (frenchTaxHelper.isPolicyFrenchTaxable(policyDTO.getPolId()).isPresent()) {
			transactionTaxSelected = frenchTaxHelper.getTransactionTax(policyDTO.getPolId(), effectiveDate, true);
		} else {
			transactionTaxSelected = frenchTaxHelper.getTransactionTax(policyDTO.getPolId(), effectiveDate, false);
		}

		return transactionTaxSelected;

	}

	private AmountType generateManagementFees(Collection<TransactionDTO> transactions) {
		AmountType amountType = new AmountType();
		List<Long> transactionIds = transactions.stream().map(x -> x.getTrnId()).collect(Collectors.toList());
		BigDecimal amount = BigDecimal.ZERO;
		for (Long id : transactionIds) {

			Collection<TransactionDTO> linkedTransactions = transactionService.getActiveLinkedTransactions(id)
					.stream()
					.filter(x -> x.getStatus() == 12 || x.getStatus() == 13)
					.collect(Collectors.toList());
			if (linkedTransactions.size() > 0) {
				amount = linkedTransactions
						.stream()
						.map(x -> x.getValue0())
						.reduce(BigDecimal.ZERO, BigDecimal::add)
						.add(amount);
			}
		}
		amountType.setValue(amount);
		amountType.setCurrencyCode(transactions.stream().findAny().get().getCurrency());

		return amountType;
	}

	private AmountType generateSurrenderFeesAmount(Collection<TransactionDTO> transactions) {
		AmountType feeAmount = new AmountType();
		if (transactions.size() > 0) {
			feeAmount.setValue(transactions.stream()
					.map(x -> x.getAccountTransactions())
					.flatMap(Set::stream)
					.filter(x -> StringUtils.compareIgnoreCase(x.getAccount().trim(), PENALTY) == 0)
					.map(x -> x.getAmount())
					.reduce(BigDecimal.ZERO, BigDecimal::add));
			feeAmount.setCurrencyCode(transactions.stream().findAny().get().getCurrency());
		}
		return feeAmount;
	}

	private AmountType getSurrenderAmout(TransactionTaxDTO transactionTaxSelected) {
		AmountType amount = new AmountType();
		BigDecimal result = transactionTaxSelected.getPolicyValue();
		amount.setValue(result);
		amount.setCurrencyCode(transactionTaxSelected.getCurrency());
		return amount;
	}

	private AmountType generateTotalpremiums(PolicyDTO policyDTO) {
		AmountType amount = new AmountType();
		BigDecimal total = policyDTO.getPolicyPremiums().stream().map(x -> x.getModalPremium()).reduce(BigDecimal.ZERO, BigDecimal::add);
		amount.setValue(total);
		amount.setCurrencyCode(policyDTO.getCurrency());
		return amount;
	}

	private List<PayAccount> getPayAccounts(TransactionFormDTO transactionFormDTO) {
		List<PayAccount> accountList = new ArrayList<SurrenderDetails.PayAccount>();

		if (transactionFormDTO.getPayments() != null) {
			for (TransferDTO transfer : transactionFormDTO.getPayments()) {
				PayAccount account = new PayAccount();
				account.setAccountHolder(transfer.getLibBenef());
				account.setAccountNumber(ibanHelper.formatIban(transfer.getIbanBenef()));
				// TODO: add the bank
				account.setBank(null);
				accountList.add(account);
			}
		}

		return accountList;
	}

	@Override
	protected DocumentType getDocumentType() {
		return DocumentType.TOTAL_SURRENDER;
	}

	@Override
	protected String getXmlPath(EditingRequest editingRequest, String policyId) {
		return buildXmlPath(policyId, filePath);
	}

}

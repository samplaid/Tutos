package lu.wealins.webia.core.service.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.enums.TransferStatus;
import lu.wealins.editing.common.webia.Account;
import lu.wealins.editing.common.webia.AccountingNote;
import lu.wealins.editing.common.webia.AmountType;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Policy;
import lu.wealins.webia.core.service.WebiaTransactionFormService;
import lu.wealins.webia.core.service.document.trait.PolicyBasedDocumentGenerationService;
import lu.wealins.webia.core.service.helper.IbanHelper;
import lu.wealins.webia.core.service.helper.TransferIdsHelper;
import lu.wealins.webia.core.service.impl.PolicyDocumentService;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Service
public class WithdrawalAccountingNoteDocumentGenerationService extends PolicyDocumentService implements PolicyBasedDocumentGenerationService {

	private static final String TRANSACTION_TYPE = "Paiement rachat partiel par virement";

	@Autowired
	private WebiaTransactionFormService transactionFormService;

	@Autowired
	private IbanHelper ibanHelper;

	@Autowired
	private TransferIdsHelper transferIdsHelper;

	@Override
	protected Document createSpecificDocument(EditingRequest editingRequest, PolicyDTO policyDTO, String productCountry, String language, String userId) {

		List<AccountingNote> accountingNotes = generateAccountingNotes(policyDTO, editingRequest.getWorkflowItemId(), editingRequest.getSimulation(), editingRequest.getTransferIds());

		Policy policy = getXmlPolicy(policyDTO, new ArrayList<>()); // TODO decide if policy holders are required or not...

		Document document = createPolicyDocument(editingRequest.getWorkflowItemId(), policyDTO, productCountry, language, policy, userId);
		document.setAccountingNotes(accountingNotes);
		return document;
	}

	private List<AccountingNote> generateAccountingNotes(PolicyDTO policyDTO, Long workflowItemId, Boolean isSimulation, String transferIds) {
		List<AccountingNote> accountingNotes = new ArrayList<>();

		TransactionFormDTO transactionFormDTO = transactionFormService.getFormData(Integer.valueOf(workflowItemId.intValue()));

		try {

			// keep only the transfer having the status ready
			Collection<TransferDTO> transferReady;
			if (Boolean.TRUE.equals(isSimulation)) {
				transferReady = transactionFormDTO.getPayments().stream()
						.filter(transfer -> TransferStatus.READY.name().equals(transfer.getTransferStatus()))
						.collect(Collectors.toList());
			} else {
				List<Long> transferIdsList = transferIdsHelper.mapStringToIds(transferIds);
				transferReady = transactionFormDTO.getPayments().stream()
						.filter(transfer -> transferIdsList.contains(transfer.getTransferId()))
						.collect(Collectors.toList());
			}
			
			for (TransferDTO transferDTO : transferReady) {

				AccountingNote accountingNote = new AccountingNote();

				// amount
				AmountType amountType = new AmountType();
				amountType.setValue(transferDTO.getTrfMt());
				amountType.setCurrencyCode(transferDTO.getTrfCurrency());
				accountingNote.setAmount(amountType);

				// source
				Account sourceAccount = new Account();
				sourceAccount.setBIC(transferDTO.getSwiftDonOrd());
				sourceAccount.setIban(ibanHelper.formatIban(transferDTO.getIbanDonOrd()));
				sourceAccount.setAccountNumber(ibanHelper.formatIban(transferDTO.getIbanDonOrd()));
				sourceAccount.setHolder(transferDTO.getLibDonOrd());
				accountingNote.setSourceAccount(sourceAccount);

				// destination
				Account destinationAccount = new Account();
				destinationAccount.setBIC(transferDTO.getSwiftBenef());
				destinationAccount.setIban(ibanHelper.formatIban(transferDTO.getIbanBenef()));
				destinationAccount.setAccountNumber(ibanHelper.formatIban(transferDTO.getIbanBenef()));
				destinationAccount.setHolder(transferDTO.getLibBenef());
				accountingNote.setDestinationAccount(destinationAccount);

				// users and dates
				accountingNote.setCPS1Trigram(transferDTO.getUserCps1());
				accountingNote.setCPS1Date(transferDTO.getCps1Dt());
				accountingNote.setValidTrigram(transferDTO.getUserCps2());
				accountingNote.setValidDate(transferDTO.getCps2Dt());

				accountingNote.setCommunication(getCommunication(transferDTO));
				accountingNote.setTransactionType(TRANSACTION_TYPE);

				accountingNote.setValueDate(transactionFormDTO.getEffectiveDate());
				accountingNote.setItem(workflowItemId.toString());
				accountingNote.setPolicyId(policyDTO.getPolId());
				accountingNote.setFundId(transferDTO.getFdsId());

				if (transferDTO.getTrfMt() != null) {
					// if (transactionFormDTO.getTransactionFees() != null) {
					// AmountType mgtFeesAmount = new AmountType();
					// mgtFeesAmount.setValue(transferDTO.getTrfMt().multiply(transactionFormDTO.getBrokerTransactionFees()).divide(BigDecimal.valueOf(100)));
					// mgtFeesAmount.setCurrencyCode(transferDTO.getTrfCurrency());
					// accountingNote.setManagementFeesAmount(mgtFeesAmount);
					// }
					//
					// if (transactionFormDTO.getBrokerTransactionFees() != null) {
					// AmountType withdrawalFeesAmount = new AmountType();
					// withdrawalFeesAmount.setValue(transferDTO.getTrfMt().multiply(transactionFormDTO.getTransactionFees()).divide(BigDecimal.valueOf(100)));
					// withdrawalFeesAmount.setCurrencyCode(transferDTO.getTrfCurrency());
					// accountingNote.setWithdrawalFeesAmount(withdrawalFeesAmount);
					// }
				}

				accountingNotes.add(accountingNote);
			}
		} catch (Exception e) {
			log.error("Error during the generation of the accounting note. policy: " + policyDTO.getPolId(), e);
		}

		return accountingNotes;
	}

	private String getCommunication(TransferDTO transferDTO) {
		String policyOut = transferDTO.getPolicyOut();
		String trfComm = transferDTO.getTrfComm();
		return StringUtils.join(trfComm, " ", policyOut);		
	}

	@Override
	protected DocumentType getDocumentType() {
		return DocumentType.WITHDRAWAL_ACCOUNTING_NOTE;
	}

	@Override
	protected String getXmlPath(EditingRequest editingRequest, String policyId) {
		return buildXmlPath(policyId, filePath);
	}
}

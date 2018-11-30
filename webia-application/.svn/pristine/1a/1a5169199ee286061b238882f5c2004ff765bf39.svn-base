package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.PaymentMethodsDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderLiteDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.liability.services.enums.PaymentMethod;
import lu.wealins.common.dto.webia.services.AccountPaymentDTO;
import lu.wealins.common.dto.webia.services.SepaDocumentRequest;
import lu.wealins.common.dto.webia.services.SurrenderTransferFormDataDTO;
import lu.wealins.common.dto.webia.services.TransferCandidate;
import lu.wealins.common.dto.webia.services.TransferComptaDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.TransferExecutionRequest;
import lu.wealins.common.dto.webia.services.TransferRefuseDTO;
import lu.wealins.common.dto.webia.services.TransferSecurityDTO;
import lu.wealins.common.dto.webia.services.WithdrawalTransferFormDataDTO;
import lu.wealins.common.dto.webia.services.enums.PaymentType;
import lu.wealins.common.dto.webia.services.enums.TransferCd;
import lu.wealins.common.dto.webia.services.enums.TransferStatus;
import lu.wealins.webia.core.mapper.TransferMapper;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.LiabilityAgentContactService;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.SynchronousDocumentService;
import lu.wealins.webia.core.service.TransferService;
import lu.wealins.webia.core.service.WebiaAccountPaymentService;
import lu.wealins.webia.core.service.helper.SEPAHelper;
import lu.wealins.webia.core.service.helper.SwiftHelper;
import lu.wealins.webia.core.service.helper.TransferIdsHelper;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.impl.exception.AccountingException;
import lu.wealins.webia.ws.rest.impl.exception.ReportExceptionHelper;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Service
public class TransferServiceImpl implements TransferService {
	
	private static final String WEALINS_S_A = "WEALINS S.A.";
	private static final String TRANSFER_ID_CANT_BE_NULL = "The transfer id can't be null";
	private static final String TRANSFER_REFUSE_DTO_CANT_BE_NULL = "The transfer refuse comment can't be null";
	
	private static final String WEBIA_TRANSFERS = "webia/transfer/";
	private static final String WEALINS = WEALINS_S_A;
	private static final String LIST = "list";
	private static final String UPDATE = "update";
	private static final String KEY_SEPARATOR = "~";

	@Autowired
	private LiabilityPolicyService policyService;
	
	@Autowired
	private LiabilityAgentContactService liabilityAgentContactService;
	
	@Autowired
	private LiabilityFundService fundService;

	@Autowired
	private LiabilityAgentService liabilityAgentService;

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private SynchronousDocumentService synchronousDocumentService;

	@Autowired
	private EditingService editingService;
	
	@Autowired
	private TransferMapper transferMapper;

	@Autowired
	private WebiaAccountPaymentService accountPaymentService;

	private static final Logger log = LoggerFactory.getLogger(TransferServiceImpl.class);

	@Autowired
	private SEPAHelper sepaHelper;
	
	@Autowired
	private SwiftHelper swiftHelper;

	@Autowired
	private TransferIdsHelper transferIdsHelper;

	private TransferDTO initCashTransferForWithdrawal(Integer workflowItemId, PolicyLightDTO policyLight) {

		TransferDTO cashTransfer = createCashTransfer(workflowItemId, policyLight, TransferCd.WITHDRAWAL);
		cashTransfer.setTrfComm("Partial surrender");

		return cashTransfer;
	}

	private TransferDTO createCashTransfer(Integer workflowItemId, PolicyLightDTO policyLight, TransferCd transferCd) {
		TransferDTO cashTransfer = createTransfer(workflowItemId, policyLight, transferCd);
		cashTransfer.setTransferType(PaymentType.CASH_TRANSFER.toString());
		return cashTransfer;
	}

	private TransferDTO initCashTransferForSurrender(Integer workflowItemId, PolicyLightDTO policyLight) {

		TransferDTO cashTransfer = createCashTransfer(workflowItemId, policyLight, TransferCd.SURRENDER);
		cashTransfer.setTrfComm("Surrender");

		return cashTransfer;
	}

	private TransferDTO initSecuritiesTransferForWithdrawal(Integer workflowItemId, PolicyLightDTO policyLight) {

		TransferDTO securitiesTransfer = createTransfer(workflowItemId, policyLight, TransferCd.WITHDRAWAL);
		securitiesTransfer.setTrfComm("Securities transfer");
		securitiesTransfer.setTransferType(PaymentType.SECURITY_TRANSFER.toString());
		securitiesTransfer.setTransferSecurities(Arrays.asList(new TransferSecurityDTO()));

		return securitiesTransfer;
	}

	@Override
	public TransferDTO initSecuritiesTransferForSurrender(Integer workflowItemId, FundLiteDTO fund) {

		PolicyLightDTO policyLight = policyService.getPolicyLight(workflowItemId);

		TransferDTO securitiesTransfer = createTransfer(workflowItemId, policyLight, TransferCd.SURRENDER);
		securitiesTransfer.setTrfComm("Surrender");
		securitiesTransfer.setTransferType(PaymentType.SECURITY_TRANSFER.toString());
		securitiesTransfer.setTransferSecurities(Arrays.asList(new TransferSecurityDTO()));

		if (fund != null) {
			if (fundService.isFIDorFAS(fund)) {
				securitiesTransfer.setLibDonOrd(WEALINS);
			} else {
				securitiesTransfer.setLibDonOrd(fund.getAccountRoot());
			}
			securitiesTransfer.setIbanDonOrd(fund.getAccountRoot());
			securitiesTransfer.setSwiftDonOrd(getBankSwift(fund.getDepositBank()));
			securitiesTransfer.setTrfCurrency(fund.getCurrency());
			securitiesTransfer.setFdsId(fund.getFdsId());
			securitiesTransfer.setPolicyId(policyLight.getPolId());
		}
		return securitiesTransfer;
	}

	@Override
	public TransferDTO createAdminFeesTransfer(Integer workflowItemId, String policyId, BigDecimal amount, String currency) {
		TransferDTO cashTransfer = new TransferDTO();

		cashTransfer.setWorkflowItemId(workflowItemId);
		cashTransfer.setTransferStatus(TransferStatus.COMPTA.getCode());
		cashTransfer.setTransferCd(TransferCd.ADMINL_FEE.getCode());
		cashTransfer.setTrfCurrency(currency);
		cashTransfer.setTrfMt(amount);
		cashTransfer.setTrfDt(new Date());
		cashTransfer.setTrfComm("ADM fees " + policyId);
		cashTransfer.setTransferType(PaymentType.CASH_TRANSFER.toString());

		//
		cashTransfer.setIbanDonOrd("????");
		cashTransfer.setSwiftDonOrd("????");

		PolicyLightDTO policyLight = policyService.getPolicyLight(policyId);

		String beneficiaries = getPolicyHolders(policyLight);
		cashTransfer.setLibBenef(beneficiaries);

		return cashTransfer;
	}

	private TransferDTO createTransfer(Integer workflowItemId, PolicyLightDTO policyLight, TransferCd transferCd) {
		TransferDTO cashTransfer = new TransferDTO();

		cashTransfer.setWorkflowItemId(workflowItemId);
		cashTransfer.setTransferCd(transferCd.getCode());
		cashTransfer.setTrfCurrency(policyLight.getCurrency());
		String beneficiaries = getPolicyHolders(policyLight);
		cashTransfer.setLibBenef(beneficiaries);
		cashTransfer.setTransferStatus(TransferStatus.NEW.getCode());
		cashTransfer.setPolicyId(policyLight.getPolId());
		cashTransfer.setPolicyOut(policyLight.getPolId());

		return cashTransfer;
	}

	private String getBeneficiary(PolicyHolderLiteDTO policyHolder) {
		return new StringBuilder()
				.append(policyHolder.getName())
				.append(" ")
				.append(policyHolder.getFirstName())
				.toString();
	}

	private String getPolicyHolders(PolicyLightDTO policyLight) {
		if (CollectionUtils.isEmpty(policyLight.getPolicyHolders())) {
			return null;
		}

		return policyLight.getPolicyHolders().stream().map(this::getBeneficiary).collect(Collectors.joining(", "));
	}

	@Override
	public WithdrawalTransferFormDataDTO getWithdrawalFormData(Integer workflowItemId) {
		PolicyLightDTO policyLight = policyService.getPolicyLight(workflowItemId);

		WithdrawalTransferFormDataDTO formData = new WithdrawalTransferFormDataDTO();
		formData.setCashTransfer(initCashTransferForWithdrawal(workflowItemId, policyLight));
		formData.setSecuritiesTransfer(initSecuritiesTransferForWithdrawal(workflowItemId, policyLight));
		formData.setTransferCandidates(initTransferCandidates(policyLight));

		return formData;
	}

	@Override
	public SurrenderTransferFormDataDTO getSurrenderFormData(Integer workflowItemId) {
		PolicyLightDTO policyLight = policyService.getPolicyLight(workflowItemId);

		SurrenderTransferFormDataDTO formData = new SurrenderTransferFormDataDTO();
		formData.setCashTransfer(initCashTransferForSurrender(workflowItemId, policyLight));
		formData.setTransferCandidates(initTransferCandidates(policyLight));

		return formData;
	}

	private Collection<TransferCandidate> initTransferCandidates(PolicyLightDTO policyLight) {

		Collection<FundLiteDTO> investedFunds = fundService.getInvestedFunds(policyLight.getPolId());
		Collection<TransferCandidate> transferCandidates = new ArrayList<>();

		// process FID and FAS
		investedFunds.stream().filter(fund -> FundSubType.FID.name().equals(fund.getFundSubType()) || FundSubType.FAS.name().equals(fund.getFundSubType()))
				.map(fundLiteDTOtoTransferCandidate())
				.forEach(transferCandidate -> transferCandidates.add(transferCandidate));


		// process FE and FIC
		fundService.getFEorFICs(investedFunds).forEach(x -> transferCandidates.addAll(createTransferCandidates(x)));

		return removeDuplicates(transferCandidates);
	}

	private Collection<TransferCandidate> createTransferCandidates(FundLiteDTO fundLigth) {
		Assert.isTrue(fundLigth != null && fundLigth.getFdsId() != null, "Fund cannot be null.");
		FundDTO fund = fundService.getFund(fundLigth.getFdsId());
		Assert.notNull(fund, "Fund cannot be null with id :" + fundLigth.getFdsId() + ".");
		
		AgentDTO depositBank = liabilityAgentService.getAgent(fundLigth.getDepositBank());
		Assert.notNull(depositBank, "Deposit bank does not exist for the fund id " + fund.getFdsId() + ".");
		
		Collection<AccountPaymentDTO> accountPayments = accountPaymentService.getAccountPayments(fund.getFundSubType(), depositBank.getPaymentAccountBic(), fund.getDepositAccount());
		
		return accountPayments.stream().map(x -> {
			TransferCandidate tcWealins = new TransferCandidate();

			tcWealins.setFundId(fund.getFdsId());
			tcWealins.setFundSubType(fund.getFundSubType());
			tcWealins.setFundName(x.getAccountName() + " " + x.getCurrency());
			tcWealins.setIban(x.getIban());
			tcWealins.setCurrency(x.getCurrency());
			tcWealins.setBic(x.getBic());

			return tcWealins;
		}).collect(Collectors.toList());
	}

	private Collection<TransferCandidate> removeDuplicates(Collection<TransferCandidate> transferCandidates) {
		class Wrapper {
			TransferCandidate transferCandidate;

			Wrapper(TransferCandidate transferCandidate) {
				this.transferCandidate = transferCandidate;
			}

			TransferCandidate unwrap() {
				return transferCandidate;
			}

			@Override
			public boolean equals(Object other) {
				if (other instanceof Wrapper) {
					return ((Wrapper) other).transferCandidate.getFundName().equals(transferCandidate.getFundName());
				}
				return false;

			}

			@Override
			public int hashCode() {
				return transferCandidate.getFundName().hashCode();
			}

		}

		return transferCandidates.stream().map(x -> new Wrapper(x)).distinct().map(x -> x.unwrap()).collect(Collectors.toList());
	}

	private Function<? super FundLiteDTO, ? extends TransferCandidate> fundLiteDTOtoTransferCandidate() {
		return fund -> {
			TransferCandidate transferCandidate = new TransferCandidate();
			transferCandidate.setFundId(fund.getFdsId());
			transferCandidate.setFundSubType(fund.getFundSubType());
			transferCandidate.setFundName(fund.getAccountRoot() + " " + fund.getIban());
			if (fundService.isFIDorFAS(fund)) {
				transferCandidate.setLibDonOrd(WEALINS_S_A);
			} else {
				transferCandidate.setLibDonOrd(transferCandidate.getFundName());
			}
			transferCandidate.setIban(fund.getIban());
			transferCandidate.setCurrency(fund.getCurrency());
			transferCandidate.setBic(getBankSwift(fund.getDepositBank()));
			return transferCandidate;
		};
	}
	
	@Override
	public String getBankSwift(String depositBank) {
		if (StringUtils.isEmpty(depositBank)) {
			return "Not Available";
		}

		AgentDTO agentDTO = liabilityAgentService.getAgent(depositBank);
		return agentDTO != null ? agentDTO.getPaymentAccountBic() : "Not Specified";
	}

	@Override
	public Collection<TransferDTO> getTransfers(List<Long> ids){
		return restClientUtils.post(WEBIA_TRANSFERS + LIST, ids, new GenericType<Collection<TransferDTO>>() {
		});
	}
	
	@Override
	public <T extends TransferDTO> Collection<T> updateTransfers(List<T> transfers) {
		return restClientUtils.post(WEBIA_TRANSFERS + UPDATE, transfers, new GenericType<Collection<T>>() {
		});
	}

	@Override
	public HashMap<String, List<TransferDTO>> groupBySwiftRecipientContribCurrency(List<Long> ids) {
		HashMap<String, List<TransferDTO>> hashMap = new HashMap<String, List<TransferDTO>>();
		Collection<TransferDTO> transfers = getTransfers(ids);
		for (TransferDTO t : transfers) {
			String ibanBenef = t.getIbanBenef();  // group for a same beneficiary 
			String trfCurrency = t.getTrfCurrency(); // group for the same currency
			String swiftDonOrd = t.getSwiftDonOrd(); // group for the same bank 
			// group for the same contact in the bank
			String agentContactId = null;
			AgentContactLiteDTO paymentContact = liabilityAgentContactService.getFundPaymentContactAgent(t.getFdsId());
			AgentContactLiteDTO depositBankContact = liabilityAgentContactService.getFundDepositBankContactAgent(t.getFdsId());

			if (paymentContact != null) {
				agentContactId = paymentContact.getAgcId().toString();
			} else if (depositBankContact != null) {
				agentContactId = depositBankContact.getAgcId().toString();
			} else {
				ReportExceptionHelper.throwIfErrorsIsNotEmpty(Arrays.asList("There is no 'payment contact' definied for the fund " + t.getFdsId()), AccountingException.class);
			}
			
			String compositKey = agentContactId + KEY_SEPARATOR + swiftDonOrd + KEY_SEPARATOR + trfCurrency + KEY_SEPARATOR + ibanBenef;
			if (hashMap.containsKey(compositKey)){
				hashMap.get(compositKey).add(t);
			} else {
				List<TransferDTO> list = new ArrayList<TransferDTO>();
				list.add(t);
				hashMap.put(compositKey, list);
			}
		}
		return hashMap;
	}

	@Override
	public Collection<TransferComptaDTO> executeFax(SecurityContext context, TransferExecutionRequest transferExecutionRequest) {

		valiateTransferExecutionRequest(transferExecutionRequest);

		List<EditingRequest> faxEditingRequests = createFaxEditingRequests(transferExecutionRequest);

		return executeDocuments(context, faxEditingRequests);
	}

	@Override
	public Collection<TransferComptaDTO> executeSepa(SecurityContext context, SepaDocumentRequest sepaDocumentRequest) {
		Assert.notNull(sepaDocumentRequest);
		Assert.notEmpty(sepaDocumentRequest.getIds());

		List<EditingRequest> editingRequests = createSepaEditingRequests(sepaDocumentRequest);

		return executeDocuments(context, editingRequests);
	}

	private void valiateTransferExecutionRequest(TransferExecutionRequest transferExecutionRequest) {
		Assert.notNull(transferExecutionRequest);
		Assert.notEmpty(transferExecutionRequest.getIds());
	}

	private Collection<TransferComptaDTO> executeDocuments(SecurityContext context, List<EditingRequest> documentEditingRequests) {
		return documentEditingRequests.stream()
				.flatMap(editingRequest -> {
					EditingRequest editingResponse = synchronousDocumentService.createEditingSynchronously(context, editingRequest);
					return executeTransfers(editingResponse).stream();
				}).collect(Collectors.toList());
	}

	private List<EditingRequest> createFaxEditingRequests(TransferExecutionRequest transferExecutionRequest) {
		// Group the data by Swift code, contributor and currency to generate one fax for each of those aggregations
		return groupBySwiftRecipientContribCurrency(transferExecutionRequest.getIds())
				.entrySet()
				.stream()
				.map(entry -> {
					String[] split = entry.getKey().split(KEY_SEPARATOR);
					String groupedIds = transferIdsHelper.mapDtosToString(entry.getValue());
					return editingService.createFaxPaymentRequest(groupedIds, split[0]);
				})
				.collect(Collectors.toList());
	}

	private List<EditingRequest> createSepaEditingRequests(SepaDocumentRequest sepaDocumentRequest) {
		return sepaHelper.createSepaEditingRequests(sepaDocumentRequest);
	}
	private List<EditingRequest> createSwiftEditingRequests(TransferExecutionRequest transferExecutionRequest) {
		return swiftHelper.createSwiftEditingRequests(transferExecutionRequest);
	}
	
	private Collection<TransferComptaDTO> executeTransfers(EditingRequest editingResponse) {
		List<Long> transferIds = transferIdsHelper.mapStringToIds(editingResponse.getTransferIds());
		TransferExecutionRequest transferExecutionRequest = new TransferExecutionRequest();
		transferExecutionRequest.setIds(transferIds);
		transferExecutionRequest.setEditingId(editingResponse.getId());

		Collection<TransferDTO> transfers = restClientUtils.post(WEBIA_TRANSFERS + "execute", transferExecutionRequest, new GenericType<Collection<TransferDTO>>() {
		});

		return enrichTransfers(transfers);
	}

	@Override
	public boolean hasReady(Collection<TransferDTO> transfers) {
		if (transfers == null) {
			return false;
		}
		return transfers.stream().map(TransferDTO::getTransferStatus).anyMatch(TransferStatus.READY.getCode()::equals);
	}

	@Override
	public Collection<TransferComptaDTO> getComptaPayments() {
		Collection<TransferDTO> transfers = restClientUtils.get(WEBIA_TRANSFERS, "compta", new MultivaluedHashMap<>(), new GenericType<Collection<TransferDTO>>() {
		});

		return enrichTransfers(transfers);
	}

	@Override
	public Collection<TransferComptaDTO> accept(Long transferId) {
		Assert.notNull(transferId, TRANSFER_ID_CANT_BE_NULL);

		TransferDTO transfer = restClientUtils.post(WEBIA_TRANSFERS + transferId + "/accept", null, TransferDTO.class);

		TransferComptaDTO transferCompta = enrichTransfer(transfer);

		List<TransferComptaDTO> transfersCompta = new ArrayList<>();

		transfersCompta.add(transferCompta);

		if (PaymentMethod.FAX.equals(transferCompta.getMode())) {
			FundDTO fund = fundService.getFund(transferCompta.getFdsId());

			if (fundService.isFIDorFAS(fund)) {
				String bicDonOrd = transferCompta.getSwiftDonOrd();
				AccountPaymentDTO primeAccountPayment = accountPaymentService.getPrimeAccountPayment(fund.getFundSubType(), bicDonOrd, transferCompta.getTrfCurrency());

				Assert.notNull(primeAccountPayment, "Prime account cannot be null.");

				if (primeAccountPayment != null && bicDonOrd != null && bicDonOrd.equalsIgnoreCase(primeAccountPayment.getBic())) {
					transferCompta.setIbanBenef(primeAccountPayment.getIban());
					transferCompta.setLibBenef(primeAccountPayment.getAccountName());
					transferCompta.setSwiftBenef(primeAccountPayment.getBic());
					transferCompta.setTrfCurrency(primeAccountPayment.getCurrency());
				} else {
					TransferComptaDTO transferCompta1 = transferMapper.asTransferComptaDTO(transferCompta);

					transferCompta1.setTransferId(null);
					transferCompta1.setIbanBenef(primeAccountPayment.getIban());
					transferCompta1.setLibBenef(primeAccountPayment.getAccountName());
					transferCompta1.setSwiftBenef(primeAccountPayment.getBic());
					transferCompta1.setTransferStatus(TransferStatus.ACCEPTED.getCode());

					TransferComptaDTO transferCompta2 = transferMapper.asTransferComptaDTO(transferCompta);

					transferCompta2.setTransferId(null);
					transferCompta2.setIbanDonOrd(primeAccountPayment.getIban());
					transferCompta2.setLibDonOrd(primeAccountPayment.getAccountName());
					transferCompta2.setSwiftDonOrd(primeAccountPayment.getBic());
					transferCompta2.setTransferStatus(TransferStatus.ACCEPTED.getCode());

					transferCompta.setTransferStatus(TransferStatus.SPLITTED.getCode());

					transfersCompta.add(transferCompta1);
					transfersCompta.add(transferCompta2);
				}
				return updateTransfers(transfersCompta);
			}
		}

		return transfersCompta;

	}

	@Override
	public TransferComptaDTO refuse(Long transferId, TransferRefuseDTO refuseDTO) {
		Assert.notNull(transferId, TRANSFER_ID_CANT_BE_NULL);
		Assert.notNull(refuseDTO, TRANSFER_REFUSE_DTO_CANT_BE_NULL);

		TransferDTO transfer = restClientUtils.post(WEBIA_TRANSFERS + transferId + "/refuse", refuseDTO, TransferDTO.class);

		return enrichTransfer(transfer);
	}

	private Collection<TransferComptaDTO> enrichTransfers(Collection<TransferDTO> transfers) {
		if(CollectionUtils.isEmpty(transfers)) {
			return Collections.emptyList();
		}

		Map<String, PaymentMethod> transferModes = getTransferModes(transfers);
		return transfers.stream().map(transfer -> enrichTransfer(transfer, transferModes)).collect(Collectors.toList());
	}

	private TransferComptaDTO enrichTransfer(TransferDTO transfer) {
		Collection<TransferComptaDTO> enrichTransfers = enrichTransfers(Arrays.asList(transfer));
		return enrichTransfers.stream().findFirst().orElseThrow(() -> new IllegalStateException("Expected one transfer"));
	}

	private Map<String, PaymentMethod> getTransferModes(Collection<TransferDTO> transfers) {
		Set<String> fundIds = transfers.stream().filter(x -> x.getFdsId() != null).map(TransferDTO::getFdsId).collect(Collectors.toSet());

		Collector<PaymentMethodsDTO, ?, Map<String, PaymentMethod>> mapCollector = Collectors.toMap(paymentMethodDto -> paymentMethodDto.getFundId(),
				paymentMethodDto -> {
					PaymentMethod paymentMethod = PaymentMethod.toPaymentMethod(paymentMethodDto.getPaymentMethod());
					return paymentMethod == null ? PaymentMethod.FAX : paymentMethod;
				});

		return liabilityAgentService.getPaymentMethodsByFundIds(fundIds).stream().collect(mapCollector);
	}

	private TransferComptaDTO enrichTransfer(TransferDTO transfer, Map<String, PaymentMethod> transferModes) {
		TransferComptaDTO transferComptaDTO = transferMapper.asTransferComptaDTO(transfer);
		PaymentMethod transferMode = transferModes.get(transfer.getFdsId());
		if (transferMode == null) {
			// TODO: For FEs/FICs, wealins account --> payment mode... multiline?
			log.warn(String.format("Transfer mode not found for transfer %s related to 'fund' %s, default account is FAX.", transfer.getTransferId(), transfer.getFdsId()));
			transferMode = PaymentMethod.FAX;
		}
		transferComptaDTO.setMode(transferMode);
		return transferComptaDTO;
	}

	@Override
	public Collection<TransferComptaDTO> executeCsv(SecurityContext context,
			TransferExecutionRequest transferExecutionRequest) {
		Assert.notNull(transferExecutionRequest);
		Assert.notEmpty(transferExecutionRequest.getIds());

		List<EditingRequest> editingRequests = createSwiftEditingRequests(transferExecutionRequest);

		return executeDocuments(context, editingRequests);
	}
		
}

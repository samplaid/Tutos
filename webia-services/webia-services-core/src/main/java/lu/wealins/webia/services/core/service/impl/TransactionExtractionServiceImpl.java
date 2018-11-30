package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lu.wealins.webia.services.core.components.PstIdWrapper;
import lu.wealins.common.dto.webia.services.constantes.Constantes;
import lu.wealins.webia.services.core.persistence.entity.SapAccountingEntity;
import lu.wealins.webia.services.core.persistence.entity.SapMappingEntity;
import lu.wealins.webia.services.core.persistence.repository.SapAccountingRepository;
import lu.wealins.webia.services.core.persistence.repository.SapMappingRepository;
import lu.wealins.webia.services.core.service.TransactionExtractionService;
import lu.wealins.webia.services.core.utils.Helpers;
import lu.wealins.common.dto.webia.services.TransactionDTO;

@Service
public class TransactionExtractionServiceImpl implements TransactionExtractionService {
	private static final String ACCOUNT_7545001 = "7545001";
	
	private static final String ACCOUNT_6223002 = "6223002";

	private static final String SP_DEFAULT_PRODUIT = "SP";

	private static final String INIT_SUPPORT_CODE_98 = "98";

	private static final String EUR_VALUE = "500";

	private static final Logger logger = LoggerFactory.getLogger(TransactionExtractionServiceImpl.class);

	private static final char C_CREDIT = 'C';
	private static final char D_DEBIT = 'D';
	private static final String C_DEFAULT_PRODUIT = "IV";
	private static final String C_MAPPING_TYPE_ACCOUNT = "ACCOUNT";
	private static final String C_MAPPING_TYPE_CURRENCY = "CURRENCY";
	private static final String C_MAPPING_TYPE_FUND = "FUND_TYPE";
	@Autowired
	private SapAccountingRepository sapAccountingRepository;
	@Autowired
	private SapMappingRepository sapMappingRepository;

	@Override
	@Transactional
	public List<PstIdWrapper> processAccountTransactionsIntoSAPAccounting(Collection<TransactionDTO> transactionDTOs) {
		List<PstIdWrapper> pstIdToIds = new ArrayList<>();

		Collection<SapAccountingEntity> sapAccEntities = doMapToSapAccounting(transactionDTOs);
		sapAccEntities = sapAccEntities.stream().map(entity -> checkAndSave(entity)).collect(Collectors.toList());
		// get pstIds and linked SapAccountingEntity ids
		Map<String, List<SapAccountingEntity>> map = sapAccEntities.stream().collect(Collectors.groupingBy(SapAccountingEntity::getPstId));
		map.forEach((pstId, acc) -> {
			PstIdWrapper wrapper = new PstIdWrapper();
			wrapper.setPstId(String.valueOf(pstId));
			wrapper.setIdSapAccList(acc.stream().map(SapAccountingEntity::getIdSapAcc).collect(Collectors.toList()));
			pstIdToIds.add(wrapper);
		});
		return pstIdToIds;
	}

	private SapAccountingEntity checkAndSave(SapAccountingEntity sapAccEntity) {
		SapAccountingEntity entity = sapAccountingRepository.findByOriginIdAndAccount(sapAccEntity.getOriginId(), sapAccEntity.getAccount());
		if (entity == null)
			entity = sapAccountingRepository.save(sapAccEntity);
		entity.setPstId(sapAccEntity.getPstId());
		return entity;
	}

	private Collection<SapAccountingEntity> doMapToSapAccounting(Collection<TransactionDTO> transactionDTOs) {
		Map<Long, List<TransactionDTO>> groupTransactions = transactionDTOs.stream().collect(Collectors.groupingBy(TransactionDTO::getTransaction0));
		List<TransactionDTO> resultTransactions = new ArrayList<>();
		List<SapAccountingEntity> SapAccountingEntities = new ArrayList<>();
		List<SapMappingEntity> sapMappings = sapMappingRepository.findAll();
		// filter on mapped transactions
		groupTransactions.forEach((K, V) -> {
			resultTransactions.addAll(filterTransaction(V, sapMappings));
		});

		// get POSTING_SETS IDs and get SapAccountingEntity list
		resultTransactions.forEach(t -> {
			// convert to SapAccountingEntity
			SapAccountingEntities.add(asSapAccountingEntity(t));
		});

		return SapAccountingEntities;
	}

	private SapAccountingEntity asSapAccountingEntity(TransactionDTO transactionDTO) {
		SapAccountingEntity sapAccountingEntity = new SapAccountingEntity();
		String policy = Helpers.removeNoAlphaNumCharacter(transactionDTO.getPolicy());
		String centre = Helpers.removeNoAlphaNumCharacter(transactionDTO.getCentre());
		String polId = Helpers.removeNoAlphaNumCharacter(transactionDTO.getPolId());
		String eventType = String.valueOf(transactionDTO.getEventType());
		String produit = transactionDTO.getProduct();
		String fund = Helpers.removeNoAlphaNumCharacter(transactionDTO.getFund());
		String agent = Helpers.removeNoAlphaNumCharacter(transactionDTO.getAgent());
		String explain = String.format("%s %s %s", policy, eventType, centre == null ? "" : centre);
		sapAccountingEntity.setCompany(Constantes.LISSIA_TO_SAP_COMPANY);
		sapAccountingEntity.setPieceNb(String.valueOf(transactionDTO.getTransaction0()));
		sapAccountingEntity.setPiece(Constantes.LISSIA_TO_SAP_ACCOUNT_PIECETYPE);
		sapAccountingEntity.setCurrency(transactionDTO.getCurrency());
		sapAccountingEntity.setChangeRate(transactionDTO.getChangeRate());
		sapAccountingEntity.setAccount(transactionDTO.getAccountNbr());
		sapAccountingEntity.setAccountGeneral(Constantes.LISSIA_TO_SAP_ACCOUNT_GENERAL);
		sapAccountingEntity.setDebitCredit(String.valueOf(transactionDTO.getDbcr()));
		sapAccountingEntity.setAmount(transactionDTO.getAmount().setScale(2));
		sapAccountingEntity.setReconciliation(Helpers.truncate(policy, 50));
		sapAccountingEntity.setProduct(produit == null || produit.isEmpty() ? C_DEFAULT_PRODUIT : produit);
		
		if (transactionDTO.getEventType() == 13 && SP_DEFAULT_PRODUIT.equals(produit == null ? null : produit.trim()) && transactionDTO.getAccountNbr().contains(ACCOUNT_7545001)) {
			sapAccountingEntity.setAccount(transactionDTO.getAccountNbr().replace(ACCOUNT_7545001, ACCOUNT_6223002));
		} else {
			sapAccountingEntity.setAccount(transactionDTO.getAccountNbr());
		}
		
		sapAccountingEntity.setPolicy(Helpers.truncate(polId, 11));
		sapAccountingEntity.setAgent(Helpers.truncate(agent, 8));
		sapAccountingEntity.setCountry(transactionDTO.getCountry());
		sapAccountingEntity.setCountryOfProduct(transactionDTO.getNlCountry());
		 
		if ((transactionDTO.getAccountNbr().startsWith("6") || transactionDTO.getAccountNbr().startsWith("7") || transactionDTO.getAccountNbr().startsWith("8"))) {
			sapAccountingEntity.setSupport(INIT_SUPPORT_CODE_98);
		} else {
			// Init support code. Temporary request added 05/09/17
			if (transactionDTO.getSupport() == null || (transactionDTO.getSupport() != null && transactionDTO.getSupport().trim().length() > 0)) {
				if (Helpers.truncate(agent, 8) != null && Helpers.truncate(agent, 8).trim().length() > 0) {
					sapAccountingEntity.setSupport(INIT_SUPPORT_CODE_98);
				}
			} else {
				sapAccountingEntity.setSupport(transactionDTO.getSupport());
			}
		}
		
		sapAccountingEntity.setFund(Helpers.truncate(fund, 8));
		sapAccountingEntity.setOrigin(Constantes.LISSIA_TO_SAP_ORIGIN);
		sapAccountingEntity.setAccountDate(transactionDTO.getEffectiveDate());
		sapAccountingEntity.setCreationDate(new Date());
		sapAccountingEntity.setExplain(Helpers.truncate(explain, 50));
		sapAccountingEntity.setStatusCD(transactionDTO.getStatusCD());
		sapAccountingEntity.setExportDate(null);
		sapAccountingEntity.setPstId(String.valueOf(transactionDTO.getPstId()));
		sapAccountingEntity.setOriginId(transactionDTO.getAtrId());
		return sapAccountingEntity;
	}

	private List<TransactionDTO> filterTransaction(List<TransactionDTO> subTransactionDTOs, List<SapMappingEntity> mappings) {
		List<TransactionDTO> filteredTransactions = null;
		BigDecimal amountIn = new BigDecimal(0),
				amountOut = new BigDecimal(0);
		if (subTransactionDTOs == null || subTransactionDTOs.isEmpty() || mappings == null)
			return filteredTransactions;
		filteredTransactions = new ArrayList<>();
		for (TransactionDTO t : subTransactionDTOs) {
			// check transactions is mapped
			List<String> accountNbr = getAccountNumberByTypeAccountAndCurrency(t.getAccount() + "_" + t.getEventSubType(), t.getCurrency(), mappings);
			if (!CollectionUtils.isEmpty(accountNbr)) {
				// total debit and credit amount
				if (t.getDbcr() == C_CREDIT)
					amountIn.add(t.getAmount());
				else if (t.getDbcr() == D_DEBIT)
					amountOut.add(t.getAmount());
				// check sum debit equal to credit
				if (amountIn.compareTo(amountOut) != 0)
					t.setStatusCD(Constantes.LISSIA_TO_SAP_NOT_EQUAL);
				else
					t.setStatusCD(Constantes.LISSIA_TO_SAP_EQUAL);
				t.setSupport(getSupportByFundSubType(t.getFundSubType(), mappings));

				for (String acc : accountNbr) {
					TransactionDTO copyCurrent = new TransactionDTO();
					BeanUtils.copyProperties(t, copyCurrent);
					copyCurrent.setAccountNbr(acc);

					filteredTransactions.add(copyCurrent);
				}
			}
		}
		return filteredTransactions;
	}

	private List<String> getAccountNumberByTypeAccountAndCurrency(String account, String currency, List<SapMappingEntity> mappings) {
		if (mappings == null)
			return null;
		
		Map<String, List<SapMappingEntity>> filteredEntities = mappings.stream()
				.filter(t -> t.getType().trim().equalsIgnoreCase(C_MAPPING_TYPE_ACCOUNT) && t.getDataIn().trim().equalsIgnoreCase(account)
						|| t.getType().trim().equalsIgnoreCase(C_MAPPING_TYPE_CURRENCY) && t.getDataIn().trim().equalsIgnoreCase(currency))
				.collect(Collectors.groupingBy(SapMappingEntity::getType));

		if (filteredEntities == null || filteredEntities.size() != 2)
			return null;
		List<String> result = new ArrayList<String>();

		filteredEntities.get(C_MAPPING_TYPE_ACCOUNT).forEach((t) -> {
			if ((t.getDataOut().startsWith("6") || t.getDataOut().startsWith("7") || t.getDataOut().startsWith("8"))) {
				result.add(t.getDataOut().concat(EUR_VALUE));
			} else {
				result.add(t.getDataOut().concat(filteredEntities.get(C_MAPPING_TYPE_CURRENCY).get(0).getDataOut()));
			}
		});

		return result;
	}

	private String getSupportByFundSubType(String fundSubType, List<SapMappingEntity> mappings) {
		if (mappings == null)
			return null;
		Optional<SapMappingEntity> support = mappings.stream().filter(t -> t.getType().trim().equalsIgnoreCase(C_MAPPING_TYPE_FUND) && t.getDataIn().equalsIgnoreCase(fundSubType)).findFirst();
		return support.orElseGet(() -> new SapMappingEntity()).getDataOut();
	}

	
	@Transactional
	@Override
	public Long removeTransactionsFromSAPAccounting(List<Long> sapAccIds) {
		logger.info("remove transactions from sap accounting");
		sapAccountingRepository.deleteSapAccountingsWithIds(sapAccIds);
		return (long) sapAccIds.size();
	}
}

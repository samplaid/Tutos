package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.webia.services.SurrenderReportResultDTO;
import lu.wealins.common.dto.webia.services.SurrenderReportResultDetailsDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxSendingDTO;
import lu.wealins.common.dto.webia.services.enums.TransactionTaxOrigin;
import lu.wealins.common.dto.webia.services.enums.TransactionTaxStatus;
import lu.wealins.common.dto.webia.services.enums.TransactionTaxType;
import lu.wealins.webia.services.core.mapper.TransactionTaxMapper;
import lu.wealins.webia.services.core.mapper.TransactionTaxSendingMapper;
import lu.wealins.webia.services.core.persistence.entity.TransactionTaxEntity;
import lu.wealins.webia.services.core.persistence.entity.TransactionTaxSendingEntity;
import lu.wealins.webia.services.core.persistence.repository.TransactionTaxRepository;
import lu.wealins.webia.services.core.persistence.repository.TransactionTaxSendingRepository;
import lu.wealins.webia.services.core.service.TransactionTaxService;
import lu.wealins.webia.services.core.utils.SurrenderResultUtils;


@Service
@Transactional
public class TransactionTaxServiceImpl implements TransactionTaxService {

	@Autowired
	private TransactionTaxRepository transactionTaxRepository;

	@Autowired
	private TransactionTaxSendingRepository transactionTaxSendingRepository;

	@Autowired
	private TransactionTaxMapper transactionMapper;

	@Autowired
	private TransactionTaxSendingMapper transactionSendingMapper;

	@Autowired
	SurrenderResultUtils surrenderResultUtils;

	@Override
	public void markAsUpdated(long id) {
		TransactionTaxEntity entity = transactionTaxRepository.findOne(id);
		TransactionTaxStatus status = getNextStatus(entity);
		entity.setStatus(status.getStatusNumber());
	}

	@Override
	@Transactional
	public List<TransactionTaxDTO> getNewTransactions() {
		List<Integer> newTransactionStatusList = Arrays
				.asList(
				TransactionTaxStatus.CALCULATED_EDITION.getStatusNumber());
		
		List<TransactionTaxEntity> entities = transactionTaxRepository
				.findAllByStatusInAndOrigin(newTransactionStatusList, TransactionTaxOrigin.DALI.name());


		if (entities == null || entities.isEmpty()) {
			return new ArrayList<TransactionTaxDTO>();
		}
		return transactionMapper.asTransactionTaxDTOs(entities);
	}


	@Override
	public List<TransactionTaxDTO> insertTransactionTax(List<TransactionTaxDTO> transactionTaxList) {

		List<TransactionTaxEntity> entitiesList = transactionMapper.asTransactionTaxEntities(transactionTaxList);
		List<TransactionTaxEntity> entitiesListInserted = new ArrayList<TransactionTaxEntity>();
		TransactionTaxEntity previousTransactionTaxId = null;

		for (TransactionTaxEntity entity : entitiesList) {
			TransactionTaxEntity existedTransactionTax = transactionTaxRepository.findByOriginId(entity.getOriginId());

			if (existedTransactionTax == null) {
				entity.setPreviousTransactionTax(previousTransactionTaxId);
				entity = transactionTaxRepository.save(entity);
				previousTransactionTaxId = entity;
				entitiesListInserted.add(entity);
			} else {
				previousTransactionTaxId = existedTransactionTax;
			}

		}

		return transactionMapper.asTransactionTaxDTOs(entitiesListInserted);
	}

	@Override
	public TransactionTaxDTO getTransactionTax(Long id) {
		TransactionTaxEntity transactionTaxEntity = transactionTaxRepository.findOne(id);
		if (transactionTaxEntity != null) {
			return transactionMapper.asTransactionTaxDTO(transactionTaxEntity);
		}
		return null;
	}

	@Override
	public List<Long> filterCalculated(List<Long> transactionTaxIds) {
		return transactionTaxRepository
				.findAllByIdInAndStatus(transactionTaxIds, TransactionTaxStatus.CALCULATED_EDITION.getStatusNumber())
				.stream()
				.map(TransactionTaxEntity::getId)
				.collect(Collectors.toList());
	}

	@Override
	public List<TransactionTaxDTO> cancelTransactionTax(List<TransactionTaxDTO> transactionTaxList) {
		// List<TransactionTaxEntity> entitiesList = transactionMapper.asTransactionTaxEntities(transactionTaxList);
		List<TransactionTaxEntity> entitiesListCancelled = new ArrayList<TransactionTaxEntity>();

		for (TransactionTaxDTO entityDto : transactionTaxList) {

			if (null != transactionTaxRepository.findByOriginId(BigDecimal.valueOf(entityDto.getOriginId()))) {
				TransactionTaxEntity entity = transactionTaxRepository.findByOriginId(BigDecimal.valueOf(entityDto.getOriginId()));
				entity.setStatus(TransactionTaxStatus.CANCELLED.getStatusNumber());

				entitiesListCancelled.add(transactionTaxRepository.save(entity));
			}

		}

		return transactionMapper.asTransactionTaxDTOs(entitiesListCancelled);
	}

	private TransactionTaxStatus getNextStatus(TransactionTaxEntity entity) {
		return TransactionTaxStatus.NEW;
	}

	@Override
	public List<TransactionTaxDTO> getTransactionsTaxByPolicy(String policy) {
		return transactionMapper.asTransactionTaxDTOs(transactionTaxRepository.findByPolicy(policy));
	}

	@Override
	public TransactionTaxDTO getTransactionTaxByOriginId(Long id) {
		TransactionTaxEntity entity = transactionTaxRepository.findByOriginId(BigDecimal.valueOf(id));
		return transactionMapper.asTransactionTaxDTO(entity);
	}

	@Override
	public SurrenderReportResultDTO getTransactionsTaxReportResult(Long transactionTaxId) {
		SurrenderReportResultDTO surrenderReportResult = new SurrenderReportResultDTO();

		List<SurrenderReportResultDetailsDTO> details = surrenderResultUtils
				.retrieveSurrenderReportDetails(transactionTaxId);

		surrenderReportResult.setSurrenderReportDetails(details);

		Map<String, BigDecimal> resultTotal = surrenderResultUtils.retrieveSurrenderReportTotal(transactionTaxId);

		if (resultTotal != null && !resultTotal.isEmpty()) {
			surrenderReportResult.setTotalPremium(resultTotal.get(TransactionTaxType.PREM.name()));
			BigDecimal TotalWith = resultTotal.get(TransactionTaxType.WITH.name());
			BigDecimal TotalSurr = resultTotal.get(TransactionTaxType.SURR.name());
			BigDecimal TotalMatu = resultTotal.get(TransactionTaxType.MATU.name());
			BigDecimal totalSurrender = BigDecimal.ZERO.add(TotalWith == null ? BigDecimal.ZERO : TotalWith)
					.add(TotalSurr == null ? BigDecimal.ZERO : TotalSurr)
					.add(TotalMatu == null ? BigDecimal.ZERO : TotalMatu);
			surrenderReportResult.setTotalWithdrawal(totalSurrender);
		}

		return surrenderReportResult;
	}

	@Override
	public List<Long> updateTransactions(List<Long> transactionTaxIds) {

		List<Long> entitiesListUpdated = new ArrayList<Long>();

		for (Long transactionTaxId : transactionTaxIds) {
			if (null != transactionTaxRepository.findOne(transactionTaxId)) {
				TransactionTaxEntity entity = transactionTaxRepository.findOne(transactionTaxId);
				entity.setStatus(TransactionTaxStatus.NEW.getStatusNumber());
				transactionTaxRepository.save(entity);
				entitiesListUpdated.add(entity.getId());
			}
		}
		return entitiesListUpdated;
	}

	@Override
	public TransactionTaxSendingDTO getTransactionTaxSending(Long id) {
		List<TransactionTaxSendingEntity> TransactionTaxSendings = transactionTaxSendingRepository
				.findAllByTransactionTaxId(id);
		if (TransactionTaxSendings != null && !TransactionTaxSendings.isEmpty()) {
			return transactionSendingMapper.asTransactionTaxDetailsDTO(TransactionTaxSendings.get(0));
		}

		return new TransactionTaxSendingDTO();
	}

}

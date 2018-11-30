
package lu.wealins.webia.services.core.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.TransferExecutionRequest;
import lu.wealins.common.dto.webia.services.TransferRefuseDTO;
import lu.wealins.common.dto.webia.services.enums.PaymentType;
import lu.wealins.common.dto.webia.services.enums.TransferCd;
import lu.wealins.common.dto.webia.services.enums.TransferStatus;
import lu.wealins.common.security.SecurityContextHelper;
import lu.wealins.webia.services.core.exceptions.InvalideActionException;
import lu.wealins.webia.services.core.mapper.TransferMapper;
import lu.wealins.webia.services.core.persistence.entity.TransferEntity;
import lu.wealins.webia.services.core.persistence.repository.TransferRepository;
import lu.wealins.webia.services.core.service.TransferService;

@Service
public class TransferServiceImpl implements TransferService {

	private static final String REJECT_COMMENT_NOT_EMPTY = "the reject comment can't be empty";
	private static final String REJECT_DTO_NOT_NULL = "the reject dto can't be null";
	private static final String TRANSFER_ID_CANNOT_BE_NULL = "The transfer id can't be null";
	private static final String TRANSFER_IDS_CANNOT_BE_NULL = "The transfer ids can't be empty";
	private static final String EDITING_ID_CANNOT_BE_NULL = "Editing id can't be null";
	private static final String TRANSFER_EXECUTE_DTO_CANNOT_BE_NULL = "transfer execute dto can't be null";

	private final static Consumer<TransferEntity> TRANSFER_COMPTA_METADATA_CONSUMER = transfer -> {
		transfer.setUserCompta(SecurityContextHelper.getPreferredUsername());
		transfer.setComptaDt(new Date());
	};

	private final static Consumer<TransferEntity> TRANSFER_CPS2_UPDATE_CONSUMER = transfer -> {
		transfer.setCps2Dt(new Date());
		transfer.setUserCps2(SecurityContextHelper.getPreferredUsername());
	};

	private final static Consumer<TransferEntity> TRANSFER_COMPTA_CONSUMER = TRANSFER_CPS2_UPDATE_CONSUMER.andThen(transfer -> {
		transfer.setTransferStatus(TransferStatus.COMPTA.getCode());
	});

	private final static Consumer<TransferEntity> TRANSFER_ACCEPT_CPS2_CONSUMER = TRANSFER_CPS2_UPDATE_CONSUMER.andThen(transfer -> {
		transfer.setTransferStatus(TransferStatus.ACCEPTED.getCode());
	});

	private final static Consumer<TransferEntity> TRANSFER_READY_CONSUMER = transfer -> {
		transfer.setCps1Dt(new Date());
		transfer.setUserCps1(SecurityContextHelper.getPreferredUsername());
	};

	private final static Consumer<TransferEntity> TRANSFER_ACCEPT_CONSUMER = TRANSFER_COMPTA_METADATA_CONSUMER.andThen(transfer -> {
		transfer.setTransferStatus(TransferStatus.ACCEPTED.getCode());
	});

	private final static Consumer<TransferEntity> TRANSFER_REFUSE_CONSUMER = TRANSFER_COMPTA_METADATA_CONSUMER.andThen(transfer -> {
		transfer.setTransferStatus(TransferStatus.REFUSED.getCode());
	});

	private final static Consumer<TransferEntity> TRANSFER_EXECUTE_CONSUMER = TRANSFER_COMPTA_METADATA_CONSUMER.andThen(transfer -> {
		transfer.setTransferStatus(TransferStatus.EXECUTED.getCode());
		transfer.setTrfDt(new Date());
	});

	@Autowired
	private TransferRepository transferRepository;

	@Autowired
	private TransferMapper transferMapper;

	@Override
	public Collection<TransferDTO> updateTransfers(Collection<TransferDTO> transferDTOs) {
		Collection<TransferEntity> transferEntities = transferMapper.asTransferEntities(transferDTOs);
		return transferMapper.asTransferDTOs(transferRepository.save(transferEntities));
	}
	
	@Override
	public Collection<TransferDTO> exportTransferReadyForGenerateStatement(Long statementId, String agentId) {
		Collection<TransferEntity> entities = transferRepository.findAllByStatementIdAndBrokerId(statementId, agentId);
		return transferMapper.asTransferDTOs(entities);
	}

	@Override
	public List<String> getDistinctTransfersByStatement(Long statementId) {
		return transferRepository.findDistinctTransfersIdByStatement(statementId);
	}

	@Override
	public Collection<TransferDTO> getComptaPayments() {
		List<String> comptaStatusList = mapEnumToString(Arrays.asList(TransferStatus.COMPTA, TransferStatus.ACCEPTED, TransferStatus.REFUSED));
		List<String> comptaCodes = TransferCd.COMPTA_GROUP.stream().map(x -> x.getCode()).collect(Collectors.toList());

		Collection<TransferEntity> entities = transferRepository.findByTransferStatusInAndTransferCdInAndTransferType(comptaStatusList, comptaCodes,
				PaymentType.CASH_TRANSFER.name());

		return transferMapper.asTransferDTOs(entities);
	}

	@Override
	@Transactional
	public TransferDTO acceptByCompta(Long transferId) {
		TransferEntity transferEntity = getTransferById(transferId);

		updateTransfers(TRANSFER_ACCEPT_CONSUMER, Arrays.asList(TransferStatus.COMPTA), Arrays.asList(transferEntity));

		return transferMapper.asTransferDTO(transferEntity);
	}

	@Override
	@Transactional
	public Collection<TransferDTO> acceptByCps2(Collection<TransferDTO> transferDTOs) {
		return updateReadyTransfers(transferDTOs, TRANSFER_ACCEPT_CPS2_CONSUMER);
	}

	@Override
	@Transactional
	public TransferDTO refuse(Long transferId, TransferRefuseDTO refuseDTO) {
		Assert.notNull(refuseDTO, REJECT_DTO_NOT_NULL);
		Assert.hasText(refuseDTO.getComment(), REJECT_COMMENT_NOT_EMPTY);

		Consumer<TransferEntity> refuseConsumer = TRANSFER_REFUSE_CONSUMER.andThen(transfer -> {
			transfer.setRejectComment(refuseDTO.getComment());
		});

		TransferEntity transferEntity = getTransferById(transferId);

		updateTransfers(refuseConsumer, Arrays.asList(TransferStatus.COMPTA), Arrays.asList(transferEntity));

		return transferMapper.asTransferDTO(transferEntity);
	}

	@Override
	public TransferDTO getTransfer(Long transferId) {
		Assert.notNull(transferId, TRANSFER_ID_CANNOT_BE_NULL);

		return transferMapper.asTransferDTO(transferRepository.findOne(transferId));
	}
	
	@Override
	public Collection<TransferDTO> getTransfers(List<Long> transferIds) {
		Assert.notEmpty(transferIds, TRANSFER_IDS_CANNOT_BE_NULL);

		Collection<TransferEntity> transferEntities = transferRepository.findAll(transferIds);
		
		return transferMapper.asTransferDTOs(transferEntities);
	}

	@Override
	@Transactional
	public Collection<TransferDTO> execute(TransferExecutionRequest dto) {
		Assert.notNull(dto, TRANSFER_EXECUTE_DTO_CANNOT_BE_NULL);
		Assert.notNull(dto.getEditingId(), EDITING_ID_CANNOT_BE_NULL);
		Assert.notEmpty(dto.getIds(), TRANSFER_IDS_CANNOT_BE_NULL);

		List<TransferEntity> transferEntities = getTransfersByIds(dto.getIds());

		Consumer<TransferEntity> executeConsumer = TRANSFER_EXECUTE_CONSUMER.andThen(transfer -> {
			transfer.setEditingId(dto.getEditingId());
		});

		updateTransfers(executeConsumer, Arrays.asList(TransferStatus.ACCEPTED), transferEntities);

		return transferMapper.asTransferDTOs(transferEntities);
	}

	@Override
	@Transactional
	public Collection<TransferDTO> updateToComptaStatus(Collection<TransferDTO> transferDTOs) {
		return updateReadyTransfers(transferDTOs, TRANSFER_COMPTA_CONSUMER);
	}

	@Override
	@Transactional
	public Collection<TransferDTO> updateWithdrawalTransfers(Collection<TransferDTO> payments) {
		if (CollectionUtils.isEmpty(payments)) {
			return Collections.emptyList();
		}
		
		List<Long> ids = getNonNullIds(payments);

		Map<Long, TransferEntity> savedEntities = transferRepository.findAll(ids).stream().collect(Collectors.toMap(TransferEntity::getTransferId, Function.identity()));

		Collection<TransferEntity> entities = transferMapper.asTransferEntities(payments);

		Predicate<TransferEntity> cpsUpdatedStatusPredicate = entity -> hasStatus(entity.getTransferStatus(), TransferStatus.READY)
				&& (entity.getTransferId() == null || hasStatus(savedEntities.get(entity.getTransferId()).getTransferStatus(), TransferStatus.NEW));

		entities.stream().filter(cpsUpdatedStatusPredicate).forEach(TRANSFER_READY_CONSUMER::accept);

		return transferMapper.asTransferDTOs(transferRepository.save(entities));
	}

	private List<Long> getNonNullIds(Collection<TransferDTO> payments) {
		return payments.stream().map(TransferDTO::getTransferId).filter(Objects::nonNull).collect(Collectors.toList());
	}

	private boolean hasStatus(String statusString, TransferStatus status) {
		return status.getCode().equals(statusString);
	}

	private TransferEntity getTransferById(Long transferId) {
		Assert.notNull(transferId, "Transfer id can't be null");

		TransferEntity transferEntity = transferRepository.findOne(transferId);

		if (transferEntity == null) {
			throw new EntityNotFoundException(String.format("The transfer with id %s was not found", transferId));
		}
		return transferEntity;
	}

	private List<TransferEntity> getTransfersByIds(Collection<Long> transferIds) {
		Assert.notEmpty(transferIds, "Transfer ids can't be empty");

		List<TransferEntity> transferEntities = transferRepository.findAll(transferIds);

		if (transferEntities.size() != transferIds.size()) {
			throw new EntityNotFoundException(String.format("Some transfer ids were not found %s", transferIds));
		}

		return transferEntities;
	}

	private void updateTransfers(Consumer<TransferEntity> transferConsumer, List<TransferStatus> permittedStatusList, Collection<TransferEntity> transferEntities) {
		List<String> statusStrings = mapEnumToString(permittedStatusList);

		transferEntities.forEach(transfer -> {
			if (!canUpdateStatus(transfer, statusStrings)) {
				throw new InvalideActionException(String.format("Action not allowed for id %s", transfer.getTransferId()));
			}
			transferConsumer.accept(transfer);
		});
	}

	private boolean canUpdateStatus(TransferEntity entity, List<String> permittedStatusList) {
		return permittedStatusList.contains(entity.getTransferStatus());
	}

	private List<String> mapEnumToString(List<TransferStatus> in) {
		return in.stream().map(TransferStatus::getCode).collect(Collectors.toList());
	}

	private Collection<TransferDTO> updateReadyTransfers(Collection<TransferDTO> transferDTOs, Consumer<TransferEntity> consumer) {
		if (CollectionUtils.isEmpty(transferDTOs)) {
			return Collections.emptyList();
		}

		List<Long> ids = getNonNullIds(transferDTOs);

		List<TransferEntity> transferEntities = getTransfersByIds(ids);

		List<TransferEntity> readyTransfers = transferEntities.stream().filter(transfer -> hasStatus(transfer.getTransferStatus(), TransferStatus.READY)).collect(Collectors.toList());

		if (!CollectionUtils.isEmpty(readyTransfers)) {
			updateTransfers(consumer, Arrays.asList(TransferStatus.READY), readyTransfers);
		}

		return transferMapper.asTransferDTOs(transferEntities);
	}
}

package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.liability.services.AgentDataForTransferDTO;
import lu.wealins.common.dto.liability.services.AgentDataForTransfersResponse;
import lu.wealins.common.dto.liability.services.BrokerProcessDTO;
import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.constantes.CommissionConstant;
import lu.wealins.common.dto.webia.services.enums.CommissionStatus;
import lu.wealins.webia.services.core.components.CommissionToPayWrapper;
import lu.wealins.webia.services.core.components.CommissionToTypeTuple;
import lu.wealins.webia.services.core.persistence.entity.CommissionToPayEntity;
import lu.wealins.webia.services.core.persistence.entity.TransferEntity;
import lu.wealins.webia.services.core.persistence.repository.CommissionToPayRepository;
import lu.wealins.webia.services.core.persistence.repository.TransferRepository;
import lu.wealins.webia.services.core.service.PaymentCommissionService;

@Service
public class PaymentCommissionServiceImpl implements PaymentCommissionService {
	private static final String TRANSFER_STATUS_VALIDATED = "VALIDATED";
	private static final String TRANSFER_STATUS_EXECUTED = "EXECUTED";
	private static final String DEFAUT_TRANSFER_CD = "BROKER";

	@Autowired
	private CommissionToPayRepository commissionToPayRepository;

	@Autowired
	private TransferRepository transferRepository;

	private static final Logger logger = LoggerFactory.getLogger(PaymentCommissionServiceImpl.class);

	@Override
	public List<CommissionToPayWrapper> getCommissionsToPayByType(String comType) {

		List<CommissionToPayEntity> commissionToPayEntities = commissionToPayRepository.findByComType(comType);

		return processEntityWrapping(commissionToPayEntities);
	}

	@Override
	@Transactional
	public List<CommissionToPayEntity> update(List<CommissionToPayEntity> commissionToPayEntities) {

		return commissionToPayRepository.save(commissionToPayEntities);
	}

	@Override
	public PageResult<CommissionToPayWrapper> getCommissionsToPayByType(String comType, int page, int size) {
		Pageable pageable = new PageRequest(page, size);

		PageResult<CommissionToPayWrapper> result = new PageResult<>();
		Page<CommissionToPayEntity> pageResult = commissionToPayRepository.findByComType(comType, pageable);
		List<CommissionToPayWrapper> wrappedEntities = processEntityWrapping(pageResult.getContent());
		if (size != 0)
			result.setSize(size);
		result.setTotalPages(pageResult.getTotalPages());
		result.setTotalRecordCount(pageResult.getTotalElements());
		result.setCurrentPage(pageResult.getNumber());

		result.setContent(wrappedEntities);
		return result;
	}

	private List<CommissionToPayWrapper> processEntityWrapping(List<CommissionToPayEntity> commissionToPayEntities) {
		List<CommissionToPayWrapper> result = new ArrayList<>();
		Map<CommissionToTypeTuple, List<CommissionToPayEntity>> maps = commissionToPayEntities.stream()
				.collect(Collectors.groupingBy(ctp -> new CommissionToTypeTuple(ctp.getAgentId(), ctp.getComCurrency())));

		maps.forEach((K, V) -> {
			CommissionToPayWrapper commissionToPayWrapper = new CommissionToPayWrapper();
			commissionToPayWrapper.setCommissionToTypeTuple(K);
			commissionToPayWrapper.setCommissionToPayEntities(V);
			commissionToPayWrapper.setTotalAmount(V.stream().map(ctp -> ctp.getComAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
			result.add(commissionToPayWrapper);
		});
		return result;
	}

	@Override
	public List<BrokerProcessDTO> getCommissionAvailableForReportDistinctByBroker(List<String> type, List<String> period, String agent) {
		List<Object[]> result = new ArrayList<Object[]>();

		if (agent == null) {
			if (period != null) {
				String maxPeriod = Collections.max(period);
				result = commissionToPayRepository.findByTypeAndPeriodDistinctByAgent(type, maxPeriod);
			}
		} else {
			if (period != null) {
				String maxPeriod = Collections.max(period);
				result = commissionToPayRepository.findByTypeAndPeriodAndAgentDistinctByAgent(type, maxPeriod, agent);
			}
		}

		List<BrokerProcessDTO> brokerList = new ArrayList<BrokerProcessDTO>();

		for (Object[] commission : result) {
			BrokerProcessDTO brokerProcess = new BrokerProcessDTO();
			brokerProcess.setBrokerId(commission[0].toString());
			brokerProcess.setCurrency(commission[1].toString());
			brokerList.add(brokerProcess);
		}

		return brokerList;
	}

	@Override
	public List<TransferEntity> processBrokerAndCreateTransfers(AgentDataForTransfersResponse agentDataForTransfersResponse) throws Exception {
		List<AgentDataForTransferDTO> listAgentsData = agentDataForTransfersResponse.getAgents();

		List<TransferEntity> transfersEntities = new ArrayList<TransferEntity>();

		for (AgentDataForTransferDTO agentData : listAgentsData) {

			for (String typeValue : agentDataForTransfersResponse.getStatementWrapperDTO().getStatementTypeValue()) {

				List<String> typeValueList = new ArrayList<String>();
				boolean nextType = false;

				switch (typeValue) {
				case CommissionConstant.ENTRY:
					typeValueList.add(CommissionConstant.ENTRY);
					break;
				case CommissionConstant.ADM:
					typeValueList.add(CommissionConstant.ADM);
					typeValueList.add(CommissionConstant.SWITCH);
					typeValueList.add(CommissionConstant.SURR);
					break;
				case CommissionConstant.SWITCH:
					nextType = true;
					break;
				case CommissionConstant.SURR:
					nextType = true;
					break;
				case CommissionConstant.OPCVM:
					typeValueList.add(CommissionConstant.OPCVM);
					break;
				default:
					break;
				}

				if (nextType) {
					continue;
				}

				// Get commission
				List<String> period = agentDataForTransfersResponse.getStatementWrapperDTO().getPeriodValue();
				String maxPeriod = Collections.max(period);
				List<CommissionToPayEntity> commissonToPayEntities = commissionToPayRepository.findByTypeAndPeriodAndAgentAndCurrency(typeValueList, maxPeriod, agentData.getBrokerId(),
						agentData.getCurrency());

				// We only need to treat the validated commissions to pay
				List<CommissionToPayEntity> validatedCommissonToPayEntities = commissonToPayEntities.stream().filter(ctp -> CommissionStatus.VALIDATED.getValue().equals(ctp.getStatus()))
						.collect(Collectors.toList());

				if (!CollectionUtils.isEmpty(validatedCommissonToPayEntities)) {
					BigDecimal amout = BigDecimal.ZERO;

					for (CommissionToPayEntity com : validatedCommissonToPayEntities) {
						amout = amout.add(com.getComAmount());
						com.setStatementId(agentDataForTransfersResponse.getStatementWrapperDTO().getStatementId());
					}

					if (amout.compareTo(BigDecimal.ZERO) == -1) {
						// throw new Exception("Negative amount, check broker " + agentData.getBrokerId() + " " + agentData.getCurrency());
						logger.error("Negative amount,  check broker " + agentData.getBrokerId() + " " + agentData.getCurrency());
						continue;
					}

					if (amout.compareTo(BigDecimal.ZERO) == 0) {
						// throw new Exception("Amount = 0, check broker " + agentData.getBrokerId() + " " + agentData.getCurrency());
						logger.info("Amount = 0,  check broker " + agentData.getBrokerId() + " " + agentData.getCurrency());
						// continue;
					}

					TransferEntity transfer = new TransferEntity();
					transfer.setTransferCd(DEFAUT_TRANSFER_CD);
					if (amout.equals(BigDecimal.ZERO)) {
						transfer.setTransferStatus(TRANSFER_STATUS_EXECUTED);
					} else {
						transfer.setTransferStatus(TRANSFER_STATUS_VALIDATED);
					}
					transfer.setTrfMt(amout);
					transfer.setTrfCurrency(agentData.getCurrency());
					transfer.setTrfComm(agentDataForTransfersResponse.getStatementWrapperDTO().getPeriod() + "/" + agentData.getBrokerId() + "/" + agentData.getCurrency());
					transfer.setTrfDt(new Date());
					transfer.setIbanDonOrd(agentData.getIbanDonord());
					transfer.setSwiftDonOrd(agentData.getSwiftDonord());
					transfer.setLibDonOrd(agentData.getLibDonord());
					transfer.setIbanBenef(agentData.getIbanBenef());
					transfer.setSwiftBenef(agentData.getSwiftBenef());
					transfer.setLibBenef(agentData.getLibBenef());
					transfer.setTransferType(typeValue);
					transfer.setCreationDt(new Date());
					transfer.setStatementId(agentDataForTransfersResponse.getStatementWrapperDTO().getStatementId());
					transfer.setBrokerId(agentData.getBrokerId());
					TransferEntity createdTransfer = transferRepository.save(transfer);

					validatedCommissonToPayEntities.forEach(t -> {
						t.setTransferId(createdTransfer.getTransferId());
						commissionToPayRepository.save(t);
					});
					transfersEntities.add(transfer);
				}
			}
		}

		return transfersEntities;
	}

	@Override
	public List<CommissionToPayEntity> exportCommissionsToPayReadyForGenerateStatement(Long transfertId) {
		return commissionToPayRepository.findByTransferId(transfertId);
	}

}

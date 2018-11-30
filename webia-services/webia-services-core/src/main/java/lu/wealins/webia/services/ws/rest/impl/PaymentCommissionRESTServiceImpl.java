package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.liability.services.AgentDataForTransfersResponse;
import lu.wealins.common.dto.liability.services.BrokerProcessDTO;
import lu.wealins.common.dto.webia.services.AgentIdListResponse;
import lu.wealins.common.dto.webia.services.CommissionToPayDTO;
import lu.wealins.common.dto.webia.services.CommissionToPayWrapperDTO;
import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.StatementWrapperDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.TransferWrapperDTO;
import lu.wealins.webia.services.core.components.CommissionToPayWrapper;
import lu.wealins.webia.services.core.mapper.CommissionToPayMapper;
import lu.wealins.webia.services.core.mapper.CommissionToPayWrapperMapper;
import lu.wealins.webia.services.core.mapper.TransferMapper;
import lu.wealins.webia.services.core.persistence.entity.CommissionToPayEntity;
import lu.wealins.webia.services.core.persistence.entity.TransferEntity;
import lu.wealins.webia.services.core.service.PaymentCommissionService;
import lu.wealins.webia.services.core.service.TransferService;
import lu.wealins.webia.services.ws.rest.PaymentCommissionRESTService;

@Component
public class PaymentCommissionRESTServiceImpl implements PaymentCommissionRESTService {
	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentCommissionRESTServiceImpl.class);

	@Autowired
	private TransferService transferService;

	@Autowired
	private PaymentCommissionService paymentCommissionService;

	@Autowired
	private CommissionToPayWrapperMapper commissionToPayWrapperMapper;

	@Autowired
	private CommissionToPayMapper commissionToPayMapper;

	@Autowired
	private TransferMapper transferMapper;

	@Override
	public PageResult<CommissionToPayWrapperDTO> extractNotTransferedCommissionsToPayByType(SecurityContext context, String comType, int page, int size) {
		PageResult<CommissionToPayWrapperDTO> response = new PageResult<>();

		PageResult<CommissionToPayWrapper> pageResult = (PageResult<CommissionToPayWrapper>) paymentCommissionService.getCommissionsToPayByType(comType, page, size);

		response.setCurrentPage(pageResult.getCurrentPage());
		response.setTotalPages(pageResult.getTotalPages());
		response.setTotalRecordCount(pageResult.getTotalRecordCount());
		response.setSize(pageResult.getSize());
		response.setContent((List<CommissionToPayWrapperDTO>) commissionToPayWrapperMapper.asCommissionToPayWrapperDTOs(pageResult.getContent()));

		return response;

	}

	@Override
	@Transactional
	public Response insertTransfers(SecurityContext context, Collection<TransferDTO> transferDTOs) {
		try {
			Collection<TransferDTO> response = transferService.updateTransfers(transferDTOs);
			return Response.ok(response).build();
		} catch (Exception e) {
			LOGGER.error("Error during insert transfers " + e);
			return Response.serverError().build();
		}
	}

	@Override
	@Transactional
	public Response updateCommissionToPay(SecurityContext context, Collection<CommissionToPayWrapperDTO> commissionDTOs) {
		try {
			LOGGER.info("Update commission to pay size" + commissionDTOs.size());
			List<CommissionToPayEntity> commissionToPayEntities = new CopyOnWriteArrayList<>();

			/* Bind commission to pay to transfer_id grouped by agent and currency */
			commissionDTOs.parallelStream().forEach(dto -> {
				List<CommissionToPayDTO> commissiondDtos = dto.getCommissionToPayEntities().stream().map(t -> {
					t.setTransferId(dto.getTransferId());
					return t;
				}).collect(Collectors.toList());

				commissionToPayEntities.addAll(commissionToPayMapper.asCommissionToPayEntities(commissiondDtos));
			});
			paymentCommissionService.update(commissionToPayEntities);
			return Response.ok().build();
		} catch (Exception e) {
			LOGGER.error("Error during update commission to pay " + e);
			return Response.serverError().build();
		}
	}

	@Override
	public List<BrokerProcessDTO> getAvailableBrokerForCommission(SecurityContext context, StatementWrapperDTO statementWrapperDTO) {
		LOGGER.info("type " + statementWrapperDTO.getStatementType() + " period " + statementWrapperDTO.getPeriod() + " broker " + statementWrapperDTO.getAgentId());
		List<BrokerProcessDTO> result = paymentCommissionService.getCommissionAvailableForReportDistinctByBroker(statementWrapperDTO.getStatementTypeValue(), statementWrapperDTO.getPeriodValue(), statementWrapperDTO.getAgentId());
		return result;
	}

	@Override
	public TransferWrapperDTO postAvailableBrokerForCreateTransfers(SecurityContext context, AgentDataForTransfersResponse agentDataForTransfersResponse) {
		LOGGER.info("type " + agentDataForTransfersResponse);
		TransferWrapperDTO transferWrapperDTO = new TransferWrapperDTO();
		try {
			List<TransferEntity> result = paymentCommissionService.processBrokerAndCreateTransfers(agentDataForTransfersResponse);
			transferWrapperDTO.setTransferDTO(transferMapper.asTransferDTOs(result));
		} catch (Exception e) {
			transferWrapperDTO.setError(e == null ? null : e.getMessage());
		}
		
		return transferWrapperDTO;
	}

	@Override
	public CommissionToPayWrapperDTO exportCommissionsToPayReadyForGenerateStatement(SecurityContext context, Long transfertId) {
		CommissionToPayWrapperDTO commissionToPayWrapperDTO = new CommissionToPayWrapperDTO();
		List<CommissionToPayEntity> result = paymentCommissionService.exportCommissionsToPayReadyForGenerateStatement(transfertId);
		commissionToPayWrapperDTO.setCommissionToPayEntities(commissionToPayMapper.asCommissionToPayDTOs(result));
		return commissionToPayWrapperDTO;
	}
	
	@Override
	public TransferWrapperDTO exportTransferReadyForGenerateStatement(SecurityContext context, Long statementId, String agentId) {
		TransferWrapperDTO transferWrapperDTO = new TransferWrapperDTO();
		Collection<TransferDTO> result = transferService.exportTransferReadyForGenerateStatement(statementId, agentId);
		transferWrapperDTO.setTransferDTO(result);
		return transferWrapperDTO;
	}

	@Override
	public AgentIdListResponse getDistinctTransfersByStatement(SecurityContext context, Long statementId) {
		AgentIdListResponse agentIdListResponse = new AgentIdListResponse();
		agentIdListResponse.setAgents(transferService.getDistinctTransfersByStatement(statementId));
		return agentIdListResponse;
	}

}

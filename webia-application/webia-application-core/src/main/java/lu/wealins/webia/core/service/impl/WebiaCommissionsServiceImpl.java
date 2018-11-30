package lu.wealins.webia.core.service.impl;

import java.util.List;

import javax.ws.rs.core.GenericType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.AgentDataForTransfersResponse;
import lu.wealins.common.dto.webia.services.AgentIdListResponse;
import lu.wealins.common.dto.liability.services.BrokerProcessDTO;
import lu.wealins.common.dto.webia.services.CommissionToPayWrapperDTO;
import lu.wealins.common.dto.webia.services.StatementDTO;
import lu.wealins.common.dto.webia.services.StatementRequest;
import lu.wealins.common.dto.webia.services.StatementResponse;
import lu.wealins.common.dto.webia.services.StatementUpdateStatusRequest;
import lu.wealins.common.dto.webia.services.StatementWrapperDTO;
import lu.wealins.common.dto.webia.services.TransferWrapperDTO;
import lu.wealins.webia.core.service.WebiaCommissionsService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Component
public class WebiaCommissionsServiceImpl implements WebiaCommissionsService {
	private static final String WEBIA_GET_TRANSFERS_READY = "webia/payment-commission/export-transfers-ready-for-generate-statement/";
	private static final String WEBIA_GET_COMMISSIONS_READY = "webia/payment-commission/export-commissions-to-pay-ready-for-generate-statement/";
	private static final String WEBIA_GET_STATEMENT = "webia/statement/";
	private static final String WEBIA_CREATE_STATEMENT = "webia/statement/new-statement";
	private static final String EXTRACT_AVAILABLE_BROKER = "webia/payment-commission/get-available-broker";
	private static final String GET_TRANSFERS_BROKER_BY_STATEMENT = "webia/payment-commission/distinct-transfers-broker-by-statement/";
	private static final String INSERT_COMMISSION_TO_PAY = "webia/payment-commission/post-available-broker-for-create-transfers";
	private static final String UPDATE_STATEMENT_STATUS = "webia/statement/update-status";
	
	@Autowired
	private RestClientUtils restClientUtils;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaApplicationParameterService#getTransfersReady(java.lang.String, java.lang.String)
	 */
	@Override
	public TransferWrapperDTO getTransfersReady(String agentId, String statementId) {
		return restClientUtils.get(WEBIA_GET_TRANSFERS_READY, statementId + "/" + agentId + "/", TransferWrapperDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaApplicationParameterService#getCommissionsReady(java.lang.String)
	 */
	@Override
	public CommissionToPayWrapperDTO getCommissionsReady(String transferId) {
		return restClientUtils.get(WEBIA_GET_COMMISSIONS_READY, transferId + "/", CommissionToPayWrapperDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaApplicationParameterService#getStatement(java.lang.String)
	 */
	@Override
	public StatementDTO getStatement(String statementId) {
		return restClientUtils.get(WEBIA_GET_STATEMENT, statementId + "/", StatementDTO.class);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaApplicationParameterService#createStatement(lu.wealins.common.dto.webia.services.StatementRequest)
	 */
	@Override
	public StatementResponse createStatement(StatementRequest statementRequest) {
		return restClientUtils.post(WEBIA_CREATE_STATEMENT, statementRequest, StatementResponse.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaApplicationParameterService#getAvailableBrokerCommission(lu.wealins.common.dto.webia.services.StatementRequest)
	 */
	@Override
	public List<BrokerProcessDTO> getAvailableBrokerCommission(StatementWrapperDTO statement){	
		return restClientUtils.post(EXTRACT_AVAILABLE_BROKER, statement, new GenericType<List<BrokerProcessDTO>>() {});
	}
	
	@Override
	public AgentIdListResponse getDistinctTransfersBrokerByStatementId(Long statementId){
		return restClientUtils.get(GET_TRANSFERS_BROKER_BY_STATEMENT, statementId+ "/", AgentIdListResponse.class);
	}
	
	@Override
	public TransferWrapperDTO processCommissionForCreateTransers(AgentDataForTransfersResponse dataAvailableBrokerResponse){
		return restClientUtils.post(INSERT_COMMISSION_TO_PAY, dataAvailableBrokerResponse, TransferWrapperDTO.class);
	}
	@Override
	public void updateStatementStatus(StatementUpdateStatusRequest statementUpdateStatusRequest){
		restClientUtils.post(UPDATE_STATEMENT_STATUS, statementUpdateStatusRequest);
	};
}

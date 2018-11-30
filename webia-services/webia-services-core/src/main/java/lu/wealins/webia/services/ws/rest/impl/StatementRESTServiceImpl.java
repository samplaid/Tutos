package lu.wealins.webia.services.ws.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.webia.services.enums.StatementStatus;
import lu.wealins.common.dto.webia.services.StatementDTO;
import lu.wealins.common.dto.webia.services.StatementRequest;
import lu.wealins.common.dto.webia.services.StatementResponse;
import lu.wealins.common.dto.webia.services.StatementUpdateStatusRequest;
import lu.wealins.common.dto.webia.services.StatementRequest;
import lu.wealins.common.dto.webia.services.StatementResponse;
import lu.wealins.common.dto.webia.services.StatementUpdateStatusRequest;
import lu.wealins.webia.services.core.mapper.StatementMapper;
import lu.wealins.webia.services.core.persistence.entity.StatementEntity;
import lu.wealins.webia.services.core.persistence.repository.StatementRepository;
import lu.wealins.webia.services.ws.rest.StatementRESTService;

@Component
public class StatementRESTServiceImpl implements StatementRESTService {
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(StatementRESTServiceImpl.class);

	@Autowired
	private StatementRepository statementRepository;
	@Autowired
	private StatementMapper statementMapper;

	@Override
	@Transactional
	public Response createNewStatement(SecurityContext context, StatementRequest statementRequest) {
		logger.info("Create statement Type=[" + statementRequest.getType() + "] Period=[" + statementRequest.getPeriod() + "] Broker=[" + statementRequest.getBroker() + "]");
		StatementResponse statementResponse = new StatementResponse();
		
		try {	
			if (statementRequest.getType() == null) {
				throw new IllegalArgumentException("Type is null");
			}
			
			if (statementRequest.getPeriod() == null) {
				throw new IllegalArgumentException("Period is null");
			}
			List<String> excludeStatement = new ArrayList<String>();
			excludeStatement.add(StatementStatus.FAILED.name());  // previous process failed
			excludeStatement.add(StatementStatus.REQUEST.name()); // previous process terminated
			List<StatementEntity> listOfStatement = statementRepository.findAllByStatementTypeAndPeriodAndAgentIdAndStatementStatusNotIn(statementRequest.getType(), statementRequest.getPeriod(), statementRequest.getBroker(), excludeStatement);
			if (CollectionUtils.isEmpty(listOfStatement)) {
				StatementEntity statement = new StatementEntity();
				statement.setAgentId(statementRequest.getBroker());
				statement.setPeriod(statementRequest.getPeriod());
				statement.setStatementType(statementRequest.getType());
				statement.setStatementStatus(StatementStatus.PROCESSING.name());
				statementResponse.setStatement(statementMapper.asStatementDTO(statementRepository.save(statement)));
			} else {
				statementResponse.setError("Statement already exists.");
			}
			return Response.ok(statementResponse).build();
		} catch (Exception e) {
			logger.error("Error during create statement " + e);
			statementResponse.setError(e.getMessage());
			return Response.ok(statementResponse).build();
		}
	}

	@Override
	public Response updateStatementStatus(SecurityContext context, StatementUpdateStatusRequest statementUpdateStatusRequest) {
		logger.info("Update statement id=[" + statementUpdateStatusRequest.getId() + "] with status=[" +statementUpdateStatusRequest.getStatus()+ "]");
		
		try {
			StatementEntity statement = statementRepository.findOne(statementUpdateStatusRequest.getId());
			statement.setStatementStatus(statementUpdateStatusRequest.getStatus());
			if (statementUpdateStatusRequest.getMessage() != null && statementUpdateStatusRequest.getMessage().trim().length() > 0) {
				statement.setErrorDesc(statementUpdateStatusRequest.getMessage());
			}
			statementRepository.save(statement);
		} catch (Exception e) {
			logger.error("Error during update status of statement ", e);
			return Response.serverError().build();
		}
			
		return Response.ok().build();
	}

	@Override
	public StatementDTO getStatement(SecurityContext context, Long statementId) {
		StatementEntity statement = statementRepository.findOne(statementId);
		return statementMapper.asStatementDTO(statement);
	}


}

/**
 * 
 */
package lu.wealins.batch.generate.statementcom;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import lu.wealins.common.dto.webia.services.StatementDTO;
import lu.wealins.common.dto.webia.services.StatementRequest;
import lu.wealins.common.dto.webia.services.StatementResponse;
import lu.wealins.utils.RestCallUtils;

/**
 * @author xqv95
 *
 */
public class GenerateStatementComTaskLet extends AbstractGenerateStatementComTaskLet {

	{
		logger = LogFactory.getLog(GenerateStatementComTaskLet.class);
	}

	private static final String GENERATE_STATEMENT_COM = "generateStatementCommission";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org. springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		logger.info("Try GenerateStatementComJob");

		try {
			generateStatementCom();

		} catch (Exception e) {
			logger.error(e);
			setJobErrorExecution(e);
			throw e;
		}

		return RepeatStatus.FINISHED;
	}

	private StatementDTO generateStatementCom() throws Exception {
		StatementRequest statementRequest = new StatementRequest();
		if (StringUtils.isNotBlank(getParamJobStatementId())) {
			statementRequest.setStatementId(Long.valueOf(getParamJobStatementId()));
		}
		statementRequest.setBroker(getParamJobBroker());
		statementRequest.setPeriod(getParamJobPeriod());
		statementRequest.setType(getParamJobType());
		logger.info("Trying to create statement ...");
		ParameterizedTypeReference<StatementResponse> typeRef = new ParameterizedTypeReference<StatementResponse>() {
		};
		ResponseEntity<StatementResponse> response = RestCallUtils.postRest(getPiaRootContextURL() + GENERATE_STATEMENT_COM, statementRequest, StatementRequest.class, typeRef, keycloackUtils, logger);
		logger.info("Sucessfully create statement ");
		StatementResponse datas = response.getBody();

		if (datas.getError() != null) {
			if (datas.getStatement() != null) {
				logger.error("Error during create Editing Request for statement ID = " + datas.getStatement().getStatementId());
			}
			throw new Exception(datas.getError());
		}

		return datas.getStatement();
	}

}

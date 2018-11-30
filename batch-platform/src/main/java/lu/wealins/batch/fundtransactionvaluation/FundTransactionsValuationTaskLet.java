package lu.wealins.batch.fundtransactionvaluation;

import java.util.Date;

import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import lu.wealins.batch.simplerest.SimpleRestTaskLet;
import lu.wealins.common.dto.webia.services.FundTransactionsValuationRequest;

public class FundTransactionsValuationTaskLet extends SimpleRestTaskLet<FundTransactionsValuationRequest, Response> {

	private static final String LIABILITY_EXECUTE_POST_FPC = "liability/fundTransaction/executePOST_FPC";
	private static final String LIABILITY_EXECUTE_PRE_FPC = "liability/fundTransaction/executePRE_FPC";
	private static final String DATE = "date";
	private static final String FUND_ID = "fundId";
	private Log logger = LogFactory.getLog(FundTransactionsValuationTaskLet.class);

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	private String message = "";

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		FundTransactionsValuationRequest request = createRequest(chunkContext);
		message = restUtilityService.post(piaRootContextURL + LIABILITY_EXECUTE_PRE_FPC, request, String.class);
		try {
			message += " - " + restUtilityService.post(getUrl(), request, String.class);
		} catch (Exception e) {
			logger.error("Cannot execute the fund transaction valuation webservice. ", e);
			throw e;
		} finally {
			message += " - " + restUtilityService.post(piaRootContextURL + LIABILITY_EXECUTE_POST_FPC, request, String.class);
		}

		return RepeatStatus.FINISHED;
	}

	@Override
	public FundTransactionsValuationRequest createRequest(ChunkContext chunkContext) {
		Date date = batchUtilityService.getDateJobParameter(chunkContext, DATE);
		String fundId = batchUtilityService.getStringJobParameter(chunkContext, FUND_ID);

		FundTransactionsValuationRequest request = new FundTransactionsValuationRequest();

		request.setDate(date);
		request.setFundId(fundId);

		return request;
	}

	@Override
	public String createExitMessage(Response response) {

		logger.debug("FundTransactionsValuationTaskLet - done");
		return message;
	}

}

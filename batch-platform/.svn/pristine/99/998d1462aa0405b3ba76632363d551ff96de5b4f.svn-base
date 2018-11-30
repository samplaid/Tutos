package lu.wealins.batch.extract.frenchTax;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import lu.wealins.batch.simplerest.SimpleRestTaskLet;
import lu.wealins.common.dto.webia.services.TransactionTaxRequest;
import lu.wealins.common.dto.webia.services.TransactionTaxResponse;

public class ExtractLISSIATransactionForFrenchTaxTaskLet extends SimpleRestTaskLet<TransactionTaxRequest, TransactionTaxResponse> {

	private static String EXTRACT_LISSIA_TRANSACTIONS_FOR_FRENCHTAX = "transactiontax/handle";

	private Log logger = LogFactory.getLog(ExtractLISSIATransactionForFrenchTaxTaskLet.class);

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Value("${webiaEndPoint}")
	private String webiaEndPoint;

	private String message = "";

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		TransactionTaxRequest request = createRequest(chunkContext);
		message = restUtilityService.post(webiaEndPoint + EXTRACT_LISSIA_TRANSACTIONS_FOR_FRENCHTAX, request, String.class);
		try {
			message += " - " + restUtilityService.post(getUrl(), request, String.class);
		} catch (Exception e) {
			logger.error("Cannot execute the extract transaction french tax webservice. ", e);
			throw e;
		}

		return RepeatStatus.FINISHED;
	}

	@Override
	public TransactionTaxRequest createRequest(ChunkContext chunkContext) {
		List<String> countryList = new ArrayList<>();
		countryList.add("FR");
		TransactionTaxRequest request = new TransactionTaxRequest();
		request.setCountryList(countryList);
		return request;
	}

	@Override
	public String createExitMessage(TransactionTaxResponse Response) {
		logger.debug("FundTransactionsValuationTaskLet - done");
		return message;
	}

}

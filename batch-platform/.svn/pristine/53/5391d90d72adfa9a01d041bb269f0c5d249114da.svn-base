package lu.wealins.batch.extract.sapaccounting;

import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.common.dto.liability.services.ResponsePostingSetIdsDTO;
import lu.wealins.utils.RestCallUtils;

public class ExtractLissiaSapAccountingReadFromLissiaTaskLet extends AbstractExtractLissiaSapAccountingTaskLet {
	private static final String POSTING_SET_AVAILABLE = "liability/transaction/postingsetavailable/sapaccounting";
	private static final String EXTRACT_LISSIA_TRANSACTION = "liability/transaction/noexportedtransactions/pagination";

	private int page = 0;

	{
		logger = LogFactory.getLog(ExtractLissiaSapAccountingReadFromLissiaTaskLet.class);
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		ExtractLissiaSapAccountingDecider.flowExecutionStatus = FlowExecutionStatus.FAILED;
		try {
			if (pstAvailable == null) {
				loadPstAvailable();
			}

			if (!CollectionUtils.isEmpty(pstAvailable)) {
				logger.info("Extract Account Transaction for posting set ID=[" + getNextPstAvailableReady() + "] step " + countPstAvailableCompleted() + "/" + pstAvailable.size());
				lissiaTransaction = extractLissiaTransactions(page, lissiaTransaction == null ? 0 : lissiaTransaction.getLastId());
			} else {
				ExtractLissiaSapAccountingDecider.flowExecutionStatus = FlowExecutionStatus.COMPLETED;
			}
		} catch (Exception e) {
			setJobErrorExecution(e);
			throw e;
		}
		

		return RepeatStatus.FINISHED;

	}

	/**
	 * Load and Init posting set ids available
	 * @throws Exception 
	 */
	private void loadPstAvailable() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ParameterizedTypeReference<ResponsePostingSetIdsDTO> typeRef = new ParameterizedTypeReference<ResponsePostingSetIdsDTO>() {};
		logger.info("Trying to get PostingSet Available from Lissia DB ...");
		ResponseEntity<ResponsePostingSetIdsDTO> response = RestCallUtils.get(getPiaRootContextURL() + POSTING_SET_AVAILABLE, params, ResponsePostingSetIdsDTO.class, typeRef, keycloackUtils, logger);
		ResponsePostingSetIdsDTO postingSetIds = response.getBody();
		
		if (postingSetIds.getErrorMessage() != null) {
			throw new Exception(postingSetIds.getErrorMessage());
		}
		
		if (!CollectionUtils.isEmpty(postingSetIds.getPostingSetAvailable())) {
			initPstAvailable(postingSetIds.getPostingSetAvailable());
		}
		logger.info("Successfully got PostingSet Available from Lissia DB  ");
	}
	
	/**
	 * extract the transactions from Lissia database
	 * 
	 * @return
	 * @throws Exception
	 */
	private PageResult<TransactionDTO> extractLissiaTransactions(int page, long lastId) throws Exception {
		PageResult<TransactionDTO> datas = null;

		// get URI and query parameters
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", String.valueOf(page));
		params.add("size", String.valueOf(PAGE_SIZE));
		params.add("lastId", String.valueOf(lastId));
		params.add("currentPst", String.valueOf(getNextPstAvailableReady()));
		
		ParameterizedTypeReference<PageResult<TransactionDTO>> typeRef = new ParameterizedTypeReference<PageResult<TransactionDTO>>() {};
		
		ResponseEntity<PageResult<TransactionDTO>> response = RestCallUtils.get(getPiaRootContextURL() + EXTRACT_LISSIA_TRANSACTION, params, PageResult.class, typeRef, keycloackUtils, logger);
		if (response.getStatusCode().is2xxSuccessful()) {
			datas = response.getBody();
			if (datas == null || (datas.getContent().isEmpty() && pstAvailableIsAllCompleted())) {
				ExtractLissiaSapAccountingDecider.flowExecutionStatus = FlowExecutionStatus.COMPLETED;
				lissiaTransaction = null;
			} else if (datas.getContent().isEmpty()) {
				setPstAvailableReadyForUpdate(getNextPstAvailableReady());
				datas.setLastId(0L);
				ExtractLissiaSapAccountingDecider.flowExecutionStatus = FlowExecutionStatus.UNKNOWN; // continue
			} else {
				ExtractLissiaSapAccountingDecider.flowExecutionStatus = FlowExecutionStatus.UNKNOWN; // continue
			}
		} else
			throw new Exception(response.getStatusCode().getReasonPhrase());
		return datas;
	}

}

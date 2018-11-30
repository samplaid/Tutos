/**
 * 
 */
package lu.wealins.batch.extract.commissiontopay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.liability.services.PstPostingSetsDTO;
import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;
import lu.wealins.common.dto.webia.services.TransactionDTO;
import lu.wealins.utils.RestCallUtils;

/**
 * @author xqv95
 *
 */
public class ExtractLissiaCommissionToPayTaskLet extends AbstractExtractLissiaCommissionToPayTaskLet {

	{
		logger = LogFactory.getLog(ExtractLissiaCommissionToPayTaskLet.class);
	}

	private static final String POSTING_SET_AVAILABLE = "liability/transaction/postingsetavailable/commissiontopay";
	private static final String INSERT_COMMISSION_TO_PAY = "webia/lissia-extract/commission-to-pay";
	private static final String EXTRACT_LISSIA_TRANSACTION = "liability/transaction/noexportedtransactionscommissiontopay/pagination";
	private static final String UPDATE_POSTINGSET_COM_STATUS = "liability/posting-sets/update-com-status";
	private static final String APPLICATION_PARAMETER = "webia/applicationParameter";
	
	private static final String CODE_BIL_APPLICATION_PARAMETER = "BROKER_BIL";

	private static final short COM_STATUS = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org. springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		if (pstAvailable == null) {
			loadPstAvailable();
		}

		if (!CollectionUtils.isEmpty(pstAvailable)) {
			int page = 0;
			Long lastId = 0L;
			do {
				logger.info("Extract Account Transaction for posting set ID=[" + getNextPstAvailableReady() + "] step " + (countPstAvailableCompleted()+ 1) + "/" + pstAvailable.size());
				
				ApplicationParameterDTO codeBilApplicationParam = getApplicationParameterCodeForBilCommission();
				
				// extract account transactions from Lissia
				lissiaTransaction = extractLissiaTransactions(page, lastId, codeBilApplicationParam);
				if (lissiaTransaction != null) {
					// get last id
					Optional<TransactionDTO> result = lissiaTransaction.getContent().stream().max((x, y) -> Long.compare(x.getAtrId(), y.getAtrId()));
					if (result.isPresent()) {
						lastId = result.get().getAtrId();
					}
					// insert transactions into Commission to pay
					idsForRollback.addAll(insertCommissionToPay(lissiaTransaction.getContent()));
				}

				if (!lissiaTransaction.hasNext()) {
					setPstAvailableReadyForUpdate(getNextPstAvailableReady());
					lastId = 0L;
				}

				updateComStatus(asPstPostingSetsDTOs(getPstAvailableReadyForUpdate()));

			} while (lissiaTransaction != null && !pstAvailableIsAllCompleted());
		}
		
		logger.info("Successfully extractLissiaCommissionToPayJob");
		return RepeatStatus.FINISHED;
	}
	
	/**
	 * Load and Init posting set ids available
	 */
	private void loadPstAvailable() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ParameterizedTypeReference<List<Long>> typeRef = new ParameterizedTypeReference<List<Long>>() {};
		logger.info("Trying to get PostingSet Available from Lissia DB ...");
		ResponseEntity<List<Long>> response = RestCallUtils.get(getPiaRootContextURL() + POSTING_SET_AVAILABLE, params, List.class, typeRef, keycloackUtils, logger);
		if (!CollectionUtils.isEmpty(response.getBody())) {
			initPstAvailable(response.getBody());
		}
		logger.info("Successfully got PostingSet Available from Lissia DB  ");
	}
	
	/**
	 * extract the transactions from Lissia database
	 * 
	 * 
	 * @return ApplicationParameterDTO
	 */
	private ApplicationParameterDTO getApplicationParameterCodeForBilCommission() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ParameterizedTypeReference<ApplicationParameterDTO> typeRef = new ParameterizedTypeReference<ApplicationParameterDTO>() {};
		logger.info("Trying to get application parameter from Webia DB ...");
		ResponseEntity<ApplicationParameterDTO> response = RestCallUtils.get(getPiaRootContextURL() + APPLICATION_PARAMETER + "/" + CODE_BIL_APPLICATION_PARAMETER, params, ApplicationParameterDTO.class, typeRef, keycloackUtils, logger);
		ApplicationParameterDTO datas = response.getBody();
		logger.info("Successfully got application parameter from Webia DB");
		return datas;
	}

	/**
	 * extract the transactions from Lissia database
	 * 
	 * @param lastId
	 * @param codeBilApplicationParam 
	 * 
	 * @return
	 */
	private PageResult<TransactionDTO> extractLissiaTransactions(int page, Long lastId, ApplicationParameterDTO codeBilApplicationParam) {
		// get URI 
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", String.valueOf(page));
		params.add("size", String.valueOf(PAGE_SIZE));
		params.add("lastId", String.valueOf(lastId));
		params.add("codeBil", String.valueOf(codeBilApplicationParam.getValue()));
		params.add("currentPst", String.valueOf(getNextPstAvailableReady()));
		
		ParameterizedTypeReference<PageResult<TransactionDTO>> typeRef = new ParameterizedTypeReference<PageResult<TransactionDTO>>() {};
		logger.info("Trying to get transactions for Commission To Pay from Lissia DB ...");
		ResponseEntity<PageResult<TransactionDTO>> response = RestCallUtils.get(getPiaRootContextURL() + EXTRACT_LISSIA_TRANSACTION, params, TransactionDTO.class, typeRef, keycloackUtils, logger);
		logger.info("Successfully got transactions for Commission To Pay from Lissia DB  ");
		PageResult<TransactionDTO> datas = response.getBody();
		return datas;
	}

	/**
	 * insert transactions into Commission to pay
	 * 
	 * @return
	 */
	private List<Long> insertCommissionToPay(Collection<TransactionDTO> transactions) {
		logger.info("Trying to post transactions for Commission To Pay into WEBIA DB ...");
		ParameterizedTypeReference<List<Long>> typeRef = new ParameterizedTypeReference<List<Long>>() {};
		ResponseEntity<List<Long>> response = RestCallUtils.postRest(getPiaRootContextURL() + INSERT_COMMISSION_TO_PAY, transactions, Collection.class, typeRef, keycloackUtils, logger);
		List<Long> datas = response.getBody();
		logger.info("Sucessfully posted transfactions for Commission To Pay into WEBIA DB");
		return datas;
	}
	
	/**
	 * Transform posting_sets id to Posting_setsDTO
	 * 
	 * @param pstIds
	 * @return
	 */
	private Set<PstPostingSetsDTO> asPstPostingSetsDTOs(Collection<Long> pstIds) {
		Set<PstPostingSetsDTO> pstDtos = new HashSet<>();
		for (Long wrapper : pstIds) {
			PstPostingSetsDTO pstDto = new PstPostingSetsDTO();
			pstDto.setPstId(wrapper);
			pstDto.setComStatus(COM_STATUS);
			pstDtos.add(pstDto);
		}
		return pstDtos;
	}

	/**
	 * Update posting_sets com_status 
	 * 
	 * @param pstIds
	 * @return
	 * @throws Exception
	 */
	private List<Long> updateComStatus(Collection<PstPostingSetsDTO> pstIds) throws Exception {
		List<Long> result = new ArrayList<Long>();
		logger.info("Trying to update com status ...");
		ParameterizedTypeReference<List<Long>> typeRef = new ParameterizedTypeReference<List<Long>>() {};
		ResponseEntity<List<Long>> response = RestCallUtils.postRest(getPiaRootContextURL() + UPDATE_POSTINGSET_COM_STATUS, pstIds, PstPostingSetsDTO.class,
				typeRef, keycloackUtils, logger);
		if (response.getStatusCode().is2xxSuccessful()) {
			result = response.getBody();
			logger.info("Successfully updated com status, records updated = " + result.size());
			setPstAvailableCompleted(getPstAvailableReadyForUpdate());
			idsForRollback.clear();
		} else {
			logger.error("Failed to update com status");
			throw new Exception(response.getStatusCode().getReasonPhrase());
		}
		return result;
	}

}

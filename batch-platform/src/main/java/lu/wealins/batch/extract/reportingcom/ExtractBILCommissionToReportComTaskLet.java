/**
 * 
 */
package lu.wealins.batch.extract.reportingcom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.common.dto.liability.services.PstPostingSetsDTO;
import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;
import lu.wealins.common.dto.webia.services.ReportingComDTO;
import lu.wealins.common.dto.webia.services.TransactionDTO;
import lu.wealins.utils.RestCallUtils;

/**
 * @author xqv95
 *
 */
public class ExtractBILCommissionToReportComTaskLet extends AbstractExtractBILCommissionToReportComTaskLet {

	private static final String CODE_BIL_APPLICATION_PARAMETER = "BROKER_BIL";

	{
		logger = LogFactory.getLog(ExtractBILCommissionToReportComTaskLet.class);
	}

	private static final String POSTING_SET_AVAILABLE = "liability/transaction/postingsetavailable/reportingcom";
	private static final String INSERT_REPORT_COM = "webia/lissia-extract/reporting-com";
	private static final String INSERT_REPORT_COM_EXTERNAL_132 = "webia/lissia-extract/reporting-com-external-132";
	private static final String INSERT_REPORT_COM_EXTERNAL_133 = "webia/lissia-extract/reporting-com-external-133";
	private static final String EXTRACT_LISSIA_TRANSACTION = "liability/transaction/noexportedtransactionsreportingcom/pagination";
	private static final String GET_POLICY_AGENT_SHARE = "liability/policyagentshare/findby-agent-type-coverage";
	private static final String EXTRACT_LISSIA_EXTERNAL_FUNDS_132 = "liability/transaction/get-external-funds-reportingcom-132";
	private static final String EXTRACT_LISSIA_EXTERNAL_FUNDS_133 = "liability/transaction/get-external-funds-reportingcom-133";
	private static final String UPDATE_POSTINGSET_REPORT_STATUS = "liability/posting-sets/update-report-status";
	private static final String APPLICATION_PARAMETER = "webia/applicationParameter";
	
	private static final short REPORT_STATUS = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org. springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		ApplicationParameterDTO codeBilApplicationParam = getApplicationParameterCodeForBilCommission();
		int page = 0;
		Long lastId = 0L;
		if (pstAvailable == null) {
			loadPstAvailable(codeBilApplicationParam);
		}

		if (codeBilApplicationParam != null && !CollectionUtils.isEmpty(pstAvailable)) {
			
			do {
				logger.info("Extract Account Transaction for posting set ID=[" + getNextPstAvailableReady() + "] step " + (countPstAvailableCompleted()+ 1) + "/" + pstAvailable.size());
				
				// extract account transactions from Lissia
				lissiaTransaction = extractLissiaTransactions(page, lastId, codeBilApplicationParam);
				if (lissiaTransaction != null) {
					// get last id
					Optional<TransactionDTO> result = lissiaTransaction.getContent().stream().max((x, y) -> Long.compare(x.getTransaction0(), y.getTransaction0()));
					if (result.isPresent()) {
						lastId = result.get().getTransaction0();
					}

					// insert transactions into report com
					idsForRollback.addAll(insertToReportCom(lissiaTransaction.getContent()));
				}

				if (!lissiaTransaction.hasNext()) {
					setPstAvailableReadyForUpdate(getNextPstAvailableReady());
					lastId = 0L;
				}
				
				if (!CollectionUtils.isEmpty(getPstAvailableReadyForUpdate())) {
					updateReportStatus(asPstPostingSetsDTOs(getPstAvailableReadyForUpdate()));
				}

			} while (lissiaTransaction != null && !pstAvailableIsAllCompleted());
		}
		
		
		Collection<String> policyAgentShares = getPolicyAgentShare(codeBilApplicationParam);
		
		Collection<ReportingComDTO> resultExternal132 = Collections.synchronizedList(new ArrayList<ReportingComDTO>());
		Collection<ReportingComDTO> resultExternal133 = Collections.synchronizedList(new ArrayList<ReportingComDTO>());
		ExecutorService executorService = Executors.newFixedThreadPool(12);
		// Export external funds 132 and 133
		for (String policy : policyAgentShares) {
			executorService.submit(new Runnable(){
				@Override
				public void run() {
					synchronized (resultExternal132) {
						resultExternal132.addAll(extractLissiaExternalFunds132(policy));
					}
					synchronized (resultExternal133) {
						resultExternal133.addAll(extractLissiaExternalFunds133(policy));
					}
				}
			});				
		}
		// Close the task accepting queue
		executorService.shutdown();
		// Wait for all the tasks
		try {
			executorService.awaitTermination(2, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			executorService.shutdownNow();
			logger.error("Error during executor service.");
			throw e;
		}
		idsForRollback.addAll(insertToReportComExternal132(resultExternal132));
		idsForRollback.addAll(insertToReportComExternal133(resultExternal133));
		
		logger.info("Successfully ExtractBILCommissionToReportComJob");
		return RepeatStatus.FINISHED;
	}

	/**
	 * Load and Init posting set ids available
	 * 
	 * @param codeBilApplicationParam AGT_ID BIL
	 */
	private void loadPstAvailable(ApplicationParameterDTO codeBilApplicationParam) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("codeBilApplicationParam", String.valueOf(codeBilApplicationParam.getValue()));
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
		// get URI and query parameters
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", String.valueOf(page));
		params.add("size", String.valueOf(PAGE_SIZE));
		params.add("lastId", String.valueOf(lastId));
		params.add("codeBilApplicationParam", String.valueOf(codeBilApplicationParam.getValue()));
		params.add("currentPst", String.valueOf(getNextPstAvailableReady()));
		ParameterizedTypeReference<PageResult<TransactionDTO>> typeRef = new ParameterizedTypeReference<PageResult<TransactionDTO>>() {};
		logger.info("Trying to get transactions for Reporting Com from Lissia DB ...");
		ResponseEntity<PageResult<TransactionDTO>> response = RestCallUtils.get(getPiaRootContextURL() + EXTRACT_LISSIA_TRANSACTION, params, PageResult.class, typeRef, keycloackUtils, logger);
		PageResult<TransactionDTO> datas = response.getBody();
		logger.info("Successfully got transactions for Reporting Com from Lissia DB  ");
		return datas;
	}
	
	/**
	 * Get policy of policy agent share with agent, type and coverage
	 * 
	 * @param codeBilApplicationParam
	 * @return Collection<PolicyAgentShareDTO>
	 */
	private Collection<String> getPolicyAgentShare(ApplicationParameterDTO codeBilApplicationParam) {
		// get URI and query parameters
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("broker", String.valueOf(codeBilApplicationParam.getValue()));
		params.add("type", String.valueOf(5));
		params.add("coverage", String.valueOf(1));
		ParameterizedTypeReference<Collection<String>> typeRef = new ParameterizedTypeReference<Collection<String>>() {};
		logger.info("Trying to get PolicyAgentShareDTO from Lissia DB ...");
		ResponseEntity<Collection<String>> response = RestCallUtils.get(getPiaRootContextURL() + GET_POLICY_AGENT_SHARE, params, Collection.class, typeRef, keycloackUtils, logger);
		Collection<String> datas = response.getBody();
		logger.info("Successfully got PolicyAgentShareDTO from Lissia DB  ");
		return datas;
	}
	
	/**
	 * extract the external funds 132 from Lissia database
	 * 
	 * @param policyAgentShare
	 * 
	 * @return Collection<ReportingComDTO>
	 */
	private Collection<ReportingComDTO> extractLissiaExternalFunds132(String policy) {
		// get URI and query parameters
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		params.add("policy", policy);
		params.add("comDate", format.format(getComDate()));
		ParameterizedTypeReference<Collection<ReportingComDTO>> typeRef = new ParameterizedTypeReference<Collection<ReportingComDTO>>() {};
		logger.info("Trying to get external 132 for Reporting Com from Lissia DB ...");
		ResponseEntity<Collection<ReportingComDTO>> response = RestCallUtils.get(getPiaRootContextURL() + EXTRACT_LISSIA_EXTERNAL_FUNDS_132, params, PolicyAgentShareDTO.class, typeRef, keycloackUtils, logger);
		Collection<ReportingComDTO> datas = response.getBody();
		logger.info("Successfully got external 132 for Reporting Com from Lissia DB  ");
		return datas;
	}
	
	/**
	 * extract the external funds 133 from Lissia database
	 * 
	 * @param codeBilApplicationParam
	 * 
	 * @return Collection<ReportingComDTO>
	 */
	private Collection<ReportingComDTO> extractLissiaExternalFunds133(String policy) {
		// get URI and query parameters
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		params.add("policy", policy);
		params.add("comDate", format.format(getComDate()));
		ParameterizedTypeReference<Collection<ReportingComDTO>> typeRef = new ParameterizedTypeReference<Collection<ReportingComDTO>>() {};
		logger.info("Trying to get external 133 for Reporting Com from Lissia DB ...");
		ResponseEntity<Collection<ReportingComDTO>> response = RestCallUtils.get(getPiaRootContextURL() + EXTRACT_LISSIA_EXTERNAL_FUNDS_133, params, PolicyAgentShareDTO.class, typeRef, keycloackUtils, logger);
		Collection<ReportingComDTO> datas = response.getBody();
		logger.info("Successfully got external 133 for Reporting Com from Lissia DB  ");
		return datas;
	}

	/**
	 * extract the BIL commission to report com from database
	 * 
	 * @param transactions Collection of TransactionDTO
	 * 
	 * @return List<Long>
	 */
	private List<Long> insertToReportCom(Collection<TransactionDTO> transactions) {
		logger.info("Trying to post transactions for Reporting Com into WEBIA DB ...");
		ParameterizedTypeReference<List<Long>> typeRef = new ParameterizedTypeReference<List<Long>>() {};
		ResponseEntity<List<Long>> response = RestCallUtils.postRest(getPiaRootContextURL() + INSERT_REPORT_COM + "/" + getReportId(), transactions, Collection.class, typeRef, keycloackUtils, logger);
		List<Long> datas = response.getBody();
		logger.info("Sucessfully posted transfactions for Reporting Com into WEBIA DB");
		return datas;
	}
	
	/**
	 * Insert To ReportCom External 132
	 * 
	 * @param reportingComDTO Collection of ReportingComDTO
	 * 
	 * @return List<Long>
	 */
	private List<Long> insertToReportComExternal132(Collection<ReportingComDTO> reportingComDTO) {
		logger.info("Trying to post for Reporting Com external funds 132 into WEBIA DB ...");
		ParameterizedTypeReference<List<Long>> typeRef = new ParameterizedTypeReference<List<Long>>() {};
		ResponseEntity<List<Long>> response = RestCallUtils.postRest(getPiaRootContextURL() + INSERT_REPORT_COM_EXTERNAL_132 + "/" + getReportId(), reportingComDTO, Collection.class, typeRef, keycloackUtils, logger);
		List<Long> datas = response.getBody();
		logger.info("Sucessfully posted for Reporting Com external funds 132 into WEBIA DB");
		return datas;
	}
	
	/**
	 * Insert To ReportCom External 133
	 * 
	 * @param reportingComDTO Collection of ReportingComDTO
	 * 
	 * @return List<Long>
	 */
	private List<Long> insertToReportComExternal133(Collection<ReportingComDTO> reportingComDTO) {
		logger.info("Trying to post for Reporting Com external funds 133 into WEBIA DB ...");
		ParameterizedTypeReference<List<Long>> typeRef = new ParameterizedTypeReference<List<Long>>() {};
		ResponseEntity<List<Long>> response = RestCallUtils.postRest(getPiaRootContextURL() + INSERT_REPORT_COM_EXTERNAL_133 + "/" + getReportId(), reportingComDTO, Collection.class, typeRef, keycloackUtils, logger);
		List<Long> datas = response.getBody();
		logger.info("Sucessfully posted for Reporting Com external funds 133 into WEBIA DB");
		return datas;
	}
	
	/**
	 * Transform id list to posting_sets 
	 * 
	 * @param pstIds Collection of Long
	 * 
	 * @return List<PstPostingSetsDTO>
	 */
	private Set<PstPostingSetsDTO> asPstPostingSetsDTOs(Collection<Long> pstIds) {
		Set<PstPostingSetsDTO> pstDtos = new HashSet<>();
		for (Long wrapper : pstIds) {
			PstPostingSetsDTO pstDto = new PstPostingSetsDTO();
			pstDto.setPstId(wrapper);
			pstDto.setReportStatus(REPORT_STATUS);
			pstDtos.add(pstDto);
		}
		return pstDtos;
	}
	
	/**
	 * Update posting_sets report status
	 * 
	 * @param pstIds Collection of PstPostingSetsDTO
	 * 
	 * @return List<Long>
	 */
	private List<Long> updateReportStatus(Collection<PstPostingSetsDTO> pstIds) throws Exception {
		List<Long> result = new ArrayList<Long>();
		logger.info("Trying to update report status ...");
		ParameterizedTypeReference<List<Long>> typeRef = new ParameterizedTypeReference<List<Long>>() {};
		ResponseEntity<List<Long>> response = RestCallUtils.postRest(getPiaRootContextURL() + UPDATE_POSTINGSET_REPORT_STATUS, pstIds, PstPostingSetsDTO.class,
				typeRef, keycloackUtils, logger);
		if (response.getStatusCode().is2xxSuccessful()) {
			result = response.getBody();
			logger.info("Successfully updated report status, records updated = " + result.size());
			setPstAvailableCompleted(getPstAvailableReadyForUpdate());
			idsForRollback.clear();
		} else {
			logger.error("Failed to update report status");
			throw new Exception(response.getStatusCode().getReasonPhrase());
		}
		return result;
	}

}

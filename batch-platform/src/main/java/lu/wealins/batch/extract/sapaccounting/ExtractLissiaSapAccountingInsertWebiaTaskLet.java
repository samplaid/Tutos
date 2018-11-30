package lu.wealins.batch.extract.sapaccounting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.utils.RestCallUtils;

public class ExtractLissiaSapAccountingInsertWebiaTaskLet extends AbstractExtractLissiaSapAccountingTaskLet {
	private static final String INSERT_SAP_ACCOUNTING = "webia/lissia-extract/insert-sap-accounting";
	{
		logger = LogFactory.getLog(ExtractLissiaSapAccountingInsertWebiaTaskLet.class);
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// extract account transactions from Lissia
		pstIdWrapperList = new HashSet<>();
		List<PstIdWrapper> pstIdsWrapper = new ArrayList<>();

		ExtractLissiaSapAccountingDecider.flowExecutionStatus = FlowExecutionStatus.FAILED;

		if (lissiaTransaction == null)
			return RepeatStatus.FINISHED;

		if (CollectionUtils.isEmpty(lissiaTransaction.getContent())) {
			ExtractLissiaSapAccountingDecider.flowExecutionStatus = FlowExecutionStatus.COMPLETED;
			return RepeatStatus.FINISHED;
		}

		// insert transactions into SAP Accounting
		try {
			pstIdsWrapper.addAll(insertSAPACCOUNTING(lissiaTransaction.getContent()));

			// get pstIds and linked SapAccountingEntity ids
			Map<String, List<PstIdWrapper>> map = pstIdsWrapper.stream().collect(Collectors.groupingBy(PstIdWrapper::getPstId));
			map.forEach((pstId, acc) -> {
				PstIdWrapper wrapper = new PstIdWrapper();
				wrapper.setPstId(String.valueOf(pstId));
				acc.forEach(w -> {
					wrapper.getIdSapAccList().addAll(w.getIdSapAccList());
					idsForRollback.addAll(w.getIdSapAccList());
				});
				pstIdWrapperList.add(wrapper);
			});

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		ExtractLissiaSapAccountingDecider.flowExecutionStatus = FlowExecutionStatus.COMPLETED;

		return RepeatStatus.FINISHED;

	}

	/**
	 * insert transactions into SAP ACCOUNTING
	 * 
	 * @return
	 */
	private List<PstIdWrapper> insertSAPACCOUNTING(Collection<TransactionDTO> transactions) throws Exception {
		logger.info("Trying to post transactions into WEBIA DB ...");
		ParameterizedTypeReference<List<PstIdWrapper>> typeRef = new ParameterizedTypeReference<List<PstIdWrapper>>() {
		};
		ResponseEntity<List<PstIdWrapper>> response = RestCallUtils.postRest(getPiaRootContextURL() + INSERT_SAP_ACCOUNTING, transactions, Collection.class, typeRef, keycloackUtils, logger);
		if (!response.getStatusCode().is2xxSuccessful())
			throw new Exception(response.getStatusCode().getReasonPhrase());
		List<PstIdWrapper> datas = response.getBody();
		logger.info("Sucessfully posted transfactions into WEBIA DB");
		return datas;
	}

}

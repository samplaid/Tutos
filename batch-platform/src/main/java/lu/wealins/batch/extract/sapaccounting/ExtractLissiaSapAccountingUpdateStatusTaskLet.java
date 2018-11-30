package lu.wealins.batch.extract.sapaccounting;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.liability.services.PstPostingSetsDTO;
import lu.wealins.utils.CsvFileWriter;
import lu.wealins.utils.RestCallUtils;

public class ExtractLissiaSapAccountingUpdateStatusTaskLet extends AbstractExtractLissiaSapAccountingTaskLet {
	private static final String UPDATE_POSTINGSET_SAP_STATUS = "liability/posting-sets/update-sap-status";
	private static final String REMOVE_SAP_ACCOUNTING = "webia/lissia-extract/delete-sap-accounting";
	private static final short SAP_STATUS = 1;
	private CsvFileWriter failureFileWriter;
	private String failureFilePath;
	@Value("${extractLissiaSapAccountingErrorFile}")
	private String extractLissiaSapAccountingonErrorFile;

	{
		logger = LogFactory.getLog(ExtractLissiaSapAccountingUpdateStatusTaskLet.class);
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		ExtractLissiaSapAccountingDecider.flowExecutionStatus = FlowExecutionStatus.FAILED;
		try {
			if (!CollectionUtils.isEmpty(getPstAvailableReadyForUpdate())) {
				updateSapStatus(asPstPostingSetsDTOs(getPstAvailableReadyForUpdate()));
			} else {
				ExtractLissiaSapAccountingDecider.flowExecutionStatus = FlowExecutionStatus.COMPLETED;
			}
		} catch (Exception e) {
			doWebiaRollBack(e);
			throw e;
		}

		return RepeatStatus.FINISHED;
	}

	private void doWebiaRollBack(Exception e) throws IOException {
		logger.error("Error on updating Sap Status " + e);
		try {
			// Trying roll back
			removeFromSapAccounting(idsForRollback);

		} catch (Exception ex) {
			try {
				logger.error("Error on rolling back WEBIA DB " + ex);
				logger.info("Trying to write in a file SapAccountings on error");
				initFiles("LISSIA");
				failureFileWriter.append("Error on updating Sap Status \n " + e + " \n \n");
				failureFileWriter.append("pstId , IdSapAccounting \n ");
				if (idsForRollback != null)
					for (Long wrapper : idsForRollback) {
						failureFileWriter.append(wrapper + " , " + getPstAvailableReadyForUpdate() + "\n ");
					}
				;
				logger.info("Successfuly wrote in a file SapAccountings on error");
			} catch (Exception e2) {
				logger.error("Error on writing to file " + e2);
			} finally {
				failureFileWriter.close();
			}
		}
	}

	private Set<PstPostingSetsDTO> asPstPostingSetsDTOs(Collection<Long> pstIds) {
		Set<PstPostingSetsDTO> pstDtos = new HashSet<>();
		Date now = new Date();
		pstIds.forEach(wrapper -> {
			PstPostingSetsDTO pstDto = new PstPostingSetsDTO();
			pstDto.setPstId(wrapper);
			pstDto.setSapStatus(SAP_STATUS);
			pstDto.setSapExportDate(DateFormatUtils.format(now, "yyyyMMdd"));
			pstDtos.add(pstDto);
		});
		return pstDtos;
	}

	private Long updateSapStatus(Collection<PstPostingSetsDTO> pstIds) throws Exception {
		Long result = null;
		logger.info("Trying to update sap status ...");
		ParameterizedTypeReference<Long> typeRef = new ParameterizedTypeReference<Long>() {};
		ResponseEntity<Long> response = RestCallUtils.postRest(getPiaRootContextURL() + UPDATE_POSTINGSET_SAP_STATUS, pstIds, PstPostingSetsDTO.class,
				typeRef, keycloackUtils, logger);
		if (response.getStatusCode().is2xxSuccessful()) {
			result = response.getBody();
			ExtractLissiaSapAccountingDecider.flowExecutionStatus = FlowExecutionStatus.COMPLETED;
			logger.info("Successfully updated sap status, records updated = " + result);
			setPstAvailableCompleted(getPstAvailableReadyForUpdate());
		} else {
			logger.error("Failed to update sap status");
			throw new Exception(response.getStatusCode().getReasonPhrase());
		}
		return result;

	}

	private void removeFromSapAccounting(List<Long> sapAccIds) throws Exception {
		logger.info("Trying to roll back  WEBIA DB...");
		ParameterizedTypeReference<Long> typeRef = new ParameterizedTypeReference<Long>() {};
		ResponseEntity<Long> response = RestCallUtils.postRest(getPiaRootContextURL() + REMOVE_SAP_ACCOUNTING, sapAccIds, Long.class,
				typeRef, keycloackUtils, logger);
		if (response.getStatusCode().is2xxSuccessful()) {
			Long result = response.getBody();
			logger.info("Successfully rolled back  WEBIA DB = " + result);
		} else {
			logger.warn("Failed to roll back Sap Status");
			throw new Exception(response.getStatusCode().getReasonPhrase());
		}
	}

	/**
	 * init success and error writer
	 * 
	 * @param fileName
	 */
	private void initFiles(String origin) {
		File failureFile = new File(extractLissiaSapAccountingonErrorFile, "error" + origin + getCurrentDate() + ".txt");
		failureFilePath = failureFile.getAbsolutePath();
		failureFileWriter = new CsvFileWriter(failureFilePath);

	}

	/**
	 * get the current date
	 * 
	 * @return
	 */
	private String getCurrentDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		return sdf.format(date);
	}

}

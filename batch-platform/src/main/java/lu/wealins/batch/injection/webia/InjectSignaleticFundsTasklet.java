package lu.wealins.batch.injection.webia;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.admin.domain.support.JobParametersExtractor;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lu.wealins.rest.model.soliam.ExtractSoliamFundsResponse;
import lu.wealins.rest.model.soliam.Fund;
import lu.wealins.common.dto.webia.services.SasIsinDTO;
import lu.wealins.common.dto.webia.services.SaveSignaletiqueRequest;
import lu.wealins.common.dto.webia.services.SaveSignaletiqueResponse;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;
import lu.wealins.utils.RestCallUtils;

/**
 * Tasklet to inject signaletic funds into WEBIA system
 * 
 * @author xqv60
 *
 */
@EnableScheduling
public class InjectSignaleticFundsTasklet implements Tasklet {

	/**
	 * The Logger
	 */
	private Log logger = LogFactory.getLog(InjectSignaleticFundsTasklet.class);

	/**
	 * The DEFAULT PAGE SIZE FOR THE PAGINATION
	 */
	private final static int PAGE_SIZE = 500;

	/**
	 * Constant JOB Parameters extractor
	 */
	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();

	/**
	 * Constant job name
	 */
	private static final String INJECT_JOB = "injectSignaleticFundsJob";

	/**
	 * Constant save webia signaletic funds url
	 */
	private static final String SAVE_WEBIA_SIGNALETIC_FUNDS_URL = "webia/signaletique/save";

	/**
	 * Constant save webia signaletic funds url
	 */
	private static final String EXTRACT_SOLIAM_SIGNALETIC_FUNDS_URL = "soliam/funds/extract";

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Autowired
	private JobService jobService;


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info(INJECT_JOB + " started ");

		ExtractSoliamFundsResponse extractSoliamFundsResponse = null;
		int page = 0;

		do {
			// Extract information from Soliam
			extractSoliamFundsResponse = extractSoliamFunds(piaRootContextURL + EXTRACT_SOLIAM_SIGNALETIC_FUNDS_URL, page);
			// Inject them into WEBIA
			SaveSignaletiqueResponse saveIsinsResponse = saveIsins(piaRootContextURL + SAVE_WEBIA_SIGNALETIC_FUNDS_URL, extractSoliamFundsResponse);
			if (saveIsinsResponse != null && !saveIsinsResponse.isSuccess()) {
				logger.error("Error during the save of the isin send to bloomberg");
			}
			page++;
		} while (!extractSoliamFundsResponse.isLastPage());

		logger.info(INJECT_JOB + " finished ");
		return RepeatStatus.FINISHED;
	}

	@Scheduled(cron = "${injectSignaleticFundsCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the injection of soliam signaletic fund into webia");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(InetAddress.getLocalHost().getHostAddress() + " : executionTime=" + timeStamp);

		jobService.launch(INJECT_JOB,
				new JobParametersBuilder(jobParameters)
						.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
	}

	/**
	 * Extract the funds from SOLIAM
	 * 
	 * @param url the rest url
	 * @param page the page requested
	 * @return ExtractSoliamFundsResponse
	 */
	private ExtractSoliamFundsResponse extractSoliamFunds(String url, int page) {

		// get URI and query parameters
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", String.valueOf(page));
		params.add("size", String.valueOf(PAGE_SIZE));
		ParameterizedTypeReference<ExtractSoliamFundsResponse> responseType = new ParameterizedTypeReference<ExtractSoliamFundsResponse>() {
		};
		ResponseEntity<ExtractSoliamFundsResponse> response = RestCallUtils.get(url, params, ExtractSoliamFundsResponse.class, responseType,
				keycloackUtils, logger);

		if (response != null) {
			return response.getBody();
		} else {
			return new ExtractSoliamFundsResponse();
		}
	}

	/**
	 * Save the isin into Webia
	 * 
	 * @param url the rest url
	 * @param currentParition the list to check
	 * 
	 * @return
	 */
	private SaveSignaletiqueResponse saveIsins(String url, ExtractSoliamFundsResponse extractSoliamFundsResponse) {

		SaveSignaletiqueRequest request = new SaveSignaletiqueRequest();

		for (Fund fund : extractSoliamFundsResponse.getFunds()) {
			SasIsinDTO sasIsin = new SasIsinDTO();
			sasIsin.setCurrency(fund.getCurrency());
			sasIsin.setIsin(fund.getIsin());
			sasIsin.setStatusCode("RECEIVED");
			request.getIsinData().add(sasIsin);
		}
		ParameterizedTypeReference<SaveSignaletiqueResponse> responseType = new ParameterizedTypeReference<SaveSignaletiqueResponse>() {
		};
		ResponseEntity<SaveSignaletiqueResponse> response = RestCallUtils.postRest(url, request, SaveSignaletiqueRequest.class, responseType,
				keycloackUtils, logger);

		return response.getBody();
	}
}

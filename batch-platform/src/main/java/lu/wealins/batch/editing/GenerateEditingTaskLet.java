/**
 * 
 */
package lu.wealins.batch.editing;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.keycloak.representations.AccessTokenResponse;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import lu.wealins.rest.model.editing.DocumentGenerationRequest;
import lu.wealins.rest.model.editing.EditingRequest;
import lu.wealins.rest.model.editing.EditingRequestStatus;
import lu.wealins.rest.model.editing.SearchEditingRequestRequest;
import lu.wealins.rest.model.editing.SearchEditingRequestResponse;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;

/**
 * @author bqv55
 *
 */
@EnableScheduling
public class GenerateEditingTaskLet implements Tasklet {
	private Log logger = LogFactory.getLog(GenerateEditingTaskLet.class);

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Autowired

	private JobService jobService;

	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();
	private static final String EXTRACT_JOB = "generateEditingJob";
	private static final String SEARCH_REQUEST_EDITING = "searchEditingRequest";
	private static final String GENERATE_DOCUMENT = "generateDocument";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org. springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		SearchEditingRequestRequest request = new SearchEditingRequestRequest();
		request.setStatus(Arrays.asList(EditingRequestStatus.NEW));
		SearchEditingRequestResponse requestToTreat = post(getPiaRootContextURL() + SEARCH_REQUEST_EDITING, request, SearchEditingRequestResponse.class);

		for (EditingRequest editingRequest : requestToTreat.getEditingRequests()) {
			DocumentGenerationRequest documentGenerationRequest = new DocumentGenerationRequest();
			documentGenerationRequest.setRequest(editingRequest);
			post(getPiaRootContextURL() + GENERATE_DOCUMENT, documentGenerationRequest, String.class);
		}

		return RepeatStatus.FINISHED;
	}

	@Scheduled(cron = "${generateEditingCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the generate editing");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(InetAddress.getLocalHost().getHostAddress() + "executionTime=" + timeStamp);

		jobService.launch(EXTRACT_JOB,
				new JobParametersBuilder(jobParameters)
						.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
	}

	/**
	 * post method
	 * 
	 * @param url
	 * @param request
	 * @param responseType
	 * @return <Response, Request>
	 */
	public <Response, Request> Response post(String url, Request request, Class<Response> responseType) {

		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Request> entity = new HttpEntity<Request>(request, headers);
		RestTemplate template = new RestTemplate();

		return template.postForObject(url, entity, responseType);
	}

	/**
	 * @return the piaRootContextURL
	 */
	public String getPiaRootContextURL() {
		return piaRootContextURL;
	}

	/**
	 * @param piaRootContextURL the piaRootContextURL to set
	 */
	public void setPiaRootContextURL(String piaRootContextURL) {
		this.piaRootContextURL = piaRootContextURL;
	}

}

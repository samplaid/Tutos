/**
 * 
 */
package lu.wealins.batch.transactionDetails;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
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

import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;

/**
 * @author NGA
 *
 */
@EnableScheduling
public class GenerateTransactionDetailsTaskLet implements Tasklet {
	private Log logger = LogFactory.getLog(GenerateTransactionDetailsTaskLet.class);

	@Value("${webiaEndPoint}")
	private String webiaEndPoint;

	@Value("${webiaInjectTransactionTaxDetailsRoute}")
	private String webiaInjectTransactionTaxDetailsRoute;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Autowired

	private JobService jobService;

	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();
	private static final String EXTRACT_JOB = "generateTransactionDetailsJob";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org. springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		post(getContextURL(), null, String.class);
		return RepeatStatus.FINISHED;
	}

	@Scheduled(cron = "${injectTransactionTaxDetailsCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the generate transaction details");
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
	public String getContextURL() {
		return webiaEndPoint + webiaInjectTransactionTaxDetailsRoute;
	}


	/**
	 * @return the webiaEndPoint
	 */
	public String getWebiaEndPoint() {
		return webiaEndPoint;
	}

	/**
	 * @param webiaEndPoint
	 *            the webiaEndPoint to set
	 */
	public void setWebiaEndPoint(String webiaEndPoint) {
		this.webiaEndPoint = webiaEndPoint;
	}

	/**
	 * @return the webiaInjectTransactionTaxDetailsRoute
	 */
	public String getWebiaInjectTransactionTaxDetailsRoute() {
		return webiaInjectTransactionTaxDetailsRoute;
	}

	/**
	 * @param webiaInjectTransactionTaxDetailsRoute
	 *            the webiaInjectTransactionTaxDetailsRoute to set
	 */
	public void setWebiaInjectTransactionTaxDetailsRoute(String webiaInjectTransactionTaxDetailsRoute) {
		this.webiaInjectTransactionTaxDetailsRoute = webiaInjectTransactionTaxDetailsRoute;
	}

}

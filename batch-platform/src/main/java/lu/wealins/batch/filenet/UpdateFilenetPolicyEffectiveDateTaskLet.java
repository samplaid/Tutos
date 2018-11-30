/**
 * 
 */
package lu.wealins.batch.filenet;

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

import lu.wealins.rest.model.filenet.UpdatePolicyRequest;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;

/**
 * Batch for updating effective date on policy class into Filenet.
 * 
 * @author lax
 *
 */
@EnableScheduling
public class UpdateFilenetPolicyEffectiveDateTaskLet implements Tasklet {

	private static final Log LOGGER = LogFactory.getLog(UpdateFilenetPolicyEffectiveDateTaskLet.class);

	private static final String UPDATE_POLICY_JOB = "updateFilenetPolicyEffectiveDateJob";
	private static final String UPDATE_POLICY_URL = "updatePolicyEffectiveDate";

	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Autowired
	private KeycloakUtils keycloackUtils;

	@Autowired
	private JobService jobService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		UpdatePolicyRequest request = new UpdatePolicyRequest();
		request.setToken(keycloackUtils.getAccessToken().getToken());
		post(piaRootContextURL + UPDATE_POLICY_URL, request, Object.class);

		return RepeatStatus.FINISHED;
	}

	@Scheduled(cron = "${updateFilenetPolicyEffectiveDateCronExpression:}")
	private void scheduleExecute() throws Exception {
		LOGGER.info("Trying to execute the update of policy effective date into Filenet");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR
				.fromString(InetAddress.getLocalHost().getHostAddress() + " : executionTime=" + timeStamp);

		jobService.launch(UPDATE_POLICY_JOB, new JobParametersBuilder(jobParameters)
				.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
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

	/**
	 * post method
	 * 
	 * @param url
	 * @param request
	 * @param responseType
	 */
	private <Resp, Req> Resp post(String url, Req request, Class<Resp> responseType) {

		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Req> entity = new HttpEntity<Req>(request, headers);
		RestTemplate template = new RestTemplate();

		return template.postForObject(url, entity, responseType);
	}

}

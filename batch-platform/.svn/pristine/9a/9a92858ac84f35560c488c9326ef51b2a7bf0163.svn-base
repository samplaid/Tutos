/**
 * 
 */
package lu.wealins.batch.extract;

import java.io.File;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import lu.wealins.rest.model.acl.SearchACLByGroupRequest;
import lu.wealins.rest.model.acl.SearchACLByGroupResponse;
import lu.wealins.rest.model.acl.common.ACL;
import lu.wealins.utils.CsvFileWriter;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;

/**
 * @author xqt5q
 *
 */
@EnableScheduling
public class ExtractPoliciesACLTaskLet implements Tasklet {


	private Log logger = LogFactory.getLog(ExtractPoliciesACLTaskLet.class);

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;
	@Value("${aclInjectionSuccessFile}")
	private String aclInjectionSuccessFile;
	@Value("${aclInjectionErrorFile}")
	private String aclInjectionErrorFile;
	@Value("${pNoirGroupId}")
	private Long pNoirGroupId;
	@Value("${blockedGroupId}")
	private Long blockedGroupId;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Autowired

	private JobService jobService;

	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();
	private static final String EXTRACT_JOB = "extractPoliciesACLJob";
	private static final String ACL_BY_GROUP = "aclByGroup";

	private CsvFileWriter successFileWriter;
	private CsvFileWriter failureFileWriter;
	private String successFilePath;
	private String failureFilePath;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org. springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		try {
			initFiles();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			List<String> pNoirPolicies = extractPNoirPolicies(getpNoirGroupId());
			List<String> blockedPolicies = extractPNoirPolicies(getBlockedGroupId());
			writePNoirPolicies(pNoirPolicies);
			writeBlockedPolicies(blockedPolicies);
		} catch (Exception e) {
			failureFileWriter.append("Erreur de génération du fichier");
		} finally {
			successFileWriter.close();
			failureFileWriter.close();
		}

		return RepeatStatus.FINISHED;
	}

	@Scheduled(cron = "${extractPoliciesACLCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the extraction of policies ACL");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(InetAddress.getLocalHost().getHostAddress() + "executionTime=" + timeStamp);

		jobService.launch(EXTRACT_JOB,
				new JobParametersBuilder(jobParameters)
						.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
	}

	/**
	 * extract the policies by group from ACL database
	 * 
	 */
	private List<String> extractPNoirPolicies(Long groupId) {
		SearchACLByGroupRequest request = new SearchACLByGroupRequest();
		request.setGroupId(groupId);
		SearchACLByGroupResponse response = post(getPiaRootContextURL() + ACL_BY_GROUP, request, SearchACLByGroupResponse.class);
		List<String> ids = new ArrayList<String>();
		for (ACL acl : response.getAcls()) {
			String id = acl.getKey();
			if (acl.getReason() != null)
				id += ";" + acl.getReason();
			ids.add(id);
		}
		return ids;
	}

	/**
	 * write pNoir policies
	 * 
	 * @param pNoirPolicies list
	 */
	private void writePNoirPolicies(List<String> pNoirPolicies) {
		for (String policy : pNoirPolicies)
			successFileWriter.append("PNOIR;" + policy + "\n");

	}

	/**
	 * write blocked policies
	 * 
	 * @param blocked list
	 */
	private void writeBlockedPolicies(List<String> blockedPoliciesList) {
		for (String policy : blockedPoliciesList)
			successFileWriter.append("BLOCKED;" + policy + "\n");

	}

	/**
	 * post method
	 * 
	 * @param url
	 * @param request
	 * @param responseType
	 * 
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
	 * init success and error writer
	 * 
	 * @param fileName
	 */
	private void initFiles() {
		File successFile = new File(aclInjectionSuccessFile, "extract" + getCurrentDate() + ".csv");
		successFilePath = successFile.getAbsolutePath();
		successFileWriter = new CsvFileWriter(successFilePath);

		File failureFile = new File(aclInjectionErrorFile, "error" + getCurrentDate() + ".csv");
		failureFilePath = failureFile.getAbsolutePath();
		failureFileWriter = new CsvFileWriter(failureFilePath);

	}

	/**
	 * get the current date
	 * 
	 * 
	 */
	private String getCurrentDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		return sdf.format(date);
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
	 * @return the aclInjectionSuccessFile
	 */
	public String getAclInjectionSuccessFile() {
		return aclInjectionSuccessFile;
	}

	/**
	 * @param aclInjectionSuccessFile the aclInjectionSuccessFile to set
	 */
	public void setAclInjectionSuccessFile(String aclInjectionSuccessFile) {
		this.aclInjectionSuccessFile = aclInjectionSuccessFile;
	}

	/**
	 * @return the aclInjectionErrorFile
	 */
	public String getAclInjectionErrorFile() {
		return aclInjectionErrorFile;
	}

	/**
	 * @param aclInjectionErrorFile the aclInjectionErrorFile to set
	 */
	public void setAclInjectionErrorFile(String aclInjectionErrorFile) {
		this.aclInjectionErrorFile = aclInjectionErrorFile;
	}

	/**
	 * @return the pNoirGroupId
	 */
	public Long getpNoirGroupId() {
		return pNoirGroupId;
	}

	/**
	 * @param pNoirGroupId the pNoirGroupId to set
	 */
	public void setpNoirGroupId(Long pNoirGroupId) {
		this.pNoirGroupId = pNoirGroupId;
	}

	/**
	 * @return the blockedGroupId
	 */
	public Long getBlockedGroupId() {
		return blockedGroupId;
	}

	/**
	 * @param blockedGroupId the blockedGroupId to set
	 */
	public void setBlockedGroupId(Long blockedGroupId) {
		this.blockedGroupId = blockedGroupId;
	}

}

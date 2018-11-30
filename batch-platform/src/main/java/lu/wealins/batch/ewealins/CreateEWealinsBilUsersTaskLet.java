/**
 * 
 */
package lu.wealins.batch.ewealins;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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

import lu.wealins.rest.model.acl.AssignGroupToUserAclRequest;
import lu.wealins.rest.model.acl.AssignGroupToUserAclResponse;
import lu.wealins.rest.model.acl.CreateAclUserRequest;
import lu.wealins.rest.model.acl.CreateAclUserResponse;
import lu.wealins.rest.model.acl.CreateKeycloakUserRequest;
import lu.wealins.rest.model.acl.CreateKeycloakUserResponse;
import lu.wealins.rest.model.acl.PingAclUserRequest;
import lu.wealins.rest.model.acl.PingAclUserResponse;
import lu.wealins.rest.model.acl.SendEmailRequest;
import lu.wealins.rest.model.acl.SendEmailResponse;
import lu.wealins.rest.model.acl.common.User;
import lu.wealins.utils.CsvFileWriter;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;

/**
 * @author bqv55
 *
 */
@EnableScheduling
public class CreateEWealinsBilUsersTaskLet implements Tasklet {


	private Log logger = LogFactory.getLog(CreateEWealinsBilUsersTaskLet.class);

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Value("${bilUsersPath}")
	private String bilUsersPathConfig;

	@Value("${bilGroupId}")
	private Long bilGroupId;

	@Value("${bilKeycloakGroupName}")
	private String bilKeycloakGroupName;

	@Value("${bilProfilId}")
	private Long bilProfilId;

	@Value("${emailActions}")
	private List<String> emailActions;

	@Value("${keycloakClient}")
	private String keycloakClient;

	@Value("${keycloakRedirectUrl}")
	private String keycloakRedirectUrl;

	@Value("${bilUserErrorPath}")
	private String bilUserErrorPath;

	@Value("${bilUserSuccessPath}")
	private String bilUserSuccessPath;

	@Value("${bilUserTreatedPath}")
	private String bilUserTreatedPath;

	private CsvFileWriter failureFileWriter;
	private String failureFilePath;
	private CsvFileWriter successFileWriter;
	private String successFilePath;

	private static final String CREATE_ACL_USER = "createAclUser";
	private static final String CREATE_KEYCLOAK_USER = "createKeycloakUser";
	private static final String ASSIGN_ACL_USER_TO_GROUP = "assignAclUserToGroup";
	private static final String PING_ACL_USER = "pingAclUser";
	private static final String SEND_KEYCLOAK_EMAIL = "sendKeycloakEmail";

	private static final String NEW_LINE_SEPARATOR = "\n";

	/**
	 * The path where correct receipt files will be
	 */
	private Path bilUsersPath;

	/**
	 * The destination path of the file while treated
	 */
	private Path bilUsersTreatedPath;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Autowired
	private JobService jobService;

	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();
	private static final String EXTRACT_JOB = "generateEditingJob";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org. springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		initPaths();

		// Parse Bil user injection folder
		List<Path> files = Files.list(bilUsersPath).collect(Collectors.toList());

		for (Path file : files) {
			Path fileName = file.getFileName();
			logger.info("Bil user file parsed : " + fileName);
			initFiles(fileName.toString());
			CSVFormat csvFormat = CSVFormat.newFormat(';');
			csvFormat.withRecordSeparator("\r\n");
			csvFormat.withHeader("Matricule SAP", "Code logon", "Titre", "Nom", "Prénom", "Nom agence", "Email", "Ressource", "Type accés", "Accés produits employés");
			csvFormat.withSkipHeaderRecord(true);
			try (
					FileReader fileReader = new FileReader(file.toFile());
					CSVParser csvParser = new CSVParser(fileReader, csvFormat);) {
				String name;
				String firstname;
				String email;
				String matricule;

				for (CSVRecord record : csvParser.getRecords()) {
					matricule = record.get(1);
					try {
						if (matricule.equals("Code logon")) {
							continue;
						}

						PingAclUserRequest pingAclUserRequest = new PingAclUserRequest();
						pingAclUserRequest.setLogin(matricule);
						PingAclUserResponse pingAclUserResponse = post(getPiaRootContextURL() + PING_ACL_USER, pingAclUserRequest, PingAclUserResponse.class);
						if (pingAclUserResponse.isExist()) {
							logger.info("User with matricule " + matricule + " already exists");
							continue;
						}

						name = record.get(3);
						firstname = record.get(4);
						email = record.get(6);

						// Create ACL user (useraccess database)
						CreateAclUserResponse createAclUserResponse = createAclUser(firstname, matricule, name);

						// Assign the newly created acl user to the BIL group (useraccess database)
						assignAclUserToBilGroup(createAclUserResponse.getUser().getId());

						// Create and assign to a keycloak group the keycloak user
						CreateKeycloakUserResponse createKeycloakUserResponse = createKeycloakUser(createAclUserResponse.getUser(), email);

						// Send email
						sendEmail(createKeycloakUserResponse.getId());

						// Log and write on the success file
						logger.info("User created and mail sent for user with matricule " + matricule);
						successFileWriter.append("User created and mail sent for user with matricule " + matricule);

					} catch (Exception e) {
						// Log and write on the failure file
						failureFileWriter.append("Error during the creation of the user with matricule : " + matricule + NEW_LINE_SEPARATOR);
						logger.error("Error during the creation of the user with matricule : " + matricule);
					}
				}
			} catch (IOException e) {
				logger.error("Error during parsing the file : " + fileName);
			} finally {
				try {
					// Move the file on the treated folder
					bilUsersTreatedPath = Paths.get(bilUserTreatedPath + fileName);
					Files.move(file, bilUsersTreatedPath);
					logger.info("File " + fileName.toString() + " moved here : " + bilUserTreatedPath);

					// Close the success and failure files
					successFileWriter.close();
					failureFileWriter.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
			logger.info("End of parsing : " + fileName);
		}

		return RepeatStatus.FINISHED;

	}

	private CreateAclUserResponse createAclUser(String firstname, String matricule, String name) {
		CreateAclUserRequest createAclUserRequest = new CreateAclUserRequest();
		createAclUserRequest.setFirstname(firstname);
		createAclUserRequest.setLogin(matricule);
		createAclUserRequest.setMatricule(matricule);
		createAclUserRequest.setName(name);
		createAclUserRequest.setProfil(bilProfilId);
		CreateAclUserResponse createAclUserResponse = post(getPiaRootContextURL() + CREATE_ACL_USER, createAclUserRequest, CreateAclUserResponse.class);

		logger.info("ACL user created for matricule : " + matricule);

		return createAclUserResponse;
	}

	private void assignAclUserToBilGroup(Long userId) {
		AssignGroupToUserAclRequest assignGroupToUserAclRequest = new AssignGroupToUserAclRequest();
		assignGroupToUserAclRequest.setUserId(userId);
		assignGroupToUserAclRequest.setGroupId(bilGroupId);
		AssignGroupToUserAclResponse assignGroupToUserAclResponse = post(getPiaRootContextURL() + ASSIGN_ACL_USER_TO_GROUP, assignGroupToUserAclRequest,
				AssignGroupToUserAclResponse.class);
		logger.info("ACL user " + assignGroupToUserAclResponse.getUser().getLogin() + " assigned to BIL group");
	}

	private CreateKeycloakUserResponse createKeycloakUser(User aclUser, String email) {
		CreateKeycloakUserRequest createKeycloakUserRequest = new CreateKeycloakUserRequest();
		createKeycloakUserRequest.setFirstname(aclUser.getFirstName());
		createKeycloakUserRequest.setEmail(email);
		createKeycloakUserRequest.setLastname(aclUser.getName());
		createKeycloakUserRequest.setUsername(aclUser.getLogin());
		createKeycloakUserRequest.setGroups(java.util.Arrays.asList(bilKeycloakGroupName));
		CreateKeycloakUserResponse createKeycloakUserResponse = post(getPiaRootContextURL() + CREATE_KEYCLOAK_USER, createKeycloakUserRequest, CreateKeycloakUserResponse.class);

		logger.info("Keycloak user created with matricule " + createKeycloakUserResponse.getUsername());

		return createKeycloakUserResponse;
	}

	private void sendEmail(String userKeycloakId) {
		SendEmailRequest emailRequest = new SendEmailRequest();
		emailRequest.setActions(emailActions);
		emailRequest.setClient(keycloakClient);
		emailRequest.setRedirectUrl(keycloakRedirectUrl);
		emailRequest.setUserId(userKeycloakId);
		SendEmailResponse sendEmailResponse = post(getPiaRootContextURL() + SEND_KEYCLOAK_EMAIL, emailRequest, SendEmailResponse.class);
	}

	/**
	 * Initialize paths
	 */
	private void initPaths() {
		bilUsersPath = Paths.get(bilUsersPathConfig);
	}

	/**
	 * init success and error writer
	 * 
	 * @param fileName
	 */
	private void initFiles(String fileName) {
		File failureFile = new File(bilUserErrorPath, fileName);
		failureFilePath = failureFile.getAbsolutePath();
		failureFileWriter = new CsvFileWriter(failureFilePath);

		File successFile = new File(bilUserSuccessPath, fileName);
		successFilePath = successFile.getAbsolutePath();
		successFileWriter = new CsvFileWriter(successFilePath);
	}

	//@Scheduled(cron = "${bilUserInjectionCronExpression:}") 01/08/2018: this is launching generate editing job
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the generate editing");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(InetAddress.getLocalHost().getHostAddress() + "executionTime=" + timeStamp);

		jobService.launch(EXTRACT_JOB,
				new JobParametersBuilder(jobParameters).addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
	}

	/**
	 * post method
	 * 
	 * @param url
	 * @param request
	 * @param responseType
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

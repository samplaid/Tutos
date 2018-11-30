package lu.wealins.batch.injection;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import lu.wealins.rest.model.ClientInjectionRequest;
import lu.wealins.rest.model.ClientInjectionResponse;
import lu.wealins.service.BatchUtilityService;
import lu.wealins.service.RestUtilityService;

/**
 * @author xqv66
 *
 */
@Component
public class InjectionTaskLet implements Tasklet {

	private static final String LIABILITY_CLIENT_INJECTION = "liability-clientInjection";

	private static final String PATH = "path";
	private static final String CSV_EXTENSION = ".csv";

	private Log logger = LogFactory.getLog(InjectionTaskLet.class);

	@Autowired
	private BatchUtilityService batchUtilityService;
	@Value("${piaRootContextURL}")
	private String piaRootContextURL;
	@Autowired
	private RestUtilityService restUtilityService;

	private String injectionSuccessPath;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org. springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		String path = batchUtilityService.getStringJobParameter(chunkContext, PATH);

		if (path == null) {
			throw new IllegalArgumentException("Argument 'path' must be specified with the injection path.");
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		File file = new File(path);
		String[] fileNameNoExtension = file.getName().split("\\.");

		File succesFile = new File(getInjectionSuccessPath(), fileNameNoExtension[0] + CSV_EXTENSION);

		if (succesFile.exists()) {
			ClientInjectionRequest request = new ClientInjectionRequest();
			request.setFilePath(succesFile.getAbsolutePath());

			logger.info("Call the import service for " + succesFile.getAbsolutePath() + ".");
			ClientInjectionResponse response = restUtilityService.post(getInjectionUrl(), request, ClientInjectionResponse.class);

			if (!response.isSuccess()) {
				logger.error("Failed importation : " + response.getErrorMessage());

				throw new JobExecutionException(response.getErrorMessage());
			}

			logger.info("Successfully imported " + succesFile.getAbsolutePath() + ".");

		} else {
			logger.info("No success file generated");
		}

		return RepeatStatus.FINISHED;
	}

	public String getInjectionUrl() {
		return piaRootContextURL + LIABILITY_CLIENT_INJECTION;
	}

	public String getInjectionSuccessPath() {
		return injectionSuccessPath;
	}

	public void setInjectionSuccessPath(String injectionSuccessPath) {
		this.injectionSuccessPath = injectionSuccessPath;
	}
}

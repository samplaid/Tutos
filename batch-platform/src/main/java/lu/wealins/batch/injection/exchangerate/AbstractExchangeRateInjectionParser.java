package lu.wealins.batch.injection.exchangerate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lu.wealins.filepoller.JobExecutor;
import lu.wealins.rest.model.BatchInjectionControlResponse;
import lu.wealins.rest.model.Error;
import lu.wealins.service.RestUtilityService;
import lu.wealins.utils.CsvFileWriter;

@Component
public abstract class AbstractExchangeRateInjectionParser<Req> {

	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String CSV_EXTENSION = ".csv";
	public static final String COMMA_DELIMITER = ",";
	public static final String SPACE_DELIMITER = " ";
	public static final String SEMICOLON_DELIMITER = ";";
	public static final String DATE_DELIMITER = "-";
	public static final String DOT_DELIMITER = ".";

	protected Log logger = LogFactory.getLog(AbstractExchangeRateInjectionParser.class);

	private CsvFileWriter successFileWriter;
	private CsvFileWriter failureFileWriter;
	private String successFilePath;
	private String failureFilePath;

	private JobExecutor jobExecutor;

	@Autowired
	private RestUtilityService restUtilityService;
	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	public File parse(File input) {
		logger.info("Parsing : " + input.getAbsolutePath());

		try (Stream<String> stream = Files.lines(Paths.get(input.getAbsolutePath()))) {
			initFiles(input.getName());
			stream.forEach((line) -> {
				try {
					parseLine(line);
				} catch (Exception e) {
					failureFileWriter.append("Erreur parsing line " + line + NEW_LINE_SEPARATOR);
					logger.error("Erreur parsing line " + line + ". Exception: " + e);
				}
			});

		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				successFileWriter.close();
				failureFileWriter.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}

		logger.info("End of Parsing");
		
		//Inject exchange rates into Lissia
		try {
			jobExecutor.launchJob(new File(successFilePath));
		} 
		catch (JobExecutionException e) {
			e.printStackTrace();
		}

		return input;
	}


	private void initFiles(String fileName) {
		String[] fileNameNoExtension = fileName.split("\\.");

		File successFile = new File(getInjectionSuccessPath(), fileNameNoExtension[0] + CSV_EXTENSION);
		successFilePath = successFile.getAbsolutePath();
		successFileWriter = new CsvFileWriter(successFilePath);
		File failureFile = new File(getInjectionFailurePath(), fileNameNoExtension[0] + CSV_EXTENSION);
		failureFilePath = failureFile.getAbsolutePath();
		failureFileWriter = new CsvFileWriter(failureFilePath);
		
	
	}

	private void parseLine(String line) {
		Req request = buildInjectionControlRequest(line);
		String url = piaRootContextURL + getInjectionControlUrl();
		BatchInjectionControlResponse response = restUtilityService.post(url, request, BatchInjectionControlResponse.class);

		if (!response.getLines().isEmpty()) {
			List<String> lines = response.getLines();
			for (String lineRes : lines) {
				successFileWriter.append(lineRes + NEW_LINE_SEPARATOR);
				logger.info("Append " + lineRes + " to " + successFilePath);
			}

		}

		if (!response.getErrors().isEmpty()) {
			List<Error> errors = response.getErrors();
			for (Error error : errors) {
				failureFileWriter.append(error.getCode() + COMMA_DELIMITER + error.getMessage() + NEW_LINE_SEPARATOR);
				logger.info("Append " + error.getCode() + COMMA_DELIMITER + error.getMessage() + " to " + failureFilePath);
			}
		}
	}

	public <Response, Request> Response post(String url, Request request, Class<Response> responseType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Request> entity = new HttpEntity<Request>(request, headers);
		RestTemplate template = new RestTemplate();

		return template.postForObject(url, entity, responseType);
	}

	public abstract String getInjectionControlUrl();

	public abstract String getInjectionFailurePath();

	public abstract String getInjectionSuccessPath();

	public abstract Req buildInjectionControlRequest(String line);
	
	public void setJobExecutor(JobExecutor jobExecutor) {
		this.jobExecutor = jobExecutor;
	}
}

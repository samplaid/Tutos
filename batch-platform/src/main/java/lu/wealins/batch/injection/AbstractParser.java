package lu.wealins.batch.injection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlResponse;
import lu.wealins.rest.model.CloturedVniInjectionControlRequest;
import lu.wealins.utils.CsvFileWriter;
import lu.wealins.utils.KeycloakUtils;
import lu.wealins.utils.RestCallUtils;

@Component
public abstract class AbstractParser<Req> {

	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String CSV_EXTENSION = ".csv";
	public static final String COMMA_DELIMITER = ",";
	public static final String SPACE_DELIMITER = " ";
	public static final String SEMICOLON_DELIMITER = ";";

	protected Log logger = LogFactory.getLog(AbstractParser.class);

	private CsvFileWriter successFileWriter;
	private CsvFileWriter failureFileWriter;
	private String successFilePath;
	private String failureFilePath;

	@Autowired
	KeycloakUtils keycloackUtils;
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
		CloturedVniInjectionControlRequest request = (CloturedVniInjectionControlRequest) buildInjectionControlRequest(
				line);
		String url = getInjectionControlUrl();
		ParameterizedTypeReference<CloturedVniInjectionControlResponse> typeRef = new ParameterizedTypeReference<CloturedVniInjectionControlResponse>() {
		};
		ResponseEntity<CloturedVniInjectionControlResponse> checkResponse = RestCallUtils.postRest(url, request,
				CloturedVniInjectionControlRequest.class, typeRef, keycloackUtils, logger);

		CloturedVniInjectionControlResponse response = checkResponse.getBody();

		if (response.getSuccessLine() != null && !response.getSuccessLine().isEmpty()) {
			String successlines = response.getSuccessLine();
			successFileWriter.append(successlines + NEW_LINE_SEPARATOR);
		}

		if (response.getErrorLine() != null && !response.getErrorLine().isEmpty()) {
			String errorLine = response.getErrorLine();
			failureFileWriter.append(errorLine + NEW_LINE_SEPARATOR);
			logger.info("Append " + errorLine);
		}
	}

	public abstract String getInjectionControlUrl();

	public abstract String getInjectionFailurePath();

	public abstract String getInjectionSuccessPath();

	public abstract Req buildInjectionControlRequest(String line);

}

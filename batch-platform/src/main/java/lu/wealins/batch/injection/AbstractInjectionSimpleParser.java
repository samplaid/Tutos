package lu.wealins.batch.injection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import lu.wealins.rest.model.BatchInjectionControlResponse;
import lu.wealins.rest.model.Error;
import lu.wealins.utils.CsvFileWriter;

@Component
public abstract class AbstractInjectionSimpleParser {

	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String TXT_EXTENSION = ".txt";
	public static final String COMMA_DELIMITER = ",";
	public static final String SPACE_DELIMITER = " ";
	public static final String SEMICOLON_DELIMITER = ";";
	public static final String TABULATION_DELIMITER = "\t";

	protected Log logger = LogFactory.getLog(AbstractInjectionSimpleParser.class);

	private CsvFileWriter successFileWriter;
	private CsvFileWriter failureFileWriter;
	private String successFilePath;
	private String failureFilePath;

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

		File successFile = new File(getInjectionSuccessPath(), fileNameNoExtension[0] + TXT_EXTENSION);
		successFilePath = successFile.getAbsolutePath();
		successFileWriter = new CsvFileWriter(successFilePath);
		File failureFile = new File(getInjectionFailurePath(), fileNameNoExtension[0] + TXT_EXTENSION);
		failureFilePath = failureFile.getAbsolutePath();
		failureFileWriter = new CsvFileWriter(failureFilePath);
	}

	private void parseLine(String line) {
		BatchInjectionControlResponse response = buildInjectionControlRequest(line);

		if (BooleanUtils.isTrue(response.isSuccess())) {
			List<String> lines = response.getLines();
			for (String lineRes : lines) {
				successFileWriter.append(lineRes + NEW_LINE_SEPARATOR);
				logger.info("Append " + lineRes + " to " + successFilePath);
			}

		} else {
			List<Error> errors = response.getErrors();
			for (Error error : errors) {
				failureFileWriter.append(error.getCode() + COMMA_DELIMITER + error.getMessage() + NEW_LINE_SEPARATOR);
				logger.info("Append " + error.getCode() + COMMA_DELIMITER + error.getMessage() + " to " + failureFilePath);
			}
		}
	}

	public abstract String getInjectionFailurePath();

	public abstract String getInjectionSuccessPath();

	public abstract BatchInjectionControlResponse buildInjectionControlRequest(String line);
}

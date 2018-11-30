package lu.wealins.batch.injection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lu.wealins.persistence.entity.LissiaFilesInjectionEntity;
import lu.wealins.persistence.repository.LissiaFilesInjectionRepository;
import lu.wealins.rest.model.BatchInjectionControlResponse;
import lu.wealins.rest.model.Error;
import lu.wealins.utils.CsvFileWriter;

@Component
public abstract class AbstractInjectionFilenameValidation<Req> {
	
	@Autowired
	private LissiaFilesInjectionRepository lissiaFilesInjectionRepository;
	
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String CSV_EXTENSION = ".csv";
	public static final String COMMA_DELIMITER = ",";
	public static final String SPACE_DELIMITER = " ";
	public static final String SEMICOLON_DELIMITER = ";";
	private static final int ACTIVE = 1;

	protected Log logger = LogFactory.getLog(AbstractInjectionFilenameValidation.class);

	private CsvFileWriter successFileWriter;
	private CsvFileWriter failureFileWriter;
	private String successFilePath;
	private String failureFilePath;

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	public File parse(File input) {
		logger.info("Filename validation : " + input.getAbsolutePath());

		initFiles(input.getName());
		try {
			fileValidationSize(input);
			filenameValidation(input);
		} catch (Exception e) {
			failureFileWriter.append("Erreur filename validation " + input.getName() + NEW_LINE_SEPARATOR);
			logger.error("Erreur filename validation " + input.getName() + ". Exception: " + e);
		}

		try {
			successFileWriter.close();
			failureFileWriter.close();
		} catch (IOException e) {
			logger.error(e);
		}		

		logger.info("End of Filename validation");

		return input;
	}
	
	/**
	 * If size > 200Kb import not working
	 * 
	 * @param input
	 * @throws Exception 
	 */
	private void fileValidationSize(File input) throws Exception {
		long fileSizeInBytes = input.length();
		// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
		long fileSizeInKB = fileSizeInBytes / 1024;
		
		if (fileSizeInKB > 200L) {
			failureFileWriter.append("File is too big > 200KB, please use LISSIA interface. " + NEW_LINE_SEPARATOR);
			throw new Exception("File is too big > 200KB, please use LISSIA interface.");
		}
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

	private void filenameValidation(File input) {
		BatchInjectionControlResponse response = checkLissiaFilesInjection(input);

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
	
	private BatchInjectionControlResponse checkLissiaFilesInjection(File input) {
		logger.info("Check LissiaFilesInjectionControlRequest for filename " + input.getName());
		BatchInjectionControlResponse response = new BatchInjectionControlResponse();
		response.setSuccess(Boolean.FALSE);
		
		List<String> successLines = new ArrayList<String>();
		List<Error> errorLines = new ArrayList<Error>();
		// Search if filename already injected.
		List<LissiaFilesInjectionEntity> filenameAlreadyInjected = lissiaFilesInjectionRepository.findByFileNameAndStatus(input.getName(), ACTIVE);
		
		if (CollectionUtils.isEmpty(filenameAlreadyInjected)) {
			buildLissiaFilesInjection(input.getName());
			response.setSuccess(Boolean.TRUE);
			
			try (Stream<String> stream = Files.lines(Paths.get(input.getAbsolutePath()))) {
				initFiles(input.getName());
				stream.forEach((line) -> {
					try {
						successLines.add(line);
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
			
		} else {
			Error error = new Error();
			error.setCode("Already exists");
			error.setMessage("Filename " + input.getName() + " already injected.");
			errorLines.add(error);
		}
		response.setLines(successLines);
		response.setErrors(errorLines);
		
		return response;
	}
	
	/**
	 * Build lissia files injection line.
	 * @param fileName
	 */
	private void buildLissiaFilesInjection(String fileName) {
		logger.info("Build lissia files injection line : " + fileName);
		LissiaFilesInjectionEntity lissiaFilesInjectionEntity = new LissiaFilesInjectionEntity();
		lissiaFilesInjectionEntity.setFileName(fileName);
		lissiaFilesInjectionEntity.setStatus(ACTIVE);
		lissiaFilesInjectionEntity.setCreationDate(new Date());
		lissiaFilesInjectionRepository.save(lissiaFilesInjectionEntity);
	}

	public abstract String getInjectionControlUrl();

	public abstract String getInjectionFailurePath();

	public abstract String getInjectionSuccessPath();

	public abstract Req buildInjectionControlRequest(File file);

}

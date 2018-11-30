package lu.wealins.batch.injection.bloomberg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lu.wealins.camel.utils.MessageUtils;
import lu.wealins.rest.model.BatchInjectionControlResponse;
import lu.wealins.rest.model.Error;
import lu.wealins.common.dto.webia.services.CheckSignaletiqueIsinRequest;
import lu.wealins.common.dto.webia.services.CheckSignaletiqueIsinResponse;
import lu.wealins.common.dto.webia.services.SasIsinDTO;
import lu.wealins.common.dto.webia.services.SaveSignaletiqueRequest;
import lu.wealins.common.dto.webia.services.SaveSignaletiqueResponse;
import lu.wealins.utils.CsvFileWriter;
import lu.wealins.utils.ExcelFileWriter;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.KeycloakUtils;
import lu.wealins.utils.RestCallUtils;

/**
 * Parser for the bloomberg response signaletic file.
 * 
 * @author xqv60
 *
 */
@Component
public class SignaleticParser implements MessageListener {

	private static final String N_S = "N.S.";
	private static final String N_D = "N.D.";
	private static final String N_A = "N.A.";

	private static final String FIELD_UNKNOWN = "FLD UNKNOWN";

	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String TXT_EXTENSION = ".txt";
	private static final String EXCEL_EXTENSION = ".xlsx";
	public static final String COMMA_DELIMITER = ",";

	/** Name of the Excel sheet name for the generated files. */
	private static final String OUTPUT_SHEETNAME = "results";

	/** Markup to detect for the start of fields. */
	private static final String START_OF_FIELDS = "START-OF-FIELDS";

	/** Markup to detect for the end of fields. */
	private static final String END_OF_FIELDS = "END-OF-FIELDS";
	
	/** Default value for ID_BB_COMPANY. */
	private static final String DEFAULT_ID_BB_COMPANY = "UNKNOWN";
	
	/** Default value for ISSUER. */
	private static final String DEFAULT_ISSUER = "UNDEFIN";

	/** Date formatter for the 'Date de traitement du fichier' cell. */
	final DateFormat format_treatment_date = new SimpleDateFormat("dd/MM/YYYY");

	/** Date formatter for the 'Source' cell. */
	final DateFormat format_source_date = new SimpleDateFormat("YYYYMM");

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Autowired
	KeycloakUtils keycloackUtils;

	/** Constant save webia signaletic funds url. */
	private static final String SAVE_WEBIA_SIGNALETIC_FUNDS_URL = "webia/signaletique/save";

	/** Constant save webia signaletic funds url */
	private static final String CHECK_WEBIA_SIGNALETIC_FUNDS_URL = "webia/signaletique/isin/exist";

	/** Store the list of the columns name and their order. */
	private static final List<String> columns = new ArrayList<>();

	/** flag indicating that we are currently parsing the columns names. */
	private boolean isParsingColumnName = false;

	/** Instance of the logger. */
	private Log logger = LogFactory.getLog(SignaleticParser.class);

	/********************/
	/** File Writers */
	/********************/

	/** CSV failure file */
	private CsvFileWriter failureFileWriter;
	/** Excel output file 1 */
	private ExcelFileWriter outputFile1Writer;
	/** Excel output file 2 */
	private ExcelFileWriter outputFile2Writer;

	/**
	 * Error folder with signaletic information
	 */
	@Value("${signaleticInjectionFailurePath:}")
	private String failureFilePath;

	/**
	 * Success folder with excel files generated
	 */
	@Value("${signaleticInjectionSuccessPath:}")
	private String successFilePath;
	
	/**
	 * Input folder containing signaletic responses
	 */
	@Value("${signaleticInjectionInputFolder}")
	private String inputFolder;

	/** Map containing the error codes to detect and their associated meaning. */
	private static Map<String, String> errorsCode = new HashMap<>();
	static {
		errorsCode.put("9", "Asset class not supported for BVAL Tier1 pricing");
		errorsCode.put("10", "Bloomberg cannot find the security as specified");
		errorsCode.put("11", "Restricted Security");
		errorsCode.put("123", "User not authorized for private loan (PRPL)");
		errorsCode.put("605", "Invalid macro value");
		errorsCode.put("988", "System Error on security level");
		errorsCode.put("989", "Unrecognized pricing source");
		errorsCode.put("990", "System Error. Contact Product Support and Technical Assistance");
		errorsCode.put("991", "Invalid override value (e.g., bad date or number) or Maximum number of overrides (20) exceeded");
		errorsCode.put("992", "Unknown override field");
		errorsCode.put("993", "Maximum number of overrides exceeded");
		errorsCode.put("994", "Permission denied");
		errorsCode.put("995", "Maximum number of fields exceeded");
		errorsCode.put("996", "Maximum number of data points exceeded (some data for this security is missing)");
		errorsCode.put("997", "General override error (e.g., formatting error)");
		errorsCode.put("998", "Security identifier type (e.g., CUSIP) is not recognized");
		errorsCode.put("999", "Unloadable security");
		errorsCode.put(" ", "requested field and security combination is not applicable");
		errorsCode.put(N_A, "Data is missing, because Bloomberg does not have the data");
		errorsCode.put(N_D, "Not downloadable, because user does not have permission to download the field");
		errorsCode.put(N_S,
				"Not subscribed, because user 1) is not entitled to download requested field and security combination, or 2) has hit the montly limits on a test account, or 3) user has not flagged correct field category in request header");
		errorsCode.put(FIELD_UNKNOWN, "Field unknown");
	}

	/** Ordered list of the columns of the 'sig' file to generate. */
	private static List<String> excelColumnNamesFile1 = new ArrayList<>();

	/** Ordered list of the columns of the 'issuer' file to generate. */
	private static List<String> excelColumnNamesFile2 = new ArrayList<>();

	static {
		excelColumnNamesFile1.add(JobConstantsUtils.TITRE_PRESENT_SOLIAM);
		excelColumnNamesFile1.add("Banque");
		excelColumnNamesFile1.add("ID_ISIN");
		excelColumnNamesFile1.add("Devise de la valeur");
		excelColumnNamesFile1.add("Libellé long");
		excelColumnNamesFile1.add("Type Valeur");
		excelColumnNamesFile1.add("Mode de comptabilisation");
		excelColumnNamesFile1.add("Mode de cotation");
		excelColumnNamesFile1.add("Classification CAA");
		excelColumnNamesFile1.add("Actif éligible BEL");
		excelColumnNamesFile1.add("Actif éligible UK");
		excelColumnNamesFile1.add("Actif spécifique");
		excelColumnNamesFile1.add("BBG_NAME");
		excelColumnNamesFile1.add("CIE_DES");
		excelColumnNamesFile1.add("EXCH_NAMES");
		excelColumnNamesFile1.add("LISTED_EXCH");
		excelColumnNamesFile1.add("FUND_ASSET_ALLOC_EQUITY");
		excelColumnNamesFile1.add("FUND_BENCHMARK_PRIM");
		excelColumnNamesFile1.add("COUNTRY_FULL_NAME");
		excelColumnNamesFile1.add("LONG_COMP_NAME");
		excelColumnNamesFile1.add("UNDERLYING_SECURITY_DES");
		excelColumnNamesFile1.add("FUND_MANAGEMENT_CO_LONG");
		excelColumnNamesFile1.add("ID_BB_COMPANY");
		excelColumnNamesFile1.add("ISSUER");
		excelColumnNamesFile1.add("ZERO_CPN");
		excelColumnNamesFile1.add("IS_PERPETUAL");
		excelColumnNamesFile1.add("CNTRY_OF_DOMICILE");
		excelColumnNamesFile1.add("MATURITY");
		excelColumnNamesFile1.add("FUND_ASSET_CLASS_FOCUS");
		excelColumnNamesFile1.add("FUND_EURO_DIRECT_UCIT");
		excelColumnNamesFile1.add("FUND_LEVERAGE");
		excelColumnNamesFile1.add("DEFAULTED");
		excelColumnNamesFile1.add("EXCH_MARKET_STATUS");
		excelColumnNamesFile1.add("PRIMARY_EXCHANGE_NAME");
		excelColumnNamesFile1.add("COMPOSITE_EXCH_CODE");
		excelColumnNamesFile1.add("FUND_TYP");
		excelColumnNamesFile1.add("INDUSTRY_SECTOR");
		excelColumnNamesFile1.add("FUND_ASSET_ALLOC_CALC");
		excelColumnNamesFile1.add("SECURITY_NAME");
		excelColumnNamesFile1.add("CPN");
		excelColumnNamesFile1.add("CPN_FREQ");
		excelColumnNamesFile1.add("CPN_TYP");
		excelColumnNamesFile1.add("FIRST_CPN_DT");
		excelColumnNamesFile1.add("ISSUE_DT");
		excelColumnNamesFile1.add("SECURITY_TYP2");
		excelColumnNamesFile1.add("SECURITY_TYP");
		excelColumnNamesFile1.add("MARKET_SECTOR_DES");
		excelColumnNamesFile1.add("EXCH_CODE");
		excelColumnNamesFile1.add("ISSUE_PX");
		excelColumnNamesFile1.add("PX_LAST");
		excelColumnNamesFile1.add("Source");
		excelColumnNamesFile1.add("Date de traitement du fichier");
		excelColumnNamesFile2.add("");
		excelColumnNamesFile2.add("");
		excelColumnNamesFile2.add("ID_BB_COMPANY");
		excelColumnNamesFile2.add("ISSUER");
	}

	/**
	 * Entry Point of the Batch. Parse and process the provided file.
	 * 
	 * @param input the file to process
	 * @return the same file
	 */
	public File parse(File input) {
		logger.info("Parsing : " + input.getAbsolutePath());

		// If the output files does not exist
		if (!checkExistingFiles(input.getName())) {

			initFiles(input.getName());

			// reset columns list
			columns.clear();

			try (Stream<String> stream = Files.lines(Paths.get(input.getAbsolutePath()))) {
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
					outputFile1Writer.close();
					outputFile2Writer.close();
					failureFileWriter.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
			logger.info("End of Parsing");

		} else {
			logger.info("End of Parsing : files already exist");

		}

		return input;

	}

	/**
	 * Check if the filename already exist
	 * 
	 * @param fileName the file name
	 * @return the result of existing
	 */
	private boolean checkExistingFiles(String fileName) {
		String[] fileNameNoExtension = fileName.split("\\.");
		String extractedTimeStamp = fileNameNoExtension[0].substring(3);

		return Files.exists(Paths.get(successFilePath + File.separator + "sig" + extractedTimeStamp + EXCEL_EXTENSION));
	}

	private void initFiles(String fileName) {
		String[] fileNameNoExtension = fileName.split("\\.");

		// extract the timestamp to use. (i.e. source file has this pattern sigxxxxxxxxxxxx.out)
		String extractedTimeStamp = fileNameNoExtension[0].substring(3);

		// Initialize excel file
		File excelOutputFile1 = new File(successFilePath, "sig" + extractedTimeStamp + EXCEL_EXTENSION);
		File excelOutputFile2 = new File(successFilePath, "issuer" + extractedTimeStamp + EXCEL_EXTENSION);
		outputFile1Writer = new ExcelFileWriter(excelOutputFile1.getAbsolutePath(), OUTPUT_SHEETNAME);
		outputFile2Writer = new ExcelFileWriter(excelOutputFile2.getAbsolutePath(), OUTPUT_SHEETNAME);
		// fulfill table header
		outputFile1Writer.appendLine(excelColumnNamesFile1.toArray(new String[excelColumnNamesFile1.size()]));
		outputFile2Writer.appendLine(excelColumnNamesFile2.toArray(new String[excelColumnNamesFile2.size()]));

		// Initialize failure file
		File failureFile = new File(failureFilePath, "sig" + extractedTimeStamp + TXT_EXTENSION);
		failureFileWriter = new CsvFileWriter(failureFile.getAbsolutePath());
	}

	/**
	 * Parse a line of signaletic bloomberg response
	 * 
	 * @param line the line to parse
	 */
	private void parseLine(String line) {
		BatchInjectionControlResponse response = controleLine(line);

		/** handle the column name parsing */
		handleColumnNameParsing(line);

		if (BooleanUtils.isTrue(response.isSuccess() || !response.getErrors().isEmpty())) {

			if (BooleanUtils.isTrue(response.isSuccess())) {
				prepareLinesForExcelFile1(line);
				outputFile2Writer.appendLine(prepareLineForExcelFile2(line));

			} else if (!response.getErrors().isEmpty()) {
				List<Error> errors = response.getErrors();
				failureFileWriter.append(NEW_LINE_SEPARATOR + "input : " + line + NEW_LINE_SEPARATOR);
				logger.error(NEW_LINE_SEPARATOR + "input : " + line + NEW_LINE_SEPARATOR);
				for (Error error : errors) {
					failureFileWriter.append(error.getCode() + COMMA_DELIMITER + error.getMessage() + NEW_LINE_SEPARATOR);
					logger.info("Append " + error.getCode() + COMMA_DELIMITER + error.getMessage() + " to " + failureFileWriter.getPath());
				}

				// update the status to RECEIVED in database
				String[] values = line.split("\\|");
				if (!" ".equals(values[columns.indexOf("ID_ISIN") + 2])) { // if currency is set
					SasIsinDTO sasIsinDTO = new SasIsinDTO();
					sasIsinDTO.setIsin(values[0]);
					sasIsinDTO.setCurrency(values[4]);
					sasIsinDTO.setStatusCode("NOT_EXIST");
					List<SasIsinDTO> sasIsinDTOs = new ArrayList<>();
					SasIsinDTO sasIsinDto = new SasIsinDTO();
					sasIsinDTOs.add(sasIsinDto);
					SaveSignaletiqueResponse saveIsinsResponse = saveIsins(piaRootContextURL + SAVE_WEBIA_SIGNALETIC_FUNDS_URL, sasIsinDTOs);
					if (saveIsinsResponse != null && !saveIsinsResponse.isSuccess()) {
						logger.error("Error during the save of the isin send to bloomberg");
					}
				} else {
					// currency is not set since not found in Bloomberg, we have to retrieve all the corresponding record
					CheckSignaletiqueIsinResponse checkSignaletiqueIsinResponse = checkExistenceInSoliam(Arrays.asList(values[0]));
					List<SasIsinDTO> sasIsinDTOs = new ArrayList<>();
					for (SasIsinDTO sasIsinDTO : checkSignaletiqueIsinResponse.getIsinFound()) {
						sasIsinDTO.setStatusCode("NOT_EXIST");
						sasIsinDTOs.add(sasIsinDTO);
					}

					SaveSignaletiqueResponse saveIsinsResponse = saveIsins(piaRootContextURL + SAVE_WEBIA_SIGNALETIC_FUNDS_URL, sasIsinDTOs);
					if (saveIsinsResponse != null && !saveIsinsResponse.isSuccess()) {
						logger.error("Error during the save of the isin send to bloomberg");
					}
				}
			}
		}
	}

	private void prepareLinesForExcelFile1(String line) {
		String[] values = line.split("\\|");
		List<String> preparedLine = new ArrayList<>();
		for (String columnName : excelColumnNamesFile1) {
			if (columns.contains(columnName)) {
				preparedLine.add(values[columns.indexOf(columnName) + 2]);
			} else {
				preparedLine.add("");
			}
		}
		
		// Default values management
		// ID_BB_COMPANY
		if (StringUtils.isEmpty(preparedLine.get(excelColumnNamesFile1.indexOf("ID_BB_COMPANY")).trim())) {
			preparedLine.set(excelColumnNamesFile1.indexOf("ID_BB_COMPANY"), DEFAULT_ID_BB_COMPANY);
		}
		// ISSUER
		if (StringUtils.isEmpty(preparedLine.get(excelColumnNamesFile1.indexOf("ISSUER")).trim())) {
			preparedLine.set(excelColumnNamesFile1.indexOf("ISSUER"), DEFAULT_ISSUER);
		}
		// CPN_FREQ
		switch (preparedLine.get(excelColumnNamesFile1.indexOf("CPN_FREQ"))) {
			case "1":
				preparedLine.set(excelColumnNamesFile1.indexOf("CPN_FREQ"), "ANNUEL");
				break;
			case "2":
				preparedLine.set(excelColumnNamesFile1.indexOf("CPN_FREQ"), "SEMEST");
				break;
			case "4":
				preparedLine.set(excelColumnNamesFile1.indexOf("CPN_FREQ"), "TRIMEST");
				break;
			case "6":
				preparedLine.set(excelColumnNamesFile1.indexOf("CPN_FREQ"), "BIMENS");
				break;
			case "12":
				preparedLine.set(excelColumnNamesFile1.indexOf("CPN_FREQ"), "MENSUEL");
				break;
			default:
				break;
		}

		String isinCode = values[0];

		// call web service to retrieve the records matching the provided isin
		CheckSignaletiqueIsinResponse checkSignaletiqueIsinResponse = checkExistenceInSoliam(Arrays.asList(isinCode));

		boolean isInSoliam = checkSignaletiqueIsinResponse.getIsinFound().stream().filter(x -> x.getIsin().equals(isinCode) && x.getStatusCode().equals("RECEIVED")).count() > 0;

		preparedLine.set(excelColumnNamesFile1.indexOf(JobConstantsUtils.TITRE_PRESENT_SOLIAM),
				Boolean.toString(isInSoliam));
		preparedLine.set(excelColumnNamesFile1.indexOf("ID_ISIN"), isinCode);
		preparedLine.set(excelColumnNamesFile1.indexOf("Source"), "C-" + format_source_date.format(new Date()));
		preparedLine.set(excelColumnNamesFile1.indexOf("Date de traitement du fichier"), format_treatment_date.format(new Date()));

		// loop on the list in order to cover all the currencies
		List<SasIsinDTO> listSasIsinDto = new ArrayList<>();
		checkSignaletiqueIsinResponse.getIsinFound().stream().filter(x -> x.getStatusCode().equals("SEND")).forEach(listSasIsinDto::add);
		for (SasIsinDTO sasIsinDto : listSasIsinDto) {

			// make a deep copy of the line to insert
			ArrayList<String> lineToInsert = new ArrayList<>();
			for (String cellValue : preparedLine) {
				lineToInsert.add(new String(cellValue));
			}

			// change the currency and populate the bank bic and fund title, since provided by the dto
			lineToInsert.set(excelColumnNamesFile1.indexOf("Devise de la valeur"), sasIsinDto.getCurrency());
			lineToInsert.set(excelColumnNamesFile1.indexOf("Banque"), sasIsinDto.getBankBic());
			lineToInsert.set(excelColumnNamesFile1.indexOf("Libellé long"), sasIsinDto.getFundTitle());

			// actually append the line the excel file
			outputFile1Writer.appendLine(lineToInsert.toArray(new String[lineToInsert.size()]));

			// update the status to RECEIVED in database
			List<SasIsinDTO> sasIsinDTOs = new ArrayList<>();
			sasIsinDto.setStatusCode("RECEIVED");
			sasIsinDTOs.add(sasIsinDto);
			SaveSignaletiqueResponse saveIsinsResponse = saveIsins(piaRootContextURL + SAVE_WEBIA_SIGNALETIC_FUNDS_URL, sasIsinDTOs);
			if (saveIsinsResponse != null && !saveIsinsResponse.isSuccess()) {
				logger.error("Error during the save of the isin send to bloomberg");
			}
		}
	}

	private String[] prepareLineForExcelFile2(String line) {
		String[] values = line.split("\\|");
		List<String> preparedLine = new ArrayList<>();
		for (String columnName : excelColumnNamesFile2) {
			if (columns.contains(columnName)) {
				preparedLine.add(values[columns.indexOf(columnName) + 2]);
			} else {
				preparedLine.add("");
			}
		}
		return preparedLine.toArray(new String[preparedLine.size()]);
	}

	private void handleColumnNameParsing(String line) {
		if (START_OF_FIELDS.equals(line)) {
			isParsingColumnName = true;
		}
		if (END_OF_FIELDS.equals(line)) {
			isParsingColumnName = false;
		}
		if (isParsingColumnName) {
			columns.add(line);
		}
	}

	/**
	 * Check some information into the line
	 * 
	 * @param line the line to check
	 * @return the checking result
	 */
	private BatchInjectionControlResponse controleLine(String line) {
		BatchInjectionControlResponse response = new BatchInjectionControlResponse();

		if (line.contains("|")) {
			response.setSuccess(true);

			/*
			 * String cells[] = line.split("\\|"); if (cells.length > columns.indexOf("ID_ISIN") + 2) { // just to prevent OutOfArrayException String IsinCodeFromBloombergResponse =
			 * cells[columns.indexOf("ID_ISIN") + 2];
			 * 
			 * // check the isin code against all error codes if (errorsCode.keySet().contains(IsinCodeFromBloombergResponse)) { Error error = new Error();
			 * error.setCode(IsinCodeFromBloombergResponse); error.setMessage(errorsCode.get(IsinCodeFromBloombergResponse)); response.getErrors().add(error); response.setSuccess(false); } }
			 */
		} else {
			response.setSuccess(false);
		}
		return response;
	}

	/**
	 * Save the isin into Webia
	 * 
	 * @param url the rest url
	 * @param currentParition the list to check
	 * 
	 * @return
	 */
	private SaveSignaletiqueResponse saveIsins(String url, List<SasIsinDTO> currentParition) {
		SaveSignaletiqueRequest request = new SaveSignaletiqueRequest();
		request.setIsinData(currentParition);
		ParameterizedTypeReference<SaveSignaletiqueResponse> responseType = new ParameterizedTypeReference<SaveSignaletiqueResponse>() {
		};
		ResponseEntity<SaveSignaletiqueResponse> response = RestCallUtils.postRest(piaRootContextURL + SAVE_WEBIA_SIGNALETIC_FUNDS_URL, request, SaveSignaletiqueRequest.class, responseType,
				keycloackUtils, logger);

		return response.getBody();
	}

	/**
	 * check the existence of the provided isin codes in webia
	 * 
	 * @param url the rest url
	 * @param currentParition the list to check
	 * 
	 * @return
	 */
	private CheckSignaletiqueIsinResponse checkExistenceInSoliam(List<String> isinCodes) {
		CheckSignaletiqueIsinRequest request = new CheckSignaletiqueIsinRequest();
		request.getIsin().addAll(isinCodes);
		ParameterizedTypeReference<CheckSignaletiqueIsinResponse> responseType = new ParameterizedTypeReference<CheckSignaletiqueIsinResponse>() {
		};
		ResponseEntity<CheckSignaletiqueIsinResponse> response = RestCallUtils.postRest(piaRootContextURL + CHECK_WEBIA_SIGNALETIC_FUNDS_URL, request, CheckSignaletiqueIsinRequest.class, responseType,
				keycloackUtils, logger);

		return response.getBody();
	}

	@Override
	public void onMessage(Message message) {
		try {
			File vniFile = MessageUtils.saveFileArchive(message, inputFolder);
			parse(vniFile);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
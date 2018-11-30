/**
 * 
 */
package lu.wealins.batch.ewealins;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import lu.wealins.common.security.ACL;
import lu.wealins.common.security.ACLContext;
import lu.wealins.common.security.Functionality;
import lu.wealins.enums.Language;
import lu.wealins.rest.model.acl.ACLsAndFunctionalities;
import lu.wealins.rest.model.acl.GetUserRequest;
import lu.wealins.rest.model.acl.SearchUserRequest;
import lu.wealins.rest.model.acl.SearchUserWithACLsAndFunctionalitiesResponse;
import lu.wealins.rest.model.ewealins.Translation;
import lu.wealins.rest.model.ewealins.rs.request.BasicInternationalizationRequest;
import lu.wealins.rest.model.ewealins.rs.response.LoadInternationalizationResponse;
import lu.wealins.rest.model.ods.EventPortfolioSearchRequest;
import lu.wealins.rest.model.ods.GetWeightedAverageRequest;
import lu.wealins.rest.model.ods.HoldingsLoadRequest;
import lu.wealins.rest.model.ods.HoldingsLoadResponse;
import lu.wealins.rest.model.ods.PolicyCountRequest;
import lu.wealins.rest.model.ods.PolicyCountResponse;
import lu.wealins.rest.model.ods.PolicySearchResponse;
import lu.wealins.rest.model.ods.PolicyWithACLContextSearchRequest;
import lu.wealins.rest.model.ods.common.EventType;
import lu.wealins.rest.model.ods.common.Fund;
import lu.wealins.rest.model.ods.common.PortfolioItem;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;

/**
 * Batch
 * 
 * @author lax
 *
 */
@EnableScheduling
public class GenerateExcelPortfolioTaskLet implements Tasklet {

	private static final Log LOGGER = LogFactory.getLog(GenerateExcelPortfolioTaskLet.class);

	private static final String GENERATE_JOB = "generateExcelPortfolioJob";
	private static final String EXTRACT_USER_ACLS_FUNCTIONALITIES = "searchWithAclsAndFunctionalities";
	private static final String EXTRACT_POLICIES = "searchPolicyForPortfolio";
	private static final String HOLDINGS = "loadHoldings";
	private static final String WEIGHTED_AVERAGE = "calculateWeightedAverageForPortfolio";
	private static final String COUNT_POLICIES = "countPoliciesByAgent";
	private static final String EXCEL_EXTENSION = ".xlsx";
	private static final String IS_FROM_LDAP = "isFromLdap";
	private static final String HYPHEN = "-";
	private static final String HYPHEN_SPACE = " - ";
	private static final String I18N_CONSTANT_URL = "translateByType";
	private static final String EXCEL_PORTFOLIO_COLUMN_HEADER_URL = "translateByType";
	private static final String EXCEL_PORTFOLIO_COLUMN_HEADER = "EXCEL_PORTFOLIO_COLUMN_HEADER";

	/**
	 * Date format used for date cell value.
	 */
	private static final String DATE_FORMAT = "dd/MM/yyyy";

	/**
	 * number format used for numeric cell value.
	 */
	private static final String NUMBER_FORMAT = "# ### ### ##0.00";

	/**
	 * Sheet name used during the workbook creation.
	 */
	private static final String SHEET_NAME = "Portfolio";
	private static final String BASE_FILE_NAME = "_portfolio";

	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();

	/**
	 * Line position management in the workbook.
	 */
	private int rowLineCursor;

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Value("${portfolioExcelFilePath}")
	private String portfolioExcelFilePath;

	@Autowired
	private KeycloakUtils keycloackUtils;

	@Autowired
	private JobService jobService;

	/**
	 * Enumerates the excel columns.
	 * 
	 * @author oro
	 *
	 */
	// DO NOT CHANGE THE TYPE OF ENUMERATION. IT IS USED IN TABLE
	// INTERNATIONALIZATION.
	private enum PortFolioExcelColumnHeader {

		/**
		 * The contract number
		 */
		CONTRACT_NUMBER(0),

		/**
		 * The trade name of the product
		 */
		PRODUCT_TRADE_NAME(1),

		/**
		 * The contract effective date
		 */
		CONTRACT_EFFECTIVE_DATE(2),

		/**
		 * The {@code ISIN} code
		 */
		ISIN_CODE(3),

		/**
		 * The fund label
		 */
		FUND_LABEL(4),

		/**
		 * The weighted average price
		 */
		WEIGHTED_AVERAGE_PRICE(5),

		/**
		 * Fund currency
		 */
		FUND_CURRENCY(6),

		/**
		 * The {@code VNI} date.
		 */
		NAV_DATE(7),

		/**
		 * The {@code VNI}.
		 */
		NAV(8),

		/**
		 * The number of unit
		 */
		UNITY_NUMBER(9),

		/**
		 * The currency amount
		 */
		CURRENCY_AMOUNT(10);

		private final int index;

		private PortFolioExcelColumnHeader(int index) {
			this.index = index;
		}

	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		List<Translation> i18nConstants = getConstantsI18n();

		List<Translation> i18nExcelHeaders = getPortfolioHeadersI18n();
		
		// Search e-wealins users who have at least one POLICY ACL
		SearchUserRequest userRequest = new SearchUserRequest();
		SearchUserWithACLsAndFunctionalitiesResponse userResponse = post(
				piaRootContextURL + EXTRACT_USER_ACLS_FUNCTIONALITIES, userRequest,
				SearchUserWithACLsAndFunctionalitiesResponse.class);

		// Filter of userResponse to keep only users not from LDAP and only one
		// occurrence of ACL
		// Only Wealins users are from Ldap and there is no need to generate a file for
		// Wealins users
		Map<String, ACLsAndFunctionalities> usersNotFromLdapDistinctAcls = new HashMap<String, ACLsAndFunctionalities>();
		// Also keep track of duplicate ACL to duplicate the Excel file
		Map<String, ACLsAndFunctionalities> usersNotFromLdapDuplicateAcls = new HashMap<String, ACLsAndFunctionalities>();
		userResponse.getUsersWithACLsAndFunctionalities().forEach((login, aCLsAndFunctionalities) -> {
			// Look if the user is from Ldap. if true then does nothing because only Wealins
			// users are from Ldap
			// and there is no need to generate a file for Wealins users
			GetUserRequest keycloakRequest = new GetUserRequest();
			keycloakRequest.setUserName(login);
			Boolean isFromLdap = post(piaRootContextURL + IS_FROM_LDAP, keycloakRequest, Boolean.class);
			// Keep only one occurrence of ACL cause it's produce the same file
			if (Boolean.FALSE.equals(isFromLdap)) {
				if (!usersNotFromLdapDistinctAcls.containsValue(aCLsAndFunctionalities)) {
					usersNotFromLdapDistinctAcls.put(login, aCLsAndFunctionalities);
					LOGGER.info("[DISTINCT] User " + login + " added to map");
				} else {
					usersNotFromLdapDuplicateAcls.put(login, aCLsAndFunctionalities);
					LOGGER.info("[DUPLICATE] User " + login + " added to map");
				}
			} else if (isFromLdap == null) {
				LOGGER.warn("The User " + login + " is not found in keycloak. It will not be treated.");
			}
		});		

		// Search count policies for each user
		Map<String, Integer> countPoliciesByUser = new HashMap<String, Integer>();
		usersNotFromLdapDistinctAcls.forEach( (user, aCLsAndFunctionalities) -> {
			PolicyCountRequest countRequest = new PolicyCountRequest();
			countRequest.setAgentId(aCLsAndFunctionalities.getAgentId());
			PolicyCountResponse countResponse = post(piaRootContextURL + COUNT_POLICIES, countRequest,
					PolicyCountResponse.class);
			countPoliciesByUser.put(user, countResponse.getPolicyNumber());
		});

		// Sort users by policies count
		Map<String, Integer> sortedCountPoliciesByUser = countPoliciesByUser.entrySet().stream()
				.sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		sortedCountPoliciesByUser.entrySet().parallelStream().forEach( entry -> {
			String login = entry.getKey();
			LOGGER.info("Start treatement for user " + login);
			ACLsAndFunctionalities aCLsAndFunctionalities = usersNotFromLdapDistinctAcls.get(login);
			List<ACL> acls = aCLsAndFunctionalities.getACLs();
			List<Functionality> functionalities = aCLsAndFunctionalities.getFunctionalities();

			// Search the policies for the user by using his ACLs and functionalities
			PolicyWithACLContextSearchRequest policyRequest = new PolicyWithACLContextSearchRequest();
			policyRequest.setWithClosed(false);
			policyRequest.setAcls(acls);
			policyRequest.setFunctionalities(functionalities);
			PolicySearchResponse policyResponse = post(piaRootContextURL + EXTRACT_POLICIES, policyRequest,
					PolicySearchResponse.class);
			LOGGER.info(policyResponse.getPolicies().size() + " policies retrieved for user " + login
					+ "with previous count " + entry.getValue());

			// Getting the portfolio items for the user's policies
			Map<Language, List<PortfolioItem>> portfolioItemsByLanguage = new HashMap<>();
			
			for (Language language : Language.values()) {
				portfolioItemsByLanguage.putIfAbsent(language, new ArrayList<>());
			}

			ACLContext acl = new ACLContext();
			acl.setUser(login);
			acl.setAcls(acls);
			acl.setFunctionalities(functionalities);

			policyResponse.getPolicies().stream().forEach(policy -> {
				LOGGER.info("Start treatement for user " + login + " for policy " + policy.getCode());
				// Load the policy's holdings
				HoldingsLoadRequest holdingsLoadRequest = new HoldingsLoadRequest();
				holdingsLoadRequest.setId(policy.getCode());
				holdingsLoadRequest.setValidityDate(new Date());

				HoldingsLoadResponse holdingsLoadResponse = post(piaRootContextURL + HOLDINGS, holdingsLoadRequest,
						HoldingsLoadResponse.class);
				holdingsLoadResponse.getHoldings().stream().forEach(holding -> {
					LOGGER.info("Start treatement for user " + login + " for policy " + policy.getCode() + " for fund "
							+ holding.getFund().getId());
					Fund fund = holding.getFund();
				
					
					for (Language language : Language.values()) {
						PortfolioItem portfolioItem = new PortfolioItem();
						portfolioItem.setPolicyId(policy.getCode());
						portfolioItem.setProductName(policy.getProductName());
						portfolioItem.setIsin(fund.getIsin());
						portfolioItem.setFundLabel(composeFundLabel(fund, language, i18nConstants));
						try {
							portfolioItem.setWeightedAverage(calculateWeightedAverage(acl, policy.getCode(),
									policy.getEffectDate(), fund.getId(), policy.getSourceName(), fund.getSubType()));
						} catch (ParseException e) {
							portfolioItem.setWeightedAverage(null);
						}
						portfolioItem.setPolicyEffectiveDate(policy.getEffectDate());
						portfolioItem.setFundCurrency(fund.getCurrency());
						portfolioItem.setNavDate(holding.getNavDate());
						portfolioItem.setNav(holding.getNav());
						portfolioItem.setNumberUnits(holding.getNumberOfUnits());
						portfolioItem.setTotalAmount(holding.getTotalAmount());
						portfolioItem.setAmountInPolicyCurrency(holding.getTotalAmountInPolicyCurrency());
						
						portfolioItemsByLanguage.get(language).add(portfolioItem);
					}
				});
			});

			generatePortfolioExcel(login, aCLsAndFunctionalities, i18nExcelHeaders, portfolioItemsByLanguage,
					usersNotFromLdapDuplicateAcls);

		});

		return RepeatStatus.FINISHED;
	}

	private List<Translation> getPortfolioHeadersI18n() {
		BasicInternationalizationRequest request = new BasicInternationalizationRequest();
		request.setType(EXCEL_PORTFOLIO_COLUMN_HEADER);
		LoadInternationalizationResponse i18nExcelHeaders = post(piaRootContextURL + EXCEL_PORTFOLIO_COLUMN_HEADER_URL,
				request, LoadInternationalizationResponse.class);
		return i18nExcelHeaders.getTranslations();
	}

	private List<Translation> getConstantsI18n() {
		// Retrieve internationalization data with key CONSTANT.
		BasicInternationalizationRequest internationalizationRequest = new BasicInternationalizationRequest();
		internationalizationRequest.setType("CONSTANT");
		LoadInternationalizationResponse i18nResponse = post(piaRootContextURL + I18N_CONSTANT_URL,
				internationalizationRequest, LoadInternationalizationResponse.class);
		List<Translation> i18n = i18nResponse.getTranslations();
		return i18n;
	}

	/**
	 * Add a header in the given sheet at the specified line.
	 * 
	 * @param sheet       the sheet to add the header
	 * @param line        the row's position
	 * @param lang        the user lang
	 * @param i18nHeaders
	 * @return
	 */
	private void createHeader(XSSFSheet sheet, String lang, List<Translation> i18nHeaders) {
		sheet.shiftRows(0, sheet.getLastRowNum() + 1, 1);
		XSSFRow row = sheet.createRow(0);

		for (PortFolioExcelColumnHeader column : PortFolioExcelColumnHeader.values()) {

			Optional<Translation> translation = i18nHeaders.stream()
					.filter(item -> lang.equalsIgnoreCase(item.getLanguage())
							&& column.name().equalsIgnoreCase(item.getSource()))
					.findFirst();

			if (translation.isPresent()) {
				createStringCell(row, column.index, translation.get().getDestination());
			}
		}
	}

	/**
	 * @param fund
	 * @param i18n
	 * @param list
	 * @param list
	 * @param i18nResponse
	 * @return
	 */
	private String composeFundLabel(Fund fund, Language language, List<Translation> i18nList) {
		String label = "";

		if (StringUtils.isNotBlank(fund.getSubType())) {
			Optional<Translation> translation = i18nList.stream()
					.filter(i18n -> ("FUND_" + fund.getSubType()).equals(i18n.getSource())
							&& language.getLang().equals(i18n.getLanguage()))
					.findFirst();

			if (translation.isPresent()) {
				label += translation.get().getDestination().trim() + HYPHEN_SPACE;
			}
		}

		if (StringUtils.isNotBlank(fund.getName())) {
			label += fund.getName().trim() + HYPHEN_SPACE;
		}

		if (StringUtils.isNotBlank(fund.getAssetManager())) {
			label += fund.getAssetManager().trim() + HYPHEN_SPACE;
		}

		if (StringUtils.isNotBlank(fund.getCurrency())) {
			label += fund.getCurrency().trim();
		}

		return label;
	}

	@Scheduled(cron = "${generatePortfolioExcelCronExpression:}")
	private void scheduleExecute() throws Exception {
		LOGGER.info("Trying to execute the generation of the portfolio Excel file");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR
				.fromString(InetAddress.getLocalHost().getHostAddress() + " : executionTime=" + timeStamp);

		jobService.launch(GENERATE_JOB, new JobParametersBuilder(jobParameters)
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

	private BigDecimal calculateWeightedAverage(ACLContext acl, String policyId, Date startDate, String fundId,
			String source, String fundSubType) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date Date_2005 = simpleDateFormat.parse("01/01/2005");

		if ((startDate.before(Date_2005) && "LISSIA".equals(source))) {
			return null;
		}

		BigDecimal result = BigDecimal.ZERO;
		GetWeightedAverageRequest calculateRequest = new GetWeightedAverageRequest();
		EventPortfolioSearchRequest request = new EventPortfolioSearchRequest();
		boolean isForTG;

		if ("TG".equals(fundSubType)) {
			request.setTypes(Arrays.asList(EventType.FOR_PORTFOLIO_TG_EVENT_HISTORY));
			isForTG = true;
		} else {
			request.setTypes(Arrays.asList(EventType.FOR_PORTFOLIO_EVENT_HISTORY));
			isForTG = false;
		}

		request.setPolicyId(policyId);
		request.setBeginDate(startDate);
		request.setEndDate(new Date());
		request.setFundId(fundId);
		request.setAcls(acl.getAcls());
		request.setFunctionalities(acl.getFunctionalities());
		request.setUser(acl.getUser());

		calculateRequest.setRequest(request);
		calculateRequest.setForTG(isForTG);

		result = post(piaRootContextURL + WEIGHTED_AVERAGE, calculateRequest, BigDecimal.class);

		return result.setScale(2, RoundingMode.HALF_UP);
	}

	private void generatePortfolioExcel(String user, ACLsAndFunctionalities userFunctionality,
			List<Translation> i18nHeaders, Map<Language, List<PortfolioItem>> portfolioItemsByLanguage,
			Map<String, ACLsAndFunctionalities> usersNotFromLdapDuplicateAcls) {
		LOGGER.info("Generate Portfolio Excel for user " + user);
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Write the header line in the workbook
		// createHeader(sheet, rowLineCursor++, lang);

		portfolioItemsByLanguage.entrySet().forEach(entry -> {
			XSSFSheet sheet = workbook.createSheet(SHEET_NAME + "_" + entry.getKey().getLang());

			// Create the cell styles instance and use it for all portfolio items
			// Date style creation
			CellStyle dateStyle = sheet.getWorkbook().createCellStyle();
			CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
			short dateFormat = createHelper.createDataFormat().getFormat(DATE_FORMAT);
			dateStyle.setDataFormat(dateFormat);

			// Numeric style creation
			CellStyle numericStyle = sheet.getWorkbook().createCellStyle();
			numericStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(NUMBER_FORMAT));

			// First create the header.
			createHeader(sheet, entry.getKey().getLang(), i18nHeaders);

			rowLineCursor = 1;
			entry.getValue().forEach(portfolioItem -> {
				if (Objects.nonNull(portfolioItem)) {
					XSSFRow row = sheet.createRow(rowLineCursor++);
		
				// Add policy id in row
				createStringCell(row, PortFolioExcelColumnHeader.CONTRACT_NUMBER.index, portfolioItem.getPolicyId());
		
				// Add product name in row
				createStringCell(row, PortFolioExcelColumnHeader.PRODUCT_TRADE_NAME.index,
						portfolioItem.getProductName());
		
				// Add policy effective date in row
				createDateCell(row, PortFolioExcelColumnHeader.CONTRACT_EFFECTIVE_DATE.index,
						portfolioItem.getPolicyEffectiveDate(), dateStyle);
		
				// Add isin in row
				createStringCell(row, PortFolioExcelColumnHeader.ISIN_CODE.index, portfolioItem.getIsin());
		
				// Add fund label in row
				createStringCell(row, PortFolioExcelColumnHeader.FUND_LABEL.index, portfolioItem.getFundLabel());
		
				// Add weighted average in row
				createNumericCell(row, PortFolioExcelColumnHeader.WEIGHTED_AVERAGE_PRICE.index,
						portfolioItem.getWeightedAverage(), numericStyle);
		
				// Add fund currency in row
				createStringCell(row, PortFolioExcelColumnHeader.FUND_CURRENCY.index, portfolioItem.getFundCurrency());
		
				// Add nav date in row
				createDateCell(row, PortFolioExcelColumnHeader.NAV_DATE.index, portfolioItem.getNavDate(), dateStyle);
		
				// Add nav in row
				createNumericCell(row, PortFolioExcelColumnHeader.NAV.index, portfolioItem.getNav(), numericStyle);
		
				// Add number units in row
				createNumericCell(row, PortFolioExcelColumnHeader.UNITY_NUMBER.index, portfolioItem.getNumberUnits(),
						numericStyle);
		
				// Add total amount

				/*
				 * EW-321: Comment: en fait c'est le montant traduit en euros qui avait été mis.
				 * Il faut le montant dans la devise du fonds (ex USD si le fonds est en USD) et
				 * non le montant converti dans la devise du contrat. (il faut utiliser AMOUNT
				 * et non AMOUNT_EUR ). Fix: We keep the CURRENCY_AMOUNT enum because the header
				 * "Amount in the investment fund currency" should be kept. Instead of using the
				 * "AmountInPolicyCurrency", use the "TotalAmount"
				 */
				createNumericCell(row, PortFolioExcelColumnHeader.CURRENCY_AMOUNT.index,
						portfolioItem.getTotalAmount(), numericStyle);
				}
			});
		});

		try {
			File directory = FileUtils.getFile(portfolioExcelFilePath);

			if (!directory.exists()) {
				directory.mkdirs();
			}

			File excelFile = FileUtils.getFile(directory, user + BASE_FILE_NAME + EXCEL_EXTENSION);
			FileOutputStream fileOut = new FileOutputStream(excelFile.getAbsolutePath(), false);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			workbook.close();

			// Copy file for duplicated ACls users
			usersNotFromLdapDuplicateAcls.entrySet().stream()
					.filter(entry -> entry.getValue().equals(userFunctionality))
					.forEach(entry -> duplicatePortfolioExcel(user, entry.getKey()));

		} catch (IOException e) {
			LOGGER.error(
					"Cannot generate file " + portfolioExcelFilePath + "/" + user + BASE_FILE_NAME + EXCEL_EXTENSION,
					e);
		}
	}

	/**
	 * Duplicate an Excel File from the source to the current user
	 * 
	 * @param sourceUser the user who already have a file
	 * @param currentUser the user for who the file is duplicated
	 */
	private void duplicatePortfolioExcel(String sourceUser, String currentUser) {
		LOGGER.info("Duplicate Portfolio Excel from user " + sourceUser + " to user " + currentUser);
		File f = new File(portfolioExcelFilePath, sourceUser + BASE_FILE_NAME + EXCEL_EXTENSION);
		if (f.exists() && !f.isDirectory()) {
			File excelOutputFile = new File(portfolioExcelFilePath, currentUser + BASE_FILE_NAME + EXCEL_EXTENSION);
			try {
				FileUtils.copyFile(f, excelOutputFile);
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.error("File from user : [" + sourceUser + "] cannot be copy to user : [" + currentUser + "]");
			}
		} else {
			LOGGER.error("File from user : [" + sourceUser + "] cannot be found");
		}
	}

	/**
	 * Add a cell with a string value in the given row at the specified column.
	 * 
	 * @param row the row to add the cell
	 * @param column the cell's position
	 * @param value the cell's value
	 */
	private void createStringCell(XSSFRow row, int column, String value) {
		XSSFCell cell = row.createCell(column);
		cell.setCellType(CellType.STRING);

		if (StringUtils.isNotBlank(value)) {
			cell.setCellValue(StringUtils.toEncodedString(value.getBytes(), StandardCharsets.UTF_8));
		} else {
			// Fix issue: As required by the P.O., fill the cell with '-' if the value is
			// empty or null.
			cell.setCellValue(HYPHEN);
		}
	}

	/**
	 * Add a cell with a date value in the given row at the specified column.
	 * 
	 * @param row the row to add the cell
	 * @param column the cell's position
	 * @param value the cell's value
	 */
	private void createDateCell(XSSFRow row, int column, Date value, CellStyle style) {
		XSSFCell cell = row.createCell(column);
		cell.setCellStyle(style);

		if (Objects.nonNull(value)) {
			cell.setCellValue(value);
		}
	}

	/**
	 * Add a cell with a number value in the given row at the specified column.
	 * 
	 * @param row the row to add the cell
	 * @param column the cell's position
	 * @param value the cell's value
	 */
	private void createNumericCell(XSSFRow row, int column, BigDecimal value, CellStyle style) {
		if (Objects.nonNull(value)) {
			XSSFCell cell = row.createCell(column);
			cell.setCellType(CellType.NUMERIC);
			cell.setCellStyle(style);
			cell.setCellValue(value.doubleValue());
		} else {
			// Fix issue: As required by the P.O., fill the cell with '-' if the value is
			// empty or null.
			// Because, the value aims to be a numeric type and the '-' character is not a
			// numeric value, we have to convert the current cell to be a string type.
			createStringCell(row, column, StringUtils.EMPTY);
		}
	}

}

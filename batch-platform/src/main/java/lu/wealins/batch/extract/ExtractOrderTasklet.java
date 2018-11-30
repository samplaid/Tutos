package lu.wealins.batch.extract;

import java.io.File;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import lu.wealins.common.dto.OrderDTO;
import lu.wealins.common.dto.webia.services.ExtractOrderResponse;
import lu.wealins.utils.CsvFileWriter;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;

/**
 * Tasklet to extract order from webia application and build some xml file
 * 
 * @author xqv60
 * 
 */
@EnableScheduling
public class ExtractOrderTasklet implements Tasklet {

	/**
	 * Constant JOB Parameters extractor
	 */
	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();

	/**
	 * Constant job name
	 */
	private static final String EXTRACT_JOB = "extractOrderJob";

	/**
	 * Constant extract order url
	 */
	private static final String EXTRACT_ORDER_URL = "webia/extract/order";

	/**
	 * The default decimal separator
	 */
	private static char DEFAULT_DECIMAL_SEPARATOR = '.';

	/**
	 * The delimiter constant
	 */
	private static final String DELIMITER = ";";

	/**
	 * Constant CSV EXTENSION
	 */
	private final static String CSV_EXTENSION = ".csv";

	/**
	 * Constant LISSIA ESTIMATED FILENAME
	 */
	private final static String LISSIA_ESTIMATED_FILENAME = "ORDTRF_FE_LISSIA_EST_";
	/**
	 * Constant LISSIA VALORIZED FILENAME
	 */
	private final static String LISSIA_VALORIZED_FILENAME = "ORDTRF_FE_LISSIA_VAL_";

	/**
	 * Constant DALI ESTIMATED FILENAME
	 */
	private final static String DALI_ESTIMATED_FILENAME = "ORDTRF_FE_DALI_EST_";
	/**
	 * Constant DALI VALORIZED FILENAME
	 */
	private final static String DALI_VALORIZED_FILENAME = "ORDTRF_FE_DALI_VAL_";

	private Log logger = LogFactory.getLog(ExtractOrderTasklet.class);
	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	/**
	 * Folder with estimated orders
	 */
	@Value("${estimatedOrderFolder}")
	private String estimatedOrderFolder;

	/**
	 * Folder with valorized orders (Flow) (Copy of valorizedOrderFolder)
	 */
	@Value("${valorizedOrderFolderFlow}")
	private String valorizedOrderFolderFlow;

	/**
	 * Folder with valorized orders
	 */
	@Value("${valorizedOrderFolder}")
	private String valorizedOrderFolder;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Autowired
	private JobService jobService;


	private CsvFileWriter lissiaEstimatedOrder;
	private CsvFileWriter lissiaValorizedOrder;
	private CsvFileWriter daliEstimatedOrder;
	private CsvFileWriter daliValorizedOrder;
	private CsvFileWriter lissiaValorizedOrderFlow;
	private CsvFileWriter daliValorizedOrderFlow;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		try {

			initFiles();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			ExtractOrderResponse extractOrderResponse = extractOrder(piaRootContextURL + EXTRACT_ORDER_URL);

			if (extractOrderResponse == null) {
				logger.error("Erreur de génération des extraction d ordres : response is null ");
			} else {
				writeOrders(extractOrderResponse);
			}

		} catch (Exception e) {
			logger.error("Erreur de génération des extraction d ordres \n " + e);
			throw e;
		} finally {
			lissiaEstimatedOrder.close();
			lissiaValorizedOrder.close();
			daliEstimatedOrder.close();
			daliValorizedOrder.close();
			lissiaValorizedOrderFlow.close();
			daliValorizedOrderFlow.close();
		}
		return RepeatStatus.FINISHED;
	}

	//@Scheduled(cron = "${extractOrderCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the extraction of order");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(InetAddress.getLocalHost().getHostAddress() + " : executionTime=" + timeStamp);

		jobService.launch(EXTRACT_JOB,
				new JobParametersBuilder(jobParameters)
						.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());

	}

	/**
	 * Write files for the order extraction 4 Files are created : one for LISSIA with estimated order, one for LISSIA with valorized order, one for DALI with estimated order, one for DALI with
	 * valorized order
	 * 
	 * @param extractOrderResponse the order response
	 */
	private void writeOrders(ExtractOrderResponse extractOrderResponse) {
		writeOrder(extractOrderResponse.getDaliEstimatedOrder(), daliEstimatedOrder);
		writeOrder(extractOrderResponse.getDaliValorizedOrder(), daliValorizedOrder);
		writeOrder(extractOrderResponse.getLissiaEstimatedOrder(), lissiaEstimatedOrder);
		writeOrder(extractOrderResponse.getLissiaValorizedOrder(), lissiaValorizedOrder);

		// Copy valorized order into an other folder (valorizedOrderFolderFlow)
		writeOrder(extractOrderResponse.getLissiaValorizedOrder(), lissiaValorizedOrderFlow);
		writeOrder(extractOrderResponse.getDaliValorizedOrder(), daliValorizedOrderFlow);

		logger.info("Order extraction finished");

	}

	/**
	 * Write order in a file
	 * 
	 * @param daliEstimatedOrder2
	 */
	private void writeOrder(List<OrderDTO> orders, CsvFileWriter csvWriter) {
		DecimalFormat decimalSeparator = new DecimalFormat();
		decimalSeparator.setGroupingUsed(Boolean.FALSE);
		DecimalFormatSymbols decimalFormatSymbols = decimalSeparator.getDecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator(DEFAULT_DECIMAL_SEPARATOR);
		decimalSeparator.setDecimalFormatSymbols(decimalFormatSymbols);

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");

		if (CollectionUtils.isEmpty(orders)) {
			csvWriter.append("No elements found");
		} else {
			for (OrderDTO order : orders) {
				csvWriter.appendWithDelimiter(DELIMITER, order.getOrigin() == null ? StringUtils.EMPTY : order.getOrigin(),
						order.getLineId() == null ? StringUtils.EMPTY : order.getLineId(),
						order.getGroupeId() == null ? StringUtils.EMPTY : order.getGroupeId(),
						order.getCancelId() == null ? StringUtils.EMPTY : order.getCancelId(),
						order.getEventType() == null ? StringUtils.EMPTY : order.getEventType(),
						order.getTransactionType() == null ? StringUtils.EMPTY : order.getTransactionType(),
						order.getNatureId() == null ? StringUtils.EMPTY : order.getNatureId(),
						order.getSecurityId() == null ? StringUtils.EMPTY : order.getSecurityId(),
						order.getContractId() == null ? StringUtils.EMPTY : order.getContractId(),
						order.getPolicyId() == null ? StringUtils.EMPTY : order.getPolicyId(),
						order.getPortfolio() == null ? StringUtils.EMPTY : order.getPortfolio(),
						order.getSct() == null ? StringUtils.EMPTY : order.getSct(),
						order.getEntryDate() == null ? StringUtils.EMPTY : sdf.format(order.getEntryDate()),
						order.getValuationDate() == null ? StringUtils.EMPTY : sdf.format(order.getValuationDate()),
						order.getQuantity() == null ? StringUtils.EMPTY : decimalSeparator.format(order.getQuantity()),
						order.getAmount() == null ? StringUtils.EMPTY : decimalSeparator.format(order.getAmount()),
						order.getAmountCurrency() == null ? StringUtils.EMPTY : order.getAmountCurrency(),
						order.getNav() == null ? StringUtils.EMPTY : decimalSeparator.format(order.getNav()),
						order.getNavDate() == null ? StringUtils.EMPTY : sdf.format(order.getNavDate()),
						order.getIsInvestment() == null ? StringUtils.EMPTY : order.getIsInvestment(),
						order.getIsConversion() == null ? StringUtils.EMPTY : order.getIsConversion(),
						order.getIsTechnicalCancel() == null ? StringUtils.EMPTY : order.getIsTechnicalCancel(),
						order.getIsCancel() == null ? StringUtils.EMPTY : order.getIsCancel(),
						order.getIsEstimated() == null ? StringUtils.EMPTY : order.getIsEstimated(),
						order.getIsQantity() == null ? StringUtils.EMPTY : order.getIsQantity(),
						order.getSendDate() == null ? "\r\n" : sdf.format(order.getSendDate()) + "\r\n");
			}
		}

	}

	/**
	 * init files
	 * 
	 */
	private void initFiles() {
		// Initialization of lissia files
		File lissiaEstimatedOrderFile = new File(estimatedOrderFolder, LISSIA_ESTIMATED_FILENAME + getCurrentDate() + CSV_EXTENSION);
		File lissiaValorizedOrderFile = new File(valorizedOrderFolder, LISSIA_VALORIZED_FILENAME + getCurrentDate() + CSV_EXTENSION);
		lissiaEstimatedOrder = new CsvFileWriter(lissiaEstimatedOrderFile.getAbsolutePath());
		lissiaValorizedOrder = new CsvFileWriter(lissiaValorizedOrderFile.getAbsolutePath());

		// Initialization of dali files
		File daliEstimatedOrderFile = new File(estimatedOrderFolder, DALI_ESTIMATED_FILENAME + getCurrentDate() + CSV_EXTENSION);
		File daliValorizedOrderFile = new File(valorizedOrderFolder, DALI_VALORIZED_FILENAME + getCurrentDate() + CSV_EXTENSION);
		daliEstimatedOrder = new CsvFileWriter(daliEstimatedOrderFile.getAbsolutePath());
		daliValorizedOrder = new CsvFileWriter(daliValorizedOrderFile.getAbsolutePath());

		// Initialization valoried files (copy of dali and lissia)
		File lissiaValorizedOrderFlowFile = new File(valorizedOrderFolderFlow, LISSIA_VALORIZED_FILENAME + getCurrentDate() + CSV_EXTENSION);
		File daliValorizedOrderFlowFile = new File(valorizedOrderFolderFlow, DALI_VALORIZED_FILENAME + getCurrentDate() + CSV_EXTENSION);
		lissiaValorizedOrderFlow = new CsvFileWriter(lissiaValorizedOrderFlowFile.getAbsolutePath());
		daliValorizedOrderFlow = new CsvFileWriter(daliValorizedOrderFlowFile.getAbsolutePath());

	}

	/**
	 * Get the current date
	 * 
	 * @return the current date
	 */
	private String getCurrentDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * extract the order from database
	 * 
	 * @param url the url of the web service
	 * 
	 * @return
	 */
	private ExtractOrderResponse extractOrder(String url) {
		ExtractOrderResponse extractOrderResponse = new ExtractOrderResponse();
		ResponseEntity<ExtractOrderResponse> extractOrderRest = extractOrderRest(url);
		if (extractOrderRest != null && extractOrderRest.getBody() != null) {
			extractOrderResponse = extractOrderRest.getBody();
		}
		return extractOrderResponse;
	}

	/**
	 * Rest call http
	 * 
	 * @param url the url of the web service
	 * @return the rest response
	 */
	private ResponseEntity<ExtractOrderResponse> extractOrderRest(String url) {
		logger.info("Token ...");
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		logger.info("Token response " + tokenResponse);
		String token = tokenResponse.getToken();
		logger.info("Token OK" + token);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		RestTemplate template = new RestTemplate();
		logger.info("Response ...");
		ResponseEntity<ExtractOrderResponse> response = template.exchange(url, HttpMethod.GET, entity, ExtractOrderResponse.class);
		logger.info("Response OK");
		return response;
	}
}

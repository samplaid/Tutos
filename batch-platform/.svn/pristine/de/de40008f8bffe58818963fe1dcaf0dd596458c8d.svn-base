package lu.wealins.batch.extract;

import java.io.File;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import lu.wealins.common.dto.OrderDTO;
import lu.wealins.common.dto.liability.services.UpdateOrderRequest;
import lu.wealins.common.dto.liability.services.UpdateOrderResponse;
import lu.wealins.common.dto.liability.services.ValorizedTransactionSearchRequest;
import lu.wealins.common.dto.liability.services.ValorizedTransactionSearchResponse;
import lu.wealins.common.dto.webia.services.ExtractOrderFIDResponse;
import lu.wealins.common.dto.webia.services.OrderFIDDTO;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderFIDRequest;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderFIDResponse;
import lu.wealins.utils.CsvFileWriter;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;

/**
 * Tasklet to extract order from lissia and generate file to inject in SOLIAM
 * 
 * @author xqt5q
 * 
 */
@EnableScheduling
public class ExtractLissiaOrdersForFIDTasklet implements Tasklet {

	/**
	 * Constant JOB Parameters extractor
	 */
	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();

	/**
	 * Constant job name
	 */
	private static final String EXTRACT_JOB = "extractLissiaOrdersForFIDJob";

	/**
	 * Constant extract lissia order url
	 */
	private static final String EXTRACT_LISSIA_ORDER_FOR_FID_URL = "extractOrdersFID";

	/**
	 * Constant update lissia order url
	 */
	private static final String UPDATE_LISSIA_ORDER_URL = "liability/extract/update";
	private static final String EXTRACT_SAS_ORDER_FID = "webia/extract/orderFID";
	private static final String SAVE_SAS_ORDER_FID = "webia/extract/saveFID";

	private static final String FID_FILENAME = "ORDRES_";

	private static final String OK_STATUS = "OK";

	private static final String TXT_EXTENSION = ".TXT";
	
	private static final String DELIMITER = "\t";

	private static final char DEFAULT_DECIMAL_SEPARATOR = ',';

	private static final String INTERN = "INTERNE";
	
	private static final String FINISHED = "TERMINEE";
	private static final String CANCELED = "ANNULEE";
	private static final String CANCELED_SUFFIX = "_A";
	private static final short FINISHED_SHORT = 1;
	private static final short CANCELED_SHORT = 5;


	private Log logger = LogFactory.getLog(ExtractLissiaOrdersForFIDTasklet.class);

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Autowired
	private JobService jobService;
	
	/**
	 * Folder with the output files
	 */
	@Value("${extractLissiaOrderForFID}")
	private String fidFolder;
	
	private CsvFileWriter lissiaOrders;


	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			/**
			 * EXTRACT ORDER
			 */
			List<OrderFIDDTO> orders = new ArrayList<OrderFIDDTO>();
			int ordersNumber = 500;
			int pageNumber = 0;
			while(ordersNumber >= 500){
				List<OrderFIDDTO> newOrders = extractLissiaOrdersForFID(piaRootContextURL + EXTRACT_LISSIA_ORDER_FOR_FID_URL, pageNumber, 500);
				orders.addAll(newOrders);
				pageNumber++;
				ordersNumber = newOrders.size();
			}
			
			
			/**
			 * INSERT IN SAS
			 */
		
			int indexTo = 500;
			int numberOfPage = 0;
			while(indexTo >= 500){
				int index = numberOfPage*500;
				indexTo = orders.size()-index;
				if(indexTo > 500)
					indexTo = 500;
	
				List<OrderFIDDTO> subList = orders.subList(index, index + indexTo);
				numberOfPage++;
				
				SaveLissiaOrderFIDResponse saveResponse = insertFIDInSASREST(subList, piaRootContextURL + SAVE_SAS_ORDER_FID).getBody();
				if (saveResponse == null || !saveResponse.isSuccess()) {
					logger.info("Lissia order extraction is running");
				}
			}
			
			/**
			 * UPDATE LISSIA ORDERS
			 */
			
			logger.info("Lissia order extraction will begin");
			
				indexTo = 500;
				numberOfPage = 0;
				while(indexTo >= 500){
					int index = numberOfPage*500;
					indexTo = orders.size()-index;
					if(indexTo > 500)
						indexTo = 500;
		
				List<OrderFIDDTO> subList = orders.subList(index, index + indexTo);
					numberOfPage++;
					
					UpdateOrderResponse updateOrderResponse = updateLissiaOrder(subList, piaRootContextURL + UPDATE_LISSIA_ORDER_URL);
					if (updateOrderResponse == null || !updateOrderResponse.isSuccess()) {
						logger.info("Lissia order extraction is running");
					}
				}
			
				logger.info("Lissia order extraction OK");
			
			orders.clear();
			
			/**
			 * EXTRACT FROM SAS
			 */
			
			ordersNumber = 500;
			while(ordersNumber >= 500){
				List<OrderFIDDTO> newOrders = extractFIDFromSas(piaRootContextURL + EXTRACT_SAS_ORDER_FID, pageNumber, 500);
				orders.addAll(newOrders);
				pageNumber++;
				ordersNumber = newOrders.size();
			}
			
			
			
			/**
			 * WRITE FID FILE	
			 */
				initFiles();
				writeOrder(orders, lissiaOrders);
			

		} catch (Exception e) {
			logger.error("Erreur durant l extraction d ordres de LISSIA " + e);
			throw e;
		}finally {
			if(lissiaOrders != null)
				lissiaOrders.close();
		}
		return RepeatStatus.FINISHED;
	}

	

	



	@Scheduled(cron = "${extractLissiaOrdersForFIDCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the extraction of lissia orders for FID");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(InetAddress.getLocalHost().getHostAddress() + " : executionTime=" + timeStamp);

		jobService.launch(EXTRACT_JOB,
				new JobParametersBuilder(jobParameters).addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
	}

	

	/**
	 * Update lissia order into lissia database
	 * 
	 * @param extractOrderResponse the order extracted
	 * @param url the rest utl
	 * @return the update order response
	 */
	private UpdateOrderResponse updateLissiaOrder(List<OrderFIDDTO> orders, String url) {
		UpdateOrderResponse updateOrderResponse = null;
		if (orders != null) {
			UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest();			
			List<OrderDTO> ordersToUpdate = new ArrayList<OrderDTO>();
			for (OrderFIDDTO o : orders) {
				OrderDTO order = new OrderDTO();
				order.setLineId(o.getTransactionId());
				order.setGroupeId(o.getTransactionId());
				if(o.getStatus().equals(FINISHED))
					order.setStatus(FINISHED_SHORT);
				if(o.getStatus().equals(CANCELED))
					order.setStatus(CANCELED_SHORT);
				ordersToUpdate.add(order);
			}
			updateOrderRequest.setValorizedOrders(ordersToUpdate);
			ResponseEntity<UpdateOrderResponse> restResponse = updateLissiaOrderRest(url, updateOrderRequest);
			if (restResponse != null && restResponse.getBody() != null) {
				updateOrderResponse = restResponse.getBody();
			}
		}
		return updateOrderResponse;
	}

	/**
	 * Extract the lissia order for FID
	 * 
	 * @param url the web service url
	 * @return the extract order response
	 */
	private List<OrderFIDDTO> extractLissiaOrdersForFID(String url, int pageNumber, int pageSize) {
		List<OrderFIDDTO> transactionsList = new ArrayList<OrderFIDDTO>();
		
		ResponseEntity<ValorizedTransactionSearchResponse> extractOrdersForFIDRest = extractLissiaOrdersForFIDRest(url, pageNumber, pageSize);
		if (extractOrdersForFIDRest != null && extractOrdersForFIDRest.getBody().getOrders() != null) {
			transactionsList = extractOrdersForFIDRest.getBody().getOrders();
		}
		return transactionsList;
	}

	/**
	 * Rest call http for extract lissia orders for FID
	 * 
	 * @param url the url of the web service
	 * @return the rest response
	 */
	private ResponseEntity<ValorizedTransactionSearchResponse> extractLissiaOrdersForFIDRest(String url, int pageNumber, int pageSize) {
		logger.info("Token ...");
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();
		logger.info("Token OK" + token);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		ValorizedTransactionSearchRequest request = new ValorizedTransactionSearchRequest();
		request.setPageNum(pageNumber);
		request.setPageSize(pageSize);
		HttpEntity<ValorizedTransactionSearchRequest> entity = new HttpEntity<ValorizedTransactionSearchRequest>(request, headers);

		RestTemplate template = new RestTemplate();
		logger.info("Response extract lissia orders for FID...");
		
		ResponseEntity<ValorizedTransactionSearchResponse> response = template.exchange(url, HttpMethod.POST, entity, ValorizedTransactionSearchResponse.class);
		
		logger.info("Response OK");
		return response;
	}
	
	
	/**
	 * Rest call http for save SasOrder
	 * 
	 * @param url the url of the web service
	 * @return the rest response
	 */
	private ResponseEntity<ExtractOrderFIDResponse> extractFIDFromSasREST(String url, int pageNumber, int pageSize) {
		logger.info("Token ...");
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();
		logger.info("Token OK" + token);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		RestTemplate template = new RestTemplate();
		logger.info("Response ...");
		ResponseEntity<ExtractOrderFIDResponse> response = template.exchange(url, HttpMethod.GET, entity, ExtractOrderFIDResponse.class);
		logger.info("Response extract lissia orders for FID...");
		
		logger.info("Response OK");
		return response;
	}
	
	/**
	 * Extract FID from SAS
	 * 
	 * @param url the web service url
	 * @return the extract FID order response
	 */
	private List<OrderFIDDTO> extractFIDFromSas(String url, int pageNumber, int pageSize) {
		List<OrderFIDDTO> transactionsList = new ArrayList<OrderFIDDTO>();
		
		ResponseEntity<ExtractOrderFIDResponse> extractOrdersFromSAS = extractFIDFromSasREST(url, pageNumber, pageSize);
		if (extractOrdersFromSAS != null && extractOrdersFromSAS.getBody().getLissiaFID() != null) {
			transactionsList = extractOrdersFromSAS.getBody().getLissiaFID();
		}
		return transactionsList;
	}
	
	
	private ResponseEntity<SaveLissiaOrderFIDResponse> insertFIDInSASREST(List<OrderFIDDTO> subList, String url) {
		logger.info("Token ...");
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();
		logger.info("Token OK" + token);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		SaveLissiaOrderFIDRequest request = new SaveLissiaOrderFIDRequest();
		request.setLissiaOrderFID(subList);
		HttpEntity<SaveLissiaOrderFIDRequest> entity = new HttpEntity<SaveLissiaOrderFIDRequest>(request, headers);

		RestTemplate template = new RestTemplate();
		logger.info("Response extract lissia orders for FID...");
		
		ResponseEntity<SaveLissiaOrderFIDResponse> response = template.exchange(url, HttpMethod.POST, entity, SaveLissiaOrderFIDResponse.class);
		
		logger.info("Response OK");
		return response;
		
	}
	
	



	/**
	 * Rest call http for update lissia order
	 * 
	 * @param url the url of the web service
	 * @param updateOrderRequest the list of order to update
	 * @return the rest response
	 */
	private ResponseEntity<UpdateOrderResponse> updateLissiaOrderRest(String url, UpdateOrderRequest updateOrderRequest) {
		logger.info("Token ...");
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();
		logger.info("Token OK" + token);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<UpdateOrderRequest> entity = new HttpEntity<UpdateOrderRequest>(updateOrderRequest, headers);

		RestTemplate template = new RestTemplate();
		logger.info("Response update lissia order...");
		ResponseEntity<UpdateOrderResponse> response = template.exchange(url, HttpMethod.POST, entity, UpdateOrderResponse.class);
		logger.info("Response OK");
		return response;
	}
	
	/**
	 * init files
	 * 
	 */
	private void initFiles() {

		// Initialization of file
		File orderFile = new File(fidFolder, FID_FILENAME + getCurrentDate() + OK_STATUS + TXT_EXTENSION);
		lissiaOrders = new CsvFileWriter(orderFile.getAbsolutePath());
	}
	
	
	/**
	 * Get the current date
	 * 
	 * @return the current date
	 */
	private String getCurrentDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();
		return sdf.format(date);
	}
	
	/**
	 * Write order in a file
	 * 
	 * @param daliEstimatedOrder2
	 */
	private void writeOrder(List<OrderFIDDTO> orders, CsvFileWriter csvWriter) {
		DecimalFormat decimalSeparator = new DecimalFormat("0.000000");
		decimalSeparator.setGroupingUsed(Boolean.FALSE);
		DecimalFormatSymbols decimalFormatSymbols = decimalSeparator.getDecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator(DEFAULT_DECIMAL_SEPARATOR);
		decimalSeparator.setDecimalFormatSymbols(decimalFormatSymbols);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		
		

		if (CollectionUtils.isEmpty(orders)) {
			csvWriter.append("No elements found");
		} else {
			for (OrderFIDDTO order : orders) {
				
				String injectionReferenceCanceled = "";
				
				if(order.getTransactionId() != null && order.getStatus() != null && order.getStatus().equals(CANCELED)){
					injectionReferenceCanceled = order.getTransactionId().split("_")[0];
				}
				
				
					csvWriter.appendWithDelimiter(DELIMITER, "DLP",
							order.getTransactionId() == null ? StringUtils.EMPTY : order.getTransactionId(),
							injectionReferenceCanceled,
							order.getFundCurrency() == null ? StringUtils.EMPTY : order.getFundCurrency(),
							order.getQuantity() == null ? StringUtils.EMPTY : decimalSeparator.format(order.getQuantity()),
							order.getPrice() == null ? StringUtils.EMPTY : decimalSeparator.format(order.getPrice()),
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							order.getTransactionDate() == null ? StringUtils.EMPTY : sdf.format(order.getTransactionDate()),
							order.getTransactionDate() == null ? StringUtils.EMPTY : sdf.format(order.getTransactionDate()),
							order.getTransactionDate() == null ? StringUtils.EMPTY : sdf.format(order.getTransactionDate()),
							order.getTransactionDate() == null ? StringUtils.EMPTY : sdf.format(order.getTransactionDate()),
							order.getTransactionType() == null ? StringUtils.EMPTY : order.getTransactionType(),
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							order.getStatus(),
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							order.getEventType() == null ? StringUtils.EMPTY : order.getEventType(),
							StringUtils.EMPTY,
							INTERN,
							order.getFunds() == null ? StringUtils.EMPTY : order.getFunds(),
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							order.getPortfolioCode() == null ? StringUtils.EMPTY : order.getPortfolioCode(),
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							order.getMirrorPortfolioCode() == null ? StringUtils.EMPTY : order.getMirrorPortfolioCode(),
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							StringUtils.EMPTY,
							order.getFundCurrency() == null ? StringUtils.EMPTY : order.getFundCurrency(),
							"\r\n");		// new line
				}
				
		}
	}
}

package lu.wealins.batch.extract;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lu.wealins.common.dto.OrderDTO;
import lu.wealins.common.dto.liability.services.ExtractOrderResponse;
import lu.wealins.common.dto.liability.services.UpdateOrderRequest;
import lu.wealins.common.dto.liability.services.UpdateOrderResponse;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderRequest;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderResponse;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;

/**
 * Tasklet to extract order from lissia and save them into Webia database
 * 
 * @author xqv60
 *
 */
@EnableScheduling
public class ExtractLissiaOrderTasklet implements Tasklet {

	/**
	 * Constant JOB Parameters extractor
	 */
	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();

	/**
	 * Default value for last id
	 */
	private static final String INIT_LAST_ID = "0";

	/**
	 * The constant VALORIZED TYPE OF ORDER
	 */
	private static final String VALORIZED_TYPE = "valorizedId";

	/**
	 * The constant ESTIMATED_TYPE
	 */
	private static final String ESTIMATED_TYPE = "estimatedId";

	/**
	 * The DEFAULT PAGE SIZE FOR THE PAGINATION
	 */
	private final static int PAGE_SIZE = 1000;

	/**
	 * Constant job name
	 */
	private static final String EXTRACT_JOB = "extractLissiaOrderJob";

	/**
	 * Constant extract lissia order url
	 */
	private static final String EXTRACT_LISSIA_ORDER_URL = "liability/extract/order";

	/**
	 * Constant update lissia order url
	 */
	private static final String UPDATE_LISSIA_ORDER_URL = "liability/extract/update";

	/**
	 * Constant save lissia order url
	 */
	private static final String SAVE_LISSIA_ORDER_URL = "webia/extract/save";

	private Log logger = LogFactory.getLog(ExtractLissiaOrderTasklet.class);

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Autowired
	private JobService jobService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			int page = 0;
			ExtractOrderResponse extractOrderResponse = null;

			Map<String, String> lastIdsByOrderType = new HashMap<String, String>();
			{
				lastIdsByOrderType.put(VALORIZED_TYPE, INIT_LAST_ID);
				lastIdsByOrderType.put(ESTIMATED_TYPE, INIT_LAST_ID);
			}
			Map<String, String> lastIdsSaved = new HashMap<String, String>();
			{
				lastIdsSaved.put(VALORIZED_TYPE, INIT_LAST_ID);
				lastIdsSaved.put(ESTIMATED_TYPE, INIT_LAST_ID);
			}

			do {
				// Update last ids saved
				lastIdsSaved.put(VALORIZED_TYPE, lastIdsByOrderType.get(VALORIZED_TYPE));
				lastIdsSaved.put(ESTIMATED_TYPE, lastIdsByOrderType.get(ESTIMATED_TYPE));

				// Execute batch
				extractOrderResponse = extractLissiaOrder(piaRootContextURL + EXTRACT_LISSIA_ORDER_URL, page, lastIdsByOrderType);
				SaveLissiaOrderResponse saveLissiaOrderResponse = saveLissiaOrder(extractOrderResponse, piaRootContextURL + SAVE_LISSIA_ORDER_URL);

				if (saveLissiaOrderResponse == null || !saveLissiaOrderResponse.isSuccess()) {
					logger.error("Lissia order extraction finished with error. See details into the log files");
				} else {
					UpdateOrderResponse updateOrderResponse = updateLissiaOrder(extractOrderResponse, piaRootContextURL + UPDATE_LISSIA_ORDER_URL);
					if (updateOrderResponse == null || !updateOrderResponse.isSuccess()) {
						logger.info("Lissia order extraction finished");
					}
				}
			} while (!lastIdsSaved.get(VALORIZED_TYPE).equals(lastIdsByOrderType.get(VALORIZED_TYPE)) ||
					!lastIdsSaved.get(ESTIMATED_TYPE).equals(lastIdsByOrderType.get(ESTIMATED_TYPE)));

		} catch (Exception e) {
			logger.error("Erreur durant l extraction d ordres de LISSIA " + e);
			throw e;
		}
		return RepeatStatus.FINISHED;
	}

	@Scheduled(cron = "${extractLissiaOrderCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the extraction of lissia order");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(InetAddress.getLocalHost().getHostAddress() + " : executionTime=" + timeStamp);

		jobService.launch(EXTRACT_JOB,
				new JobParametersBuilder(jobParameters).addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
	}

	/**
	 * Save lissia order into webia database
	 * 
	 * @param extractOrderResponse the order extracted
	 * @param url the rest url
	 * @return the save lissia order response
	 */
	private SaveLissiaOrderResponse saveLissiaOrder(ExtractOrderResponse extractOrderResponse, String url) {
		SaveLissiaOrderResponse saveLissiaOrderResponse = null;

		if (extractOrderResponse != null) {
			SaveLissiaOrderRequest saveLissiaOrderRequest = buildSaveLissiaOrderRequest(extractOrderResponse);
			ResponseEntity<SaveLissiaOrderResponse> restResponse = saveLissiaOrderRest(url, saveLissiaOrderRequest);
			if (restResponse != null && restResponse.getBody() != null) {
				saveLissiaOrderResponse = restResponse.getBody();
			}
		}
		return saveLissiaOrderResponse;
	}

	/**
	 * Build SaveLissiaOrderRequest from ExtractOrderResponse
	 * 
	 * @param extractOrderResponse the extract order response from LISSIA
	 * @return the SaveLissiaOrderRequest
	 */
	private SaveLissiaOrderRequest buildSaveLissiaOrderRequest(ExtractOrderResponse extractOrderResponse) {
		SaveLissiaOrderRequest saveLissiaOrderRequest = new SaveLissiaOrderRequest();

		// Merge estimated order
		if (extractOrderResponse.getEstimatedResult() != null && !CollectionUtils.isEmpty(extractOrderResponse.getEstimatedResult().getContent())) {
			saveLissiaOrderRequest.getLissiaEstimatedOrder()
					.addAll(extractOrderListFilter(extractOrderResponse.getEstimatedResult().getContent()));
		}
		// Merge valorized order
		if (extractOrderResponse.getValorizedResult() != null && !CollectionUtils.isEmpty(extractOrderResponse.getValorizedResult().getContent())) {
			saveLissiaOrderRequest.getLissiaValorizedOrder()
					.addAll(extractOrderListFilter(extractOrderResponse.getValorizedResult().getContent()));
		}
		return saveLissiaOrderRequest;
				
				
				
	}

	/**
	 * Update lissia order into lissia database
	 * 
	 * @param extractOrderResponse the order extracted
	 * @param url the rest utl
	 * @return the update order response
	 */
	private UpdateOrderResponse updateLissiaOrder(ExtractOrderResponse extractOrderResponse, String url) {
		UpdateOrderResponse updateOrderResponse = null;
		if (extractOrderResponse != null) {
			UpdateOrderRequest updateOrderRequest = buildUpdateOrderRequest(extractOrderResponse);
			ResponseEntity<UpdateOrderResponse> restResponse = updateLissiaOrderRest(url, updateOrderRequest);
			if (restResponse != null && restResponse.getBody() != null) {
				updateOrderResponse = restResponse.getBody();
			}
		}
		return updateOrderResponse;
	}

	/**
	 * Build UpdateOrderRequest from ExtractOrderResponse
	 * 
	 * @param extractOrderResponse the extract order response from LISSIA
	 * @return the UpdateOrderRequest
	 */
	private UpdateOrderRequest buildUpdateOrderRequest(ExtractOrderResponse extractOrderResponse) {
		UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest();

		// Merge estimated order
		if (extractOrderResponse.getEstimatedResult() != null && !CollectionUtils.isEmpty(extractOrderResponse.getEstimatedResult().getContent())) {
			updateOrderRequest.getEstimatedOrders()
					.addAll(extractOrderListFilter(extractOrderResponse.getEstimatedResult().getContent()));
		}
		// Merge valorized order
		if (extractOrderResponse.getValorizedResult() != null && !CollectionUtils.isEmpty(extractOrderResponse.getValorizedResult().getContent())) {
			updateOrderRequest.getValorizedOrders()
					.addAll(extractOrderListFilter(extractOrderResponse.getValorizedResult().getContent()));
		}
		return updateOrderRequest;
	}

	/**
	 * Extract the lissia order
	 * 
	 * @param url the web service url
	 * @param page the page searched
	 * @param lastIdsByOrderType the last id map retrieved by orders type
	 * @return the extract order response
	 */
	private ExtractOrderResponse extractLissiaOrder(String url, int page, Map<String, String> lastIdsByOrderType) {
		ExtractOrderResponse extractOrderResponse = new ExtractOrderResponse();

		ResponseEntity<ExtractOrderResponse> extractOrderRest = extractLissiaOrderRest(url, page, lastIdsByOrderType);
		if (extractOrderRest != null && extractOrderRest.getBody() != null) {
			extractOrderResponse = extractOrderRest.getBody();
			// Update lastIds maps
			if (extractOrderResponse.getEstimatedResult() != null && extractOrderResponse.getEstimatedResult().getLastId() != null) {
				lastIdsByOrderType.replace(ESTIMATED_TYPE, String.valueOf(extractOrderResponse.getEstimatedResult().getLastId()));
			}
			if (extractOrderResponse.getValorizedResult() != null && extractOrderResponse.getValorizedResult().getLastId() != null) {
				lastIdsByOrderType.replace(VALORIZED_TYPE, String.valueOf(extractOrderResponse.getValorizedResult().getLastId()));
			}
		}
		return extractOrderResponse;
	}

	/**
	 * Rest call http for extract lissia order
	 * 
	 * @param url the url of the web service
	 * @param page the page searched
	 * @param lastIdsByOrderType the last id map retrieved by orders type
	 * @return the rest response
	 */
	private ResponseEntity<ExtractOrderResponse> extractLissiaOrderRest(String url, int page, Map<String, String> lastIdsByOrderType) {
		logger.info("Token ...");
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();
		logger.info("Token OK" + token);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		// get URI and query parameters
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", String.valueOf(page));
		params.add("size", String.valueOf(PAGE_SIZE));
		params.add(VALORIZED_TYPE, lastIdsByOrderType.get(VALORIZED_TYPE));
		params.add(ESTIMATED_TYPE, lastIdsByOrderType.get(ESTIMATED_TYPE));

		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).queryParams(params).build();

		RestTemplate template = new RestTemplate();
		logger.info("Response extract lissia order...");
		ResponseEntity<ExtractOrderResponse> response = template.exchange(uriComponents.toUri(), HttpMethod.GET, entity, ExtractOrderResponse.class);
		logger.info("Response OK");
		return response;
	}

	/**
	 * Rest call http for save lissia order
	 * 
	 * @param url the url of the web service
	 * @param saveLissiaOrderRequest the list of Lissia order
	 * @return the rest response
	 */
	private ResponseEntity<SaveLissiaOrderResponse> saveLissiaOrderRest(String url, SaveLissiaOrderRequest saveLissiaOrderRequest) {
		logger.info("Token ...");
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();
		logger.info("Token OK" + token);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<SaveLissiaOrderRequest> entity = new HttpEntity<SaveLissiaOrderRequest>(saveLissiaOrderRequest, headers);

		RestTemplate template = new RestTemplate();
		logger.info("Response save lissia order...");
		ResponseEntity<SaveLissiaOrderResponse> response = template.exchange(url, HttpMethod.POST, entity, SaveLissiaOrderResponse.class);
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
	 * Filter Order that have nav equals zero.
	 * <p>
	 * this filter is made to avoid division by zero in case of aggregation when saving lissia orders.
	 * 
	 * @param extractOrderResponse
	 * @return filtered extractOrderResponse
	 * 
	 */
	private List<OrderDTO> extractOrderListFilter(List<OrderDTO> orders) {

		if (orders == null) {
			return orders;
		}

		Predicate<OrderDTO> orderPredicate = (order) -> order.getNav() != null
				&& BigDecimal.ZERO.compareTo(order.getNav()) != 0;

		List<OrderDTO> filteredOrders = orders.stream().filter(orderPredicate)
					.collect(Collectors.toList());
					
		return filteredOrders;
	}}

/**
 * 
 */
package lu.wealins.webia.core.service.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.webia.services.TransferExecutionRequest;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.SynchronousDocumentService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.CustomPaymentsPk;
import lu.wealins.webia.ws.rest.request.EditingRequest;

/**
 * Class for SEPA file generation helper.
 * 
 * @author lax
 *
 */
@Component
public class SwiftHelper {
	
	private static final String WEBIA_TRANSFERS = "webia/transfer/";
	private static final String LIST = "list";
	
	@Autowired
	private LiabilityFundService liabilityFundService;

	@Autowired
	private EditingService editingService;

	@Autowired
	private SynchronousDocumentService synchronousDocumentService;

	@Autowired
	private RestClientUtils restClientUtils;
	
	public List<EditingRequest> createSwiftEditingRequests(TransferExecutionRequest documentRequest) {
		// Group the data by is FID, is Wealins dedicated, payment date and swift code
		
		Map<String, String> transfertBySwift= groupPayments(documentRequest.getIds());
		
		return transfertBySwift
				.entrySet()
				.stream()
				.map(entry -> {
					return editingService.createSwiftPaymentRequest(entry.getValue());
				})
				.collect(Collectors.toList());
	}

	public void executeDocuments(SecurityContext context, TransferExecutionRequest documentRequest) {
		createSwiftEditingRequests(documentRequest).forEach(
				editingRequest -> synchronousDocumentService.createDocumentSynchronously(context, editingRequest));
	}

	/**
	 * Group the data by is FID, is Wealins dedicated, payment date and swift code.
	 * 
	 * @param ids
	 * @return
	 */
	private Map<String, String> groupPayments(List<Long> ids) {
		Map<CustomPaymentsPk, List<TransferDTO>> map = new HashMap<CustomPaymentsPk, List<TransferDTO>>();
		Collection<TransferDTO> transfers = restClientUtils.post(WEBIA_TRANSFERS + LIST, ids, new GenericType<Collection<TransferDTO>>() {
		});
		
		if (transfers ==  null || transfers.isEmpty()) {
			return new HashMap<String,String>();
		}
		return transfers.stream().collect(Collectors.groupingBy((TransferDTO transfer) -> transfer.getSwiftDonOrd(), Collectors.mapping((TransferDTO transfer) -> transfer.getTransferId().toString(), Collectors.joining(","))));
		
	}

	private String mapDtosToString(List<TransferDTO> transfers) {
		return transfers.stream().map(t -> t.getTransferId().toString()).collect(Collectors.joining(","));
	}
}

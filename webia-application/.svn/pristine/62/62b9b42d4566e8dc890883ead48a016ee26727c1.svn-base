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
import lu.wealins.common.dto.webia.services.SepaDocumentRequest;
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
public class SEPAHelper {
	
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
	
	public List<EditingRequest> createSepaEditingRequests(SepaDocumentRequest sepaDocumentRequest) {
		// Group the data by is FID, is Wealins dedicated, payment date and swift code
		return groupPayments(sepaDocumentRequest.getIds())
				.entrySet()
				.stream()
				.map(entry -> {
					String groupedIds = mapDtosToString(entry.getValue());
					return editingService.createSepaPaymentRequest(groupedIds);
				})
				.collect(Collectors.toList());
	}

	public void executeDocuments(SecurityContext context, SepaDocumentRequest sepaDocumentRequest) {
		createSepaEditingRequests(sepaDocumentRequest).forEach(
				editingRequest -> synchronousDocumentService.createDocumentSynchronously(context, editingRequest));
	}

	/**
	 * Group the data by is FID, is Wealins dedicated, payment date and swift code.
	 * 
	 * @param ids
	 * @return
	 */
	private Map<CustomPaymentsPk, List<TransferDTO>> groupPayments(List<Long> ids) {
		Map<CustomPaymentsPk, List<TransferDTO>> map = new HashMap<CustomPaymentsPk, List<TransferDTO>>();
		Collection<TransferDTO> transfers = restClientUtils.post(WEBIA_TRANSFERS + LIST, ids, new GenericType<Collection<TransferDTO>>() {
		});
		for (TransferDTO t : transfers) {
			CustomPaymentsPk key  = new CustomPaymentsPk();
			
			// Key fid
			FundDTO fund = liabilityFundService.getFund(t.getFdsId());
			key.setFid(fund != null && FundSubType.FID.name().equals(fund.getFundSubType()));			
			// Key fisa dedicated account
			key.setFisaDedicatedAccount(true);
			// Key payment date
			if (t.getTrfDt() != null && t.getTrfDt().after(new Date())) {
				key.setPaymentDate(t.getTrfDt());
			} else {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date today = new Date();
				Date todayWithZeroTime = new Date();
				try {
					todayWithZeroTime = formatter.parse(formatter.format(today));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				key.setPaymentDate(todayWithZeroTime);
			}			
			// Key swift
			key.setSwift(StringUtils.trimAllWhitespace(t.getSwiftDonOrd()));
			
			if (map.containsKey(key)){
				map.get(key).add(t);
			} else {
				List<TransferDTO> list = new ArrayList<TransferDTO>();
				list.add(t);
				map.put(key, list);
			}
		}
		return map;
	}

	private String mapDtosToString(List<TransferDTO> transfers) {
		return transfers.stream().map(t -> t.getTransferId().toString()).collect(Collectors.joining(","));
	}
}

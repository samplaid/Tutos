package lu.wealins.webia.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.AdminFeesDTO;
import lu.wealins.webia.core.service.LiabilityAccountTransactionService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityAccountTransactionServiceImpl implements LiabilityAccountTransactionService {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final String LIABILITY_ACCOUNT_TRANSACTION = "liability/accountTransaction/";
	private static final String ADMIN_FEES = "adminFees";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public Collection<AdminFeesDTO> geteAdminFees(String policyId, Date effectiveDate) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("policyId", policyId);
		queryParams.add("effectiveDate", DATE_FORMAT.format(effectiveDate));

		return restClientUtils.get(LIABILITY_ACCOUNT_TRANSACTION, ADMIN_FEES, queryParams, new GenericType<Collection<AdminFeesDTO>>() {
		});
	}
}

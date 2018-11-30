package lu.wealins.webia.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.PolicyTransactionValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.webia.core.service.LiabilityPolicyValuationService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityPolicyValuationServiceImpl implements LiabilityPolicyValuationService {

	private static final String POLICY_ID_CANNOT_BE_NULL = "Policy id cannot be null.";
	private static final String LIABILITY_LOAD_POLICY_VALUATION = "liability/policy/valuation?";
	private static final String LIABILITY_POLICY = "liability/policy";

	private static final String LIABILITY_POLICY_VALUATION_AFTER_TRANSACTION = "/frenchTaxPolicyValuationAfterTransaction";

	private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");

	@Autowired
	private RestClientUtils restClientUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityPolicyValuationService#getPolicyValuation(java.lang.String)
	 */
	@Override
	public PolicyValuationDTO getPolicyValuation(String policyId, String currency, Date date) {
		Assert.notNull(policyId, POLICY_ID_CANNOT_BE_NULL);
		StringBuilder query = new StringBuilder("id=" + policyId);
		if (StringUtils.hasText(currency)) {
			query.append("&currency=").append(currency);
		}
		if (date != null) {
			query.append("&date=").append(sdf.format(date));
		}
		return restClientUtils.get(LIABILITY_LOAD_POLICY_VALUATION, query.toString(), PolicyValuationDTO.class);
	}

	@Override
	public Collection<PolicyTransactionValuationDTO> getPolicyValuationAfterTransaction(Long transactionId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("transactionId", transactionId);

		return restClientUtils.get(LIABILITY_POLICY, LIABILITY_POLICY_VALUATION_AFTER_TRANSACTION, params,
				new GenericType<Collection<PolicyTransactionValuationDTO>>() {
				});
	}

	@Override
	public PolicyValuationHoldingDTO getFundValuation(PolicyValuationDTO policyValuation, String fdsId) {
		return policyValuation.getHoldings().stream().filter(x -> x.getFundId().equalsIgnoreCase(fdsId)).findFirst().orElse(null);
	}

}

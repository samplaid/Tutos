package lu.wealins.webia.core.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.UnitByCoverageDTO;
import lu.wealins.common.dto.liability.services.UnitByFundAndCoverageDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.core.mapper.AdditionalPremiumMapper;
import lu.wealins.webia.core.service.LiabilityPolicyCoverageService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityPolicyCoverageServiceImpl implements LiabilityPolicyCoverageService {

	private static final String FUNDS = "funds";
	private static final String UNIT_BY_COVERAGES = "unitByCoverages";
	private static final String UNIT_BY_FUNDS_AND_COVERAGES = "unitByFundsAndCoverages";

	private static final String POLICY_ID = "policyId";

	private static final String LIABILITY_POLICY_COVERAGE = "liability/coverage/";

	private static final String LIABILITY_ADDITIONAL_PREMIUM = "additional";

	private static final String LAST = "last";
	private static final String FIRST = "first";

	private static final String LIABILITY_VALIDATE_COVERAGE = "validate";

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private AdditionalPremiumMapper additionalPremiumMapper;

	@Override
	public PolicyCoverageDTO createAdditionalPremium(AppFormDTO appForm) {
		AdditionalPremiumDTO request = additionalPremiumMapper.asAdditionalPremiumDTO(appForm);

		return restClientUtils.post(LIABILITY_POLICY_COVERAGE + LIABILITY_ADDITIONAL_PREMIUM, request, PolicyCoverageDTO.class);
	}

	@Override
	public PolicyCoverageDTO getLastPolicyCoverage(String policyId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add(POLICY_ID, policyId);

		return restClientUtils.get(LIABILITY_POLICY_COVERAGE, LAST, params, PolicyCoverageDTO.class);
	}

	@Override
	public PolicyCoverageDTO getFirstPolicyCoverage(String policyId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add(POLICY_ID, policyId);

		return restClientUtils.get(LIABILITY_POLICY_COVERAGE, FIRST, params, PolicyCoverageDTO.class);
	}

	@Override
	public List<String> validateCreationNewCoverage(String policyId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add(POLICY_ID, policyId);

		return restClientUtils.get(LIABILITY_POLICY_COVERAGE, LIABILITY_VALIDATE_COVERAGE, params, new GenericType<List<String>>() {
		});
	}

	@Override
	public Map<String, Collection<Integer>> getCoverages(String polId, Collection<String> fdsIds) {

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("polId", polId);
		fdsIds.forEach(fdsId -> params.add("fdsIds", fdsId));

		return restClientUtils.get(LIABILITY_POLICY_COVERAGE, FUNDS, params, new GenericType<Map<String, Collection<Integer>>>() {
		});
	}

	@Override
	public Collection<UnitByCoverageDTO> getUnitByCoverages(String polId) {

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("polId", polId);

		return restClientUtils.get(LIABILITY_POLICY_COVERAGE, UNIT_BY_COVERAGES, params, new GenericType<Collection<UnitByCoverageDTO>>() {
		});
	}

	@Override
	public Collection<UnitByFundAndCoverageDTO> getUnitByFundsAndCoverages(String polId) {

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("polId", polId);

		return restClientUtils.get(LIABILITY_POLICY_COVERAGE, UNIT_BY_FUNDS_AND_COVERAGES, params, new GenericType<Collection<UnitByFundAndCoverageDTO>>() {
		});
	}

}

package lu.wealins.liability.services.core.business.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.tempuri.wssadcvg.WSSADCVG;
import org.tempuri.wssadcvg.WssadcvgExport;
import org.tempuri.wssadcvg.WssadcvgImport;

import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO;
import lu.wealins.common.dto.liability.services.FundTransactionDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.UnitByCoverageDTO;
import lu.wealins.common.dto.liability.services.UnitByFundAndCoverageDTO;
import lu.wealins.liability.services.core.business.FundTransactionService;
import lu.wealins.liability.services.core.business.PolicyCoverageService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.business.PolicyTransferService;
import lu.wealins.liability.services.core.business.exceptions.PolicyException;
import lu.wealins.liability.services.core.business.exceptions.WebServiceInvocationException;
import lu.wealins.liability.services.core.mapper.AdditionalPremiumMapper;
import lu.wealins.liability.services.core.mapper.PolicyCoverageMapper;
import lu.wealins.liability.services.core.persistence.entity.PolicyCoverageEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyTransferEntity;
import lu.wealins.liability.services.core.persistence.repository.PolicyCoverageRepository;

@Service
public class PolicyCoverageServiceImpl implements PolicyCoverageService {

	private static final String POLICY_DOES_NOT_EXIST = "Policy does not exist.";

	private static final String UNITS_BY_COVERAGE_QUERY = "SELECT COVERAGE, sum(UNITS) FROM FUND_TRANSACTIONS where POLICY like ? group by COVERAGE having sum(UNITS) > 0";
	private static final String UNITS_BY_FUND_AND_COVERAGE_QUERY = "SELECT FUND, COVERAGE, sum(UNITS) FROM FUND_TRANSACTIONS where POLICY like ? group by FUND, COVERAGE having sum(UNITS) > 0";

	@Autowired
	private PolicyCoverageMapper policyCoverageMapper;

	@Autowired
	private PolicyTransferService policyTransferService;

	@Autowired
	private PolicyService policyService;

	@Autowired
	private AdditionalPremiumMapper additionalPremiumMapper;

	@Autowired
	private WSSADCVG wssadcvg;

	@Autowired
	private PolicyCoverageRepository policyCoverageRepository;

	@Autowired
	private FundTransactionService fundTransactionService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final int EXPECTED_EXIT_CODE = 99999;

	private static final int COVERAGE_ACTIVE_STATUS = 1;
	private static final int COVERAGE_PENDING_STATUS = 3;

	@Override
	public PolicyCoverageDTO getFirstPolicyCoverage(PolicyEntity policyEntity) {
		Assert.notNull(policyEntity, POLICY_DOES_NOT_EXIST);
		Optional<PolicyCoverageEntity> firstCoverage = policyEntity.getPolicyCoverages().stream()
				.filter(a -> a.getCoverage() == 1 && a.getStatus() != 2)
				.sorted((o1, o2) -> o1.getStatus() - o2.getStatus())
				.findFirst();
		if (firstCoverage.isPresent()) {
			return policyCoverageMapper.asPolicyCoverageDTO(firstCoverage.get());
		}
		return null;
	}

	public PolicyCoverageDTO getFirstPolicyCoverage(String polId) {
		return getFirstPolicyCoverage(policyService.getPolicyEntity(polId));
	}

	@Override
	public PolicyCoverageDTO getCoverage(PolicyEntity policyEntity, Integer coverage) {
		Optional<PolicyCoverageEntity> firstCoverage = policyEntity.getPolicyCoverages().stream()
				.filter(a -> a.getCoverage() == coverage && a.getPolicyId().trim().equalsIgnoreCase(policyEntity.getPolId().trim()))
				.findFirst();
		if (firstCoverage.isPresent()) {
			return policyCoverageMapper.asPolicyCoverageDTO(firstCoverage.get());
		}
		return null;
	}

	@Override
	public PolicyCoverageDTO createPolicyPremium(AdditionalPremiumDTO request) {
		WssadcvgExport export = new WssadcvgExport();
		try {
			export = callPremiumWs(request);
		} catch (org.tempuri.wssadcvg.Exception_Exception e) {
			throw new WebServiceInvocationException(e.getMessage(), e);
		}

		String polId = request.getImpPolPolicies().getPolId();
		PolicyEntity policyEntity = policyService.getPolicyEntity(polId);

		updateAdditionalPremiumPolicyTransfers(request, policyEntity);

		PolicyCoverageDTO policyCoverage = getCoverage(policyEntity, new Integer(export.getExpPocPolicyCoverages().getCoverage()));

		return policyCoverage;
	}

	private WssadcvgExport callPremiumWs(AdditionalPremiumDTO request) throws org.tempuri.wssadcvg.Exception_Exception {
		WssadcvgImport wssadcvgImport = additionalPremiumMapper.asWssadcvgImport(request);
		wssadcvgImport.setExitState(EXPECTED_EXIT_CODE);
		WssadcvgExport wsResponse = wssadcvg.wssadcvgcall(wssadcvgImport);
		if (wsResponse.getExitState() != EXPECTED_EXIT_CODE) {
			throw new PolicyException(wsResponse.getExpErrormessageBrowserFields().getErrorMessage());
		}
		return wsResponse;
	}

	private void updateAdditionalPremiumPolicyTransfers(AdditionalPremiumDTO request, PolicyEntity policyEntity) {
		Collection<PolicyTransferEntity> policyTransfers = policyTransferService.update(request.getPolicyTransfers());

		policyEntity.setPolicyTransfers(policyTransfers.stream().collect(Collectors.toSet()));

	}

	@Override
	public PolicyCoverageDTO getLastPolicyCoverage(String policyId) {
		return policyCoverageMapper.asPolicyCoverageDTO(policyCoverageRepository.findFirstByStatusAndPolicyIdOrderByCoverageDesc(COVERAGE_ACTIVE_STATUS, policyId));
	}

	@Override
	public List<String> validateNewCoverageCreation(String policyId) {
		List<String> errors = new ArrayList<>();
		if (policyCoverageRepository.hasPendingCoverage(policyId, COVERAGE_PENDING_STATUS)) {
			errors.add("There is already a pending coverage for the policy");
		}
		return errors;
	}

	@Override
	public PolicyCoverageDTO getCoverage(String policyId, int coverage) {
		return policyCoverageMapper.asPolicyCoverageDTO(policyCoverageRepository.findByPolicyIdAndCoverage(policyId, coverage));
	}

	@Override
	public Collection<Integer> getCoverages(String polId, String fdsId) {
		Assert.notNull(polId);
		Assert.notNull(fdsId);

		Collection<FundTransactionDTO> fundTransactions = fundTransactionService.getFundTransactions(polId, fdsId, null, null);

		return fundTransactions.stream().map(x -> x.getCoverage()).collect(Collectors.toSet());
	}

	@Override
	public Map<String, Collection<Integer>> getCoverages(String polId, Collection<String> fdsIds) {
		Assert.notEmpty(fdsIds);
		Map<String, Collection<Integer>> coveragesByFunds = new HashMap<>();

		for (FundTransactionDTO fundTransaction : fundTransactionService.getFundTransactions(polId, fdsIds, null, null)) {
			String fund = fundTransaction.getFund();

			Collection<Integer> coveragesByFund = coveragesByFunds.get(fund);
			if (coveragesByFund == null) {
				coveragesByFund = new HashSet<>();
				coveragesByFunds.put(fund, coveragesByFund);
			}

			coveragesByFund.add(fundTransaction.getCoverage());

		}

		return coveragesByFunds;
	}

	@Override
	public Collection<UnitByCoverageDTO> getUnitByCoverages(String polId) {
		Assert.notNull(polId);

		Object[] params = new Object[] { polId };

		// Use the same query as the policy valuation service
		return jdbcTemplate.query(UNITS_BY_COVERAGE_QUERY, params,
				new RowMapper<UnitByCoverageDTO>() {

					@Override
					public UnitByCoverageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						UnitByCoverageDTO unit = new UnitByCoverageDTO();

						unit.setCoverage(rs.getInt(1));
						unit.setUnits(rs.getBigDecimal(2));

						return unit;
					}

				});
	}

	@Override
	public Collection<UnitByFundAndCoverageDTO> getUnitByFundsAndCoverages(String polId) {
		Assert.notNull(polId);

		Object[] params = new Object[] { polId };

		// Use the same query as the policy valuation service
		return jdbcTemplate.query(UNITS_BY_FUND_AND_COVERAGE_QUERY, params,
				new RowMapper<UnitByFundAndCoverageDTO>() {

					@Override
					public UnitByFundAndCoverageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						UnitByFundAndCoverageDTO unit = new UnitByFundAndCoverageDTO();

						unit.setFdsId(rs.getString(1));
						unit.setCoverage(rs.getInt(2));
						unit.setUnits(rs.getBigDecimal(3));

						return unit;
					}

				});
	}

}

package lu.wealins.webia.ws.rest.impl;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.DeathCoverageClauseDTO;
import lu.wealins.common.dto.liability.services.DeathCoverageClausesDTO;
import lu.wealins.webia.core.service.LiabilityDeathCoverageService;
import lu.wealins.webia.ws.rest.LiabilityDeathCoverageRESTService;

@Component
public class LiabilityDeathCoverageRESTServiceImpl implements LiabilityDeathCoverageRESTService {

	@Autowired
	private LiabilityDeathCoverageService liabilityDeathCoverageService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityDeathCoverageRESTService#getDeathCoverageClauses(java.lang.String)
	 */
	@Override
	public DeathCoverageClausesDTO getDeathCoverageClauses(String productCd) {
		return liabilityDeathCoverageService.getDeathCoverageClauses(productCd);
	}

	@Override
	public DeathCoverageClauseDTO getPolicyDeathCoverage(@Context SecurityContext context, String polId) {
		return liabilityDeathCoverageService.getPolicyDeathCoverage(polId);
	}
}

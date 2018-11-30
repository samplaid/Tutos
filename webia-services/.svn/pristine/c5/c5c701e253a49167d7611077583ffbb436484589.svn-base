package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.services.core.service.BenefClauseStdService;
import lu.wealins.webia.services.ws.rest.BenefClauseStdRESTService;
import lu.wealins.common.dto.webia.services.BenefClauseStdDTO;

@Component
public class BenefClauseStdRESTServiceImpl implements BenefClauseStdRESTService {

	@Autowired
	private BenefClauseStdService benefClauseStdService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.BenefClauseStdRESTService#getUniqueByProductCd(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public Collection<BenefClauseStdDTO> getUniqueByProductCd(SecurityContext context, String productCd) {
		return benefClauseStdService.getUniqueByProductCd(productCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.BenefClauseStdRESTService#getByProductCdAndLangCd(javax.ws.rs.core.SecurityContext, java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<BenefClauseStdDTO> getByProductCdAndLangCd(SecurityContext context, String productCd, String langCd) {
		return benefClauseStdService.getByProductCdAndLangCd(productCd, langCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.BenefClauseStdRESTService#getByBenefClauseCd(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public Collection<BenefClauseStdDTO> getByBenefClauseCd(SecurityContext context, String benefClauseCd) {
		return benefClauseStdService.getByBenefClauseCd(benefClauseCd);
	}

}

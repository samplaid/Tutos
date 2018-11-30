package lu.wealins.webia.core.service.impl;

import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.BenefClauseStdDTO;
import lu.wealins.webia.core.service.WebiaBenefClauseStdService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class WebiaBenefClauseStdServiceImpl implements WebiaBenefClauseStdService {

	private static final String WEBIA_LOAD_CLAUSE_STD = "webia/benefClauseStd/";
	private static final String WEBIA_LOAD_CLAUSE_STD_CLAUSE_ID = "webia/benefClauseStd/byId/";

	@Autowired
	private RestClientUtils restClientUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaBenefClauseStdService#getBenefClauseStd(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<BenefClauseStdDTO> getBenefClauseStd(String productCd, String lang) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		return restClientUtils.get(WEBIA_LOAD_CLAUSE_STD, productCd + "/" + lang, params, new GenericType<Collection<BenefClauseStdDTO>>() {
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaBenefClauseStdService#getBenefClauseStd(java.lang.String)
	 */
	@Override
	public Collection<BenefClauseStdDTO> getBenefClauseStd(String productCd) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		return restClientUtils.get(WEBIA_LOAD_CLAUSE_STD, productCd, params, new GenericType<Collection<BenefClauseStdDTO>>() {
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaBenefClauseStdService#getByBenefClauseCd(java.lang.String)
	 */
	@Override
	public Collection<BenefClauseStdDTO> getByBenefClauseCd(String benefClauseCd) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		return restClientUtils.get(WEBIA_LOAD_CLAUSE_STD_CLAUSE_ID, benefClauseCd, params, new GenericType<Collection<BenefClauseStdDTO>>() {
		});
	}

}

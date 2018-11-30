package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.BenefClauseStdDTO;

public interface WebiaBenefClauseStdService {

	/**
	 * Get Beneficiaries standard clauses by product and language
	 * 
	 * @param productCd - the product code id
	 * @param lang - the language code FRA/ENG/FIN/...
	 * @return A collection of Beneficiaries standard clauses.
	 */
	Collection<BenefClauseStdDTO> getBenefClauseStd(String productCd, String lang);

	/**
	 * Get Beneficiaries standard clauses by product
	 * 
	 * @param productCd - the product code id
	 * @return A collection of Beneficiaries standard clauses.
	 */
	Collection<BenefClauseStdDTO> getBenefClauseStd(String productCd);

	/**
	 * Get Beneficiaries standard clauses by code
	 * 
	 * @param benefClauseCd - the benef clause code
	 * @return A collection of Beneficiaries standard clauses.
	 */
	Collection<BenefClauseStdDTO> getByBenefClauseCd(String benefClauseCd);

}

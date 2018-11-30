package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.BenefClauseStdDTO;

public interface BenefClauseStdService {

	/**
	 * get all the standard beneficiaries clauses available
	 * 
	 * @param productCd
	 * @return the collection of standard clauses
	 */
	Collection<BenefClauseStdDTO> getByProductCd(String productCd);

	/**
	 * get all the standard beneficiaries clauses available for a given product in a given language
	 * 
	 * @param productCd
	 * @param langCd
	 * @return the collection of standard clauses
	 */
	Collection<BenefClauseStdDTO> getByProductCdAndLangCd(String productCd, String langCd);

	/**
	 * get all the standard beneficiaries clauses available in ENG or in worst case in FRA
	 * 
	 * @param productCd
	 * @return the collection of standard clauses
	 */
	Collection<BenefClauseStdDTO> getUniqueByProductCd(String productCd);

	/**
	 * get all the standard beneficiaries clauses available for the code (all languages available)
	 * 
	 * @param benefClauseCd
	 * @return the collection of standard clauses
	 */
	Collection<BenefClauseStdDTO> getByBenefClauseCd(String benefClauseCd);
}

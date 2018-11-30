package lu.wealins.webia.services.core.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.webia.services.core.mapper.BenefClauseStdMapper;
import lu.wealins.webia.services.core.persistence.entity.BenefClauseStdEntity;
import lu.wealins.webia.services.core.persistence.repository.BenefClauseStdRepository;
import lu.wealins.webia.services.core.service.BenefClauseStdService;
import lu.wealins.common.dto.webia.services.BenefClauseStdDTO;

@Service
public class BenefClauseStdServiceImpl implements BenefClauseStdService {

	@Autowired
	private BenefClauseStdRepository benefClauseStdRepository;

	@Autowired
	private BenefClauseStdMapper benefClauseStdMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.BenefClauseStdService#getByProductCd(java.lang.String)
	 */
	@Override
	public Collection<BenefClauseStdDTO> getByProductCd(String productCd) {
		List<BenefClauseStdEntity> clauses = benefClauseStdRepository.findByProductCdOrderBySortNumberAsc(productCd);
		return benefClauseStdMapper.asBenefClauseStdDTOs(clauses);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.BenefClauseStdService#getByProductCdAndLangCd(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<BenefClauseStdDTO> getByProductCdAndLangCd(String productCd, String langCd) {
		List<BenefClauseStdEntity> clauses = benefClauseStdRepository.findByProductCdAndLangCdOrderBySortNumberAsc(productCd, langCd);
		return benefClauseStdMapper.asBenefClauseStdDTOs(clauses);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.BenefClauseStdService#getByBenefClauseCd(java.lang.String)
	 */
	@Override
	public Collection<BenefClauseStdDTO> getByBenefClauseCd(String benefClauseCd) {
		List<BenefClauseStdEntity> clauses = benefClauseStdRepository.findByBenefClauseCd(benefClauseCd);
		return benefClauseStdMapper.asBenefClauseStdDTOs(clauses);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.BenefClauseStdService#getUniqueByProductCd(java.lang.String)
	 */
	@Override
	public Collection<BenefClauseStdDTO> getUniqueByProductCd(String productCd) {
		Collection<BenefClauseStdDTO> clauses = getByProductCdAndLangCd(productCd, "ENG");
		Collection<BenefClauseStdDTO> fraClauses = getByProductCdAndLangCd(productCd, "FRA");
		if (fraClauses != null) {
			fraClauses.forEach(fr -> {
				if (!clauses.stream().anyMatch(c -> c.getBenefClauseCd().equals(fr.getBenefClauseCd()))) {
					clauses.add(fr);
				}
			});
		}
		return clauses;
	}

}

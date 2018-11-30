package lu.wealins.liability.services.core.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.UoptDetailDTO;
import lu.wealins.liability.services.core.business.UoptDefinitions;
import lu.wealins.liability.services.core.business.UoptDetailService;
import lu.wealins.liability.services.core.mapper.UoptDetailMapper;
import lu.wealins.liability.services.core.persistence.entity.UoptDetailEntity;
import lu.wealins.liability.services.core.persistence.repository.UoptDetailRepository;

@Service
public class UoptDetailServiceImpl implements UoptDetailService {

	private static final int CLIENT_SENDING_RULES = 9;
	private static final int CIRCULAR_LETTER_DESCRIPTION = 29;
	private static final int RISK_PROFILE_DESCRIPTION = 37;
	private static final int RISK_CURRENCY_DESCRIPTION = 38;
	private static final int INVESTMENT_CATEGORY_DESCRIPTION = 39;
	private static final int ALTERNATIVE_FUND_DESCRIPTION = 41;
	private static final int TYPE_POA_DESCRIPTION = 44;
	private static final int FUND_CLASSIFICATION_DESCRIPTION = 56;
	private static final int RISK_CLASS_DESCRIPTION = 57;
	private static final int TYPE_OF_AGENT_CONTACT = 62;

	@Autowired
	private UoptDetailRepository uoptDetailRepository;

	@Autowired
	private UoptDetailMapper uoptDetailMapper;

	@Override
	public UoptDetailDTO getUoptDetail(String uddId) {
		return uoptDetailMapper.asUoptDetailDTO(getUoptDetailEntity(uddId));
	}

	@Override
	public UoptDetailEntity getUoptDetailEntity(String uddId) {
		return uoptDetailRepository.findOne(uddId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.UoptDetailService#getCircularLetters()
	 */
	@Override
	public List<UoptDetailDTO> getCircularLetters() {
		return uoptDetailMapper.asUoptDetailDTOs(uoptDetailRepository.findByUoptDefinitionUdfId(CIRCULAR_LETTER_DESCRIPTION));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.UoptDetailService#getFundClassifications()
	 */
	@Override
	public List<UoptDetailDTO> getFundClassifications() {
		return uoptDetailMapper.asUoptDetailDTOs(uoptDetailRepository.findByUoptDefinitionUdfId(FUND_CLASSIFICATION_DESCRIPTION));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.UoptDetailService#getRiskClasses()
	 */
	@Override
	public List<UoptDetailDTO> getRiskClasses() {
		return uoptDetailMapper.asUoptDetailDTOs(uoptDetailRepository.findByUoptDefinitionUdfId(RISK_CLASS_DESCRIPTION));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.UoptDetailService#getRiskProfiles()
	 */
	@Override
	public List<UoptDetailDTO> getRiskProfiles() {
		return uoptDetailMapper.asUoptDetailDTOs(uoptDetailRepository.findByUoptDefinitionUdfId(RISK_PROFILE_DESCRIPTION));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.UoptDetailService#getRiskCurrencies()
	 */
	@Override
	public List<UoptDetailDTO> getRiskCurrencies() {
		return uoptDetailMapper.asUoptDetailDTOs(uoptDetailRepository.findByUoptDefinitionUdfId(RISK_CURRENCY_DESCRIPTION));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.UoptDetailService#getInvestmentCategories()
	 */
	@Override
	public List<UoptDetailDTO> getInvestmentCategories() {
		return uoptDetailMapper.asUoptDetailDTOs(uoptDetailRepository.findByUoptDefinitionUdfId(INVESTMENT_CATEGORY_DESCRIPTION));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.UoptDetailService#getAlternativeFunds()
	 */
	@Override
	public List<UoptDetailDTO> getAlternativeFunds() {
		return uoptDetailMapper.asUoptDetailDTOs(uoptDetailRepository.findByUoptDefinitionUdfId(ALTERNATIVE_FUND_DESCRIPTION));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.UoptDetailService#getTypePOAs()
	 */
	@Override
	public List<UoptDetailDTO> getTypePOAs() {
		return uoptDetailMapper.asUoptDetailDTOs(uoptDetailRepository.findByUoptDefinitionUdfId(TYPE_POA_DESCRIPTION));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.UoptDetailService#getSendingRules()
	 */
	@Override
	public List<UoptDetailDTO> getSendingRules() {
		return uoptDetailMapper.asUoptDetailDTOs(uoptDetailRepository.findByUoptDefinitionUdfId(CLIENT_SENDING_RULES));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.UoptDetailService#getTypeOfAgentContact()
	 */
	@Override
	public List<UoptDetailDTO> getTypeOfAgentContact() {
		return uoptDetailMapper.asUoptDetailDTOs(uoptDetailRepository.findByUoptDefinitionUdfId(TYPE_OF_AGENT_CONTACT));
	}

	@Override
	public List<UoptDetailDTO> getOptions(UoptDefinitions title) {
		return uoptDetailMapper.asUoptDetailDTOs(
				uoptDetailRepository.findByUoptDefinitionUdfId(title.getId()));
	}

	@Override
	public UoptDetailDTO getUoptDetailEntityForKeyValue(String keyValue) {
		return uoptDetailMapper.asUoptDetailDTO(uoptDetailRepository.findByUoptKeyValue(keyValue));
	}

}

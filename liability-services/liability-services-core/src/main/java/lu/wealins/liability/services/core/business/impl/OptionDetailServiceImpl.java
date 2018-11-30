package lu.wealins.liability.services.core.business.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.liability.services.core.business.OptionDetailService;
import lu.wealins.liability.services.core.mapper.OptionDetailMapper;
import lu.wealins.liability.services.core.persistence.entity.ProductValueEntity;
import lu.wealins.liability.services.core.persistence.repository.OptionDetailRepository;
import lu.wealins.liability.services.core.persistence.repository.ProductValueRepository;
import lu.wealins.common.dto.liability.services.OptionDetailDTO;
import lu.wealins.common.dto.liability.services.OptionDetails;

/**
 * @author XQV89
 *
 */
/**
 * @author XQV89
 *
 */
/**
 * @author XQV89
 *
 */
@Service
public class OptionDetailServiceImpl implements OptionDetailService {

	private static final String OPTION_DETAILS_CANNOT_BE_NULL = "Collection of option details cannot be null.";
	private static final String NUMBERS_CANNOT_BE_NULL = "Numbers cannot be null.";
	private static final String NBVLVS = "NBVLVS";
	private static final String CPR_ROLE = "CPR_TYPE";
	private static final String LANGUAGES = "LANGUAGES";
	private static final String CLI_MSTAT = "CLI_MSTAT";
	private static final String STATUS_POL = "STATUS_POL";
	private static final String CPR_TYPE = "CPR_TYPE";
	private static final String PRICING_FREQUENCY = "PRICING_FREQUENCY";
	private static final String PRICE_TYPE = "PRICE_TYPE";
	private static final String PAYMENT_MODE = "AGSPME";
	private static final String ACCOUNT_STATUS = "STATUS_BKA";

	@Autowired
	private OptionDetailRepository optionDetailRepository;

	@Autowired
	private ProductValueRepository productValueRepository;

	@Autowired
	private OptionDetailMapper optionDetailMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.OptionDetailService#getLives()
	 */
	@Override
	public List<OptionDetailDTO> getLives() {
		return optionDetailMapper.asOptionDetailDTOs(
				optionDetailRepository.findByOption(NBVLVS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.OptionDetailService#getLives(java.lang.String)
	 */
	@Override
	public List<OptionDetailDTO> getLives(String prdId) {
		// get the values allowed by the product
		List<String> controls = new ArrayList<String>();
		controls.add(NBVLVS);
		List<ProductValueEntity> valueEntities = productValueRepository.findByProductAndControls(prdId, controls);
		List<Integer> numbers = new ArrayList<Integer>();
		// convert all result as a list of number - ex : ["1","1;2","1;2;3","4"] => [1,2,3,4]
		valueEntities.stream()
				.forEach(pve -> Arrays.stream(pve.getAlphaValue().split(";"))
						.forEach((val) -> {
							Integer num = Integer.valueOf(val.trim());
							if (!numbers.contains(num)) {
								numbers.add(num);
							}
						}));
		if (!numbers.isEmpty()) {
			return optionDetailMapper.asOptionDetailDTOs(optionDetailRepository.findByOptionAndByNumbers(NBVLVS, numbers));
		} else {
			return new ArrayList<OptionDetailDTO>();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.OptionDetailService#getCPRRoles()
	 */
	@Override
	public OptionDetails getCPRRoles() {
		OptionDetails result = new OptionDetails();
		result.setList(
				optionDetailMapper.asOptionDetailDTOs(
						optionDetailRepository.findByOption(CPR_ROLE)));
		return result;
	}

	@Override
	public List<OptionDetailDTO> getLanguages() {
		return optionDetailMapper.asOptionDetailDTOs(
				optionDetailRepository.findByOption(LANGUAGES));
	}

	@Override
	public List<OptionDetailDTO> getMaritalStatus() {
		return optionDetailMapper.asOptionDetailDTOs(
				optionDetailRepository.findByOption(CLI_MSTAT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.OptionDetailService#getPolicyStatuses()
	 */
	@Override
	public List<OptionDetailDTO> getPolicyStatuses(Integer statusPolNumber) {
		String statusPol = STATUS_POL;
		if (statusPolNumber != null && statusPolNumber > 0) {
			statusPol += statusPolNumber;
		}
		return optionDetailMapper.asOptionDetailDTOs(
				optionDetailRepository.findByOption(statusPol));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.OptionDetailService#getClientPolicyRelationTypes()
	 */
	@Override
	public List<OptionDetailDTO> getClientPolicyRelationTypes() {
		return optionDetailMapper.asOptionDetailDTOs(
				optionDetailRepository.findByOption(CPR_TYPE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.OptionDetailService#getOptionDetail(java.util.Collection, Integer)
	 */
	@Override
	public OptionDetailDTO getOptionDetail(Collection<OptionDetailDTO> optionDetails, Integer number) {
		Assert.notNull(optionDetails, OPTION_DETAILS_CANNOT_BE_NULL);

		if (number == null) {
			return null;
		}

		return optionDetails.stream().filter(o -> o.getNumber() == number.intValue()).findFirst().orElse(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.OptionDetailService#getOptionDetails(java.util.Collection, java.util.Collection)
	 */
	@Override
	@SuppressWarnings("boxing")
	public Collection<OptionDetailDTO> getOptionDetails(Collection<OptionDetailDTO> optionDetails, Collection<Integer> numbers) {
		Assert.notNull(optionDetails, OPTION_DETAILS_CANNOT_BE_NULL);
		Assert.notNull(numbers, NUMBERS_CANNOT_BE_NULL);

		return optionDetails.stream().filter(o -> numbers.contains(o.getNumber())).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.OptionDetailService#getPricingFrequency()
	 */
	@Override
	public List<OptionDetailDTO> getPricingFrequency() {
		return optionDetailMapper.asOptionDetailDTOs(
				optionDetailRepository.findByOption(PRICING_FREQUENCY));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.OptionDetailService#getPaymentModes()
	 */
	@Override
	public List<OptionDetailDTO> getPaymentModes() {
		return optionDetailMapper.asOptionDetailDTOs(
				optionDetailRepository.findByOption(PAYMENT_MODE));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.OptionDetailService#getAccountStatuses()
	 */
	@Override
	public List<OptionDetailDTO> getAccountStatus() {
		return optionDetailMapper.asOptionDetailDTOs(
				optionDetailRepository.findByOption(ACCOUNT_STATUS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.OptionDetailService#getPolicyNote(java.lang.String)
	 */
	@Override
	public List<OptionDetailDTO> getPolicyNote(String ponType) {
		return optionDetailMapper.asOptionDetailDTOs(optionDetailRepository.findByOption(ponType));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.OptionDetailService#getPolicyNote(java.lang.String, int)
	 */
	@Override
	public OptionDetailDTO getPolicyNote(String ponType, int number) {
		return optionDetailMapper.asOptionDetailDTO(optionDetailRepository.findByOptionAndByNumber(ponType, number));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.OptionDetailService#getPriceTypes()
	 */
	@Override
	public List<OptionDetailDTO> getPriceTypes() {
		return optionDetailMapper.asOptionDetailDTOs(
				optionDetailRepository.findByOption(PRICE_TYPE));
	}
}

package lu.wealins.liability.services.core.validation.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.liability.services.core.mapper.AssetManagerStrategyMapper;
import lu.wealins.liability.services.core.persistence.entity.AssetManagerStrategyEntity;
import lu.wealins.liability.services.core.persistence.repository.AssetManagerStrategyRepository;
import lu.wealins.liability.services.core.persistence.repository.FundRepository;
import lu.wealins.liability.services.core.validation.AssetManagerStrategyValidationService;
import lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO;

@Service
public class AssetManagerStrategyValidationServiceImpl implements AssetManagerStrategyValidationService {

	@Autowired
	private AssetManagerStrategyRepository assetManagerStrategyRepository;
	@Autowired
	private AssetManagerStrategyMapper assetManagerStrategyMapper;

	@Autowired
	private FundRepository fundRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.validation.AssetManagerStrategyValidationService#assertCodeUnique(lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO, java.util.List)
	 */
	@Override
	public void validateCodeUniqueInDb(AssetManagerStrategyDTO assetManageStrategy, List<String> errors) {
		Assert.notNull(errors);

		if (assetManageStrategy != null) {

			if (assetManageStrategy.getAmsId() != null) {
				AssetManagerStrategyEntity existingStrategy = assetManagerStrategyRepository.findOne(assetManageStrategy.getAmsId());

				if (existingStrategy != null) {
					AssetManagerStrategyDTO existingStrategyDto = assetManagerStrategyMapper.asAssetManagerStrategyDTO(existingStrategy);

					if (!isEqualByProfileAndType(existingStrategyDto, assetManageStrategy)) {
						validateUnicityOfCodeInDb(assetManageStrategy, errors);
					}

				} else {
					validateUnicityOfCodeInDb(assetManageStrategy, errors);
				}

			} else {
				validateUnicityOfCodeInDb(assetManageStrategy, errors);
			}

		}

	}

	private boolean isEqualByProfileAndType(AssetManagerStrategyDTO strategy, AssetManagerStrategyDTO strategyRef) {
		if (strategy == null && strategyRef == null) {
			return true;
		}
		if (strategy == null || strategyRef == null)
			return false;
		return StringUtils.equalsIgnoreCase(StringUtils.trimToEmpty(strategy.getRiskProfile()), StringUtils.trimToEmpty(strategyRef.getRiskProfile()))
				&& StringUtils.equalsIgnoreCase(StringUtils.trimToEmpty(strategy.getRiskType()), StringUtils.trimToEmpty(strategyRef.getRiskType()));
	}

	/**
	 * @param assetManageStrategy
	 * @param errors
	 */
	private void validateUnicityOfCodeInDb(AssetManagerStrategyDTO assetManageStrategy, List<String> errors) {
		List<AssetManagerStrategyEntity> existingActiveStrategies = assetManagerStrategyRepository.findByRiskProfile(assetManageStrategy.getRiskProfile());

		if (!existingActiveStrategies.isEmpty()) {
			boolean duplicated = existingActiveStrategies.stream()
					.anyMatch(strategy -> StringUtils.equalsIgnoreCase(StringUtils.trimToEmpty(strategy.getRiskType()), StringUtils.trimToEmpty(assetManageStrategy.getRiskType())));

			if (duplicated) {
				errors.add("The asset manager strategy with code (" + StringUtils.trimToEmpty(assetManageStrategy.getRiskProfile()) + ") and risk type ("
						+ StringUtils.trimToEmpty(assetManageStrategy.getRiskType())
						+ ") exists already in database.");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.validation.AssetManagerStrategyValidationService#assertCodeUniqueInList(java.util.Collection, java.util.List)
	 */
	@Override
	public void validateCodeUniqueInList(Collection<AssetManagerStrategyDTO> assetManageStrategies, List<String> errors) {
		Assert.notNull(errors);

		if (assetManageStrategies != null) {
			for (AssetManagerStrategyDTO assetManagerStrategy : assetManageStrategies) {
				boolean duplicated = assetManageStrategies.stream()
						.anyMatch(ams -> ams != null && !ams.equals(assetManagerStrategy)
								&& StringUtils.equalsIgnoreCase(StringUtils.trimToEmpty(ams.getRiskProfile()),
										StringUtils.trimToEmpty(assetManagerStrategy.getRiskProfile()))
								&& StringUtils.equalsIgnoreCase(StringUtils.trimToEmpty(ams.getRiskType()),
										StringUtils.trimToEmpty(assetManagerStrategy.getRiskType())));

				if (duplicated) {
					errors.add("The asset manager strategy with code (" + StringUtils.trimToEmpty(assetManagerStrategy.getRiskProfile()) + ") and risk type ("
							+ StringUtils.trimToEmpty(assetManagerStrategy.getRiskType())
							+ ") is duplicated in the list.");
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.validation.AssetManagerStrategyValidationService#assertReadOnlyCodeIfLinkedToFund(lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO,
	 * java.util.List)
	 */
	@Override
	public void validateReadOnlyCodeIfLinkedToFund(AssetManagerStrategyDTO assetManageStrategy, List<String> errors) {
		Assert.notNull(errors);

		if (assetManageStrategy != null && assetManageStrategy.getAmsId() != null) {
			AssetManagerStrategyEntity existingStrategy = assetManagerStrategyRepository.findOne(assetManageStrategy.getAmsId());

			if (existingStrategy != null) {
				boolean fundLinked = fundRepository.findByRiskProfile(existingStrategy.getRiskProfile()).size() > 0;
				Assert.hasText(existingStrategy.getRiskProfile(), "The existing asset manager strategy with id (" + assetManageStrategy.getAmsId() + ") has an empty risk profile.");
				Assert.hasText(assetManageStrategy.getRiskProfile(), "The Asset manager strategy with id (" + assetManageStrategy.getAmsId() + ") has an empty risk profile.");

				if (fundLinked && !StringUtils.trimToEmpty(existingStrategy.getRiskProfile()).equalsIgnoreCase(StringUtils.trimToEmpty(assetManageStrategy.getRiskProfile()))) {
					errors.add(
							"The asset manager strategy with code (" + StringUtils.trimToEmpty(assetManageStrategy.getRiskProfile()) + ") could not be changed. It is already linked to a fund");
				}
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.validation.AssetManagerStrategyValidationService#assertReadOnlyCodeIfLinkedToFundInList(java.util.Collection, java.util.List)
	 */
	@Override
	public void validateReadOnlyCodeIfLinkedToFundInList(Collection<AssetManagerStrategyDTO> strategies, List<String> errors) {
		Assert.notNull(errors);

		if (strategies != null) {
			strategies.forEach(strategy -> validateReadOnlyCodeIfLinkedToFund(strategy, errors));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.validation.AssetManagerStrategyValidationService#assertCodeUniqueInDbList(java.util.Collection, java.util.List)
	 */
	@Override
	public void validateCodeUniqueInDbList(Collection<AssetManagerStrategyDTO> strategies, List<String> errors) {
		Assert.notNull(errors);

		if (strategies != null) {
			strategies.forEach(strategy -> validateCodeUniqueInDb(strategy, errors));
		}
	}

}

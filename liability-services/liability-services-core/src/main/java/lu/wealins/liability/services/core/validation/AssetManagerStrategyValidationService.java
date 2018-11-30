package lu.wealins.liability.services.core.validation;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO;

public interface AssetManagerStrategyValidationService {

	void validateCodeUniqueInDb(AssetManagerStrategyDTO strategy, List<String> errors);

	void validateCodeUniqueInDbList(Collection<AssetManagerStrategyDTO> strategies, List<String> errors);

	void validateCodeUniqueInList(Collection<AssetManagerStrategyDTO> strategies, List<String> errors);

	void validateReadOnlyCodeIfLinkedToFund(AssetManagerStrategyDTO strategy, List<String> errors);

	void validateReadOnlyCodeIfLinkedToFundInList(Collection<AssetManagerStrategyDTO> strategies, List<String> errors);

	default void validateRiskTypeNotBlankInList(Collection<AssetManagerStrategyDTO> strategies, List<String> errors) {
		Assert.notNull(errors);

		if (strategies != null) {
			strategies.forEach(strategy -> validateRiskTypeNotBlank(strategy, errors));
		}
	}

	default void validateCodeNotBlankInList(Collection<AssetManagerStrategyDTO> strategies, List<String> errors) {
		Assert.notNull(errors);

		if (strategies != null) {
			strategies.forEach(strategy -> validateCodeNotBlank(strategy, errors));
		}
	}

	default void validateRiskDescriptionNotBlankInList(Collection<AssetManagerStrategyDTO> strategies, List<String> errors) {
		Assert.notNull(errors);

		if (strategies != null) {
			strategies.forEach(strategy -> validateRiskDescriptionNotBlank(strategy, errors));
		}
	}

	default void validateAgentNumbersNotBlank(Collection<AssetManagerStrategyDTO> strategies, List<String> errors) {
		Assert.notNull(errors);

		if (strategies != null) {
			strategies.forEach(strategy -> validateAgentNumberNotBlank(strategy, errors));
		}
	}

	/**
	 * @param strategy
	 * @param errors
	 * @return
	 */
	default void validateAgentNumberNotBlank(AssetManagerStrategyDTO strategy, List<String> errors) {
		Assert.notNull(errors);

		if (strategy != null && StringUtils.isBlank(strategy.getAgentNo())) {
			errors.add("The asset manager strategy " + strategy.toString() + " must be linked to an agent.");
		}

	}


	default void validateRiskTypeNotBlank(AssetManagerStrategyDTO assetManageStrategy, List<String> errors) {
		Assert.notNull(errors);

		if (assetManageStrategy != null && StringUtils.isBlank(assetManageStrategy.getRiskType())) {
			errors.add("The asset manager strategy risk type is mandatory.");
		}
	}

	default void validateCodeNotBlank(AssetManagerStrategyDTO assetManageStrategy, List<String> errors) {
		Assert.notNull(errors);

		if (assetManageStrategy != null && StringUtils.isBlank(assetManageStrategy.getRiskProfile())) {
			errors.add("The asset manager strategy code is mandatory.");
		}
	}

	default void validateRiskDescriptionNotBlank(AssetManagerStrategyDTO assetManageStrategy, List<String> errors) {
		Assert.notNull(errors);

		if (assetManageStrategy != null && StringUtils.isBlank(assetManageStrategy.getRiskProfileDescription())) {
			errors.add("The asset manager strategy risk profile description is mandatory.");
		}
	}

}

package lu.wealins.webia.services.core.service.validations.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;
import lu.wealins.webia.services.core.service.validations.BeneficiaryValidationService;

@Service
public class BeneficiaryValidationServiceImpl implements BeneficiaryValidationService {

	private static final String ONE_BENEF_PART_EQ_100 = "The beneficiary part of rank must be equals to 100%.";
	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	private static final String MANDATORY_BENEFICIARY_PART = "The beneficiary part is mandatory.";
	private static final String MANDATORY_BENEFICIARY_RANK = "The beneficiary rank is mandatory.";
	private static final String CLAUSE_RANK_REQUIRED = "The clause rank cannot be empty. It was reverted to the previous value as it exists.";

	@Override
	public List<String> checkPart(Collection<BeneficiaryFormDTO> beneficiaries) {
		if (beneficiaries == null || beneficiaries.size() == 0) {
			return new ArrayList<>();
		}

		final List<String> errors = new ArrayList<>();

		if (beneficiaries.size() == 1) {
			BeneficiaryFormDTO firstBeneficiary = beneficiaries.iterator().next();
			if ((firstBeneficiary.getRankNumber() == null || firstBeneficiary.getRankNumber().intValue() != 0) && firstBeneficiary.getSplit() != null
					&& firstBeneficiary.getSplit().compareTo(ONE_HUNDRED) != 0) {
				errors.add(ONE_BENEF_PART_EQ_100);
			}
		} else if (beneficiaries.size() > 1) {
			Set<Integer> ranks = beneficiaries.stream().map(b -> b.getRankNumber()).filter(rank -> rank != null).collect(Collectors.toSet());

			if (ranks.size() > 0) {
				ranks.forEach(rank -> {
					Supplier<Stream<BigDecimal>> partStream = () -> beneficiaries.stream().filter(b -> rank.equals(b.getRankNumber())).map(x -> x.getSplit()).filter(part -> part != null);

					if (partStream.get().count() > 0) {
						BigDecimal sum = partStream.get().reduce(BigDecimal.ZERO, (x, y) -> x.add(y));
						if (sum.compareTo(ONE_HUNDRED) != 0 && rank.intValue() != 0) {
							errors.add("The sum of the beneficiary parts for the rank " + rank + " must be equals to 100%.");
						}
					}
				});
			} else {
				beneficiaries.forEach(benef -> {
					if ((benef.getRankNumber() != null || benef.getRankNumber().intValue() != 0) && benef.getSplit() != null && ONE_HUNDRED.compareTo(benef.getSplit()) != 0) {
						errors.add("The part of the beneficiary with id = " + benef.getClientId() + " must be equals to 100%. Current value is "
								+ benef.getSplit().intValue() + "%.");
					}
				});
			}

		}

		return errors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.validations.BeneficiaryValidationService#assertRankNotNull(java.util.Collection, java.util.List)
	 */
	@Override
	public void validateRankNotNull(Collection<BeneficiaryFormDTO> beneficiaries, List<String> errors) {
		Assert.notNull(errors);

		if (CollectionUtils.isNotEmpty(beneficiaries)) {
			if (beneficiaries.stream().anyMatch(beneficiary -> beneficiary.getRankNumber() == null)) {
				errors.add(MANDATORY_BENEFICIARY_RANK);
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.validations.BeneficiaryValidationService#assertPartNotNull(java.util.Collection, java.util.List)
	 */
	@Override
	public void validatePartNotNull(Collection<BeneficiaryFormDTO> beneficiaries, List<String> errors) {
		validatePartNotNullFromRank(beneficiaries, -1, errors);
	}

	@Override
	public void validatePartNotNullFromRank(Collection<BeneficiaryFormDTO> beneficiaries, Integer rank, List<String> errors) {
		Assert.notNull(errors);
		Assert.notNull(rank);

		if (CollectionUtils.isNotEmpty(beneficiaries)) {
			if (beneficiaries.stream().anyMatch(beneficiary -> beneficiary.getSplit() == null
					&& (beneficiary.getRankNumber() == null || beneficiary.getRankNumber().intValue() >= rank.intValue())
					&& BooleanUtils.isNotTrue(beneficiary.getIsEqualParts()))) {
				errors.add(MANDATORY_BENEFICIARY_PART);
			}
		}
	}

	@Override
	public void validateConstraintRankNumberClause(Collection<BenefClauseFormDTO> clausesForm, List<String> errors) {
		// Clause rank
		boolean clauseRankNull = clausesForm.stream().anyMatch(clause -> clause.getRankNumber() == null);
		if (clauseRankNull) {
			errors.add(CLAUSE_RANK_REQUIRED);
		}
	}


}

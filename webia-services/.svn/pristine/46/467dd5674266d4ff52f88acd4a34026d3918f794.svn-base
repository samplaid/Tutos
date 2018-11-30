package lu.wealins.webia.services.core.service.validations.beneficiarychangeform.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;
import lu.wealins.common.dto.webia.services.enums.ClauseType;
import lu.wealins.webia.services.core.service.validations.beneficiarychangeform.BeneficiaryClausesValidationService;

@Component
public class BeneficiaryClausesValidationServiceImpl implements BeneficiaryClausesValidationService {

	public static final String NEGATIVE_RANK_IS_NOT_ALLOWED = "Negative rank is not allowed.";
	public static final String CLAUSE_RANK_REQUIRED = "The clause rank cannot be empty. It was reverted to the previous value as it exists.";
	public static final String BENEF_CLAUSE_SAME_RANK = "The clauses and beneficiaries should not be in the same rank.";
	public static final String ONE_FREE_CLAUSE_IF_BENEF_RANK_0 = "The beneficiaries rank begins with number 0. A free clause is mandatory to report them.";
	public static final String BENEF_CLAUSE_SEQUENCE_RANK = "The ranks doesn't follow a sequence.";

	
	@Override
	public <B extends BeneficiaryFormDTO, C extends BenefClauseFormDTO> List<String> validateClausesRules(
			Collection<B> beneficiaries, Collection<C> clauses) {

		List<String> errors = new ArrayList<>();
		
		int[] sortedClauseRanks = {};
		int[] sortedBenefRank = {};
		
		// check to perform if clauses is empty
		if (CollectionUtils.isNotEmpty(beneficiaries)) {
			Supplier<Stream<Integer>> beneficiaryRankStream = () -> beneficiaries.stream().map(beneficiary -> beneficiary.getRankNumber()).filter(rank -> rank != null);

			if (beneficiaryRankStream.get().count() > 0) {
				sortedBenefRank = beneficiaryRankStream.get().sorted().distinct().mapToInt(Integer::new).toArray();
				
				if (beneficiaryRankStream.get().anyMatch(rank -> rank < 0)) {
					errors.add(NEGATIVE_RANK_IS_NOT_ALLOWED);
				}
				
				// Check if beneficiary rank number begin with 0.
				int firstRank = beneficiaryRankStream.get().sorted().distinct().findFirst().get();

				if (firstRank == 0 && CollectionUtils.isEmpty(clauses)) {
					errors.add(ONE_FREE_CLAUSE_IF_BENEF_RANK_0);
				}
			}

		}

		// check to perform if clauses is not empty
		if (CollectionUtils.isNotEmpty(clauses)) {
			Supplier<Stream<Integer>> scRank = () -> clauses.stream().map(clause -> clause.getRankNumber());

			// Clause rank
			boolean nullClauseRank = clauses.stream().anyMatch(clause -> clause.getRankNumber() == null);

			if (nullClauseRank) {
				errors.add(CLAUSE_RANK_REQUIRED);
				return errors;
			}

			sortedClauseRanks = scRank.get().sorted().mapToInt(Integer::intValue).toArray();

			// In case of the beneficiary is not provided however the clause is filled in, then the clause should begin with rank 1 (rank 1 is important).
			if (CollectionUtils.isEmpty(beneficiaries) && !checkRankSequenceRules(sortedClauseRanks, 1, 1)) {
				errors.add("The clause rank should follow a sequence which begins with number 1.");
			}

			// Case of the beneficiary is provided.
			if (CollectionUtils.isNotEmpty(beneficiaries)) {

				// all clause ranks have been filled in and benef is supposed to not contain a null rank.
				if (!nullClauseRank && sortedBenefRank.length > 0) {
					// If beneficiary rank begins with 0 then there should be at least one free clause after to list them.
					if (isRankEq0AndContainsNoFreeClause(sortedBenefRank[0], clauses)) {
						errors.add(ONE_FREE_CLAUSE_IF_BENEF_RANK_0);
					}

					int maxRankBenef = sortedBenefRank[sortedBenefRank.length - 1];
					int minRankClause = sortedClauseRanks[0];

					if (minRankClause > maxRankBenef + 1) {
						errors.add("The clause rank is out of range of the beneficiary sequence.");
					}

				}

			} 
			
			int[] sortedDistinctRanks = {};

			if (sortedBenefRank.length > 0) {
				int[] ranks = ArrayUtils.addAll(sortedClauseRanks, sortedBenefRank);
				sortedDistinctRanks = Arrays.stream(ranks).sorted().distinct().toArray();

				// Check if a duplicate ranks exists in clauses and/or beneficiaries ranks.
				if (ranks.length != sortedDistinctRanks.length) {
					errors.add(BENEF_CLAUSE_SAME_RANK);
				}
			} else {
				sortedDistinctRanks = Arrays.stream(sortedClauseRanks).sorted().distinct().toArray();

				if (sortedClauseRanks.length != sortedDistinctRanks.length) {
					errors.add("The beneficiary clause contains a duplicate rank.");
				}
			}
			
			// Clauses and beneficiary rank should follow a sequence
			if (sortedClauseRanks.length > 1 && !checkRankSequenceRules(sortedDistinctRanks, sortedDistinctRanks[0], 1)) {
				errors.add(BENEF_CLAUSE_SEQUENCE_RANK);
			}
		}
		
		return errors;
	}
	
	/**
	 * Determine if the difference between each number is equal to 1 an the giving array is in correct order.
	 * 
	 * @param ranks the arrays to be checked.
	 * @param start used to test if the value at the index 0 is equal to this value.
	 * @param diff the difference between two number.
	 * @return true if the condition is met.
	 */
	private boolean checkRankSequenceRules(int[] ranks, int start, int diff) {
		return IntStream.range(0, (int) (ranks.length)).allMatch(i -> {
			if (ranks.length == 1) {
				return ranks[0] == start;
			} else if (ranks.length == i + 1) {
				return true;
			} else {
				return ranks != null && ranks.length >= 1 && ranks[0] == start && (ranks[i + 1] == ranks[i] + diff);
			}
		});

	}
	
	private <T extends BenefClauseFormDTO > boolean isRankEq0AndContainsNoFreeClause(int rank, Collection<T> clauses) {
		return (rank == 0 && (CollectionUtils.isEmpty(clauses) || clauses.stream().noneMatch(c -> ClauseType.FREE.getValue().equals(c.getClauseTp())) ));
	}

}

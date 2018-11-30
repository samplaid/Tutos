package lu.wealins.liability.services.core.persistence.specification;

import java.util.Arrays;

import org.springframework.data.jpa.domain.Specification;

import lu.wealins.common.persistence.GenericSpecification;
import lu.wealins.liability.services.core.persistence.entity.FundTransactionEntity;
public abstract class FundTransactionSpecification {

	public static Specification<FundTransactionEntity> initial() {
		return new GenericSpecification<FundTransactionEntity>().initial("ftrId");
	}

	public static Specification<FundTransactionEntity> withPolicyId(final String pattern) {
		return new GenericSpecification<FundTransactionEntity>().withContainsCriteria(pattern, "policyId");
	}

	public static Specification<FundTransactionEntity> withEventType(final Integer... eventType) {
		return new GenericSpecification<FundTransactionEntity>().withInCriteria(Arrays.asList(eventType), "eventType");
	}

	public static Specification<FundTransactionEntity> withStatus(final Integer... status) {
		return new GenericSpecification<FundTransactionEntity>().withInCriteria(Arrays.asList(status), "status");
	}

	public static Specification<FundTransactionEntity> withFundId(String... fundId) {
		return new GenericSpecification<FundTransactionEntity>().withInCriteria(Arrays.asList(fundId), "fund");
	}
}

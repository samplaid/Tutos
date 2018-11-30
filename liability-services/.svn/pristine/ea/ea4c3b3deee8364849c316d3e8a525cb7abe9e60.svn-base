package lu.wealins.liability.services.core.persistence.specification;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import lu.wealins.common.persistence.GenericSpecification;
import lu.wealins.liability.services.core.persistence.entity.TransactionEntity;

public abstract class TransactionSpecifications {

	public static Specification<TransactionEntity> initial() {
		return new GenericSpecification<TransactionEntity>().initial("trnId");
	}
	
	public static Specification<TransactionEntity> withEventType(final Integer... eventType) {
		return new GenericSpecification<TransactionEntity>().withInCriteria(Arrays.asList(eventType), "eventType");
	}

	public static Specification<TransactionEntity> withPolicy(final String pattern) {		
		return new GenericSpecification<TransactionEntity>().withContainsCriteria(pattern, "policyId");
	}	

	public static Specification<TransactionEntity> withCurrency(final String currency) {
		return new GenericSpecification<TransactionEntity>().withContainsCriteria(currency, "currency");
	}

	public static Specification<TransactionEntity> withCoverage(Integer coverage) {
		return new GenericSpecification<TransactionEntity>().withEqualCriteria(coverage, "coverage");
	}

	public static Specification<TransactionEntity> withEffectiveDateGreaterThanOrEqualTo(Date effectiveDate) {
		return new GenericSpecification<TransactionEntity>().withGreaterThanOrEqualToCriteria(effectiveDate, "effectiveDate");
	}

	public static Specification<TransactionEntity> withEffectiveDateLessThanOrEqualTo(Date effectiveDate) {
		return new GenericSpecification<TransactionEntity>().withLessThanOrEqualToCriteria(effectiveDate, "effectiveDate");
	}

	public static Specification<TransactionEntity> withValue0(final BigDecimal value0) {
		return new GenericSpecification<TransactionEntity>().withEqualCriteria(value0, "value0");
	}

	public static Specification<TransactionEntity> withStatus(final Integer status) {
		return new GenericSpecification<TransactionEntity>().withEqualCriteria(status, "status");
	}

}

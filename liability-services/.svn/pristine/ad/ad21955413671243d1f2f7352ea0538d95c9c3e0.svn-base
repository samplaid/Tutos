package lu.wealins.liability.services.core.persistence.specification;

import java.util.Arrays;

import org.springframework.data.jpa.domain.Specification;

import lu.wealins.common.persistence.GenericSpecification;
import lu.wealins.liability.services.core.persistence.entity.PolicyAgentShareEntity;

public abstract class PolicyAgentShareSpecification {

	public static Specification<PolicyAgentShareEntity> initial() {
		return new GenericSpecification<PolicyAgentShareEntity>().initial("pasId");
	}

	public static Specification<PolicyAgentShareEntity> withPolId(String polId) {
		return new GenericSpecification<PolicyAgentShareEntity>().withContainsCriteria(polId, "polId");
	}

	public static Specification<PolicyAgentShareEntity> withCoverage(Integer coverage) {
		return new GenericSpecification<PolicyAgentShareEntity>().withEqualCriteria(coverage, "coverage");
	}

	public static Specification<PolicyAgentShareEntity> withPrimary(Integer primary) {
		return new GenericSpecification<PolicyAgentShareEntity>().withEqualCriteria(primary, "primaryAgent");
	}

	public static Specification<PolicyAgentShareEntity> withAgentCategory(final String... agentCategory) {
		return new GenericSpecification<PolicyAgentShareEntity>().withInCriteria(Arrays.asList(agentCategory), "agent.agentCategoryId");
	}

	public static Specification<PolicyAgentShareEntity> withType(final Integer... type) {
		return new GenericSpecification<PolicyAgentShareEntity>().withInCriteria(Arrays.asList(type), "type");
	}

	public static Specification<PolicyAgentShareEntity> withStatus(final Integer... status) {
		return new GenericSpecification<PolicyAgentShareEntity>().withInCriteria(Arrays.asList(status), "status");
	}

}

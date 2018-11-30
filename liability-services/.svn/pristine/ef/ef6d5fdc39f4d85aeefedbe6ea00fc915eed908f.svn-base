package lu.wealins.liability.services.core.persistence.specification;

import java.math.BigDecimal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.persistence.GenericSpecification;
import lu.wealins.liability.services.core.persistence.entity.AgentEntity;
import lu.wealins.liability.services.core.persistence.entity.FundEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyAgentShareEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyFundHoldingEntity;
public abstract class PolicySpecifications {

	/**
	 * Select only the Policy entity with a given status
	 * 
	 * @return
	 */
	public static Specification<PolicyEntity> withStatus(final Short id) {
		return new Specification<PolicyEntity>() {
			@Override
			public Predicate toPredicate(Root<PolicyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("status"), id);
			}
		};
	}

	public static Specification<PolicyEntity> withoutStatus(final Short id) {
		return new Specification<PolicyEntity>() {
			@Override
			public Predicate toPredicate(Root<PolicyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.notEqual(root.get("status"), id);
			}
		};
	}

	public static Specification<PolicyEntity> withActive() {
		return new Specification<PolicyEntity>() {
			@Override
			public Predicate toPredicate(Root<PolicyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.or(cb.and(cb.equal(root.get("status"), 1), root.get("subStatus").in(1, 3)),
						cb.and(cb.equal(root.get("status"), 2), cb.equal(root.get("subStatus"), 2)));
				// return cb.or(cb.equal(root.get("status"), 1), cb.and(cb.equal(root.get("status"), 2), cb.equal(root.get("subStatus"), 2)));
			}
		};
	}

	public static Specification<PolicyEntity> withPending() {
		return new Specification<PolicyEntity>() {
			@Override
			public Predicate toPredicate(Root<PolicyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.or(cb.and(cb.equal(root.get("status"), 3), root.get("subStatus").in(1, 2)),
						cb.and(cb.equal(root.get("status"), 4), cb.equal(root.get("subStatus"), 1)));
			}
		};
	}

	public static Specification<PolicyEntity> withNotEmptyProduct() {
		return new Specification<PolicyEntity>() {

			@Override
			public Predicate toPredicate(Root<PolicyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.and(cb.isNotNull(root.get("product").get("prdId")), cb.notLike(root.get("product").get("prdId"), " %"));
			}
		};
	}

	public static Specification<PolicyEntity> withTerminated() {
		return new Specification<PolicyEntity>() {
			@Override
			public Predicate toPredicate(Root<PolicyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.or(cb.and(cb.equal(root.get("status"), 2), root.get("subStatus").in(3, 4, 5, 7, 8)),
						cb.and(cb.equal(root.get("status"), 5), root.get("subStatus").in(1, 2, 3)),
						cb.and(cb.equal(root.get("status"), 6), cb.equal(root.get("subStatus"), 8)));
			}
		};
	}

	/**
	 * Select only the Policy entity with this id pattern
	 * 
	 * @param id
	 * @return
	 */
	public static Specification<PolicyEntity> withId(final String polId) {
		return new GenericSpecification<PolicyEntity>().withContainsCriteria(polId, "polId");
	}

	/**
	 * Select only the Policy entity with a AdditionalId (=application form) matching a pattern
	 * 
	 * @param pattern - the AdditionalId or a pattern using '%'
	 * @return
	 */
	public static Specification<PolicyEntity> withAdditionalId(final String pattern) {
		return new GenericSpecification<PolicyEntity>().withContainsCriteria(pattern, "additionalId");
	}

	/**
	 * Select only the Policy entity with a brokerRefContract (=external ref) matching a pattern
	 * 
	 * @param pattern - the brokerRefContract or a pattern using '%'
	 * @return
	 */
	public static Specification<PolicyEntity> withBrokerRefContract(final String pattern) {
		return new GenericSpecification<PolicyEntity>().withContainsCriteria(pattern, "brokerRefContract");
	}

	/**
	 * Select only the Policy entity with a broker Agent
	 * 
	 * @param pattern - the exact broker Id
	 * @return
	 */
	public static Specification<PolicyEntity> withBrokerId(final String agtId) {
		return new Specification<PolicyEntity>() {
			@Override
			public Predicate toPredicate(Root<PolicyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Join<PolicyEntity, PolicyAgentShareEntity> pas = root.join("policyAgentShares");
				Join<PolicyAgentShareEntity, AgentEntity> agent = pas.join("agent");
				return cb.and(
						cb.equal(agent.get("agtId"), agtId),
						cb.equal(agent.get("agentCategoryId"), AgentCategory.BROKER.getCategory()),
						cb.equal(pas.get("status"), 1),
						cb.equal(pas.get("coverage"), 1),
						cb.equal(pas.get("type"), 5));
			}
		};
	}

	/**
	 * Select only the Policy entity with a broker Agent
	 * 
	 * @param pattern - the exact broker Id
	 * @return
	 */
	public static Specification<PolicyEntity> withAgentId(final String agtId, final String category) {
		return new Specification<PolicyEntity>() {
			@Override
			public Predicate toPredicate(Root<PolicyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Join<PolicyEntity, PolicyAgentShareEntity> pas = root.join("policyAgentShares");
				Join<PolicyAgentShareEntity, AgentEntity> agent = pas.join("agent");
				return cb.and(
						cb.equal(agent.get("agtId"), agtId),
						cb.equal(agent.get("agentCategoryId"), category),
						cb.equal(pas.get("status"), 1),
						cb.equal(pas.get("coverage"), 1));
			}
		};
	}
	
	/**
	 * Select only the Policy entity holding a fund
	 * 
	 * @param fdsId - the exact fund Id
	 * @return
	 */
	public static Specification<PolicyEntity> withFundId(final String fdsId) {
		return new Specification<PolicyEntity>() {
			@Override
			public Predicate toPredicate(Root<PolicyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Join<PolicyEntity, PolicyFundHoldingEntity> pfh = root.join("policyFundHoldings");
				Join<PolicyFundHoldingEntity, FundEntity> fund = pfh.join("fund");
				query.distinct(true);
				return cb.and(
						cb.equal(fund.get("fdsId"), fdsId),
						cb.equal(pfh.get("status"), 1),
						cb.greaterThan(pfh.get("units"), BigDecimal.ZERO));
			}
		};
	}

	/**
	 * Select all the Policy entity
	 * 
	 * @return
	 */
	public static Specification<PolicyEntity> initial() {
		return new GenericSpecification<PolicyEntity>().initial("polId");
	}
}

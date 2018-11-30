package lu.wealins.liability.services.core.persistence.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import lu.wealins.common.persistence.GenericSpecification;
import lu.wealins.liability.services.core.persistence.entity.AgentEntity;

public abstract class AgentSpecifications {

	/**
	 * Select all the Policy entity
	 * 
	 * @return
	 */
	public static Specification<AgentEntity> initial() {
		return new GenericSpecification<AgentEntity>().initial("agtId");
	}

	/**
	 * Select only the Policy entity with a given status
	 * 
	 * @return
	 */
	public static Specification<AgentEntity> withStatus(final Short id) {
		return new Specification<AgentEntity>() {
			public Predicate toPredicate(Root<AgentEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("status"), id);
			}
		};
	}

	public static Specification<AgentEntity> withId(final String polId) {
		return new GenericSpecification<AgentEntity>().withContainsCriteria(polId, "agtId");
	}

	public static Specification<AgentEntity> withStatus(final Integer status) {
		return new GenericSpecification<AgentEntity>().withEqualCriteria(status, "status");
	}

	public static Specification<AgentEntity> withCategory(final String pattern) {
		return new Specification<AgentEntity>() {
			public Predicate toPredicate(Root<AgentEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return (pattern.contains("%") ? cb.like(root.<String>get("agentCategory").get("acaId"), pattern) : cb.equal(root.<String>get("agentCategory").get("acaId"), pattern));
			}
		};
	}

	public static Specification<AgentEntity> withCategories(final List<String> patterns) {
		return new Specification<AgentEntity>() {
			public Predicate toPredicate(Root<AgentEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (patterns != null) {
					Predicate[] predicates = new Predicate[patterns.size()];
					for (int i = 0; i < predicates.length; i++) {
						String pattern = patterns.get(i);
						if (!StringUtils.isEmpty(pattern)) {
							predicates[i] = (pattern.contains("%") ? cb.like(root.<String>get("agentCategory").get("acaId"), pattern)
									: cb.equal(root.<String>get("agentCategory").get("acaId"), pattern));
						}
					}
					return cb.or(predicates);
				} else {
					return null;
				}

			}
		};
	}

	public static Specification<AgentEntity> withCodeOrName(final String pattern) {
		return new Specification<AgentEntity>() {
			public Predicate toPredicate(Root<AgentEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (pattern.contains("%")) {
					return cb.or(cb.like(root.<String>get("name"), pattern), cb.like(root.<String>get("agtId"), pattern));
				}
				return cb.or(cb.equal(root.<String>get("name"), pattern), cb.equal(root.<String>get("agtId"), pattern));
			}
		};
	}

	public static Specification<AgentEntity> withPartnerID(final String pattern) {
		return new Specification<AgentEntity>() {
			public Predicate toPredicate(Root<AgentEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<String>get("crmRefererence"), pattern);
			}
		};
	}

}

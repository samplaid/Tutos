package lu.wealins.liability.services.core.persistence.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lu.wealins.common.persistence.GenericSpecification;

import org.springframework.data.jpa.domain.Specification;

import lu.wealins.liability.services.core.persistence.entity.AgentHierarchyEntity;

public abstract class AgentHierarchySpecifications {

	/**
	 * Select all the Policy entity
	 * 
	 * @return
	 */
	public static Specification<AgentHierarchyEntity> initial() {
		return new GenericSpecification<AgentHierarchyEntity>().initial("aghId");
	}
	
	public static Specification<AgentHierarchyEntity> withStatus(final Short status) {
		return new Specification<AgentHierarchyEntity>() {
			public Predicate toPredicate(Root<AgentHierarchyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("status"), status);
			}
		};
	}

	public static Specification<AgentHierarchyEntity> withMasterBrokerId(final String agentId) {
		return new Specification<AgentHierarchyEntity>() {

			@Override
			public Predicate toPredicate(Root<AgentHierarchyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if(root.<String>get("masterBroker") != null){					
					if(agentId.contains("%")){	
						return cb.like(root.<String>get("masterBroker").get("agtId"), agentId);						
					} else {
						return cb.equal(root.<String>get("masterBroker").get("agtId"), agentId);		
					}
				}
				return null;
			}
		};
	}

	public static Specification<AgentHierarchyEntity> withStatus(final Integer status) {
		return new GenericSpecification<AgentHierarchyEntity>().withEqualCriteria(status, "status");
	}
	
	public static Specification<AgentHierarchyEntity> withAghId(final Long aghId) {
		return new GenericSpecification<AgentHierarchyEntity>().withEqualCriteria(aghId, "aghId");
	}

	public static Specification<AgentHierarchyEntity> withWritingAgent(final String writingAgent) {
		return new GenericSpecification<AgentHierarchyEntity>().withContainsCriteria(writingAgent, "writingAgent");
	}

	public static Specification<AgentHierarchyEntity> withType(final Integer type) {
		return new GenericSpecification<AgentHierarchyEntity>().withEqualCriteria(type, "type");
	}
	
}

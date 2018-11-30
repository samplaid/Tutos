package lu.wealins.liability.services.core.persistence.repository;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.data.jpa.domain.Specification;

import lu.wealins.liability.services.core.persistence.entity.ClientEntity;

public class ClientRepositorySpecs {

	public static Specification<ClientEntity> nameStartsWith(String name) {
		return new Specification<ClientEntity>() {
			
			@Override
			public Predicate toPredicate(Root<ClientEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.like(
						builder.upper(root.get("name")), 
						name.toUpperCase() + "%");
			}
			
		};
	}

	public static Specification<ClientEntity> equalIgnoreCase(String name) {
		return new Specification<ClientEntity>() {

			@Override
			public Predicate toPredicate(Root<ClientEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(builder.upper(root.get("name")), name.toUpperCase());
			}

		};
	}

	public static Specification<ClientEntity> withDateOfBirth(Date dt) {
		return new Specification<ClientEntity>() {
			
			@Override
			public Predicate toPredicate(Root<ClientEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(root.get("dateOfBirth"), DateUtils.truncate(dt, Calendar.DATE));
			}
			
		};
	}
	
	public static Specification<ClientEntity> exclude(Long clientId) {
		return new Specification<ClientEntity>() {
			
			@Override
			public Predicate toPredicate(Root<ClientEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.notEqual(root.get("cliId"), clientId);
			}
			
		};
	}
		
}

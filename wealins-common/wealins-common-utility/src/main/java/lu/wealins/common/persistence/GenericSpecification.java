package lu.wealins.common.persistence;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification<T> {

	private static final String EMPTY_STRING = "";

	public Specification<T> initial(String primaryKey) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (StringUtils.isEmpty(primaryKey)) {
					return null;
				}

				return cb.isNotNull(root.get(primaryKey));
			}
		};
	}

	public Specification<T> withContainsCriteria(String value, String propertyName) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (StringUtils.isEmpty(value)) {
					return null;
				}

				Path<String> property = getProperty(propertyName, root, String.class);

				return value.contains("%") ? cb.like(cb.upper(property), value.toUpperCase()) : cb.equal(cb.upper(property), value.toUpperCase());
			}

		};
	}

	public Specification<T> withInCriteria(List<Object> values, String propertyName) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (values == null) {
					return null;
				}

				Path<String> property = getProperty(propertyName, root, String.class);
				return property.in(values);
			}

		};
	}

	/**
	 * Get property from property name. Example it allows to retieve the polId property if property name equals to 'policy.polId'.
	 * 
	 * @param <P>
	 * 
	 * @param propertyName The property name.
	 * @param root The root criteria
	 * @param propertyType
	 * @return The path property.
	 */
	private <P> Path<P> getProperty(String propertyName, Root<T> root, Class<P> propertyType) {
		String[] propertyNames = propertyName.split("\\.");
		if (propertyNames.length == 1) {
			return root.get(propertyNames[0]);
		}
		Path<Object> path = root.get(propertyNames[0]);
		int i;
		for (i = 1; i < propertyNames.length - 1; i++) {
			path = path.get(propertyNames[i]);
		}

		return path.get(propertyNames[i]);
	}

	public Specification<T> withEqualCriteria(Number value, String propertyName) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (value == null) {
					return null;
				}

				Path<Number> property = getProperty(propertyName, root, Number.class);
				return cb.equal(property, value);
			}
		};
	}

	public Specification<T> withEqualCriteria(BigDecimal value, String propertyName) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (value == null) {
					return null;
				}

				return cb.equal(root.<BigDecimal>get(propertyName), value);
			}
		};
	}

	public Specification<T> withEqualCriteria(Object value, String propertyName) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (value == null) {
					return null;
				}

				Path<Number> property = getProperty(propertyName, root, Number.class);
				return cb.equal(property, value);
			}
		};
	}

	public Specification<T> withGreaterThanOrEqualToCriteria(Date value, String propertyName) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.greaterThanOrEqualTo(root.<Date>get(propertyName), value);
			}
		};
	}

	public Specification<T> withLessThanOrEqualToCriteria(Date value, String propertyName) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.lessThanOrEqualTo(root.<Date>get(propertyName), value);
			}
		};
	}

	public Specification<T> withNotEqualCriteria(Number value, String propertyName) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (value == null) {
					return null;
				}

				Path<Number> property = getProperty(propertyName, root, Number.class);
				return cb.notEqual(property, value);
			}
		};
	}

	public Specification<T> withConstainsCriteriaLike(Number value, String propertyName) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (value == null) {
					return null;
				}
				Path<String> property = getProperty(propertyName, root, String.class);

				return cb.like(property, "%" + value + "%");
			}
		};
	}

	public Specification<T> withConstainsCriteriaIn(Collection<Object> values, String propertyName) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (CollectionUtils.isEmpty(values)) {
					return null;
				}

				Path<Object> property = getProperty(propertyName, root, Object.class);

				return property.in(values);
			}
		};
	}

	public Specification<T> withConstainsCriteria(Date value, String propertyName) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (value == null) {
					return null;
				}
				return cb.equal(root.get(propertyName), value);
			}
		};
	}

	public Specification<T> or(List<Specification<T>> others) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (others != null) {
					Predicate[] p = new Predicate[others.size()];
					for (int i = 0; i < others.size(); i++) {
						p[i] = others.get(i).toPredicate(root, query, cb);
					}
					return cb.or(p);
				}
				return null;
			}
		};
	}

	public Specification<T> and(List<Specification<T>> others) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (others != null) {
					Predicate[] p = new Predicate[others.size()];
					for (int i = 0; i < others.size(); i++) {
						p[i] = others.get(i).toPredicate(root, query, cb);
					}
					return cb.and(p);
				}
				return null;
			}
		};
	}

	public Specification<T> withEmptyValueCriteria(String propertyName) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> property = getProperty(propertyName, root, String.class);
				return cb.or(cb.isNull(property), cb.equal(cb.trim(property), EMPTY_STRING));
			}
		};
	}
}

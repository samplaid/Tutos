package lu.wealins.liability.services.core.utils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.security.token.SecurityContextThreadLocal;

@Component
public class HistoricManager {

	private static final int USER_COLUMN_SIZE = 5;

	private static final String USER_CANNOT_BE_NULL = "User cannot be null for historization.";

	private static final Logger log = LoggerFactory.getLogger(HistoricManager.class);

	public static final String WEBIA = "WEBIA";

	@PersistenceContext
	private EntityManager em;

	/**
	 * Change creation/modification user and date if provided entity is dirty and return a boolean indicating if
	 * entity need to be saved
	 * 
	 * @param historizable the entity to historize
	 * @return <code>true</code> if entity is dirty and need to be saved, <code>false</code> otherwise
	 */
	public <T extends Serializable> boolean historize(T historizable) {
		
		if (historizable.getClass().isAnnotationPresent(Historizable.class)) {
			
			String user = SecurityContextThreadLocal.get().getKeycloakSecurityContext().getToken().getPreferredUsername();
			Annotation annotation = historizable.getClass().getAnnotation(Historizable.class);
			Historizable h = (Historizable) annotation;

			try {
				Object idValue = getFieldValue(historizable, h.id());
				Serializable oldValues = null;

				// Creation mode - new entity
				if (idValue == null || (oldValues = findById(historizable, h, idValue)) == null) {
					Assert.notNull(user, USER_CANNOT_BE_NULL);
					setFieldValue(historizable, h.createdBy(), formatUser(user));
					setFieldValue(historizable, h.createdDate(), new Date());
					setFieldValue(historizable, h.createdTime(), new Date());
					setFieldValue(historizable, h.createdProcess(), WEBIA);

					return true;
				}

				// Creation/Update user/date are ignored. We don't want to historize if the user if different or the date is different.
				Set<String> ignoredFields = getIngoredFields(h);

				for (Field field : historizable.getClass().getDeclaredFields()) {

					// collection fields must be historize in the entity linked to the collection type.
					if (Collection.class.isAssignableFrom(field.getType())) {
						continue;
					}

					if (ignoredFields.contains(field.getName())) {
						continue;
					}
					
					Object oldValue = getFieldValue(oldValues, field.getName());
					Object newValue = getFieldValue(historizable, field.getName());

					if (isNotEquals(oldValue, newValue)) {
						Assert.notNull(user, USER_CANNOT_BE_NULL);

						setFieldValue(historizable, h.createdBy(), getFieldValue(oldValues, h.createdBy()));
						setFieldValue(historizable, h.createdDate(), getFieldValue(oldValues, h.createdDate()));
						setFieldValue(historizable, h.createdTime(), getFieldValue(oldValues, h.createdTime()));
						setFieldValue(historizable, h.createdProcess(), getFieldValue(oldValues, h.createdProcess()));

						setFieldValue(historizable, h.modifyBy(), formatUser(user));
						setFieldValue(historizable, h.modifyDate(), new Date());
						setFieldValue(historizable, h.modifyTime(), new Date());
						setFieldValue(historizable, h.modifyProcess(), WEBIA);

						return true;
					}
				}

			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				log.error("Cannot historize " + historizable.getClass().getName(), e);
			}

		}
		return false;
	}

	/**
	 * Tip: find by id based on a select in order to not retrieve the persistent from the cache.
	 * 
	 */
	private <T extends Serializable> T findById(T historizable, Historizable h, Object idValue) {
		try {
			return (T) em.createQuery("select item from " + historizable.getClass().getSimpleName() + " item where " + h.id() + " = ?1").setParameter(1, idValue).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	private Set<String> getIngoredFields(Historizable h) {
		return Stream.of(h.id(), h.createdBy(), h.createdDate(), h.createdTime(), h.createdProcess(), h.modifyBy(), h.modifyDate(), h.modifyTime(), h.modifyProcess()).collect(Collectors.toSet());
	}

	private String formatUser(String user) {
		if (user.length() > USER_COLUMN_SIZE) {
			return user.substring(0, USER_COLUMN_SIZE);
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	private boolean isNotEquals(Object oldValue, Object newValue) {
		if (oldValue == null && newValue == null) {
			return false;
		}

		boolean isOneValueIsNull = (oldValue != null && newValue == null) || (oldValue == null && newValue != null);

		if (isOneValueIsNull) {
			return true;
		}

		if (newValue instanceof String && oldValue instanceof String) {
			return !((String) newValue).trim().equals(((String) oldValue).trim());
		}

		// if the property is an entity then we compare the primary key values
		if (isAnEntity(newValue)) {
			Object identifierNewValue = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(newValue);
			Object identifierOldValue = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(oldValue);
			
			if (identifierNewValue instanceof String && identifierOldValue instanceof String) {
				return !((String) identifierNewValue).trim().equals(((String) identifierOldValue).trim());
			}
			
			return !identifierNewValue.equals(identifierOldValue);
		}

		if (newValue instanceof Number && newValue instanceof Comparable) {
			return ((Comparable) newValue).compareTo(oldValue) != 0;
		}

		return !newValue.equals(oldValue);

	}

	private boolean isAnEntity(Object entity) {
		final Class entityClass = Hibernate.getClass(entity);
		return ((Session) em.getDelegate()).getSessionFactory().getClassMetadata(entityClass) != null;
	}

	private void setFieldValue(Object object, String fieldName, Object fieldValue) throws SecurityException, IllegalArgumentException, IllegalAccessException {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(object, fieldValue);
		} catch (NoSuchFieldException e) {
			log.error("Cannot set the fieldName '" + fieldName + "' with the value '" + fieldValue + "' because it does not exist.", e);
		}
	}

	private Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(object);
	}
}

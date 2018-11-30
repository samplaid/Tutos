package lu.wealins.liability.services.core.utils.predicate;

import org.apache.commons.collections4.Predicate;

import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;

@FunctionalInterface()
public interface CliPolRelationshipPredicate extends Predicate<CliPolRelationshipEntity>{

}
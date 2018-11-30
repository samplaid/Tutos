package lu.wealins.liability.services.core.persistence.specification;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import lu.wealins.common.persistence.GenericSpecification;
import lu.wealins.liability.services.core.persistence.entity.ClientEntity;

public final class ClientSearchSpecification {
	
	public static Specification<ClientEntity> initial() {
		return new GenericSpecification<ClientEntity>().initial("cliId");
	}
	public static Specification<ClientEntity> withName( String value){
		return new GenericSpecification<ClientEntity>().withContainsCriteria(value, "name");
	}
	public static Specification<ClientEntity> withFirstName( String value){
		return new GenericSpecification<ClientEntity>().withContainsCriteria(value, "firstName");
	}
	public static Specification<ClientEntity> withMaidenName( String value){
		return new GenericSpecification<ClientEntity>().withContainsCriteria(value, "maidenName");
	}
	public static Specification<ClientEntity> withStatus( Integer value){
		return new GenericSpecification<ClientEntity>().withEqualCriteria(value, "status");
	}
	public static Specification<ClientEntity> withCode( Integer value){
		return new GenericSpecification<ClientEntity>().withEqualCriteria(value, "cliId");
	}	
	public static Specification<ClientEntity> withCodeLike( Integer value){
		return new GenericSpecification<ClientEntity>().withConstainsCriteriaLike(value, "cliId");
	}
	public static Specification<ClientEntity> withBirthDay( Date value){
		return new GenericSpecification<ClientEntity>().withConstainsCriteria(value, "dateOfBirth");
	}
	public static Specification<ClientEntity> withType( Integer value){
		return new GenericSpecification<ClientEntity>().withEqualCriteria(value, "type");
	}
	public static Specification<ClientEntity> or(List<Specification<ClientEntity>> others) {
		return new GenericSpecification<ClientEntity>().or(others);
	}	
	public static Specification<ClientEntity> and(List<Specification<ClientEntity>> others) {
		return new GenericSpecification<ClientEntity>().and(others);
	}
	
	private ClientSearchSpecification() {
		throw new RuntimeException("the constructor of this class could not be accessed");
	}
}

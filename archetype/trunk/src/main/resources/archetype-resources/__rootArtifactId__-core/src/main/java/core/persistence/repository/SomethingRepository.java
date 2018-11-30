#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ${package}.core.persistence.entity.SomethingEntity;

@Repository
public interface SomethingRepository extends JpaRepository<SomethingEntity, String> {

	public List<SomethingEntity> findByName(String name);

}
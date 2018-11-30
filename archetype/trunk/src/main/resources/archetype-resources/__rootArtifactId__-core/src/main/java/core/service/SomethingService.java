#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.service;

import java.util.List;

import ${package}.core.persistence.entity.SomethingEntity;

public interface SomethingService {

	public List<SomethingEntity> doSomething();
}

#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${package}.core.persistence.entity.SomethingEntity;
import ${package}.core.persistence.repository.SomethingRepository;
import ${package}.core.service.SomethingService;

@Service
@Transactional
public class SomethingServiceImpl implements SomethingService {

	@Autowired
	private SomethingRepository somethingRepository; 
	
	@Override
	public List<SomethingEntity> doSomething() {
		return somethingRepository.findAll();
	}

}
